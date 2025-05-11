import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown'
import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
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
  const [editingTitle, setEditingTitle] = useState(false)
  const [editingContent, setEditingContent] = useState(false)
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
      const updatedSession = await dispatch(updateTherapySessionNote(formData)).unwrap()
      setOriginalFormData(updatedSession)
    } catch (err) {
      console.error('Registration error:', err)
    }
  }

  const clickSaveTitle = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setEditingTitle(false)
    handleSubmit()
  }

  const clickCancelTitle = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setEditingTitle(false)
    setFormData({ ...formData, title: originalFormData.title })
  }

  const clickSaveContent = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setEditingContent(false)
    handleSubmit()
  }

  const clickCancelContent = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setEditingContent(false)
    setFormData({ ...formData, content: originalFormData.content })
  }

  const clickEditTitle = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setEditingTitle(true)
  }

  const clickEditContent = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setEditingContent(true)
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
      <Accordion>
        <AccordionSummary
          expandIcon={<ArrowDropDownIcon />}
          aria-controls='panel2-content'
          id='panel2-header'
        >
          {editingTitle === true ? (
            <div style={{ display: 'flex', gap: '10px', alignItems: 'center', minHeight: '30px' }}>
              <TextField
                name='title'
                value={formData.title}
                onChange={handleChange}
                label='Title'
              />
              <span
                style={{ cursor: 'pointer', height: '20px', width: '20px' }}
                onClick={clickSaveTitle}
              >
                <CheckIcon style={{ color: 'green' }} />
              </span>

              <span
                style={{ cursor: 'pointer', height: '20px', width: '20px' }}
                onClick={clickCancelTitle}
              >
                {' '}
                <ClearIcon style={{ color: 'red' }} />
              </span>
            </div>
          ) : (
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
                onClick={clickEditTitle}
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
          )}
        </AccordionSummary>
        <Divider />
        <AccordionDetails sx={{ padding: '16px' }}>
          {editingContent === true ? (
            <div style={{ display: 'flex', gap: '10px', alignItems: 'center', minHeight: '40px' }}>
              <TextField
                multiline
                name='content'
                value={formData.content}
                onChange={handleChange}
                label='Content'
                fullWidth
              />
              <span
                style={{ cursor: 'pointer', height: '20px', width: '20px' }}
                onClick={clickSaveContent}
              >
                <CheckIcon style={{ color: 'green' }} />
              </span>

              <span
                style={{ cursor: 'pointer', height: '20px', width: '20px' }}
                onClick={clickCancelContent}
              >
                <ClearIcon style={{ color: 'red' }} />
              </span>
            </div>
          ) : (
            <div style={{ display: 'flex', gap: '20px', alignItems: 'center', minHeight: '40px' }}>
              <Typography
                sx={{
                  whiteSpace: 'pre-line',
                }}
              >
                {formData.content}
              </Typography>
              <span
                style={{ cursor: 'pointer', height: '20px', width: '20px' }}
                onClick={clickEditContent}
              >
                <EditIcon sx={{ height: '20px', width: '20px' }} style={{ color: 'blue' }} />
              </span>
            </div>
          )}
        </AccordionDetails>
      </Accordion>
    </div>
  )
}

export default TherapySessionNoteComponent
