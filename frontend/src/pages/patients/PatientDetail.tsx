import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import Layout from '../../generalComponents/Layout'
import FileUpload from '../../generalComponents/FileUpload'
import { useAppDispatch } from '../../utils/hooks'
import { createDocumentForPatient } from '../../store/patientDocumentSlice'

const PatientDetail = () => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const handleFileUpload = async (file: File) => {
    console.log(file)
    dispatch(
      createDocumentForPatient({
        file: file,
        patientId: patientId ?? '',
      })
    )
  }

  return (
    <Layout>
      <Typography variant='h3'>
        {t('patient_detail.message')}: "{patientId}"
      </Typography>

      <FileUpload onUpload={handleFileUpload} />
    </Layout>
  )
}

export default PatientDetail
