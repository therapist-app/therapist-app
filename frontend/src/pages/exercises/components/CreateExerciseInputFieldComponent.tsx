import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField } from '@mui/material'
import { AxiosError } from 'axios'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseComponentDTO,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
} from '../../../api'
import { useNotify } from '../../../hooks/useNotify'
import { createExerciseComponent, setAddingExerciseComponent } from '../../../store/exerciseSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateExerciseInputFieldComponentProps {
  createdInputField(): void
  active: boolean
  isPrivateField: boolean
}

const CreateExerciseInputFieldComponent: React.FC<CreateExerciseInputFieldComponentProps> = (
  props
) => {
  const inputFieldTranslationKey = props.isPrivateField
    ? 'exercise.addPrivateInput'
    : 'exercise.addSharedInput'

  const exerciseComponentType = props.isPrivateField
    ? ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldPrivate
    : ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldShared

  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const [description, setDescription] = useState('')
  const { notifyError, notifySuccess } = useNotify()
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
      const dto: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: exerciseComponentType,
        exerciseComponentDescription: description,
      }
      await dispatch(
        createExerciseComponent({ createExerciseComponentDTO: dto, file: undefined })
      ).unwrap()
      notifySuccess(t('exercise.component_created_successfully'))
      cancel()
      props.createdInputField()
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  if (!props.active) {
    return null
  }

  return (
    <div>
      {isCreatingExerciseInputField === false ? (
        <Button sx={{ ...commonButtonStyles, minWidth: '160px' }} onClick={showExerciseInputField}>
          {t(inputFieldTranslationKey)}
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
          />
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
