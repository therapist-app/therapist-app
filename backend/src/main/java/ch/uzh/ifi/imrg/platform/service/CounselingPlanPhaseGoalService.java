package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseGoalRepository;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalAIGeneratedDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseGoalMapper;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.ExampleCounselingPlans;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class CounselingPlanPhaseGoalService {

  private final CounselingPlanPhaseGoalRepository counselingPlanPhaseGoalRepository;
  private final CounselingPlanPhaseRepository counselingPlanPhaseRepository;
  private final TherapistRepository therapistRepository;

  public CounselingPlanPhaseGoalService(
      @Qualifier("counselingPlanPhaseGoalRepository")
          CounselingPlanPhaseGoalRepository counselingPlanPhaseGoalRepository,
      @Qualifier("counselingPlanPhaseRepository")
          CounselingPlanPhaseRepository counselingPlanPhaseRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.counselingPlanPhaseGoalRepository = counselingPlanPhaseGoalRepository;
    this.counselingPlanPhaseRepository = counselingPlanPhaseRepository;
    this.therapistRepository = therapistRepository;
  }

  public CounselingPlanPhaseGoalOutputDTO createCounselingPlanPhaseGoal(
      CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalDTO, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            createCounselingPlanPhaseGoalDTO.getCounselingPlanPhaseId());
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);

    CounselingPlanPhaseGoal counselingPlanPhaseGoal = new CounselingPlanPhaseGoal();
    counselingPlanPhaseGoal.setCounselingPlanPhase(counselingPlanPhase);
    counselingPlanPhaseGoal.setGoalName(createCounselingPlanPhaseGoalDTO.getGoalName());
    counselingPlanPhaseGoal.setGoalDescription(
        createCounselingPlanPhaseGoalDTO.getGoalDescription());
    counselingPlanPhaseGoal.setIsCompleted(false);

    counselingPlanPhaseGoalRepository.save(counselingPlanPhaseGoal);
    counselingPlanPhaseGoalRepository.flush();
    return CounselingPlanPhaseGoalMapper.INSTANCE.convertEntityToCounselingPlanPhaseGoalOutputDTO(
        counselingPlanPhaseGoal);
  }

  public CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalAIGenerated(
      CreateCounselingPlanPhaseGoalAIGeneratedDTO dto, String therapistId) {

    CounselingPlanPhase phase =
        counselingPlanPhaseRepository
            .findById(dto.getCounselingPlanPhaseId())
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Counseling plan phase not found with id: "
                            + dto.getCounselingPlanPhaseId()));
    SecurityUtil.checkOwnership(phase, therapistId);
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    String patientContext = phase.getCounselingPlan().getPatient().toLLMContext(0);

    StringBuilder sb = new StringBuilder();

    sb.append("You are a specialized API endpoint for a mental health application. ");
    sb.append(
        "Your ONLY function is to generate relevant content and return it as a single, raw, valid JSON object.\n\n");
    sb.append("--- CRITICAL JSON OUTPUT RULES ---\n");
    sb.append("1. Your entire response MUST be a single JSON object.\n");
    sb.append("2. NEVER include markdown formatting like ```json.\n");
    sb.append("3. NEVER include any explanatory text, conversational filler, or apologies.\n\n");

    sb.append("--- REFERENCE EXAMPLES ---\n");
    sb.append(
        "Use these high-quality counseling plans as a reference for the tone, structure, and type of content to generate. These examples demonstrate best practices.\n\n");
    sb.append(ExampleCounselingPlans.getExampleCounselingPlans());
    sb.append("\n\n");

    sb.append("--- CURRENT PATIENT CONTEXT ---\n");
    sb.append(
        "The following is the specific context for the patient you are generating a new goal for. You must use this information to ensure the goal is relevant and personalized.\n\n");
    sb.append(patientContext);

    String userPrompt =
        String.format(
            "TASK: Based on all the provided context (reference examples and the current patient's plan), "
                + "generate one new, relevant goal for the counseling plan phase named '%s' with ID '%s'.\n\n"
                + "INSTRUCTIONS: Remember the critical rules. Respond ONLY with a valid JSON object in the "
                + "following format. Do not include any other text.\n"
                + "Format: {\"goalName\":\"<name>\", \"goalDescription\":\"<description>\"}",
            phase.getPhaseName(), phase.getId());

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, sb.toString()));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));
    CreateCounselingPlanPhaseGoalDTO generatedDto =
        LLMFactory.getInstance(therapist.getLlmModel())
            .callLLMForObject(messages, CreateCounselingPlanPhaseGoalDTO.class, dto.getLanguage());
    generatedDto.setCounselingPlanPhaseId(dto.getCounselingPlanPhaseId());
    return generatedDto;
  }

  public CounselingPlanPhaseGoalOutputDTO getCounselingPlanPhaseGoalById(
      String id, String therapistId) {
    CounselingPlanPhaseGoal counselingPlanPhaseGoal =
        counselingPlanPhaseGoalRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(counselingPlanPhaseGoal, therapistId);
    return CounselingPlanPhaseGoalMapper.INSTANCE.convertEntityToCounselingPlanPhaseGoalOutputDTO(
        counselingPlanPhaseGoal);
  }

  public CounselingPlanPhaseGoalOutputDTO updateCounselingPlanPhaseGoal(
      UpdateCounselingPlanPhaseGoalDTO dto, String therapistId) {
    CounselingPlanPhaseGoal counselingPlanPhaseGoal =
        counselingPlanPhaseGoalRepository.getReferenceById(dto.getCounselingPlanPhaseGoalId());
    SecurityUtil.checkOwnership(counselingPlanPhaseGoal, therapistId);
    if (dto.getGoalName() != null) {
      counselingPlanPhaseGoal.setGoalName(dto.getGoalName());
    }

    if (dto.getGoalDescription() != null) {
      counselingPlanPhaseGoal.setGoalDescription(dto.getGoalDescription());
    }

    if (dto.getIsCompleted() != null) {
      counselingPlanPhaseGoal.setIsCompleted(dto.getIsCompleted());
    }

    counselingPlanPhaseGoalRepository.save(counselingPlanPhaseGoal);

    return CounselingPlanPhaseGoalMapper.INSTANCE.convertEntityToCounselingPlanPhaseGoalOutputDTO(
        counselingPlanPhaseGoal);
  }

  public void deleteCounselingPlanPhaseGoal(String id, String therapistId) {
    CounselingPlanPhaseGoal counselingPlanPhaseGoal =
        counselingPlanPhaseGoalRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(counselingPlanPhaseGoal, therapistId);
    counselingPlanPhaseGoal
        .getCounselingPlanPhase()
        .getPhaseGoals()
        .remove(counselingPlanPhaseGoal);
  }
}
