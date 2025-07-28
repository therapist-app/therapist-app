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
import { useNotify } from '../../hooks/useNotify'
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

// eslint-disable-next-line
const HeatmapTooltip = ({ cell }: { cell: any }) => {
  const { t } = useTranslation()
  const [hourPart, datePart] = cell.id.split('.')
  const [month, day] = datePart.split('-')

  const formattedDate = `${day}.${month}.${new Date().getFullYear()}`
  const paddedHour = hourPart.padStart(2, '0')
  const timeRange = `${paddedHour}:00 - ${paddedHour}:59`

  return (
    <div
      style={{
        fontFamily: 'Roboto, sans-serif',
        padding: '8px 12px',
        background: 'white',
        borderRadius: '4px',
        boxShadow: '0 2px 6px rgba(0,0,0,0.15)',
        color: '#333',
        width: '120px',
      }}
    >
      <div style={{ fontWeight: 'bold', marginBottom: '4px' }}>{formattedDate}</div>
      <div style={{ marginBottom: '4px' }}>{timeRange}</div>
      <div>
        {t('patient_interactions.interactions')}: <strong>{cell.value}</strong>
      </div>
    </div>
  )
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
  const { notifyError } = useNotify()
  const { patientId } = useParams()
  const [error, setError] = useState<string | null>(null)
  const [startDate, setStartDate] = useState<Date | null>(subDays(new Date(), 14))
  const [endDate, setEndDate] = useState<Date | null>(new Date())
  const [activeLogType, setActiveLogType] = useState<LogType>('JOURNAL_CREATION' as LogType)

  const [loadingStates, setLoadingStates] = useState<Record<LogType, boolean>>(
    LOG_TYPES.reduce((acc, type) => ({ ...acc, [type]: true }), {} as Record<LogType, boolean>)
  )

  const isLoading = useMemo(() => {
    return Object.values(loadingStates).some(Boolean)
  }, [loadingStates])

  const [logsByType, setLogsByType] = useState<Record<LogType, LogOutputDTO[]>>(
    {} as Record<LogType, LogOutputDTO[]>
  )

  useEffect(() => {
    if (!patientId) {
      return
    }

    const abortController = new AbortController()

    const fetchData = async (): Promise<void> => {
      try {
        setError(null)
        setLoadingStates(
          LOG_TYPES.reduce(
            (acc, type) => ({ ...acc, [type]: true }),
            {} as Record<LogType, boolean>
          )
        )

        const fetchPromises = LOG_TYPES.map(async (logType) => {
          try {
            const response = await patientLogApi.listLogs(patientId, logType, {
              signal: abortController.signal,
            })

            const normalizedData = response.data.map((apiDto) => ({
              id: apiDto.id || '',
              patientId: apiDto.patientId || '',
              logType: apiDto.logType || '',
              timestamp: apiDto.timestamp || '',
              uniqueIdentifier: apiDto.uniqueIdentifier || '',
            }))

            if (!abortController.signal.aborted) {
              setLogsByType((prev) => ({
                ...prev,
                [logType]: normalizedData,
              }))
            }
          } catch (err) {
            if (abortController.signal.aborted) {
              return
            }

            console.error(`Error fetching ${logType} logs:`, err)
            if (!abortController.signal.aborted) {
              setLogsByType((prev) => ({
                ...prev,
                [logType]: [],
              }))
              notifyError('Failed to fetch client interactions.')
            }
          } finally {
            if (!abortController.signal.aborted) {
              setLoadingStates((prev) => ({
                ...prev,
                [logType]: false,
              }))
            }
          }
        })

        await Promise.all(fetchPromises)
      } catch (err) {
        if (abortController.signal.aborted) {
          return
        }
        console.error('Error in fetchAllLogTypes:', err)
        setError(t('patient_interactions.fetch_error'))
      }
    }

    fetchData()

    return (): void => {
      abortController.abort()
    }
  }, [patientId, t, notifyError])

  // Transform logs to interaction data
  const interactionData = useMemo(() => {
    const logsToUse = activeLogType
      ? logsByType[activeLogType] || []
      : Object.values(logsByType).flatMap((logs) => logs)

    return transformLogsToInteractionData(logsToUse)
  }, [logsByType, activeLogType])

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

  const maxValue = useMemo(() => {
    if (!heatmapData || heatmapData.length === 0) {
      return 3
    }
    const calculatedMax = Math.max(
      ...heatmapData.flatMap((hourData) => hourData.data.map((d) => d.y))
    )
    return Math.max(calculatedMax, 3) // Ensure minimum maxValue of 3
  }, [heatmapData])

  if (isLoading) {
    return (
      <Layout>
        <Box display='flex' flexDirection='column' alignItems='center' height='200px'>
          <Typography>{t('patient_interactions.loading')}</Typography>
          <div style={{ marginTop: '20px' }}>
            <CircularProgress />
          </div>
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
                  {LOG_TYPES.map((logType) => (
                    <MenuItem key={logType} value={logType}>
                      {t(`patient_interactions.log_types.${logType.toLowerCase()}`)}
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
                maxValue: maxValue,
              }}
              emptyColor='#f8f9fa'
              borderColor='#ffffff'
              borderWidth={1}
              enableLabels={false}
              animate={false}
              motionConfig='gentle'
              tooltip={HeatmapTooltip}
            />
          </div>
        </Stack>
      </Paper>
    </Layout>
  )
}

export default ClientInteractions
