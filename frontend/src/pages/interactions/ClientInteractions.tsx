import {
  Box,
  Button,
  CircularProgress,
  FormControl,
  InputLabel,
  MenuItem,
  Paper,
  Select,
  Stack,
  Typography,
} from '@mui/material'
import { DatePicker } from '@mui/x-date-pickers'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { ResponsiveHeatMap } from '@nivo/heatmap'
import { eachDayOfInterval, format, isWithinInterval, subDays } from 'date-fns'
import { ReactElement, useEffect, useMemo, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'
import { LogType } from 'vite'

import Layout from '../../generalComponents/Layout'
import { LOG_TYPES } from '../../store/logTypes.ts'
import { LogOutputDTO } from '../../store/patientLogData.ts'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { patientLogApi } from '../../utils/api.ts'

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

const transformLogsToInteractionData = (
  logs: LogOutputDTO[] | undefined | null
): InteractionData[] => {
  // Handle cases where logs might be undefined or null
  if (!logs || !Array.isArray(logs)) {
    return []
  }

  return logs.map((log) => ({
    date: format(new Date(log.timestamp), 'yyyy-MM-dd'),
    hour: new Date(log.timestamp).getHours(),
    value: 1,
    type: log.logType,
  }))
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
  const { patientId } = useParams()
  const [error, setError] = useState<string | null>(null)
  const [startDate, setStartDate] = useState<Date | null>(subDays(new Date(), 14))
  const [endDate, setEndDate] = useState<Date | null>(new Date())
  const [activeLogType, setActiveLogType] = useState<LogType>('JOURNAL_CREATION' as LogType)

  // Separate states for initial loading vs background loading
  const [isInitialLoading, setIsInitialLoading] = useState(true)
  const [backgroundLoadingStates, setBackgroundLoadingStates] = useState<Record<LogType, boolean>>(
    LOG_TYPES.reduce(
      (acc, type) => ({ ...acc, [type]: false }), // Start all as false since we'll handle them separately
      {} as Record<LogType, boolean>
    )
  )

  const [logsByType, setLogsByType] = useState<Record<LogType, LogOutputDTO[]>>(
    {} as Record<LogType, LogOutputDTO[]>
  )

  useEffect(() => {
    if (!patientId) {
      return
    }
    setError(null)
    setIsInitialLoading(true)

    patientLogApi
      .listLogs(patientId, 'JOURNAL_CREATION')
      .then((response) => {
        const normalized = response.data.map((apiDto) => ({
          id: apiDto.id ?? '',
          patientId: apiDto.patientId ?? '',
          logType: apiDto.logType ?? '',
          timestamp: apiDto.timestamp ?? '',
          uniqueIdentifier: apiDto.uniqueIdentifier ?? '',
        }))
        setLogsByType((prev) => ({
          ...prev,
          JOURNAL_CREATION: normalized,
        }))
      })
      .catch((_) => {
        setLogsByType((prev) => ({ ...prev, JOURNAL_CREATION: [] }))
      })
      .finally(() => {
        // 2) As soon as JOURNAL_CREATION is back, drop the initial spinner
        setIsInitialLoading(false)

        // 3) …and *then* kick off every other log‐type, but do *not* await these
        LOG_TYPES.filter((t) => t !== 'JOURNAL_CREATION').forEach((logType) => {
          // mark it “loading” so your dropdown spinner can still show if the user clicks
          setBackgroundLoadingStates((prev) => ({ ...prev, [logType]: true }))

          patientLogApi
            .listLogs(patientId, logType)
            .then((response) => {
              const normalized = response.data.map((apiDto) => ({
                id: apiDto.id ?? '',
                patientId: apiDto.patientId ?? '',
                logType: apiDto.logType ?? '',
                timestamp: apiDto.timestamp ?? '',
                uniqueIdentifier: apiDto.uniqueIdentifier ?? '',
              }))
              setLogsByType((prev) => ({ ...prev, [logType]: normalized }))
            })
            .catch((_) => {
              setLogsByType((prev) => ({ ...prev, [logType]: [] }))
            })
            .finally(() => {
              setBackgroundLoadingStates((prev) => ({ ...prev, [logType]: false }))
            })
        })
      })
  }, [patientId, t])

  // Transform logs to interaction data
  const interactionData = useMemo(() => {
    const logsToUse = activeLogType
      ? logsByType[activeLogType] || []
      : Object.entries(logsByType)
          .filter(
            ([type]) => type === 'JOURNAL_CREATION' || !backgroundLoadingStates[type as LogType]
          ) // Only use loaded types
          .flatMap(([_, logs]) => logs)

    return transformLogsToInteractionData(logsToUse)
  }, [logsByType, activeLogType, backgroundLoadingStates])

  // Memoize filtered data with date range
  const filteredData = useMemo(() => {
    let filtered = interactionData

    if (activeLogType) {
      filtered = filtered.filter((d) => d.type === activeLogType)
    }

    if (startDate && endDate) {
      filtered = filtered.filter((d) => {
        const date = new Date(d.date)
        return isWithinInterval(date, { start: startDate, end: endDate })
      })
    }

    return filtered
  }, [interactionData, activeLogType, startDate, endDate])

  // Memoize heatmap data transformation
  const heatmapData = useMemo(
    () => transformDataForHeatmap(filteredData, startDate, endDate),
    [filteredData, startDate, endDate]
  )

  if (isInitialLoading) {
    return (
      <Layout>
        <Box display='flex' flexDirection='column' alignItems='center' height='200px'>
          <Typography>{t('patient_interactions.loading')}</Typography>
          <CircularProgress />
        </Box>
      </Layout>
    )
  }

  if (error) {
    return (
      <Layout>
        <Box display='flex' justifyContent='center' alignItems='center' height='200px'>
          <Typography color='error'>{error}</Typography>
        </Box>
      </Layout>
    )
  }

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
                  value={activeLogType || ''}
                  onChange={(e) => setActiveLogType(e.target.value as LogType)}
                  label={t('patient_interactions.log_types')}
                >
                  <MenuItem value=''>All Log Types</MenuItem>
                  {LOG_TYPES.map((logType) => (
                    <MenuItem key={logType} value={logType}>
                      <Box display='flex' alignItems='center'>
                        {logType !== 'JOURNAL_CREATION' &&
                          backgroundLoadingStates[logType as LogType] && (
                            <CircularProgress size={16} sx={{ mr: 1 }} />
                          )}
                        {t(`patient_interactions.log_types.${logType.toLowerCase()}`)}
                      </Box>
                    </MenuItem>
                  ))}
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
