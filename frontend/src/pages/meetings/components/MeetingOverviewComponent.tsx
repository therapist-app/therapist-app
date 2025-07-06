import VisibilityIcon from '@mui/icons-material/Visibility'
import {
  Button,
  IconButton,
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
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { getAllMeetingsOfPatient } from '../../../store/meetingSlice'
import { RootState } from '../../../store/store'
import { useAppDispatch } from '../../../utils/hooks'
import { getPathFromPage, PAGES } from '../../../utils/routes'

const MeetingOverviewComponent = (): ReactElement => {
  const { patientId } = useParams()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  useEffect(() => {
    dispatch(getAllMeetingsOfPatient(patientId ?? ''))
  }, [dispatch, patientId])

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
        <Typography variant='h4'> Meetings</Typography>
        <Button variant='contained' onClick={handleCreateNewMeeting}>
          Create new Meeting
        </Button>
      </div>
      <TableContainer sx={{ width: '600px' }} component={Paper}>
        <Table aria-label='simple table' sx={{ tableLayout: 'fixed' }}>
          <TableHead>
            <TableRow>
              <TableCell>Meeting Start</TableCell>
              <TableCell>Meeting End</TableCell>
              <TableCell align='right'>View</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allMeetingsOfPatient.map((meeting) => (
              <TableRow
                onClick={() => handleClickOnMeeting(meeting.id ?? '')}
                key={meeting.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 }, cursor: 'pointer' }}
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
                <TableCell align='right'>
                  <IconButton
                    aria-label='download'
                    onClick={() => handleClickOnMeeting(meeting.id ?? '')}
                  >
                    <VisibilityIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  )
}

export default MeetingOverviewComponent
