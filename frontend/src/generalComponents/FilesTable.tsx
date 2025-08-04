import {
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Tooltip,
  Typography,
} from '@mui/material'
import React from 'react'
import { useTranslation } from 'react-i18next'

import { PatientDocumentOutputDTO } from '../api'
import { useNotify } from '../hooks/useNotify'
import DeleteIcon from '../icons/DeleteIcon'
import FileDownload from './FileDownload'
import FileUpload from './FileUpload'

interface FilesTableProps {
  title: string
  allDocuments: PatientDocumentOutputDTO[]
  handleFileUpload: (file: File) => Promise<void> | void
  handleDeleteFile: (fileId: string) => Promise<void> | void
  downloadFile: (fileId: string) => Promise<string>
  maxFileSizeMessage?: string
  maxFileSizeBytes?: number
  showTitleAndUploadButton?: boolean
}

const FilesTable: React.FC<FilesTableProps> = (props) => {
  const { t } = useTranslation()
  const {
    allDocuments,
    handleFileUpload,
    handleDeleteFile,
    downloadFile,
    title,
    maxFileSizeBytes,
  } = props
  const { notifySuccess, notifyError } = useNotify()
  const wrappedUpload = async (file: File): Promise<void> => {
    if (maxFileSizeBytes && file.size > maxFileSizeBytes) {
      notifyError(t('exercise.file_too_large', { max: '50MB' }))
      return
    }

    try {
      await handleFileUpload(file)
      notifySuccess(t('files.file_uploaded_successfully'))
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  const wrappedDelete = async (fileId: string): Promise<void> => {
    try {
      await handleDeleteFile(fileId)
      notifySuccess(t('files.file_deleted_successfully'))
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  const wrappedDownload = async (fileId: string): Promise<string> => {
    try {
      return await downloadFile(fileId)
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      return ''
    }
  }

  return (
    <>
      <div
        style={{
          display: 'flex',
          gap: '30px',
          alignItems: 'center',
          marginBottom: '10px',
        }}
      >
        {props.showTitleAndUploadButton && (
          <>
            <Typography variant='h2'>{title}</Typography>
            <FileUpload onUpload={wrappedUpload} />
          </>
        )}
      </div>

      {allDocuments.length > 0 ? (
        <TableContainer sx={{ marginTop: '10px' }} component={Paper}>
          <Table aria-label='files-table'>
            <TableHead>
              <TableRow>
                <TableCell>
                  <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>
                    {t('files.file_name')}
                  </div>
                </TableCell>
                <TableCell align='right'>{t('files.actions')}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {allDocuments.map((document) => (
                <TableRow
                  key={document.id}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell
                    sx={{
                      maxWidth: 400,
                      whiteSpace: 'nowrap',
                      overflow: 'hidden',
                      textOverflow: 'ellipsis',
                    }}
                    component='th'
                    scope='row'
                  >
                    {document.fileName}
                  </TableCell>
                  <TableCell align='right'>
                    <Tooltip title={t('files.download')} arrow>
                      <FileDownload
                        download={() => wrappedDownload(document.id ?? '')}
                        fileName={document.fileName ?? ''}
                      />
                    </Tooltip>

                    <Tooltip title={t('files.delete')} arrow>
                      <IconButton
                        aria-label='delete'
                        onClick={() => wrappedDelete(document.id ?? '')}
                      >
                        <DeleteIcon />
                      </IconButton>
                    </Tooltip>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography sx={{ mt: '10px' }}>{t('patient_detail.no_files_uploaded_yet')}</Typography>
      )}
    </>
  )
}

export default FilesTable
