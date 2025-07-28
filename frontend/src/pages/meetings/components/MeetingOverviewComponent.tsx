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
import { format } from 'date-fns'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { useNotify } from '../../../hooks/useNotify'
import { getAllMeetingsOfPatient } from '../../../store/meetingSlice'
import { RootState } from '../../../store/store'
import { commonButtonStyles } from '../../../styles/buttonStyles'
import { isDateInThePast } from '../../../utils/dateUtil'
import { useAppDispatch } from '../../../utils/hooks'
import { getPathFromPage, PAGES } from '../../../utils/routes'

const MeetingOverviewComponent = (): ReactElement => {
  const { patientId } = useParams()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const { notifyError } = useNotify()

  useEffect((): void => {
    const load = async (): Promise<void> => {
      try {
        await dispatch(getAllMeetingsOfPatient(patientId ?? '')).unwrap()
      } catch (error) {
        notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
      }
    }
    void load()
  }, [dispatch, patientId, notifyError])

  const allMeetingsOfPatient = useSelector((state: RootState) => state.meeting.allMeetingsOfPatient)

  const handleCreateNewMeeting = (): void => {
    navigate(
      getPathFromPage(PAGES.MEETINGS_CREATE_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  const handleClickOnMeeting = (meetingId: string): void => {
    navigate(
      getPathFromPage(PAGES.MEETINGS_DETAILS_PAGE, {
        patientId: patientId ?? '',
        meetingId: meetingId,
      })
    )
  }

  return (
    <>
      <div
        style={{
          display: 'flex',
          gap: '30px',
          alignItems: 'center',
          marginBottom: '10px',
        }}
      >
        <Typography variant='h2'>{t('meetings.meetings')}</Typography>
        <Button sx={{ ...commonButtonStyles }} onClick={handleCreateNewMeeting}>
          {t('meetings.create_meeting')}
        </Button>
      </div>

      {allMeetingsOfPatient.length > 0 ? (
        <TableContainer component={Paper}>
          <Table aria-label='simple table' sx={{ tableLayout: 'fixed' }}>
            <TableHead>
              <TableRow>
                <TableCell>{t('meetings.meeting_start')}</TableCell>
                <TableCell>{t('meetings.meeting_end')}</TableCell>
                <TableCell>{t('meetings.meeting_location')}</TableCell>
                <TableCell align='center'>{t('meetings.is_past')}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {allMeetingsOfPatient.map((meeting) => (
                <TableRow
                  onClick={() => handleClickOnMeeting(meeting.id ?? '')}
                  key={meeting.id}
                  sx={{
                    '&:last-child td, &:last-child th': { border: 0 },
                    cursor: 'pointer',
                    backgroundColor: isDateInThePast(meeting.meetingEnd) ? '#e0e0e0' : '',
                  }}
                >
                  <TableCell component='th' scope='row'>
                    {meeting?.meetingStart
                      ? format(new Date(meeting.meetingStart), 'dd.MM.yyyy HH:mm', {
                          locale: de,
                        })
                      : '-'}
                  </TableCell>
                  <TableCell>
                    {meeting?.meetingEnd
                      ? format(new Date(meeting.meetingEnd), 'dd.MM.yyyy HH:mm', {
                          locale: de,
                        })
                      : '-'}
                  </TableCell>
                  <TableCell>{meeting.location}</TableCell>
                  <TableCell align='center'>
                    {isDateInThePast(meeting.meetingEnd) && <CheckIcon />}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography variant='body1'>{t('meetings.no_meetings_yet')}</Typography>
      )}
    </>
  )
}

export default MeetingOverviewComponent
