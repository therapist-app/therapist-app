import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField } from '@mui/material'
import { useState } from 'react'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseComponentDTO,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
} from '../api'
import { createExerciseComponent, setAddingExerciseComponent } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'

interface CreateExerciseInputFieldComponentProps {
  createdInputField(): void
  active: boolean
}

const CreateExerciseInputFieldComponent: React.FC<CreateExerciseInputFieldComponentProps> = (
  props: CreateExerciseInputFieldComponentProps
) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const [description, setDescription] = useState('')

  const [isCreatingExerciseInputField, setIsCreatingExerciseInputField] = useState(false)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setDescription(e.target.value)
  }

  const showExerciseInputField = (): void => {
    dispatch(
      setAddingExerciseComponent(ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputField)
    )
    setIsCreatingExerciseInputField(true)
  }

  const cancel = (): void => {
    setDescription('')
    setIsCreatingExerciseInputField(false)
    dispatch(setAddingExerciseComponent(null))
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      const createExerciseComponentDTO: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputField,
        description: description,
      }
      await dispatch(
        createExerciseComponent({
          createExerciseComponentDTO: createExerciseComponentDTO,
          file: undefined,
        })
      )
      cancel()
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.createdInputField()
    }
  }

  if (!props.active) {
    return null
  }

  return (
    <div>
      {isCreatingExerciseInputField === false ? (
        <Button variant='contained' color='primary' onClick={showExerciseInputField}>
          Add Input Field
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            multiline
            label='Description of Input'
            value={description}
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

            <Button variant='contained' color='error' fullWidth sx={{ mt: 2 }} onClick={cancel}>
              <ClearIcon />
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateExerciseInputFieldComponent
