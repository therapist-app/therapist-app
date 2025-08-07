package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChatMessageDTOTest {

    @Nested
    @DisplayName("equals / hashCode contract")
    class EqualsHashCode {

        @Test
        @DisplayName("reflexive – same instance")
        void equalsIsReflexive() {
            ChatMessageDTO dto = new ChatMessageDTO(ChatRole.USER, "hi");
            assertThat(dto).isEqualTo(dto);
        }

        @Test
        @DisplayName("same field values ⇒ equal & same hashCode")
        void equalWhenFieldsMatch() {
            ChatMessageDTO left  = new ChatMessageDTO(ChatRole.USER, "hi");
            ChatMessageDTO right = new ChatMessageDTO(ChatRole.USER, "hi");

            assertThat(left).isEqualTo(right);
            assertThat(left.hashCode()).isEqualTo(right.hashCode());
        }

        @Test
        @DisplayName("different enum field ⇒ not equal")
        void notEqualDifferentRole() {
            ChatMessageDTO left  = new ChatMessageDTO(ChatRole.USER,   "hi");
            ChatMessageDTO right = new ChatMessageDTO(ChatRole.SYSTEM, "hi");

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        @DisplayName("different string field ⇒ not equal")
        void notEqualDifferentContent() {
            ChatMessageDTO left  = new ChatMessageDTO(ChatRole.USER, "hi");
            ChatMessageDTO right = new ChatMessageDTO(ChatRole.USER, "bye");

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        @DisplayName("null and unrelated type ⇒ not equal")
        void notEqualNullOrOtherType() {
            ChatMessageDTO dto = new ChatMessageDTO(ChatRole.USER, "hi");

            assertThat(dto).isNotEqualTo(null);
            assertThat(dto).isNotEqualTo("something");
        }
    }

    @Test
    @DisplayName("getters / setters / toString work")
    void accessorsAndToString() {
        ChatMessageDTO dto = new ChatMessageDTO(null, null);

        dto.setChatRole(ChatRole.SYSTEM);
        dto.setContent("hello");

        assertThat(dto.getChatRole()).isEqualTo(ChatRole.SYSTEM);
        assertThat(dto.getContent()).isEqualTo("hello");
        assertThat(dto.toString())
                .contains("chatRole=SYSTEM")
                .contains("content=hello");
    }
}
