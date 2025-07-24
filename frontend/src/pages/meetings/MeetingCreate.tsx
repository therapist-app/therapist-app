import { Button, TextField } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { AxiosError } from 'axios'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate, useParams } from 'react-router-dom'

import { CreateMeetingDTO } from '../../api'
import Layout from '../../generalComponents/Layout'
import { createMeeting } from '../../store/meetingSlice'
import { showError } from '../../store/errorSlice'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

type MeetingFormData = Omit<CreateMeetingDTO, 'meetingStart' | 'meetingEnd'> & {
  meetingStart: Date | null
  durationInMinutes: number
  location: string
}

const MeetingCreate = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const [meetingFormData, setMeetingFormData] = useState<MeetingFormData>({
    meetingStart: new Date(),
    durationInMinutes: 60,
    patientId: patientId ?? '',
    location: '',
  })

  const showMessage = (message: string, severity: AlertColor = 'error') => {
    dispatch(showError({ message, severity }))
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    if (!meetingFormData.meetingStart) {
      showMessage(t('meetings.error_start_date_required'), 'error')
      return
    }

    try {
      const createMeetingDTO: CreateMeetingDTO = {
        ...meetingFormData,
        meetingStart: meetingFormData.meetingStart.toISOString(),
        meetingEnd: new Date(
          meetingFormData.meetingStart.getTime() + meetingFormData.durationInMinutes * 60000
        ).toISOString(),
        location: meetingFormData.location,
      }
      const createdMeeting = await dispatch(createMeeting(createMeetingDTO)).unwrap()
      showMessage(t('meetings.meeting_created_successfully'), 'success')
      navigate(
        getPathFromPage(PAGES.MEETINGS_DETAILS_PAGE, {
          patientId: patientId ?? '',
          meetingId: createdMeeting.id ?? '',
        })
      )
    } catch (err) {
      const msg = handleError(err as AxiosError)
      showMessage(msg, 'error')
    }
  }

  return (
    <Layout>
      <form style={{ maxWidth: '500px' }} onSubmit={handleSubmit}>
        <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
          <DateTimePicker
            label={t('meetings.meeting_start')}
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
            label={t('meetings.duration_in_minutes')}
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

          <TextField
            label={t('meetings.location')}
            name='location'
            value={meetingFormData.location}
            onChange={(e) =>
              setMeetingFormData({
                ...meetingFormData,
                location: e.target.value,
              })
            }
            sx={{ mt: 2, width: '100%' }}
          />
        </LocalizationProvider>

        <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '200px', mt: 2 }}>
          {t('meetings.create_meeting')}
        </Button>
      </form>
    </Layout>
  )
}

export default MeetingCreate
