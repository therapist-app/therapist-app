import { Typography } from '@mui/material'

import Layout from '../../generalComponents/Layout'
import { useParams } from 'react-router-dom'

const SessionCreate = () => {
  const { patientId } = useParams()

  return (
    <Layout>
      <Typography variant='h3'>Create Session for patient: {patientId}</Typography>
    </Layout>
  )
}

export default SessionCreate
