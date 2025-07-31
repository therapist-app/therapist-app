import { Button, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { CreateCounselingPlanPhaseGoalDTO } from '../../../api'
import { useNotify } from '../../../hooks/useNotify'
import {
  createCounselingPlanPhaseGoal,
  createCounselingPlanPhaseGoalAIGenerated,
} from '../../../store/counselingPlanSlice'
import {
  cancelButtonStyles,
  commonButtonStyles,
  successButtonStyles,
} from '../../../styles/buttonStyles'
import { useAppDispatch } from '../../../utils/hooks'
import { getCurrentLanguage } from '../../../utils/languageUtil'

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
  const { notifyError, notifySuccess } = useNotify()
  const dispatch = useAppDispatch()
  const [isAIAnswerLoading, setIsAIAnswerLoading] = useState(false)

  const [formValues, setFormValues] = useState<CreateCounselingPlanPhaseGoalDTO>({
    counselingPlanPhaseId: counselingPlanPhaseId,
    goalName: '',
    goalDescription: '',
  })

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()
    try {
      await dispatch(createCounselingPlanPhaseGoal(formValues)).unwrap()
      notifySuccess(t('counseling_plan.goal_created_success'))
      onSuccess()
      setOpen(false)
      setFormValues({
        counselingPlanPhaseId: counselingPlanPhaseId,
        goalName: '',
        goalDescription: '',
      })
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  const handleGenrateAI = async (): Promise<void> => {
    try {
      setIsAIAnswerLoading(true)
      const aiGenerated: CreateCounselingPlanPhaseGoalDTO = await dispatch(
        createCounselingPlanPhaseGoalAIGenerated({
          counselingPlanPhaseId: counselingPlanPhaseId,
          language: getCurrentLanguage(),
        })
      ).unwrap()

      setFormValues({
        ...aiGenerated,
        counselingPlanPhaseId: counselingPlanPhaseId,
      })
      setOpen(true)
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    } finally {
      setIsAIAnswerLoading(false)
    }
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
        <Button sx={commonButtonStyles} onClick={() => setOpen(true)}>
          {t('counseling_plan.add_a_goal')}
        </Button>
      ) : (
        <form
          style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}
          onSubmit={handleSubmit}
        >
          <TextField
            required
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
            <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '150px' }}>
              {t('counseling_plan.create_goal')}
            </Button>
            <Button
              sx={{ ...successButtonStyles, minWidth: '340px' }}
              onClick={handleGenrateAI}
              loading={isAIAnswerLoading}
            >
              {t('counseling_plan.make_ai_generated_suggestion_for_goal')}
            </Button>
            <Button sx={{ ...cancelButtonStyles, minWidth: '100px' }} onClick={handleCancel}>
              {t('counseling_plan.cancel')}
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}

export default CreateCounselingPlanPhaseGoal
