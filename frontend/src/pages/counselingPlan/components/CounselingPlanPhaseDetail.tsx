import { Typography } from '@mui/material'
import { ReactElement } from 'react'
import { useTranslation } from 'react-i18next'

import { CounselingPlanPhaseOutputDTO } from '../../../api'
import { formatDateNicely } from '../../../utils/dateUtil'
import AddCounselingPlanExercise from './AddCounselingPlanExercise'
import CounselingPlanExerciseDetail from './CounselingPlanExerciseDetail'
import CounselingPlanPhaseGoalDetail from './CounselingPlanPhaseGoalDetail'
import CreateCounselingPlanExercise from './CreateCounselingPlanExercise'
import CreateCounselingPlanPhaseGoal from './CreateCounselingPlanPhaseGoal'

interface CounselingPlanPhaseDetailProps {
  phase: CounselingPlanPhaseOutputDTO
  phaseNumber: number
  onSuccess: () => void
}

const CounselingPlanPhaseDetail = ({
  phase,
  phaseNumber,
  onSuccess,
}: CounselingPlanPhaseDetailProps): ReactElement => {
  const { t } = useTranslation()
  const handleCreateCounselingPlanPhaseGoal = (): void => {
    onSuccess()
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', width: '100%' }}>
      <Typography variant='h3'>
        {phaseNumber}. {phase.phaseName}
      </Typography>
      <div style={{ paddingLeft: '20px' }}>
        <Typography>
          {t('counseling_plan.start_date')}: {formatDateNicely(phase.startDate)}
        </Typography>
        <Typography>
          {t('counseling_plan.end_date')}: {formatDateNicely(phase.endDate)}
        </Typography>
        <Typography>
          {t('counseling_plan.duration')}: <strong>{phase.durationInWeeks}</strong>{' '}
          {t('counseling_plan.weeks')}
        </Typography>
        <div></div>
        <Typography sx={{ marginTop: '20px', marginBottom: '10px' }} variant='h4'>
          {t('counseling_plan.goals')}:
        </Typography>
        <ul style={{ marginBottom: '10px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
          {phase.phaseGoalsOutputDTO?.map((goal) => (
            <li key={goal.id}>
              <CounselingPlanPhaseGoalDetail goal={goal} />
            </li>
          ))}
        </ul>
        <div>
          <CreateCounselingPlanPhaseGoal
            counselingPlanPhaseId={phase.id || ''}
            onSuccess={handleCreateCounselingPlanPhaseGoal}
          />
        </div>
        <Typography sx={{ marginTop: '20px', marginBottom: '10px' }} variant='h4'>
          {t('counseling_plan.exercises')}:
        </Typography>
        <ul style={{ marginBottom: '10px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
          {phase.phaseExercisesOutputDTO?.map((exercise) => (
            <li key={exercise.id}>
              <CounselingPlanExerciseDetail exercise={exercise} />
            </li>
          ))}
        </ul>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
          <AddCounselingPlanExercise
            counselingPlanPhaseId={phase.id || ''}
            onSuccess={handleCreateCounselingPlanPhaseGoal}
            counselingPlanPhase={phase}
          />
          <CreateCounselingPlanExercise onSuccess={onSuccess} counselingPlanPhase={phase} />
        </div>
      </div>
    </div>
  )
}

export default CounselingPlanPhaseDetail
