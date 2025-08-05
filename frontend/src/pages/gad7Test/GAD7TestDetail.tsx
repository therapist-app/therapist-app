import { Box, Button, FormControlLabel, Switch, TextField, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { addWeeks } from 'date-fns'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import LoadingSpinner from '../../generalComponents/LoadingSpinner'
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
  const [hasExistingTest, setHasExistingTest] = useState<boolean>(false)
  const [isLoading, setIsLoading] = useState<boolean>(true)

  const testName = 'GAD7'

  useEffect(() => {
    if (!patientId) {
      return
    }

    const fetchTestConfig = async (): Promise<void> => {
      try {
        setIsLoading(true)
        const response = await patientTestApi.getPsychologicalTestToPatient(patientId, testName)
        const testConfig = response.data

        if (testConfig) {
          setStartDate(testConfig.exerciseStart ? new Date(testConfig.exerciseStart) : new Date())
          setEndDate(
            testConfig.exerciseEnd ? new Date(testConfig.exerciseEnd) : addWeeks(new Date(), 8)
          )
          setIsPaused(testConfig.isPaused ?? false)
          setIntervalDays(testConfig.doEveryNDays ?? 14)
          setHasExistingTest(true)
        } else {
          setHasExistingTest(false)
        }
      } catch {
        notifyError(t('gad7test.failed_to_load_test_configuration'))
      } finally {
        setIsLoading(false)
      }
    }

    fetchTestConfig()
  }, [patientId, notifyError, t])

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
      setHasExistingTest(true)
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

  if (isLoading) {
    return (
      <Layout>
        <Typography variant='body1'>{t('gad7test.loading_configuration')}</Typography>
        <LoadingSpinner />
      </Layout>
    )
  }

  return (
    <Layout>
      <Typography variant='body1' sx={{ mb: 3 }}>
        {t('gad7test.manage_description')}
      </Typography>
      <Box sx={{ maxWidth: '500px' }}>
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
            onChange={(e) => setIntervalDays(parseInt(e.target.value) || 0)}
            fullWidth
          />

          {!hasExistingTest ? null : (
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
          )}

          {isPaused && (
            <Typography variant='body2' sx={{ color: 'text.secondary' }}>
              {t('gad7test.schedule_is_paused')}
            </Typography>
          )}
          {!isPaused && (
            <Typography variant='body2' sx={{ color: 'text.secondary' }}>
              {t('gad7test.schedule_summary', {
                days: intervalDays,
                start_date: formattedStartDate,
                end_date: formattedEndDate,
              })}
            </Typography>
          )}
          {hasExistingTest ? (
            <Button
              onClick={handleUpdateTest}
              variant='contained'
              sx={{ ...commonButtonStyles, height: 40, width: 200, mt: 2 }}
            >
              {t('gad7test.save_changes')}
            </Button>
          ) : (
            <Button
              onClick={handleCreateTest}
              variant='contained'
              sx={{ ...commonButtonStyles, height: 40, width: 200, mt: 2 }}
            >
              {t('gad7test.assign_tests')}
            </Button>
          )}
        </Box>
      </Box>

      <Box mt={4}>
        <Typography variant='h2'>{t('gad7test.completed_gad7tests')}</Typography>
        <GAD7TestTable />
      </Box>
    </Layout>
  )
}
