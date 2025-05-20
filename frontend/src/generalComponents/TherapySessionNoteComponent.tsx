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
import { useState } from 'react'

import { TherapySessionNoteOutputDTO, UpdateTherapySessionNoteDTO } from '../api'
import { deleteTherapySessionNote, updateTherapySessionNote } from '../store/therapySessionSlice'
import { useAppDispatch } from '../utils/hooks'

interface TherapySessionNoteComponentProps {
  therapySessionNote: TherapySessionNoteOutputDTO
  delete(): void
}

const TherapySessionNoteComponent: React.FC<TherapySessionNoteComponentProps> = (props) => {
  const [isEditing, setIsEditing] = useState(false)

  const dispatch = useAppDispatch()

  const [originalFormData, setOriginalFormData] = useState<UpdateTherapySessionNoteDTO>({
    id: props?.therapySessionNote.id ?? '',
    title: props?.therapySessionNote.title,
    content: props?.therapySessionNote.content,
  })

  const [formData, setFormData] = useState<UpdateTherapySessionNoteDTO>({
    id: props?.therapySessionNote.id ?? '',
    title: props?.therapySessionNote.title,
    content: props?.therapySessionNote.content,
  })

  const handleSubmit = async (): Promise<void> => {
    try {
      setIsEditing(false)
      const updatedSession = await dispatch(updateTherapySessionNote(formData)).unwrap()
      setOriginalFormData(updatedSession)
    } catch (err) {
      console.error('Registration error:', err)
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

  const deleteNote = async (event: React.MouseEvent<HTMLButtonElement>): Promise<void> => {
    event.stopPropagation()
    try {
      await dispatch(deleteTherapySessionNote(formData.id ?? ''))
    } catch (e) {
      console.error(e)
    } finally {
      props.delete()
    }
  }

  return (
    <div>
      {isEditing === false ? (
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
              <Typography
                sx={{
                  whiteSpace: 'pre-line',
                }}
              >
                {formData.content}
              </Typography>
            </div>
          </AccordionDetails>
        </Accordion>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
          <form
            style={{ display: 'flex', flexDirection: 'column', gap: '15px', maxWidth: '600px' }}
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

                justifyContent: 'center',
              }}
            >
              <Button type='submit' variant='contained' color='primary' fullWidth>
                <CheckIcon />
              </Button>

              <Button variant='contained' color='error' fullWidth onClick={clickCancel}>
                <ClearIcon />
              </Button>
            </div>
          </form>
        </div>
      )}
    </div>
  )
}

export default TherapySessionNoteComponent
