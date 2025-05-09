import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

import { PatientDocumentOutputDTO } from '../api'
import { patientDocumentApi } from '../utils/api'

interface PatientDocumentState {
  allPatientDocumentsOfPatient: PatientDocumentOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: PatientDocumentState = {
  allPatientDocumentsOfPatient: [],
  status: 'idle',
  error: null,
}

export const createDocumentForPatient = createAsyncThunk(
  'createDocumentForPatient',
  async (props: { file: File; patientId: string }) => {
    const response = await patientDocumentApi.createPatientDocument(props.patientId, props.file)
    return response.data
  }
)

export const getAllPatientDocumentsOfPatient = createAsyncThunk(
  'getAllPatientDocumentsOfPatient',
  async (patientId: string) => {
    const response = await patientDocumentApi.getDocumentsOfPatient(patientId)
    return response.data
  }
)

export const deleteDocumentOfPatient = createAsyncThunk(
  'deleteDocumentOfPatient',
  async (patientDocumentId: string) => {
    const response = await patientDocumentApi.deleteFile(patientDocumentId)
    return response.data
  }
)

const patientDocumentSlice = createSlice({
  name: 'patientDocument',
  initialState: initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(createDocumentForPatient.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })

    builder.addCase(createDocumentForPatient.fulfilled, (state, action) => {
      state.status = 'succeeded'
      console.log(action)
    })

    builder.addCase(createDocumentForPatient.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message || 'Something went wrong'
      console.log(action)
    })

    builder.addCase(getAllPatientDocumentsOfPatient.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })

    builder.addCase(getAllPatientDocumentsOfPatient.fulfilled, (state, action) => {
      state.status = 'succeeded'
      state.allPatientDocumentsOfPatient = action.payload
    })

    builder.addCase(getAllPatientDocumentsOfPatient.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message || 'Something went wrong'
      console.log(action)
    })

    builder.addCase(deleteDocumentOfPatient.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })

    builder.addCase(deleteDocumentOfPatient.fulfilled, (state, action) => {
      state.status = 'succeeded'
      console.log(action)
    })

    builder.addCase(deleteDocumentOfPatient.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message || 'Something went wrong'
      console.log(action)
    })
  },
})

export default patientDocumentSlice.reducer
