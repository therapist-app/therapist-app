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
import { useNavigate, useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { getAllTherapySessionsOfPatient } from '../../store/therapySessionSlice'
import { useEffect } from 'react'
import { useAppDispatch } from '../../utils/hooks'
import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { getPathFromPage, PAGES } from '../../utils/routes'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'

const TherapySessionOverview = () => {
  const navigate = useNavigate()
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const allTherapySessionsOfPatient = useSelector(
    (state: RootState) => state.therapySession.allTherapySessionsOfPatient
  )

  useEffect(() => {
    dispatch(getAllTherapySessionsOfPatient(patientId ?? ''))
  }, [dispatch, patientId])

  const handleClickOnSession = (therapySessionId: string) => {
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_DETAILS_PAGE, {
        patientId: patientId ?? '',
        therapySessionId: therapySessionId,
      })
    )
  }

  const handleCreateNewSession = () => {
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_CREATE_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  return (
    <Layout>
      <Typography variant='h4'>Session Overview of patient: "{patientId}"</Typography>

      <Button
        sx={{ marginTop: '50px', marginBottom: '20px' }}
        variant='contained'
        onClick={handleCreateNewSession}
      >
        Create new Therapy Session
      </Button>
      <TableContainer sx={{ width: '600px' }} component={Paper}>
        <Table aria-label='simple table' sx={{ tableLayout: 'fixed' }}>
          <TableHead>
            <TableRow>
              <TableCell>Session Start</TableCell>
              <TableCell align='right'>Session End</TableCell>
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
                <TableCell align='right'>
                  {therapySession?.sessionEnd
                    ? format(new Date(therapySession.sessionEnd), 'dd.MM.yyyy HH:mm', {
                        locale: de,
                      })
                    : '-'}
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
