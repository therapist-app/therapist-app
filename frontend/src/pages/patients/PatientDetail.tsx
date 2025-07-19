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
import { useTranslation } from 'react-i18next'
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
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import { patientDocumentApi } from '../../utils/api'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'
import ChatbotOverview from '../chatbot/ChatbotOverview'
import ExerciseOverviewComponent from '../exercises/components/ExerciseOverviewComponent'
import GAD7TestDetail from '../gad7Test/GAD7TestDetail'
import MeetingOverviewComponent from '../meetings/components/MeetingOverviewComponent'
import PatientInteractionsComponent from './components/PatientInteractions'

const PatientDetail = (): ReactElement => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const patientDocumentsVisibleToPatient = useSelector(
    (state: RootState) => state.patientDocument.allPatientDocumentsOfPatient
  ).filter((doc) => doc.isSharedWithPatient)

  const patientDocumentsNotVisibleToPatient = useSelector(
    (state: RootState) => state.patientDocument.allPatientDocumentsOfPatient
  ).filter((doc) => !doc.isSharedWithPatient)

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

  const handleFileUploadNotSharedWithPatient = async (file: File): Promise<void> => {
    await dispatch(
      createDocumentForPatient({
        file: file,
        patientId: patientId ?? '',
        isSharedWithPatient: false,
      })
    )
    setRefreshPatientDocumentsCounter((prev) => prev + 1)
  }

  const handleFileUploadSharedWithPatient = async (file: File): Promise<void> => {
    await dispatch(
      createDocumentForPatient({
        file: file,
        patientId: patientId ?? '',
        isSharedWithPatient: true,
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
              {t('patient_detail.client')}: {patient.name}
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' sx={{ fontWeight: 'bold' }}>
                  {t('patient_detail.email_client_app')}
                </Typography>
                <Typography color='info' sx={{ wordBreak: 'break-word' }}>
                  {' '}
                  {patient.email || 'N/A'}{' '}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' sx={{ fontWeight: 'bold' }}>
                  {t('patient_detail.initial_password_client_app')}
                </Typography>
                <Typography color='info' sx={{ wordBreak: 'break-word' }}>
                  {' '}
                  {patient.initialPassword || 'N/A'}{' '}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' sx={{ fontWeight: 'bold' }}>
                  {t('patient_detail.client_app_link')}
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
                  {t('patient_detail.age')}
                </Typography>
                <Typography>{patient.age ?? 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  {t('patient_detail.gender')}
                </Typography>
                <Typography>{patient.gender || 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  {t('patient_detail.phone')}
                </Typography>
                <Typography>{patient.phoneNumber || 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  {t('patient_detail.address')}
                </Typography>
                <Typography>{patient.address || 'N/A'}</Typography>
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <Typography variant='body2' color='textSecondary'>
                  {t('patient_detail.date_of_admission')}
                </Typography>
                <Typography>{patient.dateOfAdmission || 'N/A'}</Typography>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      ) : (
        <Typography variant='h6' sx={{ mb: 3 }}>
          {t('patient_detail.loading_patient_data')}
        </Typography>
      )}

      <CustomizedDivider />

      <div>
        <div style={{ display: 'flex', gap: '30px', alignItems: 'center', marginBottom: '10px' }}>
          <Typography variant='h2'>{t('patient_detail.counseling_plan')}</Typography>
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
            {t('patient_detail.go_to_counseling_plan')}
          </Button>
        </div>
        {currentCounselingPlanPhase ? (
          <Typography>
            {t('patient_detail.current_phase')}:{' '}
            <strong>{currentCounselingPlanPhase.phaseName}</strong>
          </Typography>
        ) : (
          <Typography>{t('patient_detail.currently_no_phase_is_active')}</Typography>
        )}
      </div>

      <CustomizedDivider />

      <MeetingOverviewComponent />

      <CustomizedDivider />

      <ExerciseOverviewComponent />

      <CustomizedDivider />

      <Dialog open={openChatbotDialog} onClose={handleCloseChatbotDialog}>
        <DialogTitle>{t('patient_detail.create_a_new_chatbot')}</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {t('patient_detail.please_enter_name_for_chatbot')}:
          </DialogContentText>
          <TextField
            autoFocus
            margin='dense'
            label={t('patient_detail.chatbot_name')}
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
            {t('patient_detail.create_bot')}
          </Button>
        </DialogActions>
      </Dialog>

      <GAD7TestDetail />

      <CustomizedDivider />

      <PatientInteractionsComponent />

      <CustomizedDivider />

      <FilesTable
        title={t('patient_detail.files_visible_to_client')}
        allDocuments={patientDocumentsVisibleToPatient}
        handleFileUpload={handleFileUploadSharedWithPatient}
        handleDeleteFile={handleDeleteFile}
        downloadFile={downloadFile}
      />

      <CustomizedDivider />

      <FilesTable
        title={t('patient_detail.files_visible_only_to_coach')}
        allDocuments={patientDocumentsNotVisibleToPatient}
        handleFileUpload={handleFileUploadNotSharedWithPatient}
        handleDeleteFile={handleDeleteFile}
        downloadFile={downloadFile}
      />

      <CustomizedDivider />

      <ChatbotOverview />
    </Layout>
  )
}

export default PatientDetail
