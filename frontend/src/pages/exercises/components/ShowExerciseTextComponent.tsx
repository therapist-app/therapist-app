import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, MenuItem, TextField, Typography } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'

import { ExerciseComponentOutputDTO, UpdateExerciseComponentDTO } from '../../../api'
import { deleteExerciseComponent, updateExerciseComponent } from '../../../store/exerciseSlice'
import { showError } from '../../../store/errorSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
import { useAppDispatch } from '../../../utils/hooks'

interface ShowExerciseTextComponentProps {
  exerciseComponent: ExerciseComponentOutputDTO
  numberOfExercises: number
  refresh(): void
}

const ShowExerciseTextComponent: React.FC<ShowExerciseTextComponentProps> = (props) => {
  const { exerciseComponent } = props
  const dispatch = useAppDispatch()
  const { t } = useTranslation()

  const showMessage = (message: string, severity: AlertColor = 'error') =>
    dispatch(showError({ message, severity }))

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
      showMessage(t('exercise.component_updated_successfully'), 'success')
      setIsEditing(false)
      props.refresh()
    } catch (err) {
      const msg = handleError(err as AxiosError)
      showMessage(msg, 'error')
    }
  }

  const clickDelete = async (): Promise<void> => {
    try {
      await dispatch(deleteExerciseComponent(exerciseComponent.id ?? '')).unwrap()
      showMessage(t('exercise.component_deleted_successfully'), 'success')
      props.refresh()
    } catch (err) {
      const msg = handleError(err as AxiosError)
      showMessage(msg, 'error')
    }
  }

  return (
    <div style={{ display: 'flex', gap: '20px', flexDirection: 'column' }}>
      {!isEditing ? (
        <>
          <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
            <Typography variant='h6'>{exerciseComponent.orderNumber}.</Typography>

            <Typography variant='h6'>{t('exercise.text')}</Typography>

            <Button sx={{ minWidth: '10px' }} onClick={clickEdit}>
              <EditIcon style={{ color: 'blue' }} />
            </Button>

            <Button sx={{ minWidth: '10px' }} onClick={clickDelete}>
              <DeleteIcon style={{ color: 'red' }} />
            </Button>
          </div>

          <Typography sx={{ whiteSpace: 'pre-line' }}>
            {exerciseComponent.exerciseComponentDescription}
          </Typography>
        </>
      ) : (
        <>
          <div style={{ display: 'flex', gap: '5px', alignItems: 'center' }}>
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

            <Button
              sx={{ ...commonButtonStyles, minWidth: '280px', marginLeft: '20px' }}
              onClick={clickCancel}
            >
              <ClearIcon style={{ color: 'red' }} />
            </Button>

            <Button sx={{ ...deleteButtonStyles, minWidth: '280px' }} onClick={handleSubmit}>
              <CheckIcon style={{ color: 'green' }} />
            </Button>
          </div>

          <TextField
            multiline
            name='exerciseComponentDescription'
            value={formData.exerciseComponentDescription}
            onChange={handleChange}
            label={t('exercise.text')}
          />
        </>
      )}
    </div>
  )
}

export default ShowExerciseTextComponent
