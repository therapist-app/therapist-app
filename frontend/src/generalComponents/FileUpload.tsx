import { Button } from '@mui/material'
import React, { useRef } from 'react'

interface FileUploadProps {
  onUpload: (file: File) => void
  accept?: string
  text?: string
}

const FileUpload: React.FC<FileUploadProps> = ({ onUpload, accept, text }) => {
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
        {text ?? 'Upload File'}
      </Button>
    </div>
  )
}

export default FileUpload
