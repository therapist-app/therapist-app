import DownloadIcon from '@mui/icons-material/Download'
import { IconButton, Tooltip } from '@mui/material'
import { useTranslation } from 'react-i18next'

import { useNotify } from '../hooks/useNotify'

interface FileDownloadProps {
  download(): Promise<string>
  fileName: string
}

const FileDownload: React.FC<FileDownloadProps> = ({ download, fileName }) => {
  const { notifyError, notifySuccess } = useNotify()
  const { t } = useTranslation()

  const handleDownloadFile = async (): Promise<void> => {
    try {
      const downloadUrl = await download()
      const link = document.createElement('a')
      link.href = downloadUrl
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      link.remove()
      window.URL.revokeObjectURL(downloadUrl)
      notifySuccess(t('files.download_success'))
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  return (
    <Tooltip title={t('patient_detail.download')} arrow>
      <IconButton aria-label='download' onClick={handleDownloadFile}>
        <DownloadIcon sx={{ color: 'blue' }} />
      </IconButton>
    </Tooltip>
  )
}

export default FileDownload
