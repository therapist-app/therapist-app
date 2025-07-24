import { Alert, Box, Button, Container, Snackbar, TextField, Typography } from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'

import { CreateTherapistDTO } from '../../api'
import { registerTherapist } from '../../store/therapistSlice'
import { commonButtonStyles, successButtonStyles } from '../../styles/buttonStyles'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const Register = (): ReactElement => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const { t } = useTranslation()

  const [formData, setFormData] = useState<CreateTherapistDTO>({
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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    if (!formData.email || !formData.password) {
      openSnackbar(t('register.fields_required') || 'Both fields are required.', 'warning')
      return
    }

    try {
      await dispatch(registerTherapist(formData)).unwrap()
      openSnackbar(t('register.success') || 'Registration successful.', 'success')
      navigate(getPathFromPage(PAGES.HOME_PAGE))
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    }
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
        <Typography variant='h2'>{t('register.welcome')}</Typography>
        <Typography variant='h4' gutterBottom>
          {t('register.register_therapist')}
        </Typography>
      </div>
      <Container maxWidth='xs'>
        <Box sx={{ textAlign: 'center' }}></Box>
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
          />
          <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '380px', mt: 2 }}>
            {t('register.register')}
          </Button>
        </form>
      </Container>
      <div style={{ display: 'flex', width: '100%', marginTop: '32px', justifyContent: 'center' }}>
        <Button
          onClick={() => navigate(getPathFromPage(PAGES.LOGIN_PAGE))}
          sx={{ ...successButtonStyles, minWidth: '380px' }}
        >
          {t('register.go_login')}
        </Button>
      </div>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={() => setSnackbarOpen(false)}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert
          onClose={() => setSnackbarOpen(false)}
          severity={snackbarSeverity}
          sx={{ width: '100%' }}
        >
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </>
  )
}

export default Register
