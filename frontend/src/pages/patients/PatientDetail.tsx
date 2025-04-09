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
import { useParams } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import Layout from '../../generalComponents/Layout'
import FileUpload from '../../generalComponents/FileUpload'
import { useAppDispatch } from '../../utils/hooks'
import {
  createDocumentForPatient,
  getAllPatientDocumentsOfPatient,
} from '../../store/patientDocumentSlice'
import { useEffect } from 'react'
import { RootState } from '../../store/store'
import { useSelector } from 'react-redux'
import DeleteIcon from '@mui/icons-material/Delete'

const PatientDetail = () => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()
  const allPatientDocuments = useSelector(
    (state: RootState) => state.patientDocument.allPatientDocumentsOfPatient
  )

  useEffect(() => {
    dispatch(getAllPatientDocumentsOfPatient(patientId ?? ''))
  }, [dispatch, patientId])

  const handleFileUpload = async (file: File) => {
    console.log(file)
    dispatch(
      createDocumentForPatient({
        file: file,
        patientId: patientId ?? '',
      })
    )
  }

  const handleDeleteFile = async (fileId: string) => {
    console.log(fileId)
  }

  return (
    <Layout>
      <Typography variant='h3'>
        {t('patient_detail.message')}: "{patientId}"
      </Typography>

      <TableContainer sx={{ marginTop: '50px' }} component={Paper}>
        <Table aria-label='simple table'>
          <TableHead>
            <TableRow>
              <TableCell>
                <div style={{ display: 'flex', gap: '10px' }}>
                  File name <FileUpload onUpload={handleFileUpload} />
                </div>
              </TableCell>
              <TableCell align='right'>Delete File</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allPatientDocuments.map((patientDocument) => (
              <TableRow
                key={patientDocument.id}
                sx={{
                  '&:last-child td, &:last-child th': { border: 0 },
                  cursor: 'pointer',
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
                  {patientDocument.fileName}
                </TableCell>
                <TableCell align='right'>
                  <IconButton
                    aria-label='delete'
                    onClick={() => handleDeleteFile(patientDocument.id ?? '')}
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Layout>
  )
}

export default PatientDetail
