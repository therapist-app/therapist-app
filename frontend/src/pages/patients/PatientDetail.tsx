import DeleteIcon from '@mui/icons-material/Delete'
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Divider,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Typography,
} from '@mui/material'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import FileDownload from '../../generalComponents/FileDownload'
import FileUpload from '../../generalComponents/FileUpload'
import Layout from '../../generalComponents/Layout'
import { getAllExercisesOfPatient } from '../../store/exerciseSlice'
import { getAllMeetingsOfPatient } from '../../store/meetingSlice'
import {
  createDocumentForPatient,
  deleteDocumentOfPatient,
  getAllPatientDocumentsOfPatient,
} from '../../store/patientDocumentSlice'
import { RootState } from '../../store/store'
import { patientDocumentApi } from '../../utils/api'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'
import ExerciseOverviewComponent from '../exercises/components/ExerciseOverviewComponent'
import MeetingOverviewComponent from '../meetings/components/MeetingOverviewComponent'

const PatientDetail = (): ReactElement => {
  const { patientId } = useParams()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const allPatientDocuments = useSelector(
    (state: RootState) => state.patientDocument.allPatientDocumentsOfPatient
  )

  const [refreshPatientDocumentsCounter, setRefreshPatientDocumentsCounter] = useState(0)

  const [openChatbotDialog, setOpenChatbotDialog] = useState(false)
  const [chatbotName, setChatbotName] = useState('')

  useEffect(() => {
    dispatch(getAllPatientDocumentsOfPatient(patientId ?? ''))
    dispatch(getAllMeetingsOfPatient(patientId ?? ''))
    dispatch(getAllExercisesOfPatient(patientId ?? ''))
  }, [dispatch, patientId, refreshPatientDocumentsCounter])

  const handleFileUpload = async (file: File): Promise<void> => {
    await dispatch(
      createDocumentForPatient({
        file: file,
        patientId: patientId ?? '',
      })
    )
    setRefreshPatientDocumentsCounter((prev) => prev + 1)
  }

  const handleDeleteFile = async (fileId: string): Promise<void> => {
    await dispatch(deleteDocumentOfPatient(fileId))
    setRefreshPatientDocumentsCounter((prev) => prev + 1)
  }

  const downloadFile = async (fileId: string): Promise<string> => {
    const response = await patientDocumentApi.downloadFile(fileId, {
      responseType: 'blob',
    })
    const file = response.data
    const url = window.URL.createObjectURL(file)
    return url
  }

  const handleOpenChatbotDialog = (): void => {
    setOpenChatbotDialog(true)
  }

  const handleCloseChatbotDialog = (): void => {
    setOpenChatbotDialog(false)
    setChatbotName('')
  }

  const handleCreateNewChatbot = (): void => {
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

      <Divider style={{ margin: '50px 0' }} />

      <div
        style={{
          display: 'flex',
          gap: '30px',
          alignItems: 'center',
          marginBottom: '10px',
        }}
      >
        <Typography variant='h4'>All Files</Typography>
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
                  <FileDownload
                    download={() => downloadFile(patientDocument.id ?? '')}
                    fileName={patientDocument.fileName ?? ''}
                  />
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

      <Divider style={{ margin: '50px 0' }} />

      <MeetingOverviewComponent />

      <Divider style={{ margin: '50px 0' }} />

      <ExerciseOverviewComponent />
    </Layout>
  )
}

export default PatientDetail
