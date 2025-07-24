import { Button, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AxiosError } from 'axios'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate, useParams } from 'react-router-dom'

import { CreateExerciseDTO } from '../../api'
import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { createExercise } from '../../store/exerciseSlice'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { handleError } from '../../utils/handleError'
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
  const { notifyError, notifySuccess } = useNotify()

  const [formData, setFormData] = useState<ExerciseFormData>({
    exerciseTitle: '',
    exerciseDescription: '',
    exerciseExplanation: '',
    exerciseStart: new Date(),
    durationInWeeks: 3,
    doEveryNDays: 1,
    patientId: patientId,
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()
    try {
      const dto: CreateExerciseDTO = {
        ...formData,
        exerciseStart: formData.exerciseStart?.toISOString(),
        durationInWeeks: formData.durationInWeeks,
      }
      const createdExercise = await dispatch(createExercise(dto)).unwrap()
      notifySuccess(t('exercise.exercise_created_successfully'))
      navigate(
        getPathFromPage(PAGES.EXERCISES_DETAILS_PAGE, {
          exerciseId: createdExercise.id ?? '',
          patientId: patientId ?? '',
          meetingId: meetingId ?? '',
        })
      )
    } catch (err) {
      const msg = handleError(err as AxiosError)
      notifyError(msg)
    }
  }

  return (
    <Layout>
      <form
        style={{ maxWidth: '600px', display: 'flex', flexDirection: 'column', gap: '15px' }}
        onSubmit={handleSubmit}
      >
        <TextField
          label={t('exercise.title')}
          name='exerciseTitle'
          value={formData.exerciseTitle}
          onChange={handleChange}
          fullWidth
          required
        />

        <TextField
          label={t('exercise.exerciseDescription')}
          name='exerciseDescription'
          value={formData.exerciseDescription}
          onChange={handleChange}
          fullWidth
        />

        <TextField
          label={t('exercise.exerciseExplanation')}
          name='exerciseExplanation'
          value={formData.exerciseExplanation}
          onChange={handleChange}
          fullWidth
        />

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
        </LocalizationProvider>

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

        <TextField
          label={t('exercise.doEveryNDays')}
          type='number'
          value={formData.doEveryNDays}
          onChange={(e) => {
            setFormData({
              ...formData,
              doEveryNDays: Number(e.target.value),
            })
          }}
          sx={{ width: '100%' }}
        />

        <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '200px', mt: 2 }}>
          {t('exercise.submit')}
        </Button>
      </form>
    </Layout>
  )
}

export default ExerciseCreate
