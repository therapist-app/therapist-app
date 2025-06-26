import { Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useSelector } from 'react-redux'

import { AddExerciseToCounselingPlanPhaseDTO, CounselingPlanPhaseOutputDTO } from '../../../api'
import { addExerciseToCounselingPlanPhase } from '../../../store/counselingPlanSlice'
import { RootState } from '../../../store/store'
import { useAppDispatch } from '../../../utils/hooks'

interface AddCounselingPlanExerciseProps {
  counselingPlanPhaseId: string
  onSuccess: () => void
  counselingPlanPhase: CounselingPlanPhaseOutputDTO
}

const AddCounselingPlanExercise = ({
  counselingPlanPhaseId,
  onSuccess,
  counselingPlanPhase,
}: AddCounselingPlanExerciseProps): ReactElement => {
  const dispatch = useAppDispatch()
  const { allExercisesOfPatient } = useSelector((state: RootState) => state.exercise)
  console.log(allExercisesOfPatient)

  const exercisesToSelect = allExercisesOfPatient.filter(
    (exercise) =>
      !counselingPlanPhase.phaseExercisesOutputDTO?.find((ex2) => ex2.id === exercise.id)
  )

  const [selectedExerciseId, setSelectedExerciseId] = useState<string>('')
  const [open, setOpen] = useState(false)

  const handleSelectExerciseChange = async (event: SelectChangeEvent): Promise<void> => {
    setSelectedExerciseId(event.target.value)
  }

  const handleAddExercise = async (): Promise<void> => {
    if (!selectedExerciseId) {
      return
    }

    const addExerciseToCounselingPlanPhaseDTO: AddExerciseToCounselingPlanPhaseDTO = {
      exerciseId: selectedExerciseId,
      counselingPlanPhaseId: counselingPlanPhaseId,
    }
    await dispatch(addExerciseToCounselingPlanPhase(addExerciseToCounselingPlanPhaseDTO))
    onSuccess()
  }

  if (exercisesToSelect.length === 0) {
    return (
      <Button sx={{ maxWidth: '250px' }} variant='contained' disabled>
        Create a new exercise first
      </Button>
    )
  }

  return (
    <div>
      {!open ? (
        <Button variant='contained' onClick={() => setOpen(true)}>
          Add an Exercise
        </Button>
      ) : (
        <div style={{ display: 'flex', gap: '10px' }}>
          <FormControl sx={{ minWidth: '200px' }}>
            <InputLabel>Exercise</InputLabel>
            <Select
              id='demo-simple-select'
              value={selectedExerciseId}
              label='Exercise'
              onChange={handleSelectExerciseChange}
            >
              {exercisesToSelect.map((exercise) => (
                <MenuItem value={exercise.id}>{exercise.title}</MenuItem>
              ))}
            </Select>
          </FormControl>
          <Button variant='contained' color='success' onClick={handleAddExercise}>
            Add Exercise
          </Button>
        </div>
      )}
    </div>
  )
}

export default AddCounselingPlanExercise
