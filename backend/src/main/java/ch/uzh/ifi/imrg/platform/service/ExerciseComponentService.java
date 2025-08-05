package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.ExerciseComponentInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseComponentUpdateInputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ExerciseComponentRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.DocumentSummarizerUtil;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.ContentHandler;

@Service
@Transactional
public class ExerciseComponentService {

  private static final Logger log = LoggerFactory.getLogger(ExerciseComponentService.class);

  private final ExerciseComponentRepository exerciseComponentRepository;
  private final ExerciseRepository exerciseRepository;
  private final TherapistRepository therapistRepository;

  public ExerciseComponentService(
      @Qualifier("exerciseComponentRepository")
          ExerciseComponentRepository exerciseComponentRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.exerciseComponentRepository = exerciseComponentRepository;
    this.exerciseRepository = exerciseRepository;
    this.therapistRepository = therapistRepository;
  }

  public void createExerciseComponent(
      CreateExerciseComponentDTO createExerciseComponentDTO, String therapistId) {

    Exercise exercise =
        exerciseRepository.getReferenceById(createExerciseComponentDTO.getExerciseId());
    SecurityUtil.checkOwnership(exercise, therapistId);

    ExerciseComponent exerciseComponent = new ExerciseComponent();
    exerciseComponent.setExercise(exercise);
    exerciseComponent.setExerciseComponentType(
        createExerciseComponentDTO.getExerciseComponentType());
    exerciseComponent.setOrderNumber(exercise.getExerciseComponents().size() + 1);
    exerciseComponent.setExerciseComponentDescription(
        createExerciseComponentDTO.getExerciseComponentDescription());
    exerciseComponent.setYoutubeUrl(createExerciseComponentDTO.getYoutubeUrl());

    exerciseComponent = exerciseComponentRepository.save(exerciseComponent);

    ExerciseComponentInputDTOPatientAPI exerciseComponentInputDTOPatientAPI =
        new ExerciseComponentInputDTOPatientAPI()
            .id(exerciseComponent.getId())
            .exerciseComponentType(
                ExerciseComponentInputDTOPatientAPI.ExerciseComponentTypeEnum.fromValue(
                    exerciseComponent.getExerciseComponentType().toString()))
            .exerciseComponentDescription(exerciseComponent.getExerciseComponentDescription())
            .orderNumber(exerciseComponent.getOrderNumber())
            .youtubeUrl(exerciseComponent.getYoutubeUrl());

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .createExerciseComponent(
            exercise.getPatient().getId(),
            createExerciseComponentDTO.getExerciseId(),
            exerciseComponentInputDTOPatientAPI)
        .block();
  }

  public void createExerciseComponentWithFile(
      CreateExerciseComponentDTO dto, MultipartFile file, String therapistId) {

    Exercise exercise = exerciseRepository.getReferenceById(dto.getExerciseId());
    SecurityUtil.checkOwnership(exercise, therapistId);

    String rawText = readFullText(file);

    boolean needsSummary = rawText.length() > DocumentSummarizerUtil.MAX_SUMMARY_CHARS;

    String extractedText =
        needsSummary ? rawText.substring(0, DocumentSummarizerUtil.MAX_SUMMARY_CHARS) : rawText;

    ExerciseComponent ec = new ExerciseComponent();
    ec.setExercise(exercise);
    ec.setExerciseComponentType(dto.getExerciseComponentType());
    ec.setFileName(file.getOriginalFilename());
    ec.setFileType(file.getContentType());
    ec.setExtractedText(extractedText);
    ec.setOrderNumber(exercise.getExerciseComponents().size() + 1);
    ec.setExerciseComponentDescription(dto.getExerciseComponentDescription());

    try {
      ec.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to read file bytes", e);
    }

    ec = exerciseComponentRepository.save(ec);

    ExerciseComponentInputDTOPatientAPI api =
        new ExerciseComponentInputDTOPatientAPI()
            .id(ec.getId())
            .exerciseComponentDescription(ec.getExerciseComponentDescription())
            .exerciseComponentType(
                ExerciseComponentInputDTOPatientAPI.ExerciseComponentTypeEnum.fromValue(
                    ec.getExerciseComponentType().toString()))
            .fileData(ec.getFileData())
            .fileName(ec.getFileName())
            .fileType(ec.getFileType())
            .orderNumber(ec.getOrderNumber());

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .createExerciseComponent(exercise.getPatient().getId(), dto.getExerciseId(), api)
        .block();

    if (needsSummary) {
      final String raw = rawText;
      final String ecId = ec.getId();
      final Therapist therapist = exercise.getPatient().getTherapist();

      CompletableFuture.runAsync(
          () -> {
            try {
              String summary =
                  DocumentSummarizerUtil.summarise(raw, therapist, therapistRepository);

              ExerciseComponent update = exerciseComponentRepository.findById(ecId).orElse(null);
              if (update != null) {
                update.setExtractedText(summary);
                exerciseComponentRepository.save(update);
              }
            } catch (Exception ex) {
              log.error("Asynchronous LLM summarisation failed for ExerciseComponent {}", ecId, ex);
            }
          });
    }
  }

