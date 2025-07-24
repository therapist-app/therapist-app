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
  Typography,
} from '@mui/material'
import React, { ReactNode, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Location, useLocation, useNavigate, useParams, useSearchParams } from 'react-router-dom'

import logo from '../../public/Therapist-App.png'
import { chatWithTherapistChatbot } from '../store/therapistChatbotSlice'
import { getCurrentlyLoggedInTherapist, logoutTherapist } from '../store/therapistSlice'
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

  const sendMessage = async (): Promise<void> => {
    const msg = input.trim()
    if (!msg) {
      return
    }
    setInput('')

    navigate(therapistChatbotExpandedPage)
    await dispatch(
      chatWithTherapistChatbot({
        newMessage: msg,
        patientId: forwardPatientId,
        language: getCurrentLanguage(),
      })
    )
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
            <IconButton
              sx={{ mr: 1 }}
              onClick={() => navigate(getPathFromPage(PAGES.SETTINGS_PAGE))}
            >
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
              const active = currentPage === page
              return (
                <React.Fragment key={page}>
                  {idx !== 0 && <KeyboardArrowDownIcon sx={{ ml: 1 }} />}
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
                      justifyContent: 'flex-start',
                      width: '200px',
                      px: 1.5,
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
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault()
                sendMessage()
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
            onClick={sendMessage}
            sx={{ position: 'absolute', right: '30px', bottom: '27px' }}
          >
            <SendIcon sx={{ color: 'black' }} />
          </IconButton>

          <IconButton
            sx={{ position: 'absolute', right: '5px', bottom: '60px' }}
            onClick={() => navigate(therapistChatbotExpandedPage)}
          >
            <ExpandLessIcon sx={{ color: 'black' }} />
          </IconButton>
        </Box>
      </Box>
      <GlobalErrorSnackbar />
    </>
  )
}

export default Layout
