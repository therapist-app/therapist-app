import { Card, CardContent, CircularProgress, Typography } from '@mui/material';
import { ReactElement, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Layout from '../../generalComponents/Layout';
import { getConversationSummaries } from '../../utils/api';

interface ConversationNameDTO {
  id: string;
  name: string | null;
  createdAt: string;
}

const ConversationSummary = (): ReactElement => {
  const { patientId = '' } = useParams<{ patientId: string }>();
  const [data, setData] = useState<ConversationNameDTO[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const res = await getConversationSummaries(patientId);
        setData(res.data as ConversationNameDTO[]);
      } catch (err) {
        console.error('Failed to load conversations', err);
      } finally {
        setLoading(false);
      }
    })();
  }, [patientId]);

  return (
    <Layout>
      <Typography variant='h2' sx={{ mb: 3 }}>
        Conversation Summary
      </Typography>

      {loading ? (
        <CircularProgress />
      ) : (
        data.map((c) => (
          <Card key={c.id} sx={{ mb: 2, maxWidth: 600 }}>
            <CardContent>
              <Typography variant='h6'>{c.name || 'Unnamed chat'}</Typography>
              <Typography variant='caption' color='textSecondary'>
                {new Date(c.createdAt).toLocaleString()}
              </Typography>
            </CardContent>
          </Card>
        ))
      )}
    </Layout>
  );
};

export default ConversationSummary;
