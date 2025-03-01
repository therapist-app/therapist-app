import React, { useState, ReactNode } from 'react'
import { useNavigate } from 'react-router-dom'
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Drawer,
  Divider,
  ListItem,
  ListItemText,
  Box,
  Menu,
  MenuItem,
  ListItemButton,
  ListItemIcon,
} from '@mui/material'
import AccountCircleIcon from '@mui/icons-material/AccountCircle'
import LogoutIcon from '@mui/icons-material/Logout'
import HomeIcon from '@mui/icons-material/Home'

import logo from '../../public/Therapist-App.png'
import { useTranslation } from 'react-i18next'

interface LayoutProps {
  children: ReactNode
}

const drawerWidth = 240
const selectedColor = '#635BFF'

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const navigate = useNavigate()
  const { t } = useTranslation()

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)

  const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const handleMenuClose = () => {
    setAnchorEl(null)
  }

  const handleLogout = () => {
    handleMenuClose()
    sessionStorage.removeItem('therapistId')
    sessionStorage.removeItem('workspaceId')
    navigate('/login')
  }

  const handleDashboard = () => {
    const workspaceId = sessionStorage.getItem('workspaceId')
    navigate(`/?workspace_id=${workspaceId || ''}`)
  }

  const menuId = 'primary-search-account-menu'
  const isMenuOpen = Boolean(anchorEl)

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
            variant='h6'
            noWrap
            component='div'
            sx={{ flexGrow: 1, color: 'text.primary' }}
          >
            {t('layout.dashboard')}
          </Typography>
          <IconButton
            edge='end'
            aria-label={t('layout.account')}
            aria-controls={menuId}
            aria-haspopup='true'
            onClick={handleProfileMenuOpen}
            color='inherit'
          >
            <AccountCircleIcon />
          </IconButton>
        </Toolbar>
      </AppBar>

      <Menu
        anchorEl={anchorEl}
        anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        id={menuId}
        keepMounted
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        open={isMenuOpen}
        onClose={handleMenuClose}
      >
        <MenuItem onClick={handleMenuClose}>
          <AccountCircleIcon sx={{ marginRight: 2 }} />
        </MenuItem>
        <Divider />
        <MenuItem onClick={handleLogout}>
          <LogoutIcon fontSize='small' sx={{ marginRight: 2 }} />
          {t('layout.logout')}
        </MenuItem>
      </Menu>

      <Drawer
        variant='permanent'
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
            bgcolor: '#121621',
            color: 'white',
            overflow: 'hidden',
          },
        }}
      >
        <Typography
          variant='caption'
          sx={{
            fontSize: '1.2rem',
            color: 'white',
            marginTop: 2,
            marginBottom: 1,
            marginLeft: 2,
          }}
        >
          <img
            src={logo}
            alt='UZH Chatbot'
            style={{
              width: '100%',
              marginTop: '-95px',
              marginBottom: '-100px',
              marginLeft: '-20px',
            }}
          />
        </Typography>

        <Typography
          variant='caption'
          sx={{
            fontSize: '0.875rem',
            color: '#8A94A6',
            marginTop: 5,
            marginBottom: 1,
            marginLeft: 2,
          }}
        >
          {t('layout.dashboards')}
        </Typography>

        <ListItem disablePadding sx={{ marginY: 1 }}>
          <ListItemButton
            onClick={handleDashboard}
            sx={{
              borderRadius: 1,
              marginX: 2,
              maxWidth: 'calc(100% - 32px)',
              bgcolor: selectedColor,
              color: 'white',
              '&:hover': {
                bgcolor: selectedColor,
              },
              boxShadow: 3,
            }}
          >
            <ListItemIcon sx={{ color: 'white' }}>
              <HomeIcon />
            </ListItemIcon>
            <ListItemText primary={t('layout.overview')} />
          </ListItemButton>
        </ListItem>
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
