package ch.uzh.ifi.imrg.generated.api;

import ch.uzh.ifi.imrg.generated.invoker.ApiClient;

import ch.uzh.ifi.imrg.generated.model.ChangePasswordDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CreatePatientDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.LoginPatientDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PatientOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PutAvatarDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PutLanguageDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PutNameDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PutOnboardedDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ResetPasswordDTOPatientAPI;

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
public class PatientControllerPatientAPI {
    private ApiClient apiClient;

    public PatientControllerPatientAPI() {
        this(new ApiClient());
    }

    @Autowired
    public PatientControllerPatientAPI(ApiClient apiClient) {
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
     * @param changePasswordDTOPatientAPI The changePasswordDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec changePasswordRequestCreation(ChangePasswordDTOPatientAPI changePasswordDTOPatientAPI) throws WebClientResponseException {
        Object postBody = changePasswordDTOPatientAPI;
        // verify the required parameter 'changePasswordDTOPatientAPI' is set
        if (changePasswordDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'changePasswordDTOPatientAPI' when calling changePassword", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/passwords", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param changePasswordDTOPatientAPI The changePasswordDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> changePassword(ChangePasswordDTOPatientAPI changePasswordDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return changePasswordRequestCreation(changePasswordDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param changePasswordDTOPatientAPI The changePasswordDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> changePasswordWithHttpInfo(ChangePasswordDTOPatientAPI changePasswordDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return changePasswordRequestCreation(changePasswordDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param changePasswordDTOPatientAPI The changePasswordDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec changePasswordWithResponseSpec(ChangePasswordDTOPatientAPI changePasswordDTOPatientAPI) throws WebClientResponseException {
        return changePasswordRequestCreation(changePasswordDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAvatarRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/chat-bot-avatar", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> getAvatar() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getAvatarRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> getAvatarWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getAvatarRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAvatarWithResponseSpec() throws WebClientResponseException {
        return getAvatarRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCurrentlyLoggedInPatientRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/me", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> getCurrentlyLoggedInPatient() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getCurrentlyLoggedInPatientRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> getCurrentlyLoggedInPatientWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getCurrentlyLoggedInPatientRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCurrentlyLoggedInPatientWithResponseSpec() throws WebClientResponseException {
        return getCurrentlyLoggedInPatientRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getLanguageRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/language", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> getLanguage() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getLanguageRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> getLanguageWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getLanguageRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getLanguageWithResponseSpec() throws WebClientResponseException {
        return getLanguageRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getNameRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/name", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> getName() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getNameRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> getNameWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getNameRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getNameWithResponseSpec() throws WebClientResponseException {
        return getNameRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOnboardedRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return apiClient.invokeAPI("/patients/onboarded", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> getOnboarded() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getOnboardedRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> getOnboardedWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return getOnboardedRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOnboardedWithResponseSpec() throws WebClientResponseException {
        return getOnboardedRequestCreation();
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec loginTherapistRequestCreation(LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
        Object postBody = loginPatientDTOPatientAPI;
        // verify the required parameter 'loginPatientDTOPatientAPI' is set
        if (loginPatientDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'loginPatientDTOPatientAPI' when calling loginTherapist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/patients/login", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> loginTherapist(LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return loginTherapistRequestCreation(loginPatientDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> loginTherapistWithHttpInfo(LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return loginTherapistRequestCreation(loginPatientDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param loginPatientDTOPatientAPI The loginPatientDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec loginTherapistWithResponseSpec(LoginPatientDTOPatientAPI loginPatientDTOPatientAPI) throws WebClientResponseException {
        return loginTherapistRequestCreation(loginPatientDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec logoutTherapistRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/logout", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> logoutTherapist() throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return logoutTherapistRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> logoutTherapistWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return logoutTherapistRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec logoutTherapistWithResponseSpec() throws WebClientResponseException {
        return logoutTherapistRequestCreation();
    }
    /**
     * This endpoint will be removed
     * @Therapist app: pleas use /coach/patients/register as endpoint
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec registerPatientRequestCreation(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        Object postBody = createPatientDTOPatientAPI;
        // verify the required parameter 'createPatientDTOPatientAPI' is set
        if (createPatientDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'createPatientDTOPatientAPI' when calling registerPatient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/patients/register", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * This endpoint will be removed
     * @Therapist app: pleas use /coach/patients/register as endpoint
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return PatientOutputDTOPatientAPI
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PatientOutputDTOPatientAPI> registerPatient(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return registerPatientRequestCreation(createPatientDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * This endpoint will be removed
     * @Therapist app: pleas use /coach/patients/register as endpoint
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return ResponseEntity&lt;PatientOutputDTOPatientAPI&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PatientOutputDTOPatientAPI>> registerPatientWithHttpInfo(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<PatientOutputDTOPatientAPI> localVarReturnType = new ParameterizedTypeReference<PatientOutputDTOPatientAPI>() {};
        return registerPatientRequestCreation(createPatientDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * This endpoint will be removed
     * @Therapist app: pleas use /coach/patients/register as endpoint
     * <p><b>201</b> - Created
     * @param createPatientDTOPatientAPI The createPatientDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec registerPatientWithResponseSpec(CreatePatientDTOPatientAPI createPatientDTOPatientAPI) throws WebClientResponseException {
        return registerPatientRequestCreation(createPatientDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param resetPasswordDTOPatientAPI The resetPasswordDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec resetPasswordRequestCreation(ResetPasswordDTOPatientAPI resetPasswordDTOPatientAPI) throws WebClientResponseException {
        Object postBody = resetPasswordDTOPatientAPI;
        // verify the required parameter 'resetPasswordDTOPatientAPI' is set
        if (resetPasswordDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'resetPasswordDTOPatientAPI' when calling resetPassword", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/reset-password", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param resetPasswordDTOPatientAPI The resetPasswordDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> resetPassword(ResetPasswordDTOPatientAPI resetPasswordDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return resetPasswordRequestCreation(resetPasswordDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param resetPasswordDTOPatientAPI The resetPasswordDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> resetPasswordWithHttpInfo(ResetPasswordDTOPatientAPI resetPasswordDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return resetPasswordRequestCreation(resetPasswordDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>204</b> - No Content
     * @param resetPasswordDTOPatientAPI The resetPasswordDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec resetPasswordWithResponseSpec(ResetPasswordDTOPatientAPI resetPasswordDTOPatientAPI) throws WebClientResponseException {
        return resetPasswordRequestCreation(resetPasswordDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putAvatarDTOPatientAPI The putAvatarDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec setAvatarRequestCreation(PutAvatarDTOPatientAPI putAvatarDTOPatientAPI) throws WebClientResponseException {
        Object postBody = putAvatarDTOPatientAPI;
        // verify the required parameter 'putAvatarDTOPatientAPI' is set
        if (putAvatarDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'putAvatarDTOPatientAPI' when calling setAvatar", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/chat-bot-avatar", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putAvatarDTOPatientAPI The putAvatarDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> setAvatar(PutAvatarDTOPatientAPI putAvatarDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setAvatarRequestCreation(putAvatarDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putAvatarDTOPatientAPI The putAvatarDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> setAvatarWithHttpInfo(PutAvatarDTOPatientAPI putAvatarDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setAvatarRequestCreation(putAvatarDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putAvatarDTOPatientAPI The putAvatarDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec setAvatarWithResponseSpec(PutAvatarDTOPatientAPI putAvatarDTOPatientAPI) throws WebClientResponseException {
        return setAvatarRequestCreation(putAvatarDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putLanguageDTOPatientAPI The putLanguageDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec setLanguageRequestCreation(PutLanguageDTOPatientAPI putLanguageDTOPatientAPI) throws WebClientResponseException {
        Object postBody = putLanguageDTOPatientAPI;
        // verify the required parameter 'putLanguageDTOPatientAPI' is set
        if (putLanguageDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'putLanguageDTOPatientAPI' when calling setLanguage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/language", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putLanguageDTOPatientAPI The putLanguageDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> setLanguage(PutLanguageDTOPatientAPI putLanguageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setLanguageRequestCreation(putLanguageDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putLanguageDTOPatientAPI The putLanguageDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> setLanguageWithHttpInfo(PutLanguageDTOPatientAPI putLanguageDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setLanguageRequestCreation(putLanguageDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putLanguageDTOPatientAPI The putLanguageDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec setLanguageWithResponseSpec(PutLanguageDTOPatientAPI putLanguageDTOPatientAPI) throws WebClientResponseException {
        return setLanguageRequestCreation(putLanguageDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putNameDTOPatientAPI The putNameDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec setNameRequestCreation(PutNameDTOPatientAPI putNameDTOPatientAPI) throws WebClientResponseException {
        Object postBody = putNameDTOPatientAPI;
        // verify the required parameter 'putNameDTOPatientAPI' is set
        if (putNameDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'putNameDTOPatientAPI' when calling setName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/name", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putNameDTOPatientAPI The putNameDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> setName(PutNameDTOPatientAPI putNameDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setNameRequestCreation(putNameDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putNameDTOPatientAPI The putNameDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> setNameWithHttpInfo(PutNameDTOPatientAPI putNameDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setNameRequestCreation(putNameDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putNameDTOPatientAPI The putNameDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec setNameWithResponseSpec(PutNameDTOPatientAPI putNameDTOPatientAPI) throws WebClientResponseException {
        return setNameRequestCreation(putNameDTOPatientAPI);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putOnboardedDTOPatientAPI The putOnboardedDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec setOnboardedRequestCreation(PutOnboardedDTOPatientAPI putOnboardedDTOPatientAPI) throws WebClientResponseException {
        Object postBody = putOnboardedDTOPatientAPI;
        // verify the required parameter 'putOnboardedDTOPatientAPI' is set
        if (putOnboardedDTOPatientAPI == null) {
            throw new WebClientResponseException("Missing the required parameter 'putOnboardedDTOPatientAPI' when calling setOnboarded", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/patients/onboarded", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putOnboardedDTOPatientAPI The putOnboardedDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> setOnboarded(PutOnboardedDTOPatientAPI putOnboardedDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setOnboardedRequestCreation(putOnboardedDTOPatientAPI).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putOnboardedDTOPatientAPI The putOnboardedDTOPatientAPI parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> setOnboardedWithHttpInfo(PutOnboardedDTOPatientAPI putOnboardedDTOPatientAPI) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return setOnboardedRequestCreation(putOnboardedDTOPatientAPI).toEntity(localVarReturnType);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param putOnboardedDTOPatientAPI The putOnboardedDTOPatientAPI parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec setOnboardedWithResponseSpec(PutOnboardedDTOPatientAPI putOnboardedDTOPatientAPI) throws WebClientResponseException {
        return setOnboardedRequestCreation(putOnboardedDTOPatientAPI);
    }
}
