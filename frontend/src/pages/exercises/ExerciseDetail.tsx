import { Typography } from '@mui/material'
import React, { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import {
  ExerciseComponentOutputDTO,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
} from '../../api'
import CreateExerciseFileComponent from '../../generalComponents/CreateExerciseFileComponent'
import CreateExerciseTextComponent from '../../generalComponents/CreateExerciseTextComponent'
import Layout from '../../generalComponents/Layout'
import ShowExerciseTextComponent from '../../generalComponents/ShowExerciseTextComponent'
import { getExerciseById } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'

const ExerciseDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, therapySessionId, exerciseId } = useParams()
  const dispatch = useAppDispatch()

  const selectedExercise = useSelector((state: RootState) => state.exercise.selectedExercise)

  const refreshExercise = (): void => {
    dispatch(getExerciseById(exerciseId ?? ''))
  }

  useEffect(() => {
    refreshExercise()
  }, [dispatch, exerciseId])

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <Typography variant='h5'>Title: {selectedExercise?.title}</Typography>
        <Typography>Exercise Type: {selectedExercise?.exerciseType}</Typography>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px', marginTop: '40px' }}>
          {selectedExercise?.exerciseComponentsOutputDTO &&
            selectedExercise.exerciseComponentsOutputDTO.map((exerciseComponent) => (
              <div
                key={exerciseComponent.id}
                style={{
                  border: '1px solid black',
                  borderRadius: '5px',
                  padding: '20px',
                }}
              >
                {exerciseComponent.exerciseComponentType ===
                ExerciseComponentOutputDTOExerciseComponentTypeEnum.File ? (
                  <>yeet</>
                ) : (
                  <ShowExerciseTextComponent
                    exerciseComponent={exerciseComponent}
                    numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                    refresh={refreshExercise}
                  />
                )}
              </div>
            ))}
        </div>
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: '15px',
            marginTop: '20px',
          }}
        >
          <CreateExerciseFileComponent createdExerciseFile={refreshExercise} />
          <CreateExerciseTextComponent createdExercise={refreshExercise} />
        </div>
      </div>
    </Layout>
  )
}

export default ExerciseDetail
