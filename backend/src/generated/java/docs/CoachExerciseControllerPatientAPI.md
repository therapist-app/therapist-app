# CoachExerciseControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createExercise1**](CoachExerciseControllerPatientAPI.md#createExercise1) | **POST** /coach/patients/{patientId}/exercises |  |
| [**deleteExercise**](CoachExerciseControllerPatientAPI.md#deleteExercise) | **DELETE** /coach/patients/{patientId}/exercises/{exerciseId} |  |
| [**getAllExercises**](CoachExerciseControllerPatientAPI.md#getAllExercises) | **GET** /coach/patients/{patientId}/exercises |  |
| [**updateExercise**](CoachExerciseControllerPatientAPI.md#updateExercise) | **PUT** /coach/patients/{patientId}/exercises{exerciseId} |  |



## createExercise1

> createExercise1(patientId, exerciseInputDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachExerciseControllerPatientAPI apiInstance = new CoachExerciseControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        ExerciseInputDTOPatientAPI exerciseInputDTOPatientAPI = new ExerciseInputDTOPatientAPI(); // ExerciseInputDTOPatientAPI | 
        try {
            apiInstance.createExercise1(patientId, exerciseInputDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachExerciseControllerPatientAPI#createExercise1");
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


## deleteExercise

> deleteExercise(patientId, exerciseId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachExerciseControllerPatientAPI apiInstance = new CoachExerciseControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String exerciseId = "exerciseId_example"; // String | 
        try {
            apiInstance.deleteExercise(patientId, exerciseId);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachExerciseControllerPatientAPI#deleteExercise");
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
| **exerciseId** | **String**|  | |

### Return type

null (empty response body)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | No Content |  -  |


## getAllExercises

> List&lt;ExercisesOverviewOutputDTOPatientAPI&gt; getAllExercises(patientId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachExerciseControllerPatientAPI apiInstance = new CoachExerciseControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        try {
            List<ExercisesOverviewOutputDTOPatientAPI> result = apiInstance.getAllExercises(patientId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachExerciseControllerPatientAPI#getAllExercises");
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

### Return type

[**List&lt;ExercisesOverviewOutputDTOPatientAPI&gt;**](ExercisesOverviewOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## updateExercise

> updateExercise(patientId, exerciseId, exerciseInputDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachExerciseControllerPatientAPI apiInstance = new CoachExerciseControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String exerciseId = "exerciseId_example"; // String | 
        ExerciseInputDTOPatientAPI exerciseInputDTOPatientAPI = new ExerciseInputDTOPatientAPI(); // ExerciseInputDTOPatientAPI | 
        try {
            apiInstance.updateExercise(patientId, exerciseId, exerciseInputDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachExerciseControllerPatientAPI#updateExercise");
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
| **exerciseId** | **String**|  | |
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
| **204** | No Content |  -  |

