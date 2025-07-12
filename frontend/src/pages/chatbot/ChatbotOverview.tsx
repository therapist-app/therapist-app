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
import React, { ReactElement, useEffect, useState } from 'react'
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
  clonePatientChatbotTemplate,
  createChatbotTemplate,
  createPatientChatbotTemplate,
  deleteChatbotTemplate,
  deletePatientChatbotTemplate,
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

  const therapist = useSelector((s: RootState) => s.therapist.loggedInTherapist)
  const patient = useSelector((s: RootState) =>
    patientId ? s.patient.allPatientsOfTherapist.find((p) => p.id === patientId) : undefined
  )

  const templates: ChatbotTemplateOutputDTO[] = patientId
    ? (patient?.chatbotTemplatesOutputDTO ?? [])
    : (therapist?.chatbotTemplatesOutputDTO ?? [])

  const [snackbar, setSnackbar] = useState({
    open: false,
    message: '',
    severity: 'info' as 'info' | 'success' | 'error' | 'warning',
  })
  const openSnackbar = (msg: string, sev: typeof snackbar.severity = 'info') =>
    setSnackbar({ open: true, message: msg, severity: sev })

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const [currentChatbot, setCurrentChatbot] = useState<ChatbotTemplateOutputDTO | null>(null)

  useEffect(() => {
    patientId ? dispatch(getAllPatientsOfTherapist()) : dispatch(getCurrentlyLoggedInTherapist())
  }, [dispatch, patientId])

  const iconFor = (icon: string) =>
    (
      ({
        Chatbot: <TbMessageChatbot />,
        Robot: <RiRobot2Line />,
        Person: <IoPersonOutline />,
        Bulb: <IoBulbOutline />,
        Book: <PiBookOpenTextLight />,
      }) as Record<string, ReactElement | null>
    )[icon] ?? null

  const handleCreateChatbot = async () => {
    try {
      const dto: CreateChatbotTemplateDTO = {
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
        chatbotInputPlaceholder: 'Type your question…',
        description: '',
      }

      const created = patientId
        ? await dispatch(createPatientChatbotTemplate({ patientId: patientId, dto: dto })).unwrap()
        : await dispatch(createChatbotTemplate(dto)).unwrap()

      patientId ? dispatch(getAllPatientsOfTherapist()) : dispatch(getCurrentlyLoggedInTherapist())

      navigate(
        getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
          chatbotTemplateId: created.id!,
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
      if (patientId) {
        await dispatch(
          clonePatientChatbotTemplate({ patientId: patientId, templateId: currentChatbot.id! })
        ).unwrap()
        dispatch(getAllPatientsOfTherapist())
      } else {
        await dispatch(cloneChatbotTemplate(currentChatbot.id!)).unwrap()
        dispatch(getCurrentlyLoggedInTherapist())
      }
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
      if (patientId) {
        await dispatch(
          deletePatientChatbotTemplate({ patientId: patientId, templateId: currentChatbot.id! })
        ).unwrap()
        dispatch(getAllPatientsOfTherapist())
      } else {
        await dispatch(deleteChatbotTemplate(currentChatbot.id!)).unwrap()
        dispatch(getCurrentlyLoggedInTherapist())
      }
      openSnackbar(t('dashboard.chatbot_deleted_success'), 'success')
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    } finally {
      closeMenu()
    }
  }

  return (
    <>
      <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', mb: 2 }}>
        <Typography variant='h2'>{t('Chatbots')}</Typography>
        <Button variant='contained' onClick={handleCreateChatbot}>
          {t('Create Chatbot')}
        </Button>
      </Box>

      {templates.length ? (
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
                <IconButton onClick={(e) => handleMenu(e, bot)}>
                  <MoreVertIcon />
                </IconButton>
              </CardActions>
            </Card>
          ))}
        </Box>
      ) : (
        <Typography>{t('dashboard.no_chatbots_created_yet')}</Typography>
      )}

      <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={closeMenu}>
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
