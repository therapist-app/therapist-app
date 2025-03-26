import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit'
import api from '../utils/api'
import { CreatePatientDTO } from '../dto/input/CreatePatientDTO'
import { PatientOutputDTO } from '../dto/output/PatientOutputDTO'

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
    const response = await api.post(`/patients`, createPatientDTO)
    return response.data
  }
)

export const getPatient = createAsyncThunk('getPatient', async (patientId: string) => {
  const response = await api.get(`/patients/${patientId}`)
  return response.data
})

export const getAllPatientsOfTherapist = createAsyncThunk('getAllPatientsOfTherapist', async () => {
  const response = await api.get(`/patients`)
  return response.data
})

const patientSlice = createSlice({
  name: 'therapist',
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

      .addCase(getPatient.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getPatient.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedPatient = action.payload
      })
      .addCase(getPatient.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
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
