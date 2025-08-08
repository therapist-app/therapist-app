package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateExerciseDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validDTONoViolations() {
        CreateExerciseDTO dto = new CreateExerciseDTO();
        dto.setPatientId("p");
        dto.setExerciseTitle("title");
        dto.setExerciseDescription("desc");
        dto.setExerciseExplanation("explain");
        dto.setExerciseStart(Instant.now());
        dto.setDurationInWeeks(1);
        dto.setDoEveryNDays(1);
        Set<ConstraintViolation<CreateExerciseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullPositiveFieldsNoViolations() {
        CreateExerciseDTO dto = new CreateExerciseDTO();
        dto.setDurationInWeeks(null);
        dto.setDoEveryNDays(null);
        Set<ConstraintViolation<CreateExerciseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void negativeDurationViolation() {
        CreateExerciseDTO dto = new CreateExerciseDTO();
        dto.setDurationInWeeks(-1);
        Set<ConstraintViolation<CreateExerciseDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        ConstraintViolation<CreateExerciseDTO> v = violations.iterator().next();
        assertEquals("durationInWeeks", v.getPropertyPath().toString());
        assertEquals("Duration must be a positive number.", v.getMessage());
    }

    @Test
    void zeroDurationViolation() {
        CreateExerciseDTO dto = new CreateExerciseDTO();
        dto.setDurationInWeeks(0);
        Set<ConstraintViolation<CreateExerciseDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        ConstraintViolation<CreateExerciseDTO> v = violations.iterator().next();
        assertEquals("durationInWeeks", v.getPropertyPath().toString());
    }

    @Test
    void negativeDoEveryNDaysViolation() {
        CreateExerciseDTO dto = new CreateExerciseDTO();
        dto.setDoEveryNDays(-5);
        Set<ConstraintViolation<CreateExerciseDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        ConstraintViolation<CreateExerciseDTO> v = violations.iterator().next();
        assertEquals("doEveryNDays", v.getPropertyPath().toString());
        assertEquals("Do every ... days must be a positive number.", v.getMessage());
    }

    @Test
    void zeroDoEveryNDaysViolation() {
        CreateExerciseDTO dto = new CreateExerciseDTO();
        dto.setDoEveryNDays(0);
        Set<ConstraintViolation<CreateExerciseDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        ConstraintViolation<CreateExerciseDTO> v = violations.iterator().next();
        assertEquals("doEveryNDays", v.getPropertyPath().toString());
    }
}
