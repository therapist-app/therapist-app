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
import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import FilesTable from '../../generalComponents/FilesTable'
import Layout from '../../generalComponents/Layout'
import {
  cloneChatbotTemplate,
  createChatbotTemplate,
  deleteChatbotTemplate,
} from '../../store/chatbotTemplateSlice'
import { RootState } from '../../store/store'
import {
  createDocumentForTherapist,
  deleteDocumentOfTherapist,
  getAllTherapistDocumentsOfTherapist,
} from '../../store/therapistDocumentSlice'
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import {
  cancelButtonStyles,
  commonButtonStyles,
  disabledButtonStyles,
} from '../../styles/buttonStyles'
import { therapistDocumentApi } from '../../utils/api'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const Home = (): ReactElement => {
  const navigate = useNavigate()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const loggedInTherapist = useSelector((state: RootState) => state.therapist.loggedInTherapist)
  const ownTemplates: ChatbotTemplateOutputDTO[] = (
    loggedInTherapist?.chatbotTemplatesOutputDTO ?? []
  ).filter((tpl) => tpl.patientId == null)
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
      dispatch(getAllTherapistDocumentsOfTherapist())
    }
    fetchData()
  }, [dispatch, refreshTherapistCounter])

  const handleFileUpload = async (file: File): Promise<void> => {
    await dispatch(createDocumentForTherapist(file))
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
        chatbotName: '',
        chatbotIcon: 'Chatbot',
        chatbotRole: 'FAQ',
        chatbotTone: 'friendly',
        welcomeMessage: 'Hello! How can I assist you today?',
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
  const dialogStyle = {
    width: '500px',
    height: '300px',
  }

  return (
    <Layout>
      <div style={{ display: 'flex', gap: '20px', alignItems: 'center', marginBottom: '20px' }}>
        <Typography variant='h2'>{t('dashboard.patients')}</Typography>
        <Button
          variant='contained'
          onClick={() => navigate(getPathFromPage(PAGES.PATIENTS_CREATE_PAGE))}
          sx={{ ...commonButtonStyles }}
        >
          {t('dashboard.add_patient')}
        </Button>
      </div>
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
        <Typography>{t('dashboard.no_patients_found')}</Typography>
      )}

      <CustomizedDivider />

      <div style={{ display: 'flex', gap: '20px', alignItems: 'center', marginBottom: '20px' }}>
        <Typography variant='h2'>{t('dashboard.your_chatbot_templates')}</Typography>
        <Button
          variant='contained'
          onClick={handleCreateChatbot}
          sx={{ ...commonButtonStyles, minWidth: '230px' }}
        >
          {t('dashboard.create_bot')}
        </Button>
      </div>

      {ownTemplates.length > 0 ? (
        <Box
          sx={{
            display: 'flex',
            flexWrap: 'wrap',
            gap: 2,
            justifyContent: 'flex-start',
          }}
        >
          {ownTemplates.map((bot, index) => (
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

      <CustomizedDivider />

      <FilesTable
        title={t('dashboard.your_files')}
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

export default Home
