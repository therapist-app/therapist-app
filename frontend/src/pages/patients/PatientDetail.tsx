import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import Layout from '../../generalComponents/Layout'

const PatientDetail = () => {
  const { patientId } = useParams()
  const { t } = useTranslation()

  return (
    <Layout>
      <Typography variant='h3'>
        {t('patient_detail.message')}: "{patientId}"
      </Typography>
    </Layout>
  )
}

export default PatientDetail
