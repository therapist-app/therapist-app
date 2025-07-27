import { Button, Container, TextField, Typography } from '@mui/material'
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
        <Typography variant='h2'> {t('register.welcome')}</Typography>
        <Typography variant='h4' gutterBottom>
          {t('register.register_therapist')}
        </Typography>
      </div>
      <Container maxWidth='xs'>
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
      <GlobalErrorSnackbar />
    </>
  )
}

export default Register
