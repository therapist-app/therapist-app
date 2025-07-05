

# MeetingOutputDTOPatientAPI


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  [optional] |
|**externalMeetingId** | **String** |  |  [optional] |
|**createdAt** | **Instant** |  |  [optional] |
|**updatedAt** | **Instant** |  |  [optional] |
|**patientId** | **String** |  |  [optional] |
|**startAt** | **Instant** |  |  [optional] |
|**endAt** | **Instant** |  |  [optional] |
|**location** | **String** |  |  [optional] |
|**meetingStatus** | [**MeetingStatusEnum**](#MeetingStatusEnum) |  |  [optional] |



## Enum: MeetingStatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;PENDING&quot; |
| CONFIRMED | &quot;CONFIRMED&quot; |
| CANCELLED | &quot;CANCELLED&quot; |



