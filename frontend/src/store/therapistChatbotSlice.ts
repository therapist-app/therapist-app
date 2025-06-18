import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  CreateTherapistDTO,
  LoginTherapistDTO,
  TherapistChatCompletionOutputDTO,
  TherapistOutputDTO,
} from '../api'
import { therapistApi } from '../utils/api'

interface TherapistChatbotState {
  messages: TherapistChatCompletionOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapistChatbotState = {
  messages: [],
  status: 'idle',
  error: null,
}

export const sendChat = createAsyncThunk(
  'registerTherapist',
  async (createTherapistDTO: CreateTherapistDTO) => {
    const response = await therapistApi.createTherapist(createTherapistDTO)
    return response.data
  }
)

const therapistSlice = createSlice({
  name: 'therapist',
  initialState: initialState,
  reducers: {
    setLoggedInTherapist: (state, action: PayloadAction<TherapistOutputDTO>) => {
      state.loggedInTherapist = action.payload
    },
    clearLoggedInTherapist: (state) => {
      state.loggedInTherapist = null
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(registerTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(registerTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(loginTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(loginTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(loginTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(logoutTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(logoutTherapist.fulfilled, (state) => {
        state.status = 'succeeded'
        state.loggedInTherapist = null
      })
      .addCase(logoutTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(getCurrentlyLoggedInTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getCurrentlyLoggedInTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(getCurrentlyLoggedInTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const { setLoggedInTherapist, clearLoggedInTherapist } = therapistSlice.actions
export default therapistSlice.reducer
