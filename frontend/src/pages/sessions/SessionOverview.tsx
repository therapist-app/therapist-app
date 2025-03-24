import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'

const SessionOverview = () => {
  const { patientId } = useParams()

  return (
    <Layout>
      <Typography variant='h3'>Session Overview of patient: "{patientId}"</Typography>
    </Layout>
  )
}

export default SessionOverview
