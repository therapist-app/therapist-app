import { Box, Card, CardContent, CircularProgress, Typography } from '@mui/material'
import React, { ReactElement, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { conversationApi } from '../../utils/api'

interface ConversationSummaryOutputDTO {
  name: string | null
  start: string
  end: string
  messages: {
    id: string
    excerpt: string
    time: string
  }[]
}

const ConversationSummary = (): ReactElement => {
  const { patientId = '' } = useParams<{ patientId: string }>()
  const [summary, setSummary] = useState<ConversationSummaryOutputDTO | null>(null)
  const [loading, setLoading] = useState(true)

  useEffect((): void => {
  if (!patientId) {
    return
  }

  const now   = new Date()
  const start = new Date(now)
  start.setDate(start.getDate() - 30)

  ;(async () => {
    try {
      const res = await conversationApi.getConversationSummary(
        patientId,
        start.toISOString(),
        now.toISOString()
      )
      setSummary(res.data as ConversationSummaryOutputDTO)
    } catch (err) {
      console.error('Failed to load conversation summary', err)
    } finally {
      setLoading(false)
    }
  })()
}, [patientId])

  return (
    <Layout>
      <Typography variant='h2' sx={{ mb: 3 }}>
        Conversation Summary
      </Typography>

      {loading && <CircularProgress />}

      {!loading && summary && (
        <Box sx={{ maxWidth: 700 }}>
          <Typography variant='h5' sx={{ mb: 2 }}>
            {summary.name || 'Unnamed chatbot'}
          </Typography>

          {summary.messages.map((m) => (
            <Card key={m.id} sx={{ mb: 2 }}>
              <CardContent>
                <Typography variant='caption' color='textSecondary'>
                  {new Date(m.time).toLocaleString()}
                </Typography>
                <Typography variant='body1' sx={{ mt: 1 }}>
                  {m.excerpt}
                </Typography>
              </CardContent>
            </Card>
          ))}

          {!summary.messages.length && <Typography>No messages in the selected period.</Typography>}
        </Box>
      )}
    </Layout>
  )
}

export default ConversationSummary
