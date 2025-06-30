import { configureStore } from '@reduxjs/toolkit'

import chatbotTemplateDocumentReducer from './chatbotTemplateDocumentSlice'
import chatbotTemplateReducer from './chatbotTemplateSlice'
import counselingPlanReducer from './counselingPlanSlice'
import exerciseReducer from './exerciseSlice'
import meetingReducer from './meetingSlice'
import patientDocumentReducer from './patientDocumentSlice'
import patientReducer from './patientSlice'
import therapistChatbotReducer from './therapistChatbotSlice'
import therapistDocumentReducer from './therapistDocumentSlice'
import therapistReducer from './therapistSlice'

const store = configureStore({
  reducer: {
    therapist: therapistReducer,
    patient: patientReducer,
    patientDocument: patientDocumentReducer,
    meeting: meetingReducer,
    chatbotTemplate: chatbotTemplateReducer,
    exercise: exerciseReducer,
    therapistDocument: therapistDocumentReducer,
    therapistChatbot: therapistChatbotReducer,
    counselingPlan: counselingPlanReducer,
    chatbotTemplateDocument: chatbotTemplateDocumentReducer,
  },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
