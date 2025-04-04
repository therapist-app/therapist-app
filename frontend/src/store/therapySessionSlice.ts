import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'
import { TherapySessionOutputDTO } from '../dto/output/TherapySessionOutputDTO'
import { CreateTherapySessionDTO } from '../dto/input/CreateTherapySessionDTO'
import api from '../utils/api'

interface TherapistState {
  selectedTherapySession: TherapySessionOutputDTO | null
  allTherapySessionsOfPatient: TherapySessionOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapistState = {
  selectedTherapySession: null,
  allTherapySessionsOfPatient: [],
  status: 'idle',
  error: null,
}

export const createTherapySession = createAsyncThunk(
  'createTherapySession',
  async (createTherapySessionDTO: CreateTherapySessionDTO) => {
    const response = await api.post(`/therapy-sessions`, createTherapySessionDTO)
    return response.data
  }
)

export const getTherapySession = createAsyncThunk(
  'getTherapySession',
  async (therapySessionId: string) => {
    const response = await api.get(`/therapy-sessions/${therapySessionId}`)
    return response.data
  }
)

export const getAllTherapySessionsOfPatient = createAsyncThunk(
  'getAllTherapySessionsOfPatient',
  async (patientId: string) => {
    const response = await api.get(`/therapy-sessions/patients/${patientId}`)
    return response.data
  }
)

const therapySessionSlice = createSlice({
  name: 'therapySession',
  initialState,
  reducers: {
    setSelectedTherapySession: (state, action: PayloadAction<TherapySessionOutputDTO>) => {
      state.selectedTherapySession = action.payload
    },
    setAllTherapySessionsOfPatient: (state, action: PayloadAction<TherapySessionOutputDTO[]>) => {
      state.allTherapySessionsOfPatient = action.payload
    },
    clearSelectedTherapySession: (state) => {
      state.selectedTherapySession = null
    },
    clearAllTherapySessionsOfPatient: (state) => {
      state.allTherapySessionsOfPatient = []
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(createTherapySession.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createTherapySession.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedTherapySession = action.payload
      })
      .addCase(createTherapySession.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(getTherapySession.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getTherapySession.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedTherapySession = action.payload
      })
      .addCase(getTherapySession.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(getAllTherapySessionsOfPatient.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getAllTherapySessionsOfPatient.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.allTherapySessionsOfPatient = action.payload
      })
      .addCase(getAllTherapySessionsOfPatient.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const {
  setSelectedTherapySession,
  setAllTherapySessionsOfPatient,
  clearSelectedTherapySession,
  clearAllTherapySessionsOfPatient,
} = therapySessionSlice.actions
export default therapySessionSlice.reducer
