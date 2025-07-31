import AssistantIcon from '@mui/icons-material/Assistant'
import CloseIcon from '@mui/icons-material/Close'
import { Avatar, Box, IconButton, List, ListItem, Paper, Tooltip, Typography } from '@mui/material'
import { ReactElement, useEffect, useLayoutEffect, useRef } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useLocation, useNavigate, useParams } from 'react-router-dom'

import AssistantImage from '../../../public/AssistantImage.png'
import { ChatMessageDTOChatRoleEnum } from '../../api'
import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { useTypewriter } from '../../hooks/useTypewriter'
import { RootState } from '../../store/store'
import { clearMessages } from '../../store/therapistChatbotSlice'
import { formatResponse } from '../../utils/formatResponse'
import { useAppDispatch } from '../../utils/hooks'
import { getPageFromPath, getPathFromPage, PAGES } from '../../utils/routes'

const TherapistChatbot = (): ReactElement => {
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const navigate = useNavigate()
  const { patientId } = useParams()
  const location = useLocation()
  const { notifyError } = useNotify()

  const messages = useSelector((s: RootState) => s.therapistChatbot.therapistChatbotMessages)
  const chatbotStatus = useSelector((s: RootState) => s.therapistChatbot.status)
  const chatbotError = useSelector((s: RootState) => s.therapistChatbot.error)
  const compactBubble = { py: 0.1, px: 1.5 }

  useEffect(() => {
    if (chatbotError) {
      notifyError(typeof chatbotError === 'string' ? chatbotError : 'An unknown error occurred')
    }
  }, [chatbotError, notifyError])

  const currentPage = getPageFromPath(location.pathname)
  const closePage =
    currentPage === PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT
      ? getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, { patientId: patientId ?? '' })
      : getPathFromPage(PAGES.HOME_PAGE)

  const lastAssistant = messages.at(-1)
  const listRef = useRef<HTMLUListElement>(null)
  const listEndRef = useRef<HTMLDivElement>(null)

  const { stream: typingStream, running: isStreaming } = useTypewriter(
    lastAssistant?.chatRole === ChatMessageDTOChatRoleEnum.Assistant
      ? lastAssistant.content
      : undefined
  )

  useLayoutEffect(() => {
    listEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages, typingStream, chatbotStatus])

  const scrollToBottom = (): void => {
    const list = listRef.current
    if (list) {
      list.scrollTop = list.scrollHeight
    }
  }

  useEffect((): (() => void) => {
  const id = requestAnimationFrame(scrollToBottom)
  return () => cancelAnimationFrame(id)
}, [messages, typingStream, chatbotStatus])

  return (
    <Layout>
      <Box sx={{ display: 'flex', flexDirection: 'column' }}>
        <Tooltip title={t('layout.close')}>
          <IconButton
            sx={{ color: 'black', height: 30, width: 30, position: 'fixed', top: 80, right: 20 }}
            onClick={() => {
              dispatch(clearMessages())
              navigate(closePage)
            }}
          >
            <CloseIcon sx={{ color: 'red' }} />
          </IconButton>
        </Tooltip>

        <List
          ref={listRef}
          sx={{
            overflowY: 'auto',
            flexGrow: 1,
            display: 'flex',
            flexDirection: 'column',
            gap: 1,
          }}
        >
          {messages.map((m, i) => {
            const isLastAssistant =
              i === messages.length - 1 && m.chatRole === ChatMessageDTOChatRoleEnum.Assistant
            const body =
              isLastAssistant && isStreaming
                ? formatResponse(typingStream)
                : formatResponse(m.content ?? '')

            return m.chatRole === ChatMessageDTOChatRoleEnum.User ? (
              <ListItem key={i} sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                <Box sx={{ maxWidth: '80%', ml: 'auto' }}>
                  <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                    You
                  </Typography>
                  <Paper
                    sx={{
                      ...compactBubble,
                      bgcolor: 'primary.main',
                      color: 'white',
                      borderRadius: '20px',
                    }}
                  >
                    <Typography variant='body1'>{body}</Typography>
                  </Paper>
                </Box>
              </ListItem>
            ) : (
              <ListItem key={i} sx={{ alignItems: 'flex-start', flexDirection: 'row' }}>
                <Avatar
                  src={AssistantImage}
                  alt='Chatbot avatar'
                  sx={{ bgcolor: 'transparent', mr: 1, mt: 3 }}
                >
                  <AssistantIcon sx={{ color: 'black' }} />
                </Avatar>
                <Box sx={{ maxWidth: '80%' }}>
                  <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                    Chatbot
                  </Typography>
                  <Paper
                    sx={{
                      ...compactBubble,
                      bgcolor: '#E5E5E5',
                      borderRadius: '20px',
                      display: 'inline-block',
                    }}
                  >
                    <Typography variant='body1'>{body}</Typography>
                  </Paper>
                </Box>
              </ListItem>
            )
          })}

          {chatbotStatus === 'loading' && (
            <ListItem sx={{ alignItems: 'flex-start', flexDirection: 'row' }}>
              <Avatar
                src={AssistantImage}
                alt='Chatbot avatar'
                sx={{ bgcolor: 'transparent', mr: 1, mt: 3 }}
              >
                <AssistantIcon sx={{ color: 'black' }} />
              </Avatar>
              <Box sx={{ maxWidth: '80%' }}>
                <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                  Chatbot
                </Typography>
                <Paper
                  sx={{
                    ...compactBubble,
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
                </Paper>
              </Box>
            </ListItem>
          )}
          <div ref={listEndRef} />
        </List>
      </Box>
    </Layout>
  )
}

export default TherapistChatbot
