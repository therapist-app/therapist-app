import React, { useState, useEffect, useRef } from 'react'
import { useLocation } from 'react-router-dom'
import {
  Button,
  TextField,
  Box,
  Typography,
  CircularProgress,
  Grid,
  Paper,
  List,
  ListItem,
  Tabs,
  Tab,
  Snackbar,
  Alert,
  Avatar,
  FormControl,
  Select,
  MenuItem,
  InputLabel,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
} from '@mui/material'
import SendIcon from '@mui/icons-material/Send'
import DeleteIcon from '@mui/icons-material/Delete'
import DownloadIcon from '@mui/icons-material/Download'

import Layout from '../../generalComponents/Layout'
import FileUpload from '../../generalComponents/FileUpload'

import { TbMessageChatbot } from 'react-icons/tb'
import { RiRobot2Line } from 'react-icons/ri'
import { IoPersonOutline, IoBulbOutline } from 'react-icons/io5'
import { PiBookOpenTextLight } from 'react-icons/pi'

import { useAppDispatch } from '../../utils/hooks'
import { updateChatbotTemplate } from '../../store/chatbotTemplateSlice'
import { AxiosError } from 'axios'
import { handleError } from '../../utils/handleError'
import { useTranslation } from 'react-i18next'
import { ChatbotTemplateOutputDTO } from '../../api'

