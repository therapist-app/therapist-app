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
 * @interface CreatePatientDTO
 */
export interface CreatePatientDTO {
    /**
     * 
     * @type {string}
     * @memberof CreatePatientDTO
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof CreatePatientDTO
     */
    gender?: string;
    /**
     * 
     * @type {number}
     * @memberof CreatePatientDTO
     */
    age?: number;
    /**
     * 
     * @type {string}
     * @memberof CreatePatientDTO
     */
    phoneNumber?: string;
    /**
     * 
     * @type {string}
     * @memberof CreatePatientDTO
     */
    email?: string;
    /**
     * 
     * @type {string}
     * @memberof CreatePatientDTO
     */
    address?: string;
    /**
     * 
     * @type {string}
     * @memberof CreatePatientDTO
     */
    description?: string;
}

/**
 * Check if a given object implements the CreatePatientDTO interface.
 */
export function instanceOfCreatePatientDTO(value: object): value is CreatePatientDTO {
    return true;
}

export function CreatePatientDTOFromJSON(json: any): CreatePatientDTO {
    return CreatePatientDTOFromJSONTyped(json, false);
}

export function CreatePatientDTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): CreatePatientDTO {
    if (json == null) {
        return json;
    }
    return {
        
        'name': json['name'] == null ? undefined : json['name'],
        'gender': json['gender'] == null ? undefined : json['gender'],
        'age': json['age'] == null ? undefined : json['age'],
        'phoneNumber': json['phoneNumber'] == null ? undefined : json['phoneNumber'],
        'email': json['email'] == null ? undefined : json['email'],
        'address': json['address'] == null ? undefined : json['address'],
        'description': json['description'] == null ? undefined : json['description'],
    };
}

export function CreatePatientDTOToJSON(json: any): CreatePatientDTO {
    return CreatePatientDTOToJSONTyped(json, false);
}

export function CreatePatientDTOToJSONTyped(value?: CreatePatientDTO | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'name': value['name'],
        'gender': value['gender'],
        'age': value['age'],
        'phoneNumber': value['phoneNumber'],
        'email': value['email'],
        'address': value['address'],
        'description': value['description'],
    };
}

