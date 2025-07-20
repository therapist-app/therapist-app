import { ReactElement } from 'react'
import { Link, useParams } from 'react-router-dom'

import { ExerciseOutputDTO } from '../../../api'
import { getPathFromPage, PAGES } from '../../../utils/routes'

interface CounselingPlanExerciseDetailProps {
  exercise: ExerciseOutputDTO
}

const CounselingPlanExerciseDetail = ({
  exercise,
}: CounselingPlanExerciseDetailProps): ReactElement => {
  const { patientId } = useParams()

  return (
    <div>
      <Link
        style={{ fontFamily: 'Roboto' }}
        to={getPathFromPage(PAGES.EXERCISES_DETAILS_PAGE, {
          patientId: patientId ?? '',
          exerciseId: exercise.id ?? '',
        })}
      >
        {exercise.exerciseTitle}
      </Link>
    </div>
  )
}

export default CounselingPlanExerciseDetail
