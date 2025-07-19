package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CreateMessageDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.DocumentConversationOutputDTOPatientAPI;
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
public class DocumentConversationControllerPatientAPI {
    private ApiClient apiClient;

    public DocumentConversationControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public DocumentConversationControllerPatientAPI(ApiClient apiClient) {
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
    private ResponseSpec deleteDocumentChatRequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling deleteDocumentChat", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/patients/document-conversation/{conversationId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteDocumentChat(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteDocumentChatRequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteDocumentChatWithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteDocumentChatRequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteDocumentChatWithResponseSpec(String conversationId) throws WebClientResponseException {
        return deleteDocumentChatRequestCreation(conversationId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return DocumentConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllMessages4RequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling getAllMessages4", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<DocumentConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentConversationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/document-conversation/{conversationId}/messages", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return DocumentConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<DocumentConversationOutputDTOPatientAPI> getAllMessages4(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<DocumentConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentConversationOutputDTOPatientAPI>() {};
        return getAllMessages4RequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseEntity&lt;DocumentConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<DocumentConversationOutputDTOPatientAPI>> getAllMessages4WithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<DocumentConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentConversationOutputDTOPatientAPI>() {};
        return getAllMessages4RequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllMessages4WithResponseSpec(String conversationId) throws WebClientResponseException {
        return getAllMessages4RequestCreation(conversationId);
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
    private ResponseSpec sendMessage2RequestCreation(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createMessageDTOPatientAPI;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling sendMessage2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createMessageDTOPatientAPI' is set
        if (createMessageDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createMessageDTOPatientAPI' when calling sendMessage2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/patients/document-conversation/{conversationId}/messages", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
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
    public Mono<MessageOutputDTOPatientAPI> sendMessage2(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessage2RequestCreation(conversationId, createMessageDTOPatientAPI).bodyToMono(localVarReturnType);
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
    public Mono<ResponseEntity<MessageOutputDTOPatientAPI>> sendMessage2WithHttpInfo(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessage2RequestCreation(conversationId, createMessageDTOPatientAPI).toEntity(localVarReturnType);
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
    public ResponseSpec sendMessage2WithResponseSpec(String conversationId, CreateMessageDTOPatientAPI createMessageDTOPatientAPI) throws WebClientResponseException {
        return sendMessage2RequestCreation(conversationId, createMessageDTOPatientAPI);
    }
}
