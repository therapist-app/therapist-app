import { useState } from 'react'
import { TextField, Button, Typography, Container, Box } from '@mui/material'
import { CreateTherapistDTO } from '../../dto/input/TherapistInputDTO'
import { createTherapist } from '../../services/therapistService'
import { useNavigate } from 'react-router-dom'

const Register = () => {
  const navigate = useNavigate()

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
      const therapistResponse = await createTherapist(formData)

      sessionStorage.setItem('therapistId', therapistResponse.id)
      sessionStorage.setItem('workspaceId', therapistResponse.workspaceId)

      navigate(`/?workspace_id=${therapistResponse.workspaceId}`)
    } catch (err) {
      setError('Failed to register therapist. Please try again.')
      console.error('Registration error:', err)
    }
  }

  return (
    <Container maxWidth="xs">
      <Box sx={{ textAlign: 'center', mt: 4 }}>
        <Typography variant='h4' gutterBottom>
          Register Therapist
        </Typography>
      </Box>
      <form onSubmit={handleSubmit}>
        <TextField
          label='Email'
          name='email'
          type='email'
          value={formData.email}
          onChange={handleChange}
          fullWidth
          margin='normal'
          required
        />
        <TextField
          label='Password'
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
          Register
        </Button>
      </form>
    </Container>
  )
}

export default Register
