import { format } from 'date-fns'
import { JSX } from 'react'
import { useTranslation } from 'react-i18next'

import { getCurrentLocale } from '../../utils/dateUtil.ts'

// eslint-disable-next-line
const HeatmapTooltip = ({ cell }: { cell: any }): JSX.Element => {
  const { t } = useTranslation()
  const [hourPart, datePart] = cell.id.split('.')
  const [month, day] = datePart.split('-')

  const formattedDate = format(new Date(`${new Date().getFullYear()}-${month}-${day}`), 'PP', {
    locale: getCurrentLocale(),
  })
  const paddedHour = hourPart.padStart(2, '0')
  const timeRange = `${paddedHour}:00 - ${paddedHour}:59`

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
        width: '120px',
      }}
    >
      <div style={{ fontWeight: 'bold', marginBottom: '4px' }}>{formattedDate}</div>
      <div style={{ marginBottom: '4px' }}>{timeRange}</div>
      <div style={{ marginBottom: '8px' }}>
        {t('patient_interactions.interactions')}: <strong>{cell.value}</strong>
      </div>
    </div>
  )
}

export default HeatmapTooltip
