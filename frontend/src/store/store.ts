import { configureStore } from '@reduxjs/toolkit'

import chatbotTemplateDocumentReducer from './chatbotTemplateDocumentSlice'
import chatbotTemplateReducer from './chatbotTemplateSlice'
import conversationReducer from './conversationSlice'
import counselingPlanReducer from './counselingPlanSlice'
import errorReducer from './errorSlice'
import exerciseReducer from './exerciseSlice'
import journalEntryReducer from './journalEntrySlice'
import meetingReducer from './meetingSlice'
import patientDocumentReducer from './patientDocumentSlice'
import patientReducer from './patientSlice'
import therapistChatbotReducer, {
  therapistChatbotSaveMessagesToLocalStorageMiddleware,
} from './therapistChatbotSlice'
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
    conversation: conversationReducer,
    globalError: errorReducer,
    journalEntry: journalEntryReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(therapistChatbotSaveMessagesToLocalStorageMiddleware),
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
