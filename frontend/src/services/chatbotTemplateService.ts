import { CreateChatbotTemplateDTO } from '../dto/input/CreateChatbotTemplateDTO'
import { ChatbotTemplateOutputDTO } from '../dto/output/ChatbotTemplateOutputDTO'
import api from '../utils/api'

export async function getAllChatbotTemplatesForTherapist(
  therapistId: string
): Promise<ChatbotTemplateOutputDTO[]> {
  const response = await api.get(`/api/therapists/${therapistId}/chatbot-templates`)
  return response.data
}

export async function createChatbotTemplate(
  createChatbotTemplateDTO: CreateChatbotTemplateDTO,
  therapistId: string
): Promise<ChatbotTemplateOutputDTO> {
  const response = await api.post(
    `/api/therapists/${therapistId}/chatbot-templates`,
    createChatbotTemplateDTO
  )
  return response.data
}

export async function updateChatbotTemplate(
  therapistId: string,
  chatbotId: string,
  chatbotName: string
): Promise<ChatbotTemplateOutputDTO> {
  const response = await api.put(`/api/therapists/${therapistId}/chatbot-templates/${chatbotId}`, {
    chatbotName,
  })
  return response.data
}

export async function cloneChatbotTemplate(
  therapistId: string,
  chatbotId: string
): Promise<ChatbotTemplateOutputDTO> {
  const response = await api.post(
    `/api/therapists/${therapistId}/chatbot-templates/${chatbotId}/clone`
  )
  return response.data
}
