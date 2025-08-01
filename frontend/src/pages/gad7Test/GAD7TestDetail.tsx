import { Box, Button, FormControlLabel, Switch, TextField, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { addWeeks } from 'date-fns'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { patientTestApi } from '../../utils/api'
import { formatDateWithLocale, getCurrentLocale } from '../../utils/dateUtil'
import GAD7TestTable from './GAD7TestTable'

export const GAD7TestDetail = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams()
  const { notifyError } = useNotify()
  const [intervalDays, setIntervalDays] = useState<number>(14) // default value
  const [startDate, setStartDate] = useState<Date | null>(new Date())
  const [endDate, setEndDate] = useState<Date | null>(addWeeks(new Date(), 8)) // default to 8 weeks later
  const [isPaused, setIsPaused] = useState<boolean>(false)

  const testName = 'GAD-7'

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    try {
      e.preventDefault()
      if (!patientId || !startDate || !endDate) {
        return
      }

      const dto = {
        patientId: patientId,
        testName: testName,
        exerciseStart: startDate.toISOString(),
        exerciseEnd: endDate.toISOString(),
        isPaused: isPaused,
        doEveryNDays: intervalDays,
      }

      const response = await patientTestApi.assignPsychologicalTestToPatient(
        patientId,
        testName,
        dto
      )
      console.log('assignment response', response)
    } catch {
      notifyError(t('gad7test.failed_to_assign_tests'))
    }
  }

  const currentLocale = getCurrentLocale()
  const formattedStartDate = formatDateWithLocale(startDate)
  const formattedEndDate = formatDateWithLocale(endDate)

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
            <LocalizationProvider adapterLocale={currentLocale} dateAdapter={AdapterDateFns}>
              <DateTimePicker
                value={endDate}
                label={t('gad7test.end_date')}
                onChange={(newValue) => setEndDate(newValue)}
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

          <Box>
            <FormControlLabel
              control={
                <Switch
                  checked={isPaused}
                  onChange={(e) => setIsPaused(e.target.checked)}
                  color='primary'
                />
              }
              label={t('gad7test.pause_test_schedule')}
              labelPlacement='start'
              sx={{ justifyContent: 'space-between', width: '100%', ml: 0 }}
            />
          </Box>

          <Typography variant='body2' sx={{ mt: 1, color: 'text.secondary' }}>
            {t('gad7test.schedule_summary', {
              days: intervalDays,
              start_date: formattedStartDate,
              end_date: formattedEndDate,
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
