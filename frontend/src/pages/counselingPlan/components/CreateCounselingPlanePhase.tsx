import { Button, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'

import { CreateCounselingPlanPhaseDTO } from '../../../api'
import {
  createCounselingPlanPhase,
  createCounselingPlanPhaseAIGenerated,
} from '../../../store/counselingPlanSlice'
import { useAppDispatch } from '../../../utils/hooks'
import { getCurrentLanguage } from '../../../utils/languageUtil'
import { useTranslation } from 'react-i18next'

interface CreateCounselingPlanePhaseProps {
  counselingPlanId: string
  onSuccess: () => void
}

const initialFormValues: CreateCounselingPlanPhaseDTO = {
  phaseName: '',

  durationInWeeks: 3,
}

const CreateCounselingPlanePhase = ({
  counselingPlanId,
  onSuccess,
}: CreateCounselingPlanePhaseProps): ReactElement => {
  const [formValues, setFormValues] = useState<CreateCounselingPlanPhaseDTO>(initialFormValues)
  const [open, setOpen] = useState(false)

  const dispatch = useAppDispatch()
  const { t } = useTranslation()

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()

    const createCounselingPlanPhaseDTO: CreateCounselingPlanPhaseDTO = {
      counselingPlanId: counselingPlanId,
      phaseName: formValues.phaseName,
      durationInWeeks: formValues.durationInWeeks,
    }
    await dispatch(createCounselingPlanPhase(createCounselingPlanPhaseDTO))
    onSuccess()
    setOpen(false)
    setFormValues(initialFormValues)
  }

  const handleAIGeneration = async (): Promise<void> => {
    const createCounselingPlanPhaseDTO = await dispatch(
      createCounselingPlanPhaseAIGenerated({
        counselingPlanId: counselingPlanId,
        language: getCurrentLanguage(),
      })
    ).unwrap()
    const newFormValue: CreateCounselingPlanPhaseDTO = {
      phaseName: createCounselingPlanPhaseDTO.phaseName ?? '',
      durationInWeeks: createCounselingPlanPhaseDTO.durationInWeeks ?? 2,
    }

    setFormValues(newFormValue)
    setOpen(true)
  }

  const handleCancel = (): void => {
    setOpen(false)
    setFormValues(initialFormValues)
  }

  return (
    <>
      {!open ? (
        <Button variant='contained' onClick={() => setOpen(true)}>
          {t('counseling_plan.add_a_phase')}
        </Button>
      ) : (
        <form
          onSubmit={handleSubmit}
          style={{ display: 'flex', flexDirection: 'column', gap: '10px', width: '600px' }}
        >
          <TextField
            fullWidth
            label={t('counseling_plan.phase_name')}
            value={formValues.phaseName}
            onChange={(e) => setFormValues({ ...formValues, phaseName: e.target.value })}
          />
          <TextField
            label={t('counseling_plan.duration_in_weeks')}
            value={formValues.durationInWeeks}
            type='number'
            onChange={(e) =>
              setFormValues({ ...formValues, durationInWeeks: parseInt(e.target.value) })
            }
          />
          <div style={{ display: 'flex', gap: '10px' }}>
            <Button variant='contained' type='submit'>
              {t('counseling_plan.create_phase')}
            </Button>
            <Button variant='contained' color='success' onClick={handleAIGeneration}>
              {t('counseling_plan.make_ai_generated_suggestion_for_phase')}
            </Button>
            <Button variant='outlined' color='error' onClick={handleCancel}>
              {t('counseling_plan.cancel')}
            </Button>
          </div>
        </form>
      )}
    </>
  )
}

export default CreateCounselingPlanePhase
