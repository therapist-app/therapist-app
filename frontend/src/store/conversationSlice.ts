import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'
import { conversationApi } from '../utils/api'

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

export const fetchConversationSummary = createAsyncThunk<
  { patientId: string; summary: string },                       
  { patientId: string; start: string; end: string },            
  { rejectValue: string }
>('conversation/fetchConversationSummary', async ({ patientId, start, end }, thunkAPI) => {
  try {
    const { data } = await conversationApi.getConversationSummary(patientId, start, end)
    return { patientId, summary: data.conversationSummary ?? '' }
  } catch (err: any) {
    return thunkAPI.rejectWithValue(err?.message ?? 'error fetching summary')
  }
})

const conversationSlice = createSlice({
  name: 'conversation',
  initialState,
  reducers: {
    clearConversationSummary(state, action: PayloadAction<string | void>) {
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
