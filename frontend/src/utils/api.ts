import axios from 'axios'
import BaseAPI from './api'
import {
  ChatbotTemplateControllerApi,
  PatientControllerApi,
  PatientTestControllerApi,
  TherapistControllerApi,
  TherapySessionControllerApi,
} from '../api'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
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

BaseAPI.prototype.axios = api

export const chatbotTemplateApi = new ChatbotTemplateControllerApi()
export const patientApi = new PatientControllerApi()
export const patientTestApi = new PatientTestControllerApi()
export const therapistApi = new TherapistControllerApi()
export const therapySessionApi = new TherapySessionControllerApi()

export default api
