# ExerciseControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getExerciseOutputDTO**](ExerciseControllerPatientAPI.md#getExerciseOutputDTO) | **GET** /patients/exercises/{exerciseId} |  |
| [**getExerciseOverview**](ExerciseControllerPatientAPI.md#getExerciseOverview) | **GET** /patients/exercises |  |
| [**getPicture**](ExerciseControllerPatientAPI.md#getPicture) | **GET** /patients/exercises/{exerciseId}/{mediaId} |  |
| [**postExerciseFeedback**](ExerciseControllerPatientAPI.md#postExerciseFeedback) | **POST** /patients/exercises/{exerciseId} |  |



## getExerciseOutputDTO

> ExerciseOutputDTOPatientAPI getExerciseOutputDTO(exerciseId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        ExerciseControllerPatientAPI apiInstance = new ExerciseControllerPatientAPI(defaultClient);
        String exerciseId = "exerciseId_example"; // String | 
        try {
            ExerciseOutputDTOPatientAPI result = apiInstance.getExerciseOutputDTO(exerciseId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ExerciseControllerPatientAPI#getExerciseOutputDTO");
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
| **exerciseId** | **String**|  | |

### Return type

[**ExerciseOutputDTOPatientAPI**](ExerciseOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## getExerciseOverview

> List&lt;ExercisesOverviewOutputDTOPatientAPI&gt; getExerciseOverview()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        ExerciseControllerPatientAPI apiInstance = new ExerciseControllerPatientAPI(defaultClient);
        try {
            List<ExercisesOverviewOutputDTOPatientAPI> result = apiInstance.getExerciseOverview();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ExerciseControllerPatientAPI#getExerciseOverview");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

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


## getPicture

> ExerciseMediaOutputDTOPatientAPI getPicture(exerciseId, mediaId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        ExerciseControllerPatientAPI apiInstance = new ExerciseControllerPatientAPI(defaultClient);
        String exerciseId = "exerciseId_example"; // String | 
        String mediaId = "mediaId_example"; // String | 
        try {
            ExerciseMediaOutputDTOPatientAPI result = apiInstance.getPicture(exerciseId, mediaId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ExerciseControllerPatientAPI#getPicture");
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
| **exerciseId** | **String**|  | |
| **mediaId** | **String**|  | |

### Return type

[**ExerciseMediaOutputDTOPatientAPI**](ExerciseMediaOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## postExerciseFeedback

> postExerciseFeedback(exerciseId, exerciseInformationInputDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ExerciseControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        ExerciseControllerPatientAPI apiInstance = new ExerciseControllerPatientAPI(defaultClient);
        String exerciseId = "exerciseId_example"; // String | 
        ExerciseInformationInputDTOPatientAPI exerciseInformationInputDTOPatientAPI = new ExerciseInformationInputDTOPatientAPI(); // ExerciseInformationInputDTOPatientAPI | 
        try {
            apiInstance.postExerciseFeedback(exerciseId, exerciseInformationInputDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling ExerciseControllerPatientAPI#postExerciseFeedback");
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
| **exerciseId** | **String**|  | |
| **exerciseInformationInputDTOPatientAPI** | [**ExerciseInformationInputDTOPatientAPI**](ExerciseInformationInputDTOPatientAPI.md)|  | |

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
| **200** | OK |  -  |

