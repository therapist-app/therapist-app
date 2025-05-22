import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit'

import {
  CreateMeetingDTO,
  CreateMeetingNoteDTO,
  MeetingOutputDTO,
  UpdateMeetingNoteDTO,
} from '../api'
import { meetingApi, meetingNoteApi } from '../utils/api'

interface MeetingState {
  selectedMeeting: MeetingOutputDTO | null
  allMeetingsOfPatient: MeetingOutputDTO[]
  status: 'idle' | 'loading' | 'succeeded' | 'failed'
  error: string | null
}

const initialState: MeetingState = {
  selectedMeeting: null,
  allMeetingsOfPatient: [],
  status: 'idle',
  error: null,
}

export const createMeeting = createAsyncThunk(
  'createMeeting',
  async (createMeetingDTO: CreateMeetingDTO) => {
    const response = await meetingApi.createMeeting(createMeetingDTO)
    return response.data
  }
)

export const getMeeting = createAsyncThunk('getMeeting', async (meetingId: string) => {
  const response = await meetingApi.getMeetingById(meetingId)
  return response.data
})

export const getAllMeetingsOfPatient = createAsyncThunk(
  'getAllMeetingsOfPatient',
  async (patientId: string) => {
    const response = await meetingApi.getMeetingsOfPatient(patientId)
    return response.data
  }
)

export const deleteMeeting = createAsyncThunk('deleteMeeting', async (meetingId: string) => {
  const response = await meetingApi.deleteMeetingById(meetingId)
  return response.data
})

export const createMeetingNote = createAsyncThunk(
  'createMeetingNote',
  async (createMeetingNoteDTO: CreateMeetingNoteDTO) => {
    const response = await meetingNoteApi.createMeetingNote(createMeetingNoteDTO)
    return response.data
  }
)

export const updateMeetingNote = createAsyncThunk(
  'updateMeetingNote',
  async (updateMeetingNoteDTO: UpdateMeetingNoteDTO) => {
    const response = await meetingNoteApi.updateMeetingNote(updateMeetingNoteDTO)
    return response.data
  }
)

export const deleteMeetingNote = createAsyncThunk(
  'deleteMeetingNote',
  async (meetingNoteId: string) => {
    await meetingNoteApi.deleteMeetingNoteById(meetingNoteId)
  }
)

const meetingSlice = createSlice({
  name: 'meeting',
  initialState: initialState,
  reducers: {
    setSelectedMeeting: (state, action: PayloadAction<MeetingOutputDTO>) => {
      state.selectedMeeting = action.payload
    },
    setAllMeetingsOfPatient: (state, action: PayloadAction<MeetingOutputDTO[]>) => {
      state.allMeetingsOfPatient = action.payload
    },
    clearSelectedMeeting: (state) => {
      state.selectedMeeting = null
    },
    clearAllMeetingsOfPatient: (state) => {
      state.allMeetingsOfPatient = []
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(createMeeting.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createMeeting.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedMeeting = action.payload
      })
      .addCase(createMeeting.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(getMeeting.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getMeeting.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.selectedMeeting = action.payload
      })
      .addCase(getMeeting.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(getAllMeetingsOfPatient.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(getAllMeetingsOfPatient.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.allMeetingsOfPatient = action.payload
      })
      .addCase(getAllMeetingsOfPatient.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteMeeting.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteMeeting.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteMeeting.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(createMeetingNote.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(createMeetingNote.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(createMeetingNote.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(updateMeetingNote.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(updateMeetingNote.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(updateMeetingNote.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })

      .addCase(deleteMeetingNote.pending, (state) => {
        state.status = 'loading'
        state.error = null
      })
      .addCase(deleteMeetingNote.fulfilled, (state, action) => {
        state.status = 'succeeded'
        console.log(action)
      })
      .addCase(deleteMeetingNote.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error.message || 'Something went wrong'
        console.log(action)
      })
  },
})

export const {
  setSelectedMeeting,
  setAllMeetingsOfPatient,
  clearSelectedMeeting,
  clearAllMeetingsOfPatient,
} = meetingSlice.actions
export default meetingSlice.reducer
