package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CreateMeetingDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.MeetingOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.UpdateMeetingDTOPatientAPI;

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
public class MeetingControllerPatientAPI {
    private ApiClient apiClient;

    public MeetingControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public MeetingControllerPatientAPI(ApiClient apiClient) {
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
     * @param createMeetingDTOPatientAPI The createMeetingDTOPatientAPI parameter
     * @return MeetingOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createMeetingRequestCreation(String patientId, CreateMeetingDTOPatientAPI createMeetingDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createMeetingDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling createMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createMeetingDTOPatientAPI' is set
        if (createMeetingDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createMeetingDTOPatientAPI' when calling createMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/{patientId}/meetings", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param createMeetingDTOPatientAPI The createMeetingDTOPatientAPI parameter
     * @return MeetingOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<MeetingOutputDTOPatientAPI> createMeeting(String patientId, CreateMeetingDTOPatientAPI createMeetingDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return createMeetingRequestCreation(patientId, createMeetingDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param createMeetingDTOPatientAPI The createMeetingDTOPatientAPI parameter
     * @return ResponseEntity&lt;MeetingOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<MeetingOutputDTOPatientAPI>> createMeetingWithHttpInfo(String patientId, CreateMeetingDTOPatientAPI createMeetingDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return createMeetingRequestCreation(patientId, createMeetingDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param patientId The patientId parameter
     * @param createMeetingDTOPatientAPI The createMeetingDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createMeetingWithResponseSpec(String patientId, CreateMeetingDTOPatientAPI createMeetingDTOPatientAPI) throws WebClientResponseException {
        return createMeetingRequestCreation(patientId, createMeetingDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteMeetingRequestCreation(String patientId, String meetingId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling deleteMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new WebClientResponseException("Missing the required parameter 'meetingId' when calling deleteMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("meetingId", meetingId);

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
        return apiClient.invokeAPI("/patients/{patientId}/meetings/{meetingId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteMeeting(String patientId, String meetingId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteMeetingRequestCreation(patientId, meetingId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteMeetingWithHttpInfo(String patientId, String meetingId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteMeetingRequestCreation(patientId, meetingId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteMeetingWithResponseSpec(String patientId, String meetingId) throws WebClientResponseException {
        return deleteMeetingRequestCreation(patientId, meetingId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @return MeetingOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingRequestCreation(String patientId, String meetingId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling getMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new WebClientResponseException("Missing the required parameter 'meetingId' when calling getMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("meetingId", meetingId);

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

        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/{patientId}/meetings/{meetingId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @return MeetingOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<MeetingOutputDTOPatientAPI> getMeeting(String patientId, String meetingId) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return getMeetingRequestCreation(patientId, meetingId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @return ResponseEntity&lt;MeetingOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<MeetingOutputDTOPatientAPI>> getMeetingWithHttpInfo(String patientId, String meetingId) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return getMeetingRequestCreation(patientId, meetingId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingWithResponseSpec(String patientId, String meetingId) throws WebClientResponseException {
        return getMeetingRequestCreation(patientId, meetingId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;MeetingOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listMeetingsRequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling listMeetings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/{patientId}/meetings", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return List&lt;MeetingOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<MeetingOutputDTOPatientAPI> listMeetings(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return listMeetingsRequestCreation(patientId).bodyToFlux(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseEntity&lt;List&lt;MeetingOutputDTOPatientAPI&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<MeetingOutputDTOPatientAPI>>> listMeetingsWithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return listMeetingsRequestCreation(patientId).toEntityList(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listMeetingsWithResponseSpec(String patientId) throws WebClientResponseException {
        return listMeetingsRequestCreation(patientId);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @param updateMeetingDTOPatientAPI The updateMeetingDTOPatientAPI parameter
     * @return MeetingOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateMeetingRequestCreation(String patientId, String meetingId, UpdateMeetingDTOPatientAPI updateMeetingDTOPatientAPI) throws WebClientResponseException {
        Object postBody = updateMeetingDTOPatientAPI;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling updateMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new WebClientResponseException("Missing the required parameter 'meetingId' when calling updateMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateMeetingDTOPatientAPI' is set
        if (updateMeetingDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'updateMeetingDTOPatientAPI' when calling updateMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);
        pathParams.put("meetingId", meetingId);

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

        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/{patientId}/meetings/{meetingId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @param updateMeetingDTOPatientAPI The updateMeetingDTOPatientAPI parameter
     * @return MeetingOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<MeetingOutputDTOPatientAPI> updateMeeting(String patientId, String meetingId, UpdateMeetingDTOPatientAPI updateMeetingDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return updateMeetingRequestCreation(patientId, meetingId, updateMeetingDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @param updateMeetingDTOPatientAPI The updateMeetingDTOPatientAPI parameter
     * @return ResponseEntity&lt;MeetingOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<MeetingOutputDTOPatientAPI>> updateMeetingWithHttpInfo(String patientId, String meetingId, UpdateMeetingDTOPatientAPI updateMeetingDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<MeetingOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<MeetingOutputDTOPatientAPI>() {};
        return updateMeetingRequestCreation(patientId, meetingId, updateMeetingDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param patientId The patientId parameter
     * @param meetingId The meetingId parameter
     * @param updateMeetingDTOPatientAPI The updateMeetingDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateMeetingWithResponseSpec(String patientId, String meetingId, UpdateMeetingDTOPatientAPI updateMeetingDTOPatientAPI) throws WebClientResponseException {
        return updateMeetingRequestCreation(patientId, meetingId, updateMeetingDTOPatientAPI);
    }
}
