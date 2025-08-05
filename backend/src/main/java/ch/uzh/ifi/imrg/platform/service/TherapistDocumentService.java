package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.repository.TherapistDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistDocumentMapper;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.DocumentSummarizerUtil;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
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
public class TherapistDocumentService {

  private static final Logger logger = LoggerFactory.getLogger(TherapistDocumentService.class);

  private final TherapistRepository therapistRepository;
  private final TherapistDocumentRepository therapistDocumentRepository;

  public TherapistDocumentService(
      @Qualifier("therapistRepository") TherapistRepository therapistRepository,
      @Qualifier("therapistDocumentRepository")
          TherapistDocumentRepository therapistDocumentRepository) {
    this.therapistRepository = therapistRepository;
    this.therapistDocumentRepository = therapistDocumentRepository;
  }

  public void uploadTherapistDocument(MultipartFile file, String therapistId) {

    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Therapist not found"));

    String rawText = readFullText(file);
    String extractedText = rawText;

    if (rawText.length() > DocumentSummarizerUtil.MAX_SUMMARY_CHARS) {
      extractedText = DocumentSummarizerUtil.summarise(rawText, therapist, therapistRepository);
    }

    TherapistDocument therapistDocument = new TherapistDocument();
    therapistDocument.setTherapist(therapist);
    therapistDocument.setFileName(file.getOriginalFilename());
    therapistDocument.setFileType(file.getContentType());
    try {
      therapistDocument.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to read file bytes", e);
    }
    therapistDocument.setExtractedText(extractedText);

    therapistDocumentRepository.save(therapistDocument);
  }

  public List<TherapistDocumentOutputDTO> getDocumentsOfTherapist(String therapistId) {

    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Therapist not found"));

    return therapist.getTherapistDocuments().stream()
        .map(TherapistDocumentMapper.INSTANCE::convertEntityToTherapistDocumentOutputDTO)
        .collect(Collectors.toList());
  }

  public TherapistDocument downloadTherapistDocument(
      String therapistDocumentId, String therapistId) {

    TherapistDocument therapistDocument =
        therapistDocumentRepository
            .findById(therapistDocumentId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Therapist document not found"));
    SecurityUtil.checkOwnership(therapistDocument, therapistId);

    return therapistDocument;
  }

  public void deleteFile(String therapistDocumentId, String therapistId) {

    TherapistDocument therapistDocument =
        therapistDocumentRepository.getReferenceById(therapistDocumentId);
    SecurityUtil.checkOwnership(therapistDocument, therapistId);

    therapistDocument.getTherapist().getTherapistDocuments().remove(therapistDocument);
  }

  private String readFullText(MultipartFile file) {
    try (InputStream stream = file.getInputStream()) {
      AutoDetectParser parser = new AutoDetectParser();
      ContentHandler handler = new BodyContentHandler(-1);
      Metadata metadata = new Metadata();
      ParseContext context = new ParseContext();
      parser.parse(stream, handler, metadata, context);
      return handler.toString();
    } catch (Exception e) {
      logger.error("Full-text extraction failed – falling back to DocumentParserUtil", e);
      return DocumentParserUtil.extractText(file);
    }
  }
}
