import { IconButton } from '@mui/material'
import { ReactElement } from 'react'
import { Link, useParams } from 'react-router-dom'

import { ExerciseOutputDTO } from '../../../api'
import DeleteIcon from '../../../icons/DeleteIcon'
import { removeExerciseFromCounselingPlanPhase } from '../../../store/counselingPlanSlice'
import { useAppDispatch } from '../../../utils/hooks'
import { getPathFromPage, PAGES } from '../../../utils/routes'

interface CounselingPlanExerciseDetailProps {
  exercise: ExerciseOutputDTO
  counselingPlanPhaseId: string
  refresh: () => void
}

const CounselingPlanExerciseDetail = ({
  exercise,
  counselingPlanPhaseId,
  refresh,
}: CounselingPlanExerciseDetailProps): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()

  const handleRemoveExercise = async (): Promise<void> => {
    await dispatch(
      removeExerciseFromCounselingPlanPhase({
        exerciseId: exercise.id ?? '',
        counselingPlanPhaseId: counselingPlanPhaseId,
      })
    )
    refresh()
  }

  return (
    <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
      <Link
        style={{ fontFamily: 'Roboto' }}
        to={getPathFromPage(PAGES.EXERCISES_DETAILS_PAGE, {
          patientId: patientId ?? '',
          exerciseId: exercise.id ?? '',
        })}
      >
        {exercise.exerciseTitle}
      </Link>

      <IconButton onClick={handleRemoveExercise} sx={{ width: 'fit-content' }}>
        <DeleteIcon />
      </IconButton>
    </div>
  )
}

export default CounselingPlanExerciseDetail
