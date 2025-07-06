import DeleteIcon from '@mui/icons-material/Delete'
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

import { PatientDocumentOutputDTO } from '../api'
import FileDownload from './FileDownload'
import FileUpload from './FileUpload'

interface FilesTableProps {
  title: string
  allDocuments: PatientDocumentOutputDTO[]
  handleFileUpload: (file: File) => void
  handleDeleteFile: (fileId: string) => void
  downloadFile: (fileId: string) => Promise<string>
}

const FilesTable: React.FC<FilesTableProps> = (props: FilesTableProps) => {
  const { allDocuments, handleFileUpload, handleDeleteFile, downloadFile, title } = props

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
        <FileUpload onUpload={handleFileUpload} />
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
                    download={() => downloadFile(document.id ?? '')}
                    fileName={document.fileName ?? ''}
                  />
                  <IconButton
                    aria-label='delete'
                    onClick={() => handleDeleteFile(document.id ?? '')}
                  >
                    <DeleteIcon sx={{ color: 'red' }} />
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
