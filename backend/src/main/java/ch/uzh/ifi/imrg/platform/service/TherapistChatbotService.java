package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatbotOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TherapistChatbotService {
  private final PatientRepository patientRepository;
  private final TherapistRepository therapistRepository;
  private final PatientAppContextService contextService;

  public TherapistChatbotService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository,
      PatientAppContextService contextService) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
    this.contextService = contextService;
  }

  public TherapistChatbotOutputDTO chat(
      TherapistChatbotInputDTO therapistChatbotInputDTO, String therapistId) {
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    Patient patient = null;

    if (therapistChatbotInputDTO.getPatientId() != null) {
      patient = patientRepository.getReferenceById(therapistChatbotInputDTO.getPatientId());
    }

    String systemPrompt = getSystemPrompt(therapist, patient);
    List<ChatMessageDTO> chatMessages = new ArrayList<>();
    chatMessages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    chatMessages.addAll(therapistChatbotInputDTO.getChatMessages());

    String responseMessage =
        LLMFactory.getInstance(therapist.getLlmModel())
            .callLLM(chatMessages, therapistChatbotInputDTO.getLanguage());
    TherapistChatbotOutputDTO therapistChatbotOutputDTO = new TherapistChatbotOutputDTO();
    therapistChatbotOutputDTO.setContent(responseMessage);
    return therapistChatbotOutputDTO;
  }

  private String getSystemPrompt(Therapist loggedInTherapist, Patient patient) {
    StringBuilder promptBuilder = new StringBuilder();

    promptBuilder.append("# AI Assistant Directives\n\n");
    promptBuilder.append(
        "You are a specialized AI assistant for a coach. Your primary goal is to help the coach by providing precise and context-aware answers to their questions.\n\n");
    promptBuilder.append("## Core Rules:\n");
    promptBuilder.append(
        "1.  **Analyze the entire query and all provided client data before answering.**\n");
    promptBuilder.append(
        "2.  **Base all answers strictly on the information provided.** Do not invent or infer information that isn't present in the context.\n");
    promptBuilder.append(
        "3.  **Consider all clients unless the user specifies a particular client.**\n");
    promptBuilder.append(
        "4.  **When asked about schedules, consider the current date and time to determine what is \"next\".**\n");
    promptBuilder.append(
        "5.  **If the answer is not in the provided documents or client data, explicitly state that.**\n");
    promptBuilder.append(
        "6.  **Be concise and to the point.** Provide the information requested without unnecessary conversational filler.\n\n");

    if (patient == null) {
      promptBuilder.append(loggedInTherapist.toLLMContext(0));
    } else {
      promptBuilder.append(patient.toLLMContext(0));
      promptBuilder.append(contextService.buildContext(patient.getId()));
    }

    return promptBuilder.toString();
  }
}
