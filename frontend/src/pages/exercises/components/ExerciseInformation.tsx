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
import { useTranslation } from 'react-i18next'

import { ExerciseInformationOutputDTOPatientAPI } from '../../../api'
import { formatDateNicely } from '../../../utils/dateUtil'

interface ExerciseInformationProps {
  exerciseInformations: ExerciseInformationOutputDTOPatientAPI[]
}

const ExerciseInformation: React.FC<ExerciseInformationProps> = (props) => {
  const { t } = useTranslation()

  return (
    <>
      <Typography variant='h5'>{t('exercise.client_feedback')}</Typography>

      {props.exerciseInformations.length > 0 ? (
        <TableContainer sx={{ marginTop: '10px' }} component={Paper}>
          <Table aria-label='simple table'>
            <TableHead>
              <TableRow>
                <TableCell>{t('exercise.feedback')}</TableCell>
                <TableCell>{t('exercise.startTime')}</TableCell>
                <TableCell>{t('exercise.endTime')}</TableCell>
                <TableCell>{t('exercise.moodsBefore')}</TableCell>
                <TableCell>{t('exercise.moodsAfter')}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {props.exerciseInformations.map((exerciseInfo) => (
                <TableRow>
                  <TableCell>{exerciseInfo.feedback}</TableCell>
                  <TableCell>{formatDateNicely(exerciseInfo.startTime)}</TableCell>
                  <TableCell>{formatDateNicely(exerciseInfo.endTime)}</TableCell>
                  <TableCell>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                      {exerciseInfo.moodsBefore?.map((mood) => (
                        <div>
                          {mood.moodName} ({mood.moodScore}/5)
                        </div>
                      ))}
                    </div>
                  </TableCell>
                  <TableCell>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                      {exerciseInfo.moodsAfter?.map((mood) => (
                        <div>
                          {mood.moodName} ({mood.moodScore}/5)
                        </div>
                      ))}
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography variant='body1' sx={{ marginTop: '20px' }}>
          {t('exercise.no_client_feedback')}
        </Typography>
      )}
    </>
  )
}

export default ExerciseInformation
