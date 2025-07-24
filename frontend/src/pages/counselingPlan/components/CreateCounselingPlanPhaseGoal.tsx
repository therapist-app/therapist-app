import { Button, TextField } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { CreateCounselingPlanPhaseGoalDTO } from '../../../api'
import {
  createCounselingPlanPhaseGoal,
  createCounselingPlanPhaseGoalAIGenerated,
} from '../../../store/counselingPlanSlice'
import { showError } from '../../../store/errorSlice'
import {
  cancelButtonStyles,
  commonButtonStyles,
  successButtonStyles,
} from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
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
  const dispatch = useAppDispatch()

  const [formValues, setFormValues] = useState<CreateCounselingPlanPhaseGoalDTO>({
    counselingPlanPhaseId: counselingPlanPhaseId,
    goalName: '',
    goalDescription: '',
  })

  const showMessage = (message: string, severity: AlertColor = 'error') => {
    dispatch(showError({ message, severity }))
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()
    try {
      await dispatch(createCounselingPlanPhaseGoal(formValues)).unwrap()
      showMessage(t('counseling_plan.goal_created_success'), 'success')
      onSuccess()
      setOpen(false)
      setFormValues({
        counselingPlanPhaseId,
        goalName: '',
        goalDescription: '',
      })
    } catch (error) {
      const msg = handleError(error as AxiosError)
      showMessage(msg, 'error')
    }
  }

  const handleGenrateAI = async (): Promise<void> => {
    try {
      const aiGenerated: CreateCounselingPlanPhaseGoalDTO = await dispatch(
        createCounselingPlanPhaseGoalAIGenerated({
          counselingPlanPhaseId,
          language: getCurrentLanguage(),
        })
      ).unwrap()

      setFormValues({
        ...aiGenerated,
        counselingPlanPhaseId,
      })
      setOpen(true)
    } catch (error) {
      const msg = handleError(error as AxiosError)
      showMessage(msg, 'error')
    }
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormValues({ ...formValues, [e.target.name]: e.target.value })
  }

  const handleCancel = (): void => {
    setOpen(false)
    setFormValues({
      counselingPlanPhaseId,
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
            <Button sx={{ ...successButtonStyles, minWidth: '340px' }} onClick={handleGenrateAI}>
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
