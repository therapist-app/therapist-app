package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

  public List<Therapist> getAllTherapists() {
    return this.therapistRepository.findAll();
  }

  public Therapist registerTherapist(Therapist therapist, HttpServletResponse httpServletResponse) {
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
    Therapist createdTherapist = this.therapistRepository.save(therapist);
    String jwt = JwtUtil.createJWT(therapist.getEmail());
    JwtUtil.addJwtCookie(httpServletResponse, jwt);
    return createdTherapist;
  }

  public Therapist loginTherapist(
      LoginTherapistDTO loginTherapistDTO, HttpServletResponse httpServletResponse) {
    Therapist foundTherapist = therapistRepository.getTherapistByEmail(loginTherapistDTO.getEmail());
    if (foundTherapist == null) {
      throw new Error("No therapist with email: " + loginTherapistDTO.getEmail() + " exists");
    }
    log.info(foundTherapist.getEmail());
    log.info(foundTherapist.getPassword());
    log.info(loginTherapistDTO.getPassword());
    if (!foundTherapist.getPassword().equals(loginTherapistDTO.getPassword())) {
      throw new Error("The password you entered is wrong!");
    }

    String jwt = JwtUtil.createJWT(loginTherapistDTO.getEmail());
    JwtUtil.addJwtCookie(httpServletResponse, jwt);
    return foundTherapist;
  }

  public Therapist getCurrentlyLoggedInTherapist(HttpServletRequest httpServletRequest) {
    String email = JwtUtil.validateJWTAndExtractEmail(httpServletRequest);
    Therapist foundTherapist = therapistRepository.getTherapistByEmail((email));
    if (foundTherapist != null) {
      return foundTherapist;
    }
    throw new Error(
        "Therapist could not be found with email the follwing email: " + email + " from JWT.");
  }
}
