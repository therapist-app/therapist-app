package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanExerciseAIGeneratedOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseMapper;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class CounselingPlanPhaseService {

  private final CounselingPlanRepository counselingPlanRepository;
  private final CounselingPlanPhaseRepository counselingPlanPhaseRepository;
  private final ExerciseRepository exerciseRepository;
  private final ObjectMapper objectMapper;

  public CounselingPlanPhaseService(
      @Qualifier("counselingPlanPhaseRepository")
          CounselingPlanPhaseRepository counselingPlanPhaseRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository,
      @Qualifier("counselingPlanRepository") CounselingPlanRepository counselingPlanRepository,ObjectMapper objectMapper) {
    this.counselingPlanPhaseRepository = counselingPlanPhaseRepository;
    this.counselingPlanRepository = counselingPlanRepository;
    this.exerciseRepository = exerciseRepository;
    this.objectMapper = objectMapper;
  }

  public CounselingPlanPhaseOutputDTO createCounselingPlanPhase(
      CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO) {
    CounselingPlan counselingPlan =
        counselingPlanRepository.getReferenceById(
            createCounselingPlanPhaseDTO.getCounselingPlanId());

    CounselingPlanPhase counselingPlanPhase = new CounselingPlanPhase();
    counselingPlanPhase.setPhaseName(createCounselingPlanPhaseDTO.getPhaseName());
    counselingPlanPhase.setStartDate(createCounselingPlanPhaseDTO.getStartDate());
    counselingPlanPhase.setEndDate(createCounselingPlanPhaseDTO.getEndDate());
    counselingPlanPhase.setCounselingPlan(counselingPlan);
    counselingPlanPhaseRepository.save(counselingPlanPhase);
    counselingPlanPhaseRepository.flush();
    ;
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

 public CreateCounselingPlanPhaseDTO createCounselingPlanPhaseAIGenerated(
      String counselingPlanId) {
    CounselingPlan counselingPlan =
        counselingPlanRepository
            .findById(counselingPlanId)
            .orElseThrow(
                () -> new Error("Counseling plan not found with id: " + counselingPlanId));

    String systemPrompt = CounselingPlanService.buildSystemPrompt(counselingPlan);

    String userPrompt =
        "Based on the counseling plan context provided, generate the *next* logical phase. "
            + "If there are no existing phases, create the very first one (e.g., 'Introduction and Goal Setting'). "
            + "Determine a suitable phase name, a start date, and an end date. "
            + "The start date should be today or immediately follow the end date of the previous phase. "
            + "A typical phase duration is between 1 and 4 weeks. "
            + "Respond ONLY with a valid JSON object in the following format. Do not include any other text or explanations. "
            + "Format: {\"phaseName\":\"<name>\", \"startDate\":\"<YYYY-MM-DD'T'HH:MM:SS.sssZ>\", \"endDate\":\"<YYYY-MM-DD'T'HH:MM:SS.sssZ>\"}";

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));
    CreateCounselingPlanPhaseDTO generatedDto = LLMUZH.callLLMForObject(messages, CreateCounselingPlanPhaseDTO.class);
    generatedDto.setCounselingPlanId(counselingPlanId);
    return generatedDto;
  }

  public CounselingPlanExerciseAIGeneratedOutputDTO createCounselingPlanExerciseAIGenerated(
      String counselingPlanId) {
    CounselingPlanExerciseAIGeneratedOutputDTO counselingPlanMeetingAIGeneratedOutputDTO =
        new CounselingPlanExerciseAIGeneratedOutputDTO();
    counselingPlanMeetingAIGeneratedOutputDTO.setSelectedMeetingId(
        "aef87d40-925e-457d-a344-1599c16ff374");
    // counselingPlanMeetingAIGeneratedOutputDTO.setTitle("AI Generated Plan Meeting");
    // counselingPlanMeetingAIGeneratedOutputDTO.setExerciseType(ExerciseType.JOURNALING);
    // counselingPlanMeetingAIGeneratedOutputDTO.setExerciseStart(Instant.now());
    // counselingPlanMeetingAIGeneratedOutputDTO.setExerciseEnd(Instant.now());

    return counselingPlanMeetingAIGeneratedOutputDTO;
  }

  public CounselingPlanPhaseOutputDTO addExerciseToCounselingPlanPhase(
      AddExerciseToCounselingPlanPhaseDTO addExerciseToCounselingPlanPhaseDTO) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            addExerciseToCounselingPlanPhaseDTO.getCounselingPlanPhaseId());
    Exercise exercise =
        exerciseRepository.getReferenceById(addExerciseToCounselingPlanPhaseDTO.getExerciseId());
    counselingPlanPhase.getPhaseExercises().add(exercise);
    counselingPlanPhaseRepository.save(counselingPlanPhase);
    counselingPlanPhaseRepository.flush();
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO removeExerciseFromCounselingPlanPhase(
      RemoveExerciseFromCounselingPlanPhaseDTO removeExerciseFromCounselingPlanPhaseDTO) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            removeExerciseFromCounselingPlanPhaseDTO.getCounselingPlanPhaseId());
    Exercise exercise =
        exerciseRepository.getReferenceById(
            removeExerciseFromCounselingPlanPhaseDTO.getExerciseId());
    counselingPlanPhase.getPhaseExercises().remove(exercise);
    counselingPlanPhaseRepository.save(counselingPlanPhase);
    counselingPlanPhaseRepository.flush();
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO getCounselingPlanPhaseById(String id) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(() -> new Error("Counseling plan phase not found with id: " + id));
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public void deleteCounselingPlanPhase(String id) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(() -> new Error("Counseling plan phase not found with id: " + id));
    counselingPlanPhase.getCounselingPlan().getCounselingPlanPhases().remove(counselingPlanPhase);
    counselingPlanRepository.save(counselingPlanPhase.getCounselingPlan());
    counselingPlanRepository.flush();
  }
}
