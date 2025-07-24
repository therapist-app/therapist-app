package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseGoalRepository;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalAIGeneratedDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseGoalMapper;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.ExampleCounselingPlans;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounselingPlanPhaseGoalService {

  private final CounselingPlanPhaseGoalRepository counselingPlanPhaseGoalRepository;
  private final CounselingPlanPhaseRepository counselingPlanPhaseRepository;

  public CounselingPlanPhaseGoalService(
      @Qualifier("counselingPlanPhaseGoalRepository")
          CounselingPlanPhaseGoalRepository counselingPlanPhaseGoalRepository,
      @Qualifier("counselingPlanPhaseRepository")
          CounselingPlanPhaseRepository counselingPlanPhaseRepository) {
    this.counselingPlanPhaseGoalRepository = counselingPlanPhaseGoalRepository;
    this.counselingPlanPhaseRepository = counselingPlanPhaseRepository;
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
                    new Error(
                        "Counseling plan phase not found with id: "
                            + dto.getCounselingPlanPhaseId()));
    SecurityUtil.checkOwnership(phase, therapistId);

    String systemPrompt = ExampleCounselingPlans.getCounselingPlanSystemPrompt();

    String userPrompt =
        String.format(
            "The user wants to add a new goal to a specific phase of the counseling plan."
                + " The target phase is named '%s' (ID: %s)."
                + " Based on the overall plan and the details of this specific phase, generate one new, relevant goal."
                + " The goal should have a concise 'goalName' and a slightly more detailed 'goalDescription'."
                + " Respond ONLY with a valid JSON object in the following format. Do not include any other text or explanations."
                + " Format: {\"goalName\":\"<name>\", \"goalDescription\":\"<description>\"}",
            phase.getPhaseName(), dto.getCounselingPlanPhaseId());
    userPrompt +=
        "\n\n This is some additional context of the patient including the current counseling plan:\n\n"
            + phase.getCounselingPlan().getPatient().toLLMContext(0);

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));
    CreateCounselingPlanPhaseGoalDTO generatedDto =
        LLMUZH.callLLMForObject(
            messages, CreateCounselingPlanPhaseGoalDTO.class, dto.getLanguage());
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
      String phaseGoalId, CreateCounselingPlanPhaseGoalDTO updateDto, String therapistId) {
    CounselingPlanPhaseGoal counselingPlanPhaseGoal =
        counselingPlanPhaseGoalRepository.getReferenceById(phaseGoalId);
    SecurityUtil.checkOwnership(counselingPlanPhaseGoal, therapistId);
    if (updateDto.getGoalName() != null) {
      counselingPlanPhaseGoal.setGoalName(updateDto.getGoalName());
    }

    if (updateDto.getGoalDescription() != null) {
      counselingPlanPhaseGoal.setGoalName(updateDto.getGoalDescription());
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
