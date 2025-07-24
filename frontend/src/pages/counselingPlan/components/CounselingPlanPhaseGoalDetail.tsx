import { IconButton, Typography } from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement } from 'react'

import { CounselingPlanPhaseGoalOutputDTO } from '../../../api'
import { useNotify } from '../../../hooks/useNotify'
import DeleteIcon from '../../../icons/DeleteIcon'
import { deleteCounselingPlanPhaseGoal } from '../../../store/counselingPlanSlice'
import { handleError } from '../../../utils/handleError'
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
  const { notifyError, notifySuccess } = useNotify()

  const handleDeleteGoal = async (): Promise<void> => {
    try {
      await dispatch(deleteCounselingPlanPhaseGoal(goal.id ?? '')).unwrap()
      notifySuccess('Goal removed successfully')
      refresh()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
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
