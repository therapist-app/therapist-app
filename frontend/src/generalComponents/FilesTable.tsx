import {
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material'
import { AxiosError } from 'axios'
import React from 'react'

import { PatientDocumentOutputDTO } from '../api'
import DeleteIcon from '../icons/DeleteIcon'
import { handleError } from '../utils/handleError'
import FileDownload from './FileDownload'
import FileUpload from './FileUpload'
import { useNotify } from '../hooks/useNotify'

interface FilesTableProps {
  title: string
  allDocuments: PatientDocumentOutputDTO[]
  handleFileUpload: (file: File) => Promise<void> | void
  handleDeleteFile: (fileId: string) => Promise<void> | void
  downloadFile: (fileId: string) => Promise<string>
}

const FilesTable: React.FC<FilesTableProps> = (props) => {
  const { allDocuments, handleFileUpload, handleDeleteFile, downloadFile, title } = props
  const { notifySuccess, notifyError } = useNotify()

  const wrappedUpload = async (file: File): Promise<void> => {
    try {
      await handleFileUpload(file)
      notifySuccess('File uploaded successfully.')
    } catch (err) {
      const msg = handleError(err as AxiosError)
      notifyError(msg)
    }
  }

  const wrappedDelete = async (fileId: string): Promise<void> => {
    try {
      await handleDeleteFile(fileId)
      notifySuccess('File deleted successfully.')
    } catch (err) {
      const msg = handleError(err as AxiosError)
      notifyError(msg)
    }
  }

  const wrappedDownload = async (fileId: string): Promise<string> => {
    try {
      return await downloadFile(fileId)
    } catch (err) {
      const msg = handleError(err as AxiosError)
      notifyError(msg)
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
        <Typography variant='h2'>{title}</Typography>
        <FileUpload onUpload={wrappedUpload} />
      </div>

      <TableContainer sx={{ marginTop: '10px' }} component={Paper}>
        <Table aria-label='simple table'>
          <TableHead>
            <TableRow>
              <TableCell>
                <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>File name</div>
              </TableCell>
              <TableCell align='right'>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allDocuments.map((document) => (
              <TableRow
                key={document.id}
                sx={{
                  '&:last-child td, &:last-child th': { border: 0 },
                }}
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
                  <FileDownload
                    download={() => wrappedDownload(document.id ?? '')}
                    fileName={document.fileName ?? ''}
                  />
                  <IconButton aria-label='delete' onClick={() => wrappedDelete(document.id ?? '')}>
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  )
}

export default FilesTable
