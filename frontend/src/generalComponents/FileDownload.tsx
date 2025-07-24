import DownloadIcon from '@mui/icons-material/Download'
import { IconButton } from '@mui/material'
import { AxiosError } from 'axios'
import { useTranslation } from 'react-i18next'

import { useNotify } from '../hooks/useNotify'
import { handleError } from '../utils/handleError'

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
      const message = handleError(error as AxiosError)
      notifyError(message || t('files.download_failed'))
    }
  }

  return (
    <IconButton aria-label='download' onClick={handleDownloadFile}>
      <DownloadIcon sx={{ color: 'blue' }} />
    </IconButton>
  )
}

export default FileDownload
