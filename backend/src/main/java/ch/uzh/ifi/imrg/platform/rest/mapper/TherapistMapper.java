package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TherapistMapper {

  TherapistMapper INSTANCE = Mappers.getMapper(TherapistMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "language", target = "language")
  @Mapping(source = "chatbotTemplates", target = "chatbotTemplatesOutputDTO")
  @Mapping(source = "patients", target = "patientsOutputDTO")
  TherapistOutputDTO convertEntityToTherapistOutputDTO(Therapist therapist);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "patients", ignore = true)
  @Mapping(target = "chatbotTemplates", ignore = true)
  @Mapping(target = "therapistDocuments", ignore = true)
  @Mapping(target = "language", ignore = true)
  Therapist convertCreateTherapistDTOtoEntity(CreateTherapistDTO therapistDTO);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "patients", ignore = true)
  @Mapping(target = "chatbotTemplates", ignore = true)
  @Mapping(target = "therapistDocuments", ignore = true)
  @Mapping(target = "language", ignore = true)
  Therapist convertLoginTherapistDTOtoEntity(LoginTherapistDTO therapistDTO);

  default List<ChatbotTemplateOutputDTO> mapChatbotTemplates(
      List<ChatbotTemplate> chatbotTemplates) {
    if (chatbotTemplates == null) {
      return null;
    }
    return chatbotTemplates.stream()
        .map(ChatbotTemplateMapper.INSTANCE::convertEntityToChatbotTemplateOutputDTO)
        .collect(Collectors.toList());
  }

  default List<PatientOutputDTO> mapPatients(List<Patient> patients) {
    if (patients == null) {
      return null;
    }
    return patients.stream()
        .map(PatientMapper.INSTANCE::convertEntityToPatientOutputDTO)
        .collect(Collectors.toList());
  }
}
