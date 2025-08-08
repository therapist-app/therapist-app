package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.*;

class LLMFactoryTest {

    @Test
    void localBranch() {
        try (MockedConstruction<LLMUZH> mc = mockConstruction(LLMUZH.class)) {
            LLM inst = LLMFactory.getInstance(LLMModel.LOCAL_UZH);
            Assertions.assertTrue(inst instanceof LLMUZH);
        }
    }
}
