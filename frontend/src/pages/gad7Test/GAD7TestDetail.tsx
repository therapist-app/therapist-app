import { Box, Button, FormControlLabel, Switch, TextField, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { addWeeks } from 'date-fns'
import { ReactElement, useEffect, useState } from 'react'
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
  const { patientId } = useParams<{ patientId: string }>()
  const { notifyError, notifySuccess } = useNotify()

  const [intervalDays, setIntervalDays] = useState<number>(14)
  const [startDate, setStartDate] = useState<Date | null>(new Date())
  const [endDate, setEndDate] = useState<Date | null>(addWeeks(new Date(), 8))
  const [isPaused, setIsPaused] = useState<boolean>(false)
  const [existingTest, setExistingTest] = useState<string | null>(null)

  const testName = 'GAD7'

  useEffect(() => {
    if (!patientId) {
      return
    }
    patientTestApi
      .getPsychologicalTestNamesForPatient(patientId)
      .then((tests) => {
        const found = tests.data.find((t) => t.name === testName)
        if (found && found.name) {
          console.log('found', found)
          setExistingTest(found.name.toString())
        }
      })
      .catch((err) => {
        console.error('Failed to load existing tests', err)
        notifyError(t('gad7test.failed_to_load_tests'))
      })
  }, [patientId, testName, notifyError, t])

  console.log('Existing test:', existingTest)
  const handleCreateTest = async (e: React.FormEvent): Promise<void> => {
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

      await patientTestApi.assignPsychologicalTestToPatient(patientId, testName, dto)
      notifySuccess(t('gad7test.assigned_successfully'))
      setExistingTest(testName)
    } catch (error) {
      console.error('Error creating GAD-7 test:', error)
      notifyError(t('gad7test.failed_to_assign_tests'))
    }
  }

  const handleUpdateTest = async (e: React.FormEvent): Promise<void> => {
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

      await patientTestApi.updatePsychologicalTestToPatient(patientId, testName, dto)
      notifySuccess(t('gad7test.updated_successfully'))
    } catch (error) {
      console.error('Error updating GAD-7 test:', error)
      notifyError(t('gad7test.failed_to_update_tests'))
    }
  }

  const currentLocale = getCurrentLocale()
  const formattedStartDate = formatDateWithLocale(startDate)
  const formattedEndDate = formatDateWithLocale(endDate)

  return (
    <Layout>
      <Box sx={{ maxWidth: '500px' }}>
        <Typography variant='body1' sx={{ mb: 2 }}>
          {t('gad7test.manage_description')}
        </Typography>

        <Box
          component='form'
          sx={{ display: 'flex', flexDirection: 'column', gap: 2, maxWidth: 400 }}
        >
          <LocalizationProvider adapterLocale={currentLocale} dateAdapter={AdapterDateFns}>
            <DateTimePicker
              value={startDate}
              label={t('gad7test.start_date')}
              onChange={(newValue) => setStartDate(newValue)}
              sx={{ width: '100%' }}
            />
            <DateTimePicker
              value={endDate}
              label={t('gad7test.end_date')}
              onChange={(newValue) => setEndDate(newValue)}
              sx={{ width: '100%' }}
            />
          </LocalizationProvider>

          <TextField
            label={t('gad7test.doEveryNDays')}
            type='number'
            value={intervalDays}
            onChange={(e) => setIntervalDays(Number(e.target.value))}
            inputProps={{ min: 1 }}
            fullWidth
          />

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
            sx={{ justifyContent: 'space-between', width: '100%' }}
          />

          <Typography variant='body2' sx={{ color: 'text.secondary' }}>
            {t('gad7test.schedule_summary', {
              days: intervalDays,
              start_date: formattedStartDate,
              end_date: formattedEndDate,
            })}
          </Typography>

          <Button
            onClick={handleUpdateTest}
            variant='contained'
            sx={{ ...commonButtonStyles, height: 40, width: 200, mt: 2 }}
          >
            {t('gad7test.save_changes')}
          </Button>
          <Button
            onClick={handleCreateTest}
            variant='contained'
            sx={{ ...commonButtonStyles, height: 40, width: 200, mt: 2 }}
          >
            {t('gad7test.assign_tests')}
          </Button>
        </Box>
      </Box>

      <Box mt={4}>
        <Typography variant='h2'>{t('gad7test.completed_gad7tests')}</Typography>
        <GAD7TestTable />
      </Box>
    </Layout>
  )
}
