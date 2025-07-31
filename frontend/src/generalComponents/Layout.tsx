import ExpandLessIcon from '@mui/icons-material/ExpandLess'
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import LogoutIcon from '@mui/icons-material/Logout'
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
  Tooltip,
  Typography,
} from '@mui/material'
import React, { ReactNode, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { Location, useLocation, useNavigate, useParams, useSearchParams } from 'react-router-dom'

import logo from '../../public/uzh-logo.png'
import { RootState } from '../store/store'
import { chatWithTherapistChatbot, clearMessages } from '../store/therapistChatbotSlice'
import { getCurrentlyLoggedInTherapist, logoutTherapist } from '../store/therapistSlice'
import { commonButtonStyles, disabledButtonStyles } from '../styles/buttonStyles'
import { useAppDispatch } from '../utils/hooks'
import { getCurrentLanguage } from '../utils/languageUtil'
import {
  findPageTrace,
  getPageFromPath,
  getPageName,
  getPathFromPage,
  PAGES,
} from '../utils/routes'
import GlobalErrorSnackbar from './GlobalErrorSnackbar'

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

  const patientIdRoute = routeParams.patientId
  const patientIdQuery = searchParams.get('patientId') ?? undefined
  const patientIdState = location.state?.patientId
  const explicitId = patientIdRoute || patientIdQuery || patientIdState
  const isExplicitCtx = Boolean(explicitId)
  useEffect(() => {
    if (isExplicitCtx) {
      sessionStorage.setItem('activePatientId', explicitId!)
    }
  }, [isExplicitCtx, explicitId])

  const forwardPatientId = isExplicitCtx ? explicitId : undefined

  const currentPage = getPageFromPath(location.pathname)
  const currentPageName =
    currentPage === PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE
      ? t('pages.chatbot.details')
      : getPageName(currentPage, t)

  const therapistChatbotExpandedPage = explicitId
    ? getPathFromPage(PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT, { patientId: explicitId })
    : getPathFromPage(PAGES.THERAPIST_CHATBOT_PAGE)

  let pageTrace = findPageTrace(currentPage) ?? [PAGES.HOME_PAGE]
  if (isExplicitCtx && currentPage === PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE) {
    pageTrace = [PAGES.HOME_PAGE, PAGES.PATIENTS_DETAILS_PAGE, PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]
  }
  const labelFor = (p: PAGES): string => {
    return p === PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE
      ? t('pages.chatbot.details')
      : getPageName(p, t)
  }

  const [input, setInput] = useState('')

  const chatbotStatus = useSelector((s: RootState) => s.therapistChatbot.status)

  const sendMessage = async (): Promise<void> => {
    if (!input.trim() || chatbotStatus === 'loading') {
      return
    }
    setInput('')

    navigate(therapistChatbotExpandedPage)
    await dispatch(
      chatWithTherapistChatbot({
        newMessage: input.trim(),
        patientId: forwardPatientId,
        language: getCurrentLanguage(),
      })
    )
  }

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

  const handleChatbotInput = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void => {
    if (
      !input &&
      currentPage !== PAGES.THERAPIST_CHATBOT_PAGE &&
      currentPage !== PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT
    ) {
      dispatch(clearMessages())
    }
    setInput(e.target.value)
  }

  return (
    <>
      <style>{`
        .typing-indicator{display:flex;justify-content:center;align-items:center;margin-bottom:25px}
        .typing-indicator span{display:inline-block;margin:0 2px;font-size:60px;color:grey;
          line-height:0;animation:bounce 1s infinite}
        .typing-indicator span:nth-child(1){animation-delay:.1s}
        .typing-indicator span:nth-child(2){animation-delay:.2s}
        .typing-indicator span:nth-child(3){animation-delay:.3s}
        @keyframes bounce{0%,100%{transform:translateY(0)}50%{transform:translateY(-10px)}}
      `}</style>

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
            <Tooltip title={t('layout.settings')}>
              <IconButton
                sx={{ mr: 1 }}
                onClick={() => navigate(getPathFromPage(PAGES.SETTINGS_PAGE))}
              >
                <SettingsIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('layout.logout')}>
              <IconButton
                onClick={() => {
                  dispatch(logoutTherapist())
                  navigate(getPathFromPage(PAGES.LOGIN_PAGE))
                }}
              >
                <LogoutIcon fontSize='small' />
              </IconButton>
            </Tooltip>
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
          <div
            style={{
              display: 'flex',
              justifyContent: 'center',
              width: '100%',
              marginTop: '30px',
              marginBottom: '70px',
            }}
          >
            <img src={logo} alt='UZH Chatbot' style={{ width: '80%' }} />
          </div>
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              pl: 2,
              pr: 2,
              alignItems: 'center',
            }}
          >
            {[PAGES.HOME_PAGE, ...pageTrace.slice(1)].map((page, idx) => {
              const pathParams =
                page === PAGES.PATIENTS_DETAILS_PAGE && forwardPatientId
                  ? { patientId: forwardPatientId }
                  : (routeParams as Record<string, string>)
              const path = getPathFromPage(page, pathParams)
              const active = currentPage === page
              return (
                <React.Fragment key={page}>
                  {idx !== 0 && <KeyboardArrowDownIcon sx={{ paddingBottom: '20px' }} />}
                  <Button
                    variant={active ? 'contained' : 'text'}
                    onClick={() =>
                      forwardPatientId && page !== PAGES.HOME_PAGE
                        ? navigate(path, { state: { patientId: forwardPatientId } })
                        : navigate(path)
                    }
                    sx={{
                      color: 'white',
                      bgcolor: active ? '#1F2937' : 'transparent',
                      textTransform: 'none',
                      fontWeight: active ? 600 : 400,
                      fontSize: '18px',
                      justifyContent: 'center',
                      width: '100%',
                      px: 1.5,
                      padding: '20px 15px',
                    }}
                  >
                    {labelFor(page)}
                  </Button>
                </React.Fragment>
              )
            })}
          </Box>
        </Drawer>

        <Box component='main' sx={{ flexGrow: 1, p: 3, bgcolor: 'white', pb: '120px' }}>
          <Toolbar />
          {children}
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
            value={input}
            onChange={handleChatbotInput}
            onKeyDown={(e) => {
              if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault()
                if (chatbotStatus !== 'loading') {
                  sendMessage()
                }
              }
            }}
            placeholder={t('footer.note')}
            sx={{
              bgcolor: 'white',
              borderRadius: 1,
              '& .MuiOutlinedInput-root': { height: '60px' },
            }}
          />
          <Tooltip title={t('layout.send')}>
            <Button
              onClick={sendMessage}
              variant='contained'
              disabled={!input.trim() || chatbotStatus === 'loading'}
              sx={{
                position: 'absolute',
                right: 10,
                bottom: 27,
                top: 10,
                ...(!input.trim() || chatbotStatus === 'loading'
                  ? smallDisabledButtonStyles
                  : sendButtonStyles),
              }}
            >
              <SendIcon />
            </Button>
          </Tooltip>

          <Tooltip title={t('layout.expand_AI_assistant')}>
            <IconButton
              sx={{ position: 'absolute', right: '5px', bottom: '60px' }}
              onClick={() => navigate(therapistChatbotExpandedPage)}
            >
              <ExpandLessIcon sx={{ color: 'black' }} />
            </IconButton>
          </Tooltip>
        </Box>
      </Box>
      <GlobalErrorSnackbar />
    </>
  )
}

export default Layout
