import React, { ReactNode, useEffect } from 'react'
import { useNavigate, useLocation, useParams } from 'react-router-dom'
import { AppBar, Toolbar, Typography, IconButton, Drawer, Box, Button } from '@mui/material'
import LogoutIcon from '@mui/icons-material/Logout'
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import SettingsIcon from '@mui/icons-material/Settings'

import logo from '../../public/Therapist-App.png'
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

  useEffect(() => {
    dispatch(getCurrentlyLoggedInTherapist())
  }, [dispatch])

  const currentPage = getPageFromPath(location.pathname)
  const currentPageName = PAGE_NAMES[currentPage]
  const currentPageTrace = findPageTrace(currentPage)

  const params = useParams() as Record<string, string>

  const handleLogout = () => {
    dispatch(logoutTherapist())
    navigate(getPathFromPage(PAGES.LOGIN_PAGE))
  }

  const handleSettingsClicked = () => {
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
        }}
      >
        <Toolbar />
        {children}
      </Box>
    </Box>
  )
}

export default Layout
