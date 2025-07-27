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
import YoutubeIframe from '../../../generalComponents/YoutubeIframe'
import { useNotify } from '../../../hooks/useNotify'
import { createExerciseComponent, setAddingExerciseComponent } from '../../../store/exerciseSlice'
import { commonButtonStyles, deleteButtonStyles } from '../../../styles/buttonStyles'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateExerciseYoutubeVideoComponentProps {
  addedYoutubeVideo(): void
  active: boolean
}

const CreateExerciseYoutubeVideoComponent: React.FC<CreateExerciseYoutubeVideoComponentProps> = (
  props
) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const [description, setDescription] = useState('')
  const [youtubeVideoUrl, setYoutubeVideoUrl] = useState('')
  const { notifyError, notifySuccess } = useNotify()
  const [isCreatingExerciseInputField, setIsCreatingExerciseInputField] = useState(false)

  const showExerciseInputField = (): void => {
    dispatch(
      setAddingExerciseComponent(ExerciseComponentOutputDTOExerciseComponentTypeEnum.YoutubeVideo)
    )
    setIsCreatingExerciseInputField(true)
  }

  const cancel = (): void => {
    setDescription('')
    setYoutubeVideoUrl('')
    setIsCreatingExerciseInputField(false)
    dispatch(setAddingExerciseComponent(null))
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()
    try {
      const dto: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: ExerciseComponentOutputDTOExerciseComponentTypeEnum.YoutubeVideo,
        youtubeUrl: youtubeVideoUrl,
        exerciseComponentDescription: description,
      }
      await dispatch(
        createExerciseComponent({ createExerciseComponentDTO: dto, file: undefined })
      ).unwrap()
      notifySuccess(t('exercise.component_created_successfully'))
      cancel()
      props.addedYoutubeVideo()
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
          {t('exercise.addYoutubeVideo')}
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            multiline
            label={t('exercise.youtubeDescription')}
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />

          <TextField
            required
            label={t('exercise.youtubeVideoUrl')}
            value={youtubeVideoUrl}
            onChange={(e) => setYoutubeVideoUrl(e.target.value)}
          />

          <YoutubeIframe youtubeUrl={youtubeVideoUrl} />

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

export default CreateExerciseYoutubeVideoComponent
