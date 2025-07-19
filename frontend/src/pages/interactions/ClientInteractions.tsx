import { FormControl, InputLabel, MenuItem, Paper, Select, Stack, Typography } from '@mui/material'
import { ResponsiveHeatMap } from '@nivo/heatmap'
import { format, subDays } from 'date-fns'
import { throttle } from 'lodash'
import { ReactElement, useMemo, useState } from 'react'
import { useTranslation } from 'react-i18next'
import Layout from '../../generalComponents/Layout'

// Mock data interface
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

// Mock data generator
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
    // Generate 0-5 interactions for each day
    const dailyInteractions = Math.floor(Math.random() * 6)

    // For each interaction in this day
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

// Transform data for heatmap
const transformDataForHeatmap = (data: InteractionData[]): HeatMapData[] => {
  // Create array of hours (0-23)
  const hours = Array.from({ length: 24 }, (_, i) => i)
  const daysMap = new Map<string, number[]>()

  // Initialize all hours for all days with 0
  data.forEach((item) => {
    const shortDate = format(new Date(item.date), 'MM-dd') // Shorter date format
    if (!daysMap.has(shortDate)) {
      daysMap.set(shortDate, Array(24).fill(0))
    }
    const dayData = daysMap.get(shortDate)!
    dayData[item.hour] += item.value
  })

  // Transform into Nivo heatmap format with hours as rows
  return hours.map((hour) => ({
    id: `${hour.toString().padStart(2, '0')}:00`,
    data: Array.from(daysMap.entries()).map(([date, values]) => ({
      x: date,
      y: values[hour],
    })),
  }))
}

const ClientInteractions = (): ReactElement => {
  const { t } = useTranslation()
  const [interactionType, setInteractionType] = useState<string>('all')
  const [data] = useState<InteractionData[]>(() => generateMockData(30))

  // Memoize filtered data
  const filteredData = useMemo(
    () => (interactionType === 'all' ? data : data.filter((d) => d.type === interactionType)),
    [data, interactionType]
  )

  // Memoize heatmap data transformation
  const heatmapData = useMemo(() => transformDataForHeatmap(filteredData), [filteredData])

  return (
    <Layout>
        <Paper sx={{ p: 3, height: '800px' }}>
        <Stack spacing={2}>
            <Typography variant='h6'>{t('Patient Interactions')}</Typography>

            <FormControl fullWidth>
            <InputLabel id='interaction-type-label'>{t('Interaction Type')}</InputLabel>
            <Select
                labelId='interaction-type-label'
                value={interactionType}
                label={t('Interaction Type')}
                onChange={(e) => setInteractionType(e.target.value)}
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

            <div style={{ height: '650px' }}>
            <ResponsiveHeatMap
                data={heatmapData}
                margin={{ top: 40, right: 40, bottom: 40, left: 80 }}
                valueFormat='>-.0f'
                axisTop={null}
                axisBottom={{
                tickSize: 5,
                tickPadding: 5,
                tickRotation: -45,
                legend: 'Date (MM-DD)',
                legendPosition: 'middle',
                legendOffset: 35,
                }}
                axisLeft={{
                tickSize: 5,
                tickPadding: 5,
                tickRotation: 0,
                legend: 'Time',
                legendPosition: 'middle',
                legendOffset: -60,
                format: (value) => `${value}:00`,
                }}
                colors={{
                type: 'sequential',
                scheme: 'blues',
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
