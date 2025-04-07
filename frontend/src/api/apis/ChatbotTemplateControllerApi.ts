/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import * as runtime from '../runtime'
import type { CreateChatbotTemplateDTO, TherapistOutputDTO } from '../models/index'
import {
  CreateChatbotTemplateDTOFromJSON,
  CreateChatbotTemplateDTOToJSON,
  TherapistOutputDTOFromJSON,
  TherapistOutputDTOToJSON,
} from '../models/index'

export interface CloneTemplateRequest {
  templateId: string
}

export interface CreateTemplateRequest {
  createChatbotTemplateDTO: CreateChatbotTemplateDTO
}

export interface DeleteTemplateRequest {
  templateId: string
}

export interface UpdateTemplateRequest {
  templateId: string
  createChatbotTemplateDTO: CreateChatbotTemplateDTO
}

/**
 * ChatbotTemplateControllerApi - interface
 *
 * @export
 * @interface ChatbotTemplateControllerApiInterface
 */
export interface ChatbotTemplateControllerApiInterface {
  /**
   *
   * @param {string} templateId
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ChatbotTemplateControllerApiInterface
   */
  cloneTemplateRaw(
    requestParameters: CloneTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>>

  /**
   */
  cloneTemplate(
    requestParameters: CloneTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO>

  /**
   *
   * @param {CreateChatbotTemplateDTO} createChatbotTemplateDTO
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ChatbotTemplateControllerApiInterface
   */
  createTemplateRaw(
    requestParameters: CreateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>>

  /**
   */
  createTemplate(
    requestParameters: CreateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO>

  /**
   *
   * @param {string} templateId
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ChatbotTemplateControllerApiInterface
   */
  deleteTemplateRaw(
    requestParameters: DeleteTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>>

  /**
   */
  deleteTemplate(
    requestParameters: DeleteTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO>

  /**
   *
   * @param {string} templateId
   * @param {CreateChatbotTemplateDTO} createChatbotTemplateDTO
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ChatbotTemplateControllerApiInterface
   */
  updateTemplateRaw(
    requestParameters: UpdateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>>

  /**
   */
  updateTemplate(
    requestParameters: UpdateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO>
}

/**
 *
 */
export class ChatbotTemplateControllerApi
  extends runtime.BaseAPI
  implements ChatbotTemplateControllerApiInterface
{
  /**
   */
  async cloneTemplateRaw(
    requestParameters: CloneTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>> {
    if (requestParameters['templateId'] == null) {
      throw new runtime.RequiredError(
        'templateId',
        'Required parameter "templateId" was null or undefined when calling cloneTemplate().'
      )
    }

    const queryParameters: any = {}

    const headerParameters: runtime.HTTPHeaders = {}

    const response = await this.request(
      {
        path: `/chatbot-templates/{templateId}/clone`.replace(
          `{${'templateId'}}`,
          encodeURIComponent(String(requestParameters['templateId']))
        ),
        method: 'POST',
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    )

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      TherapistOutputDTOFromJSON(jsonValue)
    )
  }

  /**
   */
  async cloneTemplate(
    requestParameters: CloneTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO> {
    const response = await this.cloneTemplateRaw(requestParameters, initOverrides)
    return await response.value()
  }

  /**
   */
  async createTemplateRaw(
    requestParameters: CreateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>> {
    if (requestParameters['createChatbotTemplateDTO'] == null) {
      throw new runtime.RequiredError(
        'createChatbotTemplateDTO',
        'Required parameter "createChatbotTemplateDTO" was null or undefined when calling createTemplate().'
      )
    }

    const queryParameters: any = {}

    const headerParameters: runtime.HTTPHeaders = {}

    headerParameters['Content-Type'] = 'application/json'

    const response = await this.request(
      {
        path: `/chatbot-templates`,
        method: 'POST',
        headers: headerParameters,
        query: queryParameters,
        body: CreateChatbotTemplateDTOToJSON(requestParameters['createChatbotTemplateDTO']),
      },
      initOverrides
    )

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      TherapistOutputDTOFromJSON(jsonValue)
    )
  }

  /**
   */
  async createTemplate(
    requestParameters: CreateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO> {
    const response = await this.createTemplateRaw(requestParameters, initOverrides)
    return await response.value()
  }

  /**
   */
  async deleteTemplateRaw(
    requestParameters: DeleteTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>> {
    if (requestParameters['templateId'] == null) {
      throw new runtime.RequiredError(
        'templateId',
        'Required parameter "templateId" was null or undefined when calling deleteTemplate().'
      )
    }

    const queryParameters: any = {}

    const headerParameters: runtime.HTTPHeaders = {}

    const response = await this.request(
      {
        path: `/chatbot-templates/{templateId}`.replace(
          `{${'templateId'}}`,
          encodeURIComponent(String(requestParameters['templateId']))
        ),
        method: 'DELETE',
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    )

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      TherapistOutputDTOFromJSON(jsonValue)
    )
  }

  /**
   */
  async deleteTemplate(
    requestParameters: DeleteTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO> {
    const response = await this.deleteTemplateRaw(requestParameters, initOverrides)
    return await response.value()
  }

  /**
   */
  async updateTemplateRaw(
    requestParameters: UpdateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<TherapistOutputDTO>> {
    if (requestParameters['templateId'] == null) {
      throw new runtime.RequiredError(
        'templateId',
        'Required parameter "templateId" was null or undefined when calling updateTemplate().'
      )
    }

    if (requestParameters['createChatbotTemplateDTO'] == null) {
      throw new runtime.RequiredError(
        'createChatbotTemplateDTO',
        'Required parameter "createChatbotTemplateDTO" was null or undefined when calling updateTemplate().'
      )
    }

    const queryParameters: any = {}

    const headerParameters: runtime.HTTPHeaders = {}

    headerParameters['Content-Type'] = 'application/json'

    const response = await this.request(
      {
        path: `/chatbot-templates/{templateId}`.replace(
          `{${'templateId'}}`,
          encodeURIComponent(String(requestParameters['templateId']))
        ),
        method: 'PUT',
        headers: headerParameters,
        query: queryParameters,
        body: CreateChatbotTemplateDTOToJSON(requestParameters['createChatbotTemplateDTO']),
      },
      initOverrides
    )

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      TherapistOutputDTOFromJSON(jsonValue)
    )
  }

  /**
   */
  async updateTemplate(
    requestParameters: UpdateTemplateRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<TherapistOutputDTO> {
    const response = await this.updateTemplateRaw(requestParameters, initOverrides)
    return await response.value()
  }
}
