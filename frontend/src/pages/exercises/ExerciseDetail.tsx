import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import {
  Button,
  Checkbox,
  Divider,
  FormControlLabel,
  MenuItem,
  TextField,
  Typography,
} from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import {
  CreateExerciseDTOExerciseTypeEnum,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
  UpdateExerciseDTO,
} from '../../api'
import CreateExerciseFileComponent from '../../generalComponents/CreateExerciseFileComponent'
import CreateExerciseInputFieldComponent from '../../generalComponents/CreateExerciseInputFieldComponent'
import CreateExerciseTextComponent from '../../generalComponents/CreateExerciseTextComponent'
import Layout from '../../generalComponents/Layout'
import LoadingSpinner from '../../generalComponents/LoadingSpinner'
import ShowExerciseFileComponent from '../../generalComponents/ShowExerciseFileComponent'
import ShowExerciseInputFieldComponent from '../../generalComponents/ShowExerciseInputFieldComponent'
import ShowExerciseTextComponent from '../../generalComponents/ShowExerciseTextComponent'
import { deleteExcercise, getExerciseById, updateExercise } from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import { formatDateNicely } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

type ExerciseFormData = Omit<UpdateExerciseDTO, 'exerciseStart' | 'exerciseEnd'> & {
  exerciseStart: Date | null
  exerciseEnd: Date | null
}

const ExerciseDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, exerciseId } = useParams()

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
      getPathFromPage(PAGES.EXERCISES_OVERVIEW_PAGE, {
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
              Title: <strong>{selectedExercise?.title}</strong>
            </Typography>
            <Typography variant='h5'>
              Exercise Type: <strong>{selectedExercise?.exerciseType}</strong>
            </Typography>
            <Typography>
              Exercise Start: <strong>{formatDateNicely(selectedExercise?.exerciseStart)}</strong>
            </Typography>
            <Typography>
              Exercise End: <strong>{formatDateNicely(selectedExercise?.exerciseEnd)}</strong>
            </Typography>
            <Typography>
              Is currently paused: {selectedExercise?.isPaused ? 'Yes' : 'No'}
            </Typography>
            <Button
              onClick={() => toggleIsEditingExercise(true)}
              variant='outlined'
              sx={{ width: 'fit-content', marginTop: '10px' }}
            >
              <EditIcon sx={{ width: '15px', height: '15px', marginRight: '10px' }} /> Edit Exercise
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
                label='Title'
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
                label='Exercise Type'
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
              <FormControlLabel
                control={
                  <Checkbox
                    sx={{ width: 'fit-content' }}
                    name='isPaused'
                    checked={formData.isPaused}
                    onChange={() => setFormData({ ...formData, isPaused: !formData.isPaused })}
                  />
                }
                label='Is Exercise Paused'
              ></FormControlLabel>
              <LocalizationProvider adapterLocale={de} dateAdapter={AdapterDateFns}>
                <DateTimePicker
                  label='Exercise Start'
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
                  label='Exercise End'
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
              <div style={{ display: 'flex', gap: '10px' }}>
                <Button
                  variant='contained'
                  color='error'
                  fullWidth
                  sx={{ mt: 2 }}
                  onClick={() => toggleIsEditingExercise(false)}
                >
                  Cancel
                </Button>
                <Button type='submit' variant='contained' color='primary' fullWidth sx={{ mt: 2 }}>
                  Submit
                </Button>
              </div>
            </form>
          </>
        )}

        <Divider style={{ margin: '40px 0' }} />

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
            <Typography variant='h5'>Exercise Components: </Typography>
          ) : (
            <Typography sx={{ marginBottom: '20px' }} variant='h5'>
              You haven' added any exercise components yet...{' '}
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
      <Divider style={{ margin: '50px 0' }} />
      <Button sx={{ alignSelf: 'start' }} onClick={handleDeleteExercise}>
        <Typography color='error'>Delete Exercise</Typography>
        <DeleteIcon style={{ color: 'red', marginLeft: '5px' }} />
      </Button>
    </Layout>
  )
}

export default ExerciseDetail
