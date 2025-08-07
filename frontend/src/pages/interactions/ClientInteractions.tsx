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
import { AxiosResponse } from 'axios'
import { eachDayOfInterval, format, isWithinInterval, subDays } from 'date-fns'
import { ReactElement, useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import { LogType } from 'vite'

import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { LOG_TYPES } from '../../store/logTypes.ts'
import { LogOutputDTO } from '../../store/patientLogData.ts'
import { RootState } from '../../store/store.ts'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { patientLogApi } from '../../utils/api.ts'
import { getCurrentLocale } from '../../utils/dateUtil.ts'

interface InteractionData {
  hour: number
  date: string
  value: number
  type: string
  logs: LogOutputDTO[]
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
  const [positionStyle, setPositionStyle] = useState<React.CSSProperties>({})

  // Get mouse position and calculate transform
  const tooltipRef = useRef<HTMLDivElement>(null)

  const handleMouseMove = (e: React.MouseEvent) => {
    if (!tooltipRef.current) return
    
    const viewportWidth = window.innerWidth
    const mouseX = e.clientX
    const tooltipRect = tooltipRef.current.getBoundingClientRect()
    const isRightHalf = mouseX > viewportWidth / 2
    
    console.group('Tooltip Positioning Debug')
    console.log('Viewport width:', viewportWidth)
    console.log('Mouse X:', mouseX)
    console.log('Tooltip width:', tooltipRect.width)
    console.log('Screen half:', isRightHalf ? 'Right' : 'Left')
    
    const transformValue = isRightHalf ? 'translate(-50%, 0)' : 'translate(50%, 0)'
    tooltipRef.current.style.transform = transformValue
    console.groupEnd()
  }

  const formattedDate = format(new Date(`${new Date().getFullYear()}-${month}-${day}`), 'PP', {
    locale: getCurrentLocale(),
  })
  const paddedHour = hourPart.padStart(2, '0')
  const timeRange = `${paddedHour}:00 - ${paddedHour}:59`

  const logs: LogOutputDTO[] = [
    {
      id: '1afd9acd-f66e-448a-866c-2be373aa6dd0',
      patientId: 'ab66701f-8b23-433d-a7a4-3959eb0a80c1',
      logType: 'HARMFUL_CONTENT_DETECTED',
      timestamp: '2025-08-06T06:05:20.292098Z',
      uniqueIdentifier: '',
      comment: 'Potentially harmful message: "i will kill myself"',
    },
    {
      id: '3ef0eb91-69f9-4d1a-9010-7cbdb7b52022',
      patientId: 'ab66701f-8b23-433d-a7a4-3959eb0a80c1',
      logType: 'HARMFUL_CONTENT_DETECTED',
      timestamp: '2025-08-06T06:05:26.513182Z',
      uniqueIdentifier: '',
      comment:
        'Potentially harmful message: "no my bomb is finish and i will detonate in 40 seconds"',
    },
  ]

  const formatTime = (timestamp: string): string => {
    return format(new Date(timestamp), 'HH:mm:ss', {
      locale: getCurrentLocale(),
    })
  }

  const hasHarmfulContent = logs.some(
    (log) => log.logType === 'HARMFUL_CONTENT_DETECTED' && log.comment !== ''
  )

  return (
    <div
      style={{
        fontFamily: 'Roboto, sans-serif',
        pointerEvents: 'auto',
        padding: '12px',
        background: 'white',
        borderRadius: '4px',
        boxShadow: '0 2px 6px rgba(0,0,0,0.15)',
        color: '#333',
        width: hasHarmfulContent ? '400px' : '120px',
        ...positionStyle,
      }}
      onMouseMove={handleMouseMove}
      ref={tooltipRef}  
    >
      <div style={{ fontWeight: 'bold', marginBottom: '4px' }}>{formattedDate}</div>
      <div style={{ marginBottom: '4px' }}>{timeRange}</div>
      <div style={{ marginBottom: '8px' }}>
        {t('patient_interactions.interactions')}: <strong>{cell.value}</strong>
      </div>

      {hasHarmfulContent && (
        <div style={{ marginTop: '8px' }}>
          <div style={{ fontWeight: '500', marginTop: '15px', marginBottom: '4px' }}>
            {t('patient_interactions.harmful_content_alert')}:
          </div>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '4px' }}>
            {logs
              .filter((log) => log.logType === 'HARMFUL_CONTENT_DETECTED' && log.comment)
              .map((log, index) => (
                <div
                  key={index}
                  style={{
                    padding: '4px',
                    background: '#fff8f8',
                    borderRadius: '4px',
                    wordBreak: 'break-word',
                    maxWidth: '100%',
                  }}
                >
                  <div style={{ fontWeight: '500', marginBottom: '2px' }}>
                    {formatTime(log.timestamp)}:
                  </div>
                  {log.comment}
                </div>
              ))}
          </div>
        </div>
      )}
    </div>
  )
}

