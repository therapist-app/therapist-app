import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, MenuItem, TextField, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { ExerciseComponentOutputDTO, UpdateExerciseComponentDTO } from '../../../api'
import FileDownload from '../../../generalComponents/FileDownload'
import { useNotify } from '../../../hooks/useNotify'
import {
  deleteExerciseComponent,
  downloadExerciseComponent,
  updateExerciseComponent,
} from '../../../store/exerciseSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { useAppDispatch } from '../../../utils/hooks'

interface ShowExerciseFileComponentProps {
  exerciseComponent: ExerciseComponentOutputDTO
  numberOfExercises: number
  isImageComponent: boolean
  refresh(): void
}

const ShowExerciseFileComponent: React.FC<ShowExerciseFileComponentProps> = (props) => {
  const { exerciseComponent, isImageComponent } = props
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const [imageFileUrl, setImageFileUrl] = useState<string>()
  const [isEditing, setIsEditing] = useState(false)
  const { notifyError, notifySuccess } = useNotify()

  useEffect(() => {
    ;(async () => {
      try {
        const fileUrl = await dispatch(
          downloadExerciseComponent(exerciseComponent.id ?? '')
        ).unwrap()
        setImageFileUrl(fileUrl)
      } catch (err) {
        notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      }
    })()
  }, [dispatch, exerciseComponent.id])

  const originalFormData: UpdateExerciseComponentDTO = {
    id: exerciseComponent.id ?? '',
    exerciseComponentDescription: exerciseComponent.exerciseComponentDescription,
    orderNumber: exerciseComponent.orderNumber,
  }

  const [formData, setFormData] = useState<UpdateExerciseComponentDTO>(originalFormData)

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

            <Typography variant='h6'>
              {isImageComponent ? t('exercise.image') : t('exercise.file')}
            </Typography>

            <Button sx={{ minWidth: '10px' }} onClick={clickEdit}>
              <EditIcon style={{ color: 'blue' }} />
            </Button>

            <FileDownload
              download={() =>
                dispatch(downloadExerciseComponent(exerciseComponent.id ?? '')).unwrap()
              }
              fileName={exerciseComponent.fileName ?? ''}
            />

            <Button sx={{ minWidth: '10px' }} onClick={clickDelete}>
              <DeleteIcon style={{ color: 'red' }} />
            </Button>
          </div>

          {isImageComponent ? (
            <img
              src={imageFileUrl}
              alt='Exercise'
              style={{ width: '300px', height: '300px', objectFit: 'contain' }}
            />
          ) : (
            <Typography sx={{ fontWeight: 'bold' }}>{exerciseComponent.fileName}</Typography>
          )}

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

          {isImageComponent ? (
            <img src={imageFileUrl} alt='Exercise' style={{ maxWidth: '100%' }} />
          ) : (
            <Typography sx={{ fontWeight: 'bold' }}>{exerciseComponent.fileName}</Typography>
          )}

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

export default ShowExerciseFileComponent
