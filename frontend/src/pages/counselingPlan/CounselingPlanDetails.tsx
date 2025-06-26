import { Divider, Typography } from '@mui/material'
import React, { ReactElement, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

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
          marginBottom: '20px',
          alignItems: 'start',
          width: '600px',
        }}
      >
        <Typography variant='h5'>Counseling Plan Phases:</Typography>
        <ul>
          {counselingPlan?.counselingPlanPhasesOutputDTO?.map((phase, idx) => (
            <li key={phase.id}>
              <CounselingPlanPhaseDetail
                phase={phase}
                onSuccess={handleCreateCounselingPlanPhase}
              />

              {idx !== amountOfPhases - 1 && (
                <Divider
                  style={{
                    margin: '20px 0',
                    width: '500px',
                    height: '2px',
                    color: 'black',
                    backgroundColor: 'black',
                  }}
                />
              )}
            </li>
          ))}
        </ul>

        <CreateCounselingPlanePhase
          counselingPlanId={counselingPlan?.id || ''}
          onSuccess={handleCreateCounselingPlanPhase}
        />
      </div>
    </Layout>
  )
}

export default CounselingPlanDetails
