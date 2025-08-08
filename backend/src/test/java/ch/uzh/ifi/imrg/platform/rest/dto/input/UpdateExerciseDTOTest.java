package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateExerciseDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validDTONoViolations() {
        UpdateExerciseDTO dto = new UpdateExerciseDTO();
        dto.setId("i");
        dto.setExerciseTitle("t");
        dto.setExerciseDescription("d");
        dto.setExerciseExplanation("e");
        dto.setExerciseStart(Instant.now());
        dto.setExerciseEnd(Instant.now().plusSeconds(60));
        dto.setIsPaused(Boolean.TRUE);
        dto.setDoEveryNDays(1);
        Set<ConstraintViolation<UpdateExerciseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullDoEveryNDaysNoViolations() {
        UpdateExerciseDTO dto = new UpdateExerciseDTO();
        dto.setDoEveryNDays(null);
        Set<ConstraintViolation<UpdateExerciseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void negativeDoEveryNDaysViolation() {
        UpdateExerciseDTO dto = new UpdateExerciseDTO();
        dto.setDoEveryNDays(-2);
        Set<ConstraintViolation<UpdateExerciseDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        ConstraintViolation<UpdateExerciseDTO> v = violations.iterator().next();
        assertEquals("doEveryNDays", v.getPropertyPath().toString());
        assertEquals("Do every ... days must be a positive number.", v.getMessage());
    }

    @Test
    void zeroDoEveryNDaysViolation() {
        UpdateExerciseDTO dto = new UpdateExerciseDTO();
        dto.setDoEveryNDays(0);
        Set<ConstraintViolation<UpdateExerciseDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        ConstraintViolation<UpdateExerciseDTO> v = violations.iterator().next();
        assertEquals("doEveryNDays", v.getPropertyPath().toString());
    }
}
