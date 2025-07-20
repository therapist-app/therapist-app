import { Button, MenuItem, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate, useParams } from 'react-router-dom'

import {
  CreateExerciseDTO,
  CreateExerciseDTOExerciseTypeEnum,
  ExerciseOutputDTOExerciseTypeEnum,
} from '../../api'
import Layout from '../../generalComponents/Layout'
import { createExercise } from '../../store/exerciseSlice'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

type ExerciseFormData = Omit<CreateExerciseDTO, 'exerciseStart' | 'exerciseEnd'> & {
  exerciseStart: Date | null
  durationInWeeks: number
}

const ExerciseCreate = (): ReactElement => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const { patientId, meetingId } = useParams()
  const { t } = useTranslation()

  const [formData, setFormData] = useState<ExerciseFormData>({
    title: '',
    exerciseType: ExerciseOutputDTOExerciseTypeEnum.Journaling,
    exerciseStart: new Date(),
    durationInWeeks: 3,
    patientId: patientId,
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      const createExerciseDTO: CreateExerciseDTO = {
        ...formData,
        exerciseStart: formData.exerciseStart?.toISOString(),
        durationInWeeks: formData.durationInWeeks,
      }
      const createdExercise = await dispatch(createExercise(createExerciseDTO)).unwrap()
      navigate(
        getPathFromPage(PAGES.EXERCISES_DETAILS_PAGE, {
          exerciseId: createdExercise.id ?? '',
          patientId: patientId ?? '',
          meetingId: meetingId ?? '',
        })
      )
    } catch (err) {
      console.error('Registration error:', err)
    }
  }

  return (
    <Layout>
      <form
        style={{ maxWidth: '600px', display: 'flex', flexDirection: 'column', gap: '10px' }}
        onSubmit={handleSubmit}
      >
        <TextField
          label={t('exercise.title')}
          name='title'
          value={formData.title}
          onChange={handleChange}
          fullWidth
          margin='normal'
          required
        />

        <TextField
          select
          sx={{ fontWeight: 'bold' }}
          label={t('exercise.exercise_type')}
          name='exerciseType'
          value={formData.exerciseType}
          onChange={handleChange}
          required
          fullWidth
        >
          {Object.values(CreateExerciseDTOExerciseTypeEnum).map((option: string) => (
            <MenuItem key={option} value={option}>
              {option}
            </MenuItem>
          ))}
        </TextField>

        <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
          <DateTimePicker
            label={t('exercise.exercise_start')}
            value={formData.exerciseStart}
            onChange={(newValue: Date | null) => {
              setFormData({
                ...formData,
                exerciseStart: newValue,
              })
            }}
            sx={{ width: '100%' }}
          />

          <TextField
            label={t('exercise.duration_in_weeks')}
            type='number'
            value={formData.durationInWeeks}
            onChange={(e) => {
              setFormData({
                ...formData,
                durationInWeeks: Number(e.target.value),
              })
            }}
            sx={{ width: '100%' }}
          />
        </LocalizationProvider>

        <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '200px', mt: 2 }}>
          {t('exercise.submit')}
        </Button>
      </form>
    </Layout>
  )
}

export default ExerciseCreate
