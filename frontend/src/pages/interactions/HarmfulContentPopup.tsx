import CloseIcon from '@mui/icons-material/Close'
import { Box, IconButton, Modal, Typography } from '@mui/material'
import { format } from 'date-fns'
import { ReactElement } from 'react'
import { useTranslation } from 'react-i18next'

import { LogOutputDTO } from '../../store/patientLogData.ts'
import { getCurrentLocale } from '../../utils/dateUtil.ts'

interface HarmfulContentPopupProps {
  open: boolean
  onClose: () => void
  logs: LogOutputDTO[]
}

const HarmfulContentPopup = ({ open, onClose, logs }: HarmfulContentPopupProps): ReactElement => {
  const { t } = useTranslation()
  const formatTime = (timestamp: string): string => {
    return format(new Date(timestamp), 'HH:mm:ss', {
      locale: getCurrentLocale(),
    })
  }
  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 500,
          bgcolor: 'white',
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
        }}
      >
        <IconButton onClick={onClose} sx={{ position: 'absolute', right: 8, top: 8 }}>
          <CloseIcon />
        </IconButton>
        <Typography variant='h6' gutterBottom>
          {t('patient_interactions.harmful_content_detected')}
        </Typography>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
          {logs
            .filter((log) => log.logType === 'HARMFUL_CONTENT_DETECTED' && log.comment)
            .map((log, index) => (
              <div
                key={index}
                style={{
                  padding: '8px',
                  background: '#fff8f8',
                  borderRadius: '4px',
                }}
              >
                <div style={{ fontWeight: '500' }}>{formatTime(log.timestamp)}:</div>
                <div>{log.comment}</div>
              </div>
            ))}
        </div>
      </Box>
    </Modal>
  )
}

export default HarmfulContentPopup
