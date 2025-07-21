package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.PsychologicalTestInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PsychologicalTestNameAndPatientIdOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PsychologicalTestOutputDTOPatientAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class PsychologicalTestControllerPatientAPI {
    private ApiClient apiClient;

    public PsychologicalTestControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public PsychologicalTestControllerPatientAPI(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param psychologicalTestInputDTOPatientAPI The psychologicalTestInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createPsychologicalTestRequestCreation(PsychologicalTestInputDTOPatientAPI psychologicalTestInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = psychologicalTestInputDTOPatientAPI;
        // verify the required parameter 'psychologicalTestInputDTOPatientAPI' is set
        if (psychologicalTestInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'psychologicalTestInputDTOPatientAPI' when calling createPsychologicalTest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/patients/tests", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param psychologicalTestInputDTOPatientAPI The psychologicalTestInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> createPsychologicalTest(PsychologicalTestInputDTOPatientAPI psychologicalTestInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createPsychologicalTestRequestCreation(psychologicalTestInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param psychologicalTestInputDTOPatientAPI The psychologicalTestInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> createPsychologicalTestWithHttpInfo(PsychologicalTestInputDTOPatientAPI psychologicalTestInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createPsychologicalTestRequestCreation(psychologicalTestInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param psychologicalTestInputDTOPatientAPI The psychologicalTestInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createPsychologicalTestWithResponseSpec(PsychologicalTestInputDTOPatientAPI psychologicalTestInputDTOPatientAPI) throws WebClientResponseException {
        return createPsychologicalTestRequestCreation(psychologicalTestInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;PsychologicalTestNameAndPatientIdOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPsychologicalTestNamesRequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getPsychologicalTestNames", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/{patientId}/psychological-tests", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;PsychologicalTestNameAndPatientIdOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI> getPsychologicalTestNames(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI>() {};
        return getPsychologicalTestNamesRequestCreation(patientId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseEntity&lt;List&lt;PsychologicalTestNameAndPatientIdOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI>>> getPsychologicalTestNamesWithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameAndPatientIdOutputDTOPatientAPI>() {};
        return getPsychologicalTestNamesRequestCreation(patientId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPsychologicalTestNamesWithResponseSpec(String patientId) throws WebClientResponseException {
        return getPsychologicalTestNamesRequestCreation(patientId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @return List&lt;PsychologicalTestOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPsychologicalTestResultsRequestCreation(String patientId, String psychologicalTestName) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getPsychologicalTestResults", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'psychologicalTestName' is set
        if (psychologicalTestName == null) {
            throw new WebClientResponseException("Missing the required parameter 'psychologicalTestName' when calling getPsychologicalTestResults", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("psychologicalTestName", psychologicalTestName);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/{patientId}/psychological-tests/{psychologicalTestName}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @return List&lt;PsychologicalTestOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<PsychologicalTestOutputDTOPatientAPI> getPsychologicalTestResults(String patientId, String psychologicalTestName) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI>() {};
        return getPsychologicalTestResultsRequestCreation(patientId, psychologicalTestName).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @return ResponseEntity&lt;List&lt;PsychologicalTestOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<PsychologicalTestOutputDTOPatientAPI>>> getPsychologicalTestResultsWithHttpInfo(String patientId, String psychologicalTestName) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI>() {};
        return getPsychologicalTestResultsRequestCreation(patientId, psychologicalTestName).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPsychologicalTestResultsWithResponseSpec(String patientId, String psychologicalTestName) throws WebClientResponseException {
        return getPsychologicalTestResultsRequestCreation(patientId, psychologicalTestName);
    }
}
