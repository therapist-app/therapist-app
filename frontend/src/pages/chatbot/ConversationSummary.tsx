import { Alert, Box, CircularProgress, Typography } from '@mui/material'
import dayjs from 'dayjs'
import { ReactElement, useEffect } from 'react'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { fetchConversationSummary, PRIVATE_MESSAGE } from '../../store/conversationSlice'
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

  const renderContent = () => {
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
        return <Alert severity='info'>{PRIVATE_MESSAGE}</Alert>
      }
      return <Alert severity='error'>{error}</Alert>
    }

    return <Typography whiteSpace='pre-line'>{summary}</Typography>
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
