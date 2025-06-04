# ConversationControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createConversation**](ConversationControllerPatientAPI.md#createConversation) | **POST** /patients/conversations |  |
| [**getAllMessages**](ConversationControllerPatientAPI.md#getAllMessages) | **GET** /patients/conversations/messages/{conversationId} |  |
| [**nameConversationDTO**](ConversationControllerPatientAPI.md#nameConversationDTO) | **GET** /patients/conversations/{patientId} |  |
| [**sendMessage**](ConversationControllerPatientAPI.md#sendMessage) | **POST** /patients/conversations/messages/{conversationId} |  |



## createConversation

> CreateConversationOutputDTOPatientAPI createConversation()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ConversationControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        ConversationControllerPatientAPI apiInstance = new ConversationControllerPatientAPI(defaultClient);
        try {
            CreateConversationOutputDTOPatientAPI result = apiInstance.createConversation();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ConversationControllerPatientAPI#createConversation");
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

[**CreateConversationOutputDTOPatientAPI**](CreateConversationOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |


## getAllMessages

> CompleteConversationOutputDTOPatientAPI getAllMessages(conversationId)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ConversationControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        ConversationControllerPatientAPI apiInstance = new ConversationControllerPatientAPI(defaultClient);
        String conversationId = "conversationId_example"; // String | 
        try {
            CompleteConversationOutputDTOPatientAPI result = apiInstance.getAllMessages(conversationId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ConversationControllerPatientAPI#getAllMessages");
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
| **conversationId** | **String**|  | |

### Return type

[**CompleteConversationOutputDTOPatientAPI**](CompleteConversationOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## nameConversationDTO

> List&lt;NameConversationOutputDTOPatientAPI&gt; nameConversationDTO()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ConversationControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        ConversationControllerPatientAPI apiInstance = new ConversationControllerPatientAPI(defaultClient);
        try {
            List<NameConversationOutputDTOPatientAPI> result = apiInstance.nameConversationDTO();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ConversationControllerPatientAPI#nameConversationDTO");
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

[**List&lt;NameConversationOutputDTOPatientAPI&gt;**](NameConversationOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## sendMessage

> MessageOutputDTOPatientAPI sendMessage(conversationId, createMessageDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.ConversationControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        ConversationControllerPatientAPI apiInstance = new ConversationControllerPatientAPI(defaultClient);
        String conversationId = "conversationId_example"; // String | 
        CreateMessageDTOPatientAPI createMessageDTOPatientAPI = new CreateMessageDTOPatientAPI(); // CreateMessageDTOPatientAPI | 
        try {
            MessageOutputDTOPatientAPI result = apiInstance.sendMessage(conversationId, createMessageDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ConversationControllerPatientAPI#sendMessage");
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
| **conversationId** | **String**|  | |
| **createMessageDTOPatientAPI** | [**CreateMessageDTOPatientAPI**](CreateMessageDTOPatientAPI.md)|  | |

### Return type

[**MessageOutputDTOPatientAPI**](MessageOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

