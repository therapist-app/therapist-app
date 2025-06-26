import { Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'

import { AddExerciseToCounselingPlanPhaseDTO } from '../../../api'
import { addExerciseToCounselingPlanPhase } from '../../../store/counselingPlanSlice'
import { RootState } from '../../../store/store'
import { useAppDispatch } from '../../../utils/hooks'

interface AddCounselingPlanExerciseProps {
  counselingPlanPhaseId: string
  onSuccess: () => void
}

const AddCounselingPlanExercise = ({
  counselingPlanPhaseId,
  onSuccess,
}: AddCounselingPlanExerciseProps): ReactElement => {
  const dispatch = useAppDispatch()
  const { allExercisesOfPatient } = useSelector((state: RootState) => state.exercise)

  const [selectedExerciseId, setSelectedExerciseId] = useState(allExercisesOfPatient[0]?.id ?? '')
  const [open, setOpen] = useState(false)

  const handleSelectExerciseChange = (event: SelectChangeEvent): void => {
    setSelectedExerciseId(event.target.value)
  }

  const handleAddExercise = async (): Promise<void> => {
    const addExerciseToCounselingPlanPhaseDTO: AddExerciseToCounselingPlanPhaseDTO = {
      exerciseId: selectedExerciseId,
      counselingPlanPhaseId: counselingPlanPhaseId,
    }
    await dispatch(addExerciseToCounselingPlanPhase(addExerciseToCounselingPlanPhaseDTO))
    onSuccess()
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
              {allExercisesOfPatient.map((exercise) => (
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
