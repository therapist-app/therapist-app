import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline'
import SendIcon from '@mui/icons-material/Send'
import {
  Avatar,
  Box,
  Button,
  CircularProgress,
  FormControl,
  FormControlLabel,
  Grid,
  IconButton,
  InputLabel,
  List,
  ListItem,
  MenuItem,
  Paper,
  Select,
  Switch,
  Tab,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Tabs,
  TextField,
  Typography,
} from '@mui/material'
import { AxiosError } from 'axios'
import React, { ReactElement, ReactNode, useCallback, useEffect, useRef, useState } from 'react'
import { useDropzone } from 'react-dropzone'
import { useTranslation } from 'react-i18next'
import { TbMessageChatbot } from 'react-icons/tb'
import { useSelector } from 'react-redux'
import { useLocation, useParams } from 'react-router-dom'

import {
  ChatbotTemplateOutputDTO,
  ChatCompletionWithConfigRequestDTO,
  ChatMessageDTO,
  ChatMessageDTOChatRoleEnum,
} from '../../api'
import FileDownload from '../../generalComponents/FileDownload'
import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import DeleteIcon from '../../icons/DeleteIcon'
import {
  createDocumentForTemplate,
  deleteDocumentOfTemplate,
  getAllDocumentsOfTemplate,
} from '../../store/chatbotTemplateDocumentSlice'
import { updateChatbotTemplate } from '../../store/chatbotTemplateSlice'
import { RootState } from '../../store/store'
import { commonButtonStyles, disabledButtonStyles } from '../../styles/buttonStyles'
import { chatApi, chatbotTemplateApi, chatbotTemplateDocumentApi } from '../../utils/api'
import { formatResponse } from '../../utils/formatResponse'
import { useAppDispatch } from '../../utils/hooks'
import { getCurrentLanguage } from '../../utils/languageUtil'

