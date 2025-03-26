import { TherapySessionOutputDTO } from './TherapySessionOutputDTO'

export interface PatientOutputDTO {
  id: string
  name: string
  therapySessionsOutputDTO: TherapySessionOutputDTO[]
}
