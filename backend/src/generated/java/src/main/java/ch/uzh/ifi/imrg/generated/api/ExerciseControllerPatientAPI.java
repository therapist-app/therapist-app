package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

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
    private ResponseSpec getExerciseOutputDTOMockRequestCreation(String exerciseId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getExerciseOutputDTOMock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
    public Mono<ExerciseOutputDTOPatientAPI> getExerciseOutputDTOMock(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return getExerciseOutputDTOMockRequestCreation(exerciseId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseEntity&lt;ExerciseOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ExerciseOutputDTOPatientAPI>> getExerciseOutputDTOMockWithHttpInfo(String exerciseId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseOutputDTOPatientAPI>() {};
        return getExerciseOutputDTOMockRequestCreation(exerciseId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param exerciseId The exerciseId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getExerciseOutputDTOMockWithResponseSpec(String exerciseId) throws WebClientResponseException {
        return getExerciseOutputDTOMockRequestCreation(exerciseId);
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
    private ResponseSpec getPictureMockRequestCreation(String exerciseId, String mediaId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'exerciseId' is set
        if (exerciseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'exerciseId' when calling getPictureMock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mediaId' is set
        if (mediaId == null) {
            throw new WebClientResponseException("Missing the required parameter 'mediaId' when calling getPictureMock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
    public Mono<ExerciseMediaOutputDTOPatientAPI> getPictureMock(String exerciseId, String mediaId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI>() {};
        return getPictureMockRequestCreation(exerciseId, mediaId).bodyToMono(localVarReturnType);
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
    public Mono<ResponseEntity<ExerciseMediaOutputDTOPatientAPI>> getPictureMockWithHttpInfo(String exerciseId, String mediaId) throws WebClientResponseException {
        ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ExerciseMediaOutputDTOPatientAPI>() {};
        return getPictureMockRequestCreation(exerciseId, mediaId).toEntity(localVarReturnType);
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
    public ResponseSpec getPictureMockWithResponseSpec(String exerciseId, String mediaId) throws WebClientResponseException {
        return getPictureMockRequestCreation(exerciseId, mediaId);
    }
}
