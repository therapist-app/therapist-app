package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.ExerciseComponentInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseComponentOverviewOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseComponentUpdateInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseInformationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseUpdateInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExercisesOverviewOutputDTOPatientAPI;

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
public class CoachExerciseControllerPatientAPI {
    private ApiClient apiClient;

    public CoachExerciseControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public CoachExerciseControllerPatientAPI(ApiClient apiClient) {
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
     * @param exerciseInputDTOPatientAPI The exerciseInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createExerciseRequestCreation(String patientId, ExerciseInputDTOPatientAPI exerciseInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseInputDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling createExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseInputDTOPatientAPI' is set
        if (exerciseInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseInputDTOPatientAPI' when calling createExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);

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
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param exerciseInputDTOPatientAPI The exerciseInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> createExercise(String patientId, ExerciseInputDTOPatientAPI exerciseInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createExerciseRequestCreation(patientId, exerciseInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param exerciseInputDTOPatientAPI The exerciseInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> createExerciseWithHttpInfo(String patientId, ExerciseInputDTOPatientAPI exerciseInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createExerciseRequestCreation(patientId, exerciseInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param exerciseInputDTOPatientAPI The exerciseInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createExerciseWithResponseSpec(String patientId, ExerciseInputDTOPatientAPI exerciseInputDTOPatientAPI) throws WebClientResponseException {
        return createExerciseRequestCreation(patientId, exerciseInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentInputDTOPatientAPI The exerciseComponentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createExerciseComponentRequestCreation(String patientId, String exerciseId, ExerciseComponentInputDTOPatientAPI exerciseComponentInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseComponentInputDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling createExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling createExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentInputDTOPatientAPI' is set
        if (exerciseComponentInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentInputDTOPatientAPI' when calling createExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("exerciseId", exerciseId);

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
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises/{exerciseId}/exercise-components", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentInputDTOPatientAPI The exerciseComponentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> createExerciseComponent(String patientId, String exerciseId, ExerciseComponentInputDTOPatientAPI exerciseComponentInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentInputDTOPatientAPI The exerciseComponentInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> createExerciseComponentWithHttpInfo(String patientId, String exerciseId, ExerciseComponentInputDTOPatientAPI exerciseComponentInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentInputDTOPatientAPI The exerciseComponentInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createExerciseComponentWithResponseSpec(String patientId, String exerciseId, ExerciseComponentInputDTOPatientAPI exerciseComponentInputDTOPatientAPI) throws WebClientResponseException {
        return createExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteExerciseRequestCreation(String patientId, String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling deleteExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling deleteExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("exerciseId", exerciseId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises/{exerciseId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteExercise(String patientId, String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteExerciseRequestCreation(patientId, exerciseId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteExerciseWithHttpInfo(String patientId, String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteExerciseRequestCreation(patientId, exerciseId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteExerciseWithResponseSpec(String patientId, String exerciseId) throws WebClientResponseException {
        return deleteExerciseRequestCreation(patientId, exerciseId);
    }
    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteExerciseComponentRequestCreation(String patientId, String exerciseId, String exerciseComponentId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling deleteExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling deleteExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentId' is set
        if (exerciseComponentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentId' when calling deleteExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("exerciseId", exerciseId);
        pathParams.put("exerciseComponentId", exerciseComponentId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises/{exerciseId}/exercise-components/{exerciseComponentId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteExerciseComponent(String patientId, String exerciseId, String exerciseComponentId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteExerciseComponentWithHttpInfo(String patientId, String exerciseId, String exerciseComponentId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteExerciseComponentWithResponseSpec(String patientId, String exerciseId, String exerciseComponentId) throws WebClientResponseException {
        return deleteExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return List&lt;ExerciseComponentOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllExerciseComponentsRequestCreation(String patientId, String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getAllExerciseComponents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getAllExerciseComponents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("exerciseId", exerciseId);

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

        ParameterizedTypeReference<ExerciseComponentOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseComponentOverviewOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises/{exerciseId}/exercise-components", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return List&lt;ExerciseComponentOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ExerciseComponentOverviewOutputDTOPatientAPI> getAllExerciseComponents(String patientId, String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseComponentOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseComponentOverviewOutputDTOPatientAPI>() {};
        return getAllExerciseComponentsRequestCreation(patientId, exerciseId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return ResponseEntity&lt;List&lt;ExerciseComponentOverviewOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ExerciseComponentOverviewOutputDTOPatientAPI>>> getAllExerciseComponentsWithHttpInfo(String patientId, String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseComponentOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseComponentOverviewOutputDTOPatientAPI>() {};
        return getAllExerciseComponentsRequestCreation(patientId, exerciseId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllExerciseComponentsWithResponseSpec(String patientId, String exerciseId) throws WebClientResponseException {
        return getAllExerciseComponentsRequestCreation(patientId, exerciseId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;ExercisesOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllExercisesRequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getAllExercises", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;ExercisesOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ExercisesOverviewOutputDTOPatientAPI> getAllExercises(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI>() {};
        return getAllExercisesRequestCreation(patientId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseEntity&lt;List&lt;ExercisesOverviewOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ExercisesOverviewOutputDTOPatientAPI>>> getAllExercisesWithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI>() {};
        return getAllExercisesRequestCreation(patientId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllExercisesWithResponseSpec(String patientId) throws WebClientResponseException {
        return getAllExercisesRequestCreation(patientId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return List&lt;ExerciseInformationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getExerciseInformationRequestCreation(String patientId, String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getExerciseInformation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getExerciseInformation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("exerciseId", exerciseId);

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

        ParameterizedTypeReference<ExerciseInformationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseInformationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises/{exerciseId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return List&lt;ExerciseInformationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ExerciseInformationOutputDTOPatientAPI> getExerciseInformation(String patientId, String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseInformationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseInformationOutputDTOPatientAPI>() {};
        return getExerciseInformationRequestCreation(patientId, exerciseId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return ResponseEntity&lt;List&lt;ExerciseInformationOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ExerciseInformationOutputDTOPatientAPI>>> getExerciseInformationWithHttpInfo(String patientId, String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseInformationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseInformationOutputDTOPatientAPI>() {};
        return getExerciseInformationRequestCreation(patientId, exerciseId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getExerciseInformationWithResponseSpec(String patientId, String exerciseId) throws WebClientResponseException {
        return getExerciseInformationRequestCreation(patientId, exerciseId);
    }
    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseUpdateInputDTOPatientAPI The exerciseUpdateInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateExerciseRequestCreation(String patientId, String exerciseId, ExerciseUpdateInputDTOPatientAPI exerciseUpdateInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseUpdateInputDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling updateExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling updateExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseUpdateInputDTOPatientAPI' is set
        if (exerciseUpdateInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseUpdateInputDTOPatientAPI' when calling updateExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("exerciseId", exerciseId);

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
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises/{exerciseId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseUpdateInputDTOPatientAPI The exerciseUpdateInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> updateExercise(String patientId, String exerciseId, ExerciseUpdateInputDTOPatientAPI exerciseUpdateInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateExerciseRequestCreation(patientId, exerciseId, exerciseUpdateInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseUpdateInputDTOPatientAPI The exerciseUpdateInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> updateExerciseWithHttpInfo(String patientId, String exerciseId, ExerciseUpdateInputDTOPatientAPI exerciseUpdateInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateExerciseRequestCreation(patientId, exerciseId, exerciseUpdateInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseUpdateInputDTOPatientAPI The exerciseUpdateInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateExerciseWithResponseSpec(String patientId, String exerciseId, ExerciseUpdateInputDTOPatientAPI exerciseUpdateInputDTOPatientAPI) throws WebClientResponseException {
        return updateExerciseRequestCreation(patientId, exerciseId, exerciseUpdateInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentUpdateInputDTOPatientAPI The exerciseComponentUpdateInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateExerciseComponentRequestCreation(String patientId, String exerciseId, String exerciseComponentId, ExerciseComponentUpdateInputDTOPatientAPI exerciseComponentUpdateInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseComponentUpdateInputDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling updateExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling updateExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentId' is set
        if (exerciseComponentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentId' when calling updateExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentUpdateInputDTOPatientAPI' is set
        if (exerciseComponentUpdateInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentUpdateInputDTOPatientAPI' when calling updateExerciseComponent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("exerciseId", exerciseId);
        pathParams.put("exerciseComponentId", exerciseComponentId);

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
        return apiClient.invokeAPI("/coach/patients/{patientId}/exercises/{exerciseId}/exercise-components/{exerciseComponentId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentUpdateInputDTOPatientAPI The exerciseComponentUpdateInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> updateExerciseComponent(String patientId, String exerciseId, String exerciseComponentId, ExerciseComponentUpdateInputDTOPatientAPI exerciseComponentUpdateInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentId, exerciseComponentUpdateInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentUpdateInputDTOPatientAPI The exerciseComponentUpdateInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> updateExerciseComponentWithHttpInfo(String patientId, String exerciseId, String exerciseComponentId, ExerciseComponentUpdateInputDTOPatientAPI exerciseComponentUpdateInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentId, exerciseComponentUpdateInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentUpdateInputDTOPatientAPI The exerciseComponentUpdateInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateExerciseComponentWithResponseSpec(String patientId, String exerciseId, String exerciseComponentId, ExerciseComponentUpdateInputDTOPatientAPI exerciseComponentUpdateInputDTOPatientAPI) throws WebClientResponseException {
        return updateExerciseComponentRequestCreation(patientId, exerciseId, exerciseComponentId, exerciseComponentUpdateInputDTOPatientAPI);
    }
}
