// src/generalComponents/Layout.tsx
import AssistantIcon from '@mui/icons-material/Assistant'
import ExpandLessIcon from '@mui/icons-material/ExpandLess'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import LogoutIcon from '@mui/icons-material/Logout'
import PersonIcon from '@mui/icons-material/Person'
import SendIcon from '@mui/icons-material/Send'
import SettingsIcon from '@mui/icons-material/Settings'
import {
  AppBar,
  Box,
  Button,
  Drawer,
  IconButton,
  TextField,
  Toolbar,
  Typography,
} from '@mui/material'
import React, { ReactNode, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { Location, useLocation, useNavigate, useParams, useSearchParams } from 'react-router-dom'

import logo from '../../public/Therapist-App.png'
import { ChatMessageDTOChatRoleEnum } from '../api'
import { RootState } from '../store/store'
import { chatWithTherapistChatbot, clearMessages } from '../store/therapistChatbotSlice'
import { getCurrentlyLoggedInTherapist, logoutTherapist } from '../store/therapistSlice'
import { useAppDispatch } from '../utils/hooks'
import { findPageTrace, getPageFromPath, getPathFromPage, PAGE_NAMES, PAGES } from '../utils/routes'

interface LayoutProps {
  children: ReactNode
}
interface LayoutLocationState {
  patientId?: string
}

const drawerWidth = 280

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const location = useLocation() as Location & { state: LayoutLocationState | null }
  const routeParams = useParams()
  const [searchParams] = useSearchParams()
  const { t } = useTranslation()

  useEffect(() => {
    dispatch(getCurrentlyLoggedInTherapist())
  }, [dispatch])

  const patientIdFromRoute = routeParams.patientId
  const patientIdFromQuery = searchParams.get('patientId') ?? undefined
  const patientIdFromState = location.state?.patientId

  const explicitPatientId = patientIdFromRoute || patientIdFromQuery || patientIdFromState
  const isExplicitContext = Boolean(explicitPatientId)

  useEffect(() => {
    if (isExplicitContext) {
      sessionStorage.setItem('activePatientId', explicitPatientId!)
    }
  }, [isExplicitContext, explicitPatientId])

  const forwardPatientId = isExplicitContext ? explicitPatientId : undefined

  const currentPage = getPageFromPath(location.pathname)
  const currentPageName =
    currentPage === PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE
      ? 'Chatbot Details'
      : PAGE_NAMES[currentPage]

  let pageTrace = findPageTrace(currentPage) ?? [PAGES.HOME_PAGE]

  if (isExplicitContext && currentPage === PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE) {
    pageTrace = [PAGES.HOME_PAGE, PAGES.PATIENTS_DETAILS_PAGE, PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]
  }

  const labelForPage = (p: PAGES): string =>
    p === PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE ? 'Chatbot Details' : PAGE_NAMES[p]

  const [isExpanded, setIsExpanded] = useState(false)
  const [assistantInput, setAssistantInput] = useState('')
  const therapistChatbotMessages = useSelector(
    (s: RootState) => s.therapistChatbot.therapistChatbotMessages
  )

  const sendAssistantMessage = async (): Promise<void> => {
    setAssistantInput('')
    setIsExpanded(true)
    await dispatch(
      chatWithTherapistChatbot({
        newMessage: assistantInput,
        patientId: forwardPatientId,
      })
    )
  }

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar
        position='fixed'
        sx={{
          zIndex: (t) => t.zIndex.drawer + 1,
          bgcolor: 'white',
          color: 'black',
          boxShadow: 'none',
          borderBottom: '1px solid #e0e0e0',
          width: `calc(100% - ${drawerWidth}px)`,
          ml: `${drawerWidth}px`,
        }}
      >
        <Toolbar>
          <Typography variant='h1' sx={{ flexGrow: 1 }}>
            {currentPageName}
          </Typography>

          <IconButton sx={{ mr: 1 }} onClick={() => navigate(getPathFromPage(PAGES.SETTINGS_PAGE))}>
            <SettingsIcon />
          </IconButton>
          <IconButton
            onClick={() => {
              dispatch(logoutTherapist())
              navigate(getPathFromPage(PAGES.LOGIN_PAGE))
            }}
          >
            <LogoutIcon fontSize='small' />
          </IconButton>
        </Toolbar>
      </AppBar>

      <Drawer
        variant='permanent'
        sx={{
          width: drawerWidth,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            bgcolor: '#121621',
            color: '#9EA2A8',
            overflow: 'hidden',
          },
        }}
      >
        <img
          src={logo}
          alt='UZH Chatbot'
          style={{ width: '100%', marginTop: '-95px', marginLeft: '-20px' }}
        />

        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1, pl: 2 }}>
          {[PAGES.HOME_PAGE, ...pageTrace.slice(1)].map((page, idx) => {
            const pathParams =
              page === PAGES.PATIENTS_DETAILS_PAGE && forwardPatientId
                ? { patientId: forwardPatientId }
                : (routeParams as Record<string, string>)
            const path = getPathFromPage(page, pathParams)
            const isActive = currentPage === page

            const go = (): void => {
              if (forwardPatientId && page !== PAGES.HOME_PAGE) {
                navigate(path, { state: { patientId: forwardPatientId } })
              } else {
                navigate(path)
              }
            }

            return (
              <React.Fragment key={page}>
                {idx !== 0 && <KeyboardArrowDownIcon sx={{ ml: 1 }} />}
                <Button
                  variant={isActive ? 'contained' : 'text'}
                  onClick={go}
                  sx={{
                    color: 'white',
                    bgcolor: isActive ? '#1F2937' : 'transparent',
                    textTransform: 'none',
                    fontWeight: isActive ? 600 : 400,
                    fontSize: '18px',
                    justifyContent: 'flex-start',
                    width: '200px',
                    px: 1.5,
                  }}
                >
                  {labelForPage(page)}
                </Button>
              </React.Fragment>
            )
          })}
        </Box>
      </Drawer>

      <Box component='main' sx={{ flexGrow: 1, p: 3, bgcolor: 'white', pb: '120px' }}>
        <Toolbar />
        {!isExpanded && children}
      </Box>

      <Box
        sx={{
          position: 'fixed',
          top: 64,
          left: drawerWidth,
          height: 'calc(100vh - 164px)',
          bgcolor: '#b4b6b4',
          p: '20px 40px 20px 30px',
          visibility: isExpanded ? 'visible' : 'hidden',
          overflowY: 'auto',
          width: `calc(100vw - ${drawerWidth}px)`,
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
        }}
      >
        {therapistChatbotMessages.map((m, i) => (
          <Box key={i} sx={{ display: 'flex', gap: 1 }}>
            {m.chatRole === ChatMessageDTOChatRoleEnum.User ? <PersonIcon /> : <AssistantIcon />}
            <Typography sx={{ color: 'black' }}>{m.content}</Typography>
          </Box>
        ))}
      </Box>

      <Box
        sx={{
          position: 'fixed',
          bottom: 0,
          left: drawerWidth,
          right: 0,
          bgcolor: '#b4b6b4',
          display: 'flex',
          gap: 2,
          alignItems: 'center',
          p: 2,
          zIndex: 1000,
        }}
      >
        <TextField
          fullWidth
          value={assistantInput}
          onChange={(e) => setAssistantInput(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
              e.preventDefault()
              sendAssistantMessage().catch(console.error)
            }
          }}
          placeholder={t('footer.note')}
          sx={{
            bgcolor: 'white',
            borderRadius: 1,
            '& .MuiOutlinedInput-root': { height: '60px' },
          }}
        />
        <IconButton
          onClick={() => sendAssistantMessage().catch(console.error)}
          sx={{ position: 'absolute', right: isExpanded ? 30 : 70, bottom: 30 }}
        >
          <SendIcon sx={{ color: 'black' }} />
        </IconButton>
        {!isExpanded && (
          <IconButton
            sx={{ color: 'black', height: 30, width: 30 }}
            onClick={() => setIsExpanded(true)}
          >
            <ExpandLessIcon />
          </IconButton>
        )}
      </Box>

      {isExpanded && (
        <IconButton
          sx={{
            color: 'black',
            height: 30,
            width: 30,
            position: 'fixed',
            top: 80,
            right: 20,
          }}
          onClick={() => {
            dispatch(clearMessages())
            setAssistantInput('')
            setIsExpanded(false)
          }}
        >
          <ExpandMoreIcon />
        </IconButton>
      )}
    </Box>
  )
}

export default Layout
