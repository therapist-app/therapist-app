import {
  Button,
  Paper,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { GAD7TestOutputDTO } from '../../api'
import CreateTherapySessionNoteComponent from '../../generalComponents/CreateTherapySessionNoteComponent'
import Layout from '../../generalComponents/Layout'
import TherapySessionNoteComponent from '../../generalComponents/TherapySessionNoteComponent'
import { getAllExercisesOfTherapySession } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { deleteTherapySession, getTherapySession } from '../../store/therapySessionSlice'
import { patientTestApi } from '../../utils/api'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const TherapySessionDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, therapySessionId } = useParams()
  const dispatch = useAppDispatch()
  const [gad7Tests, setGad7Tests] = useState<GAD7TestOutputDTO[]>([])

  const [showCreateTherapySessionNote, setShowCreateTherapySessionNote] = useState(false)

  const selectedTherapySession = useSelector(
    (state: RootState) => state.therapySession.selectedTherapySession
  )
  const allExercises = useSelector(
    (state: RootState) => state.exercise.allExercisesOfTherapySession
  )

  useEffect(() => {
    dispatch(getTherapySession(therapySessionId ?? ''))
    dispatch(getAllExercisesOfTherapySession(therapySessionId ?? ''))
  }, [dispatch, therapySessionId])

  useEffect(() => {
    const fetchTests = async (): Promise<void> => {
      try {
        const response = await patientTestApi.getTestsByTherapySession(therapySessionId ?? '')
        setGad7Tests(response.data)
      } catch (error) {
        console.error('Error fetching GAD7 tests:', error)
      }
    }
    if (therapySessionId) {
      fetchTests()
    }
  }, [therapySessionId])

  const cancelCreateTherapySessionNote = (): void => setShowCreateTherapySessionNote(false)

  const refreshTherapySession = async (): Promise<void> => {
    await dispatch(getTherapySession(therapySessionId ?? ''))
    setShowCreateTherapySessionNote(false)
  }

  const handleDeleteTherapySession = async (): Promise<void> => {
    await dispatch(deleteTherapySession(therapySessionId ?? ''))
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE, {
        patientId: patientId ?? '',
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

  const navigateToCreateExercises = (): void => {
    navigate(
      getPathFromPage(PAGES.EXERCISES_CREATE_PAGE, {
        patientId: patientId ?? '',
        therapySessionId: therapySessionId ?? '',
      })
    )
  }

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '50px' }}>
        <div>
          <Typography>
            Session start:{' '}
            {selectedTherapySession?.sessionStart
              ? format(new Date(selectedTherapySession.sessionStart), 'dd.MM.yyyy HH:mm', {
                  locale: de,
                })
              : '-'}
          </Typography>
          <Typography>
            Session end:{' '}
            {selectedTherapySession?.sessionEnd
              ? format(new Date(selectedTherapySession.sessionEnd), 'dd.MM.yyyy HH:mm', {
                  locale: de,
                })
              : '-'}
          </Typography>
        </div>

        {showCreateTherapySessionNote === true ? (
          <CreateTherapySessionNoteComponent
            cancel={cancelCreateTherapySessionNote}
            save={refreshTherapySession}
          />
        ) : (
          <></>
        )}

        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div style={{ display: 'flex', gap: '50px', alignItems: 'center' }}>
            <Typography variant='h5'>Your Notes:</Typography>
            <Button
              variant='contained'
              color='primary'
              onClick={() => setShowCreateTherapySessionNote(true)}
              disabled={showCreateTherapySessionNote}
            >
              Create new Note
            </Button>
          </div>
          {selectedTherapySession?.therapySessionNotesOutputDTO &&
          selectedTherapySession?.therapySessionNotesOutputDTO?.length > 0 ? (
            <>
              {selectedTherapySession?.therapySessionNotesOutputDTO?.map((therapySessionNote) => (
                <TherapySessionNoteComponent
                  key={therapySessionNote.id}
                  therapySessionNote={therapySessionNote}
                  delete={refreshTherapySession}
                />
              ))}
            </>
          ) : (
            <>
              <Typography>You don't have any notes yet...</Typography>
            </>
          )}
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div style={{ display: 'flex', gap: '50px', alignItems: 'center' }}>
            <Typography variant='h5'>Exercises:</Typography>
            <Button variant='contained' color='primary' onClick={navigateToCreateExercises}>
              Create new Exercise
            </Button>
          </div>

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
        </div>

        <>
          <div
            style={{
              display: 'flex',
              gap: '50px',
              alignItems: 'center',
            }}
          >
            <Typography variant='h5'>GAD7 Tests History: </Typography>
            <Button
              variant='contained'
              color='primary'
              onClick={() =>
                navigate(
                  getPathFromPage(PAGES.GAD7_TEST_PAGE, {
                    patientId: patientId ?? '',
                    therapySessionId: therapySessionId ?? '',
                  })
                )
              }
            >
              Create GAD7 Test
            </Button>
          </div>
          {gad7Tests.length > 0 ? (
            <>
              <TableContainer component={Paper}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Date</TableCell>
                      <TableCell align='right'>
                        Q1
                        <Typography variant='caption' display='block'>
                          Feeling nervous, anxious or on edge.
                        </Typography>
                      </TableCell>
                      <TableCell align='right'>
                        Q2
                        <Typography variant='caption' display='block'>
                          Not being able to stop or control worrying.
                        </Typography>
                      </TableCell>
                      <TableCell align='right'>
                        Q3
                        <Typography variant='caption' display='block'>
                          Worrying too much about different things.
                        </Typography>
                      </TableCell>
                      <TableCell align='right'>
                        Q4
                        <Typography variant='caption' display='block'>
                          Trouble relaxing.
                        </Typography>
                      </TableCell>
                      <TableCell align='right'>
                        Q5
                        <Typography variant='caption' display='block'>
                          Being so restless that it is hard to sit still.
                        </Typography>
                      </TableCell>
                      <TableCell align='right'>
                        Q6
                        <Typography variant='caption' display='block'>
                          Becoming easily annoyed or irritable.
                        </Typography>
                      </TableCell>
                      <TableCell align='right'>
                        Q7
                        <Typography variant='caption' display='block'>
                          Feeling afraid as if something awful might happen.
                        </Typography>
                      </TableCell>
                      <TableCell align='right'>Total</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {gad7Tests.map((test) => {
                      const total = [
                        test.question1 ?? 0,
                        test.question2 ?? 0,
                        test.question3 ?? 0,
                        test.question4 ?? 0,
                        test.question5 ?? 0,
                        test.question6 ?? 0,
                        test.question7 ?? 0,
                      ].reduce((sum: number, val: number) => sum + val, 0)

                      return (
                        <TableRow key={test.testId}>
                          <TableCell>
                            {test.creationDate
                              ? format(
                                  typeof test.creationDate === 'string'
                                    ? new Date(test.creationDate)
                                    : test.creationDate,
                                  'dd.MM.yyyy HH:mm',
                                  {
                                    locale: de,
                                  }
                                )
                              : '-'}
                          </TableCell>
                          <TableCell align='right'>{test.question1 ?? '-'}</TableCell>
                          <TableCell align='right'>{test.question2 ?? '-'}</TableCell>
                          <TableCell align='right'>{test.question3 ?? '-'}</TableCell>
                          <TableCell align='right'>{test.question4 ?? '-'}</TableCell>
                          <TableCell align='right'>{test.question5 ?? '-'}</TableCell>
                          <TableCell align='right'>{test.question6 ?? '-'}</TableCell>
                          <TableCell align='right'>{test.question7 ?? '-'}</TableCell>
                          <TableCell align='right' style={{ fontWeight: 'bold' }}>
                            {total}
                          </TableCell>
                        </TableRow>
                      )
                    })}
                  </TableBody>
                </Table>
              </TableContainer>
            </>
          ) : (
            <Typography>You haven't recorded any GAD 7 tests...</Typography>
          )}
        </>

        <Stack direction='row' spacing={2}>
          <Button variant='contained' onClick={handleDeleteTherapySession} color='error'>
            Delete Session
          </Button>
        </Stack>
      </div>
    </Layout>
  )
}

export default TherapySessionDetail