const ChatBotTemplateEdit = () => {
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const { state } = useLocation() as { state?: { chatbotConfig?: ChatbotTemplateOutputDTO } }

  const [chatbotConfig, setChatbotConfig] = useState<ChatbotTemplateOutputDTO | null>(null)

  const [selectedTab, setSelectedTab] = useState<'config' | 'analytics' | 'sources'>('config')

  const [chat, setChat] = useState<Array<{ question?: string; response: string | null }>>([])
  const [isLoading, setIsLoading] = useState(false)
  const [isChatbotTyping, setIsChatbotTyping] = useState(false)
  const chatListRef = useRef<HTMLUListElement>(null)

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [snackbarSeverity, setSnackbarSeverity] = useState<
    'info' | 'success' | 'error' | 'warning'
  >('info')

  const [threads, setThreads] = useState<Array<{ threadId: string }>>([])
  const [question, setQuestion] = useState('')

  const [chatbotName, setChatbotName] = useState('')
  const [chatbotRole, setChatbotRole] = useState('')
  const [chatbotLanguage, setChatbotLanguage] = useState('')
  const [chatbotIcon, setChatbotIcon] = useState('')
  const [chatbotVoice, setChatbotVoice] = useState('')
  const [chatbotGender, setChatbotGender] = useState('')
  const [chatbotTone, setChatbotTone] = useState('')
  const [preConfiguredExercise, setPreconfiguredExercise] = useState('')
  const [additionalExercise, setAdditionalExercise] = useState('')
  const [chatbotAnimation, setChatbotAnimation] = useState('')
  const [welcomeMessage, setWelcomeMessage] = useState('')
  const [chatbotInputPlaceholder, setChatbotInputPlaceholder] = useState('')
  const [files, setFiles] = useState<Array<{ id: string; fileName: string }>>([])

  useEffect(() => {
    if (state?.chatbotConfig) {
      setChatbotConfig(state.chatbotConfig)
    }
  }, [state])

  useEffect(() => {
    if (chatbotConfig) {
      console.log('Setting config fields with:', chatbotConfig)

      setChatbotName(chatbotConfig.chatbotName || '')
      setChatbotRole(chatbotConfig.chatbotRole || '')
      setChatbotLanguage(chatbotConfig.chatbotLanguage || '')
      setChatbotIcon(chatbotConfig.chatbotIcon || '')
      setChatbotVoice(chatbotConfig.chatbotVoice || '')
      setChatbotGender(chatbotConfig.chatbotGender || '')
      setChatbotTone(chatbotConfig.chatbotTone || '')

      setPreconfiguredExercise(chatbotConfig.preConfiguredExercise || '')
      setAdditionalExercise(chatbotConfig.additionalExercise || '')
      setChatbotAnimation(chatbotConfig.animation || '')
      setWelcomeMessage(chatbotConfig.welcomeMessage || '')
      setChatbotInputPlaceholder(chatbotConfig.chatbotInputPlaceholder || '')

      if (chatbotConfig.welcomeMessage) {
        setChat([{ response: chatbotConfig.welcomeMessage }])
      }
    }
  }, [chatbotConfig])

  useEffect(() => {
    if (selectedTab === 'analytics') {
      setThreads([{ threadId: 'thread-001' }, { threadId: 'thread-002' }])
    }
  }, [selectedTab])

  useEffect(() => {
    if (chatListRef.current) {
      chatListRef.current.scrollTop = chatListRef.current.scrollHeight
    }
  }, [chat])

  const handleTabChange = (
    _event: React.SyntheticEvent,
    newValue: 'config' | 'analytics' | 'sources'
  ) => {
    setSelectedTab(newValue)
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!question.trim()) {
      alert('Question cannot be empty.')
      return
    }
    setIsLoading(true)
    setIsChatbotTyping(true)
    const newChatEntry = { question, response: null }
    setChat((prev) => [...prev, newChatEntry])
    setQuestion('')
    try {
      await new Promise((res) => setTimeout(res, 800))
      const mockResponse = `AI response to: ${newChatEntry.question}`
      setChat((prev) => {
        const updated = [...prev]
        updated[updated.length - 1] = { ...updated[updated.length - 1], response: mockResponse }
        return updated
      })
    } catch (error) {
      console.error('Chat error:', error)
    } finally {
      setIsLoading(false)
      setIsChatbotTyping(false)
    }
  }

  const handleSaveConfiguration = async () => {
    try {
      if (!chatbotConfig) {
        return
      }

      const updateChatbotTemplateDTO = {
        chatbotName,
        chatbotModel: chatbotConfig.chatbotModel || 'gpt-3.5-turbo',
        description: chatbotConfig.description || '',
        chatbotIcon,
        chatbotLanguage,
        chatbotRole,
        chatbotTone,
        welcomeMessage,
        chatbotVoice,
        chatbotGender,

        preConfiguredExercise,
        additionalExercise,
        animation: chatbotAnimation,
        chatbotInputPlaceholder,

        workspaceId: chatbotConfig.workspaceId || '',
      }

      await dispatch(
        updateChatbotTemplate({
          chatbotTemplateId: chatbotConfig.id ?? '',
          updateChatbotTemplateDTO,
        })
      )

      setSnackbarMessage(t('dashboard.chatbot_updated_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
    } catch (error) {
      const errorMessage = handleError(error as AxiosError)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  const handleCloseSnackbar = (_event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') {
      return
    }
    setSnackbarOpen(false)
  }

  const getIconComponent = (iconName: string) => {
    switch (iconName) {
      case 'Chatbot':
        return <TbMessageChatbot size='1.2em' color='black' />
      case 'Robot':
        return <RiRobot2Line size='1.2em' color='black' />
      case 'Person':
        return <IoPersonOutline size='1.2em' color='black' />
      case 'Bulb':
        return <IoBulbOutline size='1.2em' color='black' />
      case 'Book':
        return <PiBookOpenTextLight size='1.2em' color='black' />
      default:
        return <TbMessageChatbot size='1.2em' color='black' />
    }
  }

  const renderMessage = (
    chatItem: { question?: string; response: string | null },
    index: number
  ) => (
    <ListItem key={index} sx={{ alignItems: 'flex-start', flexDirection: 'row' }}>
      {chatbotIcon && (
        <Avatar
          sx={{ width: 45, height: 45, fontSize: '2rem', bgcolor: 'transparent', mr: 2, mt: 2 }}
        >
          {getIconComponent(chatbotIcon)}
        </Avatar>
      )}
      <Box sx={{ flex: 1, maxWidth: '80%', marginRight: 'auto' }}>
        <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
          {chatbotName || 'Chatbot'}
        </Typography>
        <Paper
          sx={{
            py: 1,
            px: 2,
            bgcolor: '#E5E5E5',
            borderRadius: '20px',
            mt: '0px',
            display: 'inline-block',
          }}
        >
          <Typography variant='body1'>{chatItem.response}</Typography>
        </Paper>
      </Box>
    </ListItem>
  )

  const commonButtonStyles = {
    borderRadius: 20,
    textTransform: 'none' as const,
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
  }

  const sendButtonStyles = {
    ...commonButtonStyles,
    height: '55px',
    minWidth: '80px',
    maxWidth: '80px',
  }

  const disabledButtonStyles = {
    borderRadius: 20,
    textTransform: 'none' as const,
    fontSize: '1rem',
    height: '55px',
    minWidth: '80px',
    maxWidth: '80px',
    padding: '6px 24px',
    lineHeight: 1.75,
    backgroundColor: 'lightgrey',
    boxShadow: '0 3px 5px 2px rgba(99, 91, 255, .3)',
    color: 'white',
    cursor: 'not-allowed',
    margin: 1,
  }

  const handleFileUpload = (file: File) => {
    const newFile = { id: Date.now().toString(), fileName: file.name }
    setFiles((prev) => [...prev, newFile])
    setSnackbarMessage(`File "${file.name}" uploaded successfully!`)
    setSnackbarSeverity('success')
    setSnackbarOpen(true)
  }

  const handleDeleteFile = (fileId: string) => {
    setFiles((prev) => prev.filter((f) => f.id !== fileId))
    setSnackbarMessage(`File with ID ${fileId} deleted successfully!`)
    setSnackbarSeverity('success')
    setSnackbarOpen(true)
  }

  const handleDownloadFile = (fileId: string, fileName: string) => {
    setSnackbarMessage(`Downloading "${fileName}" (placeholder)`)
    setSnackbarSeverity('info')
    setSnackbarOpen(true)
  }

  return (
    <>
      <style>
        {`
          .typing-indicator {
            margin-top: -10px;
            display: flex;
            justify-content: center;
            align-items: center;
          }
          .typing-indicator span {
            display: inline-block;
            margin: 0 2px;
            font-size: 60px;
            color: grey;
            line-height: 0;
            animation: bounce 1s infinite;
          }
          .typing-indicator span:nth-child(1) { animation-delay: 0.1s; }
          .typing-indicator span:nth-child(2) { animation-delay: 0.2s; }
          .typing-indicator span:nth-child(3) { animation-delay: 0.3s; }
          @keyframes bounce {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-10px); }
          }
        `}
      </style>

      <Layout>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
          <Tabs value={selectedTab} onChange={handleTabChange}>
            <Tab label='Configuration' value='config' />
            <Tab label='Analytics' value='analytics' />
            <Tab label='Sources' value='sources' />
          </Tabs>
        </Box>

        {selectedTab === 'config' && (
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <Typography variant='h6' gutterBottom sx={{ mt: 2 }}>
                Chatbot Configuration
              </Typography>

              <Box sx={{ mb: 2 }}>
                <TextField
                  fullWidth
                  label='Name'
                  variant='outlined'
                  value={chatbotName}
                  onChange={(e) => setChatbotName(e.target.value)}
                  margin='normal'
                />

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-role-label'>Role</InputLabel>
                  <Select
                    labelId='chatbot-role-label'
                    id='chatbot-role-select'
                    value={chatbotRole}
                    label='Role'
                    onChange={(e) => setChatbotRole(e.target.value)}
                  >
                    <MenuItem value='FAQ'>FAQ</MenuItem>
                    <MenuItem value='Supportive'>Supportive</MenuItem>
                    <MenuItem value='Counseling'>Counseling</MenuItem>
                    <MenuItem value='Self-Help'>Self-Help</MenuItem>
                    <MenuItem value='Undefined'>Undefined</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-language-label'>Language</InputLabel>
                  <Select
                    labelId='chatbot-language-label'
                    id='chatbot-language-select'
                    value={chatbotLanguage}
                    label='Language'
                    onChange={(e) => setChatbotLanguage(e.target.value)}
                  >
                    <MenuItem value='English'>English</MenuItem>
                    <MenuItem value='German'>German</MenuItem>
                    <MenuItem value='Spanish'>Spanish</MenuItem>
                    <MenuItem value='French'>French</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-icon-label'>Chatbot Icon</InputLabel>
                  <Select
                    labelId='chatbot-icon-label'
                    id='chatbot-icon-select'
                    value={chatbotIcon}
                    label='Chatbot Icon'
                    onChange={(e) => setChatbotIcon(e.target.value)}
                  >
                    <MenuItem value='Chatbot'>Chatbot</MenuItem>
                    <MenuItem value='Robot'>Robot</MenuItem>
                    <MenuItem value='Person'>Person</MenuItem>
                    <MenuItem value='Bulb'>Bulb</MenuItem>
                    <MenuItem value='Book'>Book</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-voice-label'>Voice</InputLabel>
                  <Select
                    labelId='chatbot-voice-label'
                    id='chatbot-voice-select'
                    value={chatbotVoice}
                    label='Voice'
                    onChange={(e) => setChatbotVoice(e.target.value)}
                  >
                    <MenuItem value='None'>None</MenuItem>
                    <MenuItem value='Male'>Male Voice</MenuItem>
                    <MenuItem value='Female'>Female Voice</MenuItem>
                    <MenuItem value='Robotic'>Robotic Voice</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-gender-label'>Gender</InputLabel>
                  <Select
                    labelId='chatbot-gender-label'
                    id='chatbot-gender-select'
                    value={chatbotGender}
                    label='Gender'
                    onChange={(e) => setChatbotGender(e.target.value)}
                  >
                    <MenuItem value='Neutral'>Neutral</MenuItem>
                    <MenuItem value='Male'>Male</MenuItem>
                    <MenuItem value='Female'>Female</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-tone-label'>Conversation Tone</InputLabel>
                  <Select
                    labelId='chatbot-tone-label'
                    id='chatbot-tone-select'
                    value={chatbotTone}
                    label='Conversation Tone'
                    onChange={(e) => setChatbotTone(e.target.value)}
                  >
                    <MenuItem value='friendly'>Friendly</MenuItem>
                    <MenuItem value='formal'>Formal</MenuItem>
                    <MenuItem value='informal'>Informal</MenuItem>
                    <MenuItem value='professional'>Professional</MenuItem>
                    <MenuItem value='supportive'>Supportive</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='preconfigured-exercises-label'>
                    Pre-configured Exercise
                  </InputLabel>
                  <Select
                    labelId='preconfigured-exercises-label'
                    id='preconfigured-exercises-select'
                    value={preConfiguredExercise}
                    label='Pre-configured Exercise'
                    onChange={(e) => setPreconfiguredExercise(e.target.value)}
                  >
                    <MenuItem value='Breathing exercise'>Breathing exercise</MenuItem>
                    <MenuItem value='Journaling'>Journaling</MenuItem>
                    <MenuItem value='Relaxation technique'>Relaxation technique</MenuItem>
                    <MenuItem value='Undefined'>Undefined</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='additional-exercises-label'>Additional Exercise</InputLabel>
                  <Select
                    labelId='additional-exercises-label'
                    id='additional-exercises-select'
                    value={additionalExercise}
                    label='Additional Exercise'
                    onChange={(e) => setAdditionalExercise(e.target.value)}
                  >
                    <MenuItem value='Meditation practice'>Meditation practice</MenuItem>
                    <MenuItem value='CBT example'>CBT example</MenuItem>
                    <MenuItem value='Undefined'>Undefined</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-animation-label'>Animation</InputLabel>
                  <Select
                    labelId='chatbot-animation-label'
                    id='chatbot-animation-select'
                    value={chatbotAnimation}
                    label='Animation'
                    onChange={(e) => setChatbotAnimation(e.target.value)}
                  >
                    <MenuItem value='None'>None</MenuItem>
                    <MenuItem value='Simple'>Simple</MenuItem>
                    <MenuItem value='Advanced'>Advanced</MenuItem>
                  </Select>
                </FormControl>

                <TextField
                  fullWidth
                  label='Welcome Message'
                  variant='outlined'
                  value={welcomeMessage}
                  onChange={(e) => setWelcomeMessage(e.target.value)}
                  margin='normal'
                />

                <TextField
                  fullWidth
                  label='Chatbot Input Placeholder'
                  variant='outlined'
                  value={chatbotInputPlaceholder}
                  onChange={(e) => setChatbotInputPlaceholder(e.target.value)}
                  margin='normal'
                />

                <Box
                  sx={{
                    mt: 1,
                    ml: -1,
                    display: 'flex',
                    flexDirection: 'row',
                    justifyContent: 'left',
                  }}
                >
                  <Button onClick={handleSaveConfiguration} sx={commonButtonStyles}>
                    Save
                  </Button>
                </Box>
              </Box>
            </Grid>

            <Grid item xs={12} md={6}>
              <Paper
                elevation={3}
                sx={{
                  height: '85vh',
                  display: 'flex',
                  flexDirection: 'column',
                  marginLeft: '10px',
                }}
              >
                <Box
                  sx={{
                    display: 'flex',
                    alignItems: 'center',
                    fontSize: '2rem',
                    p: 2,
                    borderBottom: '1px solid #e0e0e0',
                  }}
                >
                  {chatbotIcon && (
                    <Avatar
                      sx={{
                        width: 45,
                        height: 45,
                        fontSize: '2rem',
                        bgcolor: 'transparent',
                        mr: 2,
                      }}
                    >
                      {getIconComponent(chatbotIcon)}
                    </Avatar>
                  )}
                  <Typography variant='h4'>{chatbotName || 'Chatbot'} Simulation</Typography>
                </Box>

                <List ref={chatListRef} sx={{ overflow: 'auto', flexGrow: 1 }}>
                  {chat.map((chatItem, index) => (
                    <React.Fragment key={index}>
                      {chatItem.question && (
                        <ListItem sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                          <Box sx={{ maxWidth: '80%', marginLeft: 'auto' }}>
                            <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                              You
                            </Typography>
                            <Box
                              sx={{
                                py: 1,
                                px: 2,
                                bgcolor: 'primary.main',
                                color: 'white',
                                borderRadius: '20px',
                              }}
                            >
                              <Typography variant='body1'>{chatItem.question}</Typography>
                            </Box>
                          </Box>
                        </ListItem>
                      )}

                      {chatItem.response && renderMessage(chatItem, index)}

                      {isChatbotTyping && index === chat.length - 1 && (
                        <ListItem
                          sx={{
                            display: 'flex',
                            justifyContent: 'flex-start',
                            alignItems: 'flex-start',
                          }}
                        >
                          {chatbotIcon && (
                            <Avatar
                              sx={{
                                width: 45,
                                height: 45,
                                fontSize: '2rem',
                                bgcolor: 'transparent',
                                mr: 2,
                                mt: 2,
                              }}
                            >
                              {getIconComponent(chatbotIcon)}
                            </Avatar>
                          )}
                          <Box sx={{ maxWidth: '80%' }}>
                            <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                              {chatbotName || 'Chatbot'}
                            </Typography>
                            <Box sx={{ py: 1, px: 2, bgcolor: '#E5E5E5', borderRadius: '20px' }}>
                              <div className='typing-indicator'>
                                <span>.</span>
                                <span>.</span>
                                <span>.</span>
                              </div>
                            </Box>
                          </Box>
                        </ListItem>
                      )}
                    </React.Fragment>
                  ))}
                </List>

                <Box
                  component='form'
                  onSubmit={handleSubmit}
                  sx={{ display: 'flex', alignItems: 'center', gap: 1, mt: 2, ml: 2, mb: 1, mr: 1 }}
                >
                  <TextField
                    fullWidth
                    label={chatbotInputPlaceholder || 'Type a message...'}
                    variant='outlined'
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    disabled={isLoading}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        borderRadius: '20px',
                      },
                    }}
                  />
                  <Button
                    type='submit'
                    variant='contained'
                    sx={question ? sendButtonStyles : disabledButtonStyles}
                    disabled={!question || isLoading}
                  >
                    {isLoading ? <CircularProgress size={24} /> : <SendIcon />}
                  </Button>
                </Box>
              </Paper>
            </Grid>
          </Grid>
        )}

        {selectedTab === 'analytics' && (
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <Typography variant='h6' sx={{ mt: 2 }}>
                Analytics
              </Typography>
              <Typography variant='subtitle1' gutterBottom>
                Total Chats (Threads): {threads.length}
              </Typography>
              {threads.length > 0 ? (
                threads.map((t) => (
                  <Typography key={t.threadId}>Thread ID: {t.threadId}</Typography>
                ))
              ) : (
                <Typography>No threads found.</Typography>
              )}
            </Grid>
          </Grid>
        )}

        {selectedTab === 'sources' && (
          <Box sx={{ mt: 3 }}>
            <Typography variant='h6' gutterBottom>
              Upload & Manage Documents
            </Typography>
            <TableContainer sx={{ marginTop: '10px' }} component={Paper}>
              <Table aria-label='files-table'>
                <TableHead>
                  <TableRow>
                    <TableCell>
                      <Box sx={{ display: 'flex', gap: 2, alignItems: 'center' }}>
                        File name
                        <FileUpload onUpload={handleFileUpload} />
                      </Box>
                    </TableCell>
                    <TableCell align='right'>Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {files.map((file) => (
                    <TableRow key={file.id}>
                      <TableCell
                        sx={{
                          maxWidth: 400,
                          whiteSpace: 'nowrap',
                          overflow: 'hidden',
                          textOverflow: 'ellipsis',
                        }}
                      >
                        {file.fileName}
                      </TableCell>
                      <TableCell align='right'>
                        <IconButton
                          aria-label='download'
                          onClick={() => handleDownloadFile(file.id, file.fileName)}
                        >
                          <DownloadIcon />
                        </IconButton>
                        <IconButton aria-label='delete' onClick={() => handleDeleteFile(file.id)}>
                          <DeleteIcon />
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        )}

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
    </>
  )
}

export default ChatBotTemplateEdit
