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
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { useNotify } from '../../../hooks/useNotify'
import { getAllExercisesOfPatient } from '../../../store/exerciseSlice'
import { RootState } from '../../../store/store'
import { commonButtonStyles } from '../../../styles/buttonStyles'
import { formatDateNicely } from '../../../utils/dateUtil'
import { useAppDispatch } from '../../../utils/hooks'
import { getPathFromPage, PAGES } from '../../../utils/routes'

const ExerciseOverviewComponent = (): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const navigate = useNavigate()
  const { notifyError } = useNotify()

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
    ;(async () => {
      try {
        await dispatch(getAllExercisesOfPatient(patientId ?? '')).unwrap()
      } catch (err) {
        notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      }
    })()
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
        <Typography variant='h2'> Exercises</Typography>
        <Button sx={{ ...commonButtonStyles, minWidth: '200px' }} onClick={handleCreateNewExercise}>
          {t('exercise.create_new_exercise')}
        </Button>
      </div>
      {allExercisesOfPatient.length > 0 ? (
        <TableContainer sx={{ marginTop: '10px' }} component={Paper}>
          <Table aria-label='simple table'>
            <TableHead>
              <TableRow>
                <TableCell>
                  <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>
                    {t('exercise.exercise')}
                  </div>
                </TableCell>
                <TableCell>{t('exercise.exercise_start')}</TableCell>
                <TableCell>{t('exercise.exercise_end')}</TableCell>
                <TableCell>{t('exercise.is_paused')}</TableCell>
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
                    <strong>{exercise.exerciseTitle}</strong>
                  </TableCell>
                  <TableCell>{formatDateNicely(exercise.exerciseStart)}</TableCell>
                  <TableCell>{formatDateNicely(exercise.exerciseEnd)}</TableCell>
                  <TableCell>{exercise.isPaused && <CheckIcon />}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography>{t('exercise.no_exercises_yet')}</Typography>
      )}
    </>
  )
}

export default ExerciseOverviewComponent
