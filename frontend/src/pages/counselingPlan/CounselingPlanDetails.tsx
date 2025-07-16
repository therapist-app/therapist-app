import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import EditIcon from '@mui/icons-material/Edit'
import { IconButton, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

import { UpdateCounselingPlanDTO } from '../../api'
import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import Layout from '../../generalComponents/Layout'
import { getCounselingPlanByPatientId, updateCounselingPlan } from '../../store/counselingPlanSlice'
import { getAllExercisesOfPatient } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { formatDateNicely } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'
import CounselingPlanPhaseDetail from './components/CounselingPlanPhaseDetail'
import CreateCounselingPlanePhase from './components/CreateCounselingPlanePhase'
import { useTranslation } from 'react-i18next'

interface FormValues {
  startOfTherapy: Date | null
}

const CounselingPlanDetails = (): ReactElement => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()
  const { counselingPlan } = useSelector((state: RootState) => state.counselingPlan)
  const [isEditing, setIsEditing] = useState(false)
  const [formData, setFormData] = useState<FormValues>({
    startOfTherapy: new Date(),
  })

  const amountOfPhases = counselingPlan?.counselingPlanPhasesOutputDTO?.length ?? 0

  useEffect(() => {
    if (patientId) {
      dispatch(getCounselingPlanByPatientId(patientId))
      dispatch(getAllExercisesOfPatient(patientId))
    }
  }, [patientId, dispatch])

  const refresh = (): void => {
    dispatch(getCounselingPlanByPatientId(patientId || ''))
  }

  const handleUpdate = (): void => {
    const dto: UpdateCounselingPlanDTO = {
      counselingPlanId: counselingPlan?.id ?? '',
      startOfTherapy: formData.startOfTherapy?.toISOString(),
    }
    dispatch(updateCounselingPlan(dto))
    setIsEditing(false)
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
              <EditIcon sx={{ height: '25px' }}></EditIcon>
            </IconButton>
          </div>
        ) : (
          <div style={{ display: 'flex', gap: '10px' }}>
            <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
              <DateTimePicker
                label={t('counseling_plan.counseling_start_date')}
                value={formData.startOfTherapy}
                onChange={(newValue: Date | null) => {
                  setFormData({
                    ...formData,
                    startOfTherapy: newValue,
                  })
                }}
              />
            </LocalizationProvider>
            <IconButton onClick={handleUpdate}>
              <CheckIcon sx={{ height: '25px', color: 'green' }}></CheckIcon>
            </IconButton>
            <IconButton onClick={() => setIsEditing(false)}>
              <ClearIcon sx={{ height: '25px', color: 'red' }}></ClearIcon>
            </IconButton>
          </div>
        )}

        <Typography variant='h2'>{t('counseling_plan.counseling_plan_phases')}</Typography>
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
