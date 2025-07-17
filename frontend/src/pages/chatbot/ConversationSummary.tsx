import {
  Box,
  CircularProgress,
  Typography,
  Alert,
} from '@mui/material'
import dayjs from 'dayjs'
import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useAppDispatch, useAppSelector } from '../../utils/hooks'
import { fetchConversationSummary } from '../../store/conversationSlice'

const ConversationSummary = () => {
  const { patientId } = useParams<{ patientId: string }>()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const dispatch = useAppDispatch()
const summary = useAppSelector(
  (s) => (patientId ? s.conversation.byPatient[patientId] : '')
)


  useEffect(() => {
  if (!patientId) return
  dispatch(
    fetchConversationSummary({
      patientId,
      start: dayjs().subtract(7, 'day').toISOString(),
      end: dayjs().toISOString(),
    })
  )
}, [dispatch, patientId])

  if (loading) return <CircularProgress />
  if (error) return <Alert severity='error'>{error}</Alert>

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
