package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CreateJournalMessageDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.JournalConversationOutputDTOPatientAPI;
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
public class JournalEntryConversationControllerPatientAPI {
    private ApiClient apiClient;

    public JournalEntryConversationControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public JournalEntryConversationControllerPatientAPI(ApiClient apiClient) {
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
    private ResponseSpec deleteJournalChatRequestCreation(String conversationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling deleteJournalChat", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/patients/journal-entry-conversation/{conversationId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteJournalChat(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteJournalChatRequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteJournalChatWithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteJournalChatRequestCreation(conversationId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteJournalChatWithResponseSpec(String conversationId) throws WebClientResponseException {
        return deleteJournalChatRequestCreation(conversationId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return JournalConversationOutputDTOPatientAPI
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

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<JournalConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalConversationOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/journal-entry-conversation/{conversationId}/messages", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return JournalConversationOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<JournalConversationOutputDTOPatientAPI> getAllMessages(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<JournalConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalConversationOutputDTOPatientAPI>() {};
        return getAllMessagesRequestCreation(conversationId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @return ResponseEntity&lt;JournalConversationOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<JournalConversationOutputDTOPatientAPI>> getAllMessagesWithHttpInfo(String conversationId) throws WebClientResponseException {
        ParameterizedTypeReference<JournalConversationOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalConversationOutputDTOPatientAPI>() {};
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
     * @param conversationId The conversationId parameter
     * @param createJournalMessageDTOPatientAPI The createJournalMessageDTOPatientAPI parameter
     * @return MessageOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec sendMessageRequestCreation(String conversationId, CreateJournalMessageDTOPatientAPI createJournalMessageDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createJournalMessageDTOPatientAPI;
        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'conversationId' when calling sendMessage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createJournalMessageDTOPatientAPI' is set
        if (createJournalMessageDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createJournalMessageDTOPatientAPI' when calling sendMessage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/patients/journal-entry-conversation/{conversationId}/messages", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param createJournalMessageDTOPatientAPI The createJournalMessageDTOPatientAPI parameter
     * @return MessageOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<MessageOutputDTOPatientAPI> sendMessage(String conversationId, CreateJournalMessageDTOPatientAPI createJournalMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessageRequestCreation(conversationId, createJournalMessageDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param createJournalMessageDTOPatientAPI The createJournalMessageDTOPatientAPI parameter
     * @return ResponseEntity&lt;MessageOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<MessageOutputDTOPatientAPI>> sendMessageWithHttpInfo(String conversationId, CreateJournalMessageDTOPatientAPI createJournalMessageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MessageOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MessageOutputDTOPatientAPI>() {};
        return sendMessageRequestCreation(conversationId, createJournalMessageDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param conversationId The conversationId parameter
     * @param createJournalMessageDTOPatientAPI The createJournalMessageDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec sendMessageWithResponseSpec(String conversationId, CreateJournalMessageDTOPatientAPI createJournalMessageDTOPatientAPI) throws WebClientResponseException {
        return sendMessageRequestCreation(conversationId, createJournalMessageDTOPatientAPI);
    }
}
