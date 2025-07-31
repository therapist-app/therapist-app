import { Box, Button, TextField, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { format } from 'date-fns'
import { de, enUS, uk } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'

import Layout from '../../generalComponents/Layout'
import { commonButtonStyles } from '../../styles/buttonStyles'
import GAD7TestTable from './GAD7TestTable'

const localeMap: Record<string, Locale> = {
  de: de,
  en: enUS,
  ua: uk,
}

export const GAD7TestDetail = (): ReactElement => {
  const { t } = useTranslation()
  const [intervalDays, setIntervalDays] = useState<number>(14) // default value
  const [startDate, setStartDate] = useState<Date | null>(new Date())

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()
    if (intervalDays < 1 || !startDate) {
      return
    }
    console.log('Submitting interval:', intervalDays, 'Start date:', startDate)
  }

  const local = localStorage.getItem('i18nextLng') || 'en'
  const currentLocale = localeMap[local.split('-')[0]] || enUS
  const formattedDate = startDate ? format(startDate, 'PPPp', { locale: currentLocale }) : ''

  return (
    <Layout>
      <Box sx={{ maxWidth: '500px' }}>
        <Typography variant='body1' sx={{ marginBottom: '20px' }}>
          {t('gad7test.manage_description')}
        </Typography>

        <Box
          component='form'
          sx={{
            display: 'flex',
            flexDirection: 'column',
            gap: '20px',
            maxWidth: '400px',
          }}
          onSubmit={handleSubmit}
        >
          <Box>
            <LocalizationProvider adapterLocale={currentLocale} dateAdapter={AdapterDateFns}>
              <DateTimePicker
                value={startDate}
                label={t('gad7test.start_date')}
                onChange={(newValue) => setStartDate(newValue)}
                sx={{ width: '100%' }}
              />
            </LocalizationProvider>
          </Box>

          <Box>
            <TextField
              label={t('gad7test.doEveryNDays')}
              type='number'
              value={intervalDays}
              onChange={(e) => setIntervalDays(Number(e.target.value))}
              sx={{ width: '100%' }}
              inputProps={{ min: 1 }}
              fullWidth
            />
          </Box>

          <Typography variant='body2' sx={{ mt: 1, color: 'text.secondary' }}>
            {t('gad7test.schedule_summary', {
              days: intervalDays,
              date: formattedDate,
            })}
          </Typography>

          <Button
            type='submit'
            variant='contained'
            sx={{
              ...commonButtonStyles,
              height: '40px',
              width: '200px',
              mt: 2,
            }}
          >
            {t('gad7test.save_changes')}
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
