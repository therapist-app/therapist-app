export interface CreateChatbotTemplateDTO {
  chatbotName: string;
  description: string;
  chatbotModel: string;
  chatbotIcon: string;
  chatbotLanguage: string;
  chatbotRole: string;
  chatbotTone: string;
  welcomeMessage: string;
  workspaceId: string;
}
