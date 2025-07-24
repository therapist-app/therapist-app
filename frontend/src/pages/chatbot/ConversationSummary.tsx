import { Alert, Box, CircularProgress, Typography } from '@mui/material'
import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import dayjs from 'dayjs'
import { ReactElement, useEffect } from 'react'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { fetchConversationSummary, PRIVATE_MESSAGE } from '../../store/conversationSlice'
import { showError } from '../../store/errorSlice'
import { handleError } from '../../utils/handleError'
import { useAppDispatch, useAppSelector } from '../../utils/hooks'

const ConversationSummary = (): ReactElement => {
  const { patientId } = useParams<{ patientId: string }>()
  const dispatch = useAppDispatch()

  const summary = useAppSelector((s) => (patientId ? s.conversation.byPatient[patientId] : ''))
  const status = useAppSelector((s) => s.conversation.status)
  const error = useAppSelector((s) => s.conversation.error)

  const showMessage = (message: string, severity: AlertColor = 'error') =>
    dispatch(showError({ message, severity }))

  useEffect(() => {
    if (!patientId) {
      return
    }
    ;(async () => {
      try {
        await dispatch(
          fetchConversationSummary({
            patientId: patientId,
            start: dayjs().subtract(7, 'day').toISOString(),
            end: dayjs().toISOString(),
          })
        ).unwrap()
      } catch (err) {
        const msg = handleError(err as AxiosError)
        showMessage(msg, 'error')
      }
    })()
  }, [dispatch, patientId])

  const renderContent = (): ReactElement => {
    if (status === 'loading' || status === 'idle') {
      return (
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mt: 2 }}>
          <CircularProgress size={24} />
          <Typography>Loading conversation summary…</Typography>
        </Box>
      )
    }

    if (status === 'failed') {
      if (error === PRIVATE_MESSAGE) {
        return (
          <Typography sx={{ mt: 2 }} whiteSpace='pre-line'>
            {PRIVATE_MESSAGE}
          </Typography>
        )
      }
      return <Alert severity='error'>{error}</Alert>
    }

    return (
      <Typography sx={{ mt: 2 }} whiteSpace='pre-line'>
        {summary}
      </Typography>
    )
  }

  return (
    <Layout>
      <Box sx={{ maxWidth: 800 }}>
        <Typography variant='h4' gutterBottom>
          Conversations from&nbsp;
          {dayjs().subtract(7, 'day').format('DD MMM YYYY')}–{dayjs().format('DD MMM YYYY')}
        </Typography>

        {renderContent()}
      </Box>
    </Layout>
  )
}

export default ConversationSummary
