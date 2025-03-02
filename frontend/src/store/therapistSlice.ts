import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit'
import { TherapistOutputDTO } from '../dto/output/TherapistOutputDTO'
import { CreateChatbotTemplateDTO } from '../dto/input/CreateChatbotTemplateDTO'
import api from '../utils/api'
import { UpdateChatbotTemplateDTO } from '../dto/input/UpdateChatbotTemplateDTO'
import { CreatePatientDTO } from '../dto/input/CreatePatientDTO'
import { CreateTherapistDTO } from '../dto/input/TherapistInputDTO'
import { LoginTherapistDTO } from '../dto/input/LoginTherapistDTO'

interface TherapistState {
  loggedInTherapist: TherapistOutputDTO | null
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: TherapistState = {
  loggedInTherapist: null,
  status: 'idle',
  error: null,
}

export const registerTherapist = createAsyncThunk(
  'therapist/registerTherapist',
  async (createTherapistDTO: CreateTherapistDTO) => {
    const response = await api.post(`/therapists`, createTherapistDTO)
    return response.data
  }
)

export const loginTherapist = createAsyncThunk(
  'therapist/loginTherapist',
  async (loginTherapistDTO: LoginTherapistDTO) => {
    const response = await api.post(`/therapists/login`, loginTherapistDTO)
    return response.data
  }
)

export const logoutTherapist = createAsyncThunk('therapist/logoutTherapist', async () => {
  await api.post(`/therapists/logout`)
})

export const getCurrentlyLoggedInTherapist = createAsyncThunk(
  'therapist/getCurrentlyLoggedInTherapist',
  async () => {
    const response = await api.get(`/therapists/me`)
    return response.data
  }
)

export const createPatientForTherapist = createAsyncThunk(
  'therapist/createPatientForTherapist',
  async (createPatientDTO: CreatePatientDTO, { getState }) => {
    const state = getState() as { therapist: TherapistState }
    const { loggedInTherapist } = state.therapist

    if (!loggedInTherapist) {
      throw new Error("Therapist isn't logged in")
    }

    const response = await api.post(`/therapists/patients`, createPatientDTO)
    return response.data
  }
)

export const createChatbotTemplateForTherapist = createAsyncThunk(
  'therapist/createChatbotTemplateForTherapist',
  async (createChatbotTemplateDTO: CreateChatbotTemplateDTO) => {
    const response = await api.post(`/therapists/chatbot-templates`, createChatbotTemplateDTO)
    return response.data
  }
)

export const updateChatbotTemplateForTherapist = createAsyncThunk(
  'therapist/updateChatbotTemplateForTherapist',
  async (payload: {
    chatbotTemplateId: string
    updateChatbotTemplateDTO: UpdateChatbotTemplateDTO
  }) => {
    const response = await api.put(
      `/therapists/chatbot-templates/${payload.chatbotTemplateId}`,
      payload.updateChatbotTemplateDTO
    )
    return response.data
  }
)

export const cloneChatbotTemplateForTherapist = createAsyncThunk(
  'therapist/cloneChatbotTemplateForTherapist',
  async (chatbotTemplateId: string) => {
    const response = await api.post(`/therapists/chatbot-templates/${chatbotTemplateId}/clone`)
    return response.data
  }
)

export const deleteChatbotTemplateForTherapist = createAsyncThunk(
  'therapist/deleteChatbotTemplateForTherapist',
  async (chatbotTemplateId: string) => {
    const response = await api.delete(`/therapists/chatbot-templates/${chatbotTemplateId}`)
    return response.data
  }
)

const therapistSlice = createSlice({
  name: 'therapist',
  initialState,
  reducers: {
    setLoggedInTherapist: (state, action: PayloadAction<TherapistOutputDTO>) => {
      state.loggedInTherapist = action.payload
    },
    clearLoggedInTherapist: (state) => {
      state.loggedInTherapist = null
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(registerTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(registerTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(loginTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(loginTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(loginTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(logoutTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(logoutTherapist.fulfilled, (state) => {
        state.status = 'succeeded'
        state.loggedInTherapist = null
      })
      .addCase(logoutTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(getCurrentlyLoggedInTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getCurrentlyLoggedInTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(getCurrentlyLoggedInTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(createPatientForTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createPatientForTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(createPatientForTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(createChatbotTemplateForTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createChatbotTemplateForTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(createChatbotTemplateForTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(updateChatbotTemplateForTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateChatbotTemplateForTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(updateChatbotTemplateForTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(cloneChatbotTemplateForTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(cloneChatbotTemplateForTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(cloneChatbotTemplateForTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })

      .addCase(deleteChatbotTemplateForTherapist.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteChatbotTemplateForTherapist.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.loggedInTherapist = action.payload
      })
      .addCase(deleteChatbotTemplateForTherapist.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
      })
  },
})

export const { setLoggedInTherapist, clearLoggedInTherapist } = therapistSlice.actions
export default therapistSlice.reducer
