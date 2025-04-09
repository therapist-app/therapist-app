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
  deleteDocumentOfPatient,
  getAllPatientDocumentsOfPatient,
} from '../../store/patientDocumentSlice'
import { useEffect, useState } from 'react'
import { RootState } from '../../store/store'
import { useSelector } from 'react-redux'
import DeleteIcon from '@mui/icons-material/Delete'
import DownloadIcon from '@mui/icons-material/Download'
import { patientDocumentApi } from '../../utils/api'

const PatientDetail = () => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()
  const allPatientDocuments = useSelector(
    (state: RootState) => state.patientDocument.allPatientDocumentsOfPatient
  )

  const [refreshPatientDocumentsCounter, setRefreshPatientDocumentsCounter] = useState(0)

  useEffect(() => {
    dispatch(getAllPatientDocumentsOfPatient(patientId ?? ''))
  }, [dispatch, patientId, refreshPatientDocumentsCounter])

  const handleFileUpload = async (file: File) => {
    await dispatch(
      createDocumentForPatient({
        file: file,
        patientId: patientId ?? '',
      })
    )
    setRefreshPatientDocumentsCounter((prev) => prev + 1)
  }

  const handleDeleteFile = async (fileId: string) => {
    await dispatch(deleteDocumentOfPatient(fileId))
    setRefreshPatientDocumentsCounter((prev) => prev + 1)
  }

  const handleDownloadFile = async (fileId: string, fileName: string) => {
    try {
      const response = await patientDocumentApi.downloadFile(fileId, {
        responseType: 'blob',
      })

      const file = response.data
      const url = window.URL.createObjectURL(file)

      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      link.remove()
      window.URL.revokeObjectURL(url)
    } catch (error) {
      console.error('Download error:', error)
    }
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
                <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>
                  File name <FileUpload onUpload={handleFileUpload} />
                </div>
              </TableCell>
              <TableCell align='right'>Actions</TableCell>
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
                    aria-label='download'
                    onClick={() =>
                      handleDownloadFile(patientDocument.id ?? '', patientDocument.fileName ?? '')
                    }
                  >
                    <DownloadIcon />
                  </IconButton>
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
