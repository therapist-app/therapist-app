import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { TherapistOutputDTO } from '../dto/output/TherapistOutputDTO'

interface TherapistState {
  loggedInTherapist: TherapistOutputDTO | null
}

const initialState: TherapistState = {
  loggedInTherapist: null,
}

const therapistSlice = createSlice({
  name: 'therapist',
  initialState,
  reducers: {
    setLoggedInTherapist: (state, action: PayloadAction<TherapistOutputDTO>) => {
      state.loggedInTherapist = action.payload
    },
    clearLoggedInTherapist: (state) => {
      state.loggedInTherapist = null
    },
  },
})

export const { setLoggedInTherapist, clearLoggedInTherapist } = therapistSlice.actions
export default therapistSlice.reducer
