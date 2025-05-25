import * as runtime from '../runtime';
import {
  ChatCompletionWithConfigRequestDTO
} from '../models/ChatCompletionWithConfigRequestDTO';
import {
  ChatCompletionResponseDTO,
  ChatCompletionResponseDTOFromJSON,
} from '../models/ChatCompletionResponseDTO';

export interface ChatCompletionWithConfigRequest {
  body: ChatCompletionWithConfigRequestDTO;
}

export class ChatControllerApiWithConfig
  extends runtime.BaseAPI {

  async chatCompletionWithConfigRaw(
    req: ChatCompletionWithConfigRequest,
    init?: RequestInit | runtime.InitOverrideFunction,
  ) {
    const response = await this.request(
      {
        path: '/api/chat/completions-with-config',
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: req.body,
      },
      init,
    );

    return new runtime.JSONApiResponse(
      response,
      (json) => ChatCompletionResponseDTOFromJSON(json),
    );
  }

  async chatCompletionWithConfig(
    req: ChatCompletionWithConfigRequest,
    init?: RequestInit | runtime.InitOverrideFunction,
  ): Promise<ChatCompletionResponseDTO> {
    const r = await this.chatCompletionWithConfigRaw(req, init);
    return r.value();
  }
}
