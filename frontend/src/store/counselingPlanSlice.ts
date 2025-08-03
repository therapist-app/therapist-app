import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  AddExerciseToCounselingPlanPhaseDTO,
  CounselingPlanOutputDTO,
  CounselingPlanPhaseGoalOutputDTO,
  CreateCounselingPlanExerciseAIGeneratedDTO,
  CreateCounselingPlanPhaseAIGeneratedDTO,
  CreateCounselingPlanPhaseDTO,
  CreateCounselingPlanPhaseGoalAIGeneratedDTO,
  CreateCounselingPlanPhaseGoalDTO,
  RemoveExerciseFromCounselingPlanPhaseDTO,
  UpdateCounselingPlanDTO,
  UpdateCounselingPlanPhaseDTO,
  UpdateCounselingPlanPhaseGoalDTO,
} from '../api'
import { counselingPlanApi, counselingPlanPhaseApi, counselingPlanPhaseGoalApi } from '../utils/api'
import { getErrorPayload } from '../utils/errorUtil'

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
  async (patientId: string, thunkAPI) => {
    try {
      const response = await counselingPlanApi.getCounselingPlanByPatientId(patientId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const updateCounselingPlan = createAsyncThunk(
  'counselingPlan/updateCounselingPlan',
  async (updateDto: UpdateCounselingPlanDTO, thunkAPI) => {
    try {
      const response = await counselingPlanApi.updateCounselingPlan(updateDto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const createCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhase',
  async (phase: CreateCounselingPlanPhaseDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.createCounselingPlanPhase(phase)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const updateCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/updateCounselingPlanPhase',
  async (dto: UpdateCounselingPlanPhaseDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.updateCounselingPlanPhase(dto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const deleteCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/deleteCounselingPlanPhase',
  async (phaseId: string, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.deleteCounselingPlanPhase(phaseId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const getCounselingPlanPhaseById = createAsyncThunk(
  'counselingPlan/getCounselingPlanPhaseById',
  async (phaseId: string, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.getCounselingPlanPhaseById(phaseId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const createCounselingPlanPhaseGoal = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhaseGoal',
  async (goal: CreateCounselingPlanPhaseGoalDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseGoalApi.createCounselingPlanPhaseGoal(goal)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const updateCounselingPlanPhaseGoal = createAsyncThunk(
  'counselingPlan/updateCounselingPlanPhaseGoal',
  async (dto: UpdateCounselingPlanPhaseGoalDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseGoalApi.updateCounselingPlanPhaseGoal(dto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const deleteCounselingPlanPhaseGoal = createAsyncThunk(
  'counselingPlan/deleteCounselingPlanPhaseGoal',
  async (goalId: string, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseGoalApi.deleteCounselingPlanPhaseGoal(goalId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const getCounselingPlanPhaseGoalById = createAsyncThunk(
  'counselingPlan/getCounselingPlanPhaseGoalById',
  async (goalId: string, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseGoalApi.getCounselingPlanPhaseGoalById(goalId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const addExerciseToCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/addExerciseToCounselingPlanPhase',
  async (dto: AddExerciseToCounselingPlanPhaseDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.addExerciseToCounselingPlanPhase(dto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const removeExerciseFromCounselingPlanPhase = createAsyncThunk(
  'counselingPlan/removeExerciseToCounselingPlanPhase',
  async (dto: RemoveExerciseFromCounselingPlanPhaseDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.removeExerciseFromCounselingPlanPhase(dto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const createCounselingPlanPhaseAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhaseAIGenerated',
  async (dto: CreateCounselingPlanPhaseAIGeneratedDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.createCounselingPlanPhaseAIGenerated(dto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const createCounselingPlanPhaseGoalAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanPhaseGoalAIGenerated',
  async (dto: CreateCounselingPlanPhaseGoalAIGeneratedDTO, thunkAPI) => {
    try {
      const response =
        await counselingPlanPhaseGoalApi.createCounselingPlanPhaseGoalAIGenerated(dto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const createCounselingPlanExerciseAIGenerated = createAsyncThunk(
  'counselingPlan/createCounselingPlanExerciseAIGenerated',
  async (dto: CreateCounselingPlanExerciseAIGeneratedDTO, thunkAPI) => {
    try {
      const response = await counselingPlanPhaseApi.createCounselingPlanExerciseAIGenerated(dto)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
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
  selectors: {
    getGoalCounts: (state) => {
      if (!state.counselingPlan?.counselingPlanPhasesOutputDTO) {
        return { totalGoals: 0, completedGoals: 0 }
      }

      const allGoals: CounselingPlanPhaseGoalOutputDTO[] =
        state.counselingPlan.counselingPlanPhasesOutputDTO.flatMap(
          (phase) => phase.phaseGoalsOutputDTO || []
        )

      const totalGoals = allGoals.length

      const completedGoals = allGoals.filter((goal) => goal.isCompleted).length

      return { totalGoals: totalGoals, completedGoals: completedGoals }
    },
    getCurrentPhase: (state) => {
      return state.counselingPlan?.counselingPlanPhasesOutputDTO?.find(
        (phase) =>
          new Date(phase.startDate ?? '').getTime() < new Date().getTime() &&
          new Date(phase.endDate ?? '').getTime() > new Date().getTime()
      )
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
export const { getGoalCounts, getCurrentPhase } = counselingPlanSlice.selectors
export default counselingPlanSlice.reducer
