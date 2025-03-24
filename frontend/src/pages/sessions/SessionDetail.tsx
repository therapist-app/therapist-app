import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'

const SessionDetail = () => {
  const { patientId, sessionId } = useParams()

  return (
    <Layout>
      <Typography variant='h3'>PatientId: "{patientId}"</Typography>
      <Typography variant='h4'>SessionId: {sessionId}</Typography>
    </Layout>
  )
}

export default SessionDetail
