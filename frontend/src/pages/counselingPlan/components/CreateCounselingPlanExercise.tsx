import { Button, MenuItem, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseDTO,
  CreateExerciseDTOExerciseTypeEnum,
  ExerciseOutputDTOExerciseTypeEnum,
} from '../../../api'
import {
  clearCreateCounselingPlanExerciseAIGenerated,
  CounselingPlanExerciseStateEnum,
  setCounselingPlanExerciseStateEnum,
} from '../../../store/counselingPlanSlice'
import { createExercise } from '../../../store/exerciseSlice'
import { RootState } from '../../../store/store'
import { useAppDispatch } from '../../../utils/hooks'

type ExerciseFormData = Omit<CreateExerciseDTO, 'exerciseStart' | 'exerciseEnd'> & {
  exerciseStart: Date | null
  durationInWeeks: number
}

interface CreateCounselingPlanExerciseProps {
  onSuccess: () => void
}

const CreateCounselingPlanExercise = ({
  onSuccess,
}: CreateCounselingPlanExerciseProps): ReactElement => {
  const [open, setOpen] = useState(false)
  const { patientId } = useParams()
  const { counselingPlanExerciseAIGeneratedOutputDTO, counselingPlanExerciseStateEnum } =
    useSelector((state: RootState) => state.counselingPlan)

  const dispatch = useAppDispatch()

  const [formData, setFormData] = useState<ExerciseFormData>({
    title: '',
    exerciseType: ExerciseOutputDTOExerciseTypeEnum.Journaling,
    exerciseStart: new Date(),
    durationInWeeks: 3,
    patientId: patientId,
  })

  useEffect(() => {
    if (
      counselingPlanExerciseAIGeneratedOutputDTO &&
      !counselingPlanExerciseAIGeneratedOutputDTO.selectedMeetingId
    ) {
      const newFormData: ExerciseFormData = {
        title: counselingPlanExerciseAIGeneratedOutputDTO.title,
        exerciseType: counselingPlanExerciseAIGeneratedOutputDTO.exerciseType,
        exerciseStart: new Date(counselingPlanExerciseAIGeneratedOutputDTO.exerciseStart ?? ''),
        durationInWeeks: 2,
      }
      setFormData(newFormData)
      setOpen(true)
    }
  }, [counselingPlanExerciseAIGeneratedOutputDTO])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleCancel = (): void => {
    setOpen(false)
    dispatch(clearCreateCounselingPlanExerciseAIGenerated())
    dispatch(setCounselingPlanExerciseStateEnum(CounselingPlanExerciseStateEnum.IDLE))
  }

  const handleCreateExercise = (): void => {
    setOpen(true)
    dispatch(clearCreateCounselingPlanExerciseAIGenerated())
    dispatch(setCounselingPlanExerciseStateEnum(CounselingPlanExerciseStateEnum.CREATING_EXERCISE))
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      const exerciseStart = formData.exerciseStart ?? new Date()
      const createExerciseDTO: CreateExerciseDTO = {
        ...formData,
        exerciseStart: formData.exerciseStart?.toISOString(),
        exerciseEnd: new Date(
          exerciseStart.getTime() + formData.durationInWeeks * 7 * 24 * 60 * 60 * 1000
        ).toISOString(),
      }
      await dispatch(createExercise(createExerciseDTO)).unwrap()

      setOpen(false)
      dispatch(clearCreateCounselingPlanExerciseAIGenerated())
      dispatch(setCounselingPlanExerciseStateEnum(CounselingPlanExerciseStateEnum.IDLE))

      onSuccess()
    } catch (err) {
      console.error('Registration error:', err)
    }
  }

  if (counselingPlanExerciseStateEnum === CounselingPlanExerciseStateEnum.ADDING_EXERCISE) {
    return <></>
  }

  return (
    <div>
      {!open ? (
        <Button variant='contained' onClick={handleCreateExercise}>
          Create new Exercise
        </Button>
      ) : (
        <form
          style={{ maxWidth: '600px', display: 'flex', flexDirection: 'column', gap: '10px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            label='Title'
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
            label='Exercise Type'
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
              label='Exercise Start'
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
              label='Duration in Weeks'
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
            <Button type='submit' variant='contained' color='success'>
              Create new Exercise
            </Button>
            <Button variant='contained' onClick={handleCancel} color='error'>
              Cancel
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateCounselingPlanExercise
