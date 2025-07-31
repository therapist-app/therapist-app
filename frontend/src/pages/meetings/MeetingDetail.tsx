import EditIcon from '@mui/icons-material/Edit'
import { Button, Typography } from '@mui/material'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { deleteMeeting, getMeeting } from '../../store/meetingSlice'
import { RootState } from '../../store/store'
import {
  commonButtonStyles,
  deleteButtonStyles,
  disabledButtonStyles,
  successButtonStyles,
  successDisabledButtonStyles,
} from '../../styles/buttonStyles'
import { formatDateNicely, getMinutesBetweenDates } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'
import CreateMeetingNoteComponent from './components/CreateMeetingNoteComponent'
import MeetingNoteComponent from './components/MeetingNoteComponent'
import MeetingEditing from './MeetingEditing'

const MeetingDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, meetingId } = useParams()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const { notifyError, notifySuccess } = useNotify()
  const [isEditing, setIsEditing] = useState(false)

  const [showCreateMeetingNote, setShowCreateMeetingNote] = useState(false)
  const [isTranscription, setIsTranscription] = useState(false)

  const selectedMeeting = useSelector((state: RootState) => state.meeting.selectedMeeting)

  useEffect((): void => {
    const load = async (): Promise<void> => {
      try {
        await dispatch(getMeeting(meetingId ?? '')).unwrap()
      } catch (error) {
        notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
      }
    }
    void load()
  }, [dispatch, meetingId, notifyError])

  const cancelCreateMeetingNote = (): void => setShowCreateMeetingNote(false)

  const refreshMeeting = async (): Promise<void> => {
    try {
      await dispatch(getMeeting(meetingId ?? '')).unwrap()
      setShowCreateMeetingNote(false)
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleDeleteMeeting = async (): Promise<void> => {
    try {
      await dispatch(deleteMeeting(meetingId ?? '')).unwrap()
      notifySuccess(t('meetings.meeting_deleted_successfully'))
      navigate(
        getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, {
          patientId: patientId ?? '',
        })
      )
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleCreateNewNote = (withTranscription: boolean): void => {
    setIsTranscription(withTranscription)
    setShowCreateMeetingNote(true)
  }

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '50px' }}>
        <div>
          {!isEditing ? (
            <>
              <div>
                <Typography>
                  {t('meetings.meeting_start')}:{' '}
                  <strong>{formatDateNicely(selectedMeeting?.meetingStart)}</strong>
                </Typography>
                <Typography>
                  {t('meetings.duration')}:{' '}
                  <strong>
                    {getMinutesBetweenDates(
                      selectedMeeting?.meetingStart,
                      selectedMeeting?.meetingEnd
                    )}{' '}
                    {t('meetings.minutes')}
                  </strong>
                </Typography>
                <Typography>
                  {t('meetings.location')}: <strong>{selectedMeeting?.location}</strong>
                </Typography>
                <Typography>
                  {t('meetings.meetingStatus')}:{' '}
                  <strong> {t(`meetings.${selectedMeeting?.meetingStatus}`)}</strong>
                </Typography>
              </div>

              <Button
                onClick={() => setIsEditing(true)}
                sx={{ ...commonButtonStyles, marginTop: '20px' }}
              >
                <EditIcon sx={{ width: '15px', height: '15px', marginRight: '10px' }} />{' '}
                {t('meetings.editMeeting')}
              </Button>
            </>
          ) : (
            <MeetingEditing
              meeting={selectedMeeting}
              save={() => setIsEditing(false)}
              cancel={() => setIsEditing(false)}
            />
          )}
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div style={{ display: 'flex', gap: '30px', alignItems: 'center' }}>
            <Typography variant='h2'>{t('meetings.yourNotes')}:</Typography>
            <Button
              onClick={() => handleCreateNewNote(false)}
              disabled={showCreateMeetingNote}
              sx={{
                ...(showCreateMeetingNote ? disabledButtonStyles : commonButtonStyles),
              }}
            >
              {t('meetings.create_new_note')}
            </Button>

            <Button
              onClick={() => handleCreateNewNote(true)}
              disabled={showCreateMeetingNote}
              sx={{
                ...(showCreateMeetingNote ? successDisabledButtonStyles : successButtonStyles),
              }}
            >
              {t('meetings.transcribe_meeting')}
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
              <Typography>{t('meetings.no_notes_yet')}</Typography>
            </>
          )}
        </div>

        <CustomizedDivider />

        <Button onClick={handleDeleteMeeting} sx={{ ...deleteButtonStyles }}>
          {t('meetings.delete_meeting')}
        </Button>
      </div>
    </Layout>
  )
}

export default MeetingDetail
