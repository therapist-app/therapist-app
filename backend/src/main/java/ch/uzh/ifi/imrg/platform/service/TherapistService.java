package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TherapistService {
  private final Logger log = LoggerFactory.getLogger(TherapistService.class);

  private final TherapistRepository therapistRepository;

  @Autowired
  public TherapistService(
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.therapistRepository = therapistRepository;
  }

  public Therapist createTherapist(Therapist therapist) {
    if (therapist.getEmail() == null) {
      throw new Error("Creating therapist failed because no email was specified");
    }
    if (therapist.getPassword() == null) {
      throw new Error("Creating therapist failed because no password was specified");
    }
    if (therapist.getId() != null && this.therapistRepository.existsById(therapist.getId())) {
      throw new Error(
          "Creating therapist failed because therapist with ID "
              + therapist.getId()
              + "already exists");
    }
    if (therapist.getEmail() != null && therapistRepository.existsByEmail(therapist.getEmail())) {
      throw new Error(
          "Creating therapist failed because therapist with email "
              + therapist.getEmail()
              + "already exists");
    }
    return this.therapistRepository.save(therapist);
  }

  public void loginTherapist(LoginTherapistDTO loginTherapistDTO) {
    Therapist foundTherapist =
        therapistRepository.getTherapistByEmail(loginTherapistDTO.getEmail());
    if (foundTherapist == null) {
      throw new Error("No therapist with email: " + loginTherapistDTO.getEmail() + " exists");
    }
    if (foundTherapist.getPassword() != loginTherapistDTO.getPassword()) {
      throw new Error("The password you entered is wrong!");
    }
  }

  public List<Therapist> getAllTherapists() {
    return this.therapistRepository.findAll();
  }
}
