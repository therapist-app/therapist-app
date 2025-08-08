package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.entity.HasLLMContext;
import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

class LLMContextBuilderTest {

    private static class Sample {
        @LLMContextField(order = 2, label = "B")
        String b = "two";

        @LLMContextField(order = 1, label = "A")
        int a = 1;
    }

    private static class Entity implements HasLLMContext {
        private final String id;
        Entity(String id) { this.id = id; }
        public String getId() { return id; }
        public String toLLMContext(Integer level) { return FormatUtil.indentBlock("ctx" + id, level, false); }
    }

    @Test
    void buildAndOwnProperties() {
        try (MockedStatic<FormatUtil> st = mockStatic(FormatUtil.class)) {
            st.when(() -> FormatUtil.appendDetail(any(), anyString(), any())).thenAnswer(i -> {
                StringBuilder sb = i.getArgument(0, StringBuilder.class);
                String label = i.getArgument(1, String.class);
                Object value = i.getArgument(2);
                sb.append(label).append("=").append(String.valueOf(value)).append(";");
                return null;
            });
            st.when(() -> FormatUtil.indentBlock(anyString(), anyInt(), anyBoolean()))
                    .thenAnswer(i -> "L" + i.getArgument(1) + ":" + i.getArgument(0));

            String built = LLMContextBuilder.build(new Sample());
            Assertions.assertEquals("A=1;B=two;", built);

            String own = LLMContextBuilder.getOwnProperties(new Sample(), 2).toString();
            Assertions.assertEquals("L2:" + built, own);
        }
    }

    @Test
    void listHandling() {
        try (MockedStatic<FormatUtil> st = mockStatic(FormatUtil.class)) {
            st.when(() -> FormatUtil.indentBlock(anyString(), anyInt(), anyBoolean()))
                    .thenAnswer(i -> "L" + i.getArgument(1) + ":" + i.getArgument(0));

            StringBuilder sb = new StringBuilder();
            LLMContextBuilder.addLLMContextOfListOfEntities(sb, List.of(), "Item", 0);
            Assertions.assertEquals(0, sb.length());

            List<Entity> list = Arrays.asList(new Entity("1"), new Entity("2"));
            LLMContextBuilder.addLLMContextOfListOfEntities(sb, list, "Item", 0);
            String res = sb.toString();
            Assertions.assertTrue(res.contains("Item: 1"));
            Assertions.assertTrue(res.contains("Item: 2"));
            Assertions.assertTrue(res.contains("------------------------------------"));
            Assertions.assertTrue(res.contains("ctx1"));
            Assertions.assertTrue(res.contains("ctx2"));
        }
    }
}
