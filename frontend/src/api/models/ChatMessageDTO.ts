export type ChatRole = 'system' | 'user' | 'assistant';

export interface ChatMessageDTO {
  role: ChatRole;
  content: string;
}