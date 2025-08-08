package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.generated.model.PsychologicalTestOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PsychologicalTestQuestionOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestQuestionOutputDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientPsychologicalTestMapperTest {

    @Test
    void mapsFullDtoAndNullQuestions() {
        Instant done = Instant.now();

        PsychologicalTestQuestionOutputDTOPatientAPI q1 = new PsychologicalTestQuestionOutputDTOPatientAPI();
        q1.setQuestion("Q1");
        q1.setScore(2);

        PsychologicalTestQuestionOutputDTOPatientAPI q2 = new PsychologicalTestQuestionOutputDTOPatientAPI();
        q2.setQuestion("Q2");
        q2.setScore(3);

        PsychologicalTestOutputDTOPatientAPI api = new PsychologicalTestOutputDTOPatientAPI();
        api.setName("PHQ-9");
        api.setDescription("desc");
        api.setPatientId("p1");
        api.setCompletedAt(done);
        api.setQuestions(List.of(q1, q2));

        PsychologicalTestOutputDTO mapped = PatientPsychologicalTestMapper.INSTANCE.toPsychologicalTestOutputDTO(api);
        assertNull(mapped.getId());
        assertEquals("PHQ-9", mapped.getName());
        assertEquals("desc", mapped.getDescription());
        assertEquals("p1", mapped.getPatientId());
        assertEquals(done, mapped.getCompletedAt());
        assertEquals(2, mapped.getQuestions().size());
        PsychologicalTestQuestionOutputDTO mq1 = mapped.getQuestions().get(0);
        PsychologicalTestQuestionOutputDTO mq2 = mapped.getQuestions().get(1);
        assertEquals("Q1", mq1.getQuestion());
        assertEquals(2, mq1.getScore());
        assertEquals("Q2", mq2.getQuestion());
        assertEquals(3, mq2.getScore());

        assertNull(PatientPsychologicalTestMapper.INSTANCE.mapQuestions(null));
    }
}
