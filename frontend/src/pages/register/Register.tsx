import { useState } from 'react'
import { TextField, Button, Typography, Container, Box } from '@mui/material'
import { CreateTherapistDTO } from '../../dto/input/TherapistInputDTO'
import { useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { registerTherapist } from '../../store/therapistSlice'
import { useAppDispatch } from '../../utils/hooks'

const Register = () => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const { t } = useTranslation()

  const [formData, setFormData] = useState<CreateTherapistDTO>({
    email: '',
    password: '',
  })
  const [error, setError] = useState<string | null>(null)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)

    if (!formData.email || !formData.password) {
      setError('Both fields are required.')
      return
    }

    try {
      await dispatch(registerTherapist(formData)).unwrap()
      navigate(`/`)
    } catch (err) {
      setError('Failed to register therapist. Please try again.')
      console.error('Registration error:', err)
    }
  }

  return (
    <Container maxWidth='xs'>
      <Box sx={{ textAlign: 'center', mt: 4 }}>
        <Typography variant='h4' gutterBottom>
          {t('register.register_therapist')}
        </Typography>
      </Box>
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
        />
        {error && <Typography color='error'>{error}</Typography>}
        <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
          {t('register.register')}
        </Button>
      </form>
    </Container>
  )
}

export default Register
