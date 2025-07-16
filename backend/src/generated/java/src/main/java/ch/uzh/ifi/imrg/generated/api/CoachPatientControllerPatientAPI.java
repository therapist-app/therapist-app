package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.CreatePatientDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PatientOutputDTOPatientAPI;

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
public class CoachPatientControllerPatientAPI {
    private ApiClient apiClient;

    public CoachPatientControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public CoachPatientControllerPatientAPI(ApiClient apiClient) {
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
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deletePatientRequestCreation(String patientId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'patientId' is set
        if (patientId == null) {
            throw new WebClientResponseException("Missing the required parameter 'patientId' when calling deletePatient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("patientId", patientId);

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
        return apiClient.invokeAPI("/coach/patients/{patientId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deletePatient(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deletePatientRequestCreation(patientId).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deletePatientWithHttpInfo(String patientId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deletePatientRequestCreation(patientId).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param patientId The patientId parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deletePatientWithResponseSpec(String patientId) throws WebClientResponseException {
        return deletePatientRequestCreation(patientId);
    }
    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec registerPatient1RequestCreation(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createPatientDTOPatientAPI;
        // verify the required parameter 'createPatientDTOPatientAPI' is set
        if (createPatientDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createPatientDTOPatientAPI' when calling registerPatient1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        String[] localVarAuthNames = new String[] { "X-Coach-Key" };

        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/coach/patients/register", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> registerPatient1(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return registerPatient1RequestCreation(createPatientDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> registerPatient1WithHttpInfo(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return registerPatient1RequestCreation(createPatientDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec registerPatient1WithResponseSpec(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        return registerPatient1RequestCreation(createPatientDTOPatientAPI);
    }
}
