import { Button, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { t } from 'i18next'
import { useState } from 'react'

import { MeetingOutputDTO, UpdateMeetingDTO, UpdateMeetingDTOMeetingStatusEnum } from '../../api'
import { updateMeeting } from '../../store/meetingSlice'
import { useAppDispatch } from '../../utils/hooks'

interface MeetingEditingProps {
  save(): void
  cancel(): void
  meeting: MeetingOutputDTO | null
}

interface FormValues {
  meetingStart: Date | null
  meetingEnd: Date | null
  location: string
  meetingStatus: UpdateMeetingDTOMeetingStatusEnum
}

const MeetingEditing: React.FC<MeetingEditingProps> = (props) => {
  const dispatch = useAppDispatch()

  const [meetingFormData, setMeetingFormData] = useState<FormValues>({
    meetingStart: new Date(props.meeting?.meetingStart ?? ''),
    meetingEnd: new Date(props.meeting?.meetingEnd ?? ''),
    location: props.meeting?.location ?? '',
    meetingStatus: props.meeting?.meetingStatus ?? UpdateMeetingDTOMeetingStatusEnum.Confirmed,
  })

  if (props.meeting === null) {
    return <></>
  }

  const handleSubmit = async (): Promise<void> => {
    const dto: UpdateMeetingDTO = {
      id: props.meeting?.id ?? '',
      meetingStart: meetingFormData.meetingStart?.toISOString(),
      meetingEnd: meetingFormData.meetingEnd?.toISOString(),
      location: meetingFormData.location,
      meetingStatus: meetingFormData.meetingStatus,
    }
    await dispatch(updateMeeting(dto))
    props.save()
  }

  return (
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

        <DateTimePicker
          label={t('meetings.meeting_end')}
          value={meetingFormData.meetingEnd}
          onChange={(newValue: Date | null) => {
            setMeetingFormData({
              ...meetingFormData,
              meetingEnd: newValue,
            })
          }}
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

        <Select
          sx={{ mt: 2, width: '100%' }}
          value={meetingFormData.meetingStatus}
          onChange={(e) =>
            setMeetingFormData({
              ...meetingFormData,
              meetingStatus: e.target.value as UpdateMeetingDTOMeetingStatusEnum,
            })
          }
        >
          {Object.values(UpdateMeetingDTOMeetingStatusEnum).map((s) => (
            <MenuItem key={s} value={s}>
              {t(`meetings.${s}`)}
            </MenuItem>
          ))}
        </Select>
      </LocalizationProvider>

      <div style={{ display: 'flex', gap: '20px' }}>
        <Button type='submit' variant='contained' color='success' fullWidth sx={{ mt: 2 }}>
          {t('meetings.save')}
        </Button>
        <Button onClick={props.cancel} variant='contained' color='error' fullWidth sx={{ mt: 2 }}>
          {t('meetings.cancel')}
        </Button>
      </div>
    </form>
  )
}

export default MeetingEditing
