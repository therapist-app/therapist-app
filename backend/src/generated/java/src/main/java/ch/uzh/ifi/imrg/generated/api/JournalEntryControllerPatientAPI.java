package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.GetAllJournalEntriesDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.JournalEntryOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.JournalEntryRequestDTOPatientAPI;
import java.util.Set;

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
public class JournalEntryControllerPatientAPI {
    private ApiClient apiClient;

    public JournalEntryControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public JournalEntryControllerPatientAPI(ApiClient apiClient) {
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
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return JournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createRequestCreation(JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        Object postBody = journalEntryRequestDTOPatientAPI;
        // verify the required parameter 'journalEntryRequestDTOPatientAPI' is set
        if (journalEntryRequestDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'journalEntryRequestDTOPatientAPI' when calling create", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/journal-entries", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return JournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<JournalEntryOutputDTOPatientAPI> create(JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return createRequestCreation(journalEntryRequestDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return ResponseEntity&lt;JournalEntryOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<JournalEntryOutputDTOPatientAPI>> createWithHttpInfo(JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return createRequestCreation(journalEntryRequestDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWithResponseSpec(JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        return createRequestCreation(journalEntryRequestDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param entryId The entryId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteEntryRequestCreation(String entryId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'entryId' is set
        if (entryId == null) {
            throw new WebClientResponseException("Missing the required parameter 'entryId' when calling deleteEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("entryId", entryId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/patients/journal-entries/{entryId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param entryId The entryId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteEntry(String entryId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteEntryRequestCreation(entryId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param entryId The entryId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteEntryWithHttpInfo(String entryId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteEntryRequestCreation(entryId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param entryId The entryId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteEntryWithResponseSpec(String entryId) throws WebClientResponseException {
        return deleteEntryRequestCreation(entryId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Set&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllTagsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return apiClient.invokeAPI("/patients/journal-entries/tags", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Set&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Set<String>> getAllTags() throws WebClientResponseException {
        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return getAllTagsRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Set&lt;String&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Set<String>>> getAllTagsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return getAllTagsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllTagsWithResponseSpec() throws WebClientResponseException {
        return getAllTagsRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @return JournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOneRequestCreation(String entryId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'entryId' is set
        if (entryId == null) {
            throw new WebClientResponseException("Missing the required parameter 'entryId' when calling getOne", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("entryId", entryId);

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

        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/journal-entries/{entryId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @return JournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<JournalEntryOutputDTOPatientAPI> getOne(String entryId) throws WebClientResponseException {
        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return getOneRequestCreation(entryId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @return ResponseEntity&lt;JournalEntryOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<JournalEntryOutputDTOPatientAPI>> getOneWithHttpInfo(String entryId) throws WebClientResponseException {
        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return getOneRequestCreation(entryId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOneWithResponseSpec(String entryId) throws WebClientResponseException {
        return getOneRequestCreation(entryId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GetAllJournalEntriesDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAllRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<GetAllJournalEntriesDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<GetAllJournalEntriesDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/journal-entries", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GetAllJournalEntriesDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<GetAllJournalEntriesDTOPatientAPI> listAll() throws WebClientResponseException {
        ParameterizedTypeReference<GetAllJournalEntriesDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<GetAllJournalEntriesDTOPatientAPI>() {};
        return listAllRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GetAllJournalEntriesDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<GetAllJournalEntriesDTOPatientAPI>>> listAllWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<GetAllJournalEntriesDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<GetAllJournalEntriesDTOPatientAPI>() {};
        return listAllRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAllWithResponseSpec() throws WebClientResponseException {
        return listAllRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return JournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateJournalEntryRequestCreation(String entryId, JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        Object postBody = journalEntryRequestDTOPatientAPI;
        // verify the required parameter 'entryId' is set
        if (entryId == null) {
            throw new WebClientResponseException("Missing the required parameter 'entryId' when calling updateJournalEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'journalEntryRequestDTOPatientAPI' is set
        if (journalEntryRequestDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'journalEntryRequestDTOPatientAPI' when calling updateJournalEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("entryId", entryId);

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

        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/journal-entries/{entryId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return JournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<JournalEntryOutputDTOPatientAPI> updateJournalEntry(String entryId, JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return updateJournalEntryRequestCreation(entryId, journalEntryRequestDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return ResponseEntity&lt;JournalEntryOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<JournalEntryOutputDTOPatientAPI>> updateJournalEntryWithHttpInfo(String entryId, JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<JournalEntryOutputDTOPatientAPI>() {};
        return updateJournalEntryRequestCreation(entryId, journalEntryRequestDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param entryId The entryId parameter
     * @param journalEntryRequestDTOPatientAPI The journalEntryRequestDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateJournalEntryWithResponseSpec(String entryId, JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI) throws WebClientResponseException {
        return updateJournalEntryRequestCreation(entryId, journalEntryRequestDTOPatientAPI);
    }
}
