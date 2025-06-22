import { Button, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

import { CreateMeetingDTO } from '../../api'
import Layout from '../../generalComponents/Layout'
import { createMeeting } from '../../store/meetingSlice'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

type MeetingFormData = Omit<CreateMeetingDTO, 'meetingStart' | 'meetingEnd'> & {
  meetingStart: Date | null
  durationInMinutes: number
}

const MeetingCreate = (): ReactElement => {
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const [meetingFormData, setMeetingFormData] = useState<MeetingFormData>({
    meetingStart: new Date(),
    durationInMinutes: 60,
    patientId: patientId ?? '',
  })

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    if (!meetingFormData.meetingStart) {
      console.error('Start date is required')
      return
    }

    try {
      const createMeetingDTO: CreateMeetingDTO = {
        ...meetingFormData,
        meetingStart: meetingFormData.meetingStart.toISOString(),
        meetingEnd: new Date(
          meetingFormData.meetingStart.getTime() + meetingFormData.durationInMinutes * 60000
        ).toISOString(),
      }
      const createdMeeting = await dispatch(createMeeting(createMeetingDTO)).unwrap()
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
            value={meetingFormData.meetingStart}
            onChange={(newValue: Date | null) => {
              setMeetingFormData({
                ...meetingFormData,
                meetingStart: newValue,
              })
            }}
            sx={{ mt: 2, width: '100%' }}
          />

          <TextField
            label='Duration in Minutes'
            type='number'
            value={meetingFormData.durationInMinutes}
            onChange={(e) =>
              setMeetingFormData({
                ...meetingFormData,
                durationInMinutes: Number(e.target.value),
              })
            }
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
