import { Typography } from '@mui/material'
import { ReactElement } from 'react'

import { ExerciseOutputDTO } from '../../../api'

interface CounselingPlanExerciseDetailProps {
  exercise: ExerciseOutputDTO
}

const CounselingPlanExerciseDetail = ({
  exercise,
}: CounselingPlanExerciseDetailProps): ReactElement => {
  return (
    <div>
      <Typography style={{ fontWeight: 'bold' }}>{exercise.title}</Typography>
      <Typography>{exercise.exerciseType}</Typography>
    </div>
  )
}

export default CounselingPlanExerciseDetail
