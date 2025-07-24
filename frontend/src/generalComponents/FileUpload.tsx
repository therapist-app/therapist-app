import { Button } from '@mui/material'
import React, { useRef } from 'react'
import { useTranslation } from 'react-i18next'

import { useNotify } from '../hooks/useNotify'
import { commonButtonStyles } from '../styles/buttonStyles'

interface FileUploadProps {
  onUpload: (file: File) => void
  accept?: string
  text?: string
  buttonSx?: object
}

const FileUpload: React.FC<FileUploadProps> = ({ onUpload, accept, text, buttonSx }) => {
  const { t } = useTranslation()
  const fileInputRef = useRef<HTMLInputElement>(null)
  const { notifyError, notifyWarning } = useNotify()

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const file = e.target.files?.[0]
    if (!file) {
      return
    }

    if (accept && !file.type.match(accept.replace('*', '.*'))) {
      notifyWarning(t('errors.unsupported_file_type'))
      e.target.value = ''
      return
    }

    try {
      onUpload(file)
    } catch {
      notifyError(t('errors.upload_failed'))
    }
  }

  const triggerFileDialog = (): void => {
    try {
      fileInputRef.current?.click()
    } catch {
      notifyError(t('errors.cannot_open_file_dialog'))
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
      <Button
        onClick={triggerFileDialog}
        sx={{ ...commonButtonStyles, minWidth: '150px', ...(buttonSx || {}) }}
      >
        {text ?? t('dashboard.upload_file')}
      </Button>
    </div>
  )
}

export default FileUpload
