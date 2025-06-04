# PatientControllerPatientAPI

All URIs are relative to *http://backend-patient-app-main.jonas-blum.ch*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getCurrentlyLoggedInPatient**](PatientControllerPatientAPI.md#getCurrentlyLoggedInPatient) | **GET** /patients/me |  |
| [**getLanguage**](PatientControllerPatientAPI.md#getLanguage) | **GET** /patients/language |  |
| [**getOnboarded**](PatientControllerPatientAPI.md#getOnboarded) | **GET** /patients/onboarded |  |
| [**loginTherapist**](PatientControllerPatientAPI.md#loginTherapist) | **POST** /patients/login |  |
| [**logoutTherapist**](PatientControllerPatientAPI.md#logoutTherapist) | **POST** /patients/logout |  |
| [**registerPatient**](PatientControllerPatientAPI.md#registerPatient) | **POST** /patients/register |  |
| [**setLanguage**](PatientControllerPatientAPI.md#setLanguage) | **PUT** /patients/language |  |
| [**setOnboarded**](PatientControllerPatientAPI.md#setOnboarded) | **PUT** /patients/onboarded |  |



## getCurrentlyLoggedInPatient

> PatientOutputDTOPatientAPI getCurrentlyLoggedInPatient()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        try {
            PatientOutputDTOPatientAPI result = apiInstance.getCurrentlyLoggedInPatient();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#getCurrentlyLoggedInPatient");
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

[**PatientOutputDTOPatientAPI**](PatientOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## getLanguage

> PatientOutputDTOPatientAPI getLanguage()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        try {
            PatientOutputDTOPatientAPI result = apiInstance.getLanguage();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#getLanguage");
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

[**PatientOutputDTOPatientAPI**](PatientOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## getOnboarded

> PatientOutputDTOPatientAPI getOnboarded()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        try {
            PatientOutputDTOPatientAPI result = apiInstance.getOnboarded();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#getOnboarded");
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

[**PatientOutputDTOPatientAPI**](PatientOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## loginTherapist

> PatientOutputDTOPatientAPI loginTherapist(loginPatientDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        LoginPatientDTOPatientAPI loginPatientDTOPatientAPI = new LoginPatientDTOPatientAPI(); // LoginPatientDTOPatientAPI | 
        try {
            PatientOutputDTOPatientAPI result = apiInstance.loginTherapist(loginPatientDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#loginTherapist");
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
| **loginPatientDTOPatientAPI** | [**LoginPatientDTOPatientAPI**](LoginPatientDTOPatientAPI.md)|  | |

### Return type

[**PatientOutputDTOPatientAPI**](PatientOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## logoutTherapist

> logoutTherapist()



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        try {
            apiInstance.logoutTherapist();
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#logoutTherapist");
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

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## registerPatient

> PatientOutputDTOPatientAPI registerPatient(createPatientDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        CreatePatientDTOPatientAPI createPatientDTOPatientAPI = new CreatePatientDTOPatientAPI(); // CreatePatientDTOPatientAPI | 
        try {
            PatientOutputDTOPatientAPI result = apiInstance.registerPatient(createPatientDTOPatientAPI);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#registerPatient");
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
| **createPatientDTOPatientAPI** | [**CreatePatientDTOPatientAPI**](CreatePatientDTOPatientAPI.md)|  | |

### Return type

[**PatientOutputDTOPatientAPI**](PatientOutputDTOPatientAPI.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |


## setLanguage

> setLanguage(putLanguageDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        PutLanguageDTOPatientAPI putLanguageDTOPatientAPI = new PutLanguageDTOPatientAPI(); // PutLanguageDTOPatientAPI | 
        try {
            apiInstance.setLanguage(putLanguageDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#setLanguage");
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
| **putLanguageDTOPatientAPI** | [**PutLanguageDTOPatientAPI**](PutLanguageDTOPatientAPI.md)|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## setOnboarded

> setOnboarded(putOnboardedDTOPatientAPI)



### Example

```java
// Import classes:
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import ch.uzh.ifi.imrg.generated.invoker.ApiException;
import ch.uzh.ifi.imrg.generated.invoker.Configuration;
import ch.uzh.ifi.imrg.generated.invoker.models.*;
import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://backend-patient-app-main.jonas-blum.ch");

        PatientControllerPatientAPI apiInstance = new PatientControllerPatientAPI(defaultClient);
        PutOnboardedDTOPatientAPI putOnboardedDTOPatientAPI = new PutOnboardedDTOPatientAPI(); // PutOnboardedDTOPatientAPI | 
        try {
            apiInstance.setOnboarded(putOnboardedDTOPatientAPI);
        } catch (ApiException e) {
            System.err.println("Exception when calling PatientControllerPatientAPI#setOnboarded");
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
| **putOnboardedDTOPatientAPI** | [**PutOnboardedDTOPatientAPI**](PutOnboardedDTOPatientAPI.md)|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

