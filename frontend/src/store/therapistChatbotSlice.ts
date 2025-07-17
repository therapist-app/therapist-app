import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  ChatMessageDTO,
  ChatMessageDTOChatRoleEnum,
  TherapistChatbotInputDTO,
  TherapistChatbotInputDTOLanguageEnum,
} from '../api'
import { therapistChatbotApi } from '../utils/api'
import { createAppAsyncThunk } from './thunk'

interface TherapistChatbotState {
  therapistChatbotMessages: ChatMessageDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapistChatbotState = {
  therapistChatbotMessages: [],
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
    thunkAPI.dispatch(
      addMessage({
        chatRole: ChatMessageDTOChatRoleEnum.User,
        content: payload.newMessage,
      })
    )
    const currentMessages = thunkAPI.getState().therapistChatbot.therapistChatbotMessages
    const therapistChatbotInputDTO: TherapistChatbotInputDTO = {
      chatMessages: currentMessages,
      patientId: payload.patientId,
      language: payload.language,
    }

    const response = await therapistChatbotApi.chatWithTherapistChatbot(therapistChatbotInputDTO)
    return response.data
  }
)

const therapistChatbotSlice = createSlice({
  name: 'therapistChatbot',
  initialState: initialState,
  reducers: {
    addMessage: (state, action: PayloadAction<ChatMessageDTO>) => {
      state.therapistChatbotMessages.push(action.payload)
    },
    clearMessages: (state) => {
      state.therapistChatbotMessages = []
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
        state.therapistChatbotMessages.push({
          chatRole: ChatMessageDTOChatRoleEnum.Assistant,
          content: action.payload.content,
        })
      })
      .addCase(chatWithTherapistChatbot.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const { clearMessages, addMessage } = therapistChatbotSlice.actions
export default therapistChatbotSlice.reducer
