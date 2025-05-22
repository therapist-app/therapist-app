import { Button } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { createMeeting } from '../../store/meetingSlice'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const MeetingCreate = (): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const [meetingToCreate, setMeetingToCreate] = useState<{
    meetingStart: Date | null
    meetingEnd: Date | null
    patientId: string
  }>({
    meetingStart: null,
    meetingEnd: null,
    patientId: patientId ?? '',
  })

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    if (!meetingToCreate.meetingStart || !meetingToCreate.meetingEnd) {
      console.error('Start and end dates are required')
      return
    }

    try {
      const createdMeeting = await dispatch(
        createMeeting({
          patientId: meetingToCreate.patientId,
          meetingStart: meetingToCreate.meetingStart.toISOString(),
          meetingEnd: meetingToCreate.meetingEnd.toISOString(),
        })
      ).unwrap()
      navigate(
        getPathFromPage(PAGES.MEETINGS_DETAILS_PAGE, {
          patientId: patientId ?? '',
          meetingId: createdMeeting.id ?? '',
        })
      )
    } catch (err) {
      console.error('Creating meetings error:', err)
    }
  }

  return (
    <Layout>
      <form style={{ maxWidth: '500px' }} onSubmit={handleSubmit}>
        <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
          <DateTimePicker
            label='Meeting Start'
            value={meetingToCreate.meetingStart}
            onChange={(newValue: Date | null) => {
              setMeetingToCreate({
                ...meetingToCreate,
                meetingStart: newValue,
              })
            }}
            sx={{ mt: 2, width: '100%' }}
          />

          <DateTimePicker
            label='Meeting End'
            value={meetingToCreate.meetingEnd}
            onChange={(newValue: Date | null) => {
              setMeetingToCreate({
                ...meetingToCreate,
                meetingEnd: newValue,
              })
            }}
            sx={{ mt: 2, width: '100%' }}
          />
        </LocalizationProvider>

        <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
          Create Meeting
        </Button>
      </form>
    </Layout>
  )
}

export default MeetingCreate
