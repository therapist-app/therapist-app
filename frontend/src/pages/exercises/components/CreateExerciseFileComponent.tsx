import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField, Typography } from '@mui/material'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseComponentDTO,
  CreateExerciseComponentDTOExerciseComponentTypeEnum,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
} from '../../../api'
import FileUpload from '../../../generalComponents/FileUpload'
import ImageComponent from '../../../generalComponents/ImageComponent'
import { useNotify } from '../../../hooks/useNotify'
import { createExerciseComponent, setAddingExerciseComponent } from '../../../store/exerciseSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateExerciseFileComponentProps {
  createdExerciseFile(): void
  isImageComponent: boolean
  active: boolean
}

const MAX_FILE_SIZE_BYTES = 0.75 * 1024 * 1024
const ALLOWED_IMAGE_TYPES = ['image/png', 'image/jpeg']

const CreateExerciseFileComponent: React.FC<CreateExerciseFileComponentProps> = ({
  createdExerciseFile,
  isImageComponent,
  active,
}) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()

  const [isCreatingFile, setIsCreatingFile] = useState(false)
  const [description, setDescription] = useState('')
  const [selectedFile, setSelectedFile] = useState<File | undefined>(undefined)

  const { notifyError, notifySuccess } = useNotify()

  const saveSelectedFile = (file: File): void => {
    if (isImageComponent && !ALLOWED_IMAGE_TYPES.includes(file.type)) {
      notifyError(t('errors.unsupported_file_type'))
      return
    }

    if (file.size > MAX_FILE_SIZE_BYTES) {
      notifyError(t('exercise.file_too_large', { max: '750 KB' }))
      return
    }

    setSelectedFile(file)
    setIsCreatingFile(true)

    dispatch(
      setAddingExerciseComponent(
        isImageComponent
          ? ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image
          : ExerciseComponentOutputDTOExerciseComponentTypeEnum.File
      )
    )
  }

  const handleChangeDescription = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setDescription(e.target.value)
  }

  const cancel = (): void => {
    setIsCreatingFile(false)
    setDescription('')
    setSelectedFile(undefined)
    dispatch(setAddingExerciseComponent(null))
  }

  const handleSubmit = async (): Promise<void> => {
    try {
      const dto: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: isImageComponent
          ? CreateExerciseComponentDTOExerciseComponentTypeEnum.Image
          : CreateExerciseComponentDTOExerciseComponentTypeEnum.File,
        exerciseComponentDescription: description,
      }

      await dispatch(
        createExerciseComponent({
          createExerciseComponentDTO: dto,
          file: selectedFile,
        })
      ).unwrap()

      notifySuccess(t('exercise.component_created_successfully'))
      cancel()
      createdExerciseFile()
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  if (!active) {
    return null
  }

  return (
    <div>
      {!isCreatingFile ? (
        isImageComponent ? (
          <FileUpload
            onUpload={saveSelectedFile}
            text={t('exercise.upload_image')}
            accept='image/png,image/jpeg'
          />
        ) : (
          <FileUpload onUpload={saveSelectedFile} />
        )
      ) : (
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: '20px',
            border: '1px solid black',
            padding: '10px',
            borderRadius: '10px',
            maxWidth: '500px',
          }}
        >
          {isImageComponent ? (
            <ImageComponent imageFile={selectedFile} />
          ) : (
            <Typography>{selectedFile?.name}</Typography>
          )}

          <TextField
            label={t('exercise.description')}
            value={description}
            onChange={handleChangeDescription}
            fullWidth
          />

          <div
            style={{
              display: 'flex',
              gap: '10px',
              justifyContent: 'center',
            }}
          >
            <Button onClick={handleSubmit} sx={{ ...commonButtonStyles, minWidth: '180px' }}>
              <CheckIcon />
            </Button>

            <Button
              variant='contained'
              color='error'
              sx={{ ...deleteButtonStyles, minWidth: '180px' }}
              onClick={cancel}
            >
              <ClearIcon />
            </Button>
          </div>
        </div>
      )}
    </div>
  )
}

export default CreateExerciseFileComponent
