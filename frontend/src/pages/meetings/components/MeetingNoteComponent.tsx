import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown'
import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Button,
  Divider,
  TextField,
  Typography,
} from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'

import { MeetingNoteOutputDTO, UpdateMeetingNoteDTO } from '../../../api'
import SpeechToTextComponent from '../../../generalComponents/SpeechRecognitionComponent'
import { deleteMeetingNote, updateMeetingNote } from '../../../store/meetingSlice'
import { showError } from '../../../store/errorSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
import { useAppDispatch } from '../../../utils/hooks'

interface MeetingNoteComponentProps {
  meetingNote: MeetingNoteOutputDTO
  delete(): void
}

const MeetingNoteComponent: React.FC<MeetingNoteComponentProps> = (props) => {
  const [isEditing, setIsEditing] = useState(false)
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const showMessage = (message: string, severity: AlertColor = 'error') => {
    dispatch(showError({ message, severity }))
  }

  const [originalFormData, setOriginalFormData] = useState<UpdateMeetingNoteDTO>({
    id: props?.meetingNote.id ?? '',
    title: props?.meetingNote.title,
    content: props?.meetingNote.content,
  })

  const [formData, setFormData] = useState<UpdateMeetingNoteDTO>({
    id: props?.meetingNote.id ?? '',
    title: props?.meetingNote.title,
    content: props?.meetingNote.content,
  })

  const handleSubmit = async (): Promise<void> => {
    try {
      const updatedMeeting = await dispatch(updateMeetingNote(formData)).unwrap()
      setOriginalFormData(updatedMeeting)
      showMessage(t('meetings.note_updated_successfully'), 'success')
      setIsEditing(false)
    } catch (err) {
      const msg = handleError(err as AxiosError)
      showMessage(msg, 'error')
    }
  }

  const clickCancel = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setIsEditing(false)
    setFormData({ ...originalFormData })
  }

  const clickEdit = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setIsEditing(true)
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleContentChange = (newValue: string): void => {
    setFormData({ ...formData, content: newValue })
  }

  const deleteNote = async (event: React.MouseEvent<HTMLButtonElement>): Promise<void> => {
    event.stopPropagation()
    try {
      await dispatch(deleteMeetingNote(formData.id ?? '')).unwrap()
      showMessage(t('meetings.note_deleted_successfully'), 'success')
    } catch (e) {
      const msg = handleError(e as AxiosError)
      showMessage(msg, 'error')
    } finally {
      props.delete()
    }
  }

  return (
    <div>
      {!isEditing ? (
        <Accordion sx={{ maxWidth: '600px' }}>
          <AccordionSummary
            expandIcon={<ArrowDropDownIcon />}
            aria-controls='panel2-content'
            id='panel2-header'
          >
            <div
              style={{
                display: 'flex',
                gap: '10px',
                alignItems: 'center',
                width: '100%',
                minHeight: '30px',
              }}
            >
              <Typography component='span'>{formData.title}</Typography>
              <span
                style={{ cursor: 'pointer', height: '20px', width: '20px' }}
                onClick={clickEdit}
              >
                <EditIcon sx={{ height: '20px', width: '20px' }} style={{ color: 'blue' }} />
              </span>
              <span
                style={{
                  cursor: 'pointer',
                  marginLeft: 'auto',
                  marginRight: '20px',
                  height: '20px',
                  width: '20px',
                }}
                onClick={deleteNote}
              >
                <DeleteIcon sx={{ height: '20px', width: '20px' }} style={{ color: 'red' }} />
              </span>
            </div>
          </AccordionSummary>
          <Divider />
          <AccordionDetails sx={{ padding: '16px' }}>
            <div style={{ display: 'flex', gap: '20px', alignItems: 'center', minHeight: '40px' }}>
              <Typography sx={{ whiteSpace: 'pre-line' }}>{formData.content}</Typography>
            </div>
          </AccordionDetails>
        </Accordion>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
          <form
            style={{ display: 'flex', flexDirection: 'column', gap: '15px', maxWidth: '600px' }}
            onSubmit={(e) => {
              e.preventDefault()
              handleSubmit()
            }}
          >
            <TextField name='title' value={formData.title} onChange={handleChange} label='Title' />

            <SpeechToTextComponent
              placeholder={t('meetings.content')}
              value={formData.content ?? ''}
              onChange={handleContentChange}
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
                onClick={clickCancel}
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
      )}
    </div>
  )
}

export default MeetingNoteComponent
