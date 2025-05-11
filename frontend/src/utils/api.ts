import axios from 'axios'

import {
  ChatbotTemplateControllerApiFactory,
  PatientControllerApiFactory,
  PatientDocumentControllerApiFactory,
  PatientTestControllerApiFactory,
  TherapistControllerApiFactory,
  TherapySessionControllerApiFactory,
  TherapySessionNoteControllerApiFactory,
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
export const therapySessionApi = TherapySessionControllerApiFactory(undefined, baseURL, api)
export const therapySessionNoteApi = TherapySessionNoteControllerApiFactory(undefined, baseURL, api)

export default api
