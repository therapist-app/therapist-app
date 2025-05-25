export interface ChatCompletionResponseDTO {
    content: string;
  }
  
  export function ChatCompletionResponseDTOFromJSON(
    json: any,
  ): ChatCompletionResponseDTO {
    return json as ChatCompletionResponseDTO;
  }