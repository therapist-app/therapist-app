import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import { Button, TextField, Typography } from '@mui/material'
import { useState } from 'react'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseComponentDTO,
  CreateExerciseComponentDTOExerciseComponentTypeEnum,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
} from '../api'
import { createExerciseComponent, setAddingExerciseComponent } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'
import FileUpload from './FileUpload'
import ImageComponent from './ImageComponent'

interface CreateExerciseFileComponentProps {
  createdExerciseFile(): void
  isImageComponent: boolean
  active: boolean
}

const CreateExerciseFileComponent: React.FC<CreateExerciseFileComponentProps> = (
  props: CreateExerciseFileComponentProps
) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()

  const [isCreatingFile, setIsCreatingFile] = useState(false)
  const [description, setDescription] = useState('')
  const [selectedFile, setSelectedFile] = useState<File | undefined>(undefined)

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
      const createExerciseComponentDTO: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: exerciseComponentType,
        description: description,
      }
      await dispatch(
        createExerciseComponent({
          createExerciseComponentDTO: createExerciseComponentDTO,
          file: selectedFile,
        })
      )
      cancel()
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.createdExerciseFile()
    }
  }

  if (!props.active) {
    return null
  }

  return (
    <div>
      {!isCreatingFile ? (
        props.isImageComponent ? (
          <FileUpload onUpload={saveSelectedFile} text='Upload Image' accept='image/*' />
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

          <TextField label='Description' value={description} onChange={handleChangeDescription} />
          <div
            style={{
              display: 'flex',
              gap: '10px',
              justifyContent: 'center',
            }}
          >
            <Button onClick={handleSubmit} variant='contained' color='primary' fullWidth>
              <CheckIcon />
            </Button>

            <Button variant='contained' color='error' fullWidth onClick={cancel}>
              <ClearIcon />
            </Button>
          </div>
        </div>
      )}
    </div>
  )
}

export default CreateExerciseFileComponent
