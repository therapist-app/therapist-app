import { useState } from 'react'
import {
  Box,
  Typography,
  RadioGroup,
  FormControlLabel,
  Radio,
  Button,
  Paper,
  FormControl,
  FormLabel,
  Stack,
  Alert,
} from '@mui/material'
import { useNavigate, useParams } from 'react-router-dom'
import { PatientTestControllerApi } from '../../api'
import { CreateGAD7TestDTO } from '../../api/models'
import { ChangeEvent } from 'react'

const questions = [
  'Feeling nervous, anxious or on edge.',
  'Not being able to stop or control worrying.',
  'Worrying too much about different things.',
  'Trouble relaxing.',
  'Being so restless that it is hard to sit still.',
  'Becoming easily annoyed or irritable.',
  'Feeling afraid as if something awful might happen.',
]

const answers = [
  { value: 0, label: 'Not at all' },
  { value: 1, label: 'Several days' },
  { value: 2, label: 'More than half the days' },
  { value: 3, label: 'Nearly every day' },
]

export const GAD7test = () => {
  const navigate = useNavigate()
  const { patientId, therapySessionId } = useParams()
  const [responses, setResponses] = useState<{ [key: number]: number }>({})
  const [error, setError] = useState<string | null>(null)

  const handleAnswerChange = (questionIndex: number, value: number) => {
    setResponses((prev) => ({
      ...prev,
      [questionIndex + 1]: value,
    }))
  }

  const isFormComplete = () => {
    return questions.every((_, index) => responses[index + 1] !== undefined)
  }

  const handleSubmit = async () => {
    if (!isFormComplete()) {
      setError('Please answer all questions')
      return
    }

    try {
      const api = new PatientTestControllerApi()
      const testData: CreateGAD7TestDTO = {
        patientId,
        sessionId: therapySessionId,
        question1: responses[1],
        question2: responses[2],
        question3: responses[3],
        question4: responses[4],
        question5: responses[5],
        question6: responses[6],
        question7: responses[7],
      }

      await api.createTest(testData)
      navigate(`/patients/${patientId}/therapy-sessions/${therapySessionId}`)
    } catch (err) {
      setError('Failed to submit the test. Please try again.')
      console.error('Error submitting GAD-7 test:', err)
    }
  }

  return (
    <Box sx={{ p: 3, maxWidth: 800, margin: '0 auto' }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" gutterBottom>
          GAD-7 Assessment
        </Typography>
        <Typography variant="subtitle1" gutterBottom sx={{ mb: 4 }}>
          Over the last 2 weeks, how often have you been bothered by the following
          problems?
        </Typography>

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        <Stack spacing={3}>
          {questions.map((question, index) => (
            <FormControl key={index} component="fieldset" sx={{ width: '100%' }}>
              <FormLabel component="legend" sx={{ mb: 1, color: 'text.primary' }}>
                {index + 1}. {question}
              </FormLabel>
              <RadioGroup
                row
                value={String(responses[index + 1] ?? '')}  
                onChange={(e: ChangeEvent<HTMLInputElement>) =>
                    handleAnswerChange(index, parseInt(e.target.value))  
                }
                sx={{
                    justifyContent: 'space-between',
                    width: '100%',
                    '& .MuiRadio-root': {
                    '&.Mui-checked': {
                        color: '#635BFF',
                    },
                    '&:hover': {
                        backgroundColor: 'rgba(99, 91, 255, 0.04)',
                    },
                    }
                }}
                >
                {answers.map((answer) => (
                    <FormControlLabel
                    key={answer.value}
                    value={String(answer.value)}  
                    control={
                        <Radio 
                        sx={{
                            '&.Mui-checked': {
                            '& .MuiSvgIcon-root': {
                                color: '#635BFF',
                            }
                            },
                            '& .MuiSvgIcon-root': {
                            fontSize: '20px',
                            }
                        }}
                        />
                    }
                    label={answer.label}
                    sx={{
                        flex: 1,
                        margin: 0,
                        padding: '8px 12px',
                        borderRadius: 1,
                        transition: 'all 0.2s',
                        '&:hover': {
                        backgroundColor: 'rgba(99, 91, 255, 0.04)',
                        },
                        '& .Mui-checked + .MuiFormControlLabel-label': {
                        color: '#635BFF',
                        fontWeight: 500,
                        },
                        '&.MuiFormControlLabel-root.Mui-checked': {
                        backgroundColor: 'rgba(99, 91, 255, 0.08)',
                        }
                    }}
                    />
                ))}
                </RadioGroup>
            </FormControl>
          ))}
        </Stack>

        <Box sx={{ mt: 4, display: 'flex', justifyContent: 'space-between' }}>
          <Button
            variant="outlined"
            onClick={() => navigate(`/patients/${patientId}/therapy-sessions/${therapySessionId}`)}
          >
            Cancel
          </Button>
          <Button
            variant="contained"
            color="primary"
            onClick={handleSubmit}
            disabled={!isFormComplete()}
          >
            Submit
          </Button>
        </Box>
      </Paper>
    </Box>
  )
}

export default GAD7test