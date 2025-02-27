import { ChatbotTemplateOutputDTO } from "../dto/output/ChatbotTemplateOutputDTO";
import api from "../utils/api";

export async function getAllChatbotTemplatesForTherapist(therapistId: string): Promise<ChatbotTemplateOutputDTO[]> {
  const response = await api.get(`/api/therapists/${therapistId}/chatbot-templates`);
  return response.data;
}
