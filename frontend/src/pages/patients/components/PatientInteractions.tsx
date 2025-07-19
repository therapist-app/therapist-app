import {
  FormControl,
  InputLabel,
  MenuItem,
  Paper,
  Select,
  Stack,
  Typography,
} from '@mui/material'
import { ResponsiveTimeRange } from '@nivo/calendar'
import { format, subDays } from 'date-fns'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'

// Mock data interface
interface InteractionData {
  day: string
  value: number
  details: string
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
    'Chatbot Interaction'
  ]

  for (let i = 0; i < days; i++) {
    const date = subDays(new Date(), i)
    const randomInteractions = Math.floor(Math.random() * 10)
    
    // Create one entry per day with accumulated value
    const dayEntry: InteractionData = {
      day: format(date, 'yyyy-MM-dd'),
      value: randomInteractions,
      details: interactionTypes[Math.floor(Math.random() * interactionTypes.length)]
    }
    data.push(dayEntry)
  }
  return data
}

const PatientInteractionsComponent = (): ReactElement => {
  const { t } = useTranslation()
  const [interactionType, setInteractionType] = useState<string>('all')
  const [data] = useState<InteractionData[]>(() => generateMockData(30))

  const filteredData = interactionType === 'all' 
    ? data 
    : data.filter(d => d.details === interactionType)

  return (
    <Paper sx={{ p: 3, height: '600px' }}>
      <Stack spacing={2}>
        <Typography variant="h6">
          {t('Patient Interactions')}
        </Typography>
        
        <FormControl fullWidth>
          <InputLabel id="interaction-type-label">
            {t('Interaction Type')}
          </InputLabel>
          <Select
            labelId="interaction-type-label"
            value={interactionType}
            label={t('Interaction Type')}
            onChange={(e) => setInteractionType(e.target.value)}
          >
            <MenuItem value="all">{t('All Interactions')}</MenuItem>
            <MenuItem value="Journal Creation">{t('Journal Creation')}</MenuItem>
            <MenuItem value="Journal Update">{t('Journal Update')}</MenuItem>
            <MenuItem value="Exercise Start">{t('Exercise Start')}</MenuItem>
            <MenuItem value="Exercise Completion">{t('Exercise Completion')}</MenuItem>
            <MenuItem value="Chatbot Creation">{t('Chatbot Creation')}</MenuItem>
            <MenuItem value="Message Sent">{t('Number of Messages')}</MenuItem>
            <MenuItem value="Chatbot Interaction">{t('Chatbot Interaction')}</MenuItem>
          </Select>
        </FormControl>

        <div style={{ height: '500px' }}>
          <ResponsiveTimeRange
            data={filteredData}
            from={subDays(new Date(), 30)}
            to={new Date()}
            emptyColor="#eeeeee"
            colors={['#61cdbb', '#97e3d5', '#e8c1a0', '#f47560']}
            margin={{ top: 40, right: 40, bottom: 40, left: 40 }}
            dayBorderWidth={2}
            dayBorderColor="#ffffff"
          />
        </div>
      </Stack>
    </Paper>
  )
}

export default PatientInteractionsComponent
