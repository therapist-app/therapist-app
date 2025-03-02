import { ChatbotTemplateOutputDTO } from './ChatbotTemplateOutputDTO'
import { PatientOutputDTO } from './PatientOutputDTO'

export type TherapistOutputDTO = {
  id: string
  email: string
  workspaceId: string
  chatbotTemplatesOutputDTO: ChatbotTemplateOutputDTO[]
  patientsOutputDTO: PatientOutputDTO[]
}
