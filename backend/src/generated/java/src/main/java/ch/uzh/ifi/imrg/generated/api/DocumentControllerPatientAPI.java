package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.DocumentChatbotOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.DocumentOverviewDTOPatientAPI;
import java.io.File;

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
public class DocumentControllerPatientAPI {
    private ApiClient apiClient;

    public DocumentControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public DocumentControllerPatientAPI(ApiClient apiClient) {
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
     * @return List&lt;DocumentOverviewDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec callListRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<DocumentOverviewDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentOverviewDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/documents", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;DocumentOverviewDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<DocumentOverviewDTOPatientAPI> callList() throws WebClientResponseException {
        ParameterizedTypeReference<DocumentOverviewDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentOverviewDTOPatientAPI>() {};
        return callListRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;DocumentOverviewDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<DocumentOverviewDTOPatientAPI>>> callListWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<DocumentOverviewDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentOverviewDTOPatientAPI>() {};
        return callListRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec callListWithResponseSpec() throws WebClientResponseException {
        return callListRequestCreation();
    }
    /**
     * Download a patient’s document
     * 
     * <p><b>200</b> - The raw file bytes
     * <p><b>404</b> - Document not found
     * @param documentId The documentId parameter
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadRequestCreation(String documentId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'documentId' is set
        if (documentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'documentId' when calling download", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("documentId", documentId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/patients/documents/{documentId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a patient’s document
     * 
     * <p><b>200</b> - The raw file bytes
     * <p><b>404</b> - Document not found
     * @param documentId The documentId parameter
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> download(String documentId) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadRequestCreation(documentId).bodyToMono(localVarReturnType);
    }

    /**
     * Download a patient’s document
     * 
     * <p><b>200</b> - The raw file bytes
     * <p><b>404</b> - Document not found
     * @param documentId The documentId parameter
     * @return ResponseEntity&lt;File&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<File>> downloadWithHttpInfo(String documentId) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadRequestCreation(documentId).toEntity(localVarReturnType);
    }

    /**
     * Download a patient’s document
     * 
     * <p><b>200</b> - The raw file bytes
     * <p><b>404</b> - Document not found
     * @param documentId The documentId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadWithResponseSpec(String documentId) throws WebClientResponseException {
        return downloadRequestCreation(documentId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param documentId The documentId parameter
     * @return DocumentChatbotOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllMessages3RequestCreation(String documentId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'documentId' is set
        if (documentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'documentId' when calling getAllMessages3", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("documentId", documentId);

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

        ParameterizedTypeReference<DocumentChatbotOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentChatbotOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/documents/{documentId}/chatbot", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param documentId The documentId parameter
     * @return DocumentChatbotOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<DocumentChatbotOutputDTOPatientAPI> getAllMessages3(String documentId) throws WebClientResponseException {
        ParameterizedTypeReference<DocumentChatbotOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentChatbotOutputDTOPatientAPI>() {};
        return getAllMessages3RequestCreation(documentId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param documentId The documentId parameter
     * @return ResponseEntity&lt;DocumentChatbotOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<DocumentChatbotOutputDTOPatientAPI>> getAllMessages3WithHttpInfo(String documentId) throws WebClientResponseException {
        ParameterizedTypeReference<DocumentChatbotOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<DocumentChatbotOutputDTOPatientAPI>() {};
        return getAllMessages3RequestCreation(documentId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param documentId The documentId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllMessages3WithResponseSpec(String documentId) throws WebClientResponseException {
        return getAllMessages3RequestCreation(documentId);
    }
}
