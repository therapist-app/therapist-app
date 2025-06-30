// chatbotTemplateDocumentSlice.ts
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

import { ChatbotTemplateDocumentOutputDTO } from '../api'
import { chatbotTemplateDocumentApi } from '../utils/api'

/** Slice state --------------------------------------------------------------*/
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

/** Thunks -------------------------------------------------------------------*/
export const createDocumentForTemplate = createAsyncThunk(
  'chatbotTemplateDocument/create',
  async (props: { file: File; templateId: string }) => {
    const response = await chatbotTemplateDocumentApi.createChatbotTemplateDocument(
      props.templateId,
      props.file
    )
    return response.data as ChatbotTemplateDocumentOutputDTO
  }
)

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

/** Slice --------------------------------------------------------------------*/
const chatbotTemplateDocumentSlice = createSlice({
  name: 'chatbotTemplateDocument',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    /* ───────── create ───────── */
    builder.addCase(createDocumentForTemplate.pending, (state) => {
      state.status = 'loading'
      state.error = null
    })
    builder.addCase(createDocumentForTemplate.fulfilled, (state, action) => {
      state.status = 'succeeded'
      state.allDocumentsOfTemplate.push(action.payload)
    })
    builder.addCase(createDocumentForTemplate.rejected, (state, action) => {
      state.status = 'failed'
      state.error = action.error.message ?? 'Something went wrong'
    })

    /* ───────── list ───────── */
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

    /* ───────── delete ───────── */
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
