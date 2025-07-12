import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import { ChatbotTemplateOutputDTO, CreateChatbotTemplateDTO } from '../api'
import { chatbotTemplateApi } from '../utils/api'

interface ChatbotTemplateState {
  selectedChatbotTemplate: ChatbotTemplateOutputDTO | null
  allChatbotTemplates: ChatbotTemplateOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: ChatbotTemplateState = {
  selectedChatbotTemplate: null,
  allChatbotTemplates: [],
  status: 'idle',
  error: null,
}

export const createChatbotTemplate = createAsyncThunk(
  'createChatbotTemplate',
  async (createChatbotTemplateDTO: CreateChatbotTemplateDTO) => {
    const response = await chatbotTemplateApi.createTemplate(createChatbotTemplateDTO)
    return response.data
  }
)

export const createPatientChatbotTemplate = createAsyncThunk(
  'chatbotTemplate/createPatientChatbotTemplate',
  async (
    {
      patientId,
      dto,
    }: { patientId: string; dto: CreateChatbotTemplateDTO },
    thunkAPI
  ) => {
    try {
      const { data } = await chatbotTemplateApi.createTemplateForPatient(patientId, dto)
      return data
    } catch (err: any) {
      return thunkAPI.rejectWithValue(err.response?.data || err.message)
    }
  }
)

export const updateChatbotTemplate = createAsyncThunk(
  'updateChatbotTemplate',
  async (payload: {
    chatbotTemplateId: string
    updateChatbotTemplateDTO: CreateChatbotTemplateDTO
  }) => {
    const response = await chatbotTemplateApi.updateTemplate(
      payload.chatbotTemplateId,

      payload.updateChatbotTemplateDTO
    )
    return response.data
  }
)

export const cloneChatbotTemplate = createAsyncThunk(
  'cloneChatbotTemplate',
  async (chatbotTemplateId: string) => {
    const response = await chatbotTemplateApi.cloneTemplate(chatbotTemplateId)
    return response.data
  }
)

export const deleteChatbotTemplate = createAsyncThunk(
  'deleteChatbotTemplate',
  async (chatbotTemplateId: string) => {
    const response = await chatbotTemplateApi.deleteTemplate(chatbotTemplateId)
    return response.data
  }
)

const chatbotTemplateSlice = createSlice({
  name: 'chatbotTemplate',
  initialState: initialState,
  reducers: {
    setSelectedChatbotTemplate: (state, action: PayloadAction<ChatbotTemplateOutputDTO>) => {
      state.selectedChatbotTemplate = action.payload
    },
    setAllChatbotTemplates: (state, action: PayloadAction<ChatbotTemplateOutputDTO[]>) => {
      state.allChatbotTemplates = action.payload
    },
    clearSelectedChatbotTemplate: (state) => {
      state.selectedChatbotTemplate = null
    },
    clearAllChatbotTemplates: (state) => {
      state.allChatbotTemplates = []
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(createChatbotTemplate.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createChatbotTemplate.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedChatbotTemplate = action.payload
      })
      .addCase(createChatbotTemplate.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(createPatientChatbotTemplate.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createPatientChatbotTemplate.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedChatbotTemplate = action.payload
      })
      .addCase(createPatientChatbotTemplate.rejected, (state, action) => {
        state.status = 'failed'
        state.error =
          typeof action.payload === 'string'
            ? action.payload
            : action.error.message || 'Something went wrong'
      })

      .addCase(cloneChatbotTemplate.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(cloneChatbotTemplate.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedChatbotTemplate = action.payload
      })
      .addCase(cloneChatbotTemplate.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(updateChatbotTemplate.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateChatbotTemplate.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedChatbotTemplate = action.payload
      })
      .addCase(updateChatbotTemplate.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteChatbotTemplate.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteChatbotTemplate.fulfilled, (state) => {
        state.status = 'succeeded'
      })
      .addCase(deleteChatbotTemplate.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const {
  setSelectedChatbotTemplate,
  clearSelectedChatbotTemplate,
  setAllChatbotTemplates,
  clearAllChatbotTemplates,
} = chatbotTemplateSlice.actions
export default chatbotTemplateSlice.reducer
