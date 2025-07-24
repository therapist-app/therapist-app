import { Button, Container, Snackbar, Alert, TextField, Typography } from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'

import { LoginTherapistDTO } from '../../api'
import { loginTherapist } from '../../store/therapistSlice'
import { commonButtonStyles, successButtonStyles } from '../../styles/buttonStyles'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const Login = (): ReactElement => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const { t } = useTranslation()

  const [formData, setFormData] = useState<LoginTherapistDTO>({
    email: '',
    password: '',
  })
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [snackbarSeverity, setSnackbarSeverity] = useState<
    'info' | 'success' | 'error' | 'warning'
  >('error')

  const openSnackbar = (
    msg: string,
    sev: 'info' | 'success' | 'error' | 'warning' = 'error'
  ): void => {
    setSnackbarMessage(msg)
    setSnackbarSeverity(sev)
    setSnackbarOpen(true)
  }

  const handleLogin = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    if (!formData.email || !formData.password) {
      openSnackbar(t('login.fields_required') || 'Both fields are required.', 'warning')
      return
    }
    try {
      await dispatch(loginTherapist(formData)).unwrap()
      openSnackbar(t('login.success') || 'Login successful.', 'success')
      navigate(getPathFromPage(PAGES.HOME_PAGE))
    } catch (e) {
      openSnackbar(handleError(e as AxiosError), 'error')
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
        <Typography variant='h2'>{t('login.welcome')}</Typography>
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

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={() => setSnackbarOpen(false)}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={() => setSnackbarOpen(false)} severity={snackbarSeverity} sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </>
  )
}

export default Login
