import { Box, Button, TextField, Typography } from '@mui/material'
import { ReactElement } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { commonButtonStyles } from '../../styles/buttonStyles'
import GAD7TestTable from './GAD7TestTable'

export const GAD7TestDetail = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams()

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()
  }

  return (
    <Layout>
      <Box sx={{ marginBottom: '20px' }}>
        <Typography variant='body1' sx={{ marginBottom: '10px' }}>
          {t('gad7test.manage_description')}
        </Typography>
        <Box
          component='form'
          sx={{
            display: 'flex',
            alignItems: 'center',
            gap: '15px',
            maxWidth: '400px',
          }}
          onSubmit={handleSubmit}
        >
          <TextField label={t('gad7test.doEveryNDays')} type='number' sx={{ width: '200px' }} />
          <Button
            type='submit'
            variant='contained'
            sx={{
              ...commonButtonStyles,
              height: '56px',
            }}
          >
            {t('gad7test.submit')}
          </Button>
        </Box>
      </Box>
      <div style={{ marginTop: '40px' }}>
        <Typography variant='h2'>{t('gad7test.completed_gad7tests')}</Typography>

        <GAD7TestTable />
      </div>
    </Layout>
  )
}
