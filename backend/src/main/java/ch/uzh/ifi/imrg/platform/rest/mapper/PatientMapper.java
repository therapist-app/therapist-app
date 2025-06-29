package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.Complaint;
import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ComplaintDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {

  PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "createdAt", target = "createdAt")
  @Mapping(source = "updatedAt", target = "updatedAt")
  @Mapping(source = "phoneNumber", target = "phoneNumber")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "gender", target = "gender")
  @Mapping(source = "age", target = "age")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "maritalStatus", target = "maritalStatus")
  @Mapping(source = "religion", target = "religion")
  @Mapping(source = "education", target = "education")
  @Mapping(source = "occupation", target = "occupation")
  @Mapping(source = "income", target = "income")
  @Mapping(source = "dateOfAdmission", target = "dateOfAdmission")
  @Mapping(source = "meetings", target = "meetingsOutputDTO")
  PatientOutputDTO convertEntityToPatientOutputDTO(Patient patient);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "gender", target = "gender")
  @Mapping(source = "age", target = "age")
  @Mapping(source = "phoneNumber", target = "phoneNumber")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "maritalStatus", target = "maritalStatus")
  @Mapping(source = "religion", target = "religion")
  @Mapping(source = "education", target = "education")
  @Mapping(source = "occupation", target = "occupation")
  @Mapping(source = "income", target = "income")
  @Mapping(source = "dateOfAdmission", target = "dateOfAdmission")
  @Mapping(source = "complaints", target = "complaints")
  @Mapping(source = "treatmentPast", target = "treatmentPast")
  @Mapping(source = "treatmentCurrent", target = "treatmentCurrent")
  @Mapping(source = "pastMedical", target = "pastMedical")
  @Mapping(source = "pastPsych", target = "pastPsych")
  @Mapping(source = "familyIllness", target = "familyIllness")
  @Mapping(source = "familySocial", target = "familySocial")
  @Mapping(source = "personalPerinatal", target = "personalPerinatal")
  @Mapping(source = "personalChildhood", target = "personalChildhood")
  @Mapping(source = "personalEducation", target = "personalEducation")
  @Mapping(source = "personalPlay", target = "personalPlay")
  @Mapping(source = "personalAdolescence", target = "personalAdolescence")
  @Mapping(source = "personalPuberty", target = "personalPuberty")
  @Mapping(source = "personalObstetric", target = "personalObstetric")
  @Mapping(source = "personalOccupational", target = "personalOccupational")
  @Mapping(source = "personalMarital", target = "personalMarital")
  @Mapping(source = "personalPremorbid", target = "personalPremorbid")
  Patient convertCreatePatientDtoToEntity(CreatePatientDTO createPatientDTO);

  List<Complaint> complaintDtoListToComplaintList(List<ComplaintDTO> complaintDTOs);

  Complaint complaintDtoToComplaint(ComplaintDTO complaintDTO);

  default List<MeetingOutputDTO> mapMeetings(List<Meeting> meetings) {
    if (meetings == null) {
      return null;
    }
    return meetings.stream()
        .map(MeetingsMapper.INSTANCE::convertEntityToMeetingOutputDTO)
        .collect(Collectors.toList());
  }
}
