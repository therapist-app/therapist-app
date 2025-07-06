import { Typography } from '@mui/material'
import { ReactElement } from 'react'

import { CounselingPlanPhaseGoalOutputDTO } from '../../../api'

interface CounselingPlanPhaseGoalDetailProps {
  goal: CounselingPlanPhaseGoalOutputDTO
}

const CounselingPlanPhaseGoalDetail = ({
  goal,
}: CounselingPlanPhaseGoalDetailProps): ReactElement => {
  return (
    <div>
      <Typography style={{ fontWeight: 'bold' }}>{goal.goalName}</Typography>
      <Typography>{goal.goalDescription}</Typography>
    </div>
  )
}

export default CounselingPlanPhaseGoalDetail
