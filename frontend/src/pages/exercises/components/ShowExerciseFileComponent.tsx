import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, MenuItem, TextField, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
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
  isViewMode: boolean
}

const ShowExerciseFileComponent: React.FC<ShowExerciseFileComponentProps> = ({
  exerciseComponent,
  numberOfExercises,
  isImageComponent,
  refresh,
  isViewMode,
}) => {
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const { notifyError, notifySuccess } = useNotify()

  const [imageUrl, setImageUrl] = useState<string>()
  const [isEditing, setIsEditing] = useState<boolean>(false)
  const [isDeleted, setIsDeleted] = useState<boolean>(false)

  const originalFormData: UpdateExerciseComponentDTO = {
    id: exerciseComponent.id ?? '',
    exerciseComponentDescription: exerciseComponent.exerciseComponentDescription,
    orderNumber: exerciseComponent.orderNumber,
  }
  const [formData, setFormData] = useState<UpdateExerciseComponentDTO>(originalFormData)
  const orderOptions = Array.from({ length: numberOfExercises }, (_, i) => i + 1)

  useEffect((): (() => void) | void => {
    if (!isImageComponent || isDeleted) {
      return
    }

    let cancelled = false

    ;(async (): Promise<void> => {
      try {
        const url = await dispatch(downloadExerciseComponent(exerciseComponent.id!)).unwrap()
        if (!cancelled) {
          setImageUrl(url)
        }
      } catch (err: unknown) {
        if (cancelled) {
          return
        }

        const typed = err as { status?: number; message?: string }
        if (typed?.status === 404) {
          return
        }

        notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      }
    })()

    return (): void => {
      cancelled = true
    }
  }, [dispatch, exerciseComponent.id, isImageComponent, isDeleted, notifyError])

  const handleEditToggle = (): void => setIsEditing(true)

  const handleFieldChange = (e: React.ChangeEvent<HTMLInputElement>): void =>
    setFormData({ ...formData, [e.target.name]: e.target.value })

  const handleCancel = (): void => {
    setIsEditing(false)
    setFormData({ ...originalFormData })
  }

  const handleUpdate = async (): Promise<void> => {
    try {
      await dispatch(updateExerciseComponent(formData)).unwrap()
      notifySuccess(t('exercise.component_updated_successfully'))
      setIsEditing(false)
      refresh()
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  const handleDelete = async (): Promise<void> => {
    try {
      await dispatch(deleteExerciseComponent(exerciseComponent.id!)).unwrap()
      setIsDeleted(true)
      notifySuccess(t('exercise.component_deleted_successfully'))
      refresh()
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  if (isDeleted) {
    return null
  }

  if (isViewMode) {
    return (
      <div>
        {isImageComponent ? (
          <img src={imageUrl} alt='Exercise' style={{ width: 560, objectFit: 'contain' }} />
        ) : (
          <div style={{ display: 'flex', gap: 10, alignItems: 'center' }}>
            <Typography sx={{ fontWeight: 'bold' }}>{exerciseComponent.fileName}</Typography>
            <FileDownload
              download={() => dispatch(downloadExerciseComponent(exerciseComponent.id!)).unwrap()}
              fileName={exerciseComponent.fileName ?? ''}
            />
          </div>
        )}

        <Typography sx={{ whiteSpace: 'pre-line' }}>
          {exerciseComponent.exerciseComponentDescription}
        </Typography>
      </div>
    )
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 20 }}>
      {!isEditing ? (
        <>
          <div style={{ display: 'flex', gap: 10, alignItems: 'center' }}>
            <Typography variant='h6'>{exerciseComponent.orderNumber}.</Typography>
            <Typography variant='h6'>
              {isImageComponent ? t('exercise.image') : t('exercise.file')}
            </Typography>

            <Button sx={{ minWidth: 10 }} onClick={handleEditToggle}>
              <EditIcon sx={{ color: 'blue' }} />
            </Button>

            <FileDownload
              download={() => dispatch(downloadExerciseComponent(exerciseComponent.id!)).unwrap()}
              fileName={exerciseComponent.fileName ?? ''}
            />

            <Button sx={{ minWidth: 10 }} onClick={handleDelete}>
              <DeleteIcon sx={{ color: 'red' }} />
            </Button>
          </div>

          <div style={{ display: 'flex', gap: 5 }}>
            <Typography sx={{ fontWeight: 'bold' }}>{t('exercise.description')}:</Typography>
            <Typography sx={{ whiteSpace: 'pre-line' }}>
              {exerciseComponent.exerciseComponentDescription}
            </Typography>
          </div>

          {isImageComponent ? (
            <img src={imageUrl} alt='Exercise' style={{ width: 560, objectFit: 'contain' }} />
          ) : (
            <Typography sx={{ fontWeight: 'bold' }}>{exerciseComponent.fileName}</Typography>
          )}
        </>
      ) : (
        <>
          <div style={{ display: 'flex', gap: 5, alignItems: 'center' }}>
            <TextField
              select
              label={t('exercise.order')}
              name='orderNumber'
              value={formData.orderNumber}
              onChange={handleFieldChange}
              sx={{ width: 75, fontWeight: 'bold' }}
            >
              {orderOptions.map((n) => (
                <MenuItem key={n} value={n}>
                  {n}
                </MenuItem>
              ))}
            </TextField>

            <Button sx={{ ...deleteButtonStyles, ml: 2 }} onClick={handleCancel}>
              <ClearIcon />
            </Button>

            <Button sx={{ ...commonButtonStyles }} onClick={handleUpdate}>
              <CheckIcon />
            </Button>
          </div>

          <TextField
            multiline
            name='exerciseComponentDescription'
            label={t('exercise.description')}
            value={formData.exerciseComponentDescription}
            onChange={handleFieldChange}
          />

          {isImageComponent ? (
            <img src={imageUrl} alt='Exercise' style={{ width: 560, objectFit: 'contain' }} />
          ) : (
            <Typography sx={{ fontWeight: 'bold' }}>{exerciseComponent.fileName}</Typography>
          )}
        </>
      )}
    </div>
  )
}

export default ShowExerciseFileComponent
