import { Button, MenuItem, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useParams } from 'react-router-dom'

import {
  CreateExerciseDTO,
  CreateExerciseDTOExerciseTypeEnum,
  ExerciseOutputDTOExerciseTypeEnum,
} from '../../../api'
import { createExercise } from '../../../store/exerciseSlice'
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

  const dispatch = useAppDispatch()

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
      const exerciseStart = formData.exerciseStart ?? new Date()
      const createExerciseDTO: CreateExerciseDTO = {
        ...formData,
        exerciseStart: formData.exerciseStart?.toISOString(),
        exerciseEnd: new Date(
          exerciseStart.getTime() + formData.durationInWeeks * 7 * 24 * 60 * 60 * 1000
        ).toISOString(),
      }
      await dispatch(createExercise(createExerciseDTO)).unwrap()

      onSuccess()
    } catch (err) {
      console.error('Registration error:', err)
    }
  }

  return (
    <div>
      {!open ? (
        <Button variant='contained' onClick={() => setOpen(true)}>
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
            <Button variant='contained' onClick={() => setOpen(false)} color='error'>
              Cancel
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateCounselingPlanExercise
