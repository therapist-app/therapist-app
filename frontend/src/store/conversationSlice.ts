import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'
import type { AxiosError } from 'axios'

import { conversationApi } from '../utils/api'

export const PRIVATE_MESSAGE = 'The conversation of the client with the chatbot is private.'

interface ConversationState {
  byPatient: Record<string, string>
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: ConversationState = {
  byPatient: {},
  status: 'idle',
  error: null,
}

function getStatus(e: unknown): number | undefined {
  const err = e as Partial<{ response: { status?: number } }>
  return err.response?.status
}

export const fetchConversationSummary = createAsyncThunk<
  { patientId: string; summary: string },
  { patientId: string; start: string; end: string },
  { rejectValue: string }
>('conversation/fetchConversationSummary', async ({ patientId, start, end }, thunkAPI) => {
  try {
    const { data } = await conversationApi.getConversationSummary(patientId, start, end)
    return { patientId: patientId, summary: data.conversationSummary ?? '' }
  } catch (err: unknown) {
    const status = getStatus(err)
    if (status === 500) {
      return thunkAPI.rejectWithValue(PRIVATE_MESSAGE)
    }

    const axiosErr = err as AxiosError<{ message?: string }>
    const message =
      axiosErr.response?.data?.message ??
      axiosErr.message ??
      (err instanceof Error ? err.message : 'error fetching summary')

    return thunkAPI.rejectWithValue(message)
  }
})

const conversationSlice = createSlice({
  name: 'conversation',
  initialState: initialState,
  reducers: {
    clearConversationSummary: function (state, action: PayloadAction<string | void>) {
      if (action.payload) {
        delete state.byPatient[action.payload]
      } else {
        state.byPatient = {}
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchConversationSummary.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(fetchConversationSummary.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.byPatient[action.payload.patientId] = action.payload.summary
      })
      .addCase(fetchConversationSummary.rejected, (state, action) => {
        state.status = 'failed'
        state.error =
          action.payload ?? action.error.message ?? 'Something went wrong while fetching summary'
      })
  },
})

export const { clearConversationSummary } = conversationSlice.actions
export default conversationSlice.reducer
