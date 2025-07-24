package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.GetConversationSummaryInputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.exceptions.PrivateConversationException;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ConversationSummaryOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import jakarta.transaction.Transactional;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ConversationService {

  private final PatientRepository patientRepo;

  public ConversationService(PatientRepository patientRepo) {
    this.patientRepo = patientRepo;
  }

  public ConversationSummaryOutputDTO getConversationSummary(
      String patientId, String therapistId, Instant start, Instant end) {

    if (!patientRepo.existsByIdAndTherapistId(patientId, therapistId)) {
      throw new IllegalArgumentException("Patient does not belong to therapist");
    }

    GetConversationSummaryInputDTOPatientAPI body = new GetConversationSummaryInputDTOPatientAPI();
    body.setStart(start);
    body.setEnd(end);

    try {
      var patientResp =
          PatientAppAPIs.coachChatbotControllerPatientAPI
              .getConversationSummary(body, patientId)
              .block();

      ConversationSummaryOutputDTO dto = new ConversationSummaryOutputDTO();
      dto.setConversationSummary(patientResp.getConversationSummary());
      return dto;

    } catch (org.springframework.web.reactive.function.client.WebClientResponseException e) {
      if (e.getStatusCode().is5xxServerError()) {
        throw new PrivateConversationException(
            "The conversation of the client with the chatbot is private.");
      }
      throw e;
    }
  }
}
