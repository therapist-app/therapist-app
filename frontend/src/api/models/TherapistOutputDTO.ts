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

import { mapValues } from '../runtime'
import type { ChatbotTemplateOutputDTO } from './ChatbotTemplateOutputDTO'
import {
  ChatbotTemplateOutputDTOFromJSON,
  ChatbotTemplateOutputDTOFromJSONTyped,
  ChatbotTemplateOutputDTOToJSON,
  ChatbotTemplateOutputDTOToJSONTyped,
} from './ChatbotTemplateOutputDTO'
import type { PatientOutputDTO } from './PatientOutputDTO'
import {
  PatientOutputDTOFromJSON,
  PatientOutputDTOFromJSONTyped,
  PatientOutputDTOToJSON,
  PatientOutputDTOToJSONTyped,
} from './PatientOutputDTO'

/**
 *
 * @export
 * @interface TherapistOutputDTO
 */
export interface TherapistOutputDTO {
  /**
   *
   * @type {string}
   * @memberof TherapistOutputDTO
   */
  id?: string
  /**
   *
   * @type {string}
   * @memberof TherapistOutputDTO
   */
  email?: string
  /**
   *
   * @type {string}
   * @memberof TherapistOutputDTO
   */
  workspaceId?: string
  /**
   *
   * @type {Array<ChatbotTemplateOutputDTO>}
   * @memberof TherapistOutputDTO
   */
  chatbotTemplatesOutputDTO?: Array<ChatbotTemplateOutputDTO>
  /**
   *
   * @type {Array<PatientOutputDTO>}
   * @memberof TherapistOutputDTO
   */
  patientsOutputDTO?: Array<PatientOutputDTO>
  /**
   *
   * @type {Date}
   * @memberof TherapistOutputDTO
   */
  createdAt?: Date
  /**
   *
   * @type {Date}
   * @memberof TherapistOutputDTO
   */
  updatedAt?: Date
}

/**
 * Check if a given object implements the TherapistOutputDTO interface.
 */
export function instanceOfTherapistOutputDTO(value: object): value is TherapistOutputDTO {
  return true
}

export function TherapistOutputDTOFromJSON(json: any): TherapistOutputDTO {
  return TherapistOutputDTOFromJSONTyped(json, false)
}

export function TherapistOutputDTOFromJSONTyped(
  json: any,
  ignoreDiscriminator: boolean
): TherapistOutputDTO {
  if (json == null) {
    return json
  }
  return {
    id: json['id'] == null ? undefined : json['id'],
    email: json['email'] == null ? undefined : json['email'],
    workspaceId: json['workspaceId'] == null ? undefined : json['workspaceId'],
    chatbotTemplatesOutputDTO:
      json['chatbotTemplatesOutputDTO'] == null
        ? undefined
        : (json['chatbotTemplatesOutputDTO'] as Array<any>).map(ChatbotTemplateOutputDTOFromJSON),
    patientsOutputDTO:
      json['patientsOutputDTO'] == null
        ? undefined
        : (json['patientsOutputDTO'] as Array<any>).map(PatientOutputDTOFromJSON),
    createdAt: json['createdAt'] == null ? undefined : new Date(json['createdAt']),
    updatedAt: json['updatedAt'] == null ? undefined : new Date(json['updatedAt']),
  }
}

export function TherapistOutputDTOToJSON(json: any): TherapistOutputDTO {
  return TherapistOutputDTOToJSONTyped(json, false)
}

export function TherapistOutputDTOToJSONTyped(
  value?: TherapistOutputDTO | null,
  ignoreDiscriminator: boolean = false
): any {
  if (value == null) {
    return value
  }

  return {
    id: value['id'],
    email: value['email'],
    workspaceId: value['workspaceId'],
    chatbotTemplatesOutputDTO:
      value['chatbotTemplatesOutputDTO'] == null
        ? undefined
        : (value['chatbotTemplatesOutputDTO'] as Array<any>).map(ChatbotTemplateOutputDTOToJSON),
    patientsOutputDTO:
      value['patientsOutputDTO'] == null
        ? undefined
        : (value['patientsOutputDTO'] as Array<any>).map(PatientOutputDTOToJSON),
    createdAt: value['createdAt'] == null ? undefined : value['createdAt'].toISOString(),
    updatedAt: value['updatedAt'] == null ? undefined : value['updatedAt'].toISOString(),
  }
}
