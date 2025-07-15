import { Button } from '@mui/material'
import React, { useRef } from 'react'
import { useTranslation } from 'react-i18next'

interface FileUploadProps {
  onUpload: (file: File) => void
  accept?: string
  text?: string
}

const FileUpload: React.FC<FileUploadProps> = ({ onUpload, accept, text }) => {
  const { t } = useTranslation()
  const fileInputRef = useRef<HTMLInputElement>(null)

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const file = e.target.files?.[0]
    if (file) {
      onUpload(file)
    }
  }

  return (
    <div>
      <input
        ref={fileInputRef}
        type='file'
        accept={accept}
        onChange={handleFileChange}
        style={{ display: 'none' }}
      />
      <Button variant='contained' onClick={() => fileInputRef.current?.click()}>
        {text ?? t('dashboard.upload_file')}
      </Button>
    </div>
  )
}

export default FileUpload
