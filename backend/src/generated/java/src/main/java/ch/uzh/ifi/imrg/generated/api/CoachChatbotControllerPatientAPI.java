package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.ChatbotConfigurationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CreateChatbotDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.UpdateChatbotDTOPatientAPI;

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
public class CoachChatbotControllerPatientAPI {
    private ApiClient apiClient;

    public CoachChatbotControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public CoachChatbotControllerPatientAPI(ApiClient apiClient) {
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
     * <p><b>409</b> - Conflict
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param createChatbotDTOPatientAPI The createChatbotDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createChatbotRequestCreation(String patientId, CreateChatbotDTOPatientAPI createChatbotDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createChatbotDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling createChatbot", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createChatbotDTOPatientAPI' is set
        if (createChatbotDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createChatbotDTOPatientAPI' when calling createChatbot", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/chatbot", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param createChatbotDTOPatientAPI The createChatbotDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> createChatbot(String patientId, CreateChatbotDTOPatientAPI createChatbotDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createChatbotRequestCreation(patientId, createChatbotDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param createChatbotDTOPatientAPI The createChatbotDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> createChatbotWithHttpInfo(String patientId, CreateChatbotDTOPatientAPI createChatbotDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return createChatbotRequestCreation(patientId, createChatbotDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param createChatbotDTOPatientAPI The createChatbotDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createChatbotWithResponseSpec(String patientId, CreateChatbotDTOPatientAPI createChatbotDTOPatientAPI) throws WebClientResponseException {
        return createChatbotRequestCreation(patientId, createChatbotDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;ChatbotConfigurationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getChatbotConfigurationsRequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getChatbotConfigurations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ChatbotConfigurationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ChatbotConfigurationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/chatbot", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;ChatbotConfigurationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ChatbotConfigurationOutputDTOPatientAPI> getChatbotConfigurations(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<ChatbotConfigurationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ChatbotConfigurationOutputDTOPatientAPI>() {};
        return getChatbotConfigurationsRequestCreation(patientId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseEntity&lt;List&lt;ChatbotConfigurationOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ChatbotConfigurationOutputDTOPatientAPI>>> getChatbotConfigurationsWithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<ChatbotConfigurationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<ChatbotConfigurationOutputDTOPatientAPI>() {};
        return getChatbotConfigurationsRequestCreation(patientId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getChatbotConfigurationsWithResponseSpec(String patientId) throws WebClientResponseException {
        return getChatbotConfigurationsRequestCreation(patientId);
    }
    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param updateChatbotDTOPatientAPI The updateChatbotDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateChatbotRequestCreation(String patientId, UpdateChatbotDTOPatientAPI updateChatbotDTOPatientAPI) throws WebClientResponseException {
        Object postBody = updateChatbotDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling updateChatbot", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateChatbotDTOPatientAPI' is set
        if (updateChatbotDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'updateChatbotDTOPatientAPI' when calling updateChatbot", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/chatbot", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param updateChatbotDTOPatientAPI The updateChatbotDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> updateChatbot(String patientId, UpdateChatbotDTOPatientAPI updateChatbotDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateChatbotRequestCreation(patientId, updateChatbotDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param updateChatbotDTOPatientAPI The updateChatbotDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> updateChatbotWithHttpInfo(String patientId, UpdateChatbotDTOPatientAPI updateChatbotDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateChatbotRequestCreation(patientId, updateChatbotDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>409</b> - Conflict
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param updateChatbotDTOPatientAPI The updateChatbotDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateChatbotWithResponseSpec(String patientId, UpdateChatbotDTOPatientAPI updateChatbotDTOPatientAPI) throws WebClientResponseException {
        return updateChatbotRequestCreation(patientId, updateChatbotDTOPatientAPI);
    }
}
