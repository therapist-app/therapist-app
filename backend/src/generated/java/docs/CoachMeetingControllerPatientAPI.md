# CoachMeetingControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createMeeting1**](CoachMeetingControllerPatientAPI.md#createMeeting1) | **POST** /coach/patients/{patientId}/meetings |  |
| [**deleteMeeting1**](CoachMeetingControllerPatientAPI.md#deleteMeeting1) | **DELETE** /coach/patients/{patientId}/meetings/{meetingId} |  |
| [**getMeeting1**](CoachMeetingControllerPatientAPI.md#getMeeting1) | **GET** /coach/patients/{patientId}/meetings/{meetingId} |  |
| [**listMeetings1**](CoachMeetingControllerPatientAPI.md#listMeetings1) | **GET** /coach/patients/{patientId}/meetings |  |
| [**updateMeeting1**](CoachMeetingControllerPatientAPI.md#updateMeeting1) | **PUT** /coach/patients/{patientId}/meetings/{meetingId} |  |



## createMeeting1

> MeetingOutputDTOPatientAPI createMeeting1(patientId, createMeetingDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachMeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachMeetingControllerPatientAPI apiInstance = new CoachMeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        CreateMeetingDTOPatientAPI createMeetingDTOPatientAPI = new CreateMeetingDTOPatientAPI(); // CreateMeetingDTOPatientAPI | 
        try {
            MeetingOutputDTOPatientAPI result = apiInstance.createMeeting1(patientId, createMeetingDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachMeetingControllerPatientAPI#createMeeting1");
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
| **createMeetingDTOPatientAPI** | [**CreateMeetingDTOPatientAPI**](CreateMeetingDTOPatientAPI.md)|  | |

### Return type

[**MeetingOutputDTOPatientAPI**](MeetingOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |


## deleteMeeting1

> deleteMeeting1(patientId, meetingId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachMeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachMeetingControllerPatientAPI apiInstance = new CoachMeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String meetingId = "meetingId_example"; // String | 
        try {
            apiInstance.deleteMeeting1(patientId, meetingId);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachMeetingControllerPatientAPI#deleteMeeting1");
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
| **meetingId** | **String**|  | |

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


## getMeeting1

> MeetingOutputDTOPatientAPI getMeeting1(patientId, meetingId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachMeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachMeetingControllerPatientAPI apiInstance = new CoachMeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String meetingId = "meetingId_example"; // String | 
        try {
            MeetingOutputDTOPatientAPI result = apiInstance.getMeeting1(patientId, meetingId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachMeetingControllerPatientAPI#getMeeting1");
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
| **meetingId** | **String**|  | |

### Return type

[**MeetingOutputDTOPatientAPI**](MeetingOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## listMeetings1

> List&lt;MeetingOutputDTOPatientAPI&gt; listMeetings1(patientId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachMeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachMeetingControllerPatientAPI apiInstance = new CoachMeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        try {
            List<MeetingOutputDTOPatientAPI> result = apiInstance.listMeetings1(patientId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachMeetingControllerPatientAPI#listMeetings1");
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

[**List&lt;MeetingOutputDTOPatientAPI&gt;**](MeetingOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## updateMeeting1

> MeetingOutputDTOPatientAPI updateMeeting1(patientId, meetingId, updateMeetingDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.auth.*;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.CoachMeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");
        
        // Configure API key authorization: X-Coach-Key
        ApiKeyAuth X-Coach-Key = (ApiKeyAuth) defaultClient.getAuthentication("X-Coach-Key");
        X-Coach-Key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //X-Coach-Key.setApiKeyPrefix("Token");

        CoachMeetingControllerPatientAPI apiInstance = new CoachMeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String meetingId = "meetingId_example"; // String | 
        UpdateMeetingDTOPatientAPI updateMeetingDTOPatientAPI = new UpdateMeetingDTOPatientAPI(); // UpdateMeetingDTOPatientAPI | 
        try {
            MeetingOutputDTOPatientAPI result = apiInstance.updateMeeting1(patientId, meetingId, updateMeetingDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoachMeetingControllerPatientAPI#updateMeeting1");
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
| **meetingId** | **String**|  | |
| **updateMeetingDTOPatientAPI** | [**UpdateMeetingDTOPatientAPI**](UpdateMeetingDTOPatientAPI.md)|  | |

### Return type

[**MeetingOutputDTOPatientAPI**](MeetingOutputDTOPatientAPI.md)

### Authorization

[X-Coach-Key](../README.md#X-Coach-Key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

