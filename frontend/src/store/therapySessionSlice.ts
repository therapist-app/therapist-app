import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  CreateTherapySessionDTO,
  CreateTherapySessionNoteDTO,
  TherapySessionOutputDTO,
  UpdateTherapySessionNoteDTO,
} from '../api'
import { therapySessionApi, therapySessionNoteApi } from '../utils/api'

interface TherapySessionState {
  selectedTherapySession: TherapySessionOutputDTO | null
  allTherapySessionsOfPatient: TherapySessionOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapySessionState = {
  selectedTherapySession: null,
  allTherapySessionsOfPatient: [],
  status: 'idle',
  error: null,
}

export const createTherapySession = createAsyncThunk(
  'createTherapySession',
  async (createTherapySessionDTO: CreateTherapySessionDTO) => {
    const response = await therapySessionApi.createSession(createTherapySessionDTO)
    return response.data
  }
)

export const getTherapySession = createAsyncThunk(
  'getTherapySession',
  async (therapySessionId: string) => {
    const response = await therapySessionApi.getTherapySessionById(therapySessionId)
    return response.data
  }
)

export const getAllTherapySessionsOfPatient = createAsyncThunk(
  'getAllTherapySessionsOfPatient',
  async (patientId: string) => {
    const response = await therapySessionApi.getTherapySessionsOfPatient(patientId)
    return response.data
  }
)

export const deleteTherapySession = createAsyncThunk(
  'deleteTherapySession',
  async (patientId: string) => {
    const response = await therapySessionApi.deleteTherapySessionById(patientId)
    return response.data
  }
)

export const createTherapySessionNote = createAsyncThunk(
  'createTherapySessionNote',
  async (createTherapySessionNoteDTO: CreateTherapySessionNoteDTO) => {
    const response = await therapySessionNoteApi.createTherapySessionNote(
      createTherapySessionNoteDTO
    )
    return response.data
  }
)

export const updateTherapySessionNote = createAsyncThunk(
  'updateTherapySessionNote',
  async (updateTherapySessionNoteDTO: UpdateTherapySessionNoteDTO) => {
    const response = await therapySessionNoteApi.updateTherapySessionNote(
      updateTherapySessionNoteDTO
    )
    return response.data
  }
)

export const deleteTherapySessionNote = createAsyncThunk(
  'deleteTherapySessionNote',
  async (therapySessionNoteId: string) => {
    await therapySessionNoteApi.deleteTherapySessionById1(therapySessionNoteId)
  }
)

const therapySessionSlice = createSlice({
  name: 'therapySession',
  initialState: initialState,
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

      .addCase(deleteTherapySession.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteTherapySession.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteTherapySession.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(createTherapySessionNote.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createTherapySessionNote.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(createTherapySessionNote.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(updateTherapySessionNote.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateTherapySessionNote.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(updateTherapySessionNote.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteTherapySessionNote.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteTherapySessionNote.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteTherapySessionNote.rejected, (state, action) => {
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
