import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseComponentDTO,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
} from '../../../api'
import { createExerciseComponent, setAddingExerciseComponent } from '../../../store/exerciseSlice'
import { showError } from '../../../store/errorSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateExerciseTextComponentProps {
  createdExercise(): void
  active: boolean
}

const CreateExerciseTextComponent: React.FC<CreateExerciseTextComponentProps> = (props) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const [exerciseText, setExerciseText] = useState('')
  const { t } = useTranslation()

  const showMessage = (message: string, severity: AlertColor = 'error') =>
    dispatch(showError({ message, severity }))

  const [isCreatingExerciseText, setIsCreatingExerciseText] = useState(false)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setExerciseText(e.target.value)
  }

  const showExerciseTextField = (): void => {
    dispatch(setAddingExerciseComponent(ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text))
    setIsCreatingExerciseText(true)
  }

  const cancel = (): void => {
    setExerciseText('')
    setIsCreatingExerciseText(false)
    dispatch(setAddingExerciseComponent(null))
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()
    try {
      const dto: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text,
        exerciseComponentDescription: exerciseText,
      }
      await dispatch(createExerciseComponent({ createExerciseComponentDTO: dto, file: undefined })).unwrap()
      showMessage(t('exercise.component_created_successfully'), 'success')
      cancel()
      props.createdExercise()
    } catch (err) {
      const msg = handleError(err as AxiosError)
      showMessage(msg, 'error')
    }
  }

  if (!props.active) {
    return null
  }

  return (
    <div>
      {isCreatingExerciseText === false ? (
        <Button sx={commonButtonStyles} onClick={showExerciseTextField}>
          {t('exercise.add_text')}
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            multiline
            label={t('exercise.text')}
            value={exerciseText}
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
            <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '150px', mt: 2 }}>
              <CheckIcon />
            </Button>

            <Button
              variant='contained'
              color='error'
              fullWidth
              sx={{ ...deleteButtonStyles, minWidth: '150px', mt: 2 }}
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

export default CreateExerciseTextComponent
