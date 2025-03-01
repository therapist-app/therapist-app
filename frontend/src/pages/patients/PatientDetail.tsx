import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'
import { useTranslation } from 'react-i18next'

const PatientDetail = () => {
  const { patientId } = useParams()
  const { t } = useTranslation()

  return (
    <div>
      <Typography variant='h3'>
        {t('patient_detail.message')}: "{patientId}"
      </Typography>
    </div>
  )
}

export default PatientDetail
