import { Box, Button, FormControl, InputLabel, MenuItem, Paper, Select, Stack } from '@mui/material'
import { DatePicker } from '@mui/x-date-pickers'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { ResponsiveHeatMap } from '@nivo/heatmap'
import { AxiosError } from 'axios'
import { eachDayOfInterval, format, isWithinInterval, subDays } from 'date-fns'
import { ReactElement, useEffect, useMemo, useState } from 'react'
import { useTranslation } from 'react-i18next'

import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { showError } from '../../store/errorSlice'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'

const { notifyError } = useNotify()

interface InteractionData {
  hour: number
  date: string
  value: number
  type: string
}

interface HeatMapData {
  id: string
  data: { x: string; y: number }[]
}

const generateMockData = (days: number): InteractionData[] => {
  const data: InteractionData[] = []
  const interactionTypes = [
    'Journal Creation',
    'Journal Update',
    'Exercise Start',
    'Exercise Completion',
    'Chatbot Creation',
    'Message Sent',
    'Chatbot Interaction',
  ]

  for (let i = 0; i < days; i++) {
    const date = subDays(new Date(), i)
    const dailyInteractions = Math.floor(Math.random() * 6)

    for (let j = 0; j < dailyInteractions; j++) {
      const hour = Math.floor(Math.random() * 24)
      const type = interactionTypes[Math.floor(Math.random() * interactionTypes.length)]

      data.push({
        date: format(date, 'yyyy-MM-dd'),
        hour: hour,
        value: 1,
        type: type,
      })
    }
  }
  return data
}

const transformDataForHeatmap = (
  data: InteractionData[],
  startDate: Date | null,
  endDate: Date | null
): HeatMapData[] => {
  const hours = Array.from({ length: 24 }, (_, i) => i)
  const daysMap = new Map<string, number[]>()

  if (startDate && endDate) {
    const allDates = eachDayOfInterval({ start: startDate, end: endDate })
    allDates.forEach((date) => {
      const shortDate = format(date, 'MM-dd')
      daysMap.set(shortDate, Array(24).fill(0))
    })
  }

  data.forEach((item) => {
    const shortDate = format(new Date(item.date), 'MM-dd')
    if (!daysMap.has(shortDate)) {
      daysMap.set(shortDate, Array(24).fill(0))
    }
    const dayData = daysMap.get(shortDate)!
    dayData[item.hour] += item.value
  })

  const sortedDates = Array.from(daysMap.keys()).sort((a, b) => {
    const findYear = (shortDate: string): string => {
      const match = data.find((item) => format(new Date(item.date), 'MM-dd') === shortDate)
      return match ? format(new Date(match.date), 'yyyy') : String(new Date().getFullYear())
    }
    const dateA = new Date(`${findYear(a)}-${a}`)
    const dateB = new Date(`${findYear(b)}-${b}`)
    return dateA.getTime() - dateB.getTime()
  })

  return hours.map((hour) => ({
    id: `${hour.toString().padStart(2, '0')}`,
    data: sortedDates.map((date) => ({
      x: date,
      y: daysMap.get(date)![hour],
    })),
  }))
}

