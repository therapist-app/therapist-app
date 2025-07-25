import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material'
import { format } from 'date-fns'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import { useNotify } from '../../hooks/useNotify'
import { PsychologicalTestOutputDTO } from '../../store/psychologicalTest'
import { patientTestApi } from '../../utils/api'

export const GAD7TestDetail = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams()
  const { notifyError } = useNotify()
  const [gad7Tests, setGad7Tests] = useState<PsychologicalTestOutputDTO[]>([])

  useEffect(() => {
    const fetchTests = async (): Promise<void> => {
      try {
        const response = await patientTestApi.getTestsForPatient(patientId!)
        const normalized: PsychologicalTestOutputDTO[] = response.data.map((apiDto) => ({
          id: apiDto.id!,
          name: apiDto.name || '',
          description: apiDto.description || '',
          patientId: apiDto.patientId || '',
          completedAt: apiDto.completedAt || '',
          questions:
            apiDto.questions?.map((q) => ({
              question: q.question || '',
              score: q.score || 0,
            })) || [],
        }))
        setGad7Tests(normalized)
      } catch {
        notifyError('Failed to fetch GAD7 tests')
      }
    }

    if (patientId) {
      fetchTests()
    }
  }, [patientId, notifyError])

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '50px' }}>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
        <div
          style={{
            display: 'flex',
            gap: '30px',
            alignItems: 'center',
          }}
        >
          <Typography variant='h2'>{t('gad7test.gad7tests')}</Typography>
        </div>
        {gad7Tests.length > 0 ? (
          <>
            <TableContainer component={Paper}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>{t('gad7test.date')}</TableCell>
                    {[1, 2, 3, 4, 5, 6, 7].map((num) => (
                      <TableCell key={num} align='right'>
                        Q{num}
                        <Typography variant='caption' display='block'>
                          {t(`gad7test.question${num}`)}
                        </Typography>
                      </TableCell>
                    ))}
                    <TableCell align='right'>{t('gad7test.total_score')}</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {gad7Tests.map((test, index) => (
                    <TableRow key={index}>
                      <TableCell>{format(new Date(test.completedAt), 'dd.MM.yyyy')}</TableCell>
                      {test.questions.map((question, qIndex) => (
                        <TableCell key={qIndex} align='right'>
                          {question.score}
                        </TableCell>
                      ))}
                      <TableCell align='right'>
                        {test.questions.reduce((acc, curr) => acc + curr.score, 0)}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </>
        ) : (
          <Typography>{t('gad7test.no_tests_yet')}</Typography>
        )}
      </div>
    </div>
  )
}

export default GAD7TestDetail
