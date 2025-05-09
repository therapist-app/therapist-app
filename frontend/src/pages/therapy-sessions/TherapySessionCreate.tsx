import { Button } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { createTherapySession } from '../../store/therapySessionSlice'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const TherapySessionCreate = (): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const [therapySessionToCreate, setTherapySessionToCreate] = useState<{
    sessionStart: Date | null
    sessionEnd: Date | null
    patientId: string
  }>({
    sessionStart: null,
    sessionEnd: null,
    patientId: patientId ?? '',
  })

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    if (!therapySessionToCreate.sessionStart || !therapySessionToCreate.sessionEnd) {
      console.error('Start and end dates are required')
      return
    }

    try {
      const createdTherapySession = await dispatch(
        createTherapySession({
          patientId: therapySessionToCreate.patientId,
          sessionStart: therapySessionToCreate.sessionStart.toISOString(),
          sessionEnd: therapySessionToCreate.sessionEnd.toISOString(),
        })
      ).unwrap()
      navigate(
        getPathFromPage(PAGES.THERAPY_SESSIONS_DETAILS_PAGE, {
          patientId: patientId ?? '',
          therapySessionId: createdTherapySession.id ?? '',
        })
      )
    } catch (err) {
      console.error('Creating therapy sessions error:', err)
    }
  }

  return (
    <Layout>
      <form style={{ maxWidth: '500px' }} onSubmit={handleSubmit}>
        <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
          <DateTimePicker
            label='Session Start'
            value={therapySessionToCreate.sessionStart}
            onChange={(newValue: Date | null) => {
              setTherapySessionToCreate({
                ...therapySessionToCreate,
                sessionStart: newValue,
              })
            }}
            sx={{ mt: 2, width: '100%' }}
          />

          <DateTimePicker
            label='Session End'
            value={therapySessionToCreate.sessionEnd}
            onChange={(newValue: Date | null) => {
              setTherapySessionToCreate({
                ...therapySessionToCreate,
                sessionEnd: newValue,
              })
            }}
            sx={{ mt: 2, width: '100%' }}
          />
        </LocalizationProvider>

        <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
          Create Session
        </Button>
      </form>
    </Layout>
  )
}

export default TherapySessionCreate
