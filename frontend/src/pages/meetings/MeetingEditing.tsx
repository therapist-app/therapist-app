import { Button, MenuItem, Select, TextField } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { t } from 'i18next'
import { useState } from 'react'

import { MeetingOutputDTO, UpdateMeetingDTO, UpdateMeetingDTOMeetingStatusEnum } from '../../api'
import { useNotify } from '../../hooks/useNotify'
import { updateMeeting } from '../../store/meetingSlice'
import { cancelButtonStyles, successButtonStyles } from '../../styles/buttonStyles'
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

const MeetingEditing: React.FC<MeetingEditingProps> = ({ meeting, save, cancel }) => {
  const dispatch = useAppDispatch()
  const { notifyError, notifySuccess } = useNotify()

  const [meetingFormData, setMeetingFormData] = useState<FormValues>({
    meetingStart: new Date(meeting?.meetingStart ?? ''),
    meetingEnd: new Date(meeting?.meetingEnd ?? ''),
    location: meeting?.location ?? '',
    meetingStatus: meeting?.meetingStatus ?? UpdateMeetingDTOMeetingStatusEnum.Confirmed,
  })

  if (meeting === null) {
    return <></>
  }

  const handleSubmit = async (): Promise<void> => {
    const { meetingStart, meetingEnd } = meetingFormData
    if (meetingStart && meetingEnd && meetingEnd <= meetingStart) {
      notifyError(t('meetings.end_must_be_after_start'))
      return
    }

    try {
      const dto: UpdateMeetingDTO = {
        id: meeting.id,
        meetingStart: meetingStart?.toISOString(),
        meetingEnd: meetingEnd?.toISOString(),
        location: meetingFormData.location,
        meetingStatus: meetingFormData.meetingStatus,
      }
      await dispatch(updateMeeting(dto)).unwrap()
      notifySuccess(t('meetings.meeting_updated_successfully'))
      save()
    } catch (error) {
      notifyError(typeof error === 'string' ? error : 'An unknown error occurred')
    }
  }

  return (
    <form
      style={{ maxWidth: 500 }}
      onSubmit={(e) => {
        e.preventDefault()
        handleSubmit()
      }}
    >
      <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
        <DateTimePicker
          label={t('meetings.meeting_start')}
          value={meetingFormData.meetingStart}
          onChange={(newStart) =>
            setMeetingFormData((prev) => {
              let newEnd = prev.meetingEnd
              if (newStart) {
                if (!prev.meetingEnd || prev.meetingEnd <= newStart) {
                  newEnd = new Date(newStart.getTime() + 60_000)
                }
              }
              return { ...prev, meetingStart: newStart, meetingEnd: newEnd }
            })
          }
          sx={{ mt: 2, width: '100%' }}
        />

        <DateTimePicker
          label={t('meetings.meeting_end')}
          value={meetingFormData.meetingEnd}
          minDateTime={
            meetingFormData.meetingStart
              ? new Date(meetingFormData.meetingStart.getTime() + 60_000)
              : undefined
          }
          onChange={(newEnd) => setMeetingFormData((prev) => ({ ...prev, meetingEnd: newEnd }))}
          sx={{ mt: 2, width: '100%' }}
        />

        <TextField
          label={t('meetings.location')}
          multiline
          value={meetingFormData.location}
          onChange={(e) => setMeetingFormData({ ...meetingFormData, location: e.target.value })}
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

      <div style={{ display: 'flex', gap: 20 }}>
        <Button type='submit' sx={{ ...successButtonStyles, minWidth: 225, mt: 4 }} fullWidth>
          {t('meetings.save')}
        </Button>
        <Button onClick={cancel} sx={{ ...cancelButtonStyles, minWidth: 225, mt: 4 }}>
          {t('meetings.cancel')}
        </Button>
      </div>
    </form>
  )
}

export default MeetingEditing
