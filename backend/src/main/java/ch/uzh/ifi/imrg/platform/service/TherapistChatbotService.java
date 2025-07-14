package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatbotOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.LLMContextUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
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

  public TherapistChatbotService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
  }

  public TherapistChatbotOutputDTO chat(
      TherapistChatbotInputDTO therapistChatbotInputDTO, String therapistId) {
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    List<Patient> patients;

    if (therapistChatbotInputDTO.getPatientId() != null) {
      patients = new ArrayList<>();
      patients.add(patientRepository.getReferenceById(therapistChatbotInputDTO.getPatientId()));
    } else {
      patients = therapist.getPatients();
    }

    String systemPrompt = getSystemPrompt(therapist, patients);
    List<ChatMessageDTO> chatMessages = new ArrayList<>();
    chatMessages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    chatMessages.addAll(therapistChatbotInputDTO.getChatMessages());

    System.out.println(chatMessages);

    String responseMessage = LLMUZH.callLLM(chatMessages);
    TherapistChatbotOutputDTO therapistChatbotOutputDTO = new TherapistChatbotOutputDTO();
    therapistChatbotOutputDTO.setContent(responseMessage);
    return therapistChatbotOutputDTO;
  }

  private String getSystemPrompt(Therapist loggedInTherapist, List<Patient> patients) {
    StringBuilder promptBuilder = new StringBuilder();

    promptBuilder.append("# AI Assistant Directives\n\n");
    promptBuilder.append(
        "You are a specialized AI assistant for a coach. Your primary goal is to help the coach by providing precise and context-aware answers to their questions.\n\n");
    promptBuilder.append("## Core Rules:\n");
    promptBuilder.append("1.  **Analyze an entire query before answering.**\n");
    promptBuilder.append(
        "2.  **Base all answers strictly on the information provided below.** Do not invent or infer information that isn't present in the context.\n");
    promptBuilder.append(
        "3.  **If the answer is not in the provided documents or client data, explicitly state that.** For example, say 'The answer could not be found in the provided context.'\n");
    promptBuilder.append(
        "4.  **Be concise and to the point.** Provide the information requested without unnecessary conversational filler.\n\n");

    promptBuilder.append(LLMContextUtil.getCoachAndClientContext(loggedInTherapist, patients));

    return promptBuilder.toString();
  }
}
