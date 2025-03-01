import api from '../utils/api'
import { TherapistOutputDTO } from '../dto/output/TherapistOutputDTO'
import { CreateTherapistDTO } from '../dto/input/TherapistInputDTO'
import { LoginTherapistDTO } from '../dto/input/LoginTherapistDTO'

export const getAllTherapists = async (): Promise<TherapistOutputDTO[]> => {
  const response = await api.get('/therapists')
  return response.data
}

export const createTherapist = async (
  therapist: CreateTherapistDTO
): Promise<TherapistOutputDTO> => {
  const response = await api.post('/therapists', therapist, {
    withCredentials: true,
  })
  return response.data
}

export const loginTherapist = async (therapist: LoginTherapistDTO): Promise<TherapistOutputDTO> => {
  const response = await api.post('/therapists/login', therapist, {
    withCredentials: true,
  })
  return response.data
}
