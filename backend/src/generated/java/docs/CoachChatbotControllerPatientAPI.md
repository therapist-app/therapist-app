# CoachChatbotControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createChatbot**](CoachChatbotControllerPatientAPI.md#createChatbot) | **POST** /coach/patients/{patientId}/chatbot |  |
| [**getChatbotConfigurations**](CoachChatbotControllerPatientAPI.md#getChatbotConfigurations) | **GET** /coach/patients/{patientId}/chatbot |  |
| [**updateChatbot**](CoachChatbotControllerPatientAPI.md#updateChatbot) | **PUT** /coach/patients/{patientId}/chatbot |  |



## createChatbot

> createChatbot(patientId, createChatbotDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachChatbotControllerPatientAPI apiInstance = new CoachChatbotControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        CreateChatbotDTOPatientAPI createChatbotDTOPatientAPI = new CreateChatbotDTOPatientAPI(); // CreateChatbotDTOPatientAPI | 
        try {
            apiInstance.createChatbot(patientId, createChatbotDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachChatbotControllerPatientAPI#createChatbot");
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
| **createChatbotDTOPatientAPI** | [**CreateChatbotDTOPatientAPI**](CreateChatbotDTOPatientAPI.md)|  | |

### Return type

null (empty response body)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **409** | Conflict |  -  |
| **201** | Created |  -  |


## getChatbotConfigurations

> List&lt;ChatbotConfigurationOutputDTOPatientAPI&gt; getChatbotConfigurations(patientId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachChatbotControllerPatientAPI apiInstance = new CoachChatbotControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        try {
            List<ChatbotConfigurationOutputDTOPatientAPI> result = apiInstance.getChatbotConfigurations(patientId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachChatbotControllerPatientAPI#getChatbotConfigurations");
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

[**List&lt;ChatbotConfigurationOutputDTOPatientAPI&gt;**](ChatbotConfigurationOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **409** | Conflict |  -  |
| **200** | OK |  -  |


## updateChatbot

> updateChatbot(patientId, updateChatbotDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachChatbotControllerPatientAPI apiInstance = new CoachChatbotControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        UpdateChatbotDTOPatientAPI updateChatbotDTOPatientAPI = new UpdateChatbotDTOPatientAPI(); // UpdateChatbotDTOPatientAPI | 
        try {
            apiInstance.updateChatbot(patientId, updateChatbotDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachChatbotControllerPatientAPI#updateChatbot");
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
| **updateChatbotDTOPatientAPI** | [**UpdateChatbotDTOPatientAPI**](UpdateChatbotDTOPatientAPI.md)|  | |

### Return type

null (empty response body)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **409** | Conflict |  -  |
| **200** | OK |  -  |

