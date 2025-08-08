import { format } from 'date-fns'
import { ReactElement, useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { LOG_TYPES } from '../../store/logTypes.ts'
import { LogOutputDTO } from '../../store/patientLogData.ts'
import { getCurrentLocale } from '../../utils/dateUtil.ts'


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

export default HeatmapTooltip