import CheckIcon from '@mui/icons-material/Check'
import {
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material'
import { ReactElement, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { getAllExercisesOfPatient } from '../../../store/exerciseSlice'
import { RootState } from '../../../store/store'
import { formatDateNicely } from '../../../utils/dateUtil'
import { useAppDispatch } from '../../../utils/hooks'
import { getPathFromPage, PAGES } from '../../../utils/routes'

const ExerciseOverviewComponent = (): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()

  const navigate = useNavigate()

  const handleCreateNewExercise = (): void => {
    navigate(
      getPathFromPage(PAGES.EXERCISES_CREATE_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  const navigateToSpecificExercise = (exerciseId: string): void => {
    navigate(
      getPathFromPage(PAGES.EXERCISES_DETAILS_PAGE, {
        patientId: patientId ?? '',
        exerciseId: exerciseId ?? '',
      })
    )
  }

  const allExercisesOfPatient = useSelector(
    (state: RootState) => state.exercise.allExercisesOfPatient
  )

  useEffect(() => {
    dispatch(getAllExercisesOfPatient(patientId ?? ''))
  }, [dispatch, patientId])

  return (
    <>
      <div
        style={{
          display: 'flex',
          gap: '30px',
          alignItems: 'center',
          marginBottom: '20px',
        }}
      >
        <Typography variant='h4'> All Exercises</Typography>
        <Button variant='contained' onClick={handleCreateNewExercise}>
          Create new Exercise
        </Button>
      </div>
      {allExercisesOfPatient.length > 0 ? (
        <TableContainer sx={{ marginTop: '10px', maxWidth: '600px' }} component={Paper}>
          <Table aria-label='simple table'>
            <TableHead>
              <TableRow>
                <TableCell>
                  <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>Exercise</div>
                </TableCell>
                <TableCell>Exercise Type</TableCell>
                <TableCell>Exercise Start</TableCell>
                <TableCell>Exercise End</TableCell>
                <TableCell>Is Paused</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {allExercisesOfPatient.map((exercise) => (
                <TableRow
                  onClick={() => navigateToSpecificExercise(exercise.id ?? '')}
                  key={exercise.id}
                  sx={{
                    '&:last-child td, &:last-child th': { border: 0 },
                    cursor: 'pointer',
                  }}
                >
                  <TableCell
                    sx={{
                      maxWidth: 400,
                      whiteSpace: 'nowrap',
                      overflow: 'hidden',
                      textOverflow: 'ellipsis',
                    }}
                    component='th'
                    scope='row'
                  >
                    <strong>{exercise.title}</strong>
                  </TableCell>
                  <TableCell>{exercise.exerciseType}</TableCell>
                  <TableCell>{formatDateNicely(exercise.exerciseStart)}</TableCell>
                  <TableCell>{formatDateNicely(exercise.exerciseEnd)}</TableCell>
                  <TableCell>{exercise.isPaused && <CheckIcon />}</TableCell>
                  <TableCell></TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography>You haven't added any exercises yet...</Typography>
      )}
    </>
  )
}

export default ExerciseOverviewComponent
