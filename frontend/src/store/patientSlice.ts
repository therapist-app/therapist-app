import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import { CreatePatientDTO, PatientOutputDTO, UpdatePatientDetailDTO } from '../api'
import { patientApi } from '../utils/api'
import { getErrorPayload } from '../utils/errorUtil'

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
  async (createPatientDTO: CreatePatientDTO, thunkAPI) => {
    try {
      const response = await patientApi.createPatientForTherapist(createPatientDTO)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const updatePatient = createAsyncThunk(
  'updatePatient',
  async (
    {
      patientId,
      updatePatientDetailDTO,
    }: {
      patientId: string
      updatePatientDetailDTO: UpdatePatientDetailDTO
    },
    thunkAPI
  ) => {
    try {
      const response = await patientApi.updatePatientDetails(patientId, updatePatientDetailDTO)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const getPatientById = createAsyncThunk(
  'getPatientById',
  async (patientId: string, thunkAPI) => {
    try {
      const response = await patientApi.getPatientById(patientId)
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

export const getAllPatientsOfTherapist = createAsyncThunk(
  'getAllPatientsOfTherapist',
  async (_, thunkAPI) => {
    try {
      const response = await patientApi.getPatientsOfTherapist()
      return response.data
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

const patientSlice = createSlice({
  name: 'patient',
  initialState: initialState,
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
      .addCase(getPatientById.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getPatientById.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedPatient = action.payload
      })
      .addCase(getPatientById.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })
      .addCase(updatePatient.fulfilled, (state, action) => {
        // Update patient in list
        const index = state.allPatientsOfTherapist.findIndex((p) => p.id === action.payload.id)
        if (index !== -1) {
          state.allPatientsOfTherapist[index] = action.payload
        }
        // Optional: also update selectedPatient
        if (state.selectedPatient?.id === action.payload.id) {
          state.selectedPatient = action.payload
        }
      })
  },
})

export const { setSelectedPatient, clearSelectedPatient } = patientSlice.actions
export default patientSlice.reducer
