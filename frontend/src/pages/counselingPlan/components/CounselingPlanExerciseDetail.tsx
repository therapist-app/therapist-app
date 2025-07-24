import { IconButton } from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement } from 'react'
import { Link, useParams } from 'react-router-dom'

import { ExerciseOutputDTO } from '../../../api'
import { useNotify } from '../../../hooks/useNotify'
import DeleteIcon from '../../../icons/DeleteIcon'
import { removeExerciseFromCounselingPlanPhase } from '../../../store/counselingPlanSlice'
import { handleError } from '../../../utils/handleError'
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
  const { notifyError, notifySuccess } = useNotify()

  const handleRemoveExercise = async (): Promise<void> => {
    try {
      await dispatch(
        removeExerciseFromCounselingPlanPhase({
          exerciseId: exercise.id ?? '',
          counselingPlanPhaseId: counselingPlanPhaseId,
        })
      ).unwrap()
      notifySuccess('Exercise removed successfully')
      refresh()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
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
