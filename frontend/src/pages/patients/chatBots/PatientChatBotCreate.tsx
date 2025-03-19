import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'
import Layout from '../../../generalComponents/Layout'

const PatientChatBotCreate = () => {
  const { patientId } = useParams()
  return (
    <Layout>
      <Typography variant='h3'>
        This page is for creating a new bot for patient with ID: "{patientId}"
      </Typography>
    </Layout>
  )
}

export default PatientChatBotCreate
