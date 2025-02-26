import { AxiosResponse } from "axios";
import api from "../utils/api";

interface Patient {
  id: string;
  name: string;
}

interface NewPatientDTO {
  name: string;
}

export async function getAllPatients(): Promise<Patient[]> {
  const response = await api.get("/patients");
  return response.data;
}

export async function getPatientsForTherapist(): Promise<AxiosResponse<any, any>> {
    const response = await api.get(`/therapists/${sessionStorage.getItem('therapistId')}/patients`);
    return response;
  }

export async function registerPatient(therapistId: string, newPatient: NewPatientDTO): Promise<Patient> {
    const response = await api.post(`/therapists/${therapistId}/patients`, newPatient);
    return response.data;
}