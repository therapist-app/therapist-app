import { format } from 'date-fns'
import { JSX } from 'react'
import { useTranslation } from 'react-i18next'

import { LogOutputDTO } from '../../api/models/log-output-dto.ts'
import { getCurrentLocale } from '../../utils/dateUtil.ts'

const HeatmapTooltip = ({
  cell,
  activeLogType,
}: {
  // eslint-disable-next-line
  cell: any
  activeLogType: string
}): JSX.Element => {
  const { t } = useTranslation()
  const [hourPart, datePart] = cell.id.split('.')
  const [month, day] = datePart.split('-')

  const formattedDate = format(new Date(`${new Date().getFullYear()}-${month}-${day}`), 'PP', {
    locale: getCurrentLocale(),
  })
  const paddedHour = hourPart.padStart(2, '0')
  const timeRange = `${paddedHour}:00 - ${paddedHour}:59`

  const isClickable =
    activeLogType === 'HARMFUL_CONTENT_DETECTED' &&
    (cell.data.logs || []).some(
      (log: LogOutputDTO) => log.logType === 'HARMFUL_CONTENT_DETECTED' && log.comment
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
        width: '120px',
      }}
    >
      <div style={{ fontWeight: 'bold', marginBottom: '4px' }}>{formattedDate}</div>
      <div style={{ marginBottom: '4px' }}>{timeRange}</div>
      <div style={{ marginBottom: '8px' }}>
        {t('patient_interactions.interactions')}: <strong>{cell.value}</strong>
      </div>
      {isClickable && (
        <div style={{ fontSize: '12px', color: '#666' }}>
          {t('patient_interactions.click_to_view_details')}
        </div>
      )}
    </div>
  )
}

export default HeatmapTooltip
