package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanExerciseAIGeneratedDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseAIGeneratedDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseMapper;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.DateUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMContextUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounselingPlanPhaseService {

  private final CounselingPlanRepository counselingPlanRepository;
  private final CounselingPlanPhaseRepository counselingPlanPhaseRepository;
  private final ExerciseRepository exerciseRepository;
  private final TherapistRepository therapistRepository;

  public CounselingPlanPhaseService(
      @Qualifier("counselingPlanPhaseRepository")
          CounselingPlanPhaseRepository counselingPlanPhaseRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository,
      @Qualifier("counselingPlanRepository") CounselingPlanRepository counselingPlanRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.counselingPlanPhaseRepository = counselingPlanPhaseRepository;
    this.counselingPlanRepository = counselingPlanRepository;
    this.exerciseRepository = exerciseRepository;
    this.therapistRepository = therapistRepository;
  }

  public CounselingPlanPhaseOutputDTO createCounselingPlanPhase(
      CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO, String therapistId) {

    CounselingPlan counselingPlan =
        counselingPlanRepository.getReferenceById(
            createCounselingPlanPhaseDTO.getCounselingPlanId());
    SecurityUtil.checkOwnership(counselingPlan, therapistId);

    CounselingPlanPhase counselingPlanPhase = new CounselingPlanPhase();
    counselingPlanPhase.setPhaseName(createCounselingPlanPhaseDTO.getPhaseName());
    counselingPlanPhase.setDurationInWeeks(createCounselingPlanPhaseDTO.getDurationInWeeks());
    counselingPlanPhase.setPhaseNumber(counselingPlan.getCounselingPlanPhases().size() + 1);

    counselingPlanPhase.setCounselingPlan(counselingPlan);
    counselingPlanPhaseRepository.save(counselingPlanPhase);

    return getOutputDto(counselingPlanPhase, counselingPlan);
  }

  public CreateCounselingPlanPhaseDTO createCounselingPlanPhaseAIGenerated(
      CreateCounselingPlanPhaseAIGeneratedDTO dto, String therapistId) {
    CounselingPlan counselingPlan =
        counselingPlanRepository
            .findById(dto.getCounselingPlanId())
            .orElseThrow(
                () -> new Error("Counseling plan not found with id: " + dto.getCounselingPlanId()));
    SecurityUtil.checkOwnership(counselingPlan, therapistId);

    Therapist therapist = therapistRepository.getReferenceById(therapistId);

    String systemPrompt = LLMContextUtil.getCounselingPlanContext(counselingPlan);

    List<Patient> patientList = new ArrayList<>();
    patientList.add(counselingPlan.getPatient());

    systemPrompt += LLMContextUtil.getCoachAndClientContext(therapist, patientList);

    String userPrompt =
        "Based on the counseling plan context provided, generate the *next* phase. "
            + "If there are no existing phases, create the very first one (e.g., 'Introduction and Goal Setting'). "
            + "Determine a suitable phase name, and a duration in weeks. "
            + "A typical phase duration is between 1 and 4 weeks. "
            + "Respond ONLY with a valid JSON object in the following format. Do not include any other text or explanations. "
            + "Format: {\"phaseName\":\"<name>\", \"durationInWeeks\":<numberOfWeeks>}";

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));
    CreateCounselingPlanPhaseDTO generatedDto =
        LLMUZH.callLLMForObject(messages, CreateCounselingPlanPhaseDTO.class, dto.getLanguage());
    generatedDto.setCounselingPlanId(dto.getCounselingPlanId());
    return generatedDto;
  }

  public CreateExerciseDTO createCounselingPlanExerciseAIGenerated(
      CreateCounselingPlanExerciseAIGeneratedDTO dto, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(dto.getCounselingPlanPhaseId())
            .orElseThrow(
                () ->
                    new Error(
                        "Counseling plan phase not found with id: "
                            + dto.getCounselingPlanPhaseId()));
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);
    Therapist therapist = therapistRepository.getReferenceById(therapistId);

    CounselingPlan counselingPlan = counselingPlanPhase.getCounselingPlan();

    String systemPrompt = LLMContextUtil.getCounselingPlanContext(counselingPlan);

    List<Patient> patientList = new ArrayList<>();
    patientList.add(counselingPlan.getPatient());

    systemPrompt += LLMContextUtil.getCoachAndClientContext(therapist, patientList);

    String userPrompt =
        "Based on the counseling plan provided, generate one new, relevant exercise that would be a good next step for the patient. "
            + "The exercise should have a title, a description and an explanation (which will is used to provide additional context to an AI model).\n"
            + "Additionally, your response should in include how often it should be done, e.g. every other day: doEveryNDays=2"
            + "Respond ONLY with a valid JSON object in the following format. Do not include any other text or explanations. "
            + " Format: {\"exerciseTitle\":\"<title>\", \"exerciseDescription\":\"<description>\", \"exerciseExplanation\":\"<explanation>\", \"doEveryNDays\":\"<doEveryNDays>\"}";

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));

    CreateExerciseDTO generatedDto =
        LLMUZH.callLLMForObject(messages, CreateExerciseDTO.class, dto.getLanguage());
    CounselingPlanPhaseOutputDTO counselingPlanPhaseOutputDTO =
        getOutputDto(counselingPlanPhase, counselingPlan);
    generatedDto.setExerciseStart(counselingPlanPhaseOutputDTO.getStartDate());
    generatedDto.setDurationInWeeks(counselingPlanPhaseOutputDTO.getDurationInWeeks());

    generatedDto.setPatientId(counselingPlan.getPatient().getId());

    return generatedDto;
  }

  public CounselingPlanPhaseOutputDTO addExerciseToCounselingPlanPhase(
      AddExerciseToCounselingPlanPhaseDTO addExerciseToCounselingPlanPhaseDTO, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            addExerciseToCounselingPlanPhaseDTO.getCounselingPlanPhaseId());
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);
    Exercise exercise =
        exerciseRepository.getReferenceById(addExerciseToCounselingPlanPhaseDTO.getExerciseId());
    SecurityUtil.checkOwnership(exercise, therapistId);

    counselingPlanPhase.getPhaseExercises().add(exercise);
    counselingPlanPhaseRepository.save(counselingPlanPhase);

    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO removeExerciseFromCounselingPlanPhase(
      RemoveExerciseFromCounselingPlanPhaseDTO removeExerciseFromCounselingPlanPhaseDTO,
      String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            removeExerciseFromCounselingPlanPhaseDTO.getCounselingPlanPhaseId());
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);
    Exercise exercise =
        exerciseRepository.getReferenceById(
            removeExerciseFromCounselingPlanPhaseDTO.getExerciseId());
    SecurityUtil.checkOwnership(exercise, therapistId);
    counselingPlanPhase.getPhaseExercises().remove(exercise);
    counselingPlanPhaseRepository.save(counselingPlanPhase);

    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO getCounselingPlanPhaseById(String id, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(() -> new Error("Counseling plan phase not found with id: " + id));
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);

    return getOutputDto(counselingPlanPhase, counselingPlanPhase.getCounselingPlan());
  }

  public CounselingPlanPhaseOutputDTO updateCounselingPlanPhase(
      String counselingPlanPhaseId, CreateCounselingPlanPhaseDTO updateDto, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(counselingPlanPhaseId)
            .orElseThrow(
                () ->
                    new Error("Counseling plan phase not found with id: " + counselingPlanPhaseId));

    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);
    if (updateDto.getPhaseName() != null) {
      counselingPlanPhase.setPhaseName(updateDto.getPhaseName());
    }

    counselingPlanPhase.setDurationInWeeks(updateDto.getDurationInWeeks());
    counselingPlanPhaseRepository.save(counselingPlanPhase);

    return getOutputDto(counselingPlanPhase, counselingPlanPhase.getCounselingPlan());
  }

  public void deleteCounselingPlanPhase(String id, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(() -> new Error("Counseling plan phase not found with id: " + id));
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);
    counselingPlanPhase.getCounselingPlan().getCounselingPlanPhases().remove(counselingPlanPhase);
    counselingPlanRepository.save(counselingPlanPhase.getCounselingPlan());
  }

  public static CounselingPlanPhaseOutputDTO getOutputDto(
      CounselingPlanPhase currentPhase, CounselingPlan counselingPlan) {
    CounselingPlanPhaseOutputDTO outputDTO =
        CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
            currentPhase);

    int totalAmountOfWeeksSinceStart = 0;
    for (CounselingPlanPhase phase : counselingPlan.getCounselingPlanPhases()) {
      if (phase.getPhaseNumber() < currentPhase.getPhaseNumber()) {
        totalAmountOfWeeksSinceStart += phase.getDurationInWeeks();
      }
    }

    outputDTO.setStartDate(
        DateUtil.addAmountOfWeeks(
            counselingPlan.getStartOfTherapy(), totalAmountOfWeeksSinceStart));
    outputDTO.setEndDate(
        DateUtil.addAmountOfWeeks(outputDTO.getStartDate(), currentPhase.getDurationInWeeks()));

    return outputDTO;
  }
}
