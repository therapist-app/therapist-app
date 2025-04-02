import React, { useRef } from 'react'

interface FileUploadProps {
  onUpload: (file: File) => void
  accept?: string
}

const FileUpload: React.FC<FileUploadProps> = ({ onUpload, accept }) => {
  const fileInputRef = useRef<HTMLInputElement>(null)

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
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
      <button onClick={() => fileInputRef.current?.click()}>Upload File</button>
    </div>
  )
}

export default FileUpload
