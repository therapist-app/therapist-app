import DeleteIcon from '@mui/icons-material/Delete'
import { Button, Typography } from '@mui/material'
import { ReactElement, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { ExerciseComponentOutputDTOExerciseComponentTypeEnum } from '../../api'
import CreateExerciseFileComponent from '../../generalComponents/CreateExerciseFileComponent'
import CreateExerciseTextComponent from '../../generalComponents/CreateExerciseTextComponent'
import Layout from '../../generalComponents/Layout'
import LoadingSpinner from '../../generalComponents/LoadingSpinner'
import ShowExerciseFileComponent from '../../generalComponents/ShowExerciseFileComponent'
import ShowExerciseTextComponent from '../../generalComponents/ShowExerciseTextComponent'
import { deleteExcercise, getExerciseById } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const ExerciseDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, therapySessionId, exerciseId } = useParams()

  const dispatch = useAppDispatch()

  const selectedExercise = useSelector((state: RootState) => state.exercise.selectedExercise)

  const exerciseStatus = useSelector((state: RootState) => state.exercise.status)

  const addingExerciseComponent = useSelector(
    (state: RootState) => state.exercise.addingExerciseComponent
  )

  const refreshExercise = async (): Promise<void> => {
    try {
      await dispatch(getExerciseById(exerciseId ?? ''))
    } catch (e) {
      console.error(e)
    }
  }

  const handleDeleteExercise = async (): Promise<void> => {
    await dispatch(deleteExcercise(exerciseId ?? ''))
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_DETAILS_PAGE, {
        patientId: patientId ?? '',
        therapySessionId: therapySessionId ?? '',
      })
    )
  }

  useEffect(() => {
    const refreshExercise = async (): Promise<void> => {
      try {
        await dispatch(getExerciseById(exerciseId ?? ''))
      } catch (e) {
        console.error(e)
      }
    }
    refreshExercise()
  }, [exerciseId, dispatch])

  if (exerciseStatus === 'loading') {
    return (
      <Layout>
        <LoadingSpinner />{' '}
      </Layout>
    )
  }

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <Typography variant='h5'>Title: {selectedExercise?.title}</Typography>
        <Typography>Exercise Type: {selectedExercise?.exerciseType}</Typography>
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: '20px',
            marginTop: '40px',
            maxWidth: '600px',
          }}
        >
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
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image && (
                  <ShowExerciseFileComponent
                    exerciseComponent={exerciseComponent}
                    numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                    refresh={refreshExercise}
                    isImageComponent
                  />
                )}
                {exerciseComponent.exerciseComponentType ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.File && (
                  <ShowExerciseFileComponent
                    exerciseComponent={exerciseComponent}
                    numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                    refresh={refreshExercise}
                    isImageComponent={false}
                  />
                )}
                {exerciseComponent.exerciseComponentType ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text && (
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
            gap: '15px',
            marginTop: '20px',
          }}
        >
          <CreateExerciseTextComponent
            createdExercise={refreshExercise}
            active={
              addingExerciseComponent === null ||
              addingExerciseComponent === ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text
            }
          />

          <CreateExerciseFileComponent
            createdExerciseFile={refreshExercise}
            isImageComponent
            active={
              addingExerciseComponent === null ||
              addingExerciseComponent === ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image
            }
          />

          <CreateExerciseFileComponent
            createdExerciseFile={refreshExercise}
            isImageComponent={false}
            active={
              addingExerciseComponent === null ||
              addingExerciseComponent === ExerciseComponentOutputDTOExerciseComponentTypeEnum.File
            }
          />
        </div>
        <Button sx={{ alignSelf: 'start', marginTop: '50px' }} onClick={handleDeleteExercise}>
          <Typography color='error'>Delete Exercise</Typography>
          <DeleteIcon style={{ color: 'red', marginLeft: '5px' }} />
        </Button>
      </div>
    </Layout>
  )
}

export default ExerciseDetail
