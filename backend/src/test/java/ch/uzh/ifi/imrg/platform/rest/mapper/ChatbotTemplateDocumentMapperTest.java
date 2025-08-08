package ch.uzh.ifi.imrg.platform.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateDocumentOutputDTO;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ChatbotTemplateDocumentMapperTest {

    @Test
    void convertEntity_copiesAllMappedFields() {
        ChatbotTemplateDocument source = new ChatbotTemplateDocument();
        source.setId(UUID.randomUUID().toString());
        source.setFileName("policies.pdf");
        source.setFileType("application/pdf");

        ChatbotTemplateDocumentOutputDTO dto =
                ChatbotTemplateDocumentMapper.INSTANCE
                        .convertEntityToChatbotTemplateDocumentOutputDTO(source);

        assertThat(dto.getId()).isEqualTo(source.getId());
        assertThat(dto.getFileName()).isEqualTo("policies.pdf");
        assertThat(dto.getFileType()).isEqualTo("application/pdf");
    }

    @Test
    void convertEntity_withNull_returnsNull() {
        ChatbotTemplateDocumentOutputDTO dto =
                ChatbotTemplateDocumentMapper.INSTANCE
                        .convertEntityToChatbotTemplateDocumentOutputDTO(null);

        assertThat(dto).isNull();
    }
}
