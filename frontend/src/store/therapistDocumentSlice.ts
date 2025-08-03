import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

import { TherapistDocumentOutputDTO } from '../api'
import { therapistDocumentApi } from '../utils/api'
import { getErrorPayload } from '../utils/errorUtil'

interface TherapistDocumentState {
  allTherapistDocumentsOfTherapist: TherapistDocumentOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapistDocumentState = {
  allTherapistDocumentsOfTherapist: [],
  status: 'idle',
  error: null,
}

export const createDocumentForTherapist = createAsyncThunk(
  'createDocumentForTherapist',
  async (file: File) => {
    const response = await therapistDocumentApi.createTherapistDocument(file)
    return response.data
  }
)

export const getAllTherapistDocumentsOfTherapist = createAsyncThunk(
  'getAllTherapistDocumentsOfTherapist',
  async (_, thunkAPI) => {
    try {
      const response = await therapistDocumentApi.getDocumentsOfTherapist()
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const deleteDocumentOfTherapist = createAsyncThunk(
  'deleteDocumentOfTherapist',
  async (therapistDocumentId: string, thunkAPI) => {
    try {
      const response = await therapistDocumentApi.deleteTherapistDocument(therapistDocumentId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

const therapistDocumentSlice = createSlice({
  name: 'therapistDocument',
  initialState: initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(createDocumentForTherapist.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })

    builder.addCase(createDocumentForTherapist.fulfilled, (state, action) => {
      state.status = 'succeeded'
      console.log(action)
    })

    builder.addCase(createDocumentForTherapist.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message || 'Something went wrong'
      console.log(action)
    })

    builder.addCase(getAllTherapistDocumentsOfTherapist.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })

    builder.addCase(getAllTherapistDocumentsOfTherapist.fulfilled, (state, action) => {
      state.status = 'succeeded'
      state.allTherapistDocumentsOfTherapist = action.payload
    })

    builder.addCase(getAllTherapistDocumentsOfTherapist.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message || 'Something went wrong'
      console.log(action)
    })

    builder.addCase(deleteDocumentOfTherapist.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })

    builder.addCase(deleteDocumentOfTherapist.fulfilled, (state, action) => {
      state.status = 'succeeded'
      console.log(action)
    })

    builder.addCase(deleteDocumentOfTherapist.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message || 'Something went wrong'
      console.log(action)
    })
  },
})

export default therapistDocumentSlice.reducer
