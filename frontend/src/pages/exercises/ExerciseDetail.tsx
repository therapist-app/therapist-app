import { Typography } from '@mui/material'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { ExerciseFileOutputDTO, ExerciseTextOutputDTO } from '../../api'
import Layout from '../../generalComponents/Layout'
import { getExerciseById } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'

interface SortedExerciseDetails {
  type: 'file' | 'text'
  exerciseFile: ExerciseFileOutputDTO
  exerciseText: ExerciseTextOutputDTO
  orderNumber: number
}

const ExerciseDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, therapySessionId, exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const [sortedExerciseDetails, setSortedExerciseDetails] = useState<SortedExerciseDetails[]>([])

  const selectedExercise = useSelector((state: RootState) => state.exercise.selectedExercise)

  useEffect(() => {
    dispatch(getExerciseById(exerciseId ?? ''))
  }, [dispatch, exerciseId])

  useEffect(() => {
    if (
      selectedExercise?.exerciseFilesOutputDTO === undefined ||
      selectedExercise.exerciseTextsOutputDTO === undefined
    ) {
      return
    }
    const totalNumberOfDetails =
      selectedExercise.exerciseFilesOutputDTO.length +
      selectedExercise.exerciseTextsOutputDTO.length
    for (let i = 0; i < totalNumberOfDetails; i++) {
      //
    }
  }, [selectedExercise])

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <Typography variant='h5'>Title: {selectedExercise?.title}</Typography>
        <Typography>Exercise Type: {selectedExercise?.exerciseType}</Typography>
      </div>
    </Layout>
  )
}

export default ExerciseDetail
