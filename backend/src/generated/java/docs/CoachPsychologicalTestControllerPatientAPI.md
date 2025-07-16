# CoachPsychologicalTestControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getPsychologicalTestNames**](CoachPsychologicalTestControllerPatientAPI.md#getPsychologicalTestNames) | **GET** /coach/patients/{patientId}/psychological-tests |  |
| [**getPsychologicalTestResults**](CoachPsychologicalTestControllerPatientAPI.md#getPsychologicalTestResults) | **GET** /coach/patients/{patientId}/psychological-tests/{psychologicalTestName} |  |



## getPsychologicalTestNames

> List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt; getPsychologicalTestNames(patientId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachPsychologicalTestControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachPsychologicalTestControllerPatientAPI apiInstance = new CoachPsychologicalTestControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        try {
            List<PsychologicalTestNameOutputDTOPatientAPI> result = apiInstance.getPsychologicalTestNames(patientId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachPsychologicalTestControllerPatientAPI#getPsychologicalTestNames");
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

[**List&lt;PsychologicalTestNameOutputDTOPatientAPI&gt;**](PsychologicalTestNameOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## getPsychologicalTestResults

> List&lt;PsychologicalTestOutputDTOPatientAPI&gt; getPsychologicalTestResults(patientId, psychologicalTestName)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachPsychologicalTestControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachPsychologicalTestControllerPatientAPI apiInstance = new CoachPsychologicalTestControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String psychologicalTestName = "psychologicalTestName_example"; // String | 
        try {
            List<PsychologicalTestOutputDTOPatientAPI> result = apiInstance.getPsychologicalTestResults(patientId, psychologicalTestName);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachPsychologicalTestControllerPatientAPI#getPsychologicalTestResults");
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
| **psychologicalTestName** | **String**|  | |

### Return type

[**List&lt;PsychologicalTestOutputDTOPatientAPI&gt;**](PsychologicalTestOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

