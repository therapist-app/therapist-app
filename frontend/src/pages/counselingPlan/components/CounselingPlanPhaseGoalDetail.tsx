import { IconButton, Typography } from '@mui/material'
import { ReactElement } from 'react'

import { CounselingPlanPhaseGoalOutputDTO } from '../../../api'
import DeleteIcon from '../../../icons/DeleteIcon'
import { deleteCounselingPlanPhaseGoal } from '../../../store/counselingPlanSlice'
import { useAppDispatch } from '../../../utils/hooks'

interface CounselingPlanPhaseGoalDetailProps {
  goal: CounselingPlanPhaseGoalOutputDTO
  refresh: () => void
}

const CounselingPlanPhaseGoalDetail = ({
  goal,
  refresh,
}: CounselingPlanPhaseGoalDetailProps): ReactElement => {
  const dispatch = useAppDispatch()
  const handleDeleteGoal = async (): Promise<void> => {
    await dispatch(deleteCounselingPlanPhaseGoal(goal.id ?? ''))
    refresh()
  }
  return (
    <div>
      <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
        <Typography style={{ fontWeight: 'bold' }}>{goal.goalName}</Typography>
        <IconButton onClick={handleDeleteGoal} sx={{ width: 'fit-content' }}>
          <DeleteIcon />
        </IconButton>
      </div>
      <Typography>{goal.goalDescription}</Typography>
    </div>
  )
}

export default CounselingPlanPhaseGoalDetail
