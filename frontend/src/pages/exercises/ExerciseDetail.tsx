import { Typography } from '@mui/material'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { ExerciseFileOutputDTO, ExerciseTextOutputDTO } from '../../api'
import CreateExerciseFile from '../../generalComponents/CreateExerciseFile'
import CreateExerciseText from '../../generalComponents/CreateExerciseText'
import Layout from '../../generalComponents/Layout'
import { getExerciseById } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'

interface SortedExerciseDetails {
  exerciseFile: ExerciseFileOutputDTO | undefined
  exerciseText: ExerciseTextOutputDTO | undefined
  orderNumber: number
}

const ExerciseDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, therapySessionId, exerciseId } = useParams()
  const dispatch = useAppDispatch()
  const [sortedExerciseDetails, setSortedExerciseDetails] = useState<SortedExerciseDetails[]>([])

  const selectedExercise = useSelector((state: RootState) => state.exercise.selectedExercise)

  const refreshExercise = (): void => {
    dispatch(getExerciseById(exerciseId ?? ''))
  }

  useEffect(() => {
    refreshExercise()
  }, [dispatch, exerciseId])

  useEffect(() => {
    const tempSortedExercises: SortedExerciseDetails[] = []
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
      const exerciseFile = selectedExercise.exerciseFilesOutputDTO.find(
        (ef) => ef.orderNumber === i
      )
      if (exerciseFile) {
        tempSortedExercises.push({
          exerciseFile: exerciseFile,
          exerciseText: undefined,
          orderNumber: i,
        })
      }

      const exerciseText = selectedExercise.exerciseTextsOutputDTO.find(
        (et) => et.orderNumber === i
      )
      if (exerciseText) {
        tempSortedExercises.push({
          exerciseFile: undefined,
          exerciseText: exerciseText,
          orderNumber: i,
        })
      }
    }
    setSortedExerciseDetails(tempSortedExercises)
  }, [selectedExercise])

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <Typography variant='h5'>Title: {selectedExercise?.title}</Typography>
        <Typography>Exercise Type: {selectedExercise?.exerciseType}</Typography>
        {sortedExerciseDetails.map((exercise) => (
          <div>
            {exercise.exerciseFile ? (
              <Typography>
                {exercise.orderNumber} | {exercise.exerciseFile.fileName}
              </Typography>
            ) : (
              <Typography>
                {exercise.orderNumber} | {exercise.exerciseText?.text}
              </Typography>
            )}
          </div>
        ))}
        <CreateExerciseText createdExercise={refreshExercise} />
        <CreateExerciseFile createdExerciseFile={refreshExercise} />
      </div>
    </Layout>
  )
}

export default ExerciseDetail
