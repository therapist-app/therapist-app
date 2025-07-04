import AddIcon from '@mui/icons-material/Add'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import {
  Alert,
  Box,
  Button,
  Card,
  CardActionArea,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Menu,
  MenuItem,
  Snackbar,
  TextField,
  Typography,
} from '@mui/material'
import CardActions from '@mui/material/CardActions'
import { AxiosError } from 'axios'
import React, { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { IoBulbOutline, IoPersonOutline } from 'react-icons/io5'
import { PiBookOpenTextLight } from 'react-icons/pi'
import { RiRobot2Line } from 'react-icons/ri'
import { TbMessageChatbot } from 'react-icons/tb'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

import { ChatbotTemplateOutputDTO, CreateChatbotTemplateDTO } from '../../api'
import FilesTable from '../../generalComponents/FilesTable'
import Layout from '../../generalComponents/Layout'
import {
  cloneChatbotTemplate,
  createChatbotTemplate,
  deleteChatbotTemplate,
  updateChatbotTemplate,
} from '../../store/chatbotTemplateSlice'
import { RootState } from '../../store/store'
import {
  createDocumentForTherapist,
  deleteDocumentOfTherapist,
  getAllTherapistDocumentsOfTherapist,
} from '../../store/therapistDocumentSlice'
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import { therapistDocumentApi } from '../../utils/api'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const Dashboard = (): ReactElement => {
  const navigate = useNavigate()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const loggedInTherapist = useSelector((state: RootState) => state.therapist.loggedInTherapist)
  const allTherapistDocuments = useSelector(
    (state: RootState) => state.therapistDocument.allTherapistDocumentsOfTherapist
  )

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [snackbarSeverity, setSnackbarSeverity] = useState<
    'info' | 'success' | 'error' | 'warning'
  >('info')

  const [openBotDialog, setOpenBotDialog] = useState(false)
  const [chatbotName, setChatbotName] = useState('')

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const [currentChatbot, setCurrentChatbot] = useState<ChatbotTemplateOutputDTO | null>(null)
  const [openRenameDialog, setOpenRenameDialog] = useState(false)

  const [refreshTherapistCounter, setRefreshTherapistCounter] = useState(0)

  useEffect(() => {
    const fetchData = async (): Promise<void> => {
      await dispatch(getCurrentlyLoggedInTherapist())
      if (loggedInTherapist?.id) {
        dispatch(getAllTherapistDocumentsOfTherapist(loggedInTherapist.id))
      }
    }
    fetchData()
  }, [dispatch, refreshTherapistCounter, loggedInTherapist?.id])

  const handleFileUpload = async (file: File): Promise<void> => {
    await dispatch(
      createDocumentForTherapist({
        file: file,
        therapistId: loggedInTherapist?.id ?? '',
      })
    )
    setRefreshTherapistCounter((prev) => prev + 1)
  }

  const handleDeleteFile = async (fileId: string): Promise<void> => {
    await dispatch(deleteDocumentOfTherapist(fileId))
    setRefreshTherapistCounter((prev) => prev + 1)
  }

  const downloadFile = async (fileId: string): Promise<string> => {
    const response = await therapistDocumentApi.downloadTherapistDocument(fileId, {
      responseType: 'blob',
    })
    const file = response.data
    const url = window.URL.createObjectURL(file)
    return url
  }

  const handleCloseSnackbar = (_event?: React.SyntheticEvent | Event, reason?: string): void => {
    if (reason === 'clickaway') {
      return
    }
    setSnackbarOpen(false)
  }
  const handlePatientClick = (patientId: string): void => {
    navigate(getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, { patientId: patientId }))
  }
  const handleOpenBotDialog = (): void => {
    setOpenBotDialog(true)
  }
  const handleCloseBotDialog = (): void => {
    setOpenBotDialog(false)
    setChatbotName('')
  }
  const handleCreateChatbot = async (): Promise<void> => {
    try {
      if (!loggedInTherapist) {
        return
      }
      const chatbotConfigurations: CreateChatbotTemplateDTO = {
        chatbotName: chatbotName,
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
        workspaceId: loggedInTherapist.workspaceId,
      }
      await dispatch(createChatbotTemplate(chatbotConfigurations))
      setRefreshTherapistCounter((prev) => prev + 1)
      setSnackbarMessage(t('dashboard.chatbot_created_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
      handleCloseBotDialog()
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
  const handleRename = (): void => {
    if (currentChatbot) {
      setChatbotName(currentChatbot.chatbotName ?? '')
      setOpenRenameDialog(true)
    }
    handleMenuClose()
  }
  const handleRenameChatbot = async (): Promise<void> => {
    try {
      if (!currentChatbot) {
        return
      }
      await dispatch(
        updateChatbotTemplate({
          chatbotTemplateId: currentChatbot.id ?? '',
          updateChatbotTemplateDTO: { chatbotName: chatbotName },
        })
      )
      setRefreshTherapistCounter((prev) => prev + 1)
      setSnackbarMessage(t('dashboard.chatbot_named_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
      setOpenRenameDialog(false)
    } catch (error) {
      const errorMessage = handleError(error as AxiosError)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
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
    try {
      if (!currentChatbot) {
        return
      }
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
        state: {
          chatbotConfig: selectedChatbot,
        },
      }
    )
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
  const commonButtonStyles = {
    borderRadius: 20,
    textTransform: 'none',
    fontSize: '1rem',
    minWidth: '130px',
    maxWidth: '130px',
    padding: '6px 24px',
    lineHeight: 1.75,
    backgroundColor: '#635BFF',
    backgroundImage: 'linear-gradient(45deg, #635BFF 30%, #7C4DFF 90%)',
    boxShadow: '0 3px 5px 2px rgba(99, 91, 255, .3)',
    color: 'white',
    '&:hover': {
      backgroundColor: '#7C4DFF',
    },
    margin: 1,
  } as const
  const disabledButtonStyles = {
    ...commonButtonStyles,
    backgroundImage: 'lightgrey',
    '&:hover': {
      disabled: 'true',
    },
  } as const
  const cancelButtonStyles = {
    borderRadius: 20,
    textTransform: 'none',
    fontSize: '1rem',
    minWidth: '130px',
    maxWidth: '130px',
    padding: '6px 24px',
    lineHeight: 1.75,
    backgroundColor: 'white',
    color: '#635BFF',
    '&:hover': {
      backgroundColor: '#f0f0f0',
    },
    margin: 1,
  } as const
  const dialogStyle = {
    width: '500px',
    height: '300px',
  }

  return (
    <Layout>
      <Box sx={{ marginBottom: 4 }}>
        <Card
          sx={{
            p: 2,
            boxShadow: 'none',
            border: '1px solid #e0e0e0',
            borderRadius: '8px',
          }}
        >
          <Typography variant='h6' gutterBottom>
            {t('dashboard.register_new_patient')}
          </Typography>
          <Button
            variant='contained'
            startIcon={<AddIcon />}
            onClick={() => navigate(getPathFromPage(PAGES.PATIENTS_CREATE_PAGE))}
            sx={commonButtonStyles}
            style={{ minWidth: '200px', maxWidth: '200px' }}
          >
            {t('dashboard.add_patient')}
          </Button>
        </Card>
      </Box>

      <Typography variant='h5' sx={{ marginBottom: 3 }}>
        {t('dashboard.patients')}
      </Typography>
      {loggedInTherapist?.patientsOutputDTO && loggedInTherapist.patientsOutputDTO.length > 0 ? (
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, justifyContent: 'flex-start' }}>
          {loggedInTherapist?.patientsOutputDTO.map((patient) => (
            <Card
              key={patient.id}
              variant='outlined'
              sx={{
                mb: 2,
                maxWidth: '300px',
                minWidth: '300px',
                maxHeight: '150px',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                position: 'relative',
                boxShadow: 'none',
                border: '1px solid #e0e0e0',
                borderRadius: '8px',
              }}
            >
              <CardActionArea onClick={() => handlePatientClick(patient.id ?? '')}>
                <CardContent>
                  <Typography variant='h6'>{patient.name ?? 'Unnamed Patient'}</Typography>
                  <Typography variant='body2' color='textSecondary'>
                    {t('dashboard.patient_id')}: {patient.id}
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          ))}
        </Box>
      ) : (
        <Typography variant='subtitle1' sx={{ textAlign: 'center' }}>
          {t('dashboard.no_patients_found')}
        </Typography>
      )}

      <Box sx={{ mt: 6, mb: 4 }}>
        <Card
          sx={{
            p: 2,
            boxShadow: 'none',
            border: '1px solid #e0e0e0',
            borderRadius: '8px',
          }}
        >
          <Typography variant='h6' gutterBottom>
            {t('dashboard.create_new_chatbot_template')}
          </Typography>
          <Button
            variant='contained'
            startIcon={<AddIcon />}
            onClick={handleOpenBotDialog}
            sx={commonButtonStyles}
            style={{ minWidth: '200px', maxWidth: '200px' }}
          >
            {t('dashboard.create_bot')}
          </Button>
        </Card>
      </Box>

      <Typography variant='h5' sx={{ mt: 6, mb: 3 }}>
        {t('dashboard.your_chatbot_templates')}
      </Typography>
      {loggedInTherapist?.chatbotTemplatesOutputDTO &&
      loggedInTherapist.chatbotTemplatesOutputDTO.length > 0 ? (
        <Box
          sx={{
            display: 'flex',
            flexWrap: 'wrap',
            gap: 2,
            justifyContent: 'flex-start',
          }}
        >
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
        <Typography variant='subtitle1' sx={{ textAlign: 'center' }}>
          {t('dashboard.no_chatbots_created_yet')}
        </Typography>
      )}

      <FilesTable
        title='Your Files'
        allDocuments={allTherapistDocuments}
        handleFileUpload={handleFileUpload}
        handleDeleteFile={handleDeleteFile}
        downloadFile={downloadFile}
      />

      <Menu
        id='chatbot-menu'
        anchorEl={anchorEl}
        keepMounted
        open={Boolean(anchorEl && currentChatbot)}
        onClose={handleMenuClose}
      >
        <MenuItem onClick={handleRename}>{t('dashboard.rename')}</MenuItem>
        <MenuItem onClick={handleClone}>{t('dashboard.clone')}</MenuItem>
        <MenuItem onClick={handleDelete}>{t('dashboard.delete')}</MenuItem>
      </Menu>

      <Dialog open={openBotDialog} onClose={handleCloseBotDialog} PaperProps={{ sx: dialogStyle }}>
        <DialogTitle>{t('dashboard.new_bot')}</DialogTitle>
        <DialogContent sx={{ mt: 1 }}>
          <DialogContentText sx={{ mb: 1 }}>
            {t('dashboard.what_would_you_like_to_name_your_bot')}
          </DialogContentText>
          <TextField
            autoFocus
            margin='dense'
            id='bot-name'
            label={t('dashboard.enter_bot_name')}
            type='text'
            fullWidth
            variant='outlined'
            value={chatbotName}
            onChange={(e) => setChatbotName(e.target.value)}
          />
        </DialogContent>
        <DialogActions sx={{ justifyContent: 'right', pr: 2 }}>
          <Button onClick={handleCloseBotDialog} sx={cancelButtonStyles}>
            {t('dashboard.cancel')}
          </Button>
          <Button
            onClick={handleCreateChatbot}
            variant='contained'
            sx={chatbotName.trim() !== '' ? commonButtonStyles : disabledButtonStyles}
            disabled={chatbotName.trim() === ''}
          >
            {t('dashboard.create_bot')}
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog
        open={openRenameDialog}
        onClose={() => setOpenRenameDialog(false)}
        PaperProps={{ sx: dialogStyle }}
      >
        <DialogTitle>{t('dashboard.rename_bot')}</DialogTitle>
        <DialogContent sx={{ mt: 1 }}>
          <DialogContentText sx={{ mb: 1 }}>
            {t('dashboard.enter_new_name_for_bot')}
          </DialogContentText>
          <TextField
            autoFocus
            margin='dense'
            id='rename'
            label={t('dashboard.enter_new_bot_name')}
            type='text'
            fullWidth
            variant='outlined'
            value={chatbotName}
            onChange={(e) => setChatbotName(e.target.value)}
          />
        </DialogContent>
        <DialogActions sx={{ justifyContent: 'right', pr: 2 }}>
          <Button onClick={() => setOpenRenameDialog(false)} sx={cancelButtonStyles}>
            {t('dashboard.cancel')}
          </Button>
          <Button
            onClick={handleRenameChatbot}
            variant='contained'
            sx={chatbotName.trim() !== '' ? commonButtonStyles : disabledButtonStyles}
            disabled={chatbotName.trim() === ''}
          >
            {t('dashboard.save')}
          </Button>
        </DialogActions>
      </Dialog>

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
    </Layout>
  )
}

export default Dashboard
