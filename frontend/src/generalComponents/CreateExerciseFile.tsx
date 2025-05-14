import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useParams } from 'react-router-dom'

import { CreateExerciseFileDTO, CreateExerciseTextDTO } from '../api'
import { createExerciseFile, createExerciseText } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'
import FileUpload from './FileUpload'

interface CreateExerciseFileProps {
  createdExerciseFile(): void
}

const CreateExerciseFile: React.FC<CreateExerciseFileProps> = (props: CreateExerciseFileProps) => {
  const { patientId, therapySessionId, exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const [exerciseText, setExerciseText] = useState('')

  const [isCreatingExerciseText, setIsCreatingExerciseText] = useState(false)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setExerciseText(e.target.value)
  }

  const showExerciseTextField = (): void => {
    setIsCreatingExerciseText(true)
  }

  const showCreateExerciseButton = (): void => {
    setExerciseText('')
    setIsCreatingExerciseText(false)
  }

  const handleUpload = async (file: File): Promise<void> => {
    try {
      const createExerciseFileDTO: CreateExerciseFileDTO = {
        exerciseId: exerciseId ?? '',
        description: 'Test',
      }
      await dispatch(
        createExerciseFile({ createExerciseFileDTO: createExerciseFileDTO, file: file })
      )
      showCreateExerciseButton()
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.createdExerciseFile()
    }
  }

  return (
    <div>
      {isCreatingExerciseText === false ? (
        <Button onClick={showExerciseTextField}>Add Text</Button>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', maxWidth: '600px' }}>
          <FileUpload onUpload={handleUpload} />
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

            <Button
              variant='contained'
              color='error'
              fullWidth
              sx={{ mt: 2 }}
              onClick={showCreateExerciseButton}
            >
              <ClearIcon />
            </Button>
          </div>
        </div>
      )}
    </div>
  )
}

export default CreateExerciseFile
