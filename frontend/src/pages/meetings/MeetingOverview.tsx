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
} from '@mui/material'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { getAllMeetingsOfPatient } from '../../store/meetingSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const MeetingOverview = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const allMeetingsOfPatient = useSelector((state: RootState) => state.meeting.allMeetingsOfPatient)

  useEffect(() => {
    dispatch(getAllMeetingsOfPatient(patientId ?? ''))
  }, [dispatch, patientId])

  const handleClickOnSession = (meetingId: string): void => {
    navigate(
      getPathFromPage(PAGES.MEETINGS_DETAILS_PAGE, {
        patientId: patientId ?? '',
        meetingId: meetingId,
      })
    )
  }

  const handleCreateNewSession = (): void => {
    navigate(
      getPathFromPage(PAGES.MEETINGS_CREATE_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  return (
    <Layout>
      <Button sx={{ marginBottom: '20px' }} variant='contained' onClick={handleCreateNewSession}>
        Create new session
      </Button>
      <TableContainer sx={{ width: '600px' }} component={Paper}>
        <Table aria-label='simple table' sx={{ tableLayout: 'fixed' }}>
          <TableHead>
            <TableRow>
              <TableCell>Session Start</TableCell>
              <TableCell>Session End</TableCell>
              <TableCell align='right'>View</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allMeetingsOfPatient.map((meeting) => (
              <TableRow
                onClick={() => handleClickOnSession(meeting.id ?? '')}
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
                    onClick={() => handleClickOnSession(meeting.id ?? '')}
                  >
                    <VisibilityIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Layout>
  )
}

export default MeetingOverview
