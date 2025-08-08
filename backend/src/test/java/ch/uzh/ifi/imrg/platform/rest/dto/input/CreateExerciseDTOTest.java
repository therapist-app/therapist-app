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
    void gettersSettersEqualsHashCodeToString() {
        CreateExerciseDTO a = new CreateExerciseDTO();
        CreateExerciseDTO b = new CreateExerciseDTO();

        Instant start = Instant.now();

        a.setPatientId("p1");
        a.setExerciseTitle("title");
        a.setExerciseDescription("desc");
        a.setExerciseExplanation("expl");
        a.setExerciseStart(start);
        a.setDurationInWeeks(4);
        a.setDoEveryNDays(2);

        b.setPatientId("p1");
        b.setExerciseTitle("title");
        b.setExerciseDescription("desc");
        b.setExerciseExplanation("expl");
        b.setExerciseStart(start);
        b.setDurationInWeeks(4);
        b.setDoEveryNDays(2);

        assertEquals("p1", a.getPatientId());
        assertEquals("title", a.getExerciseTitle());
        assertEquals("desc", a.getExerciseDescription());
        assertEquals("expl", a.getExerciseExplanation());
        assertEquals(start, a.getExerciseStart());
        assertEquals(4, a.getDurationInWeeks());
        assertEquals(2, a.getDoEveryNDays());

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertTrue(a.toString().contains("exerciseTitle"));

        b.setDoEveryNDays(3);
        assertNotEquals(a, b);
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
