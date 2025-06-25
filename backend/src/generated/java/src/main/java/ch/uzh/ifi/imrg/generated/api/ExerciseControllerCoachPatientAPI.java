package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.ExerciseInputDTOPatientAPI;

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
public class ExerciseControllerCoachPatientAPI {
    private ApiClient apiClient;

    public ExerciseControllerCoachPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public ExerciseControllerCoachPatientAPI(ApiClient apiClient) {
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
        return apiClient.invokeAPI("/coach/{patientId}/exercises", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
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
}
