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
import type { CreateExerciseDTO } from '../models';
// @ts-ignore
import type { ExerciseOutputDTO } from '../models';
// @ts-ignore
import type { UpdateExerciseDTO } from '../models';
/**
 * ExerciseControllerApi - axios parameter creator
 * @export
 */
export const ExerciseControllerApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @param {CreateExerciseDTO} createExerciseDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createExercise: async (createExerciseDTO: CreateExerciseDTO, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'createExerciseDTO' is not null or undefined
            assertParamExists('createExercise', 'createExerciseDTO', createExerciseDTO)
            const localVarPath = `/exercises/`;
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
            localVarRequestOptions.data = serializeDataIfNeeded(createExerciseDTO, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} exerciseId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteExercise: async (exerciseId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'exerciseId' is not null or undefined
            assertParamExists('deleteExercise', 'exerciseId', exerciseId)
            const localVarPath = `/exercises/{exerciseId}`
                .replace(`{${"exerciseId"}}`, encodeURIComponent(String(exerciseId)));
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
         * @param {string} patientId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllExercisesOfPatient: async (patientId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'patientId' is not null or undefined
            assertParamExists('getAllExercisesOfPatient', 'patientId', patientId)
            const localVarPath = `/exercises/patient/{patientId}`
                .replace(`{${"patientId"}}`, encodeURIComponent(String(patientId)));
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
         * @param {string} exerciseId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getExerciseById: async (exerciseId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'exerciseId' is not null or undefined
            assertParamExists('getExerciseById', 'exerciseId', exerciseId)
            const localVarPath = `/exercises/{exerciseId}`
                .replace(`{${"exerciseId"}}`, encodeURIComponent(String(exerciseId)));
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
         * @param {UpdateExerciseDTO} updateExerciseDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        updateExercise: async (updateExerciseDTO: UpdateExerciseDTO, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'updateExerciseDTO' is not null or undefined
            assertParamExists('updateExercise', 'updateExerciseDTO', updateExerciseDTO)
            const localVarPath = `/exercises/`;
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
            localVarRequestOptions.data = serializeDataIfNeeded(updateExerciseDTO, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * ExerciseControllerApi - functional programming interface
 * @export
 */
export const ExerciseControllerApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = ExerciseControllerApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @param {CreateExerciseDTO} createExerciseDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async createExercise(createExerciseDTO: CreateExerciseDTO, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<ExerciseOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.createExercise(createExerciseDTO, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['ExerciseControllerApi.createExercise']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} exerciseId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async deleteExercise(exerciseId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.deleteExercise(exerciseId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['ExerciseControllerApi.deleteExercise']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} patientId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getAllExercisesOfPatient(patientId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<ExerciseOutputDTO>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getAllExercisesOfPatient(patientId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['ExerciseControllerApi.getAllExercisesOfPatient']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} exerciseId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getExerciseById(exerciseId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<ExerciseOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getExerciseById(exerciseId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['ExerciseControllerApi.getExerciseById']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {UpdateExerciseDTO} updateExerciseDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async updateExercise(updateExerciseDTO: UpdateExerciseDTO, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<ExerciseOutputDTO>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.updateExercise(updateExerciseDTO, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['ExerciseControllerApi.updateExercise']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * ExerciseControllerApi - factory interface
 * @export
 */
export const ExerciseControllerApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = ExerciseControllerApiFp(configuration)
    return {
        /**
         * 
         * @param {CreateExerciseDTO} createExerciseDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        createExercise(createExerciseDTO: CreateExerciseDTO, options?: RawAxiosRequestConfig): AxiosPromise<ExerciseOutputDTO> {
            return localVarFp.createExercise(createExerciseDTO, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} exerciseId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteExercise(exerciseId: string, options?: RawAxiosRequestConfig): AxiosPromise<void> {
            return localVarFp.deleteExercise(exerciseId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} patientId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllExercisesOfPatient(patientId: string, options?: RawAxiosRequestConfig): AxiosPromise<Array<ExerciseOutputDTO>> {
            return localVarFp.getAllExercisesOfPatient(patientId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} exerciseId 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getExerciseById(exerciseId: string, options?: RawAxiosRequestConfig): AxiosPromise<ExerciseOutputDTO> {
            return localVarFp.getExerciseById(exerciseId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {UpdateExerciseDTO} updateExerciseDTO 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        updateExercise(updateExerciseDTO: UpdateExerciseDTO, options?: RawAxiosRequestConfig): AxiosPromise<ExerciseOutputDTO> {
            return localVarFp.updateExercise(updateExerciseDTO, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * ExerciseControllerApi - interface
 * @export
 * @interface ExerciseControllerApi
 */
export interface ExerciseControllerApiInterface {
    /**
     * 
     * @param {CreateExerciseDTO} createExerciseDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApiInterface
     */
    createExercise(createExerciseDTO: CreateExerciseDTO, options?: RawAxiosRequestConfig): AxiosPromise<ExerciseOutputDTO>;

    /**
     * 
     * @param {string} exerciseId 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApiInterface
     */
    deleteExercise(exerciseId: string, options?: RawAxiosRequestConfig): AxiosPromise<void>;

    /**
     * 
     * @param {string} patientId 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApiInterface
     */
    getAllExercisesOfPatient(patientId: string, options?: RawAxiosRequestConfig): AxiosPromise<Array<ExerciseOutputDTO>>;

    /**
     * 
     * @param {string} exerciseId 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApiInterface
     */
    getExerciseById(exerciseId: string, options?: RawAxiosRequestConfig): AxiosPromise<ExerciseOutputDTO>;

    /**
     * 
     * @param {UpdateExerciseDTO} updateExerciseDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApiInterface
     */
    updateExercise(updateExerciseDTO: UpdateExerciseDTO, options?: RawAxiosRequestConfig): AxiosPromise<ExerciseOutputDTO>;

}

/**
 * ExerciseControllerApi - object-oriented interface
 * @export
 * @class ExerciseControllerApi
 * @extends {BaseAPI}
 */
export class ExerciseControllerApi extends BaseAPI implements ExerciseControllerApiInterface {
    /**
     * 
     * @param {CreateExerciseDTO} createExerciseDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApi
     */
    public createExercise(createExerciseDTO: CreateExerciseDTO, options?: RawAxiosRequestConfig) {
        return ExerciseControllerApiFp(this.configuration).createExercise(createExerciseDTO, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} exerciseId 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApi
     */
    public deleteExercise(exerciseId: string, options?: RawAxiosRequestConfig) {
        return ExerciseControllerApiFp(this.configuration).deleteExercise(exerciseId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} patientId 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApi
     */
    public getAllExercisesOfPatient(patientId: string, options?: RawAxiosRequestConfig) {
        return ExerciseControllerApiFp(this.configuration).getAllExercisesOfPatient(patientId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} exerciseId 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApi
     */
    public getExerciseById(exerciseId: string, options?: RawAxiosRequestConfig) {
        return ExerciseControllerApiFp(this.configuration).getExerciseById(exerciseId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {UpdateExerciseDTO} updateExerciseDTO 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof ExerciseControllerApi
     */
    public updateExercise(updateExerciseDTO: UpdateExerciseDTO, options?: RawAxiosRequestConfig) {
        return ExerciseControllerApiFp(this.configuration).updateExercise(updateExerciseDTO, options).then((request) => request(this.axios, this.basePath));
    }
}

