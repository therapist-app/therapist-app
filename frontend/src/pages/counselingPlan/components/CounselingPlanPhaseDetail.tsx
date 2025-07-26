import EditIcon from '@mui/icons-material/Edit'
import { Button, IconButton, TextField, Typography } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { CounselingPlanPhaseOutputDTO, UpdateCounselingPlanPhase } from '../../../api'
import { useNotify } from '../../../hooks/useNotify'
import DeleteIcon from '../../../icons/DeleteIcon'
import {
  deleteCounselingPlanPhase,
  updateCounselingPlanPhase,
} from '../../../store/counselingPlanSlice'
import { cancelButtonStyles, commonButtonStyles } from '../../../styles/buttonStyles'
import { formatDateNicely } from '../../../utils/dateUtil'
import { useAppDispatch } from '../../../utils/hooks'
import AddCounselingPlanExercise from './AddCounselingPlanExercise'
import CounselingPlanExerciseDetail from './CounselingPlanExerciseDetail'
import CounselingPlanPhaseGoalDetail from './CounselingPlanPhaseGoalDetail'
import CreateCounselingPlanExercise from './CreateCounselingPlanExercise'
import CreateCounselingPlanPhaseGoal from './CreateCounselingPlanPhaseGoal'

interface CounselingPlanPhaseDetailProps {
  phase: CounselingPlanPhaseOutputDTO
  phaseNumber: number
  onSuccess: () => void
  isLastPhase: boolean
}

const CounselingPlanPhaseDetail = ({
  phase,
  phaseNumber,
  onSuccess,
  isLastPhase,
}: CounselingPlanPhaseDetailProps): ReactElement => {
  const { t } = useTranslation()
  const { notifyError, notifySuccess } = useNotify()
  const dispatch = useAppDispatch()

  const [isEditing, setIsEditing] = useState(false)
  const [updatePhaseName, setUpdatePhaseName] = useState(phase.phaseName)
  const [updateDurationInWeeks, setUpdateDurationInWeeks] = useState(phase.durationInWeeks)

  const handleCreateCounselingPlanPhaseGoal = (): void => {
    onSuccess()
  }

  const handleClickEditPhase = (): void => {
    setIsEditing(true)
    setUpdatePhaseName(phase.phaseName)
    setUpdateDurationInWeeks(phase.durationInWeeks)
  }

  const handleCancelUpdate = (): void => {
    setIsEditing(false)
    setUpdatePhaseName(phase.phaseName)
    setUpdateDurationInWeeks(phase.durationInWeeks)
  }

  const handleSubmitUpdate = async (): Promise<void> => {
    try {
      const dto: UpdateCounselingPlanPhase = {
        counselingPlanPhaseId: phase.id,
        phaseName: updatePhaseName,
        durationInWeeks: updateDurationInWeeks,
      }
      await dispatch(updateCounselingPlanPhase(dto)).unwrap()
      setIsEditing(false)
      notifySuccess(t('counseling_plan.phase_updated_success'))
      onSuccess()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleDeletePhase = async (): Promise<void> => {
    try {
      await dispatch(deleteCounselingPlanPhase(phase.id ?? '')).unwrap()
      notifySuccess(t('counseling_plan.phase_deleted_success'))
      onSuccess()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', width: '100%' }}>
      <div style={{ display: ' flex', gap: '10px', alignItems: 'center' }}>
        <Typography variant='h3'>
          {phaseNumber}. {phase.phaseName}
        </Typography>
        <IconButton onClick={handleClickEditPhase}>
          {' '}
          <EditIcon sx={{ height: '25px', color: 'blue' }} />
        </IconButton>
        {isLastPhase && (
          <IconButton onClick={handleDeletePhase} sx={{ width: 'fit-content' }}>
            <DeleteIcon />
          </IconButton>
        )}
      </div>

      <div style={{ paddingLeft: '20px' }}>
        {!isEditing ? (
          <>
            {' '}
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
          </>
        ) : (
          <>
            <TextField
              required
              fullWidth
              label={t('counseling_plan.phase_name')}
              value={updatePhaseName}
              onChange={(e) => setUpdatePhaseName(e.target.value)}
            />
            <TextField
              sx={{ marginTop: '20px' }}
              label={t('counseling_plan.duration_in_weeks')}
              value={updateDurationInWeeks}
              type='number'
              onChange={(e) => setUpdateDurationInWeeks(parseInt(e.target.value))}
            />
            <div style={{ display: 'flex', gap: '10px' }}>
              <Button sx={{ ...commonButtonStyles }} onClick={handleSubmitUpdate}>
                {t('counseling_plan.update_phase')}
              </Button>

              <Button sx={{ ...cancelButtonStyles }} onClick={handleCancelUpdate}>
                {t('counseling_plan.cancel')}
              </Button>
            </div>
          </>
        )}

        <Typography sx={{ marginTop: '20px', marginBottom: '10px' }} variant='h4'>
          {t('counseling_plan.goals')}:
        </Typography>
        <ul style={{ marginBottom: '10px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
          {phase.phaseGoalsOutputDTO?.map((goal) => (
            <li key={goal.id}>
              <CounselingPlanPhaseGoalDetail goal={goal} refresh={onSuccess} />
            </li>
          ))}
        </ul>
        <CreateCounselingPlanPhaseGoal
          counselingPlanPhaseId={phase.id || ''}
          onSuccess={handleCreateCounselingPlanPhaseGoal}
        />

        <Typography sx={{ marginTop: '20px', marginBottom: '10px' }} variant='h4'>
          {t('counseling_plan.exercises')}:
        </Typography>
        <ul style={{ marginBottom: '20px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
          {phase.phaseExercisesOutputDTO?.map((exercise) => (
            <li key={exercise.id}>
              <CounselingPlanExerciseDetail
                exercise={exercise}
                counselingPlanPhaseId={phase.id ?? ''}
                refresh={onSuccess}
              />
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
