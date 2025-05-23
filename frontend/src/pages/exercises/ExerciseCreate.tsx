import { Button, MenuItem, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

import {
  CreateExerciseDTO,
  CreateExerciseDTOExerciseTypeEnum,
  ExerciseOutputDTOExerciseTypeEnum,
} from '../../api'
import Layout from '../../generalComponents/Layout'
import { createExercise } from '../../store/exerciseSlice'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const ExerciseCreate = (): ReactElement => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const { patientId, therapySessionId } = useParams()

  const [formData, setFormData] = useState<CreateExerciseDTO>({
    title: '',
    exerciseType: ExerciseOutputDTOExerciseTypeEnum.Journaling,
    therapySessionId: therapySessionId,
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      const createdExercise = await dispatch(createExercise(formData)).unwrap()
      navigate(
        getPathFromPage(PAGES.EXERCISES_DETAILS_PAGE, {
          exerciseId: createdExercise.id ?? '',
          patientId: patientId ?? '',
          therapySessionId: therapySessionId ?? '',
        })
      )
    } catch (err) {
      console.error('Registration error:', err)
    }
  }

  return (
    <Layout>
      <form style={{ maxWidth: '600px' }} onSubmit={handleSubmit}>
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

        <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
          Submit
        </Button>
      </form>
    </Layout>
  )
}

export default ExerciseCreate
