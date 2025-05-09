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
import { RootState } from '../../store/store'
import { getAllTherapySessionsOfPatient } from '../../store/therapySessionSlice'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const TherapySessionOverview = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const allTherapySessionsOfPatient = useSelector(
    (state: RootState) => state.therapySession.allTherapySessionsOfPatient
  )

  useEffect(() => {
    dispatch(getAllTherapySessionsOfPatient(patientId ?? ''))
  }, [dispatch, patientId])

  const handleClickOnSession = (therapySessionId: string): void => {
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_DETAILS_PAGE, {
        patientId: patientId ?? '',
        therapySessionId: therapySessionId,
      })
    )
  }

  const handleCreateNewSession = (): void => {
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_CREATE_PAGE, {
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
            {allTherapySessionsOfPatient.map((therapySession) => (
              <TableRow
                onClick={() => handleClickOnSession(therapySession.id ?? '')}
                key={therapySession.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 }, cursor: 'pointer' }}
              >
                <TableCell component='th' scope='row'>
                  {therapySession?.sessionStart
                    ? format(new Date(therapySession.sessionStart), 'dd.MM.yyyy HH:mm', {
                        locale: de,
                      })
                    : '-'}
                </TableCell>
                <TableCell>
                  {therapySession?.sessionEnd
                    ? format(new Date(therapySession.sessionEnd), 'dd.MM.yyyy HH:mm', {
                        locale: de,
                      })
                    : '-'}
                </TableCell>
                <TableCell align='right'>
                  <IconButton
                    aria-label='download'
                    onClick={() => handleClickOnSession(therapySession.id ?? '')}
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

export default TherapySessionOverview
