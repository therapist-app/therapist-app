import {
  Button,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  TextField,
} from '@mui/material'
import { useNavigate, useParams } from 'react-router-dom'
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
import { getPathFromPage, PAGES } from '../../utils/routes'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'
import VisibilityIcon from '@mui/icons-material/Visibility'
import { getAllTherapySessionsOfPatient } from '../../store/therapySessionSlice'

const PatientDetail = () => {
  const { patientId } = useParams()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const allPatientDocuments = useSelector(
    (state: RootState) => state.patientDocument.allPatientDocumentsOfPatient
  )
  const allTherapySessionsOfPatient = useSelector(
    (state: RootState) => state.therapySession.allTherapySessionsOfPatient
  )

  const [refreshPatientDocumentsCounter, setRefreshPatientDocumentsCounter] = useState(0)

  const [openChatbotDialog, setOpenChatbotDialog] = useState(false)
  const [chatbotName, setChatbotName] = useState('')

  useEffect(() => {
    dispatch(getAllPatientDocumentsOfPatient(patientId ?? ''))
    dispatch(getAllTherapySessionsOfPatient(patientId ?? ''))
  }, [dispatch, patientId, refreshPatientDocumentsCounter])

  const handleFileUpload = async (file: File) => {
    await dispatch(
      createDocumentForPatient({
        file,
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

  const handleClickOnSession = (therapySessionId: string) => {
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_DETAILS_PAGE, {
        patientId: patientId ?? '',
        therapySessionId: therapySessionId,
      })
    )
  }

  const handleCreateNewSession = () => {
    navigate(
      getPathFromPage(PAGES.THERAPY_SESSIONS_CREATE_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  const handleOpenChatbotDialog = () => {
    setOpenChatbotDialog(true)
  }

  const handleCloseChatbotDialog = () => {
    setOpenChatbotDialog(false)
    setChatbotName('')
  }

  const handleCreateNewChatbot = () => {
    console.log('Chatbot created with name:', chatbotName)

    navigate(
      getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
        chatbotTemplateId: 'newBot',
      })
    )

    handleCloseChatbotDialog()
  }

  return (
    <Layout>
      <Typography variant='h4'>Patient details of "{patientId}"</Typography>

      <Button
        variant='contained'
        onClick={handleOpenChatbotDialog}
        sx={{ marginTop: '20px', marginBottom: '20px' }}
      >
        Create new Chatbot
      </Button>

      <Dialog open={openChatbotDialog} onClose={handleCloseChatbotDialog}>
        <DialogTitle>Create a new Chatbot</DialogTitle>
        <DialogContent>
          <DialogContentText>Please enter a name for your new chatbot:</DialogContentText>
          <TextField
            autoFocus
            margin='dense'
            label='Chatbot Name'
            type='text'
            fullWidth
            variant='outlined'
            value={chatbotName}
            onChange={(e) => setChatbotName(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseChatbotDialog}>Cancel</Button>
          <Button
            onClick={handleCreateNewChatbot}
            variant='contained'
            disabled={!chatbotName.trim()}
          >
            Create Bot
          </Button>
        </DialogActions>
      </Dialog>

      <Typography sx={{ marginTop: '50px' }} variant='h4'>
        All Files
      </Typography>
      <TableContainer sx={{ marginTop: '10px' }} component={Paper}>
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

      <Typography sx={{ marginTop: '50px', marginBottom: '10px' }} variant='h4'>
        All Sessions
      </Typography>

      <Button sx={{ marginBottom: '20px' }} variant='contained' onClick={handleCreateNewSession}>
        Create new Therapy Session
      </Button>
      <TableContainer sx={{ width: '600px' }} component={Paper}>
        <Table aria-label='simple table' sx={{ tableLayout: 'fixed' }}>
          <TableHead>
            <TableRow>
              <TableCell>Session Start</TableCell>
              <TableCell>Session End</TableCell>
              <TableCell align='right'>View</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allTherapySessionsOfPatient.map((therapySession) => (
              <TableRow
                onClick={() => handleClickOnSession(therapySession.id ?? '')}
                key={therapySession.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 }, cursor: 'pointer' }}
              >
                <TableCell component='th' scope='row'>
                  {therapySession?.sessionStart
                    ? format(new Date(therapySession.sessionStart), 'dd.MM.yyyy HH:mm', {
                        locale: de,
                      })
                    : '-'}
                </TableCell>
                <TableCell>
                  {therapySession?.sessionEnd
                    ? format(new Date(therapySession.sessionEnd), 'dd.MM.yyyy HH:mm', {
                        locale: de,
                      })
                    : '-'}
                </TableCell>
                <TableCell align='right'>
                  <IconButton
                    aria-label='download'
                    onClick={() => handleClickOnSession(therapySession.id ?? '')}
                  >
                    <VisibilityIcon />
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
