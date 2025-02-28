import api from '../utils/api'
import { PatientOutputDTO } from '../dto/output/PatientOutputDTO'
import { CreatePatientDTO } from '../dto/input/CreatePatientDTO'

export async function getAllPatients(): Promise<PatientOutputDTO[]> {
  const response = await api.get('/patients')
  return response.data
}

export async function getPatientsForTherapist(): Promise<PatientOutputDTO[]> {
  const response = await api.get(`/therapists/${sessionStorage.getItem('therapistId')}/patients`)
  return response.data
}

export async function registerPatient(
  therapistId: string,
  newPatient: CreatePatientDTO
): Promise<PatientOutputDTO> {
  const response = await api.post(`/therapists/${therapistId}/patients`, newPatient)
  return response.data
}