const ChatBotTemplateEdit: React.FC = () => {
  const { t } = useTranslation()
  const { chatbotTemplateId } = useParams<{ chatbotTemplateId: string }>()
  const dispatch = useAppDispatch()
  const { notifyError, notifySuccess, notifyWarning } = useNotify()

  const { state } = useLocation() as {
    state?: { chatbotConfig?: ChatbotTemplateOutputDTO; patientId?: string }
  }

  const [chatbotConfig, setChatbotConfig] = useState<ChatbotTemplateOutputDTO | null>(null)

  const [selectedTab, setSelectedTab] = useState<'config' | 'sources'>('config')

  const [chat, setChat] = useState<Array<{ question?: string; response: ReactNode | null }>>([])

  const [isLoading, setIsLoading] = useState(false)
  const [isChatbotTyping, setIsChatbotTyping] = useState(false)
  const [isStreaming, setIsStreaming] = useState(false)
  const [isActive, setIsActive] = useState<boolean>(false)
  const [isOnlyTemplateForClient, setIsOnlyTemplateForClient] = useState(false)
  const [isUploadingFile, setIsUploadingFile] = useState(false)
  const isPatientTemplate = Boolean(
    chatbotConfig?.patientId && chatbotConfig.patientId.trim() !== ''
  )

  const chatListRef = useRef<HTMLUListElement>(null)

  const [question, setQuestion] = useState('')
  const templateDocuments = useSelector(
    (s: RootState) => s.chatbotTemplateDocument.allDocumentsOfTemplate
  )

  const [chatbotName, setChatbotName] = useState('')
  const [chatbotRole, setChatbotRole] = useState('')
  const [chatbotIcon, setChatbotIcon] = useState('')
  const [chatbotTone, setChatbotTone] = useState('')
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
        notifyError(typeof e === 'string' ? e : 'An unknown error occurred')
      }
    })()
  }, [chatbotConfig, chatbotTemplateId, notifyError])

  useEffect(() => {
    if (state?.chatbotConfig) {
      setChatbotConfig(state.chatbotConfig)
    }
  }, [state])

  useEffect(() => {
    if (!chatbotConfig) {
      return
    }

    setChatbotName(chatbotConfig.chatbotName || '')
    setChatbotRole(chatbotConfig.chatbotRole || '')
    setChatbotIcon(chatbotConfig.chatbotIcon || '')
    setChatbotTone(chatbotConfig.chatbotTone || '')
    setWelcomeMessage(chatbotConfig.welcomeMessage || '')

    setIsActive(isPatientTemplate ? (chatbotConfig.isActive ?? false) : false)

    if (chatbotConfig.welcomeMessage) {
      setChat([{ response: chatbotConfig.welcomeMessage }])
    }
  }, [chatbotConfig, isPatientTemplate])

  const listEndRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    listEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [chat, isChatbotTyping, isStreaming])

  useEffect(() => {
    if (chatbotConfig?.id && selectedTab === 'sources') {
      dispatch(getAllDocumentsOfTemplate(chatbotConfig.id)).catch((err: AxiosError) =>
        notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      )
    }
  }, [dispatch, chatbotConfig?.id, selectedTab, notifyError])

  useEffect((): void => {
    const load = async (): Promise<void> => {
      if (!chatbotConfig?.patientId) {
        return
      }
      try {
        const { data } = await chatbotTemplateApi.getTemplatesForPatient(chatbotConfig.patientId)
        setIsOnlyTemplateForClient(data.length === 1)
      } catch (e) {
        notifyError(typeof e === 'string' ? e : 'An unknown error occurred')
      }
    }
    load()
  }, [chatbotConfig?.patientId, notifyError])

  const handleActiveChange = (next: boolean): void => {
    if (isOnlyTemplateForClient && isActive && !next) {
      notifyWarning(
        t('chatbot.cannot_deactivate_last_template') || 'Cannot deactivate last template'
      )
      return
    }
    setIsActive(next)
  }

  const handleTabChange = (_event: React.SyntheticEvent, newValue: 'config' | 'sources'): void => {
    setSelectedTab(newValue)
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()
    if (!chatbotConfig?.id) {
      notifyWarning(t('chatbot.template_not_loaded') || 'Template not loaded yet.')
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
        templateId: chatbotConfig.id,
        config: {
          chatbotRole: chatbotRole,
          chatbotTone: chatbotTone,
          welcomeMessage: welcomeMessage,
        },
        history: history,
        message: userPrompt,
        language: getCurrentLanguage(),
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
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')

      setChat((prev) => {
        const copy = [...prev]
        copy[copy.length - 1] = {
          ...copy[copy.length - 1],
          response: t('chatbot.error_occurred') || 'An error occurred.',
        }
        return copy
      })
      setIsChatbotTyping(false)
      setIsStreaming(false)
      setIsLoading(false)
    }
  }

  const handleSaveConfiguration = async (): Promise<void> => {
    if (!chatbotConfig) {
      return
    }

    try {
      const dto = {
        chatbotName: chatbotName,
        chatbotRole: chatbotRole,
        chatbotTone: chatbotTone,
        welcomeMessage: welcomeMessage,
        ...(isPatientTemplate ? { isActive: isActive } : {}),
      }

      await dispatch(
        updateChatbotTemplate({
          chatbotTemplateId: chatbotConfig.id ?? '',
          updateChatbotTemplateDTO: dto,
        })
      ).unwrap()

      notifySuccess(t('chatbot.chatbot_updated_success'))
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const getIconComponent = (): ReactElement => <TbMessageChatbot size='1.2em' color='black' />

  const renderMessage = (
    chatItem: { question?: string; response: ReactNode | null },
    index: number
  ): ReactElement => (
    <ListItem key={index} sx={{ display: 'flex', alignItems: 'flex-start', flexDirection: 'row' }}>
      {chatbotIcon && (
        <Avatar
          sx={{
            width: 45,
            height: 45,
            fontSize: '2rem',
            bgcolor: 'transparent',
            mr: 2,
            mt: 2.5,
          }}
        >
          {getIconComponent()}
        </Avatar>
      )}
      <Box sx={{ flex: 1, maxWidth: '80%' }}>
        <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
          {chatbotName || 'Chatbot'}
        </Typography>
        <Paper
          sx={{
            px: 2,
            bgcolor: '#E5E5E5',
            borderRadius: '20px',
            display: 'inline-flex',
            alignItems: 'center',
            minHeight: '40px',
          }}
        >
          <Typography variant='body1'>{chatItem.response}</Typography>
        </Paper>
      </Box>
    </ListItem>
  )

  const sendButtonStyles = {
    ...commonButtonStyles,
    height: '55px',
    minWidth: '80px',
    maxWidth: '80px',
  } as const

  const smallDisabledButtonStyles = {
    ...disabledButtonStyles,
    height: '55px',
    minWidth: '80px',
    maxWidth: '80px',
  } as const

  const handleFileUpload = useCallback(
    async (file: File): Promise<void> => {
      if (!chatbotConfig?.id) {
        return
      }

      setIsUploadingFile(true)

      try {
        await dispatch(
          createDocumentForTemplate({ file: file, templateId: chatbotConfig.id })
        ).unwrap()

        dispatch(getAllDocumentsOfTemplate(chatbotConfig.id))
        notifySuccess(t('files.file_uploaded_successfully'))
      } catch (err) {
        notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      } finally {
        setIsUploadingFile(false)
      }
    },
    [chatbotConfig?.id, dispatch, notifyError, notifySuccess, t]
  )

  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      acceptedFiles.forEach(handleFileUpload)
    },
    [handleFileUpload]
  )

  const { getRootProps, getInputProps } = useDropzone({ onDrop: onDrop })

  const dropzoneStyles: React.CSSProperties = {
    border: '2px dashed #B4B4B4',
    borderRadius: '12px',
    padding: '32px',
    textAlign: 'center',
    cursor: 'pointer',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    height: '200px',
    backgroundColor: '#FFFFFF',
    color: '#5B5B5B',
  }

  const handleDeleteFile = async (fileId: string): Promise<void> => {
    if (!chatbotConfig?.id) {
      return
    }
    try {
      await dispatch(deleteDocumentOfTemplate(fileId)).unwrap()
      dispatch(getAllDocumentsOfTemplate(chatbotConfig.id))
      notifySuccess(t('files.file_deleted_successfully'))
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  const downloadFile = async (fileId: string): Promise<string> => {
    try {
      const { data } = await chatbotTemplateDocumentApi.downloadChatbotTemplateDocument(fileId, {
        responseType: 'blob',
      })
      return URL.createObjectURL(data)
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      throw err
    }
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
            <Tab label={t('chatbot.configuration')} value='config' />
            <Tab label={t('chatbot.sources')} value='sources' />
          </Tabs>
        </Box>

        {selectedTab === 'config' && (
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <Typography variant='h6' gutterBottom sx={{ mt: 2 }}>
                {t('chatbot.chatbot_configuration')}
              </Typography>

              <Box sx={{ mb: 2 }}>
                <TextField
                  fullWidth
                  label={t('chatbot.name')}
                  variant='outlined'
                  value={chatbotName}
                  onChange={(e) => setChatbotName(e.target.value)}
                  margin='normal'
                />

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-role-label'>{t('chatbot.role')}</InputLabel>
                  <Select
                    labelId='chatbot-role-label'
                    id='chatbot-role-select'
                    value={chatbotRole}
                    label='Role'
                    onChange={(e) => setChatbotRole(e.target.value)}
                  >
                    <MenuItem value='FAQ'>{t('chatbot.faq')}</MenuItem>
                    <MenuItem value='Supportive'>{t('chatbot.supportive')}</MenuItem>
                    <MenuItem value='Counseling'>{t('chatbot.counseling')}</MenuItem>
                    <MenuItem value='Self-Help'>{t('chatbot.self_help')}</MenuItem>
                    <MenuItem value='Undefined'>{t('chatbot.undefined')}</MenuItem>
                  </Select>
                </FormControl>

                <FormControl fullWidth margin='normal'>
                  <InputLabel id='chatbot-tone-label'>{t('chatbot.conversation_tone')}</InputLabel>
                  <Select
                    labelId='chatbot-tone-label'
                    id='chatbot-tone-select'
                    value={chatbotTone}
                    label={t('chatbot.conversation_tone')}
                    onChange={(e) => setChatbotTone(e.target.value)}
                  >
                    <MenuItem value='friendly'>{t('chatbot.friendly')}</MenuItem>
                    <MenuItem value='formal'>{t('chatbot.formal')}</MenuItem>
                    <MenuItem value='informal'>{t('chatbot.casual')}</MenuItem>
                    <MenuItem value='professional'>{t('chatbot.professional')}</MenuItem>
                    <MenuItem value='supportive'>{t('chatbot.supportive')}</MenuItem>
                  </Select>
                </FormControl>

                <TextField
                  fullWidth
                  label={t('chatbot.welcome_message')}
                  variant='outlined'
                  value={welcomeMessage}
                  onChange={(e) => setWelcomeMessage(e.target.value)}
                  margin='normal'
                />

                {isPatientTemplate && (
                  <FormControlLabel
                    control={
                      <Switch
                        checked={isActive}
                        onChange={(e) => handleActiveChange(e.target.checked)}
                        color='success'
                        disabled={isOnlyTemplateForClient && isActive}
                      />
                    }
                    label={t('chatbot.active_visiable_to_client')}
                  />
                )}

                <Box sx={{ mt: 1, ml: -1, display: 'flex', justifyContent: 'left' }}>
                  <Button onClick={handleSaveConfiguration} sx={commonButtonStyles}>
                    {t('chatbot.save')}
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
                      {getIconComponent()}
                    </Avatar>
                  )}
                  <Typography variant='h4'>
                    {chatbotName || 'Chatbot'} {t('chatbot.simulation')}
                  </Typography>
                </Box>

                <List ref={chatListRef} sx={{ overflow: 'auto', flexGrow: 1 }}>
                  {chat.map((chatItem, index) => (
                    <React.Fragment key={index}>
                      {chatItem.question && (
                        <ListItem sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                          <Box sx={{ maxWidth: '80%', marginLeft: 'auto' }}>
                            <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                              {t('chatbot.you')}
                            </Typography>
                            <Paper
                              sx={{
                                py: 1,
                                px: 2,
                                bgcolor: 'primary.main',
                                color: 'white',
                                borderRadius: '20px',
                              }}
                            >
                              <Typography variant='body1'>{chatItem.question}</Typography>
                            </Paper>
                          </Box>
                        </ListItem>
                      )}

                      {chatItem.response && renderMessage(chatItem, index)}

                      {isChatbotTyping && !isStreaming && index === chat.length - 1 && (
                        <ListItem sx={{ display: 'flex', alignItems: 'flex-start' }}>
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
                              {getIconComponent()}
                            </Avatar>
                          )}
                          <Box sx={{ maxWidth: '80%' }}>
                            <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                              {chatbotName || 'Chatbot'}
                            </Typography>
                            <Paper
                              sx={{
                                px: 1.5,
                                bgcolor: '#E5E5E5',
                                borderRadius: '20px',
                                minHeight: '40px',
                                display: 'flex',
                                alignItems: 'center',
                              }}
                            >
                              <div className='typing-indicator'>
                                <span>.</span>
                                <span>.</span>
                                <span>.</span>
                              </div>
                            </Paper>
                          </Box>
                        </ListItem>
                      )}
                    </React.Fragment>
                  ))}
                  <div ref={listEndRef} />
                </List>

                <Box
                  component='form'
                  onSubmit={handleSubmit}
                  sx={{ display: 'flex', alignItems: 'center', gap: 1, mt: 2, ml: 2, mb: 1, mr: 1 }}
                >
                  <TextField
                    fullWidth
                    label={t('chatbot.type_your_message')}
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
                    sx={
                      !question || isChatbotTyping || isStreaming
                        ? smallDisabledButtonStyles
                        : sendButtonStyles
                    }
                    disabled={!question || isChatbotTyping || isStreaming || isLoading}
                  >
                    <SendIcon />
                  </Button>
                </Box>
              </Paper>
            </Grid>
          </Grid>
        )}
        {selectedTab === 'sources' && (
          <Box sx={{ mt: 3 }}>
            <Typography variant='h6' sx={{ mb: 2 }}>
              Chatbot Documents
            </Typography>
            <Box sx={{ display: 'flex', justifyContent: 'center', mb: 3 }}>
              <Paper elevation={0} sx={{ width: 400 }}>
                <div {...getRootProps()} style={dropzoneStyles}>
                  <input
                    {...getInputProps({
                      accept: '.pdf,.doc,.docx,.txt',
                    })}
                  />
                  {isUploadingFile ? (
                    <CircularProgress />
                  ) : (
                    <>
                      <AddCircleOutlineIcon sx={{ fontSize: 50, color: '#635BFF' }} />
                      <Typography variant='subtitle1' sx={{ mt: 1 }}>
                        Click to upload a file or drag and drop it here
                      </Typography>
                      <Typography variant='caption' display='block' sx={{ mt: 1 }}>
                        Up&nbsp;to&nbsp;512&nbsp;MB – PDF, DOC, DOCX, TXT
                      </Typography>
                    </>
                  )}
                </div>
              </Paper>
            </Box>

            <TableContainer component={Paper} elevation={3} sx={{ boxShadow: 1 }}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>File name</TableCell>
                    <TableCell align='right'>Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {templateDocuments.length ? (
                    templateDocuments.map((doc) => (
                      <TableRow key={doc.id}>
                        <TableCell
                          sx={{
                            maxWidth: 400,
                            whiteSpace: 'nowrap',
                            overflow: 'hidden',
                            textOverflow: 'ellipsis',
                          }}
                        >
                          {doc.fileName}
                        </TableCell>
                        <TableCell align='right'>
                          <FileDownload
                            download={() => downloadFile(doc.id ?? '')}
                            fileName={doc.fileName ?? ''}
                          />
                          <IconButton
                            aria-label='delete'
                            onClick={() => handleDeleteFile(doc.id ?? '')}
                          >
                            <DeleteIcon />
                          </IconButton>
                        </TableCell>
                      </TableRow>
                    ))
                  ) : (
                    <TableRow>
                      <TableCell colSpan={2}>No files uploaded yet.</TableCell>
                    </TableRow>
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        )}
      </Layout>
    </>
  )
}

export default ChatBotTemplateEdit
