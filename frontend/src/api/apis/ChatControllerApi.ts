import * as runtime from '../runtime';
import {
    ChatCompletionRequestDTO,
    ChatCompletionRequestDTOToJSON,
  } from '../models/ChatCompletionRequestDTO';
  
  import {
    ChatCompletionResponseDTO,
    ChatCompletionResponseDTOFromJSON,
  } from '../models/ChatCompletionResponseDTO';


export interface ChatCompletionRequest {
  chatCompletionRequestDTO: ChatCompletionRequestDTO;
}

export interface ChatControllerApiInterface {
  chatCompletionRaw(
    requestParameters: ChatCompletionRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction,
  ): Promise<runtime.ApiResponse<ChatCompletionResponseDTO>>;

  chatCompletion(
    requestParameters: ChatCompletionRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction,
  ): Promise<ChatCompletionResponseDTO>;
}

export class ChatControllerApi
  extends runtime.BaseAPI
  implements ChatControllerApiInterface {

  async chatCompletionRaw(
    requestParameters: ChatCompletionRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction,
  ): Promise<runtime.ApiResponse<ChatCompletionResponseDTO>> {

    if (requestParameters['chatCompletionRequestDTO'] == null) {
      throw new runtime.RequiredError(
        'chatCompletionRequestDTO',
        'Required parameter "chatCompletionRequestDTO" was null or undefined.',
      );
    }

    const headerParameters: runtime.HTTPHeaders = {};
    headerParameters['Content-Type'] = 'application/json';

    const response = await this.request({
      path  : '/api/chat/completions',
      method: 'POST',
      headers: headerParameters,
      body  : ChatCompletionRequestDTOToJSON(
               requestParameters.chatCompletionRequestDTO),
    }, initOverrides);

    return new runtime.JSONApiResponse(
      response,
      (json) => ChatCompletionResponseDTOFromJSON(json),
    );
  }

  async chatCompletion(
    requestParameters: ChatCompletionRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction,
  ): Promise<ChatCompletionResponseDTO> {
    const response = await this.chatCompletionRaw(
      requestParameters, initOverrides,
    );
    return await response.value();
  }
}
