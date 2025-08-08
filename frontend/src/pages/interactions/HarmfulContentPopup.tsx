import CloseIcon from '@mui/icons-material/Close'
import ReportIcon from '@mui/icons-material/Report'
import { Box, IconButton, Modal, Tooltip, Typography } from '@mui/material'
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
          width: 600,
          maxHeight: '80%',
          bgcolor: 'white',
          boxShadow: 24,
          p: 4,
          borderRadius: '10px',
          display: 'flex',
          flexDirection: 'column',
          overflowY: 'auto',
        }}
      >
        <Tooltip title={t('layout.close')} placement='top' arrow>
          <IconButton
            onClick={onClose}
            sx={{ position: 'absolute', right: 8, top: 8 }}
            aria-label='close'
          >
            <CloseIcon />
          </IconButton>
        </Tooltip>
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 2 }}>
          <ReportIcon sx={{ color: 'error.main' }} />
          <Typography variant='h6'>{t('patient_interactions.harmful_content_alert')}</Typography>
        </Box>
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: '8px',
            flex: 1,
            overflowY: 'auto',
          }}
        >
          {logs
            .filter((log) => log.logType === 'HARMFUL_CONTENT_DETECTED' && log.comment)
            .map((log, index) => (
              <div
                key={index}
                style={{
                  padding: '8px',
                  background: '#fffaf8',
                  borderRadius: '10px',
                }}
              >
                <div style={{ fontWeight: '500', marginBottom: '5px' }}>
                  {formatTime(log.timestamp)}:
                </div>
                <div>{log.comment}</div>
              </div>
            ))}
        </div>
      </Box>
    </Modal>
  )
}

export default HarmfulContentPopup
