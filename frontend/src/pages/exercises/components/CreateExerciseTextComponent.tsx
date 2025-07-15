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
import { createExerciseComponent, setAddingExerciseComponent } from '../../../store/exerciseSlice'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateExerciseTextComponentProps {
  createdExercise(): void
  active: boolean
}

const CreateExerciseTextComponent: React.FC<CreateExerciseTextComponentProps> = (
  props: CreateExerciseTextComponentProps
) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const [exerciseText, setExerciseText] = useState('')
  const { t } = useTranslation()

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
      const createExerciseComponentDTO: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text,
        description: exerciseText,
      }
      await dispatch(
        createExerciseComponent({
          createExerciseComponentDTO: createExerciseComponentDTO,
          file: undefined,
        })
      )
      cancel()
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.createdExercise()
    }
  }

  if (!props.active) {
    return null
  }

  return (
    <div>
      {isCreatingExerciseText === false ? (
        <Button variant='contained' color='primary' onClick={showExerciseTextField}>
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
          ></TextField>
          <div
            style={{
              display: 'flex',
              width: '100%',
              gap: '10px',

              justifyContent: 'center',
            }}
          >
            <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
              <CheckIcon />
            </Button>

            <Button variant='contained' color='error' fullWidth sx={{ mt: 2 }} onClick={cancel}>
              <ClearIcon />
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateExerciseTextComponent
