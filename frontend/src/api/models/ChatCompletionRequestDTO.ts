import type { ChatMessageDTO } from './ChatMessageDTO';

export interface ChatCompletionRequestDTO {
  messages: ChatMessageDTO[];
}

export function ChatCompletionRequestDTOToJSON(
  value: ChatCompletionRequestDTO,
): any {
  return value;
}

export function ChatCompletionRequestDTOFromJSON(
  json: any,
): ChatCompletionRequestDTO {
  return json as ChatCompletionRequestDTO;
}