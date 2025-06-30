import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  AddExerciseToCounselingPlanPhaseDTO,
  CounselingPlanExerciseAIGeneratedOutputDTO,
  CounselingPlanOutputDTO,
  CreateCounselingPlanPhaseDTO,
  CreateCounselingPlanPhaseGoalDTO,
  RemoveExerciseFromCounselingPlanPhaseDTO,
} from '../api'
import { counselingPlanApi, counselingPlanPhaseApi, counselingPlanPhaseGoalApi } from '../utils/api'

export enum CounselingPlanExerciseStateEnum {
  ADDING_EXERCISE = 'ADDING_EXERCISE',
  CREATING_EXERCISE = 'CREATING_EXERCISE',
  IDLE = 'IDLE',
}

interface CounselingPlanState {
  counselingPlan: CounselingPlanOutputDTO | null
  counselingPlanExerciseAIGeneratedOutputDTO: CounselingPlanExerciseAIGeneratedOutputDTO | null
  counselingPlanExerciseStateEnum: CounselingPlanExerciseStateEnum
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: CounselingPlanState = {
  counselingPlan: null,
  counselingPlanExerciseAIGeneratedOutputDTO: null,
  counselingPlanExerciseStateEnum: CounselingPlanExerciseStateEnum.IDLE,
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
  async (counselingPlanId: string) => {
    const response =
      await counselingPlanPhaseApi.createCounselingPlanPhaseAIGenerated(counselingPlanId)
    return response.data
  }
)

export const createCounselingPlanPhaseGoalAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhaseGoalAIGenerated',
  async (counselingPlanPhaseId: string) => {
    const response =
      await counselingPlanPhaseGoalApi.createCounselingPlanPhaseGoalAIGenerated(
        counselingPlanPhaseId
      )
    return response.data
  }
)

export const createCounselingPlanExerciseAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanExerciseAIGenerated',
  async (counselingPlanPhaseId: string) => {
    const response =
      await counselingPlanPhaseApi.createCounselingPlanExerciseAIGenerated(counselingPlanPhaseId)
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
    clearCreateCounselingPlanExerciseAIGenerated: (state) => {
      state.counselingPlanExerciseAIGeneratedOutputDTO = null
    },
    setCounselingPlanExerciseStateEnum: (
      state,
      action: PayloadAction<CounselingPlanExerciseStateEnum>
    ) => {
      state.counselingPlanExerciseStateEnum = action.payload
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

      .addCase(createCounselingPlanExerciseAIGenerated.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createCounselingPlanExerciseAIGenerated.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.counselingPlanExerciseAIGeneratedOutputDTO = action.payload
      })
      .addCase(createCounselingPlanExerciseAIGenerated.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })
  },
})

export const {
  setCounselingPlan,
  clearCreateCounselingPlanExerciseAIGenerated,
  setCounselingPlanExerciseStateEnum,
} = counselingPlanSlice.actions
export default counselingPlanSlice.reducer
