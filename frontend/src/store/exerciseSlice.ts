import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  CreateExcerciseDTO,
  CreateExerciseFileDTO,
  CreateExerciseTextDTO,
  ExerciseOutputDTO,
  TherapySessionOutputDTO,
  UpdateExerciseDTO,
  UpdateExerciseFileDTO,
  UpdateExerciseTextDTO,
} from '../api'
import { exerciseApi, exerciseFileApi, exerciseTextApi } from '../utils/api'

interface ExerciseState {
  selectedExercise: ExerciseOutputDTO | null
  allExercisesOfTherapySession: ExerciseOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: ExerciseState = {
  selectedExercise: null,
  allExercisesOfTherapySession: [],
  status: 'idle',
  error: null,
}

export const createExercise = createAsyncThunk(
  'createExercise',
  async (createExerciseDTO: CreateExcerciseDTO) => {
    const response = await exerciseApi.createExercise(createExerciseDTO)
    return response.data
  }
)

export const getExerciseById = createAsyncThunk('getExerciseById', async (exerciseId: string) => {
  const response = await exerciseApi.getExerciseById(exerciseId)
  return response.data
})

export const getAllExercisesOfTherapySession = createAsyncThunk(
  'getAllExercisesOfTherapySession',
  async (therapySessionId: string) => {
    const response = await exerciseApi.getAllExercisesOfTherapySession(therapySessionId)
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

export const deleteExcercise = createAsyncThunk('deleteExcercise', async (exerciseId: string) => {
  const response = await exerciseApi.deleteExcercise(exerciseId)
  return response.data
})

export const createExerciseFile = createAsyncThunk(
  'createExerciseFile',
  async (props: { createExerciseFileDTO: CreateExerciseFileDTO; file: File }) => {
    const response = await exerciseFileApi.createExerciseFile(
      props.createExerciseFileDTO,
      props.file
    )
    return response.data
  }
)

export const downloadExerciseFile = createAsyncThunk(
  'downloadExerciseFile',
  async (exerciseFileId: string) => {
    const response = await exerciseFileApi.downloadExerciseFile(exerciseFileId)
    return response.data
  }
)

export const updateExerciseFile = createAsyncThunk(
  'updateExerciseFile',
  async (updateExerciseFileDTO: UpdateExerciseFileDTO) => {
    const response = await exerciseFileApi.updateExerciseFile(updateExerciseFileDTO)
    return response.data
  }
)

export const deleteExerciseFile = createAsyncThunk(
  'deleteExerciseFile',
  async (exerciseFileId: string) => {
    const response = await exerciseFileApi.deleteExcerciseFile(exerciseFileId)
    return response.data
  }
)

export const createExerciseText = createAsyncThunk(
  'createExerciseText',
  async (createExerciseTextDTO: CreateExerciseTextDTO) => {
    const response = await exerciseTextApi.createExerciseText(createExerciseTextDTO)
    return response.data
  }
)

export const updateExerciseText = createAsyncThunk(
  'updateExerciseText',
  async (updateExerciseTextDTO: UpdateExerciseTextDTO) => {
    const response = await exerciseTextApi.updateExerciseText(updateExerciseTextDTO)
    return response.data
  }
)

export const deleteExerciseText = createAsyncThunk(
  'deleteExerciseText',
  async (exerciseTextId: string) => {
    const response = await exerciseTextApi.deleteExerciseText(exerciseTextId)
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
    setAllExercisesOfTherapySession: (state, action: PayloadAction<TherapySessionOutputDTO[]>) => {
      state.allExercisesOfTherapySession = action.payload
    },
    clearSelectedExercise: (state) => {
      state.selectedExercise = null
    },
    clearAllExercisesOfTherapySession: (state) => {
      state.allExercisesOfTherapySession = []
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

      .addCase(getAllExercisesOfTherapySession.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getAllExercisesOfTherapySession.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.allExercisesOfTherapySession = action.payload
      })
      .addCase(getAllExercisesOfTherapySession.rejected, (state, action) => {
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

      .addCase(deleteExcercise.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteExcercise.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteExcercise.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(createExerciseFile.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createExerciseFile.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(createExerciseFile.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(downloadExerciseFile.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(downloadExerciseFile.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(downloadExerciseFile.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(updateExerciseFile.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateExerciseFile.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(updateExerciseFile.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteExerciseFile.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteExerciseFile.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteExerciseFile.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(createExerciseText.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createExerciseText.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(createExerciseText.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(updateExerciseText.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateExerciseText.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(updateExerciseText.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteExerciseText.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteExerciseText.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteExerciseText.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const {
  setSelectedExercise,
  setAllExercisesOfTherapySession,
  clearSelectedExercise,
  clearAllExercisesOfTherapySession,
} = exerciseSlice.actions
export default exerciseSlice.reducer
