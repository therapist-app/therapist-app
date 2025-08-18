import { Box, Button, Container, TextField, Typography } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'

import { LoginTherapistDTO } from '../../api'
import GlobalErrorSnackbar from '../../generalComponents/GlobalErrorSnackbar'
import { useNotify } from '../../hooks/useNotify'
import { clearMessages } from '../../store/therapistChatbotSlice'
import { loginTherapist } from '../../store/therapistSlice'
import { commonButtonStyles, successButtonStyles } from '../../styles/buttonStyles'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const Login = (): ReactElement => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const { t } = useTranslation()
  const { notifyError, notifySuccess, notifyWarning } = useNotify()

  const [formData, setFormData] = useState<LoginTherapistDTO>({
    email: '',
    password: '',
  })

  const handleLogin = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()
    if (!formData.email || !formData.password) {
      notifyWarning(t('login.fields_required') || 'Both fields are required.')
      return
    }
    try {
      await dispatch(loginTherapist(formData)).unwrap()
      dispatch(clearMessages())
      notifySuccess(t('login.success'))
      navigate(getPathFromPage(PAGES.HOME_PAGE))
    } catch (msg) {
      notifyError(typeof msg === 'string' ? msg : 'An unknown error occurred')
    }
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <Box
        sx={{
          backgroundColor: 'rgba(255, 255, 255, 0.9)',
          borderRadius: 8,
          padding: 4,
          width: '100%',
          maxWidth: '400px',
          boxShadow: '0 10px 30px rgba(53, 3, 100, 0.31)',
          marginTop: 6,
        }}
      >
        <Typography
          variant='h1'
          sx={{
            fontFamily: 'Jost, sans-serif',
            fontSize: '90px',
            color: '#764ba2',
            textAlign: 'center',
            marginBottom: 2,
            fontWeight: 700,
            lineHeight: 1,
          }}
        >
          Nexa
        </Typography>

        <Typography
          variant='h5'
          sx={{
            textAlign: 'center',
            color: '#555',
            marginBottom: 5,
            fontWeight: 200,
          }}
        >
          {t('login.welcome')}
        </Typography>

        <Typography
          variant='subtitle1'
          sx={{
            marginLeft: 3,
            color: '#666',
            fontWeight: 500,
          }}
        >
          {t('login.login')}
        </Typography>

        <Container maxWidth='sm' sx={{ paddingX: 0 }}>
          <form onSubmit={handleLogin}>
            <TextField
              label={t('login.email')}
              name='email'
              type='email'
              value={formData.email}
              onChange={handleChange}
              fullWidth
              margin='normal'
              required
              autoComplete='email'
              sx={{
                marginBottom: 0,
                '& .MuiOutlinedInput-root': {
                  borderRadius: '8px',
                },
              }}
            />
            <TextField
              label={t('login.password')}
              name='password'
              type='password'
              value={formData.password}
              onChange={handleChange}
              fullWidth
              margin='normal'
              required
              autoComplete='current-password'
              sx={{
                marginBottom: 4,
                '& .MuiOutlinedInput-root': {
                  borderRadius: '8px',
                },
              }}
            />
            <Button
              type='submit'
              sx={{
                ...commonButtonStyles,
                width: '100%',
                padding: '12px',
                fontSize: '1rem',
                margin: 0,
              }}
            >
              {t('login.login')}
            </Button>
          </form>

          <Typography
            variant='body2'
            sx={{
              textAlign: 'center',
              color: '#666',
              marginY: 2,
            }}
          >
            {t('login.or')}
          </Typography>

          <Button
            onClick={() => navigate(getPathFromPage(PAGES.REGISTRATION_PAGE))}
            sx={{
              ...successButtonStyles,
              width: '100%',
              padding: '12px',
              fontSize: '1rem',
              margin: 0,
              marginBottom: 2,
            }}
          >
            {t('login.go_registration')}
          </Button>
        </Container>
      </Box>
      <GlobalErrorSnackbar />
    </div>
  )
}

export default Login
