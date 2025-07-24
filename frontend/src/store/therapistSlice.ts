import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  CreateTherapistDTO,
  LoginTherapistDTO,
  TherapistOutputDTO,
  UpdateTherapistDTO,
} from '../api'
import { therapistApi } from '../utils/api'
import { handleError } from '../utils/handleError'

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

export const registerTherapist = createAsyncThunk<
  TherapistOutputDTO,
  CreateTherapistDTO,
  { rejectValue: string }
>('therapist/registerTherapist', async (dto, { rejectWithValue }) => {
  try {
    const { data } = await therapistApi.createTherapist(dto)
    return data
  } catch (err) {
    return rejectWithValue(await handleError(err))
  }
})

export const loginTherapist = createAsyncThunk<
  TherapistOutputDTO,
  LoginTherapistDTO,
  { rejectValue: string }
>('therapist/loginTherapist', async (dto, { rejectWithValue }) => {
  try {
    const { data } = await therapistApi.loginTherapist(dto)
    return data
  } catch (err) {
    return rejectWithValue(await handleError(err))
  }
})

export const logoutTherapist = createAsyncThunk<void, void, { rejectValue: string }>(
  'therapist/logoutTherapist',
  async (_, { rejectWithValue }) => {
    try {
      await therapistApi.logoutTherapist()
    } catch (err) {
      return rejectWithValue(await handleError(err))
    }
  }
)

export const getCurrentlyLoggedInTherapist = createAsyncThunk<
  TherapistOutputDTO,
  void,
  { rejectValue: string }
>('therapist/getCurrentlyLoggedInTherapist', async (_, { rejectWithValue }) => {
  try {
    const { data } = await therapistApi.getCurrentlyLoggedInTherapist()
    return data
  } catch (err) {
    return rejectWithValue(await handleError(err))
  }
})

export const updateTherapist = createAsyncThunk<
  TherapistOutputDTO,
  UpdateTherapistDTO,
  { rejectValue: string }
>('therapist/updateTherapist', async (dto, { rejectWithValue }) => {
  try {
    const { data } = await therapistApi.updateTherapist(dto)
    return data
  } catch (err) {
    return rejectWithValue(await handleError(err))
  }
})

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
        state.error =
          action.meta.rejectedWithValue && typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
      })

      // login
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
        state.error =
          action.meta.rejectedWithValue && typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
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
        state.error =
          action.meta.rejectedWithValue && typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
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
        state.error =
          action.meta.rejectedWithValue && typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
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
        state.error =
          action.meta.rejectedWithValue && typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
      })
  },
})

export const { setLoggedInTherapist, clearLoggedInTherapist } = therapistSlice.actions
export default therapistSlice.reducer
