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
      <Typography variant='h5'>Feedback from Client</Typography>
      <TableContainer sx={{ marginTop: '10px' }} component={Paper}>
        <Table aria-label='simple table'>
          <TableHead>
            <TableRow>
              <TableCell>Feedback</TableCell>
              <TableCell>Start Time</TableCell>
              <TableCell>End Time</TableCell>
              <TableCell>Moods Before</TableCell>
              <TableCell>Moods After</TableCell>
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
                        {mood.moodName} ({mood.moodScore}/10)
                      </div>
                    ))}
                  </div>
                </TableCell>
                <TableCell>
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                    {exerciseInfo.moodsAfter?.map((mood) => (
                      <div>
                        {mood.moodName} ({mood.moodScore}/10)
                      </div>
                    ))}
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  )
}

export default ExerciseInformation
