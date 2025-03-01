import { configureStore } from '@reduxjs/toolkit'
import therapistReducer from './therapistSlice'

const store = configureStore({
  reducer: { therapist: therapistReducer },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
