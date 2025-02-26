package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("chatbotTemplateRepository")
public interface ChatbotTemplateRepository extends JpaRepository<ChatbotTemplate, String> {
    List<ChatbotTemplate> findByTherapistId(String therapistId);
    @Query("SELECT t FROM ChatbotTemplate t WHERE t.id = :templateId AND t.therapist.id = :therapistId")
    Optional<ChatbotTemplate> findByIdAndTherapistId(
            @Param("templateId") String templateId,
            @Param("therapistId") String therapistId
    );
}