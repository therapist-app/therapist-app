import { Button, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'

import { CreateCounselingPlanPhaseGoalDTO } from '../../../api'
import {
  createCounselingPlanPhaseGoal,
  createCounselingPlanPhaseGoalAIGenerated,
} from '../../../store/counselingPlanSlice'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateCounselingPlanPhaseGoalProps {
  counselingPlanPhaseId: string
  onSuccess: () => void
}

const CreateCounselingPlanPhaseGoal = ({
  counselingPlanPhaseId,
  onSuccess,
}: CreateCounselingPlanPhaseGoalProps): ReactElement => {
  const [open, setOpen] = useState(false)

  const dispatch = useAppDispatch()

  const [formValues, setFormValues] = useState<CreateCounselingPlanPhaseGoalDTO>({
    counselingPlanPhaseId: counselingPlanPhaseId,
    goalName: '',
    goalDescription: '',
  })

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()
    await dispatch(createCounselingPlanPhaseGoal(formValues))
    onSuccess()
    setOpen(false)
    setFormValues({
      counselingPlanPhaseId: counselingPlanPhaseId,
      goalName: '',
      goalDescription: '',
    })
  }

  const handleGenrateAI = async (): Promise<void> => {
    const aiGeneratedPhaseGoal: CreateCounselingPlanPhaseGoalDTO = await dispatch(
      createCounselingPlanPhaseGoalAIGenerated(counselingPlanPhaseId)
    ).unwrap()
    const newFormValues: CreateCounselingPlanPhaseGoalDTO = {
      ...aiGeneratedPhaseGoal,
      counselingPlanPhaseId: counselingPlanPhaseId,
    }
    setFormValues(newFormValues)
    setOpen(true)
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormValues({ ...formValues, [e.target.name]: e.target.value })
  }

  const handleCancel = (): void => {
    setOpen(false)
    setFormValues({
      counselingPlanPhaseId: counselingPlanPhaseId,
      goalName: '',
      goalDescription: '',
    })
  }

  return (
    <div>
      {!open ? (
        <Button variant='contained' onClick={() => setOpen(true)}>
          Add a goal
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            label='Goal Name'
            name='goalName'
            value={formValues.goalName}
            onChange={handleChange}
          />
          <TextField
            label='Goal Description'
            name='goalDescription'
            value={formValues.goalDescription}
            onChange={handleChange}
          />
          <div style={{ display: 'flex', gap: '10px' }}>
            <Button type='submit' variant='contained'>
              Create goal
            </Button>
            <Button variant='contained' color='success' onClick={handleGenrateAI}>
              Make AI generated suggestion
            </Button>
            <Button variant='outlined' color='error' onClick={handleCancel}>
              Cancel
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateCounselingPlanPhaseGoal
