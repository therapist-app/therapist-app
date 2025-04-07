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
import type { CreateGAD7TestDTO, GAD7TestOutputDTO } from '../models/index'
import {
  CreateGAD7TestDTOFromJSON,
  CreateGAD7TestDTOToJSON,
  GAD7TestOutputDTOFromJSON,
  GAD7TestOutputDTOToJSON,
} from '../models/index'

export interface CreateTestRequest {
  createGAD7TestDTO: CreateGAD7TestDTO
}

export interface GetTestByIdRequest {
  testId: string
}

export interface GetTestsForPatientRequest {
  patientId: string
}

/**
 * PatientTestControllerApi - interface
 *
 * @export
 * @interface PatientTestControllerApiInterface
 */
export interface PatientTestControllerApiInterface {
  /**
   *
   * @param {CreateGAD7TestDTO} createGAD7TestDTO
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof PatientTestControllerApiInterface
   */
  createTestRaw(
    requestParameters: CreateTestRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<GAD7TestOutputDTO>>

  /**
   */
  createTest(
    requestParameters: CreateTestRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<GAD7TestOutputDTO>

  /**
   *
   * @param {string} testId
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof PatientTestControllerApiInterface
   */
  getTestByIdRaw(
    requestParameters: GetTestByIdRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<GAD7TestOutputDTO>>

  /**
   */
  getTestById(
    requestParameters: GetTestByIdRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<GAD7TestOutputDTO>

  /**
   *
   * @param {string} patientId
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof PatientTestControllerApiInterface
   */
  getTestsForPatientRaw(
    requestParameters: GetTestsForPatientRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<Array<GAD7TestOutputDTO>>>

  /**
   */
  getTestsForPatient(
    requestParameters: GetTestsForPatientRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<Array<GAD7TestOutputDTO>>
}

/**
 *
 */
export class PatientTestControllerApi
  extends runtime.BaseAPI
  implements PatientTestControllerApiInterface
{
  /**
   */
  async createTestRaw(
    requestParameters: CreateTestRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<GAD7TestOutputDTO>> {
    if (requestParameters['createGAD7TestDTO'] == null) {
      throw new runtime.RequiredError(
        'createGAD7TestDTO',
        'Required parameter "createGAD7TestDTO" was null or undefined when calling createTest().'
      )
    }

    const queryParameters: any = {}

    const headerParameters: runtime.HTTPHeaders = {}

    headerParameters['Content-Type'] = 'application/json'

    const response = await this.request(
      {
        path: `/tests/gad7`,
        method: 'POST',
        headers: headerParameters,
        query: queryParameters,
        body: CreateGAD7TestDTOToJSON(requestParameters['createGAD7TestDTO']),
      },
      initOverrides
    )

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      GAD7TestOutputDTOFromJSON(jsonValue)
    )
  }

  /**
   */
  async createTest(
    requestParameters: CreateTestRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<GAD7TestOutputDTO> {
    const response = await this.createTestRaw(requestParameters, initOverrides)
    return await response.value()
  }

  /**
   */
  async getTestByIdRaw(
    requestParameters: GetTestByIdRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<GAD7TestOutputDTO>> {
    if (requestParameters['testId'] == null) {
      throw new runtime.RequiredError(
        'testId',
        'Required parameter "testId" was null or undefined when calling getTestById().'
      )
    }

    const queryParameters: any = {}

    const headerParameters: runtime.HTTPHeaders = {}

    const response = await this.request(
      {
        path: `/tests/gad7/{testId}`.replace(
          `{${'testId'}}`,
          encodeURIComponent(String(requestParameters['testId']))
        ),
        method: 'GET',
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    )

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      GAD7TestOutputDTOFromJSON(jsonValue)
    )
  }

  /**
   */
  async getTestById(
    requestParameters: GetTestByIdRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<GAD7TestOutputDTO> {
    const response = await this.getTestByIdRaw(requestParameters, initOverrides)
    return await response.value()
  }

  /**
   */
  async getTestsForPatientRaw(
    requestParameters: GetTestsForPatientRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<runtime.ApiResponse<Array<GAD7TestOutputDTO>>> {
    if (requestParameters['patientId'] == null) {
      throw new runtime.RequiredError(
        'patientId',
        'Required parameter "patientId" was null or undefined when calling getTestsForPatient().'
      )
    }

    const queryParameters: any = {}

    const headerParameters: runtime.HTTPHeaders = {}

    const response = await this.request(
      {
        path: `/tests/gad7/patient/{patientId}`.replace(
          `{${'patientId'}}`,
          encodeURIComponent(String(requestParameters['patientId']))
        ),
        method: 'GET',
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    )

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      jsonValue.map(GAD7TestOutputDTOFromJSON)
    )
  }

  /**
   */
  async getTestsForPatient(
    requestParameters: GetTestsForPatientRequest,
    initOverrides?: RequestInit | runtime.InitOverrideFunction
  ): Promise<Array<GAD7TestOutputDTO>> {
    const response = await this.getTestsForPatientRaw(requestParameters, initOverrides)
    return await response.value()
  }
}
