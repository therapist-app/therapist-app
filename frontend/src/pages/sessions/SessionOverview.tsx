import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { getAllTherapySessionsOfPatient } from '../../store/therapySessionSlice'
import { useEffect } from 'react'
import { useAppDispatch } from '../../utils/hooks'
import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'

const SessionOverview = () => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const allTherapySessionsOfPatient = useSelector(
    (state: RootState) => state.therapySession.allTherapySessionsOfPatient
  )

  useEffect(() => {
    dispatch(getAllTherapySessionsOfPatient(patientId ?? ''))
  }, [dispatch])

  return (
    <Layout>
      <Typography variant='h3'>Session Overview of patient: "{patientId}"</Typography>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        {allTherapySessionsOfPatient.map((therapySession) => (
          <div>
            <Typography>SessionId: {therapySession.id}</Typography>
            <Typography>Session Start: {therapySession.sessionStart}</Typography>
            <Typography>Session End: {therapySession.sessionEnd}</Typography>
          </div>
        ))}
      </div>
    </Layout>
  )
}

export default SessionOverview
