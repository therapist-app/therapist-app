package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseMapper;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.DateUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMContextUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounselingPlanPhaseService {

  private final CounselingPlanRepository counselingPlanRepository;
  private final CounselingPlanPhaseRepository counselingPlanPhaseRepository;
  private final ExerciseRepository exerciseRepository;

  public CounselingPlanPhaseService(
      @Qualifier("counselingPlanPhaseRepository")
          CounselingPlanPhaseRepository counselingPlanPhaseRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository,
      @Qualifier("counselingPlanRepository") CounselingPlanRepository counselingPlanRepository) {
    this.counselingPlanPhaseRepository = counselingPlanPhaseRepository;
    this.counselingPlanRepository = counselingPlanRepository;
    this.exerciseRepository = exerciseRepository;
  }

  public CounselingPlanPhaseOutputDTO createCounselingPlanPhase(
      CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO) {
    List<CounselingPlanPhase> allCounselingPlanPhases = counselingPlanPhaseRepository.findAll();
    CounselingPlan counselingPlan =
        counselingPlanRepository.getReferenceById(
            createCounselingPlanPhaseDTO.getCounselingPlanId());

    CounselingPlanPhase counselingPlanPhase = new CounselingPlanPhase();
    counselingPlanPhase.setPhaseName(createCounselingPlanPhaseDTO.getPhaseName());
    counselingPlanPhase.setDurationInWeeks(createCounselingPlanPhaseDTO.getDurationInWeeks());
    counselingPlanPhase.setPhaseNumber(allCounselingPlanPhases.size() + 1);

    counselingPlanPhase.setCounselingPlan(counselingPlan);
    counselingPlanPhaseRepository.save(counselingPlanPhase);

    return getOutputDto(counselingPlanPhase, counselingPlan);
  }

  public CreateCounselingPlanPhaseDTO createCounselingPlanPhaseAIGenerated(
      String counselingPlanId, Therapist loggedInTherapist) {
    CounselingPlan counselingPlan =
        counselingPlanRepository
            .findById(counselingPlanId)
            .orElseThrow(() -> new Error("Counseling plan not found with id: " + counselingPlanId));

    String systemPrompt = LLMContextUtil.getCounselingPlanContext(counselingPlan);

    List<Patient> patientList = new ArrayList<>();
    patientList.add(counselingPlan.getPatient());

    systemPrompt += LLMContextUtil.getCoachAndClientContext(loggedInTherapist, patientList);

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
        LLMUZH.callLLMForObject(messages, CreateCounselingPlanPhaseDTO.class);
    generatedDto.setCounselingPlanId(counselingPlanId);
    return generatedDto;
  }

  public CreateExerciseDTO createCounselingPlanExerciseAIGenerated(
      String counselingPlanPhaseId, Therapist loggedInTherapist) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(counselingPlanPhaseId)
            .orElseThrow(
                () ->
                    new Error("Counseling plan phase not found with id: " + counselingPlanPhaseId));

    CounselingPlan counselingPlan = counselingPlanPhase.getCounselingPlan();

    String systemPrompt = LLMContextUtil.getCounselingPlanContext(counselingPlan);

    List<Patient> patientList = new ArrayList<>();
    patientList.add(counselingPlan.getPatient());

    systemPrompt += LLMContextUtil.getCoachAndClientContext(loggedInTherapist, patientList);

    String validExerciseTypes =
        Arrays.stream(ExerciseType.values())
            .map(Enum::name)
            .collect(Collectors.joining("', '", "'", "'"));

    String userPrompt =
        "Based on the counseling plan provided, generate one new, relevant exercise that would be a good next step for the patient. "
            + "The exercise should have a title and a type. "
            + "The 'exerciseType' MUST be one of the following values: "
            + validExerciseTypes
            + ". "
            + "Respond ONLY with a valid JSON object in the following format. Do not include any other text or explanations. "
            + "Format: {\"title\":\"<title>\", \"exerciseType\":\"<TYPE>\"";

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));

    CreateExerciseDTO generatedDto = LLMUZH.callLLMForObject(messages, CreateExerciseDTO.class);
    CounselingPlanPhaseOutputDTO counselingPlanPhaseOutputDTO =
        getOutputDto(counselingPlanPhase, counselingPlan);
    generatedDto.setExerciseStart(counselingPlanPhaseOutputDTO.getStartDate());
    generatedDto.setDurationInWeeks(counselingPlanPhaseOutputDTO.getDurationInWeeks());

    generatedDto.setPatientId(counselingPlan.getPatient().getId());

    return generatedDto;
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

    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO getCounselingPlanPhaseById(String id) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(() -> new Error("Counseling plan phase not found with id: " + id));

    return getOutputDto(counselingPlanPhase, counselingPlanPhase.getCounselingPlan());
  }

  public CounselingPlanPhaseOutputDTO updateCounselingPlanPhase(
      String counselingPlanPhaseId, CreateCounselingPlanPhaseDTO updateDto) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(counselingPlanPhaseId)
            .orElseThrow(
                () ->
                    new Error("Counseling plan phase not found with id: " + counselingPlanPhaseId));

    if (updateDto.getPhaseName() != null) {
      counselingPlanPhase.setPhaseName(updateDto.getPhaseName());
    }

    counselingPlanPhase.setDurationInWeeks(updateDto.getDurationInWeeks());
    counselingPlanPhaseRepository.save(counselingPlanPhase);

    return getOutputDto(counselingPlanPhase, counselingPlanPhase.getCounselingPlan());
  }

  public void deleteCounselingPlanPhase(String id) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(() -> new Error("Counseling plan phase not found with id: " + id));
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
