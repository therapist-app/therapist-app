# MeetingControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createMeeting**](MeetingControllerPatientAPI.md#createMeeting) | **POST** /patients/{patientId}/meetings |  |
| [**deleteMeeting**](MeetingControllerPatientAPI.md#deleteMeeting) | **DELETE** /patients/{patientId}/meetings/{meetingId} |  |
| [**getMeeting**](MeetingControllerPatientAPI.md#getMeeting) | **GET** /patients/{patientId}/meetings/{meetingId} |  |
| [**listMeetings**](MeetingControllerPatientAPI.md#listMeetings) | **GET** /patients/{patientId}/meetings |  |
| [**updateMeeting**](MeetingControllerPatientAPI.md#updateMeeting) | **PUT** /patients/{patientId}/meetings/{meetingId} |  |



## createMeeting

> MeetingOutputDTOPatientAPI createMeeting(patientId, createMeetingDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.MeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        MeetingControllerPatientAPI apiInstance = new MeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        CreateMeetingDTOPatientAPI createMeetingDTOPatientAPI = new CreateMeetingDTOPatientAPI(); // CreateMeetingDTOPatientAPI | 
        try {
            MeetingOutputDTOPatientAPI result = apiInstance.createMeeting(patientId, createMeetingDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MeetingControllerPatientAPI#createMeeting");
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

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |


## deleteMeeting

> deleteMeeting(patientId, meetingId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.MeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        MeetingControllerPatientAPI apiInstance = new MeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String meetingId = "meetingId_example"; // String | 
        try {
            apiInstance.deleteMeeting(patientId, meetingId);
        } catch (ApiException e) {
            System.err.println("Exception when calling MeetingControllerPatientAPI#deleteMeeting");
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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | No Content |  -  |


## getMeeting

> MeetingOutputDTOPatientAPI getMeeting(patientId, meetingId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.MeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        MeetingControllerPatientAPI apiInstance = new MeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String meetingId = "meetingId_example"; // String | 
        try {
            MeetingOutputDTOPatientAPI result = apiInstance.getMeeting(patientId, meetingId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MeetingControllerPatientAPI#getMeeting");
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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## listMeetings

> List&lt;MeetingOutputDTOPatientAPI&gt; listMeetings(patientId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.MeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        MeetingControllerPatientAPI apiInstance = new MeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        try {
            List<MeetingOutputDTOPatientAPI> result = apiInstance.listMeetings(patientId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MeetingControllerPatientAPI#listMeetings");
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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## updateMeeting

> MeetingOutputDTOPatientAPI updateMeeting(patientId, meetingId, updateMeetingDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.MeetingControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        MeetingControllerPatientAPI apiInstance = new MeetingControllerPatientAPI(defaultClient);
        String patientId = "patientId_example"; // String | 
        String meetingId = "meetingId_example"; // String | 
        UpdateMeetingDTOPatientAPI updateMeetingDTOPatientAPI = new UpdateMeetingDTOPatientAPI(); // UpdateMeetingDTOPatientAPI | 
        try {
            MeetingOutputDTOPatientAPI result = apiInstance.updateMeeting(patientId, meetingId, updateMeetingDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MeetingControllerPatientAPI#updateMeeting");
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

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

