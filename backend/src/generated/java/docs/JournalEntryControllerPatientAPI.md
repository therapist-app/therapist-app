# JournalEntryControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**create**](JournalEntryControllerPatientAPI.md#create) | **POST** /patients/journal-entries |  |
| [**deleteEntry**](JournalEntryControllerPatientAPI.md#deleteEntry) | **DELETE** /patients/journal-entries/{entryId} |  |
| [**getAllTags**](JournalEntryControllerPatientAPI.md#getAllTags) | **GET** /patients/journal-entries/tags |  |
| [**getOne**](JournalEntryControllerPatientAPI.md#getOne) | **GET** /patients/journal-entries/{entryId} |  |
| [**listAll**](JournalEntryControllerPatientAPI.md#listAll) | **GET** /patients/journal-entries |  |
| [**updateJournalEntry**](JournalEntryControllerPatientAPI.md#updateJournalEntry) | **PUT** /patients/journal-entries/{entryId} |  |



## create

> JournalEntryOutputDTOPatientAPI create(journalEntryRequestDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.JournalEntryControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        JournalEntryControllerPatientAPI apiInstance = new JournalEntryControllerPatientAPI(defaultClient);
        JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI = new JournalEntryRequestDTOPatientAPI(); // JournalEntryRequestDTOPatientAPI | 
        try {
            JournalEntryOutputDTOPatientAPI result = apiInstance.create(journalEntryRequestDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling JournalEntryControllerPatientAPI#create");
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
| **journalEntryRequestDTOPatientAPI** | [**JournalEntryRequestDTOPatientAPI**](JournalEntryRequestDTOPatientAPI.md)|  | |

### Return type

[**JournalEntryOutputDTOPatientAPI**](JournalEntryOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |


## deleteEntry

> deleteEntry(entryId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.JournalEntryControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        JournalEntryControllerPatientAPI apiInstance = new JournalEntryControllerPatientAPI(defaultClient);
        String entryId = "entryId_example"; // String | 
        try {
            apiInstance.deleteEntry(entryId);
        } catch (ApiException e) {
            System.err.println("Exception when calling JournalEntryControllerPatientAPI#deleteEntry");
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
| **entryId** | **String**|  | |

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


## getAllTags

> Set&lt;String&gt; getAllTags()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.JournalEntryControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        JournalEntryControllerPatientAPI apiInstance = new JournalEntryControllerPatientAPI(defaultClient);
        try {
            Set<String> result = apiInstance.getAllTags();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling JournalEntryControllerPatientAPI#getAllTags");
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

**Set&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## getOne

> JournalEntryOutputDTOPatientAPI getOne(entryId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.JournalEntryControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        JournalEntryControllerPatientAPI apiInstance = new JournalEntryControllerPatientAPI(defaultClient);
        String entryId = "entryId_example"; // String | 
        try {
            JournalEntryOutputDTOPatientAPI result = apiInstance.getOne(entryId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling JournalEntryControllerPatientAPI#getOne");
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
| **entryId** | **String**|  | |

### Return type

[**JournalEntryOutputDTOPatientAPI**](JournalEntryOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## listAll

> List&lt;GetAllJournalEntriesDTOPatientAPI&gt; listAll()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.JournalEntryControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        JournalEntryControllerPatientAPI apiInstance = new JournalEntryControllerPatientAPI(defaultClient);
        try {
            List<GetAllJournalEntriesDTOPatientAPI> result = apiInstance.listAll();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling JournalEntryControllerPatientAPI#listAll");
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

[**List&lt;GetAllJournalEntriesDTOPatientAPI&gt;**](GetAllJournalEntriesDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## updateJournalEntry

> JournalEntryOutputDTOPatientAPI updateJournalEntry(entryId, journalEntryRequestDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.JournalEntryControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        JournalEntryControllerPatientAPI apiInstance = new JournalEntryControllerPatientAPI(defaultClient);
        String entryId = "entryId_example"; // String | 
        JournalEntryRequestDTOPatientAPI journalEntryRequestDTOPatientAPI = new JournalEntryRequestDTOPatientAPI(); // JournalEntryRequestDTOPatientAPI | 
        try {
            JournalEntryOutputDTOPatientAPI result = apiInstance.updateJournalEntry(entryId, journalEntryRequestDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling JournalEntryControllerPatientAPI#updateJournalEntry");
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
| **entryId** | **String**|  | |
| **journalEntryRequestDTOPatientAPI** | [**JournalEntryRequestDTOPatientAPI**](JournalEntryRequestDTOPatientAPI.md)|  | |

### Return type

[**JournalEntryOutputDTOPatientAPI**](JournalEntryOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

