package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.ExecutionOverviewOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseChatbotOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseCompletionNameInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseComponentResultInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseInformationInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseStartOutputDTOPatientAPI;
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
public class ExerciseControllerPatientAPI {
    private ApiClient apiClient;

    public ExerciseControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public ExerciseControllerPatientAPI(ApiClient apiClient) {
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
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ExerciseChatbotOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getExerciseChatbotRequestCreation(String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getExerciseChatbot", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<ExerciseChatbotOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseChatbotOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/chatbot", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ExerciseChatbotOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ExerciseChatbotOutputDTOPatientAPI> getExerciseChatbot(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseChatbotOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseChatbotOutputDTOPatientAPI>() {};
        return getExerciseChatbotRequestCreation(exerciseId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseEntity&lt;ExerciseChatbotOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ExerciseChatbotOutputDTOPatientAPI>> getExerciseChatbotWithHttpInfo(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseChatbotOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseChatbotOutputDTOPatientAPI>() {};
        return getExerciseChatbotRequestCreation(exerciseId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getExerciseChatbotWithResponseSpec(String exerciseId) throws WebClientResponseException {
        return getExerciseChatbotRequestCreation(exerciseId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseExecutionId The exerciseExecutionId parameter
     * @return ExerciseOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getExerciseExecutionRequestCreation(String exerciseId, String exerciseExecutionId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getExerciseExecution", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseExecutionId' is set
        if (exerciseExecutionId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseExecutionId' when calling getExerciseExecution", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("exerciseId", exerciseId);
        pathParams.put("exerciseExecutionId", exerciseExecutionId);

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

        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/execution-information/{exerciseExecutionId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseExecutionId The exerciseExecutionId parameter
     * @return ExerciseOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ExerciseOutputDTOPatientAPI> getExerciseExecution(String exerciseId, String exerciseExecutionId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return getExerciseExecutionRequestCreation(exerciseId, exerciseExecutionId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseExecutionId The exerciseExecutionId parameter
     * @return ResponseEntity&lt;ExerciseOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ExerciseOutputDTOPatientAPI>> getExerciseExecutionWithHttpInfo(String exerciseId, String exerciseExecutionId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return getExerciseExecutionRequestCreation(exerciseId, exerciseExecutionId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseExecutionId The exerciseExecutionId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getExerciseExecutionWithResponseSpec(String exerciseId, String exerciseExecutionId) throws WebClientResponseException {
        return getExerciseExecutionRequestCreation(exerciseId, exerciseExecutionId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;ExercisesOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getExerciseOverviewRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/exercises", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;ExercisesOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ExercisesOverviewOutputDTOPatientAPI> getExerciseOverview() throws WebClientResponseException {
        ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI>() {};
        return getExerciseOverviewRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;ExercisesOverviewOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ExercisesOverviewOutputDTOPatientAPI>>> getExerciseOverviewWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExercisesOverviewOutputDTOPatientAPI>() {};
        return getExerciseOverviewRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getExerciseOverviewWithResponseSpec() throws WebClientResponseException {
        return getExerciseOverviewRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return List&lt;ExecutionOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOneExerciseOverviewRequestCreation(String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getOneExerciseOverview", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<ExecutionOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExecutionOverviewOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return List&lt;ExecutionOverviewOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ExecutionOverviewOutputDTOPatientAPI> getOneExerciseOverview(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExecutionOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExecutionOverviewOutputDTOPatientAPI>() {};
        return getOneExerciseOverviewRequestCreation(exerciseId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseEntity&lt;List&lt;ExecutionOverviewOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ExecutionOverviewOutputDTOPatientAPI>>> getOneExerciseOverviewWithHttpInfo(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExecutionOverviewOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExecutionOverviewOutputDTOPatientAPI>() {};
        return getOneExerciseOverviewRequestCreation(exerciseId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOneExerciseOverviewWithResponseSpec(String exerciseId) throws WebClientResponseException {
        return getOneExerciseOverviewRequestCreation(exerciseId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postExerciseCompletionNameRequestCreation(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseCompletionNameInputDTOPatientAPI;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling postExerciseCompletionName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseCompletionNameInputDTOPatientAPI' is set
        if (exerciseCompletionNameInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseCompletionNameInputDTOPatientAPI' when calling postExerciseCompletionName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/exercise-completion-name", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> postExerciseCompletionName(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postExerciseCompletionNameRequestCreation(exerciseId, exerciseCompletionNameInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> postExerciseCompletionNameWithHttpInfo(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postExerciseCompletionNameRequestCreation(exerciseId, exerciseCompletionNameInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postExerciseCompletionNameWithResponseSpec(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        return postExerciseCompletionNameRequestCreation(exerciseId, exerciseCompletionNameInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postExerciseComponentResultRequestCreation(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseComponentResultInputDTOPatientAPI;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling postExerciseComponentResult", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentId' is set
        if (exerciseComponentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentId' when calling postExerciseComponentResult", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentResultInputDTOPatientAPI' is set
        if (exerciseComponentResultInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentResultInputDTOPatientAPI' when calling postExerciseComponentResult", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/exercise-components/{exerciseComponentId}", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> postExerciseComponentResult(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postExerciseComponentResultRequestCreation(exerciseId, exerciseComponentId, exerciseComponentResultInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> postExerciseComponentResultWithHttpInfo(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postExerciseComponentResultRequestCreation(exerciseId, exerciseComponentId, exerciseComponentResultInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postExerciseComponentResultWithResponseSpec(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        return postExerciseComponentResultRequestCreation(exerciseId, exerciseComponentId, exerciseComponentResultInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseInformationInputDTOPatientAPI The exerciseInformationInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postExerciseFeedbackRequestCreation(String exerciseId, ExerciseInformationInputDTOPatientAPI exerciseInformationInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseInformationInputDTOPatientAPI;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling postExerciseFeedback", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseInformationInputDTOPatientAPI' is set
        if (exerciseInformationInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseInformationInputDTOPatientAPI' when calling postExerciseFeedback", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/exercise-completed", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseInformationInputDTOPatientAPI The exerciseInformationInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> postExerciseFeedback(String exerciseId, ExerciseInformationInputDTOPatientAPI exerciseInformationInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postExerciseFeedbackRequestCreation(exerciseId, exerciseInformationInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseInformationInputDTOPatientAPI The exerciseInformationInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> postExerciseFeedbackWithHttpInfo(String exerciseId, ExerciseInformationInputDTOPatientAPI exerciseInformationInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postExerciseFeedbackRequestCreation(exerciseId, exerciseInformationInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseInformationInputDTOPatientAPI The exerciseInformationInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postExerciseFeedbackWithResponseSpec(String exerciseId, ExerciseInformationInputDTOPatientAPI exerciseInformationInputDTOPatientAPI) throws WebClientResponseException {
        return postExerciseFeedbackRequestCreation(exerciseId, exerciseInformationInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putExerciseCompletionNameRequestCreation(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseCompletionNameInputDTOPatientAPI;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling putExerciseCompletionName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseCompletionNameInputDTOPatientAPI' is set
        if (exerciseCompletionNameInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseCompletionNameInputDTOPatientAPI' when calling putExerciseCompletionName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/exercise-completion-name", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> putExerciseCompletionName(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return putExerciseCompletionNameRequestCreation(exerciseId, exerciseCompletionNameInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> putExerciseCompletionNameWithHttpInfo(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return putExerciseCompletionNameRequestCreation(exerciseId, exerciseCompletionNameInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseCompletionNameInputDTOPatientAPI The exerciseCompletionNameInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putExerciseCompletionNameWithResponseSpec(String exerciseId, ExerciseCompletionNameInputDTOPatientAPI exerciseCompletionNameInputDTOPatientAPI) throws WebClientResponseException {
        return putExerciseCompletionNameRequestCreation(exerciseId, exerciseCompletionNameInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putExerciseComponentResultRequestCreation(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        Object postBody = exerciseComponentResultInputDTOPatientAPI;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling putExerciseComponentResult", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentId' is set
        if (exerciseComponentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentId' when calling putExerciseComponentResult", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'exerciseComponentResultInputDTOPatientAPI' is set
        if (exerciseComponentResultInputDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseComponentResultInputDTOPatientAPI' when calling putExerciseComponentResult", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/exercise-components/{exerciseComponentId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> putExerciseComponentResult(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return putExerciseComponentResultRequestCreation(exerciseId, exerciseComponentId, exerciseComponentResultInputDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> putExerciseComponentResultWithHttpInfo(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return putExerciseComponentResultRequestCreation(exerciseId, exerciseComponentId, exerciseComponentResultInputDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param exerciseComponentId The exerciseComponentId parameter
     * @param exerciseComponentResultInputDTOPatientAPI The exerciseComponentResultInputDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putExerciseComponentResultWithResponseSpec(String exerciseId, String exerciseComponentId, ExerciseComponentResultInputDTOPatientAPI exerciseComponentResultInputDTOPatientAPI) throws WebClientResponseException {
        return putExerciseComponentResultRequestCreation(exerciseId, exerciseComponentId, exerciseComponentResultInputDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ExerciseStartOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec startExerciseRequestCreation(String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling startExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<ExerciseStartOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseStartOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/start", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ExerciseStartOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ExerciseStartOutputDTOPatientAPI> startExercise(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseStartOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseStartOutputDTOPatientAPI>() {};
        return startExerciseRequestCreation(exerciseId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseEntity&lt;ExerciseStartOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ExerciseStartOutputDTOPatientAPI>> startExerciseWithHttpInfo(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseStartOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseStartOutputDTOPatientAPI>() {};
        return startExerciseRequestCreation(exerciseId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec startExerciseWithResponseSpec(String exerciseId) throws WebClientResponseException {
        return startExerciseRequestCreation(exerciseId);
    }
}
