import { Button, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'

import { CreateCounselingPlanPhaseGoalDTO } from '../../../api'
import {
  createCounselingPlanPhaseGoal,
  createCounselingPlanPhaseGoalAIGenerated,
} from '../../../store/counselingPlanSlice'
import { useAppDispatch } from '../../../utils/hooks'
import { getCurrentLanguage } from '../../../utils/languageUtil'
import { useTranslation } from 'react-i18next'

interface CreateCounselingPlanPhaseGoalProps {
  counselingPlanPhaseId: string
  onSuccess: () => void
}

const CreateCounselingPlanPhaseGoal = ({
  counselingPlanPhaseId,
  onSuccess,
}: CreateCounselingPlanPhaseGoalProps): ReactElement => {
  const [open, setOpen] = useState(false)
  const { t } = useTranslation()
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
      createCounselingPlanPhaseGoalAIGenerated({
        counselingPlanPhaseId: counselingPlanPhaseId,
        language: getCurrentLanguage(),
      })
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
          {t('counseling_plan.add_a_goal')}
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            label={t('counseling_plan.goal_name')}
            name='goalName'
            value={formValues.goalName}
            onChange={handleChange}
          />
          <TextField
            label={t('counseling_plan.goal_description')}
            name='goalDescription'
            value={formValues.goalDescription}
            onChange={handleChange}
          />
          <div style={{ display: 'flex', gap: '10px' }}>
            <Button type='submit' variant='contained'>
              {t('counseling_plan.create_goal')}
            </Button>
            <Button variant='contained' color='success' onClick={handleGenrateAI}>
              {t('counseling_plan.make_ai_generated_suggestion_for_goal')}
            </Button>
            <Button variant='outlined' color='error' onClick={handleCancel}>
              {t('counseling_plan.cancel')}
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateCounselingPlanPhaseGoal
