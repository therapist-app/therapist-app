import { Button, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'

import { CreateCounselingPlanPhaseDTO } from '../../../api'
import {
  createCounselingPlanPhase,
  createCounselingPlanPhaseAIGenerated,
} from '../../../store/counselingPlanSlice'
import { useAppDispatch } from '../../../utils/hooks'

interface CreateCounselingPlanePhaseProps {
  counselingPlanId: string
  onSuccess: () => void
}

interface FormValues {
  phaseName: string
  startDate: Date | null
  durationInWeeks: number
}

const initialFormValues: FormValues = {
  phaseName: '',
  startDate: new Date(),
  durationInWeeks: 3,
}

const CreateCounselingPlanePhase = ({
  counselingPlanId,
  onSuccess,
}: CreateCounselingPlanePhaseProps): ReactElement => {
  const [formValues, setFormValues] = useState<FormValues>(initialFormValues)

  const [open, setOpen] = useState(false)

  const dispatch = useAppDispatch()

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault()
    const startDate = formValues.startDate || new Date()
    const createCounselingPlanPhaseDTO: CreateCounselingPlanPhaseDTO = {
      counselingPlanId: counselingPlanId,
      phaseName: formValues.phaseName,
      startDate: startDate.toISOString(),
      endDate: new Date(
        startDate.getTime() + formValues.durationInWeeks * 7 * 24 * 60 * 60 * 1000
      ).toISOString(),
    }
    await dispatch(createCounselingPlanPhase(createCounselingPlanPhaseDTO))
    onSuccess()
    setOpen(false)
    setFormValues(initialFormValues)
  }

  const handleAIGeneration = async (): Promise<void> => {
    const createCounselingPlanPhaseDTO = await dispatch(
      createCounselingPlanPhaseAIGenerated(counselingPlanId)
    ).unwrap()
    const newFormValue: FormValues = {
      phaseName: createCounselingPlanPhaseDTO.phaseName ?? '',
      startDate: new Date(createCounselingPlanPhaseDTO.startDate ?? ''),
      durationInWeeks: 2,
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
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          <Button variant='contained' onClick={() => setOpen(true)}>
            Add a phase
          </Button>
          <Button variant='contained' color='success' onClick={handleAIGeneration}>
            Add phase with AI
          </Button>
        </div>
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
          <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
            <DateTimePicker
              label='Start Date'
              value={formValues.startDate}
              onChange={(newValue: Date | null) => {
                setFormValues({
                  ...formValues,
                  startDate: newValue,
                })
              }}
              sx={{ width: '100%' }}
            />
          </LocalizationProvider>
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
