import { Typography } from '@mui/material'
import { ReactElement } from 'react'

import { CounselingPlanPhaseOutputDTO } from '../../../api'
import { formatDateNicely } from '../../../utils/dateUtil'
import AddCounselingPlanExercise from './AddCounselingPlanExercise'
import CounselingPlanExerciseDetail from './CounselingPlanExerciseDetail'
import CounselingPlanPhaseGoalDetail from './CounselingPlanPhaseGoalDetail'
import CreateCounselingPlanPhaseGoal from './CreateCounselingPlanPhaseGoal'

interface CounselingPlanPhaseDetailProps {
  phase: CounselingPlanPhaseOutputDTO
  onSuccess: () => void
}

const CounselingPlanPhaseDetail = ({
  phase,
  onSuccess,
}: CounselingPlanPhaseDetailProps): ReactElement => {
  const handleCreateCounselingPlanPhaseGoal = (): void => {
    onSuccess()
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', width: '100%' }}>
      <Typography variant='h6'>{phase.phaseName}</Typography>
      <Typography variant='body1'>Start Date: {formatDateNicely(phase.startDate)}</Typography>
      <Typography variant='body1'>End Date: {formatDateNicely(phase.endDate)}</Typography>
      <div></div>
      <Typography variant='h6'>Goals:</Typography>
      <ul style={{ margin: 0 }}>
        {phase.phaseGoalsOutputDTO?.map((goal) => (
          <li key={goal.id}>
            <CounselingPlanPhaseGoalDetail goal={goal} />
          </li>
        ))}
      </ul>
      <div style={{ paddingLeft: phase.phaseGoalsOutputDTO?.length ? '40px' : '0px' }}>
        <CreateCounselingPlanPhaseGoal
          counselingPlanPhaseId={phase.id || ''}
          onSuccess={handleCreateCounselingPlanPhaseGoal}
        />
      </div>
      <Typography variant='h6'>Exercises:</Typography>
      <ul style={{ margin: 0 }}>
        {phase.phaseExercisesOutputDTO?.map((exercise) => (
          <li key={exercise.id}>
            <CounselingPlanExerciseDetail exercise={exercise} />
          </li>
        ))}
      </ul>
      <AddCounselingPlanExercise
        counselingPlanPhaseId={phase.id || ''}
        onSuccess={handleCreateCounselingPlanPhaseGoal}
        counselingPlanPhase={phase}
      />
    </div>
  )
}

export default CounselingPlanPhaseDetail