const transformLogsToInteractionData = (
  logs: LogOutputDTO[] | undefined | null
): InteractionData[] => {
  if (!logs || !Array.isArray(logs)) {
    return []
  }

  // Group logs by date and hour
  const grouped = logs.reduce(
    (acc, log) => {
      const date = format(new Date(log.timestamp), 'yyyy-MM-dd')
      const hour = new Date(log.timestamp).getHours()
      const key = `${date}-${hour}`

      if (!acc[key]) {
        acc[key] = {
          date: date,
          hour: hour,
          value: 0,
          type: log.logType,
          logs: [],
        }
      }
      acc[key].value++
      acc[key].logs.push(log)
      return acc
    },
    {} as Record<string, InteractionData>
  )

  return Object.values(grouped)
}

const transformDataForHeatmap = (data: InteractionData[], dateRange: Date[]): HeatMapData[] => {
  const hourMap = new Map<number, Map<string, { y: number; logs: LogOutputDTO[] }>>()

  Array.from({ length: 24 }).forEach((_, hour) => {
    hourMap.set(hour, new Map())
  })

  data.forEach((item) => {
    const shortDate = format(new Date(item.date), 'MM-dd')
    const hourData = hourMap.get(item.hour)
    if (hourData) {
      const existing = hourData.get(shortDate) || { y: 0, logs: [] }
      hourData.set(shortDate, {
        y: existing.y + item.value,
        logs: [...existing.logs, ...item.logs],
      })
    }
  })

  return Array.from(hourMap.entries()).map(([hour, dateCounts]) => ({
    id: hour.toString().padStart(2, '0'),
    data: dateRange.map((date) => {
      const shortDate = format(date, 'MM-dd')
      const countData = dateCounts.get(shortDate) || { y: 0, logs: [] }
      return {
        x: shortDate,
        y: countData.y,
        logs: countData.logs,
      }
    }),
  }))
}

const ClientInteractions = (): ReactElement => {
  const { t } = useTranslation()
  const { notifyError } = useNotify()
  const { patientId } = useParams()
  const currentLocale = getCurrentLocale()
  const [startDate, setStartDate] = useState<Date | null>(subDays(new Date(), 14))
  const [endDate, setEndDate] = useState<Date | null>(new Date())
  const [activeLogType, setActiveLogType] = useState<LogType>('JOURNAL_CREATION' as LogType)

  const [loadedLogTypes, setLoadedLogTypes] = useState<Set<LogType>>(new Set())
  const [loading, setLoading] = useState<boolean>(true)
  const [logsByType, setLogsByType] = useState<Record<LogType, LogOutputDTO[]>>(
    {} as Record<LogType, LogOutputDTO[]>
  )

  const patient = useSelector((state: RootState) =>
    state.patient.allPatientsOfTherapist.find((p) => p.id === patientId?.toString())
  )

  // Memoize the date range calculation
  const dateRange = useMemo(() => {
    return startDate && endDate ? eachDayOfInterval({ start: startDate, end: endDate }) : []
  }, [startDate, endDate])
  const dayDifference = dateRange.length

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
          comment: apiDto.comment || '',
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

  const exportToCSV = async (): Promise<void> => {
    if (!patientId) {
      return
    }

    try {
      const response = (await patientLogApi.exportAllLogsCsv(patientId, {
        responseType: 'blob',
      })) as unknown as AxiosResponse<Blob>
      const blob = response.data
      const url = window.URL.createObjectURL(blob)

      const link = document.createElement('a')
      link.href = url

      const today = new Date().toISOString().slice(0, 10)
      link.download = `${patient?.name}-client-logs-${today}.csv`

      document.body.appendChild(link)
      link.click()
      link.remove()
      window.URL.revokeObjectURL(url)
    } catch {
      notifyError('Failed to export logs to CSV.')
    }
  }

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
    <div style={{ overflow: 'hidden' }}>
      <Layout>
        <Stack spacing={2}>
          <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', mb: 2 }}>
            <LocalizationProvider adapterLocale={currentLocale} dateAdapter={AdapterDateFns}>
              <FormControl sx={{ minWidth: 250, marginTop: '5px' }}>
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
              <Box sx={{ flexGrow: 1 }} />
              <Button
                component='a'
                onClick={exportToCSV}
                target='_blank'
                rel='noopener noreferrer'
                sx={{ ...commonButtonStyles }}
                size='small'
              >
                {t('patient_interactions.export_to_csv')}
              </Button>
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
                margin={{
                  top: 20,
                  right: 40,
                  bottom: dayDifference > 50 ? 120 : dayDifference > 28 ? 90 : 80,
                  left: 80,
                }}
                valueFormat='>-.0f'
                axisTop={null}
                axisBottom={{
                  tickSize: 5,
                  tickPadding: 5,
                  tickRotation: dayDifference > 50 ? 90 : dayDifference > 28 ? 45 : 0,
                  legend: t('patient_interactions.date'),
                  legendPosition: 'middle',
                  legendOffset: dayDifference > 50 ? 80 : 50,
                  format: (value) =>
                    format(new Date(`${new Date().getFullYear()}-${value}`), 'MMM dd', {
                      locale: getCurrentLocale(),
                    }),
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
    </div>
  )
}

export default ClientInteractions
