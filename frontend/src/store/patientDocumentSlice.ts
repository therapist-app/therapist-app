import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { PatientDocumentOutputDTO } from '../dto/output/PatientDocumentOutputDTO'
import api from '../utils/api'

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
    const formData = new FormData()
    formData.append('file', props.file)
    const response = await api.post(`/patient-documents/${props.patientId}`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
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
  },
})

export default patientDocumentSlice.reducer
