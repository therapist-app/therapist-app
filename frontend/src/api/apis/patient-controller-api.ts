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


import type { Configuration } from '../configuration';
import type { AxiosPromise, AxiosInstance, RawAxiosRequestConfig } from 'axios';
import globalAxios from 'axios';
// Some imports not used depending on template conditions
// @ts-ignore
import { DUMMY_BASE_URL, assertParamExists, setApiKeyToObject, setBasicAuthToObject, setBearerAuthToObject, setOAuthToObject, setSearchParams, serializeDataIfNeeded, toPathString, createRequestFunction } from '../common';
// @ts-ignore
import { BASE_PATH, COLLECTION_FORMATS, type RequestArgs, BaseAPI, RequiredError, operationServerMap } from '../base';
// @ts-ignore
import type { CreatePatientDTO } from '../models';
// @ts-ignore
import type { PatientOutputDTO } from '../models';
/**
 * PatientControllerApi - axios parameter creator
 * @export
 */
export const PatientControllerApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @param {CreatePatientDTO} createPatientDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createPatientForTherapist: async (createPatientDTO: CreatePatientDTO, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'createPatientDTO' is not null or undefined
            assertParamExists('createPatientForTherapist', 'createPatientDTO', createPatientDTO)
            const localVarPath = `/patients`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(createPatientDTO, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deletePatient: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('deletePatient', 'id', id)
            const localVarPath = `/patients/{id}`
                .replace(`{${"id"}}`, encodeURIComponent(String(id)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'DELETE', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getPatientById: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('getPatientById', 'id', id)
            const localVarPath = `/patients/{id}`
                .replace(`{${"id"}}`, encodeURIComponent(String(id)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getPatientsOfTherapist: async (options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            const localVarPath = `/patients`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * PatientControllerApi - functional programming interface
 * @export
 */
export const PatientControllerApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = PatientControllerApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @param {CreatePatientDTO} createPatientDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async createPatientForTherapist(createPatientDTO: CreatePatientDTO, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<PatientOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.createPatientForTherapist(createPatientDTO, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PatientControllerApi.createPatientForTherapist']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async deletePatient(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.deletePatient(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PatientControllerApi.deletePatient']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getPatientById(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<PatientOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getPatientById(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PatientControllerApi.getPatientById']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getPatientsOfTherapist(options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<PatientOutputDTO>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getPatientsOfTherapist(options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PatientControllerApi.getPatientsOfTherapist']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * PatientControllerApi - factory interface
 * @export
 */
export const PatientControllerApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = PatientControllerApiFp(configuration)
    return {
        /**
         * 
         * @param {CreatePatientDTO} createPatientDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createPatientForTherapist(createPatientDTO: CreatePatientDTO, options?: RawAxiosRequestConfig): AxiosPromise<PatientOutputDTO> {
            return localVarFp.createPatientForTherapist(createPatientDTO, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deletePatient(id: string, options?: RawAxiosRequestConfig): AxiosPromise<void> {
            return localVarFp.deletePatient(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getPatientById(id: string, options?: RawAxiosRequestConfig): AxiosPromise<PatientOutputDTO> {
            return localVarFp.getPatientById(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getPatientsOfTherapist(options?: RawAxiosRequestConfig): AxiosPromise<Array<PatientOutputDTO>> {
            return localVarFp.getPatientsOfTherapist(options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * PatientControllerApi - interface
 * @export
 * @interface PatientControllerApi
 */
export interface PatientControllerApiInterface {
    /**
     * 
     * @param {CreatePatientDTO} createPatientDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApiInterface
     */
    createPatientForTherapist(createPatientDTO: CreatePatientDTO, options?: RawAxiosRequestConfig): AxiosPromise<PatientOutputDTO>;

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApiInterface
     */
    deletePatient(id: string, options?: RawAxiosRequestConfig): AxiosPromise<void>;

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApiInterface
     */
    getPatientById(id: string, options?: RawAxiosRequestConfig): AxiosPromise<PatientOutputDTO>;

    /**
     * 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApiInterface
     */
    getPatientsOfTherapist(options?: RawAxiosRequestConfig): AxiosPromise<Array<PatientOutputDTO>>;

}

/**
 * PatientControllerApi - object-oriented interface
 * @export
 * @class PatientControllerApi
 * @extends {BaseAPI}
 */
export class PatientControllerApi extends BaseAPI implements PatientControllerApiInterface {
    /**
     * 
     * @param {CreatePatientDTO} createPatientDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApi
     */
    public createPatientForTherapist(createPatientDTO: CreatePatientDTO, options?: RawAxiosRequestConfig) {
        return PatientControllerApiFp(this.configuration).createPatientForTherapist(createPatientDTO, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApi
     */
    public deletePatient(id: string, options?: RawAxiosRequestConfig) {
        return PatientControllerApiFp(this.configuration).deletePatient(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApi
     */
    public getPatientById(id: string, options?: RawAxiosRequestConfig) {
        return PatientControllerApiFp(this.configuration).getPatientById(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PatientControllerApi
     */
    public getPatientsOfTherapist(options?: RawAxiosRequestConfig) {
        return PatientControllerApiFp(this.configuration).getPatientsOfTherapist(options).then((request) => request(this.axios, this.basePath));
    }
}

