import { Alert, Box, CircularProgress, Typography } from '@mui/material'
import dayjs from 'dayjs'
import { ReactElement, useEffect } from 'react'
import { useParams } from 'react-router-dom'

import { fetchConversationSummary } from '../../store/conversationSlice'
import { useAppDispatch, useAppSelector } from '../../utils/hooks'

const ConversationSummary = (): ReactElement => {
  const { patientId } = useParams<{ patientId: string }>()
  const dispatch = useAppDispatch()

  const summary = useAppSelector((s) => (patientId ? s.conversation.byPatient[patientId] : ''))
  const status = useAppSelector((s) => s.conversation.status)
  const error = useAppSelector((s) => s.conversation.error)

  useEffect(() => {
    if (!patientId) {
      return
    }
    dispatch(
      fetchConversationSummary({
        patientId: patientId,
        start: dayjs().subtract(7, 'day').toISOString(),
        end: dayjs().toISOString(),
      })
    )
  }, [dispatch, patientId])

  if (status === 'loading') {
    return <CircularProgress />
  }
  if (status === 'failed') {
    return <Alert severity='error'>{error}</Alert>
  }

  return (
    <Box sx={{ maxWidth: 800 }}>
      <Typography variant='h2' gutterBottom>
        Conversation Summary
      </Typography>
      <Typography whiteSpace='pre-line'>{summary}</Typography>
    </Box>
  )
}

export default ConversationSummary
