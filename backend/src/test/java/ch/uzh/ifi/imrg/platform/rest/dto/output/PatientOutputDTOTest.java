package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ComplaintDTO;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PatientOutputDTOTest {

    @Test
    void allGettersSettersAndConstructors() {
        PatientOutputDTO dto = new PatientOutputDTO();
        Instant now = Instant.now();

        dto.setId("id");
        dto.setName("name");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        dto.setPhoneNumber("123");
        dto.setAddress("addr");
        dto.setGender("g");
        dto.setAge(33);
        dto.setEmail("mail");
        dto.setInitialPassword("pwd");
        dto.setMaritalStatus("single");
        dto.setReligion("none");
        dto.setEducation("edu");
        dto.setOccupation("occ");
        dto.setIncome("inc");
        dto.setDateOfAdmission("date");

        dto.setComplaints(List.of(new ComplaintDTO()));
        dto.setTreatmentPast("tp");
        dto.setTreatmentCurrent("tc");
        dto.setPastMedical("pm");
        dto.setPastPsych("pp");
        dto.setFamilyIllness("fi");
        dto.setFamilySocial("fs");
        dto.setPersonalPerinatal("ppn");
        dto.setPersonalChildhood("pc");
        dto.setPersonalEducation("pedu");
        dto.setPersonalPlay("pplay");
        dto.setPersonalAdolescence("pa");
        dto.setPersonalPuberty("pub");
        dto.setPersonalObstetric("pob");
        dto.setPersonalOccupational("pocc");
        dto.setPersonalMarital("pmar");
        dto.setPersonalPremorbid("ppm");

        dto.setMeetingsOutputDTO(List.of(mock(MeetingOutputDTO.class)));
        dto.setChatbotTemplatesOutputDTO(List.of(mock(ChatbotTemplateOutputDTO.class)));

        assertEquals("id", dto.getId());
        assertEquals("name", dto.getName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals("123", dto.getPhoneNumber());
        assertEquals("addr", dto.getAddress());
        assertEquals("g", dto.getGender());
        assertEquals(33, dto.getAge());
        assertEquals("mail", dto.getEmail());
        assertEquals("pwd", dto.getInitialPassword());
        assertEquals("single", dto.getMaritalStatus());
        assertEquals("none", dto.getReligion());
        assertEquals("edu", dto.getEducation());
        assertEquals("occ", dto.getOccupation());
        assertEquals("inc", dto.getIncome());
        assertEquals("date", dto.getDateOfAdmission());
        assertEquals(1, dto.getComplaints().size());
        assertEquals("tp", dto.getTreatmentPast());
        assertEquals("tc", dto.getTreatmentCurrent());
        assertEquals("pm", dto.getPastMedical());
        assertEquals("pp", dto.getPastPsych());
        assertEquals("fi", dto.getFamilyIllness());
        assertEquals("fs", dto.getFamilySocial());
        assertEquals("ppn", dto.getPersonalPerinatal());
        assertEquals("pc", dto.getPersonalChildhood());
        assertEquals("pedu", dto.getPersonalEducation());
        assertEquals("pplay", dto.getPersonalPlay());
        assertEquals("pa", dto.getPersonalAdolescence());
        assertEquals("pub", dto.getPersonalPuberty());
        assertEquals("pob", dto.getPersonalObstetric());
        assertEquals("pocc", dto.getPersonalOccupational());
        assertEquals("pmar", dto.getPersonalMarital());
        assertEquals("ppm", dto.getPersonalPremorbid());
        assertEquals(1, dto.getMeetingsOutputDTO().size());
        assertEquals(1, dto.getChatbotTemplatesOutputDTO().size());

        PatientOutputDTO ctor = new PatientOutputDTO("id", "name");
        assertEquals("id", ctor.getId());
        assertEquals("name", ctor.getName());
    }
}
