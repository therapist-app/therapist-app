import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  AddExerciseToCounselingPlanPhaseDTO,
  CounselingPlanOutputDTO,
  CreateCounselingPlanExerciseAIGeneratedDTO,
  CreateCounselingPlanPhaseAIGeneratedDTO,
  CreateCounselingPlanPhaseDTO,
  CreateCounselingPlanPhaseGoalAIGeneratedDTO,
  CreateCounselingPlanPhaseGoalDTO,
  RemoveExerciseFromCounselingPlanPhaseDTO,
  UpdateCounselingPlanDTO,
} from '../api'
import { counselingPlanApi, counselingPlanPhaseApi, counselingPlanPhaseGoalApi } from '../utils/api'

interface CounselingPlanState {
  counselingPlan: CounselingPlanOutputDTO | null

  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: CounselingPlanState = {
  counselingPlan: null,

  status: 'idle',
  error: null,
}

export const getCounselingPlanByPatientId = createAsyncThunk(
  'counselingPlan/getCounselingPlanByPatientId',
  async (patientId: string) => {
    const response = await counselingPlanApi.getCounselingPlanByPatientId(patientId)
    return response.data
  }
)

export const updateCounselingPlan = createAsyncThunk(
  'counselingPlan/updateCounselingPlan',
  async (updateDto: UpdateCounselingPlanDTO) => {
    const response = await counselingPlanApi.updateCounselingPlan(updateDto)
    return response.data
  }
)

export const createCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhase',
  async (phase: CreateCounselingPlanPhaseDTO) => {
    const response = await counselingPlanPhaseApi.createCounselingPlanPhase(phase)
    return response.data
  }
)

export const deleteCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/deleteCounselingPlanPhase',
  async (phaseId: string) => {
    const response = await counselingPlanPhaseApi.deleteCounselingPlanPhase(phaseId)
    return response.data
  }
)

export const getCounselingPlanPhaseById = createAsyncThunk(
  'counselingPlan/getCounselingPlanPhaseById',
  async (phaseId: string) => {
    const response = await counselingPlanPhaseApi.getCounselingPlanPhaseById(phaseId)
    return response.data
  }
)

export const createCounselingPlanPhaseGoal = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhaseGoal',
  async (goal: CreateCounselingPlanPhaseGoalDTO) => {
    const response = await counselingPlanPhaseGoalApi.createCounselingPlanPhaseGoal(goal)
    return response.data
  }
)

export const deleteCounselingPlanPhaseGoal = createAsyncThunk(
  'counselingPlan/deleteCounselingPlanPhaseGoal',
  async (goalId: string) => {
    const response = await counselingPlanPhaseGoalApi.deleteCounselingPlanPhaseGoal(goalId)
    return response.data
  }
)

export const getCounselingPlanPhaseGoalById = createAsyncThunk(
  'counselingPlan/getCounselingPlanPhaseGoalById',
  async (goalId: string) => {
    const response = await counselingPlanPhaseGoalApi.getCounselingPlanPhaseGoalById(goalId)
    return response.data
  }
)

export const addExerciseToCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/addExerciseToCounselingPlanPhase',
  async (dto: AddExerciseToCounselingPlanPhaseDTO) => {
    const response = await counselingPlanPhaseApi.addExerciseToCounselingPlanPhase(dto)
    return response.data
  }
)

export const removeExerciseFromCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/removeExerciseToCounselingPlanPhase',
  async (dto: RemoveExerciseFromCounselingPlanPhaseDTO) => {
    const response = await counselingPlanPhaseApi.removeExerciseFromCounselingPlanPhase(dto)
    return response.data
  }
)

export const createCounselingPlanPhaseAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhaseAIGenerated',
  async (dto: CreateCounselingPlanPhaseAIGeneratedDTO) => {
    const response = await counselingPlanPhaseApi.createCounselingPlanPhaseAIGenerated(dto)
    return response.data
  }
)

export const createCounselingPlanPhaseGoalAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhaseGoalAIGenerated',
  async (dto: CreateCounselingPlanPhaseGoalAIGeneratedDTO) => {
    const response = await counselingPlanPhaseGoalApi.createCounselingPlanPhaseGoalAIGenerated(dto)
    return response.data
  }
)

export const createCounselingPlanExerciseAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanExerciseAIGenerated',
  async (dto: CreateCounselingPlanExerciseAIGeneratedDTO) => {
    const response = await counselingPlanPhaseApi.createCounselingPlanExerciseAIGenerated(dto)
    return response.data
  }
)

const counselingPlanSlice = createSlice({
  name: 'counselingPlan',
  initialState: initialState,
  reducers: {
    setCounselingPlan: (state, action: PayloadAction<CounselingPlanOutputDTO>) => {
      state.counselingPlan = action.payload
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getCounselingPlanByPatientId.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getCounselingPlanByPatientId.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.counselingPlan = action.payload
      })
      .addCase(getCounselingPlanByPatientId.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(updateCounselingPlan.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateCounselingPlan.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.counselingPlan = action.payload
      })
      .addCase(updateCounselingPlan.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })
  },
})

export const { setCounselingPlan } = counselingPlanSlice.actions
export default counselingPlanSlice.reducer
