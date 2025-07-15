import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, Checkbox, FormControlLabel, MenuItem, TextField, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import {
  CreateExerciseDTOExerciseTypeEnum,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
  UpdateExerciseDTO,
} from '../../api'
import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import Layout from '../../generalComponents/Layout'
import LoadingSpinner from '../../generalComponents/LoadingSpinner'
import { deleteExcercise, getExerciseById, updateExercise } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { formatDateNicely } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'
import CreateExerciseFileComponent from './components/CreateExerciseFileComponent'
import CreateExerciseInputFieldComponent from './components/CreateExerciseInputFieldComponent'
import CreateExerciseTextComponent from './components/CreateExerciseTextComponent'
import ShowExerciseFileComponent from './components/ShowExerciseFileComponent'
import ShowExerciseInputFieldComponent from './components/ShowExerciseInputFieldComponent'
import ShowExerciseTextComponent from './components/ShowExerciseTextComponent'

type ExerciseFormData = Omit<UpdateExerciseDTO, 'exerciseStart' | 'exerciseEnd'> & {
  exerciseStart: Date | null
  exerciseEnd: Date | null
}

const ExerciseDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, exerciseId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()

  const selectedExercise = useSelector((state: RootState) => state.exercise.selectedExercise)

  const exerciseStatus = useSelector((state: RootState) => state.exercise.status)

  const addingExerciseComponent = useSelector(
    (state: RootState) => state.exercise.addingExerciseComponent
  )

  const [formData, setFormData] = useState<ExerciseFormData>({
    title: selectedExercise?.title,
    exerciseType: selectedExercise?.exerciseType,
    exerciseStart: new Date(selectedExercise?.exerciseStart ?? ''),
    exerciseEnd: new Date(selectedExercise?.exerciseEnd ?? ''),
    isPaused: selectedExercise?.isPaused,
  })

  const [isEditingExercise, setIsEditingExercise] = useState(false)

  const toggleIsEditingExercise = (isEditing: boolean): void => {
    if (isEditing) {
      setFormData({
        title: selectedExercise?.title,
        exerciseType: selectedExercise?.exerciseType,
        exerciseStart: new Date(selectedExercise?.exerciseStart ?? ''),
        exerciseEnd: new Date(selectedExercise?.exerciseEnd ?? ''),
        isPaused: selectedExercise?.isPaused,
      })
      setIsEditingExercise(true)
    } else {
      setIsEditingExercise(false)
    }
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault()

    try {
      const UpdateExerciseDTO: UpdateExerciseDTO = {
        ...formData,
        exerciseStart: formData.exerciseStart?.toISOString(),
        exerciseEnd: formData.exerciseEnd?.toISOString(),
        id: exerciseId ?? '',
      }
      await dispatch(updateExercise(UpdateExerciseDTO))
      setIsEditingExercise(false)
    } catch (err) {
      console.error('Registration error:', err)
    }
  }

  const refreshExercise = async (): Promise<void> => {
    try {
      await dispatch(getExerciseById(exerciseId ?? ''))
    } catch (e) {
      console.error(e)
    }
  }

  const handleDeleteExercise = async (): Promise<void> => {
    await dispatch(deleteExcercise(exerciseId ?? ''))
    navigate(
      getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, {
        patientId: patientId ?? '',
      })
    )
  }

  useEffect(() => {
    const refreshExercise = async (): Promise<void> => {
      try {
        await dispatch(getExerciseById(exerciseId ?? ''))
      } catch (e) {
        console.error(e)
      }
    }
    refreshExercise()
  }, [exerciseId, dispatch])

  if (exerciseStatus === 'loading') {
    return (
      <Layout>
        <LoadingSpinner />{' '}
      </Layout>
    )
  }

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        {isEditingExercise === false ? (
          <>
            {' '}
            <Typography variant='h4'>
              {t('exercise.title')}: <strong>{selectedExercise?.title}</strong>
            </Typography>
            <Typography variant='h5'>
              {t('exercise.exercise_type')}: <strong>{selectedExercise?.exerciseType}</strong>
            </Typography>
            <Typography>
              {t('exercise.exercise_start')}:{' '}
              <strong>{formatDateNicely(selectedExercise?.exerciseStart)}</strong>
            </Typography>
            <Typography>
              {t('exercise.exercise_end')}:{' '}
              <strong>{formatDateNicely(selectedExercise?.exerciseEnd)}</strong>
            </Typography>
            <Typography>
              {t('exercise.is_currently_paused')}: {selectedExercise?.isPaused ? 'Yes' : 'No'}
            </Typography>
            <Button
              onClick={() => toggleIsEditingExercise(true)}
              variant='outlined'
              sx={{ width: 'fit-content', marginTop: '10px' }}
            >
              <EditIcon sx={{ width: '15px', height: '15px', marginRight: '10px' }} />{' '}
              {t('exercise.edit_exercise')}
            </Button>
          </>
        ) : (
          <>
            {' '}
            <form
              onSubmit={handleSubmit}
              style={{ display: 'flex', flexDirection: 'column', gap: '15px', maxWidth: '600px' }}
            >
              <TextField
                label={t('exercise.title')}
                name='title'
                value={formData.title}
                onChange={handleChange}
                fullWidth
                margin='normal'
                required
              />
              <TextField
                select
                sx={{ fontWeight: 'bold' }}
                label={t('exercise.exercise_type')}
                name='exerciseType'
                value={formData.exerciseType}
                onChange={handleChange}
                required
                fullWidth
              >
                {Object.values(CreateExerciseDTOExerciseTypeEnum).map((option: string) => (
                  <MenuItem key={option} value={option}>
                    {option}
                  </MenuItem>
                ))}
              </TextField>

              <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
                <DateTimePicker
                  label={t('exercise.exercise_start')}
                  value={formData.exerciseStart}
                  onChange={(newValue: Date | null) => {
                    setFormData({
                      ...formData,
                      exerciseStart: newValue,
                    })
                  }}
                  sx={{ width: '100%' }}
                />

                <DateTimePicker
                  label={t('exercise.exercise_end')}
                  value={formData.exerciseEnd}
                  onChange={(newValue: Date | null) => {
                    setFormData({
                      ...formData,
                      exerciseEnd: newValue,
                    })
                  }}
                  sx={{ width: '100%' }}
                />
              </LocalizationProvider>
              <FormControlLabel
                control={
                  <Checkbox
                    sx={{ width: 'fit-content' }}
                    name='isPaused'
                    checked={formData.isPaused}
                    onChange={() => setFormData({ ...formData, isPaused: !formData.isPaused })}
                  />
                }
                label={t('exercise.is_exercise_paused')}
              ></FormControlLabel>
              <div style={{ display: 'flex', gap: '10px' }}>
                <Button
                  variant='contained'
                  color='error'
                  fullWidth
                  sx={{ mt: 2 }}
                  onClick={() => toggleIsEditingExercise(false)}
                >
                  {t('exercise.cancel')}
                </Button>
                <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
                  {t('exercise.submit')}
                </Button>
              </div>
            </form>
          </>
        )}

        <CustomizedDivider />

        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: '20px',

            maxWidth: '600px',
          }}
        >
          {selectedExercise?.exerciseComponentsOutputDTO &&
          selectedExercise.exerciseComponentsOutputDTO.length > 0 ? (
            <Typography variant='h5'>{t('exercise.exercise_components')}: </Typography>
          ) : (
            <Typography sx={{ marginBottom: '20px' }} variant='h5'>
              {t('exercise.no_exercise_components_yet')}{' '}
            </Typography>
          )}

          {selectedExercise?.exerciseComponentsOutputDTO &&
            selectedExercise.exerciseComponentsOutputDTO.map((exerciseComponent) => (
              <div
                key={exerciseComponent.id}
                style={{
                  border: '1px solid black',
                  borderRadius: '5px',
                  padding: '20px',
                }}
              >
                {exerciseComponent.exerciseComponentType ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image && (
                  <ShowExerciseFileComponent
                    exerciseComponent={exerciseComponent}
                    numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                    refresh={refreshExercise}
                    isImageComponent
                  />
                )}
                {exerciseComponent.exerciseComponentType ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.File && (
                  <ShowExerciseFileComponent
                    exerciseComponent={exerciseComponent}
                    numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                    refresh={refreshExercise}
                    isImageComponent={false}
                  />
                )}
                {exerciseComponent.exerciseComponentType ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text && (
                  <ShowExerciseTextComponent
                    exerciseComponent={exerciseComponent}
                    numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                    refresh={refreshExercise}
                  />
                )}

                {exerciseComponent.exerciseComponentType ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputField && (
                  <ShowExerciseInputFieldComponent
                    exerciseComponent={exerciseComponent}
                    numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                    refresh={refreshExercise}
                  />
                )}
              </div>
            ))}
        </div>
        <div
          style={{
            display: 'flex',
            gap: '15px',
            marginTop: '20px',
          }}
        >
          <CreateExerciseTextComponent
            createdExercise={refreshExercise}
            active={
              addingExerciseComponent === null ||
              addingExerciseComponent === ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text
            }
          />

          <CreateExerciseFileComponent
            createdExerciseFile={refreshExercise}
            isImageComponent
            active={
              addingExerciseComponent === null ||
              addingExerciseComponent === ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image
            }
          />

          <CreateExerciseFileComponent
            createdExerciseFile={refreshExercise}
            isImageComponent={false}
            active={
              addingExerciseComponent === null ||
              addingExerciseComponent === ExerciseComponentOutputDTOExerciseComponentTypeEnum.File
            }
          />

          <CreateExerciseInputFieldComponent
            createdInputField={refreshExercise}
            active={
              addingExerciseComponent === null ||
              addingExerciseComponent ===
                ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputField
            }
          />
        </div>
      </div>

      <CustomizedDivider />

      <Button sx={{ alignSelf: 'start' }} onClick={handleDeleteExercise}>
        <Typography color='error'>{t('exercise.delete_exercise')}</Typography>
        <DeleteIcon style={{ color: 'red', marginLeft: '5px' }} />
      </Button>
    </Layout>
  )
}

export default ExerciseDetail
