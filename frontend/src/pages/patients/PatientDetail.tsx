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
import { useNotify } from '../../hooks/useNotify'
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
import { commonButtonStyles, disabledButtonStyles } from '../../styles/buttonStyles'
import { patientDocumentApi } from '../../utils/api'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'
import ChatbotOverview from '../chatbot/ChatbotOverview'
import ExerciseOverviewComponent from '../exercises/components/ExerciseOverviewComponent'
import GAD7TestDetail from '../gad7Test/GAD7TestDetail'
import MeetingOverviewComponent from '../meetings/components/MeetingOverviewComponent'

const PatientDetail = (): ReactElement => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const { notifyError, notifySuccess } = useNotify()
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
    dispatch(getCurrentlyLoggedInTherapist()).catch((error: unknown) => {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    })
  }, [dispatch, notifyError])

  useEffect(() => {
    setCurrentCounselingPlanPhase(
      counselingPlan?.counselingPlanPhasesOutputDTO?.find(
        (phase) =>
          new Date(phase.startDate ?? '').getTime() < new Date().getTime() &&
          new Date(phase.endDate ?? '').getTime() > new Date().getTime()
      )
    )
  }, [counselingPlan?.counselingPlanPhasesOutputDTO, counselingPlan])

  useEffect((): void => {
    const load = async (): Promise<void> => {
      try {
        await Promise.all([
          dispatch(getAllPatientsOfTherapist()).unwrap(),
          dispatch(getAllPatientDocumentsOfPatient(patientId ?? '')).unwrap(),
          dispatch(getAllMeetingsOfPatient(patientId ?? '')).unwrap(),
          dispatch(getAllExercisesOfPatient(patientId ?? '')).unwrap(),
          dispatch(getCounselingPlanByPatientId(patientId ?? '')).unwrap(),
        ])
      } catch (error) {
        notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
      }
    }
    void load()
  }, [dispatch, patientId, refreshPatientDocumentsCounter, notifyError])

  const handleFileUploadNotSharedWithPatient = async (file: File): Promise<void> => {
    try {
      await dispatch(
        createDocumentForPatient({
          file: file,
          patientId: patientId ?? '',
          isSharedWithPatient: false,
        })
      ).unwrap()
      setRefreshPatientDocumentsCounter((prev) => prev + 1)
      notifySuccess(t('patient_detail.file_upload_success'))
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleFileUploadSharedWithPatient = async (file: File): Promise<void> => {
    try {
      await dispatch(
        createDocumentForPatient({
          file: file,
          patientId: patientId ?? '',
          isSharedWithPatient: true,
        })
      ).unwrap()
      setRefreshPatientDocumentsCounter((prev) => prev + 1)
      notifySuccess(t('patient_detail.file_upload_success'))
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleDeleteFile = async (fileId: string): Promise<void> => {
    try {
      await dispatch(deleteDocumentOfPatient(fileId)).unwrap()
      setRefreshPatientDocumentsCounter((prev) => prev + 1)
      notifySuccess(t('patient_detail.file_delete_success'))
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const downloadFile = async (fileId: string): Promise<string> => {
    try {
      const response = await patientDocumentApi.downloadPatientDocument(fileId, {
        responseType: 'blob',
      })
      const file = response.data
      const url = window.URL.createObjectURL(file)
      return url
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
      throw error
    }
  }

  const handleCloseChatbotDialog = (): void => {
    setOpenChatbotDialog(false)
    setChatbotName('')
  }

  const handleCreateNewChatbot = (): void => {
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
            <Grid container alignItems='center' justifyContent='space-between' sx={{ mb: 2 }}>
              <Grid item>
                <Typography variant='h3' gutterBottom>
                  {t('patient_detail.client')}: {patient.name}
                </Typography>
              </Grid>
              <Grid item>
                <Button
                  variant='contained'
                  color='primary'
                  onClick={() =>
                    navigate(
                      getPathFromPage(PAGES.CLIENT_INTERACTIONS_PAGE, {
                        patientId: patientId ?? '',
                      })
                    )
                  }
                  sx={{ ...commonButtonStyles, minWidth: '220px' }}
                >
                  {t('patient_detail.view_client_interactions')}
                </Button>
              </Grid>
            </Grid>
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
            sx={{ ...commonButtonStyles, minWidth: '210px' }}
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
          <Button onClick={handleCloseChatbotDialog} sx={commonButtonStyles}>
            {t('patient_detail.cancel')}
          </Button>
          <Button
            onClick={handleCreateNewChatbot}
            variant='contained'
            sx={chatbotName.trim() ? commonButtonStyles : disabledButtonStyles}
            disabled={!chatbotName.trim()}
          >
            {t('patient_detail.create_bot')}
          </Button>
        </DialogActions>
      </Dialog>

      <GAD7TestDetail />

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
