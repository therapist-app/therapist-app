import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField, Typography } from '@mui/material'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'

import { CreateMeetingNoteDTO } from '../../../api'
import SpeechToTextComponent from '../../../generalComponents/SpeechRecognitionComponent'
import { useNotify } from '../../../hooks/useNotify'
import { createMeetingNote } from '../../../store/meetingSlice'
import { RootState } from '../../../store/store'
import { deleteButtonStyles, successButtonStyles } from '../../../styles/buttonStyles'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateMeetingNoteComponentProps {
  save(): void
  cancel(): void
  isTranscription: boolean
}

const CreateMeetingNoteComponent: React.FC<CreateMeetingNoteComponentProps> = (props) => {
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const { notifyError, notifySuccess } = useNotify()

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
      notifySuccess(t('meetings.note_created_successfully'))
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
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
      }}
    >
      <Typography variant='h5'>{t('meetings.new_note')}</Typography>
      <form
        style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}
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
              ...successButtonStyles,
            }}
          >
            <CheckIcon />
          </Button>

          <Button
            onClick={props.cancel}
            sx={{
              ...deleteButtonStyles,
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
