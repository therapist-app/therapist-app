import {
  Button,
  Card,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  TextField,
  Typography,
} from '@mui/material'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { CounselingPlanPhaseOutputDTO } from '../../api'
import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import FilesTable from '../../generalComponents/FilesTable'
import Layout from '../../generalComponents/Layout'
import { getCounselingPlanByPatientId } from '../../store/counselingPlanSlice'
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
import ChatbotOverview from '../chatbot/ChatbotOverview'
import ExerciseOverviewComponent from '../exercises/components/ExerciseOverviewComponent'
import GAD7TestDetail from '../gad7Test/GAD7TestDetail'
import MeetingOverviewComponent from '../meetings/components/MeetingOverviewComponent'
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import { ChatbotTemplateOutputDTO } from '../../api'


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

  const counselingPlan = useSelector((state: RootState) => state.counselingPlan).counselingPlan

  const [currentCounselingPlanPhase, setCurrentCounselingPlanPhase] = useState<
    CounselingPlanPhaseOutputDTO | undefined
  >(undefined)

  const [refreshPatientDocumentsCounter, setRefreshPatientDocumentsCounter] = useState(0)

  const [openChatbotDialog, setOpenChatbotDialog] = useState(false)
  const [chatbotName, setChatbotName] = useState('')

  const therapist = useSelector((s: RootState) => s.therapist.loggedInTherapist)
const therapistTemplates = (therapist?.chatbotTemplatesOutputDTO ?? []).filter(
  (tpl) => tpl.patientId == null
)

const handleSelectChatbot = (bot: ChatbotTemplateOutputDTO): void => {
  navigate(
    getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, { chatbotTemplateId: bot.id ?? '' }),
    { state: { chatbotConfig: bot } }
  )
}

useEffect(() => {
  dispatch(getCurrentlyLoggedInTherapist())
}, [dispatch])

  useEffect(() => {
    console.log(counselingPlan)
    setCurrentCounselingPlanPhase(
      counselingPlan?.counselingPlanPhasesOutputDTO?.find(
        (phase) =>
          new Date(phase.startDate ?? '').getTime() < new Date().getTime() &&
          new Date(phase.endDate ?? '').getTime() > new Date().getTime()
      )
    )
  }, [counselingPlan?.counselingPlanPhasesOutputDTO, counselingPlan])

  useEffect(() => {
    dispatch(getAllPatientsOfTherapist())
    dispatch(getAllPatientDocumentsOfPatient(patientId ?? ''))
    dispatch(getAllMeetingsOfPatient(patientId ?? ''))
    dispatch(getAllExercisesOfPatient(patientId ?? ''))
    dispatch(getCounselingPlanByPatientId(patientId ?? ''))
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
            <Typography variant='h3' gutterBottom>
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

      <CustomizedDivider />

      <div>
        <div style={{ display: 'flex', gap: '30px', alignItems: 'center', marginBottom: '10px' }}>
          <Typography variant='h2'>Counseling Plan</Typography>
          <Button
            sx={{ height: 'fit-content' }}
            variant='contained'
            onClick={() =>
              navigate(
                getPathFromPage(PAGES.COUNSELING_PLAN_DETAILS_PAGE, {
                  patientId: patientId ?? '',
                })
              )
            }
          >
            Go to Counseling Plan
          </Button>
        </div>
        {currentCounselingPlanPhase ? (
          <Typography>
            Current Phase: <strong>{currentCounselingPlanPhase.phaseName}</strong>
          </Typography>
        ) : (
          <Typography>Currently no phase is active</Typography>
        )}
      </div>

      <CustomizedDivider />

      <MeetingOverviewComponent />

      <CustomizedDivider />

      <ExerciseOverviewComponent />

      <CustomizedDivider />

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

      <GAD7TestDetail />

      <CustomizedDivider />

      <FilesTable
        title='Files'
        allDocuments={allPatientDocuments}
        handleFileUpload={handleFileUpload}
        handleDeleteFile={handleDeleteFile}
        downloadFile={downloadFile}
      />

      <CustomizedDivider />

      <ChatbotOverview />
    </Layout>
  )
}

export default PatientDetail
