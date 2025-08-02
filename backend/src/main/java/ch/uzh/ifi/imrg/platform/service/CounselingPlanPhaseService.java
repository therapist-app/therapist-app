package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
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
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseMapper;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.DateUtil;
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
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Counseling plan not found with id: " + dto.getCounselingPlanId()));
    SecurityUtil.checkOwnership(counselingPlan, therapistId);
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    String patientContext = counselingPlan.getPatient().toLLMContext(0);

    StringBuilder sb = new StringBuilder();
    sb.append("You are a specialized API endpoint for a mental health application. ");
    sb.append(
        "Your function is to determine the next logical phase of a counseling plan and respond with a single, raw, valid JSON object.\n\n");

    sb.append("--- CRITICAL JSON OUTPUT RULES ---\n");
    sb.append("1. Your entire response MUST be a single JSON object.\n");
    sb.append("2. NEVER include markdown like ```json.\n");
    sb.append("3. NEVER include any explanatory text, conversational filler, or apologies.\n\n");

    sb.append("--- REFERENCE EXAMPLES OF FULL PLANS ---\n");
    sb.append(
        "Use these high-quality counseling plans as a reference for typical phase progressions (e.g., Assessment -> Interventions -> Relapse Prevention).\n\n");
    sb.append(ExampleCounselingPlans.getExampleCounselingPlans());
    sb.append("\n\n");

    sb.append("--- CURRENT PATIENT CONTEXT ---\n");
    sb.append(
        "The following is the specific context for the patient you are generating a phase for. You must use this information to ensure the phase is relevant and personalized.\n\n");
    sb.append(patientContext);

    String userPrompt =
        "TASK: Based on all the provided context, generate the *next logical counseling phase* for the patient. "
            + "If the plan has no phases yet, generate the first one.\n\n"
            + "INSTRUCTIONS:\n"
            + "1. The 'phaseName' should NOT include a number or the word 'Phase'. (e.g., CORRECT: 'Relapse Prevention', INCORRECT: 'Phase 3: Relapse Prevention').\n"
            + "2. The 'durationInWeeks' must be an integer, typically between 2 and 10.\n"
            + "3. Remember the critical rules. Respond ONLY with a valid JSON object in the following format. Do not include any other text.\n"
            + "Format: {\"phaseName\":\"<name>\", \"durationInWeeks\":<numberOfWeeks>}";

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, sb.toString()));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));

    CreateCounselingPlanPhaseDTO generatedDto =
        LLMFactory.getInstance(therapist.getLlmModel())
            .callLLMForObject(messages, CreateCounselingPlanPhaseDTO.class, dto.getLanguage());

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
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Counseling plan phase not found with id: "
                            + dto.getCounselingPlanPhaseId()));
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    CounselingPlan counselingPlan = counselingPlanPhase.getCounselingPlan();
    String patientContext = counselingPlan.getPatient().toLLMContext(0);

    StringBuilder sb = new StringBuilder();
    sb.append("You are a specialized API endpoint for a mental health application. ");
    sb.append(
        "Your function is to create a relevant counseling exercise and respond with a single, raw, valid JSON object.\n\n");

    sb.append("--- CRITICAL JSON OUTPUT RULES ---\n");
    sb.append("1. Your entire response MUST be a single JSON object.\n");
    sb.append("2. NEVER include markdown like ```json.\n");
    sb.append("3. NEVER include any explanatory text, conversational filler, or apologies.\n\n");

    sb.append("--- REFERENCE EXAMPLES OF FULL PLANS & EXERCISES ---\n");
    sb.append(
        "Use these high-quality counseling plans as a reference for the tone, structure, and type of exercises to generate for different phases and disorders.\n\n");
    sb.append(ExampleCounselingPlans.getExampleCounselingPlans());
    sb.append("\n\n");

    sb.append("--- CURRENT PATIENT CONTEXT ---\n");
    sb.append(
        "The following is the specific context for the patient you are generating an exercise for. You must use this information to ensure the exercise is relevant and personalized.\n\n");
    sb.append(patientContext);

    String userPrompt =
        String.format(
            "TASK: Based on all the provided context, generate one new, relevant exercise for the counseling plan phase named '%s' (ID: %s).\n\n"
                + "EXAMPLE OF A PERFECT RESPONSE:\n"
                + "{\n"
                + "  \"exerciseTitle\": \"Scheduled Worry Time\",\n"
                + "  \"exerciseDescription\": \"Designate a specific 15-minute period each day (e.g., 5:00 PM - 5:15 PM) as your 'Worry Time'. When worries pop up outside this time, jot them down and postpone engaging with them until your scheduled time.\",\n"
                + "  \"exerciseExplanation\": \"This helps contain worry to a specific period, preventing it from dominating your entire day. It teaches you that you have control over when you engage with worry.\",\n"
                + "  \"doEveryNDays\": 1\n"
                + "}\n\n"
                + "INSTRUCTIONS: Remember the critical rules. The 'doEveryNDays' value must be an integer. Respond ONLY with a valid JSON object in the following format. Do not include any other text.\n"
                + "Format: {\"exerciseTitle\":\"<title>\", \"exerciseDescription\":\"<description>\", \"exerciseExplanation\":\"<explanation>\", \"doEveryNDays\":<integer_value>}",
            counselingPlanPhase.getPhaseName(), counselingPlanPhase.getId());

    List<ChatMessageDTO> messages = new ArrayList<>();
    messages.add(new ChatMessageDTO(ChatRole.SYSTEM, sb.toString()));
    messages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));

    CreateExerciseDTO generatedDto =
        LLMFactory.getInstance(therapist.getLlmModel())
            .callLLMForObject(messages, CreateExerciseDTO.class, dto.getLanguage());

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
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Counseling plan phase not found with id: " + id));
    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);

    return getOutputDto(counselingPlanPhase, counselingPlanPhase.getCounselingPlan());
  }

  public CounselingPlanPhaseOutputDTO updateCounselingPlanPhase(
      UpdateCounselingPlanPhaseDTO dto, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(dto.getCounselingPlanPhaseId())
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Counseling plan phase not found with id: "
                            + dto.getCounselingPlanPhaseId()));

    SecurityUtil.checkOwnership(counselingPlanPhase, therapistId);

    if (dto.getPhaseName() != null) {
      counselingPlanPhase.setPhaseName(dto.getPhaseName());
    }

    if (dto.getDurationInWeeks() != null) {
      counselingPlanPhase.setDurationInWeeks(dto.getDurationInWeeks());
    }

    counselingPlanPhaseRepository.save(counselingPlanPhase);

    return getOutputDto(counselingPlanPhase, counselingPlanPhase.getCounselingPlan());
  }

  public void deleteCounselingPlanPhase(String id, String therapistId) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Counseling plan phase not found with id: " + id));
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
