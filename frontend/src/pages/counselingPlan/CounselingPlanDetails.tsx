import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import EditIcon from '@mui/icons-material/Edit'
import { IconButton, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

import { UpdateCounselingPlanDTO } from '../../api'
import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import {
  getCounselingPlanByPatientId,
  getGoalCounts,
  updateCounselingPlan,
} from '../../store/counselingPlanSlice'
import { getAllExercisesOfPatient } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { formatDateNicely } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'
import CounselingPlanPhaseDetail from './components/CounselingPlanPhaseDetail'
import CreateCounselingPlanePhase from './components/CreateCounselingPlanePhase'

interface FormValues {
  startOfTherapy: Date | null
}

const CounselingPlanDetails = (): ReactElement => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()
  const counselingPlanState = useSelector((state: RootState) => state.counselingPlan)
  const { counselingPlan } = counselingPlanState
  const [isEditing, setIsEditing] = useState(false)
  const { notifyError, notifySuccess } = useNotify()
  const [formData, setFormData] = useState<FormValues>({
    startOfTherapy: new Date(),
  })

  const { totalGoals, completedGoals } = getGoalCounts({ counselingPlan: counselingPlanState })

  const amountOfPhases = counselingPlan?.counselingPlanPhasesOutputDTO?.length ?? 0

  useEffect(() => {
    if (!patientId) {
      return
    }

    const load = async (): Promise<void> => {
      try {
        await Promise.all([
          dispatch(getCounselingPlanByPatientId(patientId)).unwrap(),
          dispatch(getAllExercisesOfPatient(patientId)).unwrap(),
        ])
      } catch (error) {
        notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
      }
    }

    void load()
  }, [patientId, dispatch, notifyError])

  const refresh = (): void => {
    if (!patientId) {
      return
    }
    dispatch(getCounselingPlanByPatientId(patientId)).catch((error: unknown) => {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    })
  }

  const handleUpdate = async (): Promise<void> => {
    try {
      const dto: UpdateCounselingPlanDTO = {
        counselingPlanId: counselingPlan?.id ?? '',
        startOfTherapy: formData.startOfTherapy?.toISOString(),
      }
      await dispatch(updateCounselingPlan(dto)).unwrap()
      setIsEditing(false)
      notifySuccess(t('counseling_plan.updated_successfully'))
      refresh()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  return (
    <Layout>
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          gap: '20px',
          alignItems: 'start',
        }}
      >
        {!isEditing ? (
          <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
            <Typography>
              {t('counseling_plan.counseling_start_date')}:{' '}
              <strong>{formatDateNicely(counselingPlan?.startOfTherapy)}</strong>
            </Typography>
            <IconButton onClick={() => setIsEditing(true)}>
              <EditIcon sx={{ height: '25px', color: 'blue' }} />
            </IconButton>
          </div>
        ) : (
          <div style={{ display: 'flex', gap: '10px' }}>
            <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
              <DateTimePicker
                label={t('counseling_plan.counseling_start_date')}
                value={formData.startOfTherapy}
                onChange={(newValue: Date | null): void => {
                  setFormData((prev) => ({
                    ...prev,
                    startOfTherapy: newValue,
                  }))
                }}
              />
            </LocalizationProvider>
            <IconButton onClick={handleUpdate}>
              <CheckIcon sx={{ height: '25px', color: 'green' }} />
            </IconButton>
            <IconButton onClick={() => setIsEditing(false)}>
              <ClearIcon sx={{ height: '25px', color: 'red' }} />
            </IconButton>
          </div>
        )}

        <div>
          <Typography variant='h2'>{t('counseling_plan.counseling_plan')}</Typography>
          <Typography>
            {counselingPlan?.counselingPlanPhasesOutputDTO?.length ?? 0} phases
          </Typography>
          <Typography>
            {completedGoals}/{totalGoals} goals completed
          </Typography>
        </div>
        {!!counselingPlan?.counselingPlanPhasesOutputDTO?.length && (
          <ul
            style={{
              listStyleType: 'none',
              paddingLeft: '30px',
            }}
          >
            {counselingPlan?.counselingPlanPhasesOutputDTO?.map((phase, idx) => (
              <li key={phase.id}>
                <CounselingPlanPhaseDetail
                  phase={phase}
                  phaseNumber={idx + 1}
                  onSuccess={refresh}
                  isLastPhase={idx + 1 === counselingPlan?.counselingPlanPhasesOutputDTO?.length}
                />
                {idx !== amountOfPhases - 1 && <CustomizedDivider />}
              </li>
            ))}
          </ul>
        )}

        <CreateCounselingPlanePhase
          counselingPlanId={counselingPlan?.id || ''}
          onSuccess={refresh}
        />
      </div>
    </Layout>
  )
}

export default CounselingPlanDetails
