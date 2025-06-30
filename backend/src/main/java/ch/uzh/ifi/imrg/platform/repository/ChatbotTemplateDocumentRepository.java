package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotTemplateDocumentRepository
    extends JpaRepository<ChatbotTemplateDocument, String> {}
