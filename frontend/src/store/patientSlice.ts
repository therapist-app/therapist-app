import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit'
import { CreatePatientDTO, PatientOutputDTO } from '../api'
import { patientApi } from '../utils/api'

interface PatientState {
  selectedPatient: PatientOutputDTO | null
  allPatientsOfTherapist: PatientOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: PatientState = {
  selectedPatient: null,
  allPatientsOfTherapist: [],
  status: 'idle',
  error: null,
}

export const registerPatient = createAsyncThunk(
  'registerPatient',
  async (createPatientDTO: CreatePatientDTO) => {
    const response = await patientApi.createPatientForTherapist(createPatientDTO)
    return response.data
  }
)

export const getAllPatientsOfTherapist = createAsyncThunk('getAllPatientsOfTherapist', async () => {
  const response = await patientApi.getPatientsOfTherapist()
  return response.data
})

const patientSlice = createSlice({
  name: 'patient',
  initialState,
  reducers: {
    setSelectedPatient: (state, action: PayloadAction<PatientOutputDTO>) => {
      state.selectedPatient = action.payload
    },
    setAllPatientsOfTherapist: (state, action: PayloadAction<PatientOutputDTO[]>) => {
      state.allPatientsOfTherapist = action.payload
    },
    clearSelectedPatient: (state) => {
      state.selectedPatient = null
    },
    clearAllPatientsOfTherapist: (state) => {
      state.allPatientsOfTherapist = []
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerPatient.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(registerPatient.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedPatient = action.payload
      })
      .addCase(registerPatient.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(getAllPatientsOfTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getAllPatientsOfTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.allPatientsOfTherapist = action.payload
      })
      .addCase(getAllPatientsOfTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })
  },
})

export const { setSelectedPatient, clearSelectedPatient } = patientSlice.actions
export default patientSlice.reducer
