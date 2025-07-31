import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

import { ChatbotTemplateDocumentOutputDTO } from '../api'
import { chatbotTemplateDocumentApi } from '../utils/api'

interface ChatbotTemplateDocumentState {
  allDocumentsOfTemplate: ChatbotTemplateDocumentOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: ChatbotTemplateDocumentState = {
  allDocumentsOfTemplate: [],
  status: 'idle',
  error: null,
}

export const createDocumentForTemplate = createAsyncThunk<
  void,
  { file: File; templateId: string }
>('chatbotTemplateDocument/create', async ({ file, templateId }) => {
  await chatbotTemplateDocumentApi.createChatbotTemplateDocument(templateId, file)
})

export const getAllDocumentsOfTemplate = createAsyncThunk(
  'chatbotTemplateDocument/getAll',
  async (templateId: string) => {
    const response = await chatbotTemplateDocumentApi.getDocumentsOfTemplate(templateId)
    return response.data as ChatbotTemplateDocumentOutputDTO[]
  }
)

export const deleteDocumentOfTemplate = createAsyncThunk(
  'chatbotTemplateDocument/delete',
  async (templateDocumentId: string) => {
    await chatbotTemplateDocumentApi.deleteChatbotTemplateDocument(templateDocumentId)
    return templateDocumentId
  }
)

const chatbotTemplateDocumentSlice = createSlice({
  name: 'chatbotTemplateDocument',
  initialState: initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(createDocumentForTemplate.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })
    builder.addCase(createDocumentForTemplate.fulfilled, (state) => {
      state.status = 'succeeded'
    })
    builder.addCase(createDocumentForTemplate.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message ?? 'Something went wrong'
    })

    builder.addCase(getAllDocumentsOfTemplate.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })
    builder.addCase(getAllDocumentsOfTemplate.fulfilled, (state, action) => {
      state.status = 'succeeded'
      state.allDocumentsOfTemplate = action.payload
    })
    builder.addCase(getAllDocumentsOfTemplate.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message ?? 'Something went wrong'
    })

    builder.addCase(deleteDocumentOfTemplate.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })
    builder.addCase(deleteDocumentOfTemplate.fulfilled, (state, action) => {
      state.status = 'succeeded'
      console.log(action)
    })
    builder.addCase(deleteDocumentOfTemplate.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message ?? 'Something went wrong'
    })
  },
})

export default chatbotTemplateDocumentSlice.reducer
