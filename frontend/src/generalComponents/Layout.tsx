import React, { useState, ReactNode } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
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
  Collapse,
} from '@mui/material'
import AccountCircleIcon from '@mui/icons-material/AccountCircle'
import LogoutIcon from '@mui/icons-material/Logout'
import HomeIcon from '@mui/icons-material/Home'
import SettingsIcon from '@mui/icons-material/Settings'
import PeopleIcon from '@mui/icons-material/People'
import ExpandLess from '@mui/icons-material/ExpandLess'
import ExpandMore from '@mui/icons-material/ExpandMore'

import logo from '../../public/Therapist-App.png'
import { useTranslation } from 'react-i18next'
import { logoutTherapist } from '../store/therapistSlice'
import { useAppDispatch } from '../utils/hooks'
import { useSelector } from 'react-redux'
import { RootState } from '../store/store'

interface LayoutProps {
  children: ReactNode
}

const drawerWidth = 280
const selectedColor = '#635BFF'

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const navigate = useNavigate()
  const location = useLocation()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()

  const loggedInTherapist = useSelector((state: RootState) => state.therapist.loggedInTherapist)

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const [openPatients, setOpenPatients] = useState(location.pathname.startsWith('/patients'))

  const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget)
  }
  const handleMenuClose = () => {
    setAnchorEl(null)
  }
  const handleLogout = () => {
    handleMenuClose()
    dispatch(logoutTherapist())
    navigate('/login')
  }

  const handleSettings = () => {
    navigate('/settings')
  }

  const handlePatientsClick = () => {
    setOpenPatients(!openPatients)
    if (!location.pathname.startsWith('/patients')) {
      navigate('/patients')
    }
  }

  const handleListPatients = () => {
    navigate(`/patients`)
  }
  const handleCreatePatient = () => {
    navigate('/patients/create')
  }
  const handlePatientDetails = () => {
    if (loggedInTherapist?.patientsOutputDTO && loggedInTherapist.patientsOutputDTO.length > 0) {
      navigate(`/patients/${loggedInTherapist?.patientsOutputDTO[0].id}`)
    }
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
            color: '#9EA2A8',
            overflow: 'hidden',
          },
        }}
      >
        <Typography
          variant='caption'
          sx={{
            fontSize: '1.2rem',
            color: '#fff',
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
            onClick={() => navigate('/')}
            sx={{
              borderRadius: 2,
              marginX: 2,
              maxWidth: 'calc(100% - 32px)',
              bgcolor: location.pathname === '/' ? selectedColor : 'transparent',
              color: location.pathname === '/' ? '#fff' : '#9EA2A8',
              '&:hover': {
                bgcolor: location.pathname === '/' ? selectedColor : '#1D2336',
                color: '#fff',
              },
              boxShadow: 3,
            }}
          >
            <ListItemIcon sx={{ color: location.pathname === '/' ? '#fff' : '#9EA2A8' }}>
              <HomeIcon />
            </ListItemIcon>
            <ListItemText primary={t('layout.overview')} />
          </ListItemButton>
        </ListItem>

        <Typography
          variant='caption'
          sx={{
            fontSize: '0.875rem',
            color: '#8A94A6',
            marginTop: 3,
            marginBottom: 1,
            marginLeft: 2,
          }}
        >
          {t('General')}
        </Typography>

        <ListItem disablePadding sx={{ marginY: 0.5 }}>
          <ListItemButton
            onClick={handlePatientsClick}
            sx={{
              borderRadius: 1,
              marginX: 2,
              maxWidth: 'calc(100% - 32px)',
              '&:hover': {
                color: '#fff',
              },
            }}
          >
            <ListItemIcon sx={{ color: location.pathname === '/patients' ? '#fff' : '#9EA2A8' }}>
              <PeopleIcon />
            </ListItemIcon>
            <ListItemText primary={t('layout.patients')} style={{ marginLeft: -20 }} />
            {openPatients ? <ExpandLess /> : <ExpandMore />}
          </ListItemButton>
        </ListItem>

        <Collapse in={openPatients} timeout='auto' unmountOnExit>
          <Box sx={{ position: 'relative', mt: 1, mb: 1 }}>
            <Box
              sx={{
                position: 'absolute',
                top: 0,
                bottom: 0,
                left: '32px',
                width: '1px',
                bgcolor: '#8A94A6',
              }}
            />

            <ListItem disablePadding>
              <ListItemButton
                onClick={handleListPatients}
                sx={{
                  ml: '48px',
                  borderRadius: 2,
                  maxWidth: '215px',
                  marginLeft: '50px',
                  position: 'relative',
                  bgcolor: location.pathname === '/patients' ? selectedColor : 'transparent',
                  color: location.pathname === '/patients' ? '#fff' : '#9EA2A8',
                  '&:hover': {
                    bgcolor: location.pathname === '/patients' ? selectedColor : '#1A2030',
                    color: '#fff',
                  },
                  '&::before': {
                    content: '""',
                    position: 'absolute',
                    left: '-19px',
                    top: '50%',
                    transform: 'translateY(-50%)',
                    width: '4px',
                    height: '16px',
                    bgcolor: location.pathname === '/patients' ? '#8A94A6' : 'transparent',
                  },
                }}
              >
                <ListItemText primary='List patients' style={{ marginLeft: 20 }} />
              </ListItemButton>
            </ListItem>

            <ListItem disablePadding>
              <ListItemButton
                onClick={handleCreatePatient}
                sx={{
                  ml: '48px',
                  borderRadius: 2,
                  maxWidth: '215px',
                  marginLeft: '50px',
                  position: 'relative',
                  bgcolor: location.pathname === '/patients/create' ? selectedColor : 'transparent',
                  color: location.pathname === '/patients/create' ? '#fff' : '#9EA2A8',
                  '&:hover': {
                    bgcolor: location.pathname === '/patients/create' ? selectedColor : '#1A2030',
                    color: '#fff',
                  },
                  '&::before': {
                    content: '""',
                    position: 'absolute',
                    left: '-19px',
                    top: '50%',
                    transform: 'translateY(-50%)',
                    width: '4px',
                    height: '16px',
                    bgcolor: location.pathname === '/patients/create' ? '#8A94A6' : 'transparent',
                  },
                }}
              >
                <ListItemText primary='Create patient' style={{ marginLeft: 20 }} />
              </ListItemButton>
            </ListItem>

            <ListItem disablePadding>
              <ListItemButton
                onClick={handlePatientDetails}
                sx={{
                  ml: '48px',
                  borderRadius: 2,
                  maxWidth: '215px',
                  marginLeft: '50px',
                  position: 'relative',
                  bgcolor:
                    location.pathname.startsWith('/patients/') &&
                    location.pathname !== '/patients/create'
                      ? selectedColor
                      : 'transparent',
                  color:
                    location.pathname.startsWith('/patients/') &&
                    location.pathname !== '/patients/create'
                      ? '#fff'
                      : '#9EA2A8',
                  '&:hover': {
                    bgcolor:
                      location.pathname.startsWith('/patients/') &&
                      location.pathname !== '/patients/create'
                        ? selectedColor
                        : '#1A2030',
                    color: '#fff',
                  },
                  '&::before': {
                    content: '""',
                    position: 'absolute',
                    left: '-19px',
                    top: '50%',
                    transform: 'translateY(-50%)',
                    width: '4px',
                    height: '16px',
                    bgcolor:
                      location.pathname.startsWith('/patients/') &&
                      location.pathname !== '/patients/create'
                        ? '#8A94A6'
                        : 'transparent',
                  },
                }}
              >
                <ListItemText primary='Patient details' style={{ marginLeft: 20 }} />
              </ListItemButton>
            </ListItem>
          </Box>
        </Collapse>

        <ListItem disablePadding sx={{ marginY: 0.5 }}>
          <ListItemButton
            onClick={handleSettings}
            sx={{
              borderRadius: 1,
              marginX: 2,
              maxWidth: 'calc(100% - 32px)',
              bgcolor: location.pathname === '/settings' ? selectedColor : 'transparent',
              color: location.pathname === '/settings' ? '#fff' : '#9EA2A8',
              '&:hover': {
                bgcolor: location.pathname === '/settings' ? selectedColor : '#1D2336',
                color: '#fff',
              },
            }}
          >
            <ListItemIcon sx={{ color: location.pathname === '/settings' ? '#fff' : '#9EA2A8' }}>
              <SettingsIcon />
            </ListItemIcon>
            <ListItemText primary={t('layout.settings')} style={{ marginLeft: -20 }} />
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
