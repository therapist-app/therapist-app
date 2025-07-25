import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  CreateExerciseComponentDTO,
  CreateExerciseDTO,
  ExerciseComponentOutputDTOExerciseComponentTypeEnum,
  ExerciseOutputDTO,
  MeetingOutputDTO,
  UpdateExerciseComponentDTO,
  UpdateExerciseDTO,
} from '../api'
import { exerciseApi, exerciseComponentApi } from '../utils/api'

interface ExerciseState {
  selectedExercise: ExerciseOutputDTO | null
  allExercisesOfPatient: ExerciseOutputDTO[]
  addingExerciseComponent: ExerciseComponentOutputDTOExerciseComponentTypeEnum | null
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: ExerciseState = {
  selectedExercise: null,
  allExercisesOfPatient: [],
  addingExerciseComponent: null,
  status: 'idle',
  error: null,
}

export const createExercise = createAsyncThunk(
  'createExercise',
  async (createExerciseDTO: CreateExerciseDTO) => {
    const response = await exerciseApi.createExercise(createExerciseDTO)
    return response.data
  }
)

export const getExerciseById = createAsyncThunk('getExerciseById', async (exerciseId: string) => {
  const response = await exerciseApi.getExerciseById(exerciseId)
  return response.data
})

export const getAllExercisesOfPatient = createAsyncThunk(
  'getAllExercisesOfPatient',
  async (patientId: string) => {
    const response = await exerciseApi.getAllExercisesOfPatient(patientId)
    return response.data
  }
)

export const updateExercise = createAsyncThunk(
  'updateExercise',
  async (updateExerciseDTO: UpdateExerciseDTO) => {
    const response = await exerciseApi.updateExercise(updateExerciseDTO)
    return response.data
  }
)

export const deleteExercise = createAsyncThunk('deleteExcercise', async (exerciseId: string) => {
  const response = await exerciseApi.deleteExercise(exerciseId)
  return response.data
})

export const createExerciseComponent = createAsyncThunk(
  'createExerciseComponent',
  async (props: {
    createExerciseComponentDTO: CreateExerciseComponentDTO
    file: File | undefined
  }) => {
    if (props.file) {
      const response = await exerciseComponentApi.createExerciseComponentWithFile(
        props.createExerciseComponentDTO,
        props.file
      )
      return response.data
    }
    const response = await exerciseComponentApi.createExerciseComponent(
      props.createExerciseComponentDTO
    )
    return response.data
  }
)

export const downloadExerciseComponent = createAsyncThunk(
  'downloadExerciseComponent',
  async (exerciseComponentId: string) => {
    const response = await exerciseComponentApi.downloadExerciseComponentFile(exerciseComponentId, {
      responseType: 'blob',
    })
    const file = response.data
    const url = window.URL.createObjectURL(file)
    return url
  }
)

export const updateExerciseComponent = createAsyncThunk(
  'updateExerciseComponent',
  async (updateExerciseComponentDTO: UpdateExerciseComponentDTO) => {
    const response = await exerciseComponentApi.updateExerciseComponent(updateExerciseComponentDTO)
    return response.data
  }
)

export const deleteExerciseComponent = createAsyncThunk(
  'deleteExerciseComponent',
  async (exerciseComponentId: string) => {
    const response = await exerciseComponentApi.deleteExerciseComponent(exerciseComponentId)
    return response.data
  }
)

const exerciseSlice = createSlice({
  name: 'exercise',
  initialState: initialState,
  reducers: {
    setSelectedExercise: (state, action: PayloadAction<ExerciseOutputDTO>) => {
      state.selectedExercise = action.payload
    },
    setAllExercisesOfMeeting: (state, action: PayloadAction<MeetingOutputDTO[]>) => {
      state.allExercisesOfPatient = action.payload
    },
    clearSelectedExercise: (state) => {
      state.selectedExercise = null
    },
    clearAllExercisesOfMeeting: (state) => {
      state.allExercisesOfPatient = []
    },
    setAddingExerciseComponent: (
      state,
      action: PayloadAction<ExerciseComponentOutputDTOExerciseComponentTypeEnum | null>
    ) => {
      state.addingExerciseComponent = action.payload
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(createExercise.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createExercise.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedExercise = action.payload
      })
      .addCase(createExercise.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(getExerciseById.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getExerciseById.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedExercise = action.payload
      })
      .addCase(getExerciseById.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(getAllExercisesOfPatient.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getAllExercisesOfPatient.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.allExercisesOfPatient = action.payload
      })
      .addCase(getAllExercisesOfPatient.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(updateExercise.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateExercise.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedExercise = action.payload
      })
      .addCase(updateExercise.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteExercise.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteExercise.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteExercise.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(createExerciseComponent.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createExerciseComponent.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(createExerciseComponent.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(updateExerciseComponent.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateExerciseComponent.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(updateExerciseComponent.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteExerciseComponent.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteExerciseComponent.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteExerciseComponent.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const {
  setSelectedExercise,
  setAllExercisesOfMeeting,
  clearSelectedExercise,
  clearAllExercisesOfMeeting,
  setAddingExerciseComponent,
} = exerciseSlice.actions
export default exerciseSlice.reducer
