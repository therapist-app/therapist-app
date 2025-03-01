import { ChatbotTemplateOutputDTO } from './ChatbotTemplateOutputDTO'

export type TherapistOutputDTO = {
  id: string
  email: string
  workspaceId: string
  chatbotTemplatesOutputDTO: ChatbotTemplateOutputDTO[]
}