const ClientInteractions = (): ReactElement => {
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const [interactionType, setInteractionType] = useState<string>('all')
  const [data] = useState<InteractionData[]>(() => {
    try {
      return generateMockData(15)
    } catch (error) {
      const msg = handleError(error as AxiosError)
      dispatch(showError({ message: msg, severity: 'error' }))
      return []
    }
  })
  const [startDate, setStartDate] = useState<Date | null>(subDays(new Date(), 14))
  const [endDate, setEndDate] = useState<Date | null>(new Date())

  useEffect(() => {
    if (startDate && endDate && startDate > endDate) {
      notifyError(t('patient_interactions.invalid_date_range'))
    }
  }, [startDate, endDate, t])

  const filteredData = useMemo(() => {
    try {
      let filtered = data

      if (interactionType !== 'all') {
        filtered = filtered.filter((d) => d.type === interactionType)
      }

      if (startDate && endDate) {
        filtered = filtered.filter((d) => {
          const date = new Date(d.date)
          return isWithinInterval(date, { start: startDate, end: endDate })
        })
      }

      return filtered
    } catch (error) {
      const msg = handleError(error as AxiosError)
      notifyError(msg)
      return []
    }
  }, [data, interactionType, startDate, endDate])

  const heatmapData = useMemo(() => {
    try {
      return transformDataForHeatmap(filteredData, startDate, endDate)
    } catch (error) {
      const msg = handleError(error as AxiosError)
      notifyError(msg)
      return []
    }
  }, [filteredData, startDate, endDate])

  return (
    <Layout>
      <Paper sx={{ p: 3, height: '800px' }}>
        <Stack spacing={2}>
          <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', mb: 2 }}>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <FormControl sx={{ minWidth: 250 }}>
                <InputLabel id='interaction-type-label'>
                  {t('patient_interactions.interaction_type')}
                </InputLabel>
                <Select
                  labelId='interaction-type-label'
                  value={interactionType}
                  label={t('patient_interactions.interaction_type')}
                  onChange={(e) => setInteractionType(e.target.value)}
                  size='small'
                >
                  <MenuItem value='all'>{t('All Interactions')}</MenuItem>
                  <MenuItem value='Journal Creation'>{t('Journal Creation')}</MenuItem>
                  <MenuItem value='Journal Update'>{t('Journal Update')}</MenuItem>
                  <MenuItem value='Exercise Start'>{t('Exercise Start')}</MenuItem>
                  <MenuItem value='Exercise Completion'>{t('Exercise Completion')}</MenuItem>
                  <MenuItem value='Chatbot Creation'>{t('Chatbot Creation')}</MenuItem>
                  <MenuItem value='Message Sent'>{t('Number of Messages')}</MenuItem>
                  <MenuItem value='Chatbot Interaction'>{t('Chatbot Interaction')}</MenuItem>
                </Select>
              </FormControl>
              <DatePicker
                label={t('patient_interactions.start_date')}
                value={startDate}
                onChange={(newValue) => setStartDate(newValue)}
                slotProps={{ textField: { size: 'small' } }}
                maxDate={endDate || undefined}
              />
              <DatePicker
                label={t('patient_interactions.end_date')}
                value={endDate}
                onChange={(newValue) => setEndDate(newValue)}
                slotProps={{ textField: { size: 'small' } }}
                minDate={startDate || undefined}
              />
            </LocalizationProvider>
          </Box>

          <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
            <Button
              sx={{ ...commonButtonStyles, minWidth: '160px' }}
              size='small'
              onClick={() => {
                setStartDate(subDays(new Date(), 7))
                setEndDate(new Date())
              }}
            >
              {t('patient_interactions.last_week')}
            </Button>
            <Button
              sx={{ ...commonButtonStyles, minWidth: '160px' }}
              size='small'
              onClick={() => {
                setStartDate(subDays(new Date(), 14))
                setEndDate(new Date())
              }}
            >
              {t('patient_interactions.last_two_weeks')}
            </Button>
            <Button
              sx={{ ...commonButtonStyles, minWidth: '160px' }}
              size='small'
              onClick={() => {
                setStartDate(subDays(new Date(), 21))
                setEndDate(new Date())
              }}
            >
              {t('patient_interactions.last_three_weeks')}
            </Button>
          </Box>

          <div style={{ height: '650px' }}>
            <ResponsiveHeatMap
              data={heatmapData}
              margin={{ top: 20, right: 40, bottom: 80, left: 80 }}
              valueFormat='>-.0f'
              axisTop={null}
              axisBottom={{
                tickSize: 5,
                tickPadding: 5,
                tickRotation: 0,
                legend: t('patient_interactions.date'),
                legendPosition: 'middle',
                legendOffset: 50,
              }}
              axisLeft={{
                tickSize: 5,
                tickPadding: 5,
                tickRotation: 0,
                legend: t('patient_interactions.time'),
                legendPosition: 'middle',
                legendOffset: -70,
                format: (value) => `${value}:00`,
              }}
              colors={{
                type: 'sequential',
                scheme: 'purples',
                minValue: 0,
                maxValue: 3,
              }}
              emptyColor='#f8f9fa'
              borderColor='#ffffff'
              borderWidth={1}
              enableLabels={false}
              animate={false}
              motionConfig='gentle'
            />
          </div>
        </Stack>
      </Paper>
    </Layout>
  )
}

export default ClientInteractions
