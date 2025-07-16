package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.ExerciseChatbotOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseInformationInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseMediaOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseOutputDTOPatientAPI;
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
     * @return ExerciseOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getExerciseRequestCreation(String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getExercise", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ExerciseOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ExerciseOutputDTOPatientAPI> getExercise(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return getExerciseRequestCreation(exerciseId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseEntity&lt;ExerciseOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ExerciseOutputDTOPatientAPI>> getExerciseWithHttpInfo(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return getExerciseRequestCreation(exerciseId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getExerciseWithResponseSpec(String exerciseId) throws WebClientResponseException {
        return getExerciseRequestCreation(exerciseId);
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
     * @param mediaId The mediaId parameter
     * @return ExerciseMediaOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPictureRequestCreation(String exerciseId, String mediaId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getPicture", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mediaId' is set
        if (mediaId == null) {
            throw new WebClientResponseException("Missing the required parameter 'mediaId' when calling getPicture", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("exerciseId", exerciseId);
        pathParams.put("mediaId", mediaId);

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

        ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}/{mediaId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param mediaId The mediaId parameter
     * @return ExerciseMediaOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ExerciseMediaOutputDTOPatientAPI> getPicture(String exerciseId, String mediaId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI>() {};
        return getPictureRequestCreation(exerciseId, mediaId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param mediaId The mediaId parameter
     * @return ResponseEntity&lt;ExerciseMediaOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ExerciseMediaOutputDTOPatientAPI>> getPictureWithHttpInfo(String exerciseId, String mediaId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI>() {};
        return getPictureRequestCreation(exerciseId, mediaId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @param mediaId The mediaId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPictureWithResponseSpec(String exerciseId, String mediaId) throws WebClientResponseException {
        return getPictureRequestCreation(exerciseId, mediaId);
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
        return apiClient.invokeAPI("/patients/exercises/{exerciseId}", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
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
}
