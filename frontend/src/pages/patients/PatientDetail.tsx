import {
  Button,
  Card,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Divider,
  Grid,
  TextField,
  Typography,
} from '@mui/material'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import FilesTable from '../../generalComponents/FilesTable'
import Layout from '../../generalComponents/Layout'
import { getAllExercisesOfPatient } from '../../store/exerciseSlice'
import { getAllMeetingsOfPatient } from '../../store/meetingSlice'
import {
  createDocumentForPatient,
  deleteDocumentOfPatient,
  getAllPatientDocumentsOfPatient,
} from '../../store/patientDocumentSlice'
import { getAllPatientsOfTherapist } from '../../store/patientSlice'
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

  const patient = useSelector((state: RootState) =>
    state.patient.allPatientsOfTherapist.find((p) => p.id === patientId?.toString())
  )

  const [refreshPatientDocumentsCounter, setRefreshPatientDocumentsCounter] = useState(0)

  const [openChatbotDialog, setOpenChatbotDialog] = useState(false)
  const [chatbotName, setChatbotName] = useState('')

  useEffect(() => {
    dispatch(getAllPatientsOfTherapist())
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
    const response = await patientDocumentApi.downloadPatientDocument(fileId, {
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
      {patient ? (
        <Card sx={{ mb: 4, p: 2 }}>
          <CardContent>
            <Typography variant='h6' gutterBottom>
              Client: {patient.name}
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' sx={{ fontWeight: 'bold' }}>
                  Email (for Login in Client App)
                </Typography>
                <Typography color='info' sx={{ wordBreak: 'break-word' }}>
                  {' '}
                  {patient.email || 'N/A'}{' '}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' sx={{ fontWeight: 'bold' }}>
                  Initial Password (for Login in Client App)
                </Typography>
                <Typography color='info' sx={{ wordBreak: 'break-word' }}>
                  {' '}
                  {patient.initialPassword || 'N/A'}{' '}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' sx={{ fontWeight: 'bold' }}>
                  Client App Link
                </Typography>
                <Typography color='info' sx={{ wordBreak: 'break-word' }}>
                  <a
                    href={import.meta.env.VITE_PATIENT_APP_URL}
                    target='_blank'
                    rel='noopener noreferrer'
                  >
                    {import.meta.env.VITE_PATIENT_APP_URL}
                  </a>
                </Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  Age
                </Typography>
                <Typography>{patient.age ?? 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  Gender
                </Typography>
                <Typography>{patient.gender || 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  Phone
                </Typography>
                <Typography>{patient.phoneNumber || 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  Address
                </Typography>
                <Typography>{patient.address || 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  Date of Admission
                </Typography>
                <Typography>{patient.dateOfAdmission || 'N/A'}</Typography>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      ) : (
        <Typography variant='h6' sx={{ mb: 3 }}>
          Loading patient data...
        </Typography>
      )}
      <Button
        variant='contained'
        onClick={() =>
          navigate(
            getPathFromPage(PAGES.COUNSELING_PLAN_DETAILS_PAGE, {
              patientId: patientId ?? '',
            })
          )
        }
        sx={{ marginTop: '20px', marginBottom: '20px' }}
      >
        Go to Counseling Plan
      </Button>
      <Divider style={{ margin: '20px 0' }} />
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

      <FilesTable
        title='Files of Patient'
        allDocuments={allPatientDocuments}
        handleFileUpload={handleFileUpload}
        handleDeleteFile={handleDeleteFile}
        downloadFile={downloadFile}
      />

      <Divider style={{ margin: '50px 0' }} />

      <MeetingOverviewComponent />

      <Divider style={{ margin: '50px 0' }} />

      <ExerciseOverviewComponent />
    </Layout>
  )
}

export default PatientDetail
