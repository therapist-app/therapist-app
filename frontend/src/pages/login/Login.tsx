import { Button, Container, TextField, Typography } from '@mui/material'

import { t } from 'i18next'
import { LoginTherapistDTO } from '../../dto/input/LoginTherapistDTO'
import { useState } from 'react'
import { loginTherapist } from '../../store/therapistSlice'

import { useNavigate } from 'react-router-dom'
import { useAppDispatch } from '../../utils/hooks'

const Login = () => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const [formData, setFormData] = useState<LoginTherapistDTO>({
    email: '',
    password: '',
  })
  const [error, setError] = useState<string | null>(null)

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)

    if (!formData.email || !formData.password) {
      setError('Both fields are required.')
      return
    }
    try {
      await dispatch(loginTherapist(formData)).unwrap()
      navigate(`/`)
    } catch (e) {
      setError(`Failed to login, please try again`)
      console.error('Failed to login', e)
    }
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  return (
    <>
      {' '}
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
          />
          {error && <Typography color='error'>{error}</Typography>}
          <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
            {t('login.login')}
          </Button>
        </form>
      </Container>
      <div style={{ display: 'flex', width: '100%', marginTop: '32px', justifyContent: 'center' }}>
        <Button
          onClick={() => navigate('/register')}
          color='success'
          size='large'
          variant='contained'
        >
          {t('login.go_registration')}
        </Button>
      </div>
    </>
  )
}

export default Login
