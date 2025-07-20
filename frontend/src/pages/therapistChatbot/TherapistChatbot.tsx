import AssistantIcon from '@mui/icons-material/Assistant'
import CloseIcon from '@mui/icons-material/Close'
import { Avatar, Box, IconButton, List, ListItem, Paper, Typography } from '@mui/material'
import { ReactElement, useEffect, useRef } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { ChatMessageDTOChatRoleEnum } from '../../api'
import Layout from '../../generalComponents/Layout'
import { useTypewriter } from '../../hooks/useTypewriter'
import { RootState } from '../../store/store'
import { clearMessages } from '../../store/therapistChatbotSlice'
import { formatResponse } from '../../utils/formatResponse'
import { useAppDispatch } from '../../utils/hooks'
import { getPageFromPath, getPathFromPage, PAGES } from '../../utils/routes'

const TherapistChatbot = (): ReactElement => {
  const dispatch = useAppDispatch()

  const navigate = useNavigate()
  const { patientId } = useParams()

  const messages = useSelector((s: RootState) => s.therapistChatbot.therapistChatbotMessages)
  const chatbotStatus = useSelector((s: RootState) => s.therapistChatbot.status)

  const currentPage = getPageFromPath(location.pathname)
  const closePage =
    currentPage === PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT
      ? getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, { patientId: patientId ?? '' })
      : getPathFromPage(PAGES.HOME_PAGE)

  const lastAssistant = messages.at(-1)

  const listRef = useRef<HTMLUListElement>(null)
  useEffect(() => {
    listRef.current?.scrollTo(0, listRef.current.scrollHeight)
  }, [messages])

  const { stream: typingStream, running: isStreaming } = useTypewriter(
    lastAssistant?.chatRole === ChatMessageDTOChatRoleEnum.Assistant
      ? lastAssistant.content
      : undefined
  )

  return (
    <Layout>
      {' '}
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
        }}
      >
        <IconButton
          sx={{ color: 'black', height: 30, width: 30, position: 'fixed', top: 80, right: 20 }}
          onClick={() => {
            dispatch(clearMessages())
            navigate(closePage)
          }}
        >
          <CloseIcon sx={{ color: 'red' }} />
        </IconButton>
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
                  <Box
                    sx={{
                      py: 1,
                      px: 2,
                      bgcolor: 'primary.main',
                      color: 'white',
                      borderRadius: '20px',
                    }}
                  >
                    <Typography variant='body1'>{body}</Typography>
                  </Box>
                </Box>
              </ListItem>
            ) : (
              <ListItem key={i} sx={{ alignItems: 'flex-start', flexDirection: 'row' }}>
                <Avatar sx={{ bgcolor: 'transparent', mr: 1, mt: 0.5 }}>
                  <AssistantIcon sx={{ color: 'black' }} />
                </Avatar>
                <Box sx={{ maxWidth: '80%' }}>
                  <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                    Chatbot
                  </Typography>
                  <Paper
                    sx={{
                      py: 1,
                      px: 2,
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
              <Avatar sx={{ bgcolor: 'transparent', mr: 1, mt: 0.5 }}>
                <AssistantIcon sx={{ color: 'black' }} />
              </Avatar>
              <Box sx={{ maxWidth: '80%' }}>
                <Typography variant='caption' sx={{ display: 'block', ml: 1 }}>
                  Chatbot
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
        </List>
      </Box>
    </Layout>
  )
}

export default TherapistChatbot
