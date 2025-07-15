import { Button, TextField } from '@mui/material'
import { ReactElement, useState } from 'react'

import { CreateCounselingPlanPhaseDTO } from '../../../api'
import {
  createCounselingPlanPhase,
  createCounselingPlanPhaseAIGenerated,
} from '../../../store/counselingPlanSlice'
import { useAppDispatch } from '../../../utils/hooks'
import { getCurrentLanguage } from '../../../utils/languageUtil'

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
          Add a phase
        </Button>
      ) : (
        <form
          onSubmit={handleSubmit}
          style={{ display: 'flex', flexDirection: 'column', gap: '10px', width: '600px' }}
        >
          <TextField
            fullWidth
            label='Phase Name'
            value={formValues.phaseName}
            onChange={(e) => setFormValues({ ...formValues, phaseName: e.target.value })}
          />
          <TextField
            label='Duration in Weeks'
            value={formValues.durationInWeeks}
            type='number'
            onChange={(e) =>
              setFormValues({ ...formValues, durationInWeeks: parseInt(e.target.value) })
            }
          />
          <div style={{ display: 'flex', gap: '10px' }}>
            <Button variant='contained' type='submit'>
              Create phase
            </Button>
            <Button variant='contained' color='success' onClick={handleAIGeneration}>
              Make AI generated suggestion
            </Button>
            <Button variant='outlined' color='error' onClick={handleCancel}>
              Cancel
            </Button>
          </div>
        </form>
      )}
    </>
  )
}

export default CreateCounselingPlanePhase