  public ExerciseComponent getExerciseComponent(String id, String therapistId) {
    ExerciseComponent ec = exerciseComponentRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(ec, therapistId);
    return ec;
  }

  public ExerciseComponent downloadExerciseComponent(String id, String therapistId) {
    ExerciseComponent ec = exerciseComponentRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(ec, therapistId);
    ec.getFileData();
    return ec;
  }

  public void updateExerciseComponent(UpdateExerciseComponentDTO dto, String therapistId) {
    ExerciseComponent target = exerciseComponentRepository.getReferenceById(dto.getId());
    SecurityUtil.checkOwnership(target, therapistId);
    Exercise exercise = target.getExercise();
    SecurityUtil.checkOwnership(exercise, therapistId);
    String patientId = exercise.getPatient().getId();

    ExerciseComponentUpdateInputDTOPatientAPI apiUpdate =
        new ExerciseComponentUpdateInputDTOPatientAPI().id(dto.getId());

    if (dto.getExerciseComponentDescription() != null) {
      target.setExerciseComponentDescription(dto.getExerciseComponentDescription());
      apiUpdate.exerciseComponentDescription(dto.getExerciseComponentDescription());
    }

    if (dto.getYoutubeUrl() != null) {
      target.setYoutubeUrl(dto.getYoutubeUrl());
      apiUpdate.youtubeUrl(dto.getYoutubeUrl());
    }

    if (dto.getOrderNumber() != null) {
      int oldOrder = target.getOrderNumber();
      int newOrder =
          Math.max(1, Math.min(dto.getOrderNumber(), exercise.getExerciseComponents().size()));

      if (newOrder != oldOrder) {
        if (newOrder < oldOrder) {
          for (ExerciseComponent ec : exercise.getExerciseComponents()) {
            int ord = ec.getOrderNumber();
            if (ord >= newOrder && ord < oldOrder) {
              ec.setOrderNumber(ord + 1);
              exerciseComponentRepository.save(ec);

              ExerciseComponentUpdateInputDTOPatientAPI up =
                  new ExerciseComponentUpdateInputDTOPatientAPI()
                      .id(ec.getId())
                      .orderNumber(ec.getOrderNumber());
              PatientAppAPIs.coachExerciseControllerPatientAPI
                  .updateExerciseComponent(patientId, exercise.getId(), ec.getId(), up)
                  .block();
            }
          }
        } else {
          for (ExerciseComponent ec : exercise.getExerciseComponents()) {
            int ord = ec.getOrderNumber();
            if (ord <= newOrder && ord > oldOrder) {
              ec.setOrderNumber(ord - 1);
              exerciseComponentRepository.save(ec);

              ExerciseComponentUpdateInputDTOPatientAPI up =
                  new ExerciseComponentUpdateInputDTOPatientAPI()
                      .id(ec.getId())
                      .orderNumber(ec.getOrderNumber());
              PatientAppAPIs.coachExerciseControllerPatientAPI
                  .updateExerciseComponent(patientId, exercise.getId(), ec.getId(), up)
                  .block();
            }
          }
        }
        target.setOrderNumber(newOrder);
      }
    }

    apiUpdate.orderNumber(target.getOrderNumber());

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .updateExerciseComponent(patientId, exercise.getId(), target.getId(), apiUpdate)
        .block();

    exerciseComponentRepository.save(target);
  }

  public void deleteExerciseComponent(String id, String therapistId) {

    ExerciseComponent component =
        exerciseComponentRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Exercise component " + id + " not found"));

    SecurityUtil.checkOwnership(component, therapistId);

    Exercise exercise = component.getExercise();
    String patientId = exercise.getPatient().getId();
    int removedNo = component.getOrderNumber();

    for (ExerciseComponent ec : exercise.getExerciseComponents()) {
      if (ec.getOrderNumber() > removedNo) {
        ec.setOrderNumber(ec.getOrderNumber() - 1);
        exerciseComponentRepository.save(ec);

        ExerciseComponentUpdateInputDTOPatientAPI up =
            new ExerciseComponentUpdateInputDTOPatientAPI()
                .id(ec.getId())
                .orderNumber(ec.getOrderNumber());

        PatientAppAPIs.coachExerciseControllerPatientAPI
            .updateExerciseComponent(patientId, exercise.getId(), ec.getId(), up)
            .block();
      }
    }

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .deleteExerciseComponent(patientId, exercise.getId(), id)
        .block();

    exercise.getExerciseComponents().remove(component);
    exerciseComponentRepository.delete(component);
    exerciseRepository.save(exercise);
    exerciseComponentRepository.flush();
  }

  private String readFullText(MultipartFile file) {
    try (InputStream in = file.getInputStream()) {
      AutoDetectParser parser = new AutoDetectParser();
      ContentHandler handler = new BodyContentHandler(-1);
      Metadata meta = new Metadata();
      parser.parse(in, handler, meta, new ParseContext());
      return handler.toString();
    } catch (Exception e) {
      log.error("Tika full-text extraction failed – falling back to PDFBox", e);
      return DocumentParserUtil.extractText(file);
    }
  }
}
