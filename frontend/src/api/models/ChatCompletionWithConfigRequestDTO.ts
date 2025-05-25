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
    message: string;
  }
  