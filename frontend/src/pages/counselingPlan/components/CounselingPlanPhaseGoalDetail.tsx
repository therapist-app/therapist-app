import { IconButton, Typography } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement } from 'react'

import { CounselingPlanPhaseGoalOutputDTO } from '../../../api'
import DeleteIcon from '../../../icons/DeleteIcon'
import { deleteCounselingPlanPhaseGoal } from '../../../store/counselingPlanSlice'
import { showError } from '../../../store/errorSlice'
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

  const showMessage = (message: string, severity: AlertColor = 'error') => {
    dispatch(showError({ message: message, severity: severity }))
  }

  const handleDeleteGoal = async (): Promise<void> => {
    try {
      await dispatch(deleteCounselingPlanPhaseGoal(goal.id ?? '')).unwrap()
      showMessage('Goal removed successfully', 'success')
      refresh()
    } catch (error) {
      const msg = handleError(error as AxiosError)
      showMessage(msg, 'error')
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
