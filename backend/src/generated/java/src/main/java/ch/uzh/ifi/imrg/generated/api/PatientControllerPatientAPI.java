package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.model.CreatePatientDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.LoginPatientDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PatientOutputDTOPatientAPI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.4.0")
public class PatientControllerPatientAPI {
  private ApiClient apiClient;

  public PatientControllerPatientAPI() {
    this(new ApiClient());
  }

  @Autowired
  public PatientControllerPatientAPI(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * <b>200</b> - OK
   *
   * @return PatientOutputDTOPatientAPI
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  private ResponseSpec getCurrentlyLoggedInPatientRequestCreation()
      throws WebClientResponseException {
    Object postBody = null;
    // create path and map variables
    final Map<String, Object> pathParams = new HashMap<String, Object>();

    final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
    final HttpHeaders headerParams = new HttpHeaders();
    final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
    final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"*/*"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return apiClient.invokeAPI(
        "/patients/me",
        HttpMethod.GET,
        pathParams,
        queryParams,
        postBody,
        headerParams,
        cookieParams,
        formParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @return PatientOutputDTOPatientAPI
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<PatientOutputDTOPatientAPI> getCurrentlyLoggedInPatient()
      throws WebClientResponseException {
    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return getCurrentlyLoggedInPatientRequestCreation().bodyToMono(localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> getCurrentlyLoggedInPatientWithHttpInfo()
      throws WebClientResponseException {
    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return getCurrentlyLoggedInPatientRequestCreation().toEntity(localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @return ResponseSpec
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public ResponseSpec getCurrentlyLoggedInPatientWithResponseSpec()
      throws WebClientResponseException {
    return getCurrentlyLoggedInPatientRequestCreation();
  }

  /**
   * <b>200</b> - OK
   *
   * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
   * @return PatientOutputDTOPatientAPI
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  private ResponseSpec loginTherapistRequestCreation(
      LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
    Object postBody = loginPatientDTOPatientAPI;
    // verify the required parameter 'loginPatientDTOPatientAPI' is set
    if (loginPatientDTOPatientAPI == null) {
      throw new WebClientResponseException(
          "Missing the required parameter 'loginPatientDTOPatientAPI' when calling loginTherapist",
          HttpStatus.BAD_REQUEST.value(),
          HttpStatus.BAD_REQUEST.getReasonPhrase(),
          null,
          null,
          null);
    }
    // create path and map variables
    final Map<String, Object> pathParams = new HashMap<String, Object>();

    final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
    final HttpHeaders headerParams = new HttpHeaders();
    final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
    final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"*/*"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return apiClient.invokeAPI(
        "/patients/login",
        HttpMethod.POST,
        pathParams,
        queryParams,
        postBody,
        headerParams,
        cookieParams,
        formParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
   * @return PatientOutputDTOPatientAPI
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<PatientOutputDTOPatientAPI> loginTherapist(
      LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return loginTherapistRequestCreation(loginPatientDTOPatientAPI).bodyToMono(localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
   * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> loginTherapistWithHttpInfo(
      LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return loginTherapistRequestCreation(loginPatientDTOPatientAPI).toEntity(localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
   * @return ResponseSpec
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public ResponseSpec loginTherapistWithResponseSpec(
      LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
    return loginTherapistRequestCreation(loginPatientDTOPatientAPI);
  }

  /**
   * <b>200</b> - OK
   *
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  private ResponseSpec logoutTherapistRequestCreation() throws WebClientResponseException {
    Object postBody = null;
    // create path and map variables
    final Map<String, Object> pathParams = new HashMap<String, Object>();

    final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
    final HttpHeaders headerParams = new HttpHeaders();
    final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
    final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
    return apiClient.invokeAPI(
        "/patients/logout",
        HttpMethod.POST,
        pathParams,
        queryParams,
        postBody,
        headerParams,
        cookieParams,
        formParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<Void> logoutTherapist() throws WebClientResponseException {
    ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
    return logoutTherapistRequestCreation().bodyToMono(localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<ResponseEntity<Void>> logoutTherapistWithHttpInfo()
      throws WebClientResponseException {
    ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
    return logoutTherapistRequestCreation().toEntity(localVarReturnType);
  }

  /**
   * <b>200</b> - OK
   *
   * @return ResponseSpec
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public ResponseSpec logoutTherapistWithResponseSpec() throws WebClientResponseException {
    return logoutTherapistRequestCreation();
  }

  /**
   * <b>201</b> - Created
   *
   * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
   * @return PatientOutputDTOPatientAPI
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  private ResponseSpec registerPatientRequestCreation(
      CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
    Object postBody = createPatientDTOPatientAPI;
    // verify the required parameter 'createPatientDTOPatientAPI' is set
    if (createPatientDTOPatientAPI == null) {
      throw new WebClientResponseException(
          "Missing the required parameter 'createPatientDTOPatientAPI' when calling registerPatient",
          HttpStatus.BAD_REQUEST.value(),
          HttpStatus.BAD_REQUEST.getReasonPhrase(),
          null,
          null,
          null);
    }
    // create path and map variables
    final Map<String, Object> pathParams = new HashMap<String, Object>();

    final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
    final HttpHeaders headerParams = new HttpHeaders();
    final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
    final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"*/*"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return apiClient.invokeAPI(
        "/patients/register",
        HttpMethod.POST,
        pathParams,
        queryParams,
        postBody,
        headerParams,
        cookieParams,
        formParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * <b>201</b> - Created
   *
   * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
   * @return PatientOutputDTOPatientAPI
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<PatientOutputDTOPatientAPI> registerPatient(
      CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return registerPatientRequestCreation(createPatientDTOPatientAPI)
        .bodyToMono(localVarReturnType);
  }

  /**
   * <b>201</b> - Created
   *
   * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
   * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> registerPatientWithHttpInfo(
      CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
    ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType =
        new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
    return registerPatientRequestCreation(createPatientDTOPatientAPI).toEntity(localVarReturnType);
  }

  /**
   * <b>201</b> - Created
   *
   * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
   * @return ResponseSpec
   * @throws WebClientResponseException if an error occurs while attempting to invoke the API
   */
  public ResponseSpec registerPatientWithResponseSpec(
      CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
    return registerPatientRequestCreation(createPatientDTOPatientAPI);
  }
}
