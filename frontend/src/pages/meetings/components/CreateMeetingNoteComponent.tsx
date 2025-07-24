import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField, Typography } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'

import { CreateMeetingNoteDTO } from '../../../api'
import SpeechToTextComponent from '../../../generalComponents/SpeechRecognitionComponent'
import { createMeetingNote } from '../../../store/meetingSlice'
import { showError } from '../../../store/errorSlice'
import { RootState } from '../../../store/store'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateMeetingNoteComponentProps {
  save(): void
  cancel(): void
  isTranscription: boolean
}

const CreateMeetingNoteComponent: React.FC<CreateMeetingNoteComponentProps> = (props) => {
  const dispatch = useAppDispatch()
  const { t } = useTranslation()

  const showMessage = (message: string, severity: AlertColor = 'error') => {
    dispatch(showError({ message, severity }))
  }

  const selectedMeeting = useSelector((state: RootState) => state.meeting.selectedMeeting)

  const [formData, setFormData] = useState<CreateMeetingNoteDTO>({
    meetingId: selectedMeeting?.id ?? '',
    title: props.isTranscription ? t('meetings.meeting_transcription') : '',
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
      showMessage(t('meetings.note_created_successfully'), 'success')
    } catch (err) {
      const msg = handleError(err as AxiosError)
      showMessage(msg, 'error')
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
      <Typography variant='h5'>{t('meetings.new_note')}</Typography>
      <form
        style={{ display: 'flex', flexDirection: 'column', gap: '5px', maxWidth: '600px' }}
        onSubmit={handleSubmit}
      >
        <TextField name='title' value={formData.title} onChange={handleChange} label='Title' />

        <SpeechToTextComponent
          placeholder={t('meetings.content')}
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
          <Button
            type='submit'
            sx={{
              ...commonButtonStyles,
              minWidth: '280px',
            }}
          >
            <CheckIcon />
          </Button>

          <Button
            onClick={props.cancel}
            sx={{
              ...deleteButtonStyles,
              minWidth: '280px',
            }}
          >
            <ClearIcon />
          </Button>
        </div>
      </form>
    </div>
  )
}

export default CreateMeetingNoteComponent
