import { Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'

import { AddExerciseToCounselingPlanPhaseDTO, CounselingPlanPhaseOutputDTO } from '../../../api'
import { addExerciseToCounselingPlanPhase } from '../../../store/counselingPlanSlice'
import { RootState } from '../../../store/store'
import {
  cancelButtonStyles,
  commonButtonStyles,
  successButtonStyles,
} from '../../../styles/buttonStyles'
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
  const { t } = useTranslation()
  const { allExercisesOfPatient } = useSelector((state: RootState) => state.exercise)

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
    setOpen(false)

    onSuccess()
  }

  const handleCancel = (): void => {
    setOpen(false)
  }

  const handleAddExerciseFirstClick = (): void => {
    setOpen(true)
  }

  return (
    <div>
      {!open ? (
        <Button
          sx={{ ...commonButtonStyles, minWidth: '210px' }}
          onClick={handleAddExerciseFirstClick}
        >
          {t('counseling_plan.add_existing_exercise')}
        </Button>
      ) : (
        <div style={{ display: 'flex', gap: '10px', flexDirection: 'column' }}>
          <div style={{ display: 'flex', gap: '10px' }}>
            <FormControl sx={{ minWidth: '200px' }}>
              <InputLabel>Exercise</InputLabel>
              <Select
                id='demo-simple-select'
                value={selectedExerciseId}
                label={t('exercise.exercise')}
                onChange={handleSelectExerciseChange}
              >
                {exercisesToSelect.map((exercise) => (
                  <MenuItem value={exercise.id}>{exercise.exerciseTitle}</MenuItem>
                ))}
              </Select>
            </FormControl>
            <Button sx={{ ...successButtonStyles, minWidth: '210px' }} onClick={handleAddExercise}>
              {t('counseling_plan.add_existing_exercise')}
            </Button>
            <Button sx={{ ...cancelButtonStyles, minWidth: '100px' }} onClick={handleCancel}>
              {t('counseling_plan.cancel')}
            </Button>
          </div>
        </div>
      )}
    </div>
  )
}

export default AddCounselingPlanExercise
