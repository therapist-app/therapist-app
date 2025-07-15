import SendIcon from '@mui/icons-material/Send'
import {
  Alert,
  Avatar,
  Box,
  Button,
  CircularProgress,
  FormControl,
  Grid,
  InputLabel,
  List,
  ListItem,
  MenuItem,
  Paper,
  Select,
  Snackbar,
  Tab,
  Tabs,
  TextField,
  Typography,
} from '@mui/material'
import { AxiosError } from 'axios'
import React, { ReactElement, ReactNode, useEffect, useRef, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { IoBulbOutline, IoPersonOutline } from 'react-icons/io5'
import { PiBookOpenTextLight } from 'react-icons/pi'
import { RiRobot2Line } from 'react-icons/ri'
import { TbMessageChatbot } from 'react-icons/tb'
import { useSelector } from 'react-redux'
import { useLocation, useParams } from 'react-router-dom'

import {
  ChatbotTemplateOutputDTO,
  ChatCompletionWithConfigRequestDTO,
  ChatMessageDTO,
  ChatMessageDTOChatRoleEnum,
} from '../../api'
import FilesTable from '../../generalComponents/FilesTable'
import Layout from '../../generalComponents/Layout'
import {
  createDocumentForTemplate,
  deleteDocumentOfTemplate,
  getAllDocumentsOfTemplate,
} from '../../store/chatbotTemplateDocumentSlice'
import { updateChatbotTemplate } from '../../store/chatbotTemplateSlice'
import { RootState } from '../../store/store'
import { chatApi, chatbotTemplateApi, chatbotTemplateDocumentApi } from '../../utils/api'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'

const ChatBotTemplateEdit: React.FC = () => {
  const { t } = useTranslation()
  const { chatbotTemplateId } = useParams<{ chatbotTemplateId: string }>()
  const dispatch = useAppDispatch()

  const { state } = useLocation() as { state?: { chatbotConfig?: ChatbotTemplateOutputDTO } }

  const [chatbotConfig, setChatbotConfig] = useState<ChatbotTemplateOutputDTO | null>(null)

  const [selectedTab, setSelectedTab] = useState<'config' | 'sources'>('config')

  const [chat, setChat] = useState<Array<{ question?: string; response: ReactNode | null }>>([])

  const [isLoading, setIsLoading] = useState(false)
  const [isChatbotTyping, setIsChatbotTyping] = useState(false)
  const [isStreaming, setIsStreaming] = useState(false)
  const chatListRef = useRef<HTMLUListElement>(null)

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [snackbarSeverity, setSnackbarSeverity] = useState<
    'info' | 'success' | 'error' | 'warning'
  >('info')

  const [threads, setThreads] = useState<Array<{ threadId: string }>>([])
  const [question, setQuestion] = useState('')
  const templateDocuments = useSelector(
    (s: RootState) => s.chatbotTemplateDocument.allDocumentsOfTemplate
  )

  const [chatbotName, setChatbotName] = useState('')
  const [chatbotRole, setChatbotRole] = useState('')
  const [chatbotLanguage, setChatbotLanguage] = useState('')
  const [chatbotIcon, setChatbotIcon] = useState('')
  const [chatbotTone, setChatbotTone] = useState('')
  const [preConfiguredExercise, setPreconfiguredExercise] = useState('')
  const [additionalExercise, setAdditionalExercise] = useState('')
  const [welcomeMessage, setWelcomeMessage] = useState('')

  type ChatCompletionWithTemplate = ChatCompletionWithConfigRequestDTO & {
    templateId: string
  }

  useEffect(() => {
    if (chatbotConfig) {
      return
    }

    const id = chatbotTemplateId ?? sessionStorage.getItem('chatbotTemplateId')
    if (!id) {
      return
    }
    ;(async (): Promise<void> => {
      try {
        const { data } = await chatbotTemplateApi.getTemplate(id)
        setChatbotConfig(data)
      } catch (e) {
        console.error('Failed to load template', e)
      }
    })()
  }, [chatbotConfig, chatbotTemplateId])

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
      setChatbotTone(chatbotConfig.chatbotTone || '')

      setPreconfiguredExercise(chatbotConfig.preConfiguredExercise || '')
      setAdditionalExercise(chatbotConfig.additionalExercise || '')
      setWelcomeMessage(chatbotConfig.welcomeMessage || '')

      if (chatbotConfig.welcomeMessage) {
        setChat([{ response: chatbotConfig.welcomeMessage }])
      }
    }
  }, [chatbotConfig])

  useEffect(() => {
    if (chatListRef.current) {
      chatListRef.current.scrollTop = chatListRef.current.scrollHeight
    }
  }, [chat])

  useEffect(() => {
    if (chatbotConfig?.id && selectedTab === 'sources') {
      dispatch(getAllDocumentsOfTemplate(chatbotConfig.id))
    }
  }, [dispatch, chatbotConfig?.id, selectedTab])

  const handleTabChange = (
    _event: React.SyntheticEvent,
    newValue: 'config' | 'sources'
  ): void => {
    setSelectedTab(newValue)
  }

  const formatResponse = (text: string): ReactNode => {
    const lines = text.split('\n')
    const output: ReactNode[] = []

    let listBuffer: ReactNode[] = []
    let listType: 'ul' | 'ol' | null = null

    const flushList = (): void => {
      if (!listType) {
        return
      }
      const Wrapper = (listType === 'ul' ? 'ul' : 'ol') as React.ElementType
      output.push(
        <Wrapper key={output.length} style={{ margin: '0.75em 0' }}>
          {listBuffer}
        </Wrapper>
      )
      listBuffer = []
      listType = null
    }

    const parseInline = (str: string): ReactNode[] => {
      const nodes: ReactNode[] = []
      const boldRe = /\*\*(.+?)\*\*/g
      let lastIndex = 0
      let match: RegExpExecArray | null

      while ((match = boldRe.exec(str)) !== null) {
        if (match.index > lastIndex) {
          nodes.push(str.slice(lastIndex, match.index))
        }
        nodes.push(<strong key={match.index}>{match[1]}</strong>)
        lastIndex = match.index + match[0].length
      }
      if (lastIndex < str.length) {
        nodes.push(str.slice(lastIndex))
      }
      return nodes
    }

    lines.forEach((raw, idx) => {
      const line = raw.trim()

      if (!line) {
        flushList()
        return
      }

      const h = line.match(/^(#{1,6})\s+(.*)$/)
      if (h) {
        flushList()
        const level = h[1].length
        const Tag = `h${level}` as React.ElementType
        output.push(
          <Tag key={idx} style={{ margin: '1em 0 0.5em' }}>
            {parseInline(h[2])}
          </Tag>
        )
        return
      }

      const ol = line.match(/^\d+\.\s+(.*)$/)
      if (ol) {
        if (listType !== 'ol') {
          flushList()
          listType = 'ol'
        }
        listBuffer.push(
          <li key={idx} style={{ margin: '0.4em 0' }}>
            {parseInline(ol[1])}
          </li>
        )
        return
      }

      const ul = line.match(/^[-*]\s+(.*)$/)
      if (ul) {
        if (listType !== 'ul') {
          flushList()
          listType = 'ul'
        }
        listBuffer.push(
          <li key={idx} style={{ margin: '0.4em 0' }}>
            {parseInline(ul[1])}
          </li>
        )
        return
      }

      flushList()
      output.push(
        <p key={idx} style={{ margin: '0.75em 0', lineHeight: 1.5 }}>
          {parseInline(line)}
        </p>
      )
    })

    flushList()

    return <div>{output}</div>
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()
    if (!chatbotConfig?.id) {
      setSnackbarMessage('Template not loaded yet.')
      setSnackbarSeverity('warning')
      setSnackbarOpen(true)
      return
    }
    const userPrompt = question.trim()
    if (!userPrompt) {
      return
    }

    setChat((prev) => [...prev, { question: userPrompt, response: null }])
    setQuestion('')
    setIsChatbotTyping(true)
    setIsStreaming(false)
    setIsLoading(true)

    try {
      const history: ChatMessageDTO[] = chat.flatMap((msg) => {
        const out: ChatMessageDTO[] = []
        if (msg.question) {
          out.push({ chatRole: ChatMessageDTOChatRoleEnum.User, content: msg.question })
        }
        if (typeof msg.response === 'string') {
          out.push({ chatRole: ChatMessageDTOChatRoleEnum.Assistant, content: msg.response })
        }
        return out
      })

      const payload: ChatCompletionWithTemplate = {
        templateId: chatbotConfig?.id ?? '',
        config: {
          chatbotRole: chatbotRole,
          chatbotTone: chatbotTone,
          chatbotLanguage: chatbotLanguage,
          preConfiguredExercise: preConfiguredExercise,
          additionalExercise: additionalExercise,
          welcomeMessage: welcomeMessage,
        },
        history: history,
        message: userPrompt,
      }

      const fullText = (await chatApi.chatWithConfig(payload)).data.content ?? ''

      setIsChatbotTyping(false)

      const idx = chat.length
      setChat((prev) => {
        const copy = [...prev]
        copy[idx] = { ...copy[idx], response: '' }
        return copy
      })

      setIsStreaming(true)
      const CHUNK = 5
      let pos = 0
      const total = fullText.length

      const typeChunk = (): void => {
        pos = Math.min(pos + CHUNK, total)
        const slice = fullText.slice(0, pos)
        const formatted = formatResponse(slice)
        setChat((prev) => {
          const copy = [...prev]
          copy[idx] = { ...copy[idx], response: formatted }
          return copy
        })

        if (pos < total) {
          setTimeout(typeChunk, 0)
        } else {
          setIsStreaming(false)
          setIsLoading(false)
        }
      }

      typeChunk()
    } catch (err) {
      console.error(err)
      setSnackbarMessage('Chat service unavailable.')
      setSnackbarSeverity('error')
      setSnackbarOpen(true)

      setChat((prev) => {
        const copy = [...prev]
        copy[copy.length - 1] = {
          ...copy[copy.length - 1],
          response: 'An error occurred.',
        }
        return copy
      })
      setIsChatbotTyping(false)
      setIsStreaming(false)
      setIsLoading(false)
    }
  }

  const handleSaveConfiguration = async (): Promise<void> => {
    try {
      if (!chatbotConfig) {
        return
      }

      const updateChatbotTemplateDTO = {
        chatbotName: chatbotName,
        chatbotModel: chatbotConfig.chatbotModel || 'gpt-3.5-turbo',
        description: chatbotConfig.description || '',
        chatbotIcon: chatbotIcon,
        chatbotLanguage: chatbotLanguage,
        chatbotRole: chatbotRole,
        chatbotTone: chatbotTone,
        welcomeMessage: welcomeMessage,
        preConfiguredExercise: preConfiguredExercise,
        additionalExercise: additionalExercise,
      }

      await dispatch(
        updateChatbotTemplate({
          chatbotTemplateId: chatbotConfig.id ?? '',
          updateChatbotTemplateDTO: updateChatbotTemplateDTO,
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

  const handleCloseSnackbar = (_event?: React.SyntheticEvent | Event, reason?: string): void => {
    if (reason === 'clickaway') {
      return
    }
    setSnackbarOpen(false)
  }

  const getIconComponent = (iconName: string): ReactElement => {
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
    chatItem: { question?: string; response: ReactNode | null },
    index: number
  ): ReactElement => (
    <ListItem key={index} sx={{ alignItems: 'flex-start', flexDirection: 'row' }}>
      {chatbotIcon && <Avatar /* … */>{getIconComponent(chatbotIcon)}</Avatar>}
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

  const handleFileUpload = async (file: File): Promise<void> => {
    if (!chatbotConfig?.id) {
      return
    }

    await dispatch(createDocumentForTemplate({ file: file, templateId: chatbotConfig.id }))

    dispatch(getAllDocumentsOfTemplate(chatbotConfig.id))
  }

  const handleDeleteFile = async (fileId: string): Promise<void> => {
    if (!chatbotConfig?.id) {
      return
    }

    await dispatch(deleteDocumentOfTemplate(fileId))

    dispatch(getAllDocumentsOfTemplate(chatbotConfig.id))
  }

  const downloadFile = async (fileId: string): Promise<string> => {
    const { data } = await chatbotTemplateDocumentApi.downloadChatbotTemplateDocument(fileId, {
      responseType: 'blob',
    })
    return URL.createObjectURL(data)
  }

  return (
    <>
      <style>
        {`
          .typing-indicator {
            margin-top: -30px;
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

                <TextField
                  fullWidth
                  label='Welcome Message'
                  variant='outlined'
                  value={welcomeMessage}
                  onChange={(e) => setWelcomeMessage(e.target.value)}
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
                  height: '78vh',
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

                      {isChatbotTyping && !isStreaming && index === chat.length - 1 && (
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
                            <Box
                              sx={{
                                px: 2,
                                bgcolor: '#E5E5E5',
                                borderRadius: '20px',
                                minHeight: '40px',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                              }}
                            >
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
                    label='Type a message...'
                    variant='outlined'
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        borderRadius: '20px',
                      },
                    }}
                  />
                  <Button
                    type='submit'
                    variant='contained'
                    sx={!question || isChatbotTyping ? disabledButtonStyles : sendButtonStyles}
                    disabled={!question || isChatbotTyping || isLoading}
                  >
                    {isChatbotTyping ? <CircularProgress size={24} /> : <SendIcon />}
                  </Button>
                </Box>
              </Paper>
            </Grid>
          </Grid>
        )}

        {selectedTab === 'sources' && (
          <Box sx={{ mt: 3 }}>
            <FilesTable
              title='Template Files'
              allDocuments={templateDocuments}
              handleFileUpload={handleFileUpload}
              handleDeleteFile={handleDeleteFile}
              downloadFile={downloadFile}
            />
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
