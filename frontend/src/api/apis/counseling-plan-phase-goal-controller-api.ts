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
import type { CounselingPlanPhaseGoalOutputDTO } from '../models';
// @ts-ignore
import type { CreateCounselingPlanPhaseGoalAIGeneratedDTO } from '../models';
// @ts-ignore
import type { CreateCounselingPlanPhaseGoalDTO } from '../models';
/**
 * CounselingPlanPhaseGoalControllerApi - axios parameter creator
 * @export
 */
export const CounselingPlanPhaseGoalControllerApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createCounselingPlanPhaseGoal: async (createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'createCounselingPlanPhaseGoalDTO' is not null or undefined
            assertParamExists('createCounselingPlanPhaseGoal', 'createCounselingPlanPhaseGoalDTO', createCounselingPlanPhaseGoalDTO)
            const localVarPath = `/counseling-plan-phase-goals/`;
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
            localVarRequestOptions.data = serializeDataIfNeeded(createCounselingPlanPhaseGoalDTO, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {CreateCounselingPlanPhaseGoalAIGeneratedDTO} createCounselingPlanPhaseGoalAIGeneratedDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createCounselingPlanPhaseGoalAIGenerated: async (createCounselingPlanPhaseGoalAIGeneratedDTO: CreateCounselingPlanPhaseGoalAIGeneratedDTO, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'createCounselingPlanPhaseGoalAIGeneratedDTO' is not null or undefined
            assertParamExists('createCounselingPlanPhaseGoalAIGenerated', 'createCounselingPlanPhaseGoalAIGeneratedDTO', createCounselingPlanPhaseGoalAIGeneratedDTO)
            const localVarPath = `/counseling-plan-phase-goals/{counselingPlanPhaseId}`;
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
            localVarRequestOptions.data = serializeDataIfNeeded(createCounselingPlanPhaseGoalAIGeneratedDTO, localVarRequestOptions, configuration)

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
        deleteCounselingPlanPhaseGoal: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('deleteCounselingPlanPhaseGoal', 'id', id)
            const localVarPath = `/counseling-plan-phase-goals/{id}`
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
        getCounselingPlanPhaseGoalById: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('getCounselingPlanPhaseGoalById', 'id', id)
            const localVarPath = `/counseling-plan-phase-goals/{id}`
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
         * @param {string} id 
         * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        updateCounselingPlanPhase1: async (id: string, createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('updateCounselingPlanPhase1', 'id', id)
            // verify required parameter 'createCounselingPlanPhaseGoalDTO' is not null or undefined
            assertParamExists('updateCounselingPlanPhase1', 'createCounselingPlanPhaseGoalDTO', createCounselingPlanPhaseGoalDTO)
            const localVarPath = `/counseling-plan-phase-goals/{id}`
                .replace(`{${"id"}}`, encodeURIComponent(String(id)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'PUT', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(createCounselingPlanPhaseGoalDTO, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * CounselingPlanPhaseGoalControllerApi - functional programming interface
 * @export
 */
export const CounselingPlanPhaseGoalControllerApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = CounselingPlanPhaseGoalControllerApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async createCounselingPlanPhaseGoal(createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<CounselingPlanPhaseGoalOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.createCounselingPlanPhaseGoal(createCounselingPlanPhaseGoalDTO, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['CounselingPlanPhaseGoalControllerApi.createCounselingPlanPhaseGoal']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {CreateCounselingPlanPhaseGoalAIGeneratedDTO} createCounselingPlanPhaseGoalAIGeneratedDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async createCounselingPlanPhaseGoalAIGenerated(createCounselingPlanPhaseGoalAIGeneratedDTO: CreateCounselingPlanPhaseGoalAIGeneratedDTO, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<CreateCounselingPlanPhaseGoalDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.createCounselingPlanPhaseGoalAIGenerated(createCounselingPlanPhaseGoalAIGeneratedDTO, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['CounselingPlanPhaseGoalControllerApi.createCounselingPlanPhaseGoalAIGenerated']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async deleteCounselingPlanPhaseGoal(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.deleteCounselingPlanPhaseGoal(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['CounselingPlanPhaseGoalControllerApi.deleteCounselingPlanPhaseGoal']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getCounselingPlanPhaseGoalById(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<CounselingPlanPhaseGoalOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getCounselingPlanPhaseGoalById(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['CounselingPlanPhaseGoalControllerApi.getCounselingPlanPhaseGoalById']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async updateCounselingPlanPhase1(id: string, createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<CounselingPlanPhaseGoalOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.updateCounselingPlanPhase1(id, createCounselingPlanPhaseGoalDTO, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['CounselingPlanPhaseGoalControllerApi.updateCounselingPlanPhase1']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * CounselingPlanPhaseGoalControllerApi - factory interface
 * @export
 */
export const CounselingPlanPhaseGoalControllerApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = CounselingPlanPhaseGoalControllerApiFp(configuration)
    return {
        /**
         * 
         * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createCounselingPlanPhaseGoal(createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig): AxiosPromise<CounselingPlanPhaseGoalOutputDTO> {
            return localVarFp.createCounselingPlanPhaseGoal(createCounselingPlanPhaseGoalDTO, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {CreateCounselingPlanPhaseGoalAIGeneratedDTO} createCounselingPlanPhaseGoalAIGeneratedDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createCounselingPlanPhaseGoalAIGenerated(createCounselingPlanPhaseGoalAIGeneratedDTO: CreateCounselingPlanPhaseGoalAIGeneratedDTO, options?: RawAxiosRequestConfig): AxiosPromise<CreateCounselingPlanPhaseGoalDTO> {
            return localVarFp.createCounselingPlanPhaseGoalAIGenerated(createCounselingPlanPhaseGoalAIGeneratedDTO, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteCounselingPlanPhaseGoal(id: string, options?: RawAxiosRequestConfig): AxiosPromise<void> {
            return localVarFp.deleteCounselingPlanPhaseGoal(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getCounselingPlanPhaseGoalById(id: string, options?: RawAxiosRequestConfig): AxiosPromise<CounselingPlanPhaseGoalOutputDTO> {
            return localVarFp.getCounselingPlanPhaseGoalById(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        updateCounselingPlanPhase1(id: string, createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig): AxiosPromise<CounselingPlanPhaseGoalOutputDTO> {
            return localVarFp.updateCounselingPlanPhase1(id, createCounselingPlanPhaseGoalDTO, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * CounselingPlanPhaseGoalControllerApi - interface
 * @export
 * @interface CounselingPlanPhaseGoalControllerApi
 */
export interface CounselingPlanPhaseGoalControllerApiInterface {
    /**
     * 
     * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApiInterface
     */
    createCounselingPlanPhaseGoal(createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig): AxiosPromise<CounselingPlanPhaseGoalOutputDTO>;

    /**
     * 
     * @param {CreateCounselingPlanPhaseGoalAIGeneratedDTO} createCounselingPlanPhaseGoalAIGeneratedDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApiInterface
     */
    createCounselingPlanPhaseGoalAIGenerated(createCounselingPlanPhaseGoalAIGeneratedDTO: CreateCounselingPlanPhaseGoalAIGeneratedDTO, options?: RawAxiosRequestConfig): AxiosPromise<CreateCounselingPlanPhaseGoalDTO>;

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApiInterface
     */
    deleteCounselingPlanPhaseGoal(id: string, options?: RawAxiosRequestConfig): AxiosPromise<void>;

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApiInterface
     */
    getCounselingPlanPhaseGoalById(id: string, options?: RawAxiosRequestConfig): AxiosPromise<CounselingPlanPhaseGoalOutputDTO>;

    /**
     * 
     * @param {string} id 
     * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApiInterface
     */
    updateCounselingPlanPhase1(id: string, createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig): AxiosPromise<CounselingPlanPhaseGoalOutputDTO>;

}

/**
 * CounselingPlanPhaseGoalControllerApi - object-oriented interface
 * @export
 * @class CounselingPlanPhaseGoalControllerApi
 * @extends {BaseAPI}
 */
export class CounselingPlanPhaseGoalControllerApi extends BaseAPI implements CounselingPlanPhaseGoalControllerApiInterface {
    /**
     * 
     * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApi
     */
    public createCounselingPlanPhaseGoal(createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig) {
        return CounselingPlanPhaseGoalControllerApiFp(this.configuration).createCounselingPlanPhaseGoal(createCounselingPlanPhaseGoalDTO, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {CreateCounselingPlanPhaseGoalAIGeneratedDTO} createCounselingPlanPhaseGoalAIGeneratedDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApi
     */
    public createCounselingPlanPhaseGoalAIGenerated(createCounselingPlanPhaseGoalAIGeneratedDTO: CreateCounselingPlanPhaseGoalAIGeneratedDTO, options?: RawAxiosRequestConfig) {
        return CounselingPlanPhaseGoalControllerApiFp(this.configuration).createCounselingPlanPhaseGoalAIGenerated(createCounselingPlanPhaseGoalAIGeneratedDTO, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApi
     */
    public deleteCounselingPlanPhaseGoal(id: string, options?: RawAxiosRequestConfig) {
        return CounselingPlanPhaseGoalControllerApiFp(this.configuration).deleteCounselingPlanPhaseGoal(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApi
     */
    public getCounselingPlanPhaseGoalById(id: string, options?: RawAxiosRequestConfig) {
        return CounselingPlanPhaseGoalControllerApiFp(this.configuration).getCounselingPlanPhaseGoalById(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {CreateCounselingPlanPhaseGoalDTO} createCounselingPlanPhaseGoalDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof CounselingPlanPhaseGoalControllerApi
     */
    public updateCounselingPlanPhase1(id: string, createCounselingPlanPhaseGoalDTO: CreateCounselingPlanPhaseGoalDTO, options?: RawAxiosRequestConfig) {
        return CounselingPlanPhaseGoalControllerApiFp(this.configuration).updateCounselingPlanPhase1(id, createCounselingPlanPhaseGoalDTO, options).then((request) => request(this.axios, this.basePath));
    }
}

