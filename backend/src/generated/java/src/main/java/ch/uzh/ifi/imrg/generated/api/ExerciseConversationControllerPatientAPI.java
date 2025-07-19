package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CompleteExerciseConversationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CreateMessageDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.MessageOutputDTOPatientAPI;

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
public class ExerciseConversationControllerPatientAPI {
    private ApiClient apiClient;

    public ExerciseConversationControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public ExerciseConversationControllerPatientAPI(ApiClient apiClient) {
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
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteExerciseChatRequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling deleteExerciseChat", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("conversationId", conversationId);

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
        return apiClient.invokeAPI("/patients/exercise-conversation/{conversationId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteExerciseChat(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteExerciseChatRequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteExerciseChatWithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteExerciseChatRequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteExerciseChatWithResponseSpec(String conversationId) throws WebClientResponseException {
        return deleteExerciseChatRequestCreation(conversationId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return CompleteExerciseConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllMessages2RequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling getAllMessages2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("conversationId", conversationId);

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

        ParameterizedTypeReference<CompleteExerciseConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteExerciseConversationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercise-conversation/{conversationId}/messages", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return CompleteExerciseConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CompleteExerciseConversationOutputDTOPatientAPI> getAllMessages2(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<CompleteExerciseConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteExerciseConversationOutputDTOPatientAPI>() {};
        return getAllMessages2RequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseEntity&lt;CompleteExerciseConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CompleteExerciseConversationOutputDTOPatientAPI>> getAllMessages2WithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<CompleteExerciseConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteExerciseConversationOutputDTOPatientAPI>() {};
        return getAllMessages2RequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllMessages2WithResponseSpec(String conversationId) throws WebClientResponseException {
        return getAllMessages2RequestCreation(conversationId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param createMessageDTOPatientAPI The createMessageDTOPatientAPI parameter
     * @return MessageOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec sendMessage1RequestCreation(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createMessageDTOPatientAPI;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling sendMessage1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createMessageDTOPatientAPI' is set
        if (createMessageDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createMessageDTOPatientAPI' when calling sendMessage1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("conversationId", conversationId);

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

        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/exercise-conversation/{conversationId}/messages", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param createMessageDTOPatientAPI The createMessageDTOPatientAPI parameter
     * @return MessageOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<MessageOutputDTOPatientAPI> sendMessage1(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessage1RequestCreation(conversationId, createMessageDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param createMessageDTOPatientAPI The createMessageDTOPatientAPI parameter
     * @return ResponseEntity&lt;MessageOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<MessageOutputDTOPatientAPI>> sendMessage1WithHttpInfo(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessage1RequestCreation(conversationId, createMessageDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param createMessageDTOPatientAPI The createMessageDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec sendMessage1WithResponseSpec(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        return sendMessage1RequestCreation(conversationId, createMessageDTOPatientAPI);
    }
}
