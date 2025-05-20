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
import { ReactElement } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { RootState } from '../../store/store'
import { getPathFromPage, PAGES } from '../../utils/routes'

const ExerciseOverview = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, therapySessionId } = useParams()

  const navigateToCreateExercises = (): void => {
    navigate(
      getPathFromPage(PAGES.EXERCISES_CREATE_PAGE, {
        patientId: patientId ?? '',
        therapySessionId: therapySessionId ?? '',
      })
    )
  }

  const navigateToSpecificExercise = (exerciseId: string): void => {
    navigate(
      getPathFromPage(PAGES.EXERCISES_DETAILS_PAGE, {
        patientId: patientId ?? '',
        therapySessionId: therapySessionId ?? '',
        exerciseId: exerciseId ?? '',
      })
    )
  }

  const allExercises = useSelector(
    (state: RootState) => state.exercise.allExercisesOfTherapySession
  )

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
        <div style={{ display: 'flex', gap: '50px', alignItems: 'center' }}>
          <Typography variant='h5'>Exercises:</Typography>
          <Button variant='contained' color='primary' onClick={navigateToCreateExercises}>
            Create new Exercise
          </Button>
        </div>

        {allExercises.length > 0 ? (
          <TableContainer sx={{ marginTop: '10px', maxWidth: '600px' }} component={Paper}>
            <Table aria-label='simple table'>
              <TableHead>
                <TableRow>
                  <TableCell>
                    <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>
                      Exercise
                    </div>
                  </TableCell>
                  <TableCell align='right'>Exercise Type</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {allExercises.map((exercise) => (
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
                      {exercise.title}
                    </TableCell>
                    <TableCell align='right'>{exercise.exerciseType}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        ) : (
          <Typography>You haven't added any exercises yet...</Typography>
        )}
      </div>
    </Layout>
  )
}

export default ExerciseOverview
