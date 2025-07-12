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
import { useNavigate } from 'react-router-dom'

import { ChatbotTemplateOutputDTO, CreateChatbotTemplateDTO } from '../../api'
import {
  cloneChatbotTemplate,
  createChatbotTemplate,
  deleteChatbotTemplate,
} from '../../store/chatbotTemplateSlice'
import { RootState } from '../../store/store'
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const ChatbotOverview = (): ReactElement => {
  const { t } = useTranslation()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const loggedInTherapist = useSelector((state: RootState) => state.therapist.loggedInTherapist)

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [snackbarSeverity, setSnackbarSeverity] = useState<
    'info' | 'success' | 'error' | 'warning'
  >('info')

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const [currentChatbot, setCurrentChatbot] = useState<ChatbotTemplateOutputDTO | null>(null)
  const [refreshTherapistCounter, setRefreshTherapistCounter] = useState(0)

  useEffect(() => {
    dispatch(getCurrentlyLoggedInTherapist())
  }, [dispatch, refreshTherapistCounter])

  const handleCloseSnackbar = (_event?: React.SyntheticEvent | Event, reason?: string): void => {
    if (reason === 'clickaway') {
      return
    }
    setSnackbarOpen(false)
  }

  const getIconComponent = (iconName: string): ReactElement | null => {
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

  const handleCreateChatbot = async (): Promise<void> => {
    try {
      if (!loggedInTherapist) {
        return
      }

      const chatbotConfigurations: CreateChatbotTemplateDTO = {
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

      const createdChatbot = await dispatch(createChatbotTemplate(chatbotConfigurations)).unwrap()
      navigate(
        getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
          chatbotTemplateId: createdChatbot.id ?? '',
        })
      )
    } catch (error) {
      const errorMessage = handleError(error as AxiosError)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  const handleMenuClick = (
    event: React.MouseEvent<HTMLButtonElement>,
    chatbot: ChatbotTemplateOutputDTO
  ): void => {
    setAnchorEl(event.currentTarget)
    setCurrentChatbot(chatbot)
  }

  const handleMenuClose = (): void => {
    setAnchorEl(null)
  }

  const handleClone = async (): Promise<void> => {
    if (!currentChatbot) {
      return
    }
    try {
      await dispatch(cloneChatbotTemplate(currentChatbot.id ?? ''))
      setRefreshTherapistCounter((prev) => prev + 1)
      setSnackbarMessage(t('dashboard.chatbot_cloned_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
      handleMenuClose()
    } catch (error) {
      const errorMessage = handleError(error as AxiosError)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  const handleDelete = async (): Promise<void> => {
    if (!currentChatbot) {
      return
    }
    try {
      await dispatch(deleteChatbotTemplate(currentChatbot.id ?? ''))
      setRefreshTherapistCounter((prev) => prev + 1)
      setSnackbarMessage(t('dashboard.chatbot_deleted_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
      handleMenuClose()
    } catch (error) {
      const errorMessage = handleError(error as AxiosError)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  const handleChatbotTemplateClick = (chatbotTemplateId: string): void => {
    if (!loggedInTherapist?.chatbotTemplatesOutputDTO) {
      return
    }

    const selectedChatbot = loggedInTherapist.chatbotTemplatesOutputDTO.find(
      (bot) => bot.id === chatbotTemplateId
    )
    if (!selectedChatbot) {
      return
    }

    sessionStorage.setItem('chatbotTemplateId', chatbotTemplateId)
    navigate(
      getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
        chatbotTemplateId: chatbotTemplateId,
      }),
      {
        state: { chatbotConfig: selectedChatbot },
      }
    )
  }

  return (
    <>
      <div style={{ display: 'flex', gap: '20px', alignItems: 'center', marginBottom: '20px' }}>
        <Typography variant='h2'>{t('Chatbots')}</Typography>
        <Button variant='contained' onClick={handleCreateChatbot} sx={{ height: 'fit-content' }}>
          {t('Create Chatbot')}
        </Button>
      </div>

      {loggedInTherapist?.chatbotTemplatesOutputDTO &&
      loggedInTherapist.chatbotTemplatesOutputDTO.length > 0 ? (
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, justifyContent: 'flex-start' }}>
          {loggedInTherapist.chatbotTemplatesOutputDTO.map((bot, index) => (
            <Card
              key={bot.id || index}
              variant='outlined'
              sx={{
                mb: 2,
                maxWidth: '300px',
                minWidth: '300px',
                maxHeight: '250px',
                minHeight: '250px',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                position: 'relative',
                boxShadow: 'none',
                border: '1px solid #e0e0e0',
                borderRadius: '8px',
              }}
            >
              <CardActionArea onClick={() => handleChatbotTemplateClick(bot.id ?? '')}>
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
                  <Typography variant='body1' sx={{ fontSize: '48px', textAlign: 'center' }}>
                    {getIconComponent(bot.chatbotIcon ?? '')}
                  </Typography>
                </CardContent>
              </CardActionArea>

              <CardActions
                disableSpacing
                sx={{
                  position: 'absolute',
                  top: 0,
                  right: 0,
                }}
              >
                <IconButton
                  aria-label='more'
                  aria-controls='chatbot-menu'
                  aria-haspopup='true'
                  onClick={(event) => handleMenuClick(event, bot)}
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

      <Menu
        id='chatbot-menu'
        anchorEl={anchorEl}
        keepMounted
        open={Boolean(anchorEl && currentChatbot)}
        onClose={handleMenuClose}
      >
        <MenuItem onClick={handleClone}>{t('dashboard.clone')}</MenuItem>
        <MenuItem onClick={handleDelete}>{t('dashboard.delete')}</MenuItem>
      </Menu>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </>
  )
}

export default ChatbotOverview
