import EditIcon from '@mui/icons-material/Edit'
import { Button, Checkbox, IconButton, TextField, Typography } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { CounselingPlanPhaseGoalOutputDTO, UpdateCounselingPlanPhaseGoalDTO } from '../../../api'
import { useNotify } from '../../../hooks/useNotify'
import DeleteIcon from '../../../icons/DeleteIcon'
import {
  deleteCounselingPlanPhaseGoal,
  updateCounselingPlanPhaseGoal,
} from '../../../store/counselingPlanSlice'
import { cancelButtonStyles, commonButtonStyles } from '../../../styles/buttonStyles'
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
  const { t } = useTranslation()
  const { notifyError, notifySuccess } = useNotify()
  const [isEditing, setIsEditing] = useState(false)
  const [updatePhaseGoalName, setUpdatePhaseGoalName] = useState(goal.goalName)
  const [updatePhaseGoalDescription, setUpdatePhaseGoalDescription] = useState(goal.goalDescription)

  const handleDeleteGoal = async (): Promise<void> => {
    try {
      await dispatch(deleteCounselingPlanPhaseGoal(goal.id ?? '')).unwrap()
      notifySuccess('Goal removed successfully')
      refresh()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleClickEditPhaseGoal = (): void => {
    setIsEditing(true)
    setUpdatePhaseGoalName(goal.goalName)
    setUpdatePhaseGoalDescription(goal.goalDescription)
  }

  const handleCancelUpdate = (): void => {
    setIsEditing(false)
    setUpdatePhaseGoalName(goal.goalName)
    setUpdatePhaseGoalDescription(goal.goalDescription)
  }

  const handleSubmitUpdate = async (): Promise<void> => {
    try {
      const dto: UpdateCounselingPlanPhaseGoalDTO = {
        counselingPlanPhaseGoalId: goal.id,
        goalName: updatePhaseGoalName,
        goalDescription: updatePhaseGoalDescription,
      }
      await dispatch(updateCounselingPlanPhaseGoal(dto)).unwrap()
      setIsEditing(false)
      notifySuccess(t('counseling_plan.phase_goal_updated_success'))
      refresh()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleToggleIsCompleted = async (): Promise<void> => {
    try {
      const dto: UpdateCounselingPlanPhaseGoalDTO = {
        counselingPlanPhaseGoalId: goal.id,
        isCompleted: !goal.isCompleted,
      }
      await dispatch(updateCounselingPlanPhaseGoal(dto)).unwrap()
      notifySuccess(t('counseling_plan.phase_goal_updated_success'))
      refresh()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  return (
    <div>
      <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
        <Typography style={{ fontWeight: 'bold' }}>{goal.goalName}</Typography>
        <div style={{ display: 'flex', alignItems: 'center' }}>
          <Checkbox
            sx={{ width: 'fit-content' }}
            name='isCompleted'
            checked={goal.isCompleted}
            onChange={handleToggleIsCompleted}
          />
          <Typography>Is completed?</Typography>
        </div>
        <Typography> |</Typography>
        {!isEditing && (
          <IconButton onClick={handleClickEditPhaseGoal} sx={{ width: 'fit-content' }}>
            <EditIcon sx={{ color: 'blue' }} />
          </IconButton>
        )}
        <IconButton onClick={handleDeleteGoal} sx={{ width: 'fit-content' }}>
          <DeleteIcon />
        </IconButton>
      </div>
      {!isEditing ? (
        <Typography>{goal.goalDescription}</Typography>
      ) : (
        <form style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
          <TextField
            required
            label={t('counseling_plan.goal_name')}
            name='goalName'
            value={updatePhaseGoalName}
            onChange={(e) => setUpdatePhaseGoalName(e.target.value)}
          />
          <TextField
            label={t('counseling_plan.goal_description')}
            name='goalDescription'
            value={updatePhaseGoalDescription}
            onChange={(e) => setUpdatePhaseGoalDescription(e.target.value)}
          />
          <div style={{ display: 'flex', gap: '10px' }}>
            <Button onClick={handleSubmitUpdate} sx={{ ...commonButtonStyles }}>
              {t('counseling_plan.updateGoal')}
            </Button>

            <Button sx={{ ...cancelButtonStyles }} onClick={handleCancelUpdate}>
              {t('counseling_plan.cancel')}
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CounselingPlanPhaseGoalDetail
