# ExerciseControllerCoachPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createExercise**](ExerciseControllerCoachPatientAPI.md#createExercise) | **POST** /coach/{patientId}/exercises |  |



## createExercise

> createExercise(patientId, exerciseInputDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ExerciseControllerCoachPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        ExerciseControllerCoachPatientAPI apiInstance = new ExerciseControllerCoachPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        ExerciseInputDTOPatientAPI exerciseInputDTOPatientAPI = new ExerciseInputDTOPatientAPI(); // ExerciseInputDTOPatientAPI | 
        try {
            apiInstance.createExercise(patientId, exerciseInputDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling ExerciseControllerCoachPatientAPI#createExercise");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **patientId** | **String**|  | |
| **exerciseInputDTOPatientAPI** | [**ExerciseInputDTOPatientAPI**](ExerciseInputDTOPatientAPI.md)|  | |

### Return type

null (empty response body)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

