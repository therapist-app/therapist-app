import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField, Typography } from '@mui/material'
import { useState } from 'react'
import { useSelector } from 'react-redux'

import { CreateTherapySessionNoteDTO } from '../api'
import { RootState } from '../store/store'
import { createTherapySessionNote } from '../store/therapySessionSlice'
import { useAppDispatch } from '../utils/hooks'

interface CreateTherapySessionNoteComponentProps {
  save(): void
  cancel(): void
}

const CreateTherapySessionNoteComponent: React.FC<CreateTherapySessionNoteComponentProps> = (
  props
) => {
  const dispatch = useAppDispatch()

  const selectedTherapySession = useSelector(
    (state: RootState) => state.therapySession.selectedTherapySession
  )

  const [formData, setFormData] = useState<CreateTherapySessionNoteDTO>({
    therapySessionId: selectedTherapySession?.id ?? '',
    title: '',
    content: '',
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      await dispatch(createTherapySessionNote(formData)).unwrap()
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.save()
    }
  }

  return (
    <div style={{ marginTop: '40px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
      <Typography variant='h5'>New Note:</Typography>
      <form
        style={{ display: 'flex', flexDirection: 'column', gap: '5px', maxWidth: '600px' }}
        onSubmit={handleSubmit}
      >
        <TextField name='title' value={formData.title} onChange={handleChange} label='Title' />

        <TextField
          multiline
          name='content'
          value={formData.content}
          onChange={handleChange}
          label='Content'
        />

        <div
          style={{
            display: 'flex',
            width: '100%',
            gap: '10px',
            marginTop: '10px',
            justifyContent: 'center',
          }}
        >
          <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
            <CheckIcon />
          </Button>

          <Button variant='contained' color='error' fullWidth sx={{ mt: 2 }} onClick={props.cancel}>
            <ClearIcon />
          </Button>
        </div>
      </form>
    </div>
  )
}

export default CreateTherapySessionNoteComponent
