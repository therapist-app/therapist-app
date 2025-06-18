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
import { useSelector } from 'react-redux'
import { useLocation, useNavigate, useParams } from 'react-router-dom'

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

const drawerWidth = 280

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const navigate = useNavigate()
  const location = useLocation()
  const dispatch = useAppDispatch()

  const { patientId } = useParams()

  useEffect(() => {
    dispatch(getCurrentlyLoggedInTherapist())
  }, [dispatch])

  const currentPage = getPageFromPath(location.pathname)
  const currentPageName = PAGE_NAMES[currentPage]
  const currentPageTrace = findPageTrace(currentPage)

  const [isExpanded, setIsExpanded] = useState(false)
  const [therapistChatbotInput, setTherapistChatbotInput] = useState('')

  const therapistChatbotMessages = useSelector(
    (state: RootState) => state.therapistChatbot.therapistChatbotMessages
  )

  const handleExpandClicked = (): void => {
    if (isExpanded) {
      dispatch(clearMessages())
      setTherapistChatbotInput('')
    }
    setIsExpanded(!isExpanded)
  }

  const handleAssistantInputChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    setTherapistChatbotInput(event.target.value)
  }

  const handleChatWithTherapistChatbot = async (): Promise<void> => {
    setTherapistChatbotInput('')
    setIsExpanded(true)

    await dispatch(
      chatWithTherapistChatbot({ newMessage: therapistChatbotInput, patientId: patientId })
    )
  }

  const params = useParams() as Record<string, string>

  const handleLogout = (): void => {
    dispatch(logoutTherapist())
    navigate(getPathFromPage(PAGES.LOGIN_PAGE))
  }

  const handleSettingsClicked = (): void => {
    navigate(getPathFromPage(PAGES.SETTINGS_PAGE))
  }

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar
        position='fixed'
        sx={{
          zIndex: (theme) => theme.zIndex.drawer + 1,
          backgroundColor: 'white',
          color: 'black',
          boxShadow: 'none',
          borderBottom: '1px solid #e0e0e0',
          width: `calc(100% - ${drawerWidth}px)`,
          ml: `${drawerWidth}px`,
        }}
      >
        <Toolbar>
          <Typography
            variant='h4'
            noWrap
            component='div'
            sx={{ flexGrow: 1, color: 'text.primary', fontWeight: 500 }}
          >
            {currentPageName}
          </Typography>
          <IconButton sx={{ marginRight: 1 }} onClick={handleSettingsClicked} color='inherit'>
            <SettingsIcon />
          </IconButton>

          <IconButton onClick={handleLogout} color='inherit'>
            <LogoutIcon fontSize='small' />
          </IconButton>
        </Toolbar>
      </AppBar>

      <Drawer
        variant='permanent'
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
            bgcolor: '#121621',
            color: '#9EA2A8',
            overflow: 'hidden',
          },
        }}
      >
        <img
          src={logo}
          alt='UZH Chatbot'
          style={{
            width: '100%',
            marginTop: '-95px',

            marginLeft: '-20px',
          }}
        />

        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: '2px',
            alignItems: 'start',
            paddingLeft: '20px',

            width: '100%',
          }}
        >
          {[PAGES.HOME_PAGE, ...(currentPageTrace?.slice(1) || [])].map((page, index) => {
            const path = getPathFromPage(page, params)
            const isActive = currentPage === page
            return (
              <React.Fragment key={page}>
                {index !== 0 && <KeyboardArrowDownIcon sx={{ ml: '15px' }} />}
                <Button
                  variant={isActive ? 'contained' : 'text'}
                  onClick={() => navigate(path)}
                  sx={{
                    color: 'white',
                    backgroundColor: isActive ? '#1F2937' : 'transparent',
                    textTransform: 'none',
                    fontWeight: isActive ? 600 : 400,
                    fontSize: '18px',
                    justifyContent: 'flex-start',
                    width: '200px',
                    px: 1.5,
                  }}
                >
                  {PAGE_NAMES[page]}
                </Button>
              </React.Fragment>
            )
          })}
        </div>
      </Drawer>

      <Box
        component='main'
        sx={{
          flexGrow: 1,
          p: 3,
          backgroundColor: 'white',
          pb: '120px',
        }}
      >
        <Toolbar />
        <div
          style={{
            display: isExpanded ? 'none' : 'block',
          }}
        >
          {children}
        </div>
      </Box>
      <div
        style={{
          position: 'fixed',
          top: 64,
          left: drawerWidth,
          height: `calc(100vh - 164px)`,
          backgroundColor: '#b4b6b4',
          boxSizing: 'border-box',
          padding: '20px 30px',
          visibility: isExpanded ? 'visible' : 'hidden',
          overflowY: 'auto',
          width: '100%',
        }}
      >
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: '15px',
            paddingRight: '50px',
          }}
        >
          {therapistChatbotMessages.map((message, idx) => (
            <div key={idx} style={{ display: 'flex', gap: '10px' }}>
              {message.chatRole === ChatMessageDTOChatRoleEnum.User ? (
                <PersonIcon />
              ) : (
                <AssistantIcon />
              )}
              <Typography sx={{ color: 'black' }}>{message.content}</Typography>
            </div>
          ))}
        </div>
      </div>

      <div
        style={{
          position: 'fixed',
          bottom: 0,
          left: drawerWidth,
          right: 0,

          backgroundColor: '#b4b6b4',
          display: 'flex',
          gap: '10px',
          justifyContent: 'space-around',
          alignItems: 'center',
          padding: '20px',
        }}
      >
        <TextField
          value={therapistChatbotInput}
          onChange={handleAssistantInputChange}
          onKeyDown={(e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
              e.preventDefault()
              handleChatWithTherapistChatbot()
            }
          }}
          placeholder='Ask your personal assistant...'
          sx={{
            width: '100%',
            height: '60px',
            backgroundColor: 'white',
            borderRadius: '10px',
            '& .MuiOutlinedInput-root': {
              height: '100%',
            },
          }}
        ></TextField>
        <IconButton
          onClick={handleChatWithTherapistChatbot}
          sx={{ position: 'absolute', right: 70, bottom: 30 }}
        >
          <SendIcon sx={{ color: 'black' }} />
        </IconButton>
        <IconButton
          style={{
            color: 'black',
            height: '30px',
            width: '30px',
          }}
          onClick={handleExpandClicked}
        >
          {isExpanded ? (
            <ExpandMoreIcon sx={{ height: '30px', width: '30px' }} />
          ) : (
            <ExpandLessIcon sx={{ height: '30px', width: '30px' }} />
          )}
        </IconButton>
      </div>
    </Box>
  )
}

export default Layout
