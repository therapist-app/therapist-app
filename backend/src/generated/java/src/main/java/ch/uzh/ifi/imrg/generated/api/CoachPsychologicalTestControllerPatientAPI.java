package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.PsychologicalTestAssignmentInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PsychologicalTestNameOutputDTOPatientAPI;
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
public class CoachPsychologicalTestControllerPatientAPI {
    private ApiClient apiClient;

    public CoachPsychologicalTestControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public CoachPsychologicalTestControllerPatientAPI(ApiClient apiClient) {
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
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createPsychologicalTest1RequestCreation(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = psychologicalTestAssignmentInputDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling createPsychologicalTest1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'psychologicalTestName' is set
        if (psychologicalTestName == null) {
            throw new WebClientResponseException("Missing the required parameter 'psychologicalTestName' when calling createPsychologicalTest1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'psychologicalTestAssignmentInputDTOPatientAPI' is set
        if (psychologicalTestAssignmentInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'psychologicalTestAssignmentInputDTOPatientAPI' when calling createPsychologicalTest1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("psychologicalTestName", psychologicalTestName);

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
        return apiClient.invokeAPI("/coach/patients/{patientId}/psychological-tests/{psychologicalTestName}", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> createPsychologicalTest1(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createPsychologicalTest1RequestCreation(patientId, psychologicalTestName, psychologicalTestAssignmentInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> createPsychologicalTest1WithHttpInfo(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createPsychologicalTest1RequestCreation(patientId, psychologicalTestName, psychologicalTestAssignmentInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createPsychologicalTest1WithResponseSpec(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        return createPsychologicalTest1RequestCreation(patientId, psychologicalTestName, psychologicalTestAssignmentInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAvailablePsychologicalTestNamesRequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getAvailablePsychologicalTestNames", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/psychological-tests/available-tests", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<PsychologicalTestNameOutputDTOPatientAPI> getAvailablePsychologicalTestNames(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI>() {};
        return getAvailablePsychologicalTestNamesRequestCreation(patientId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseEntity&lt;List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<PsychologicalTestNameOutputDTOPatientAPI>>> getAvailablePsychologicalTestNamesWithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI>() {};
        return getAvailablePsychologicalTestNamesRequestCreation(patientId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAvailablePsychologicalTestNamesWithResponseSpec(String patientId) throws WebClientResponseException {
        return getAvailablePsychologicalTestNamesRequestCreation(patientId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPsychologicalTestNames1RequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getPsychologicalTestNames1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/psychological-tests", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<PsychologicalTestNameOutputDTOPatientAPI> getPsychologicalTestNames1(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI>() {};
        return getPsychologicalTestNames1RequestCreation(patientId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseEntity&lt;List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<PsychologicalTestNameOutputDTOPatientAPI>>> getPsychologicalTestNames1WithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestNameOutputDTOPatientAPI>() {};
        return getPsychologicalTestNames1RequestCreation(patientId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPsychologicalTestNames1WithResponseSpec(String patientId) throws WebClientResponseException {
        return getPsychologicalTestNames1RequestCreation(patientId);
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
    private ResponseSpec getPsychologicalTestResults1RequestCreation(String patientId, String psychologicalTestName) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getPsychologicalTestResults1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'psychologicalTestName' is set
        if (psychologicalTestName == null) {
            throw new WebClientResponseException("Missing the required parameter 'psychologicalTestName' when calling getPsychologicalTestResults1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/coach/patients/{patientId}/psychological-tests/{psychologicalTestName}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
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
    public Flux<PsychologicalTestOutputDTOPatientAPI> getPsychologicalTestResults1(String patientId, String psychologicalTestName) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI>() {};
        return getPsychologicalTestResults1RequestCreation(patientId, psychologicalTestName).bodyToFlux(localVarReturnType);
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
    public Mono<ResponseEntity<List<PsychologicalTestOutputDTOPatientAPI>>> getPsychologicalTestResults1WithHttpInfo(String patientId, String psychologicalTestName) throws WebClientResponseException {
        ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PsychologicalTestOutputDTOPatientAPI>() {};
        return getPsychologicalTestResults1RequestCreation(patientId, psychologicalTestName).toEntityList(localVarReturnType);
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
    public ResponseSpec getPsychologicalTestResults1WithResponseSpec(String patientId, String psychologicalTestName) throws WebClientResponseException {
        return getPsychologicalTestResults1RequestCreation(patientId, psychologicalTestName);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updatePsychologicalTestRequestCreation(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = psychologicalTestAssignmentInputDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling updatePsychologicalTest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'psychologicalTestName' is set
        if (psychologicalTestName == null) {
            throw new WebClientResponseException("Missing the required parameter 'psychologicalTestName' when calling updatePsychologicalTest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'psychologicalTestAssignmentInputDTOPatientAPI' is set
        if (psychologicalTestAssignmentInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'psychologicalTestAssignmentInputDTOPatientAPI' when calling updatePsychologicalTest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("psychologicalTestName", psychologicalTestName);

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
        return apiClient.invokeAPI("/coach/patients/{patientId}/psychological-tests/{psychologicalTestName}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> updatePsychologicalTest(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updatePsychologicalTestRequestCreation(patientId, psychologicalTestName, psychologicalTestAssignmentInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> updatePsychologicalTestWithHttpInfo(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updatePsychologicalTestRequestCreation(patientId, psychologicalTestName, psychologicalTestAssignmentInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param psychologicalTestName The psychologicalTestName parameter
     * @param psychologicalTestAssignmentInputDTOPatientAPI The psychologicalTestAssignmentInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updatePsychologicalTestWithResponseSpec(String patientId, String psychologicalTestName, PsychologicalTestAssignmentInputDTOPatientAPI psychologicalTestAssignmentInputDTOPatientAPI) throws WebClientResponseException {
        return updatePsychologicalTestRequestCreation(patientId, psychologicalTestName, psychologicalTestAssignmentInputDTOPatientAPI);
    }
}
