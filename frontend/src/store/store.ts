import { configureStore } from '@reduxjs/toolkit'

import chatbotTemplateReducer from './chatbotTemplateSlice'
import exerciseReducer from './exerciseSlice'
import patientDocumentReducer from './patientDocumentSlice'
import patientReducer from './patientSlice'
import therapistReducer from './therapistSlice'
import therapySessionReducer from './therapySessionSlice'

const store = configureStore({
  reducer: {
    therapist: therapistReducer,
    patient: patientReducer,
    patientDocument: patientDocumentReducer,
    therapySession: therapySessionReducer,
    chatbotTemplate: chatbotTemplateReducer,
    exercise: exerciseReducer,
  },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
