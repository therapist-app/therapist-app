package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseGoalRepository;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseGoalMapper;
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
      CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalDTO) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            createCounselingPlanPhaseGoalDTO.getCounselingPlanPhaseId());

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
      String counselingPlanPhaseId, Therapist loggedInTherapist) {

    CounselingPlanPhase phase =
        counselingPlanPhaseRepository
            .findById(counselingPlanPhaseId)
            .orElseThrow(
                () ->
                    new Error("Counseling plan phase not found with id: " + counselingPlanPhaseId));

    CounselingPlan counselingPlan = phase.getCounselingPlan();

    String systemPrompt = LLMContextUtil.getCounselingPlanContext(counselingPlan);

    List<Patient> patientList = new ArrayList<>();
    patientList.add(counselingPlan.getPatient());

    systemPrompt += LLMContextUtil.getCoachAndClientContext(loggedInTherapist, patientList);

    String userPrompt =
        String.format(
            "The user wants to add a new goal to a specific phase of the counseling plan."
                + " The target phase is named '%s' (ID: %s)."
                + " Based on the overall plan and the details of this specific phase, generate one new, relevant goal."
                + " The goal should have a concise 'goalName' and a slightly more detailed 'goalDescription'."
                + " Respond ONLY with a valid JSON object in the following format. Do not include any other text or explanations."
                + " Format: {\"goalName\":\"<name>\", \"goalDescription\":\"<description>\"}",
            phase.getPhaseName(), counselingPlanPhaseId);

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));
    CreateCounselingPlanPhaseGoalDTO generatedDto =
        LLMUZH.callLLMForObject(messages, CreateCounselingPlanPhaseGoalDTO.class);
    generatedDto.setCounselingPlanPhaseId(counselingPlanPhaseId);
    return generatedDto;
  }

  public CounselingPlanPhaseGoalOutputDTO getCounselingPlanPhaseGoalById(String id) {
    CounselingPlanPhaseGoal counselingPlanPhaseGoal =
        counselingPlanPhaseGoalRepository.getReferenceById(id);
    return CounselingPlanPhaseGoalMapper.INSTANCE.convertEntityToCounselingPlanPhaseGoalOutputDTO(
        counselingPlanPhaseGoal);
  }

  public CounselingPlanPhaseGoalOutputDTO updateCounselingPlanPhaseGoal(
      String phaseGoalId, CreateCounselingPlanPhaseGoalDTO updateDto) {
    CounselingPlanPhaseGoal counselingPlanPhaseGoal =
        counselingPlanPhaseGoalRepository.getReferenceById(phaseGoalId);
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

  public void deleteCounselingPlanPhaseGoal(String id) {
    counselingPlanPhaseGoalRepository.deleteById(id);
  }
}
