import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField, Typography } from '@mui/material'
import { useState } from 'react'
import { useSelector } from 'react-redux'

import { CreateMeetingNoteDTO } from '../../../api'
import SpeechToTextComponent from '../../../generalComponents/SpeechRecognitionComponent'
import { createMeetingNote } from '../../../store/meetingSlice'
import { RootState } from '../../../store/store'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateMeetingNoteComponentProps {
  save(): void
  cancel(): void
  isTranscription: boolean
}

const CreateMeetingNoteComponent: React.FC<CreateMeetingNoteComponentProps> = (props) => {
  const dispatch = useAppDispatch()

  const selectedMeeting = useSelector((state: RootState) => state.meeting.selectedMeeting)

  const [formData, setFormData] = useState<CreateMeetingNoteDTO>({
    meetingId: selectedMeeting?.id ?? '',
    title: props.isTranscription ? 'Meeting Transcription' : '',
    content: '',
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleContentChange = (newValue: string): void => {
    setFormData({ ...formData, content: newValue })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      await dispatch(createMeetingNote(formData)).unwrap()
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.save()
    }
  }

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        gap: '10px',
        border: '1px solid black',
        padding: '20px',
        borderRadius: '20px',
        maxWidth: '700px',
      }}
    >
      <Typography variant='h5'>New Note:</Typography>
      <form
        style={{ display: 'flex', flexDirection: 'column', gap: '5px', maxWidth: '600px' }}
        onSubmit={handleSubmit}
      >
        <TextField name='title' value={formData.title} onChange={handleChange} label='Title' />

        <SpeechToTextComponent
          placeholder='Content (Type or Speak)'
          value={formData.content ?? ''}
          onChange={handleContentChange}
          startDirectly={props.isTranscription}
        />

        <div
          style={{
            display: 'flex',
            width: '100%',
            gap: '10px',

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

export default CreateMeetingNoteComponent
