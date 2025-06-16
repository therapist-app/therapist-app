import axios from 'axios'

import {
  ChatbotTemplateControllerApiFactory,
  ChatControllerApiFactory,
  ExerciseComponentControllerApiFactory,
  ExerciseControllerApiFactory,
  MeetingControllerApiFactory,
  MeetingNoteControllerApiFactory,
  PatientControllerApiFactory,
  PatientDocumentControllerApiFactory,
  PatientTestControllerApiFactory,
  TherapistControllerApiFactory,
  TherapistDocumentControllerApiFactory,
} from '../api'

const baseURL: string = import.meta.env.VITE_API_BASE_URL

const api = axios.create({
  baseURL: baseURL,
  timeout: 60000,
  withCredentials: true,
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export const chatbotTemplateApi = ChatbotTemplateControllerApiFactory(undefined, baseURL, api)
export const patientApi = PatientControllerApiFactory(undefined, baseURL, api)
export const patientDocumentApi = PatientDocumentControllerApiFactory(undefined, baseURL, api)
export const patientTestApi = PatientTestControllerApiFactory(undefined, baseURL, api)
export const therapistApi = TherapistControllerApiFactory(undefined, baseURL, api)
export const therapistDocumentApi = TherapistDocumentControllerApiFactory(undefined, baseURL, api)
export const meetingApi = MeetingControllerApiFactory(undefined, baseURL, api)
export const meetingNoteApi = MeetingNoteControllerApiFactory(undefined, baseURL, api)
export const exerciseApi = ExerciseControllerApiFactory(undefined, baseURL, api)
export const exerciseComponentApi = ExerciseComponentControllerApiFactory(undefined, baseURL, api)
export const chatApi = ChatControllerApiFactory(undefined, baseURL, api)
