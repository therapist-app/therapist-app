import { Button, TextField } from '@mui/material'
import { AxiosError } from 'axios'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { CreateCounselingPlanPhaseDTO } from '../../../api'
import {
  createCounselingPlanPhase,
  createCounselingPlanPhaseAIGenerated,
} from '../../../store/counselingPlanSlice'
import {
  cancelButtonStyles,
  commonButtonStyles,
  successButtonStyles,
} from '../../../styles/buttonStyles'
import { handleError } from '../../../utils/handleError'
import { useAppDispatch } from '../../../utils/hooks'
import { getCurrentLanguage } from '../../../utils/languageUtil'
import { useNotify } from '../../../hooks/useNotify'

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
  const { notifyError, notifySuccess } = useNotify()

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()
    try {
      const dto: CreateCounselingPlanPhaseDTO = {
        counselingPlanId: counselingPlanId,
        phaseName: formValues.phaseName,
        durationInWeeks: formValues.durationInWeeks,
      }
      await dispatch(createCounselingPlanPhase(dto)).unwrap()
      notifySuccess(t('counseling_plan.phase_created_success'))
      onSuccess()
      setOpen(false)
      setFormValues(initialFormValues)
    } catch (error) {
      const msg = handleError(error as AxiosError)
      notifyError(msg)
    }
  }

  const handleAIGeneration = async (): Promise<void> => {
    try {
      const aiDto = await dispatch(
        createCounselingPlanPhaseAIGenerated({
          counselingPlanId: counselingPlanId,
          language: getCurrentLanguage(),
        })
      ).unwrap()

      setFormValues({
        phaseName: aiDto.phaseName ?? '',
        durationInWeeks: aiDto.durationInWeeks ?? 2,
      })
      setOpen(true)
    } catch (error) {
      const msg = handleError(error as AxiosError)
      notifyError(msg)
    }
  }

  const handleCancel = (): void => {
    setOpen(false)
    setFormValues(initialFormValues)
  }

  return (
    <>
      {!open ? (
        <Button sx={{ ...commonButtonStyles, minWidth: '140px' }} onClick={() => setOpen(true)}>
          {t('counseling_plan.add_a_phase')}
        </Button>
      ) : (
        <form
          onSubmit={handleSubmit}
          style={{ display: 'flex', flexDirection: 'column', gap: '10px', width: '600px' }}
        >
          <TextField
            required
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
            <Button sx={{ ...commonButtonStyles, minWidth: '150px' }} type='submit'>
              {t('counseling_plan.create_phase')}
            </Button>
            <Button sx={{ ...successButtonStyles, minWidth: '340px' }} onClick={handleAIGeneration}>
              {t('counseling_plan.make_ai_generated_suggestion_for_phase')}
            </Button>
            <Button sx={{ ...cancelButtonStyles, minWidth: '100px' }} onClick={handleCancel}>
              {t('counseling_plan.cancel')}
            </Button>
          </div>
        </form>
      )}
    </>
  )
}

export default CreateCounselingPlanePhase
