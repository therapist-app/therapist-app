import { Typography } from '@mui/material'
import { ReactElement } from 'react'
import { useTranslation } from 'react-i18next'

import Layout from '../../generalComponents/Layout'

const PatientCreate = (): ReactElement => {
  const { t } = useTranslation()

  return (
    <Layout>
      <Typography variant='h3'>{t('patient_create.title')}</Typography>
    </Layout>
  )
}

export default PatientCreate
