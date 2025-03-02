package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.utils.JwtUtil;
import ch.uzh.ifi.imrg.platform.utils.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class TherapistService {
  private final Logger logger = LoggerFactory.getLogger(TherapistService.class);

  private final TherapistRepository therapistRepository;

  @Autowired
  public TherapistService(
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.therapistRepository = therapistRepository;
  }

  public Therapist getCurrentlyLoggedInTherapist(HttpServletRequest httpServletRequest) {
    String email = JwtUtil.validateJWTAndExtractEmail(httpServletRequest);
    Therapist foundTherapist = therapistRepository.getTherapistByEmail((email));
    if (foundTherapist != null) {
      return foundTherapist;
    }
    throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED, "Therapist could not be found for the provided JWT");
  }

  public Therapist registerTherapist(
      Therapist therapist,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
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

    therapist.setPassword(PasswordUtil.encryptPassword(therapist.getPassword()));

    Therapist createdTherapist = this.therapistRepository.save(therapist);
    String jwt = JwtUtil.createJWT(therapist.getEmail());
    JwtUtil.addJwtCookie(httpServletResponse, httpServletRequest, jwt);
    return createdTherapist;
  }

  public Therapist loginTherapist(
      LoginTherapistDTO loginTherapistDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist foundTherapist = therapistRepository.getTherapistByEmail(loginTherapistDTO.getEmail());
    if (foundTherapist == null) {
      throw new Error("No therapist with email: " + loginTherapistDTO.getEmail() + " exists");
    }

    if (!PasswordUtil.checkPassword(
        loginTherapistDTO.getPassword(), foundTherapist.getPassword())) {
      throw new Error("The password you entered is wrong!");
    }

    String jwt = JwtUtil.createJWT(loginTherapistDTO.getEmail());
    JwtUtil.addJwtCookie(httpServletResponse, httpServletRequest, jwt);
    return foundTherapist;
  }

  public void logoutTherapist(HttpServletResponse httpServletResponse) {
    JwtUtil.removeJwtCookie(httpServletResponse);
  }
}
