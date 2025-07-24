import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { AlertColor } from '@mui/material'

export interface GlobalErrorState {
  open: boolean
  message: string
  severity: AlertColor
}

const initialState: GlobalErrorState = {
  open: false,
  message: '',
  severity: 'error',
}

const errorSlice = createSlice({
  name: 'globalError',
  initialState,
  reducers: {
    showError: (
      state,
      action: PayloadAction<{ message: string; severity?: AlertColor }>
    ) => {
      state.open = true
      state.message = action.payload.message
      state.severity = action.payload.severity ?? 'error'
    },
    hideError: (state) => {
      state.open = false
      state.message = ''
    },
  },
})

export const { showError, hideError } = errorSlice.actions
export default errorSlice.reducer
