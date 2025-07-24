import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField, Typography } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
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
import { showError } from '../../../store/errorSlice'
import { createExerciseComponent, setAddingExerciseComponent } from '../../../store/exerciseSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateExerciseFileComponentProps {
  createdExerciseFile(): void
  isImageComponent: boolean
  active: boolean
}

const CreateExerciseFileComponent: React.FC<CreateExerciseFileComponentProps> = (props) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()

  const [isCreatingFile, setIsCreatingFile] = useState(false)
  const [description, setDescription] = useState('')
  const [selectedFile, setSelectedFile] = useState<File | undefined>(undefined)

  const { notifyError, notifySuccess } = useNotify()

  const saveSelectedFile = (file: File): void => {
    setSelectedFile(file)
    setIsCreatingFile(true)
    if (props.isImageComponent) {
      dispatch(
        setAddingExerciseComponent(ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image)
      )
    } else {
      dispatch(setAddingExerciseComponent(ExerciseComponentOutputDTOExerciseComponentTypeEnum.File))
    }
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
      let exerciseComponentType: CreateExerciseComponentDTOExerciseComponentTypeEnum =
        CreateExerciseComponentDTOExerciseComponentTypeEnum.File
      if (props.isImageComponent) {
        exerciseComponentType = CreateExerciseComponentDTOExerciseComponentTypeEnum.Image
      }

      const dto: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: exerciseComponentType,
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
      props.createdExerciseFile()
    } catch (err) {
      const msg = handleError(err as AxiosError)
      notifyError(msg)
    }
  }

  if (!props.active) {
    return null
  }

  return (
    <div>
      {!isCreatingFile ? (
        props.isImageComponent ? (
          <FileUpload
            onUpload={saveSelectedFile}
            text={t('exercise.upload_image')}
            accept='image/*'
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
          {props.isImageComponent ? (
            <ImageComponent imageFile={selectedFile} />
          ) : (
            <Typography>{selectedFile?.name}</Typography>
          )}

          <TextField
            label={t('exercise.description')}
            value={description}
            onChange={handleChangeDescription}
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
              fullWidth
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
