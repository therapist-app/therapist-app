import MoreVertIcon from '@mui/icons-material/MoreVert'
import {
  Alert,
  Box,
  Button,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  IconButton,
  Menu,
  MenuItem,
  Snackbar,
  Typography,
} from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { IoBulbOutline, IoPersonOutline } from 'react-icons/io5'
import { PiBookOpenTextLight } from 'react-icons/pi'
import { RiRobot2Line } from 'react-icons/ri'
import { TbMessageChatbot } from 'react-icons/tb'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { ChatbotTemplateOutputDTO, CreateChatbotTemplateDTO } from '../../api'
import {
  cloneChatbotTemplate,
  createChatbotTemplate,
  createPatientChatbotTemplate,
  deleteChatbotTemplate,
} from '../../store/chatbotTemplateSlice'
import { getAllPatientsOfTherapist } from '../../store/patientSlice'
import { RootState } from '../../store/store'
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const ChatbotOverview = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams<{ patientId: string }>()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const therapist = useSelector((state: RootState) => state.therapist.loggedInTherapist)

  const patient = useSelector((state: RootState) =>
    patientId ? state.patient.allPatientsOfTherapist.find((p) => p.id === patientId) : undefined
  )

  const templates: ChatbotTemplateOutputDTO[] = patientId
    ? (patient?.chatbotTemplatesOutputDTO ?? [])
    : (therapist?.chatbotTemplatesOutputDTO ?? [])

  const [snackbar, setSnackbar] = useState<{
    open: boolean
    message: string
    severity: 'info' | 'success' | 'error' | 'warning'
  }>({ open: false, message: '', severity: 'info' })

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const [currentChatbot, setCurrentChatbot] = useState<ChatbotTemplateOutputDTO | null>(null)

  useEffect(() => {
    if (patientId) {
      dispatch(getAllPatientsOfTherapist())
    } else {
      dispatch(getCurrentlyLoggedInTherapist())
    }
  }, [dispatch, patientId])

  const openSnackbar = (
    message: string,
    severity: 'info' | 'success' | 'error' | 'warning' = 'info'
  ) => setSnackbar({ open: true, message: message, severity: severity })

  const iconFor = (iconName: string): ReactElement | null => {
    switch (iconName) {
      case 'Chatbot':
        return <TbMessageChatbot />
      case 'Robot':
        return <RiRobot2Line />
      case 'Person':
        return <IoPersonOutline />
      case 'Bulb':
        return <IoBulbOutline />
      case 'Book':
        return <PiBookOpenTextLight />
      default:
        return null
    }
  }

  const handleCreateChatbot = async () => {
    try {
      const cfg: CreateChatbotTemplateDTO = {
        chatbotName: '',
        chatbotModel: 'gpt-3.5-turbo',
        chatbotIcon: 'Chatbot',
        chatbotLanguage: 'English',
        chatbotRole: 'FAQ',
        chatbotTone: 'friendly',
        welcomeMessage: 'Hello! How can I assist you today?',
        chatbotVoice: 'None',
        chatbotGender: 'Neutral',
        preConfiguredExercise: 'Breathing exercise',
        additionalExercise: 'Meditation practice',
        animation: 'Simple',
        chatbotInputPlaceholder: 'Type your question...',
        description: '',
      }

      console.log(patientId)

      const created = patientId
        ? await dispatch(createPatientChatbotTemplate({ patientId: patientId, dto: cfg })).unwrap()
        : await dispatch(createChatbotTemplate(cfg)).unwrap()

      patientId ? dispatch(getAllPatientsOfTherapist()) : dispatch(getCurrentlyLoggedInTherapist())

      navigate(
        getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
          chatbotTemplateId: created.id ?? '',
        })
      )
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    }
  }

  const handleMenu = (e: React.MouseEvent<HTMLButtonElement>, bot: ChatbotTemplateOutputDTO) => {
    setAnchorEl(e.currentTarget)
    setCurrentChatbot(bot)
  }

  const closeMenu = () => setAnchorEl(null)

  const handleClone = async () => {
    if (!currentChatbot) {
      return
    }
    try {
      await dispatch(cloneChatbotTemplate(currentChatbot.id!)).unwrap()
      dispatch(getCurrentlyLoggedInTherapist())
      openSnackbar(t('dashboard.chatbot_cloned_success'), 'success')
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    } finally {
      closeMenu()
    }
  }

  const handleDelete = async () => {
    if (!currentChatbot) {
      return
    }
    try {
      await dispatch(deleteChatbotTemplate(currentChatbot.id!)).unwrap()
      dispatch(getCurrentlyLoggedInTherapist())
      openSnackbar(t('dashboard.chatbot_deleted_success'), 'success')
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    } finally {
      closeMenu()
    }
  }

  return (
    <>
      <div style={{ display: 'flex', gap: 20, alignItems: 'center', marginBottom: 20 }}>
        <Typography variant='h2'>{t('Chatbots')}</Typography>
        <Button variant='contained' onClick={handleCreateChatbot}>
          {t('Create Chatbot')}
        </Button>
      </div>

      {templates.length > 0 ? (
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2 }}>
          {templates.map((bot) => (
            <Card
              key={bot.id}
              sx={{
                maxWidth: 300,
                minWidth: 300,
                maxHeight: 250,
                minHeight: 250,
                position: 'relative',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                border: '1px solid #e0e0e0',
                boxShadow: 'none',
                borderRadius: 2,
              }}
            >
              <CardActionArea
                onClick={() =>
                  navigate(
                    getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
                      chatbotTemplateId: bot.id!,
                    }),
                    { state: { chatbotConfig: bot } }
                  )
                }
              >
                <CardContent>
                  <Typography variant='h6'>
                    {bot.chatbotName || t('dashboard.unnamed_bot')}
                  </Typography>
                  <Typography variant='body2' color='textSecondary'>
                    {bot.welcomeMessage || t('dashboard.no_welcome_message_set')}
                  </Typography>

                  <Typography variant='body1' sx={{ mt: 1 }}>
                    {t('dashboard.language')}: {bot.chatbotLanguage}
                  </Typography>
                  <Typography variant='body1' sx={{ mt: 1 }}>
                    {t('dashboard.role')}: {bot.chatbotRole}
                  </Typography>
                  <Typography variant='body1'>{`Tone: ${bot.chatbotTone}`}</Typography>

                  <Typography variant='body1' sx={{ fontSize: 48, textAlign: 'center' }}>
                    {iconFor(bot.chatbotIcon ?? '')}
                  </Typography>
                </CardContent>
              </CardActionArea>

              <CardActions disableSpacing sx={{ position: 'absolute', top: 0, right: 0 }}>
                <IconButton
                  aria-label='more'
                  aria-controls='chatbot-menu'
                  aria-haspopup='true'
                  onClick={(e) => handleMenu(e, bot)}
                >
                  <MoreVertIcon />
                </IconButton>
              </CardActions>
            </Card>
          ))}
        </Box>
      ) : (
        <Typography>{t('dashboard.no_chatbots_created_yet')}</Typography>
      )}

      <Menu id='chatbot-menu' anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={closeMenu}>
        <MenuItem onClick={handleClone}>{t('dashboard.clone')}</MenuItem>
        <MenuItem onClick={handleDelete}>{t('dashboard.delete')}</MenuItem>
      </Menu>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={6000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          severity={snackbar.severity}
          sx={{ width: '100%' }}
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  )
}

export default ChatbotOverview
