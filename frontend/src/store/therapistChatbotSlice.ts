import { createSlice, Middleware, PayloadAction } from '@reduxjs/toolkit'

import {
  ChatMessageDTO,
  ChatMessageDTOChatRoleEnum,
  TherapistChatbotInputDTO,
  TherapistChatbotInputDTOLanguageEnum,
} from '../api'
import { therapistChatbotApi } from '../utils/api'
import { getErrorPayload } from '../utils/errorUtil'
import { createAppAsyncThunk } from './thunk'

export const ALL_CLIENTS_KEY = 'ALL_CLIENTS'
const LOCAL_STORAGE_KEY = 'COACH_CHAT'

export const getPatientIdKey = (patientId: string | undefined): string => {
  if (patientId) {
    return patientId
  }
  return ALL_CLIENTS_KEY
}

const loadMessagesFromStorage = (): Record<string, ChatMessageDTO[] | undefined> => {
  try {
    const serializedMessages = localStorage.getItem(LOCAL_STORAGE_KEY)
    if (serializedMessages === null) {
      return {}
    }
    return JSON.parse(serializedMessages)
  } catch (err) {
    console.error('Could not load chat messages from localStorage', err)
    return {}
  }
}

export const therapistChatbotSaveMessagesToLocalStorageMiddleware: Middleware =
  (store) => (next) => (action) => {
    const result = next(action)

    try {
      const messages = store.getState().therapistChatbot.therapistChatbotMessages
      const serializedMessages = JSON.stringify(messages)
      localStorage.setItem(LOCAL_STORAGE_KEY, serializedMessages)
    } catch (err) {
      console.error('Could not save chat messages to localStorage', err)
    }

    return result
  }

interface TherapistChatbotState {
  therapistChatbotMessages: Record<string, ChatMessageDTO[] | undefined>
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapistChatbotState = {
  therapistChatbotMessages: loadMessagesFromStorage(),
  status: 'idle',
  error: null,
}

export const chatWithTherapistChatbot = createAppAsyncThunk(
  'chatWithTherapistChatbot',
  async (
    payload: {
      newMessage: string
      patientId: string | undefined
      language: TherapistChatbotInputDTOLanguageEnum
    },
    thunkAPI
  ) => {
    try {
      thunkAPI.dispatch(
        addMessage({
          chatMessage: {
            chatRole: ChatMessageDTOChatRoleEnum.User,
            content: payload.newMessage,
          },
          patientId: payload.patientId,
        })
      )

      const currentMessages =
        thunkAPI.getState().therapistChatbot.therapistChatbotMessages[
          getPatientIdKey(payload.patientId)
        ]
      const therapistChatbotInputDTO: TherapistChatbotInputDTO = {
        chatMessages: currentMessages,
        patientId: payload.patientId,
        language: payload.language,
      }

      const response = await therapistChatbotApi.chatWithTherapistChatbot(therapistChatbotInputDTO)
      return { response: response.data, patientId: payload.patientId }
    } catch (error) {
      return thunkAPI.rejectWithValue(getErrorPayload(error))
    }
  }
)

const therapistChatbotSlice = createSlice({
  name: 'therapistChatbot',
  initialState: initialState,
  reducers: {
    addMessage: (
      state,
      action: PayloadAction<{ chatMessage: ChatMessageDTO; patientId: string | undefined }>
    ) => {
      const key = getPatientIdKey(action.payload.patientId)
      const existingMessages = state.therapistChatbotMessages[key] ?? []

      state.therapistChatbotMessages[key] = [...existingMessages, action.payload.chatMessage]
    },
    clearMessagesOfPatient: (state, action: PayloadAction<string | undefined>) => {
      state.therapistChatbotMessages[getPatientIdKey(action.payload)] = []
      state.status = 'idle'
      state.error = null
    },
    clearAllTherapistChatbotMessages: (state) => {
      state.therapistChatbotMessages = {}
    },
    resetStatus: (state) => {
      state.status = 'idle'
      state.error = null
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(chatWithTherapistChatbot.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(chatWithTherapistChatbot.fulfilled, (state, action) => {
        state.status = 'succeeded'
        const key = getPatientIdKey(action.payload.patientId)
        const existingMessages = state.therapistChatbotMessages[key] ?? []

        const assistantMessage: ChatMessageDTO = {
          chatRole: ChatMessageDTOChatRoleEnum.Assistant,
          content: action.payload.response.content,
        }

        state.therapistChatbotMessages[key] = [...existingMessages, assistantMessage]
      })
      .addCase(chatWithTherapistChatbot.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const { clearMessagesOfPatient, addMessage, resetStatus, clearAllTherapistChatbotMessages } =
  therapistChatbotSlice.actions
export default therapistChatbotSlice.reducer
