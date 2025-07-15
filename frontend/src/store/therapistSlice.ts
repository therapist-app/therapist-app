import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  CreateTherapistDTO,
  LoginTherapistDTO,
  TherapistOutputDTO,
  UpdateTherapistDTO,
} from '../api'
import { therapistApi } from '../utils/api'

interface TherapistState {
  loggedInTherapist: TherapistOutputDTO | null
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapistState = {
  loggedInTherapist: null,
  status: 'idle',
  error: null,
}

export const registerTherapist = createAsyncThunk(
  'registerTherapist',
  async (createTherapistDTO: CreateTherapistDTO) => {
    const response = await therapistApi.createTherapist(createTherapistDTO)
    return response.data
  }
)

export const loginTherapist = createAsyncThunk(
  'loginTherapist',
  async (loginTherapistDTO: LoginTherapistDTO) => {
    const response = await therapistApi.loginTherapist(loginTherapistDTO)
    return response.data
  }
)

export const logoutTherapist = createAsyncThunk('logoutTherapist', async () => {
  await therapistApi.logoutTherapist()
})

export const getCurrentlyLoggedInTherapist = createAsyncThunk(
  'getCurrentlyLoggedInTherapist',
  async () => {
    const response = await therapistApi.getCurrentlyLoggedInTherapist()
    return response.data
  }
)

export const updateTherapist = createAsyncThunk(
  'updateTherapist',
  async (updateDto: UpdateTherapistDTO) => {
    const response = await therapistApi.updateTherapist(updateDto)
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

      .addCase(updateTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(updateTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const { setLoggedInTherapist, clearLoggedInTherapist } = therapistSlice.actions
export default therapistSlice.reducer
