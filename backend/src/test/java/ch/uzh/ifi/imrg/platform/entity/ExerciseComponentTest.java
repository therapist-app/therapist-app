package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExerciseComponentTest {

    @Test
    void fullCoverage() {
        ExerciseComponent component = new ExerciseComponent();
        Exercise exercise = mock(Exercise.class, RETURNS_DEEP_STUBS);
        when(exercise.getPatient().getTherapist().getId()).thenReturn("tid");
        component.setExercise(exercise);
        component.setExerciseComponentType(ExerciseComponentType.YOUTUBE_VIDEO);
        component.setExerciseComponentDescription("desc");
        component.setYoutubeUrl("url");
        component.setFileName("name");
        component.setFileType("mp4");
        component.setFileData(new byte[]{1, 2});
        component.setExtractedText("txt");
        component.setOrderNumber(5);

        assertEquals("tid", component.getOwningTherapistId());

        try (MockedStatic<LLMContextBuilder> mocked = Mockito.mockStatic(LLMContextBuilder.class)) {
            mocked.when(() -> LLMContextBuilder.getOwnProperties(eq(component), eq(2)))
                    .thenReturn(new StringBuilder("ctx2"));
            assertEquals("ctx2", component.toLLMContext(2));
        }

        assertEquals(component, component);
        assertNotEquals(component, new ExerciseComponent());
        assertEquals(component.hashCode(), component.hashCode());
        assertNotNull(component.toString());
        assertNotNull(component.getId());
    }
}
