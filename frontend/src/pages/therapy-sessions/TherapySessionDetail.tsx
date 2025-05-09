import {
  Button,
  Typography,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from '@mui/material'
import { useNavigate, useParams } from 'react-router-dom'
import Layout from '../../generalComponents/Layout'
import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { ReactElement, useEffect, useState } from 'react'
import { deleteTherapySession, getTherapySession } from '../../store/therapySessionSlice'
import { useAppDispatch } from '../../utils/hooks'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'
import { getPathFromPage, PAGES } from '../../utils/routes'
import { GAD7TestOutputDTO } from '../../api'
import { patientTestApi } from '../../utils/api'

const TherapySessionDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, therapySessionId } = useParams()
  const dispatch = useAppDispatch()
  const [gad7Tests, setGad7Tests] = useState<GAD7TestOutputDTO[]>([])
  const selectedTherapySession = useSelector(
    (state: RootState) => state.therapySession.selectedTherapySession
  )

  useEffect(() => {
    dispatch(getTherapySession(therapySessionId ?? ''))
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

  const handleDeleteTherapySession = async (): Promise<void> => {
    await dispatch(deleteTherapySession(therapySessionId ?? ''))
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  return (
    <Layout>
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

      {gad7Tests.length > 0 && (
        <>
          <Typography variant='h5' style={{ marginTop: '40px', marginBottom: '20px' }}>
            GAD7 Tests History
          </Typography>

          <TableContainer component={Paper} sx={{ marginBottom: '20px' }}>
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
      )}

      <Stack direction='row' spacing={2} sx={{ marginTop: '50px', marginBottom: '20px' }}>
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
        <Button variant='contained' onClick={handleDeleteTherapySession} color='error'>
          Delete Session
        </Button>
      </Stack>
    </Layout>
  )
}

export default TherapySessionDetail
