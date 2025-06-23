import { Divider, Typography } from '@mui/material'
import { ReactElement, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { getCounselingPlanByPatientId } from '../../store/counselingPlanSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'
import CounselingPlanPhaseDetail from './components/CounselingPlanPhaseDetail'
import CreateCounselingPlanePhase from './components/CreateCounselingPlanePhase'

const CounselingPlanDetails = (): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const { counselingPlan } = useSelector((state: RootState) => state.counselingPlan)

  useEffect(() => {
    if (patientId) {
      dispatch(getCounselingPlanByPatientId(patientId))
    }
  }, [patientId, dispatch])

  const handleCreateCounselingPlanPhase = (): void => {
    dispatch(getCounselingPlanByPatientId(patientId || ''))
  }

  return (
    <Layout>
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          gap: '20px',
          marginBottom: '20px',
          alignItems: 'start',
        }}
      >
        <Typography variant='h5'>Counseling Plan Phases:</Typography>
        {counselingPlan?.counselingPlanPhasesOutputDTO?.map((phase) => (
          <>
            <CounselingPlanPhaseDetail
              key={phase.id}
              phase={phase}
              onSuccess={handleCreateCounselingPlanPhase}
            />

            <Divider style={{ width: '100%' }} />
          </>
        ))}

        <CreateCounselingPlanePhase
          counselingPlanId={counselingPlan?.id || ''}
          onSuccess={handleCreateCounselingPlanPhase}
        />
      </div>
    </Layout>
  )
}

export default CounselingPlanDetails
