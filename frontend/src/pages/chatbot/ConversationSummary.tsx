import { Alert, Box, CircularProgress, Typography } from '@mui/material'
import dayjs from 'dayjs'
import { ReactElement, useEffect } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { fetchConversationSummary, PRIVATE_MESSAGE } from '../../store/conversationSlice'
import { useAppDispatch, useAppSelector } from '../../utils/hooks'

const ConversationSummary = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams<{ patientId: string }>()
  const { notifyError } = useNotify()
  const dispatch = useAppDispatch()

  const summary = useAppSelector((s) => (patientId ? s.conversation.byPatient[patientId] : ''))
  const status = useAppSelector((s) => s.conversation.status)
  const error = useAppSelector((s) => s.conversation.error)

  useEffect(() => {
    if (!patientId) {
      return
    }

    const load = async (): Promise<void> => {
      try {
        await dispatch(
          fetchConversationSummary({
            patientId: patientId,
            start: dayjs().subtract(7, 'day').toISOString(),
            end: dayjs().toISOString(),
          })
        ).unwrap()
      } catch (err) {
        notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      }
    }

    void load()
  }, [dispatch, patientId, notifyError])

  const renderContent = (): ReactElement => {
    if (status === 'loading' || status === 'idle') {
      return (
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mt: 2 }}>
          <CircularProgress size={24} />
          <Typography>{t('chatbot.loading_conversation_summary')}</Typography>
        </Box>
      )
    }

    if (status === 'failed') {
    if (error === PRIVATE_MESSAGE) {
      return (
        <Typography sx={{ mt: 2 }} whiteSpace="pre-line">
          {t('chatbot.conversation_private')}
        </Typography>
      );
    }
    return <Alert severity="error">{error}</Alert>;
  }

  if (!summary?.trim()) {
    return (
      <Typography sx={{ mt: 2 }} whiteSpace="pre-line">
        {t('chatbot.no_conversations_exist')}
      </Typography>
    );
  }

  return (
    <Typography sx={{ mt: 2 }} whiteSpace="pre-line">
      {summary}
    </Typography>
  );
};

  return (
    <Layout>
      <Box sx={{ maxWidth: 800 }}>
        <Typography variant='h4' gutterBottom>
          {t('chatbot.conversations_from')}&nbsp;
          {dayjs().subtract(7, 'day').format('DD MMM YYYY')}–{dayjs().format('DD MMM YYYY')}
        </Typography>

        {renderContent()}
      </Box>
    </Layout>
  )
}

export default ConversationSummary
