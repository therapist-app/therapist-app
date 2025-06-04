package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CompleteConversationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CreateConversationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CreateMessageDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.MessageOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.NameConversationOutputDTOPatientAPI;

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
public class ConversationControllerPatientAPI {
    private ApiClient apiClient;

    public ConversationControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public ConversationControllerPatientAPI(ApiClient apiClient) {
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
     * @return CreateConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createConversationRequestCreation() throws WebClientResponseException {
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

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<CreateConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CreateConversationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/conversations", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @return CreateConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CreateConversationOutputDTOPatientAPI> createConversation() throws WebClientResponseException {
        ParameterizedTypeReference<CreateConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CreateConversationOutputDTOPatientAPI>() {};
        return createConversationRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @return ResponseEntity&lt;CreateConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CreateConversationOutputDTOPatientAPI>> createConversationWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<CreateConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CreateConversationOutputDTOPatientAPI>() {};
        return createConversationRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createConversationWithResponseSpec() throws WebClientResponseException {
        return createConversationRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return CompleteConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllMessagesRequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling getAllMessages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/conversations/messages/{conversationId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return CompleteConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CompleteConversationOutputDTOPatientAPI> getAllMessages(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI>() {};
        return getAllMessagesRequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseEntity&lt;CompleteConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CompleteConversationOutputDTOPatientAPI>> getAllMessagesWithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI>() {};
        return getAllMessagesRequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllMessagesWithResponseSpec(String conversationId) throws WebClientResponseException {
        return getAllMessagesRequestCreation(conversationId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;NameConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec nameConversationDTORequestCreation() throws WebClientResponseException {
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

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<NameConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<NameConversationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/conversations/{patientId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;NameConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<NameConversationOutputDTOPatientAPI> nameConversationDTO() throws WebClientResponseException {
        ParameterizedTypeReference<NameConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<NameConversationOutputDTOPatientAPI>() {};
        return nameConversationDTORequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;NameConversationOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<NameConversationOutputDTOPatientAPI>>> nameConversationDTOWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<NameConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<NameConversationOutputDTOPatientAPI>() {};
        return nameConversationDTORequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec nameConversationDTOWithResponseSpec() throws WebClientResponseException {
        return nameConversationDTORequestCreation();
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
    private ResponseSpec sendMessageRequestCreation(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createMessageDTOPatientAPI;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling sendMessage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createMessageDTOPatientAPI' is set
        if (createMessageDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createMessageDTOPatientAPI' when calling sendMessage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/conversations/messages/{conversationId}", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
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
    public Mono<MessageOutputDTOPatientAPI> sendMessage(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessageRequestCreation(conversationId, createMessageDTOPatientAPI).bodyToMono(localVarReturnType);
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
    public Mono<ResponseEntity<MessageOutputDTOPatientAPI>> sendMessageWithHttpInfo(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessageRequestCreation(conversationId, createMessageDTOPatientAPI).toEntity(localVarReturnType);
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
    public ResponseSpec sendMessageWithResponseSpec(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        return sendMessageRequestCreation(conversationId, createMessageDTOPatientAPI);
    }
}
