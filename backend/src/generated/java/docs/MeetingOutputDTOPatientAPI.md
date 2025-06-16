

# MeetingOutputDTOPatientAPI


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  [optional] |
|**externalMeetingId** | **String** |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**updatedAt** | **OffsetDateTime** |  |  [optional] |
|**patientId** | **String** |  |  [optional] |
|**startAt** | **OffsetDateTime** |  |  [optional] |
|**endAt** | **OffsetDateTime** |  |  [optional] |
|**location** | **String** |  |  [optional] |
|**meetingStatus** | [**MeetingStatusEnum**](#MeetingStatusEnum) |  |  [optional] |



## Enum: MeetingStatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;PENDING&quot; |
| CONFIRMED | &quot;CONFIRMED&quot; |
| CANCELLED | &quot;CANCELLED&quot; |



