import { Button, TextField } from '@mui/material';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { de } from 'date-fns/locale';
import { ReactElement, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useParams } from 'react-router-dom';

import { CreateMeetingDTO } from '../../api';
import Layout from '../../generalComponents/Layout';
import { useNotify } from '../../hooks/useNotify';
import { createMeeting } from '../../store/meetingSlice';
import { commonButtonStyles } from '../../styles/buttonStyles';
import { useAppDispatch } from '../../utils/hooks';
import { getPathFromPage, PAGES } from '../../utils/routes';

type MeetingFormData = Omit<CreateMeetingDTO, 'meetingStart' | 'meetingEnd'> & {
  meetingStart: Date | null;
  durationInMinutes: number;
  location: string;
};

const MeetingCreate = (): ReactElement => {
  const { t } = useTranslation();
  const { patientId } = useParams();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { notifyError, notifySuccess } = useNotify();

  const [meetingFormData, setMeetingFormData] = useState<MeetingFormData>({
    meetingStart: new Date(),
    durationInMinutes: 60,
    patientId: patientId ?? '',
    location: '',
  });

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault();

    if (!meetingFormData.meetingStart) {
      notifyError(t('meetings.error_start_date_required'));
      return;
    }
    if (meetingFormData.durationInMinutes < 1) {
      notifyError(t('meetings.duration_must_be_positive'));
      return;
    }

    try {
      const createMeetingDTO: CreateMeetingDTO = {
        ...meetingFormData,
        meetingStart: meetingFormData.meetingStart.toISOString(),
        meetingEnd: new Date(
          meetingFormData.meetingStart.getTime() +
            meetingFormData.durationInMinutes * 60_000
        ).toISOString(),
        location: meetingFormData.location,
      };

      const createdMeeting = await dispatch(
        createMeeting(createMeetingDTO)
      ).unwrap();

      notifySuccess(t('meetings.meeting_created_successfully'));
      navigate(
        getPathFromPage(PAGES.MEETINGS_DETAILS_PAGE, {
          patientId: patientId ?? '',
          meetingId: createdMeeting.id ?? '',
        })
      );
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred');
    }
  };

  return (
    <Layout>
      <form style={{ maxWidth: 500 }} onSubmit={handleSubmit}>
        <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
          <DateTimePicker
            label={t('meetings.meeting_start')}
            value={meetingFormData.meetingStart}
            onChange={(newValue) =>
              setMeetingFormData({ ...meetingFormData, meetingStart: newValue })
            }
            sx={{ mt: 2, width: '100%' }}
          />

          <TextField
            label={t('meetings.duration_in_minutes')}
            type="number"
            required
            value={meetingFormData.durationInMinutes}
            inputProps={{
              pattern: '[0-9]*',
              inputMode: 'numeric',
              min: 1,
              step: 1,
            }}
            onChange={(e) => {
              const v = Number(e.target.value);
              if (!Number.isNaN(v) && v >= 1) {
                setMeetingFormData({ ...meetingFormData, durationInMinutes: v });
              }
            }}
            sx={{ mt: 2, width: '100%' }}
          />

          <TextField
            label={t('meetings.location')}
            multiline
            value={meetingFormData.location}
            onChange={(e) =>
              setMeetingFormData({ ...meetingFormData, location: e.target.value })
            }
            sx={{ mt: 2, width: '100%' }}
          />
        </LocalizationProvider>

        <Button type="submit" sx={{ ...commonButtonStyles, minWidth: 200, mt: 2 }}>
          {t('meetings.create_meeting')}
        </Button>
      </form>
    </Layout>
  );
};

export default MeetingCreate;
