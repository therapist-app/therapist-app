import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { useEffect } from 'react'
import { getTherapySession } from '../../store/therapySessionSlice'
import { useAppDispatch } from '../../utils/hooks'

const TherapySessionDetail = () => {
  const { patientId, therapySessionId } = useParams()
  const dispatch = useAppDispatch()
  const selectedTherapySession = useSelector(
    (state: RootState) => state.therapySession.selectedTherapySession
  )

  useEffect(() => {
    dispatch(getTherapySession(therapySessionId ?? ''))
  }, [dispatch])

  return (
    <Layout>
      <Typography variant='h4' style={{ marginBottom: '20px' }}>
        Showing the session details of session: "{therapySessionId}" and patient: "{patientId}"
      </Typography>
      <Typography>Session start: {selectedTherapySession?.sessionStart}</Typography>
      <Typography>Session end: {selectedTherapySession?.sessionEnd}</Typography>
    </Layout>
  )
}

export default TherapySessionDetail
