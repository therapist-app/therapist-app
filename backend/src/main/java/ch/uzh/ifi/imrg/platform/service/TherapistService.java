package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
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
    initializeSomeTherapists();
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

  public List<Therapist> getAllTherapists() {
    return this.therapistRepository.findAll();
  }

  private void initializeSomeTherapists() {
    Therapist therapist1 = new Therapist();
    therapist1.setId("bf2a19d5-3037-4b65-86dd-cec232ec66b1");
    therapist1.setEmail("admin@admin.com");
    therapist1.setPassword("admin");

    Therapist therapist2 = new Therapist();
    therapist2.setId("6af0e118-c344-4444-8681-2d2e56cf6302");
    therapist2.setEmail("test@test.com");
    therapist2.setPassword("test");

    Therapist therapist3 = new Therapist();
    therapist3.setId("f8fda83a-995f-4bf5-8604-89e10d287738");
    therapist3.setEmail("therapist@therapist.com");
    therapist3.setPassword("therapist");

    if (!this.therapistRepository.existsById(therapist1.getId())) {
      this.therapistRepository.save(therapist1);
    }

    if (!this.therapistRepository.existsById(therapist2.getId())) {
      this.therapistRepository.save(therapist2);
    }

    if (!this.therapistRepository.existsById(therapist3.getId())) {
      this.therapistRepository.save(therapist3);
    }
  }
}
