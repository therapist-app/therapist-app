import { Button, Typography, Stack } from '@mui/material'
import { useNavigate, useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { useEffect } from 'react'
import { deleteTherapySession, getTherapySession } from '../../store/therapySessionSlice'
import { useAppDispatch } from '../../utils/hooks'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'
import { getPathFromPage, PAGES } from '../../utils/routes'

const TherapySessionDetail = () => {
  const navigate = useNavigate()
  const { patientId, therapySessionId } = useParams()
  const dispatch = useAppDispatch()
  const selectedTherapySession = useSelector(
    (state: RootState) => state.therapySession.selectedTherapySession
  )

  useEffect(() => {
    dispatch(getTherapySession(therapySessionId ?? ''))
  }, [dispatch, therapySessionId])

  const handleDeleteTherapySession = async () => {
    await dispatch(deleteTherapySession(therapySessionId ?? ''))
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  return (
    <Layout>
      <Typography variant='h4' style={{ marginBottom: '20px' }}>
        Showing the session details of session: {therapySessionId}"
      </Typography>

      <Typography style={{ marginTop: '50px' }}>
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
