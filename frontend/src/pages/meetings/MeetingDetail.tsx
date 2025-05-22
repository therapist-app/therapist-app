import {
  Button,
  Divider,
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
import CreateMeetingNoteComponent from '../../generalComponents/CreateMeetingNoteComponent'
import Layout from '../../generalComponents/Layout'
import MeetingNoteComponent from '../../generalComponents/MeetingNoteComponent'
import { deleteMeeting, getMeeting } from '../../store/meetingSlice'
import { RootState } from '../../store/store'
import { patientTestApi } from '../../utils/api'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const MeetingDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, meetingId } = useParams()
  const dispatch = useAppDispatch()
  const [gad7Tests, setGad7Tests] = useState<GAD7TestOutputDTO[]>([])

  const [showCreateMeetingNote, setShowCreateMeetingNote] = useState(false)

  const selectedMeeting = useSelector((state: RootState) => state.meeting.selectedMeeting)

  useEffect(() => {
    dispatch(getMeeting(meetingId ?? ''))
  }, [dispatch, meetingId])

  useEffect(() => {
    const fetchTests = async (): Promise<void> => {
      try {
        const response = await patientTestApi.getTestsByMeeting(meetingId ?? '')
        setGad7Tests(response.data)
      } catch (error) {
        console.error('Error fetching GAD7 tests:', error)
      }
    }
    if (meetingId) {
      fetchTests()
    }
  }, [meetingId])

  const cancelCreateMeetingNote = (): void => setShowCreateMeetingNote(false)

  const refreshMeeting = async (): Promise<void> => {
    await dispatch(getMeeting(meetingId ?? ''))
    setShowCreateMeetingNote(false)
  }

  const handleDeleteMeeting = async (): Promise<void> => {
    await dispatch(deleteMeeting(meetingId ?? ''))
    navigate(
      getPathFromPage(PAGES.MEETINGS_OVERVIEW_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  const navigateToCreateExercises = (): void => {
    navigate(
      getPathFromPage(PAGES.EXERCISES_CREATE_PAGE, {
        patientId: patientId ?? '',
        meetingId: meetingId ?? '',
      })
    )
  }

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '50px' }}>
        <div>
          <Typography>
            Session start:{' '}
            {selectedMeeting?.meetingStart
              ? format(new Date(selectedMeeting.meetingStart), 'dd.MM.yyyy HH:mm', {
                  locale: de,
                })
              : '-'}
          </Typography>
          <Typography>
            Session end:{' '}
            {selectedMeeting?.meetingEnd
              ? format(new Date(selectedMeeting.meetingEnd), 'dd.MM.yyyy HH:mm', {
                  locale: de,
                })
              : '-'}
          </Typography>
        </div>

        {showCreateMeetingNote === true ? (
          <CreateMeetingNoteComponent cancel={cancelCreateMeetingNote} save={refreshMeeting} />
        ) : (
          <></>
        )}

        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div style={{ display: 'flex', gap: '50px', alignItems: 'center' }}>
            <Typography variant='h5'>Your Notes:</Typography>
            <Button
              variant='contained'
              color='primary'
              onClick={() => setShowCreateMeetingNote(true)}
              disabled={showCreateMeetingNote}
            >
              Create new Note
            </Button>
          </div>
          {selectedMeeting?.meetingNotesOutputDTO &&
          selectedMeeting?.meetingNotesOutputDTO?.length > 0 ? (
            <>
              {selectedMeeting?.meetingNotesOutputDTO?.map((meetingNote) => (
                <MeetingNoteComponent
                  key={meetingNote.id}
                  meetingNote={meetingNote}
                  delete={refreshMeeting}
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
                      meetingId: meetingId ?? '',
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

          <Divider style={{ margin: '20px 0' }} />

          <Stack direction='row' spacing={2}>
            <Button variant='contained' onClick={handleDeleteMeeting} color='error'>
              Delete Session
            </Button>
          </Stack>
        </div>
      </div>
    </Layout>
  )
}

export default MeetingDetail
