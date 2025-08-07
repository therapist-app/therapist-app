import { Box, Button, Container, TextField, Typography } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'

import { CreateTherapistDTO } from '../../api'
import GlobalErrorSnackbar from '../../generalComponents/GlobalErrorSnackbar'
import { useNotify } from '../../hooks/useNotify'
import { registerTherapist } from '../../store/therapistSlice'
import { commonButtonStyles, successButtonStyles } from '../../styles/buttonStyles'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const Register = (): ReactElement => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const { t } = useTranslation()
  const { notifyError, notifySuccess, notifyWarning } = useNotify()

  const [formData, setFormData] = useState<CreateTherapistDTO>({
    email: '',
    password: '',
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    if (!formData.email || !formData.password) {
      notifyWarning(t('register.fields_required') || 'Both fields are required.')
      return
    }

    try {
      await dispatch(registerTherapist(formData)).unwrap()
      notifySuccess(t('register.success'))
      navigate(getPathFromPage(PAGES.HOME_PAGE))
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  return (
    <div>
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
            {t('register.welcome')}
          </Typography>

          <Typography
            variant='subtitle1'
            sx={{
              marginLeft: 3,
              color: '#666',
              fontWeight: 500,
            }}
          >
            {t('register.register')}
          </Typography>

          <Container maxWidth='sm' sx={{ paddingX: 0 }}>
            <form onSubmit={handleSubmit}>
              <TextField
                label={t('register.email')}
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
                label={t('register.password')}
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
                {t('register.register')}
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
              onClick={() => navigate(getPathFromPage(PAGES.LOGIN_PAGE))}
              sx={{
                ...successButtonStyles,
                width: '100%',
                padding: '12px',
                fontSize: '1rem',
                margin: 0,
                marginBottom: 2,
              }}
            >
              {t('register.go_login')}
            </Button>
          </Container>
        </Box>
      </div>
      <GlobalErrorSnackbar />
    </div>
  )
}

export default Register
