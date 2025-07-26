import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, MenuItem, TextField, Typography } from '@mui/material'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'

import { ExerciseComponentOutputDTO, UpdateExerciseComponentDTO } from '../../../api'
import { useNotify } from '../../../hooks/useNotify'
import { deleteExerciseComponent, updateExerciseComponent } from '../../../store/exerciseSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { useAppDispatch } from '../../../utils/hooks'

interface ShowExerciseInputFieldComponentProps {
  exerciseComponent: ExerciseComponentOutputDTO
  numberOfExercises: number
  refresh(): void
  isPrivateField: boolean
}

const ShowExerciseInputFieldComponent: React.FC<ShowExerciseInputFieldComponentProps> = (props) => {
  const inputFieldTranslationKey = props.isPrivateField
    ? 'exercise.privateInput'
    : 'exercise.sharedInput'
  const { exerciseComponent } = props
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const { notifyError, notifySuccess } = useNotify()

  const originalFormData: UpdateExerciseComponentDTO = {
    id: exerciseComponent.id ?? '',
    exerciseComponentDescription: exerciseComponent.exerciseComponentDescription,
    orderNumber: exerciseComponent.orderNumber,
  }

  const [formData, setFormData] = useState<UpdateExerciseComponentDTO>(originalFormData)
  const [isEditing, setIsEditing] = useState(false)

  const arrayOfNumbers: number[] = Array.from({ length: props.numberOfExercises }, (_, i) => i + 1)

  const clickCancel = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setIsEditing(false)
    setFormData({ ...originalFormData })
  }

  const clickEdit = (): void => {
    setIsEditing(true)
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (): Promise<void> => {
    try {
      await dispatch(updateExerciseComponent(formData)).unwrap()
      notifySuccess(t('exercise.component_updated_successfully'))
      setIsEditing(false)
      props.refresh()
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  const clickDelete = async (): Promise<void> => {
    try {
      await dispatch(deleteExerciseComponent(exerciseComponent.id ?? '')).unwrap()
      notifySuccess(t('exercise.component_deleted_successfully'))
      props.refresh()
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  return (
    <div style={{ display: 'flex', gap: '20px', flexDirection: 'column' }}>
      {!isEditing ? (
        <>
          <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
            <Typography variant='h6'>{exerciseComponent.orderNumber}.</Typography>

            <Typography variant='h6'>{t(inputFieldTranslationKey)}</Typography>

            <Button sx={{ minWidth: '10px' }} onClick={clickEdit}>
              <EditIcon style={{ color: 'blue' }} />
            </Button>

            <Button sx={{ minWidth: '10px' }} onClick={clickDelete}>
              <DeleteIcon style={{ color: 'red' }} />
            </Button>
          </div>

          <div style={{ display: 'flex', gap: '5px' }}>
            <Typography>
              <strong>{t('exercise.description')}:</strong>
            </Typography>
            <Typography sx={{ whiteSpace: 'pre-line' }}>
              {exerciseComponent.exerciseComponentDescription}
            </Typography>
          </div>
        </>
      ) : (
        <>
          <div style={{ display: 'flex', gap: 'px', alignItems: 'center' }}>
            <TextField
              select
              sx={{ fontWeight: 'bold', width: '75px' }}
              label={t('exercise.order')}
              name='orderNumber'
              value={formData.orderNumber}
              onChange={handleChange}
            >
              {arrayOfNumbers.map((option) => (
                <MenuItem key={option} value={option}>
                  {option}
                </MenuItem>
              ))}
            </TextField>

            <Button sx={{ ...deleteButtonStyles, marginLeft: '20px' }} onClick={clickCancel}>
              <ClearIcon />
            </Button>

            <Button sx={{ ...commonButtonStyles }} onClick={handleSubmit}>
              <CheckIcon />
            </Button>
          </div>

          <TextField
            multiline
            name='exerciseComponentDescription'
            value={formData.exerciseComponentDescription}
            onChange={handleChange}
            label={t('exercise.description_of_input')}
          />
        </>
      )}
    </div>
  )
}

export default ShowExerciseInputFieldComponent
