import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

import {
  CoachGetAllJournalEntriesDTOPatientAPI,
  CoachJournalEntryOutputDTOPatientAPI,
} from '../api'
import { journalEntryApi } from '../utils/api'
import { getErrorPayload } from '../utils/errorUtil'

interface JournalEntryState {
  allJournalEntries: CoachGetAllJournalEntriesDTOPatientAPI[]
  selectedJournalEntry: CoachJournalEntryOutputDTOPatientAPI | undefined
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: JournalEntryState = {
  allJournalEntries: [],
  selectedJournalEntry: undefined,
  status: 'idle',
  error: null,
}
export const listAllJournalEntries = createAsyncThunk(
  'listAllJournalEntries',
  async (patientId: string, thunkAPI) => {
    try {
      const response = await journalEntryApi.listAllJournalEntries(patientId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const getOneJournalEntry = createAsyncThunk(
  'getOneJournalEntry',
  async (dto: { patientId: string; journalEntryId: string }, thunkAPI) => {
    try {
      const response = await await journalEntryApi.getOneJournalEntry(
        dto.patientId,
        dto.journalEntryId
      )
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

const journalEntrySlice = createSlice({
  name: 'journalEntry',
  initialState: initialState,
  reducers: {
    clearSelectedJournalEntry: (state) => {
      state.selectedJournalEntry = undefined
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(listAllJournalEntries.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(listAllJournalEntries.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.allJournalEntries = action.payload
      })
      .addCase(listAllJournalEntries.rejected, (state, action) => {
        state.status = 'failed'
        state.error =
          action.meta.rejectedWithValue && typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
      })

      .addCase(getOneJournalEntry.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getOneJournalEntry.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedJournalEntry = action.payload
      })
      .addCase(getOneJournalEntry.rejected, (state, action) => {
        state.status = 'failed'
        state.error =
          action.meta.rejectedWithValue && typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
      })
  },
})

export const { clearSelectedJournalEntry } = journalEntrySlice.actions
export default journalEntrySlice.reducer
