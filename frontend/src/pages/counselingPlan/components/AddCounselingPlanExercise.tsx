import { ReactElement } from 'react'

interface AddCounselingPlanExerciseProps {
  counselingPlanPhaseId: string
  onSuccess: () => void
}

const AddCounselingPlanExercise = ({
  counselingPlanPhaseId,
  onSuccess,
}: AddCounselingPlanExerciseProps): ReactElement => {
  console.log(counselingPlanPhaseId)
  console.log(onSuccess)
  return <div>AddCounselingPlanExercise</div>
}

export default AddCounselingPlanExercise
