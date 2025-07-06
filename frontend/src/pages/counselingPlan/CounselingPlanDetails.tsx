import { Divider, Typography } from '@mui/material'
import React, { ReactElement, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import Layout from '../../generalComponents/Layout'
import { getCounselingPlanByPatientId } from '../../store/counselingPlanSlice'
import { getAllExercisesOfPatient } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { useAppDispatch } from '../../utils/hooks'
import CounselingPlanPhaseDetail from './components/CounselingPlanPhaseDetail'
import CreateCounselingPlanePhase from './components/CreateCounselingPlanePhase'

const CounselingPlanDetails = (): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const { counselingPlan } = useSelector((state: RootState) => state.counselingPlan)

  const amountOfPhases = counselingPlan?.counselingPlanPhasesOutputDTO?.length ?? 0

  useEffect(() => {
    if (patientId) {
      dispatch(getCounselingPlanByPatientId(patientId))
      dispatch(getAllExercisesOfPatient(patientId))
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
          alignItems: 'start',
        }}
      >
        <Typography variant='h2'>Counseling Plan Phases:</Typography>
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
                  onSuccess={handleCreateCounselingPlanPhase}
                />

                {idx !== amountOfPhases - 1 && <CustomizedDivider />}
              </li>
            ))}
          </ul>
        )}

        <CreateCounselingPlanePhase
          counselingPlanId={counselingPlan?.id || ''}
          onSuccess={handleCreateCounselingPlanPhase}
        />
      </div>
    </Layout>
  )
}

export default CounselingPlanDetails
