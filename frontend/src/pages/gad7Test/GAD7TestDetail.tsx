import {
  Button,
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
import { useNavigate, useParams } from 'react-router-dom'

import { GAD7TestOutputDTO } from '../../api/models'
import { patientTestApi } from '../../utils/api'
import { getPathFromPage, PAGES } from '../../utils/routes'

export const GAD7TestDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, meetingId } = useParams()

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
            gap: '50px',
            alignItems: 'center',
          }}
        >
          <Typography variant='h4'>GAD7 Tests </Typography>
          <Button
            variant='contained'
            color='primary'
            onClick={() =>
              navigate(
                getPathFromPage(PAGES.GAD7_TEST_CREATE_PAGE, {
                  patientId: patientId ?? '',
                  meetingId: meetingId ?? '',
                })
              )
            }
          >
            Create GAD7 Test
          </Button>
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
                        Feeling nervous, anxious or on edge.
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q2
                      <Typography variant='caption' display='block'>
                        Not being able to stop or control worrying.
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q3
                      <Typography variant='caption' display='block'>
                        Worrying too much about different things.
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q4
                      <Typography variant='caption' display='block'>
                        Trouble relaxing.
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q5
                      <Typography variant='caption' display='block'>
                        Being so restless that it is hard to sit still.
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q6
                      <Typography variant='caption' display='block'>
                        Becoming easily annoyed or irritable.
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>
                      Q7
                      <Typography variant='caption' display='block'>
                        Feeling afraid as if something awful might happen.
                      </Typography>
                    </TableCell>
                    <TableCell align='right'>Total</TableCell>
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
          <Typography>You haven't recorded any GAD 7 tests...</Typography>
        )}
      </div>
    </div>
  )
}

export default GAD7TestDetail
