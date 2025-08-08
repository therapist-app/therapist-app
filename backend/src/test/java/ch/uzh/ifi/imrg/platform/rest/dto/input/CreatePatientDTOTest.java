package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreatePatientDTOTest {

    @Test
    void allFieldsCovered() {
        CreatePatientDTO dto = new CreatePatientDTO();

        dto.setName("name");
        dto.setGender("gender");
        dto.setAge(21);
        dto.setPhoneNumber("123");
        dto.setEmail("mail");
        dto.setAddress("addr");
        dto.setMaritalStatus("single");
        dto.setReligion("none");
        dto.setEducation("edu");
        dto.setOccupation("occ");
        dto.setIncome("inc");
        dto.setDateOfAdmission("2025-08-08");

        ComplaintDTO complaint = new ComplaintDTO();
        dto.setComplaints(List.of(complaint));

        dto.setTreatmentPast("tp");
        dto.setTreatmentCurrent("tc");
        dto.setPastMedical("pm");
        dto.setPastPsych("pp");
        dto.setFamilyIllness("fi");
        dto.setFamilySocial("fs");
        dto.setPersonalPerinatal("perinatal");
        dto.setPersonalChildhood("childhood");
        dto.setPersonalEducation("pedu");
        dto.setPersonalPlay("play");
        dto.setPersonalAdolescence("ado");
        dto.setPersonalPuberty("pub");
        dto.setPersonalObstetric("ob");
        dto.setPersonalOccupational("pocc");
        dto.setPersonalMarital("pmar");
        dto.setPersonalPremorbid("prem");

        assertAll(
                () -> assertEquals("name", dto.getName()),
                () -> assertEquals("gender", dto.getGender()),
                () -> assertEquals(21, dto.getAge()),
                () -> assertEquals("123", dto.getPhoneNumber()),
                () -> assertEquals("mail", dto.getEmail()),
                () -> assertEquals("addr", dto.getAddress()),
                () -> assertEquals("single", dto.getMaritalStatus()),
                () -> assertEquals("none", dto.getReligion()),
                () -> assertEquals("edu", dto.getEducation()),
                () -> assertEquals("occ", dto.getOccupation()),
                () -> assertEquals("inc", dto.getIncome()),
                () -> assertEquals("2025-08-08", dto.getDateOfAdmission()),
                () -> assertEquals(1, dto.getComplaints().size()),
                () -> assertEquals("tp", dto.getTreatmentPast()),
                () -> assertEquals("tc", dto.getTreatmentCurrent()),
                () -> assertEquals("pm", dto.getPastMedical()),
                () -> assertEquals("pp", dto.getPastPsych()),
                () -> assertEquals("fi", dto.getFamilyIllness()),
                () -> assertEquals("fs", dto.getFamilySocial()),
                () -> assertEquals("perinatal", dto.getPersonalPerinatal()),
                () -> assertEquals("childhood", dto.getPersonalChildhood()),
                () -> assertEquals("pedu", dto.getPersonalEducation()),
                () -> assertEquals("play", dto.getPersonalPlay()),
                () -> assertEquals("ado", dto.getPersonalAdolescence()),
                () -> assertEquals("pub", dto.getPersonalPuberty()),
                () -> assertEquals("ob", dto.getPersonalObstetric()),
                () -> assertEquals("pocc", dto.getPersonalOccupational()),
                () -> assertEquals("pmar", dto.getPersonalMarital()),
                () -> assertEquals("prem", dto.getPersonalPremorbid())
        );

        assertTrue(dto.toString().contains("name"));
        assertEquals(dto, dto);
        assertEquals(dto.hashCode(), dto.hashCode());
    }
}
