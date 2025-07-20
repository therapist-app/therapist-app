import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField } from '@mui/material'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseComponentDTO,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
} from '../../../api'
import { createExerciseComponent, setAddingExerciseComponent } from '../../../store/exerciseSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateExerciseInputFieldComponentProps {
  createdInputField(): void
  active: boolean
  isPrivateField: boolean
}

const CreateExerciseInputFieldComponent: React.FC<CreateExerciseInputFieldComponentProps> = (
  props: CreateExerciseInputFieldComponentProps
) => {
  const exerciseComponentType = props.isPrivateField
    ? ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldPrivate
    : ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldShared
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const [description, setDescription] = useState('')

  const [isCreatingExerciseInputField, setIsCreatingExerciseInputField] = useState(false)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setDescription(e.target.value)
  }

  const showExerciseInputField = (): void => {
    dispatch(setAddingExerciseComponent(exerciseComponentType))

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
        exerciseComponentType: exerciseComponentType,
        exerciseComponentDescription: description,
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
        <Button sx={{ ...commonButtonStyles, minWidth: '160px' }} onClick={showExerciseInputField}>
          {t('exercise.add_input_field')}
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            multiline
            label={t('exercise.description_of_input')}
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
            <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '180px', mt: 2 }}>
              <CheckIcon />
            </Button>

            <Button
              variant='contained'
              color='error'
              fullWidth
              sx={{ ...deleteButtonStyles, minWidth: '180px', mt: 2 }}
              onClick={cancel}
            >
              <ClearIcon />
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateExerciseInputFieldComponent
