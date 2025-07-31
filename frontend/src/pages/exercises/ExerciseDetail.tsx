import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, Checkbox, FormControlLabel, Switch, TextField, Typography } from '@mui/material'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { de } from 'date-fns/locale'
import { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { ExerciseComponentOutputDTOExerciseComponentTypeEnum, UpdateExerciseDTO } from '../../api'
import CustomizedDivider from '../../generalComponents/CustomizedDivider'
import Layout from '../../generalComponents/Layout'
import LoadingSpinner from '../../generalComponents/LoadingSpinner'
import { useNotify } from '../../hooks/useNotify'
import {
  deleteExercise,
  getExerciseById,
  getExerciseInformation,
  updateExercise,
} from '../../store/exerciseSlice'
import { RootState } from '../../store/store'
import {
  cancelButtonStyles,
  commonButtonStyles,
  deleteButtonStyles,
  successButtonStyles,
} from '../../styles/buttonStyles'
import { formatDateNicely } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'
import CreateExerciseFileComponent from './components/CreateExerciseFileComponent'
import CreateExerciseInputFieldComponent from './components/CreateExerciseInputFieldComponent'
import CreateExerciseTextComponent from './components/CreateExerciseTextComponent'
import CreateExerciseYoutubeVideoComponent from './components/CreateExerciseYoutubeVideoComponent'
import ExerciseInformation from './components/ExerciseInformation'
import ShowExerciseFileComponent from './components/ShowExerciseFileComponent'
import ShowExerciseInputFieldComponent from './components/ShowExerciseInputFieldComponent'
import ShowExerciseTextComponent from './components/ShowExerciseTextComponent'
import ShowExerciseYoutubeVideoComponent from './components/ShowExerciseYoutubeVideoComponent'

type ExerciseFormData = Omit<UpdateExerciseDTO, 'exerciseStart' | 'exerciseEnd'> & {
  exerciseStart: Date | null
  exerciseEnd: Date | null
}

const ExerciseDetail = (): ReactElement => {
  const navigate = useNavigate()
  const { patientId, exerciseId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()
  const { notifyError, notifySuccess } = useNotify()

  const selectedExercise = useSelector((state: RootState) => state.exercise.selectedExercise)
  const selectedExerciseInformation = useSelector(
    (state: RootState) => state.exercise.selectedExerciseInformation
  )
  const exerciseStatus = useSelector((state: RootState) => state.exercise.status)
  const addingExerciseComponent = useSelector(
    (state: RootState) => state.exercise.addingExerciseComponent
  )

  const [formData, setFormData] = useState<ExerciseFormData>({
    exerciseTitle: selectedExercise?.exerciseTitle,
    exerciseDescription: selectedExercise?.exerciseDescription,
    exerciseExplanation: selectedExercise?.exerciseExplanation,
    exerciseStart: new Date(selectedExercise?.exerciseStart ?? ''),
    exerciseEnd: new Date(selectedExercise?.exerciseEnd ?? ''),
    isPaused: selectedExercise?.isPaused,
    doEveryNDays: selectedExercise?.doEveryNDays,
  })

  const [isEditingExercise, setIsEditingExercise] = useState(false)
  const [isViewMode, setIsViewMode] = useState(false)

  const toggleIsEditingExercise = (isEditing: boolean): void => {
    if (isEditing) {
      setFormData({
        exerciseTitle: selectedExercise?.exerciseTitle,
        exerciseDescription: selectedExercise?.exerciseDescription,
        exerciseExplanation: selectedExercise?.exerciseExplanation,
        exerciseStart: new Date(selectedExercise?.exerciseStart ?? ''),
        exerciseEnd: new Date(selectedExercise?.exerciseEnd ?? ''),
        isPaused: selectedExercise?.isPaused,
        doEveryNDays: selectedExercise?.doEveryNDays,
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
      const dto: UpdateExerciseDTO = {
        ...formData,
        exerciseStart: formData.exerciseStart?.toISOString(),
        exerciseEnd: formData.exerciseEnd?.toISOString(),
        id: exerciseId ?? '',
      }
      await dispatch(updateExercise(dto)).unwrap()
      notifySuccess(t('exercise.exercise_updated_successfully'))
      setIsEditingExercise(false)
      await refreshExercise()
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    }
  }

  const refreshExercise = async (): Promise<void> => {
    try {
      await dispatch(getExerciseById(exerciseId ?? '')).unwrap()
    } catch (e) {
      notifyError(typeof e === 'string' ? e : 'An unknown error occurred')
    }
  }

  const handleDeleteExercise = async (): Promise<void> => {
    try {
      await dispatch(deleteExercise(exerciseId ?? '')).unwrap()
      notifySuccess(t('exercise.exercise_deleted_successfully'))
      navigate(
        getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, {
          patientId: patientId ?? '',
        })
      )
    } catch (e) {
      notifyError(typeof e === 'string' ? e : 'An unknown error occurred')
    }
  }

  useEffect(() => {
    const load = async (): Promise<void> => {
      try {
        await dispatch(getExerciseById(exerciseId ?? '')).unwrap()
        await dispatch(getExerciseInformation(exerciseId ?? '')).unwrap()
      } catch (e) {
        notifyError(typeof e === 'string' ? e : 'An unknown error occurred')
      }
    }
    void load()
  }, [exerciseId, dispatch, notifyError])

  if (exerciseStatus === 'loading') {
    return (
      <Layout>
        <LoadingSpinner />
      </Layout>
    )
  }

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        {!isEditingExercise ? (
          <>
            <Typography variant='h4'>
              {t('exercise.title')}: {selectedExercise?.exerciseTitle}
            </Typography>
            <Typography>
              <strong>{t('exercise.exerciseDescription')}: </strong>
              {selectedExercise?.exerciseDescription}
            </Typography>
            <Typography>
              <strong>{t('exercise.exerciseExplanation')}: </strong>
              {selectedExercise?.exerciseExplanation}
            </Typography>
            <Typography>
              <strong>{t('exercise.exercise_start')}: </strong>
              {formatDateNicely(selectedExercise?.exerciseStart)}
            </Typography>
            <Typography>
              <strong>{t('exercise.exercise_end')}: </strong>
              {formatDateNicely(selectedExercise?.exerciseEnd)}
            </Typography>
            <Typography>
              <strong>{t('exercise.is_currently_paused')}: </strong>
              {selectedExercise?.isPaused ? t('yes') : t('no')}
            </Typography>
            <Typography>
              <strong>{t('exercise.doEveryNDays')}: </strong>
              {selectedExercise?.doEveryNDays}
            </Typography>
            <Button
              onClick={() => toggleIsEditingExercise(true)}
              sx={{ ...commonButtonStyles, minWidth: '170px', marginTop: '10px' }}
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
                name='exerciseTitle'
                value={formData.exerciseTitle}
                onChange={handleChange}
                fullWidth
                required
              />
              <TextField
                label={t('exercise.exerciseDescription')}
                name='exerciseDescription'
                value={formData.exerciseDescription}
                onChange={handleChange}
                fullWidth
              />

              <TextField
                label={t('exercise.exerciseExplanation')}
                name='exerciseExplanation'
                value={formData.exerciseExplanation}
                onChange={handleChange}
                fullWidth
              />

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

              <TextField
                label={t('exercise.doEveryNDays')}
                type='number'
                value={formData.doEveryNDays}
                onChange={(e) => {
                  setFormData({
                    ...formData,
                    doEveryNDays: Number(e.target.value),
                  })
                }}
                sx={{ width: '100%' }}
              />

              <div style={{ display: 'flex', gap: '10px' }}>
                <Button
                  sx={{ ...cancelButtonStyles, mt: 2 }}
                  onClick={() => toggleIsEditingExercise(false)}
                >
                  {t('exercise.cancel')}
                </Button>
                <Button type='submit' sx={{ ...successButtonStyles, mt: 2 }}>
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
          }}
        >
          {selectedExercise?.exerciseComponentsOutputDTO &&
          selectedExercise.exerciseComponentsOutputDTO.length > 0 ? (
            <Typography variant='h5'>{t('exercise.exercise_components')}:</Typography>
          ) : (
            <Typography sx={{ marginBottom: '20px' }} variant='h5'>
              {t('exercise.no_exercise_components_yet')}
            </Typography>
          )}
          <div style={{ display: 'flex', gap: '5px', alignItems: 'center' }}>
            <Typography>{t('exercise.editMode')}</Typography>
            <Switch onClick={() => setIsViewMode(!isViewMode)} />
            <Typography>{t('exercise.viewMode')}</Typography>
          </div>
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              gap: '20px',
              border: isViewMode ? '1px solid black' : '',
              borderRadius: '5px',
              padding: isViewMode ? '20px' : '',
            }}
          >
            {selectedExercise?.exerciseComponentsOutputDTO &&
              selectedExercise.exerciseComponentsOutputDTO.map((exerciseComponent) => (
                <div
                  key={exerciseComponent.id}
                  style={{
                    border: isViewMode ? '' : '1px solid black',
                    borderRadius: '5px',
                    padding: isViewMode ? '' : '20px',
                  }}
                >
                  {exerciseComponent.exerciseComponentType ===
                    ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image && (
                    <ShowExerciseFileComponent
                      isViewMode={isViewMode}
                      exerciseComponent={exerciseComponent}
                      numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                      refresh={refreshExercise}
                      isImageComponent
                    />
                  )}
                  {exerciseComponent.exerciseComponentType ===
                    ExerciseComponentOutputDTOExerciseComponentTypeEnum.File && (
                    <ShowExerciseFileComponent
                      isViewMode={isViewMode}
                      exerciseComponent={exerciseComponent}
                      numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                      refresh={refreshExercise}
                      isImageComponent={false}
                    />
                  )}

                  {exerciseComponent.exerciseComponentType ===
                    ExerciseComponentOutputDTOExerciseComponentTypeEnum.YoutubeVideo && (
                    <ShowExerciseYoutubeVideoComponent
                      isViewMode={isViewMode}
                      exerciseComponent={exerciseComponent}
                      numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                      refresh={refreshExercise}
                    />
                  )}

                  {exerciseComponent.exerciseComponentType ===
                    ExerciseComponentOutputDTOExerciseComponentTypeEnum.Text && (
                    <ShowExerciseTextComponent
                      isViewMode={isViewMode}
                      exerciseComponent={exerciseComponent}
                      numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                      refresh={refreshExercise}
                    />
                  )}

                  {exerciseComponent.exerciseComponentType ===
                    ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldPrivate && (
                    <ShowExerciseInputFieldComponent
                      isViewMode={isViewMode}
                      isPrivateField
                      exerciseComponent={exerciseComponent}
                      numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                      refresh={refreshExercise}
                    />
                  )}

                  {exerciseComponent.exerciseComponentType ===
                    ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldShared && (
                    <ShowExerciseInputFieldComponent
                      isViewMode={isViewMode}
                      isPrivateField={false}
                      exerciseComponent={exerciseComponent}
                      numberOfExercises={selectedExercise.exerciseComponentsOutputDTO?.length ?? 0}
                      refresh={refreshExercise}
                    />
                  )}
                </div>
              ))}
          </div>
        </div>

        {!isViewMode && (
          <div style={{ display: 'flex', gap: '15px', marginTop: '20px' }}>
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
                addingExerciseComponent ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.Image
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
            <CreateExerciseYoutubeVideoComponent
              addedYoutubeVideo={refreshExercise}
              active={
                addingExerciseComponent === null ||
                addingExerciseComponent ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.YoutubeVideo
              }
            />
            <CreateExerciseInputFieldComponent
              createdInputField={refreshExercise}
              isPrivateField
              active={
                addingExerciseComponent === null ||
                addingExerciseComponent ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldPrivate
              }
            />

            <CreateExerciseInputFieldComponent
              createdInputField={refreshExercise}
              isPrivateField={false}
              active={
                addingExerciseComponent === null ||
                addingExerciseComponent ===
                  ExerciseComponentOutputDTOExerciseComponentTypeEnum.InputFieldShared
              }
            />
          </div>
        )}
      </div>

      <CustomizedDivider />

      <ExerciseInformation exerciseInformations={selectedExerciseInformation} />

      <CustomizedDivider />

      <Button sx={{ ...deleteButtonStyles, minWidth: '190px' }} onClick={handleDeleteExercise}>
        <Typography>{t('exercise.delete_exercise')}</Typography>
        <DeleteIcon style={{ marginLeft: '5px' }} />
      </Button>
    </Layout>
  )
}

export default ExerciseDetail
