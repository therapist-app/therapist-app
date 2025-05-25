import { ChatMessageDTO } from './ChatMessageDTO';

export interface ChatCompletionWithConfigRequestDTO {
  config: {
    chatbotRole: string;
    chatbotTone: string;
    chatbotLanguage: string;
    chatbotVoice: string;
    chatbotGender: string;
    preConfiguredExercise: string;
    additionalExercise: string;
    welcomeMessage: string;
  };
  history: ChatMessageDTO[];
  message: string;
}

export function ChatCompletionWithConfigRequestDTOToJSON(
  value: ChatCompletionWithConfigRequestDTO
): any {
  return {
    config: { ...value.config },
    history: value.history.map(m => ({ role: m.role, content: m.content })),
    message: value.message,
  };
}

export function ChatCompletionWithConfigRequestDTOFromJSON(
  json: any
): ChatCompletionWithConfigRequestDTO {
  return {
    config: {
      chatbotRole: json.config.chatbotRole,
      chatbotTone: json.config.chatbotTone,
      chatbotLanguage: json.config.chatbotLanguage,
      chatbotVoice: json.config.chatbotVoice,
      chatbotGender: json.config.chatbotGender,
      preConfiguredExercise: json.config.preConfiguredExercise,
      additionalExercise: json.config.additionalExercise,
      welcomeMessage: json.config.welcomeMessage,
    },
    history: (json.history as any[]).map(
      m => ({ role: m.role, content: m.content } as ChatMessageDTO)
    ),
    message: json.message,
  };
}
