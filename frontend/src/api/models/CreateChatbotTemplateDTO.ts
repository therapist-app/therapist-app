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

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface CreateChatbotTemplateDTO
 */
export interface CreateChatbotTemplateDTO {
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    chatbotName?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    description?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    chatbotModel?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    chatbotIcon?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    chatbotLanguage?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    chatbotRole?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    chatbotTone?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    welcomeMessage?: string;
    /**
     * 
     * @type {string}
     * @memberof CreateChatbotTemplateDTO
     */
    workspaceId?: string;
}

/**
 * Check if a given object implements the CreateChatbotTemplateDTO interface.
 */
export function instanceOfCreateChatbotTemplateDTO(value: object): value is CreateChatbotTemplateDTO {
    return true;
}

export function CreateChatbotTemplateDTOFromJSON(json: any): CreateChatbotTemplateDTO {
    return CreateChatbotTemplateDTOFromJSONTyped(json, false);
}

export function CreateChatbotTemplateDTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): CreateChatbotTemplateDTO {
    if (json == null) {
        return json;
    }
    return {
        
        'chatbotName': json['chatbotName'] == null ? undefined : json['chatbotName'],
        'description': json['description'] == null ? undefined : json['description'],
        'chatbotModel': json['chatbotModel'] == null ? undefined : json['chatbotModel'],
        'chatbotIcon': json['chatbotIcon'] == null ? undefined : json['chatbotIcon'],
        'chatbotLanguage': json['chatbotLanguage'] == null ? undefined : json['chatbotLanguage'],
        'chatbotRole': json['chatbotRole'] == null ? undefined : json['chatbotRole'],
        'chatbotTone': json['chatbotTone'] == null ? undefined : json['chatbotTone'],
        'welcomeMessage': json['welcomeMessage'] == null ? undefined : json['welcomeMessage'],
        'workspaceId': json['workspaceId'] == null ? undefined : json['workspaceId'],
    };
}

export function CreateChatbotTemplateDTOToJSON(json: any): CreateChatbotTemplateDTO {
    return CreateChatbotTemplateDTOToJSONTyped(json, false);
}

export function CreateChatbotTemplateDTOToJSONTyped(value?: CreateChatbotTemplateDTO | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'chatbotName': value['chatbotName'],
        'description': value['description'],
        'chatbotModel': value['chatbotModel'],
        'chatbotIcon': value['chatbotIcon'],
        'chatbotLanguage': value['chatbotLanguage'],
        'chatbotRole': value['chatbotRole'],
        'chatbotTone': value['chatbotTone'],
        'welcomeMessage': value['welcomeMessage'],
        'workspaceId': value['workspaceId'],
    };
}

