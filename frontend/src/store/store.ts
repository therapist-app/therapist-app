import { configureStore } from '@reduxjs/toolkit'
import therapistReducer from './therapistSlice'
import patientReducer from './patientSlice'
import therapySessionReducer from './therapySessionSlice'
import chatbotTemplateReducer from './chatbotTemplateSlice'

const store = configureStore({
  reducer: {
    therapist: therapistReducer,
    patient: patientReducer,
    therapySession: therapySessionReducer,
    chatbotTemplate: chatbotTemplateReducer,
  },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
