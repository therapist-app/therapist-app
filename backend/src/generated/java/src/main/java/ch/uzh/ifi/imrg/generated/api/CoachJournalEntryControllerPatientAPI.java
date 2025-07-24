package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CoachGetAllJournalEntriesDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CoachJournalEntryOutputDTOPatientAPI;

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
public class CoachJournalEntryControllerPatientAPI {
    private ApiClient apiClient;

    public CoachJournalEntryControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public CoachJournalEntryControllerPatientAPI(ApiClient apiClient) {
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
     * @param patientId The patientId parameter
     * @param entryId The entryId parameter
     * @return CoachJournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOne1RequestCreation(String patientId, String entryId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getOne1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'entryId' is set
        if (entryId == null) {
            throw new WebClientResponseException("Missing the required parameter 'entryId' when calling getOne1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
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

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<CoachJournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CoachJournalEntryOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/journal-entries/{entryId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param entryId The entryId parameter
     * @return CoachJournalEntryOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CoachJournalEntryOutputDTOPatientAPI> getOne1(String patientId, String entryId) throws WebClientResponseException {
        ParameterizedTypeReference<CoachJournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CoachJournalEntryOutputDTOPatientAPI>() {};
        return getOne1RequestCreation(patientId, entryId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param entryId The entryId parameter
     * @return ResponseEntity&lt;CoachJournalEntryOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CoachJournalEntryOutputDTOPatientAPI>> getOne1WithHttpInfo(String patientId, String entryId) throws WebClientResponseException {
        ParameterizedTypeReference<CoachJournalEntryOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CoachJournalEntryOutputDTOPatientAPI>() {};
        return getOne1RequestCreation(patientId, entryId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param entryId The entryId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOne1WithResponseSpec(String patientId, String entryId) throws WebClientResponseException {
        return getOne1RequestCreation(patientId, entryId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;CoachGetAllJournalEntriesDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAll2RequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling listAll2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CoachGetAllJournalEntriesDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CoachGetAllJournalEntriesDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/{patientId}/journal-entries", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;CoachGetAllJournalEntriesDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<CoachGetAllJournalEntriesDTOPatientAPI> listAll2(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<CoachGetAllJournalEntriesDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CoachGetAllJournalEntriesDTOPatientAPI>() {};
        return listAll2RequestCreation(patientId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseEntity&lt;List&lt;CoachGetAllJournalEntriesDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<CoachGetAllJournalEntriesDTOPatientAPI>>> listAll2WithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<CoachGetAllJournalEntriesDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<CoachGetAllJournalEntriesDTOPatientAPI>() {};
        return listAll2RequestCreation(patientId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAll2WithResponseSpec(String patientId) throws WebClientResponseException {
        return listAll2RequestCreation(patientId);
    }
}
