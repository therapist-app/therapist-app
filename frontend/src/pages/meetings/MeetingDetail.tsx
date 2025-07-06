import { Button, Typography } from '@mui/material'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import Layout from '../../generalComponents/Layout'
import { deleteMeeting, getMeeting } from '../../store/meetingSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'
import CreateMeetingNoteComponent from './components/CreateMeetingNoteComponent'
import MeetingNoteComponent from './components/MeetingNoteComponent'

const MeetingDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, meetingId } = useParams()
  const dispatch = useAppDispatch()

  const [showCreateMeetingNote, setShowCreateMeetingNote] = useState(false)
  const [isTranscription, setIsTranscription] = useState(false)

  const selectedMeeting = useSelector((state: RootState) => state.meeting.selectedMeeting)

  useEffect(() => {
    dispatch(getMeeting(meetingId ?? ''))
  }, [dispatch, meetingId])

  const cancelCreateMeetingNote = (): void => setShowCreateMeetingNote(false)

  const refreshMeeting = async (): Promise<void> => {
    await dispatch(getMeeting(meetingId ?? ''))
    setShowCreateMeetingNote(false)
  }

  const handleDeleteMeeting = async (): Promise<void> => {
    await dispatch(deleteMeeting(meetingId ?? ''))
    navigate(
      getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  const handleCreateNewNote = (withTranscription: boolean): void => {
    setIsTranscription(withTranscription)
    setShowCreateMeetingNote(true)
  }

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '50px' }}>
        <div>
          <Typography>
            <strong>Meeting start:</strong>{' '}
            {selectedMeeting?.meetingStart
              ? format(new Date(selectedMeeting.meetingStart), 'dd.MM.yyyy HH:mm', { locale: de })
              : ''}
          </Typography>
          <Typography>
            <strong>Duration:</strong>{' '}
            {selectedMeeting?.meetingEnd && selectedMeeting?.meetingStart
              ? Math.floor(
                  (new Date(selectedMeeting.meetingEnd).getTime() -
                    new Date(selectedMeeting.meetingStart).getTime()) /
                    60000
                )
              : 0}{' '}
            minutes
          </Typography>
          <Typography>
            <strong>Location:</strong> {selectedMeeting?.location}
          </Typography>
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div style={{ display: 'flex', gap: '30px', alignItems: 'center' }}>
            <Typography variant='h2'>Your Notes:</Typography>
            <Button
              variant='contained'
              color='primary'
              onClick={() => handleCreateNewNote(false)}
              disabled={showCreateMeetingNote}
            >
              Create new Note
            </Button>

            <Button
              variant='contained'
              color='success'
              onClick={() => handleCreateNewNote(true)}
              disabled={showCreateMeetingNote}
            >
              Transcribe the Meeting
            </Button>
          </div>
          {showCreateMeetingNote === true ? (
            <CreateMeetingNoteComponent
              cancel={cancelCreateMeetingNote}
              save={refreshMeeting}
              isTranscription={isTranscription}
            />
          ) : (
            <></>
          )}
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

        <CustomizedDivider />

        <Button
          variant='contained'
          onClick={handleDeleteMeeting}
          color='error'
          sx={{ width: 'fit-content' }}
        >
          Delete Meeting
        </Button>
      </div>
    </Layout>
  )
}

export default MeetingDetail
