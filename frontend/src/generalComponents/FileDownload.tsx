import DownloadIcon from '@mui/icons-material/Download'
import { IconButton } from '@mui/material'

interface FileDownloadProps {
  download(): Promise<string>
  fileName: string
}

const FileDownload: React.FC<FileDownloadProps> = (props: FileDownloadProps) => {
  const { fileName } = props

  const handleDownloadFile = async (): Promise<void> => {
    try {
      const downloadUrl = await props.download()
      const link = document.createElement('a')
      link.href = downloadUrl
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      link.remove()
      window.URL.revokeObjectURL(downloadUrl)
    } catch (error) {
      console.error('Download error:', error)
    }
  }

  return (
    <IconButton aria-label='download' onClick={handleDownloadFile}>
      <DownloadIcon sx={{ color: 'blue' }} />
    </IconButton>
  )
}

export default FileDownload
