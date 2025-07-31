import {
  Box,
  Button,
  CircularProgress,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  Typography,
} from '@mui/material'
import { DatePicker } from '@mui/x-date-pickers'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { ResponsiveHeatMapCanvas } from '@nivo/heatmap'
import { eachDayOfInterval, format, isWithinInterval, subDays } from 'date-fns'
import { ReactElement, useCallback, useEffect, useMemo, useState } from 'react'
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

const transformDataForHeatmap = (data: InteractionData[], dateRange: Date[]): HeatMapData[] => {
  const hourMap = new Map<number, Map<string, number>>()

  Array.from({ length: 24 }).forEach((_, hour) => {
    hourMap.set(hour, new Map())
  })

  data.forEach((item) => {
    const shortDate = format(new Date(item.date), 'MM-dd')
    const hourData = hourMap.get(item.hour)
    if (hourData) {
      hourData.set(shortDate, (hourData.get(shortDate) || 0) + item.value)
    }
  })

  return Array.from(hourMap.entries()).map(([hour, dateCounts]) => ({
    id: hour.toString().padStart(2, '0'),
    data: dateRange.map((date) => ({
      x: format(date, 'MM-dd'),
      y: dateCounts.get(format(date, 'MM-dd')) || 0,
    })),
  }))
}

const ClientInteractions = (): ReactElement => {
  const { t } = useTranslation()
  const { notifyError } = useNotify()
  const { patientId } = useParams()
  const [startDate, setStartDate] = useState<Date | null>(subDays(new Date(), 14))
  const [endDate, setEndDate] = useState<Date | null>(new Date())
  const [activeLogType, setActiveLogType] = useState<LogType>('JOURNAL_CREATION' as LogType)

  const [loadedLogTypes, setLoadedLogTypes] = useState<Set<LogType>>(new Set())
  const [loading, setLoading] = useState<boolean>(true)
  const [logsByType, setLogsByType] = useState<Record<LogType, LogOutputDTO[]>>(
    {} as Record<LogType, LogOutputDTO[]>
  )

  // Memoize the date range calculation
  const dateRange = useMemo(() => {
    return startDate && endDate ? eachDayOfInterval({ start: startDate, end: endDate }) : []
  }, [startDate, endDate])

  // Fetch data for a specific log type
  const fetchLogTypeData = useCallback(
    async (logType: LogType) => {
      if (!patientId || loadedLogTypes.has(logType)) {
        return
      }

      try {
        setLoading(true)
        const response = await patientLogApi.listLogs(patientId, logType)
        const normalized = response.data.map((apiDto) => ({
          id: apiDto.id || '',
          patientId: apiDto.patientId || '',
          logType: apiDto.logType || '',
          timestamp: apiDto.timestamp || '',
          uniqueIdentifier: apiDto.uniqueIdentifier || '',
        }))

        setLogsByType((prev) => ({ ...prev, [logType]: normalized }))
        setLoadedLogTypes((prev) => new Set(prev).add(logType))
      } catch (err) {
        console.error(err)
        notifyError('Failed to fetch client interactions.')
        setLogsByType((prev) => ({ ...prev, [logType]: [] }))
      } finally {
        setLoading(false)
      }
    },
    [patientId, loadedLogTypes, notifyError]
  )

  // Initial load - fetch only JOURNAL_CREATION data
  useEffect(() => {
    if (patientId) {
      fetchLogTypeData('JOURNAL_CREATION' as LogType)
    }
  }, [patientId, fetchLogTypeData])

  const handleLogTypeChange = async (logType: LogType): Promise<void> => {
    setActiveLogType(logType)
    if (!loadedLogTypes.has(logType)) {
      await fetchLogTypeData(logType)
    }
    console.log('Heatmap data for:', logType, heatmapData)
  }

  // Transform logs to interaction data
  const interactionData = useMemo(() => {
    const logsToUse = logsByType[activeLogType] || []
    return transformLogsToInteractionData(logsToUse)
  }, [logsByType, activeLogType])

  // Memoize filtered data with date range
  const filteredData = useMemo(() => {
    let filtered = interactionData

    if (startDate && endDate) {
      filtered = filtered.filter((d) => {
        const date = new Date(d.date)
        return isWithinInterval(date, { start: startDate, end: endDate })
      })
    }

    return filtered
  }, [interactionData, startDate, endDate])

  // Memoize heatmap data transformation
  const heatmapData = useMemo(
    () => transformDataForHeatmap(filteredData, dateRange),
    [filteredData, dateRange]
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

  return (
    <Layout>
      <Stack spacing={2}>
        <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', mb: 2 }}>
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <FormControl sx={{ minWidth: 250 }}>
              <InputLabel id='interaction-type-label'>
                {t('patient_interactions.interaction_type')}
              </InputLabel>
              <Select
                value={activeLogType || ''}
                onChange={(e) => handleLogTypeChange(e.target.value as LogType)}
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

        {loading ? (
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              marginTop: '50px',
            }}
          >
            <Typography>{t('patient_interactions.loading')}</Typography>
            <div style={{ marginTop: '20px' }}>
              <CircularProgress />
            </div>
          </div>
        ) : (
          <div style={{ height: '650px' }}>
            <ResponsiveHeatMapCanvas
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
        )}
      </Stack>
    </Layout>
  )
}

export default ClientInteractions
