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
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useParams } from 'react-router-dom'

import { GAD7TestOutputDTO } from '../../api/models'
import { patientTestApi } from '../../utils/api'

export const GAD7TestDetail = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams()

  const [gad7Tests, setGad7Tests] = useState<GAD7TestOutputDTO[]>([])

  useEffect(() => {
    const fetchTests = async (): Promise<void> => {
      try {
        const response = await patientTestApi.getTestsForPatient(patientId ?? '')
        setGad7Tests(response.data)
      } catch (error) {
        console.error('Error fetching GAD7 tests:', error)
      }
    }
    if (patientId) {
      fetchTests()
    }
  }, [patientId])

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
                    <TableCell>Date</TableCell>
                    <TableCell align='right'>
                      Q1
                      <Typography variant='caption' display='block'>
                        {t('gad7test.question1')}
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q2
                      <Typography variant='caption' display='block'>
                        {t('gad7test.question2')}
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q3
                      <Typography variant='caption' display='block'>
                        {t('gad7test.question3')}
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q4
                      <Typography variant='caption' display='block'>
                        {t('gad7test.question4')}
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q5
                      <Typography variant='caption' display='block'>
                        {t('gad7test.question5')}
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q6
                      <Typography variant='caption' display='block'>
                        {t('gad7test.question6')}
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q7
                      <Typography variant='caption' display='block'>
                        {t('gad7test.question7')}
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>{t('gad7test.total_score')}</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {gad7Tests.map((test) => {
                    const total = [
                      test.question1 ?? 0,
                      test.question2 ?? 0,
                      test.question3 ?? 0,
                      test.question4 ?? 0,
                      test.question5 ?? 0,
                      test.question6 ?? 0,
                      test.question7 ?? 0,
                    ].reduce((sum: number, val: number) => sum + val, 0)

                    return (
                      <TableRow key={test.testId}>
                        <TableCell>
                          {test.creationDate
                            ? format(
                                typeof test.creationDate === 'string'
                                  ? new Date(test.creationDate)
                                  : test.creationDate,
                                'dd.MM.yyyy HH:mm',
                                {
                                  locale: de,
                                }
                              )
                            : '-'}
                        </TableCell>
                        <TableCell align='right'>{test.question1 ?? '-'}</TableCell>
                        <TableCell align='right'>{test.question2 ?? '-'}</TableCell>
                        <TableCell align='right'>{test.question3 ?? '-'}</TableCell>
                        <TableCell align='right'>{test.question4 ?? '-'}</TableCell>
                        <TableCell align='right'>{test.question5 ?? '-'}</TableCell>
                        <TableCell align='right'>{test.question6 ?? '-'}</TableCell>
                        <TableCell align='right'>{test.question7 ?? '-'}</TableCell>
                        <TableCell align='right' style={{ fontWeight: 'bold' }}>
                          {total}
                        </TableCell>
                      </TableRow>
                    )
                  })}
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
