import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

import { patientDocumentApi } from '../utils/api'
import { PatientDocumentOutputDTO } from '../api'

interface PatientDocumentState {
  selectedPatientDocument: PatientDocumentOutputDTO | null
  allPatientDocumentsOfPatient: PatientDocumentOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: PatientDocumentState = {
  selectedPatientDocument: null,
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

const patientDocumentSlice = createSlice({
  name: 'patientDocument',
  initialState,
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
  },
})

export default patientDocumentSlice.reducer
