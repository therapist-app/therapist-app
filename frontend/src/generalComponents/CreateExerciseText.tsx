import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useParams } from 'react-router-dom'

import { CreateExerciseTextDTO } from '../api'
import { createExerciseText } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'

interface CreateExerciseTextProps {
  createdExercise(): void
}

const CreateExerciseText: React.FC<CreateExerciseTextProps> = (props: CreateExerciseTextProps) => {
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

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      const createExerciseTextDTO: CreateExerciseTextDTO = {
        exerciseId: exerciseId ?? '',
        text: exerciseText,
      }
      await dispatch(createExerciseText(createExerciseTextDTO))
      showCreateExerciseButton()
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.createdExercise()
    }
  }

  return (
    <div>
      {isCreatingExerciseText === false ? (
        <Button variant='contained' color='primary' onClick={showExerciseTextField}>
          Add Text
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '5px', maxWidth: '600px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            multiline
            label='Text'
            value={exerciseText}
            onChange={handleChange}
          ></TextField>
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
        </form>
      )}
    </div>
  )
}

export default CreateExerciseText
