import { Box, Button, TextField, Typography } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { commonButtonStyles } from '../../styles/buttonStyles'
import GAD7TestTable from './GAD7TestTable'

export const GAD7TestDetail = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams()
  const [intervalDays, setIntervalDays] = useState<number>(1)

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()
    if (intervalDays < 1) {
      return
    }
    console.log('Submitting interval:', intervalDays)
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
          <TextField 
            label={t('gad7test.doEveryNDays')} 
            type='number'
            value={intervalDays}
            sx={{ width: '200px' }}
            inputProps={{ min: 1 }}
          />
          <Button
            type='submit'
            variant='contained'
            sx={{
              ...commonButtonStyles,
              height: '40px',
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
