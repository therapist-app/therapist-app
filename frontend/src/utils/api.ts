import axios from 'axios'

import {
  ChatbotTemplateControllerApiFactory,
  ChatControllerApiFactory,
  CounselingPlanControllerApiFactory,
  CounselingPlanPhaseControllerApiFactory,
  CounselingPlanPhaseGoalControllerApiFactory,
  ExerciseComponentControllerApiFactory,
  ExerciseControllerApiFactory,
  MeetingControllerApiFactory,
  MeetingNoteControllerApiFactory,
  PatientControllerApiFactory,
  PatientDocumentControllerApiFactory,
  PatientTestControllerApiFactory,
  TherapistChatbotControllerApiFactory,
  TherapistControllerApiFactory,
  TherapistDocumentControllerApiFactory,
} from '../api'

const baseURL: string = import.meta.env.VITE_API_BASE_URL

const api = axios.create({
  baseURL: baseURL,
  timeout: 60000,
  withCredentials: true,
})

export const chatbotTemplateApi = ChatbotTemplateControllerApiFactory(undefined, baseURL, api)
export const patientApi = PatientControllerApiFactory(undefined, baseURL, api)
export const patientDocumentApi = PatientDocumentControllerApiFactory(undefined, baseURL, api)
export const patientTestApi = PatientTestControllerApiFactory(undefined, baseURL, api)
export const therapistApi = TherapistControllerApiFactory(undefined, baseURL, api)
export const therapistDocumentApi = TherapistDocumentControllerApiFactory(undefined, baseURL, api)
export const therapistChatbotApi = TherapistChatbotControllerApiFactory(undefined, baseURL, api)
export const meetingApi = MeetingControllerApiFactory(undefined, baseURL, api)
export const meetingNoteApi = MeetingNoteControllerApiFactory(undefined, baseURL, api)
export const exerciseApi = ExerciseControllerApiFactory(undefined, baseURL, api)
export const exerciseComponentApi = ExerciseComponentControllerApiFactory(undefined, baseURL, api)
export const chatApi = ChatControllerApiFactory(undefined, baseURL, api)
export const counselingPlanApi = CounselingPlanControllerApiFactory(undefined, baseURL, api)
export const counselingPlanPhaseApi = CounselingPlanPhaseControllerApiFactory(
  undefined,
  baseURL,
  api
)
export const counselingPlanPhaseGoalApi = CounselingPlanPhaseGoalControllerApiFactory(
  undefined,
  baseURL,
  api
)

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response && error.response.status === 401) {
      try {
        await therapistApi.logoutTherapist()
      } catch (error) {
        console.error(error)
      } finally {
        window.location.href = '/register'
      }
    }
    return Promise.reject(error)
  }
)
