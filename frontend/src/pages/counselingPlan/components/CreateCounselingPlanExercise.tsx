import { Button, MenuItem, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import {
  CounselingPlanPhaseOutputDTO,
  CreateExerciseDTO,
  CreateExerciseDTOExerciseTypeEnum,
  ExerciseOutputDTOExerciseTypeEnum,
} from '../../../api'
import {
  addExerciseToCounselingPlanPhase,
  createCounselingPlanExerciseAIGenerated,
} from '../../../store/counselingPlanSlice'
import { createExercise } from '../../../store/exerciseSlice'
import { useAppDispatch } from '../../../utils/hooks'
import { getCurrentLanguage } from '../../../utils/languageUtil'
import { cancelButtonStyles, commonButtonStyles, successButtonStyles } from '../../../styles/buttonStyles'

type ExerciseFormData = Omit<CreateExerciseDTO, 'exerciseStart' | 'exerciseEnd'> & {
  exerciseStart: Date | null
  durationInWeeks: number
}

interface CreateCounselingPlanExerciseProps {
  onSuccess: () => void
  counselingPlanPhase: CounselingPlanPhaseOutputDTO
}

const CreateCounselingPlanExercise = ({
  onSuccess,
  counselingPlanPhase,
}: CreateCounselingPlanExerciseProps): ReactElement => {
  const [open, setOpen] = useState(false)
  const { patientId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const [formData, setFormData] = useState<ExerciseFormData>({
    title: '',
    exerciseType: ExerciseOutputDTOExerciseTypeEnum.Journaling,
    exerciseStart: new Date(counselingPlanPhase.startDate ?? ''),
    durationInWeeks: counselingPlanPhase.durationInWeeks ?? 2,
    patientId: patientId,
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleCancel = (): void => {
    setOpen(false)
  }

  const handleCreateExercise = (): void => {
    setOpen(true)
  }

  const handleCreateExerciseWithAI = async (): Promise<void> => {
    const createExerciseDTO = await dispatch(
      createCounselingPlanExerciseAIGenerated({
        counselingPlanPhaseId: counselingPlanPhase.id ?? '',
        language: getCurrentLanguage(),
      })
    ).unwrap()

    const newExerciseFormData: ExerciseFormData = {
      title: createExerciseDTO.title,
      exerciseType: createExerciseDTO.exerciseType,
      exerciseStart: new Date(createExerciseDTO.exerciseStart ?? ''),
      durationInWeeks: createExerciseDTO.durationInWeeks ?? 2,
      patientId: patientId,
    }

    setFormData(newExerciseFormData)
    setOpen(true)
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      const createExerciseDTO: CreateExerciseDTO = {
        ...formData,
        exerciseStart: formData.exerciseStart?.toISOString(),
        durationInWeeks: formData.durationInWeeks,
      }
      const response = await dispatch(createExercise(createExerciseDTO)).unwrap()
      await dispatch(
        addExerciseToCounselingPlanPhase({
          exerciseId: response.id ?? '',
          counselingPlanPhaseId: counselingPlanPhase.id ?? '',
        })
      )

      setOpen(false)

      onSuccess()
    } catch (err) {
      console.error('Registration error:', err)
    }
  }

  return (
    <div>
      {!open ? (
        <Button sx={{ ...commonButtonStyles, minWidth: '200px' }} onClick={handleCreateExercise}>
          {t('counseling_plan.create_new_exercise')}
        </Button>
      ) : (
        <form
          style={{ maxWidth: '600px', display: 'flex', flexDirection: 'column', gap: '10px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            label={t('counseling_plan.title')}
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
            label={t('counseling_plan.exercise_type')}
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
              label={t('counseling_plan.exercise_start')}
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
              label={t('counseling_plan.duration_in_weeks')}
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
          <div style={{ display: 'flex', gap: '10px' }}>
            <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '200px' }}>
              {t('counseling_plan.create_new_exercise')}
            </Button>
            <Button sx={{ ...successButtonStyles, minWidth: '360px' }} onClick={handleCreateExerciseWithAI}>
              {t('counseling_plan.make_ai_generated_suggestion_for_exercise')}
            </Button>
            <Button sx={{ ...cancelButtonStyles, minWidth: '100px' }} onClick={handleCancel}>
              {t('counseling_plan.cancel')}
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateCounselingPlanExercise
