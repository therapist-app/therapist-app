import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { useEffect } from 'react'
import { getTherapySession } from '../../store/therapySessionSlice'
import { useAppDispatch } from '../../utils/hooks'
import { format } from 'date-fns'
import { de } from 'date-fns/locale'

const TherapySessionDetail = () => {
  const { therapySessionId } = useParams()
  const dispatch = useAppDispatch()
  const selectedTherapySession = useSelector(
    (state: RootState) => state.therapySession.selectedTherapySession
  )

  useEffect(() => {
    dispatch(getTherapySession(therapySessionId ?? ''))
  }, [dispatch, therapySessionId])

  return (
    <Layout>
      <Typography variant='h4' style={{ marginBottom: '20px' }}>
        Showing the session details of session: {therapySessionId}"
      </Typography>
      <Typography style={{ marginTop: '50px' }}>
        Session start:{' '}
        {selectedTherapySession?.sessionStart
          ? format(new Date(selectedTherapySession.sessionStart), 'dd.MM.yyyy HH:mm', {
              locale: de,
            })
          : '-'}
      </Typography>
      <Typography>
        Session end:{' '}
        {selectedTherapySession?.sessionEnd
          ? format(new Date(selectedTherapySession.sessionEnd), 'dd.MM.yyyy HH:mm', {
              locale: de,
            })
          : '-'}
      </Typography>
    </Layout>
  )
}

export default TherapySessionDetail
