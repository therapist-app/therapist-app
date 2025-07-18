import {
  Alert,
  Box,
  Button,
  FormControl,
  FormControlLabel,
  FormLabel,
  Paper,
  Radio,
  RadioGroup,
  Stack,
  Typography,
} from '@mui/material'
import { ChangeEvent, ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate, useParams } from 'react-router-dom'

import { CreateGAD7TestDTO } from '../../api/models'
import Layout from '../../generalComponents/Layout'
import { patientTestApi } from '../../utils/api'
import { getPathFromPage, PAGES } from '../../utils/routes'

export const GAD7TestCreate = (): ReactElement => {
  const navigate = useNavigate()
  const { t } = useTranslation()
  const { patientId } = useParams()
  const [responses, setResponses] = useState<{ [key: number]: number }>({})
  const [error, setError] = useState<string | null>(null)

  const answers = [
    { value: 0, label: t('gad7test.answers.not_at_all') },
    { value: 1, label: t('gad7test.answers.several_days') },
    { value: 2, label: t('gad7test.answers.more_than_half_the_days') },
    { value: 3, label: t('gad7test.answers.nearly_everyday') },
  ]

  const questions = [
    t('gad7test.question1'),
    t('gad7test.question2'),
    t('gad7test.question3'),
    t('gad7test.question4'),
    t('gad7test.question5'),
    t('gad7test.question6'),
    t('gad7test.question7'),
  ]

  const handleAnswerChange = (questionIndex: number, value: number): void => {
    setResponses((prev) => ({
      ...prev,
      [questionIndex + 1]: value,
    }))
  }

  const isFormComplete = (): boolean => {
    return questions.every((_, index) => responses[index + 1] !== undefined)
  }

  const handleSubmit = async (): Promise<void> => {
    if (!isFormComplete()) {
      setError('Please answer all questions')
      return
    }

    try {
      const testData: CreateGAD7TestDTO = {
        patientId: patientId,
        question1: responses[1],
        question2: responses[2],
        question3: responses[3],
        question4: responses[4],
        question5: responses[5],
        question6: responses[6],
        question7: responses[7],
      }

      await patientTestApi.createTest(testData)

      navigate(
        getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, {
          patientId: patientId ?? '',
        })
      )
    } catch (err) {
      setError('Failed to submit the test. Please try again.')
      console.error('Error submitting GAD-7 test:', err)
    }
  }

  const handleCancel = (): void => {
    navigate(
      getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  return (
    <Layout>
      <Box sx={{ maxWidth: 800, margin: '0 auto' }}>
        <Paper elevation={3} sx={{ p: 4 }}>
          <Typography variant='h4' gutterBottom>
            {t('gad7test.title')}
          </Typography>
          <Typography variant='subtitle1' gutterBottom sx={{ mb: 4 }}>
            {t('gad7test.description')}
          </Typography>

          {error && (
            <Alert severity='error' sx={{ mb: 3 }}>
              {error}
            </Alert>
          )}

          <Stack spacing={3}>
            {questions.map((question, index) => (
              <FormControl key={index} component='fieldset' sx={{ width: '100%' }}>
                <FormLabel component='legend' sx={{ mb: 1, color: 'text.primary' }}>
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
                    },
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
                              },
                            },
                            '& .MuiSvgIcon-root': {
                              fontSize: '20px',
                            },
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
                        },
                      }}
                    />
                  ))}
                </RadioGroup>
              </FormControl>
            ))}
          </Stack>

          <Box sx={{ mt: 4, display: 'flex', justifyContent: 'space-between' }}>
            <Button variant='outlined' onClick={handleCancel}>
              {t('gad7test.cancel')}
            </Button>
            <Button
              variant='contained'
              color='primary'
              onClick={handleSubmit}
              disabled={!isFormComplete()}
            >
              {t('gad7test.submit')}
            </Button>
          </Box>
        </Paper>
      </Box>
    </Layout>
  )
}

export default GAD7TestCreate
