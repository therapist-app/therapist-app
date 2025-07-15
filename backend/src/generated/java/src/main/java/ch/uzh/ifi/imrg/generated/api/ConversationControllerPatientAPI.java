package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CompleteConversationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CreateConversationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CreateMessageDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.MessageOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.NameConversationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PutConversationNameDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PutSharingDTOPatientAPI;

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

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

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
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteChatRequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling deleteChat", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/patients/conversations/{conversationId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteChat(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteChatRequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteChatWithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteChatRequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteChatWithResponseSpec(String conversationId) throws WebClientResponseException {
        return deleteChatRequestCreation(conversationId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return CompleteConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllMessages1RequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling getAllMessages1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
    public Mono<CompleteConversationOutputDTOPatientAPI> getAllMessages1(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI>() {};
        return getAllMessages1RequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseEntity&lt;CompleteConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CompleteConversationOutputDTOPatientAPI>> getAllMessages1WithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CompleteConversationOutputDTOPatientAPI>() {};
        return getAllMessages1RequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllMessages1WithResponseSpec(String conversationId) throws WebClientResponseException {
        return getAllMessages1RequestCreation(conversationId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;NameConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getConversationNamesRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<NameConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<NameConversationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/conversations", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;NameConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<NameConversationOutputDTOPatientAPI> getConversationNames() throws WebClientResponseException {
        ParameterizedTypeReference<NameConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<NameConversationOutputDTOPatientAPI>() {};
        return getConversationNamesRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;NameConversationOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<NameConversationOutputDTOPatientAPI>>> getConversationNamesWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<NameConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<NameConversationOutputDTOPatientAPI>() {};
        return getConversationNamesRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getConversationNamesWithResponseSpec() throws WebClientResponseException {
        return getConversationNamesRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putConversationNameDTOPatientAPI The putConversationNameDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postConversationNameRequestCreation(String conversationId, PutConversationNameDTOPatientAPI putConversationNameDTOPatientAPI) throws WebClientResponseException {
        Object postBody = putConversationNameDTOPatientAPI;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling postConversationName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putConversationNameDTOPatientAPI' is set
        if (putConversationNameDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'putConversationNameDTOPatientAPI' when calling postConversationName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/patients/conversations/{conversationId}/conversation-name", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putConversationNameDTOPatientAPI The putConversationNameDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> postConversationName(String conversationId, PutConversationNameDTOPatientAPI putConversationNameDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postConversationNameRequestCreation(conversationId, putConversationNameDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putConversationNameDTOPatientAPI The putConversationNameDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> postConversationNameWithHttpInfo(String conversationId, PutConversationNameDTOPatientAPI putConversationNameDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return postConversationNameRequestCreation(conversationId, putConversationNameDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putConversationNameDTOPatientAPI The putConversationNameDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postConversationNameWithResponseSpec(String conversationId, PutConversationNameDTOPatientAPI putConversationNameDTOPatientAPI) throws WebClientResponseException {
        return postConversationNameRequestCreation(conversationId, putConversationNameDTOPatientAPI);
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
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putSharingDTOPatientAPI The putSharingDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateSharingRequestCreation(String conversationId, PutSharingDTOPatientAPI putSharingDTOPatientAPI) throws WebClientResponseException {
        Object postBody = putSharingDTOPatientAPI;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling updateSharing", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putSharingDTOPatientAPI' is set
        if (putSharingDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'putSharingDTOPatientAPI' when calling updateSharing", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/patients/conversations/{conversationId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putSharingDTOPatientAPI The putSharingDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> updateSharing(String conversationId, PutSharingDTOPatientAPI putSharingDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateSharingRequestCreation(conversationId, putSharingDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putSharingDTOPatientAPI The putSharingDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> updateSharingWithHttpInfo(String conversationId, PutSharingDTOPatientAPI putSharingDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateSharingRequestCreation(conversationId, putSharingDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param putSharingDTOPatientAPI The putSharingDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateSharingWithResponseSpec(String conversationId, PutSharingDTOPatientAPI putSharingDTOPatientAPI) throws WebClientResponseException {
        return updateSharingRequestCreation(conversationId, putSharingDTOPatientAPI);
    }
}
