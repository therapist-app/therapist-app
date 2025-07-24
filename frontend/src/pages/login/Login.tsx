import { Button, Container, TextField, Typography } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'

import { LoginTherapistDTO } from '../../api'
import GlobalErrorSnackbar from '../../generalComponents/GlobalErrorSnackbar'
import { useNotify } from '../../hooks/useNotify'
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
      notifySuccess(t('login.success') || 'Login successful.')
      navigate(getPathFromPage(PAGES.HOME_PAGE))
    } catch (msg) {
      notifyError(typeof msg === 'string' ? msg : 'An unknown error occurred')
    }
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  return (
    <>
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          width: '100%',
          gap: '32px',
        }}
      >
        <Typography variant='h2'> {t('login.welcome')}</Typography>
        <Typography variant='h4' gutterBottom>
          {t('login.login_therapist')}
        </Typography>
      </div>
      <Container maxWidth='xs'>
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
          />
          <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '380px', mt: 2 }}>
            {t('login.login')}
          </Button>
        </form>
      </Container>
      <div style={{ display: 'flex', width: '100%', marginTop: '32px', justifyContent: 'center' }}>
        <Button
          onClick={() => navigate(getPathFromPage(PAGES.REGISTRATION_PAGE))}
          sx={{ ...successButtonStyles, minWidth: '380px' }}
        >
          {t('login.go_registration')}
        </Button>
      </div>
      <GlobalErrorSnackbar />
    </>
  )
}

export default Login
