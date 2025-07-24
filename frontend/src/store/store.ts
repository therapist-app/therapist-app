import { configureStore } from '@reduxjs/toolkit'

import chatbotTemplateDocumentReducer from './chatbotTemplateDocumentSlice'
import chatbotTemplateReducer from './chatbotTemplateSlice'
import conversationReducer from './conversationSlice'
import counselingPlanReducer from './counselingPlanSlice'
import exerciseReducer from './exerciseSlice'
import meetingReducer from './meetingSlice'
import patientDocumentReducer from './patientDocumentSlice'
import patientReducer from './patientSlice'
import therapistChatbotReducer from './therapistChatbotSlice'
import therapistDocumentReducer from './therapistDocumentSlice'
import therapistReducer from './therapistSlice'
import errorReducer from './errorSlice'

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
    conversation: conversationReducer,
    globalError: errorReducer,
  },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
