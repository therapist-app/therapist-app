package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.utils.JwtUtil;
import ch.uzh.ifi.imrg.platform.utils.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class TherapistService {

  private final TherapistRepository therapistRepository;

  @Autowired
  public TherapistService(
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.therapistRepository = therapistRepository;
  }

  public TherapistOutputDTO getTherapistById(String therapistId) {
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(therapist);
  }

  public String getTherapistIdBasedOnRequest(HttpServletRequest httpServletRequest) {
    String email = JwtUtil.validateJWTAndExtractEmail(httpServletRequest);
    Therapist foundTherapist = therapistRepository.getTherapistByEmail((email));
    if (foundTherapist != null) {
      return foundTherapist.getId();
    }
    throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED, "Therapist could not be found for the provided JWT");
  }

  public TherapistOutputDTO registerTherapist(
      Therapist therapist,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    if (therapist.getEmail() == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Creating therapist failed because no email was specified");
    }
    if (therapist.getPassword() == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Creating therapist failed because no password was specified");
    }
    if (therapist.getId() != null && this.therapistRepository.existsById(therapist.getId())) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Creating therapist failed because therapist with ID "
              + therapist.getId()
              + " already exists");
    }
    if (therapist.getEmail() != null && therapistRepository.existsByEmail(therapist.getEmail())) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Creating therapist failed because therapist with email "
              + therapist.getEmail()
              + " already exists");
    }

    therapist.setPassword(PasswordUtil.encryptPassword(therapist.getPassword()));
    therapist.setLlmModel(LLMModel.LOCAL_UZH);

    Therapist createdTherapist = this.therapistRepository.save(therapist);
    String jwt = JwtUtil.createJWT(therapist.getEmail());
    JwtUtil.addJwtCookie(httpServletResponse, httpServletRequest, jwt);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(createdTherapist);
  }

  public TherapistOutputDTO updateTherapist(UpdateTherapistDTO dto, String therapistId) {
    Therapist therapist = therapistRepository.getReferenceById(therapistId);

    if (dto.getPassword() != null) {
      therapist.setPassword(PasswordUtil.encryptPassword(dto.getPassword()));
    }

    if (dto.getLlmModel() != null) {
      therapist.setLlmModel(dto.getLlmModel());
    }

    therapistRepository.save(therapist);

    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(therapist);
  }

  public TherapistOutputDTO loginTherapist(
      LoginTherapistDTO loginTherapistDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist foundTherapist =
        therapistRepository.getTherapistByEmail(loginTherapistDTO.getEmail());
    if (foundTherapist == null) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "No therapist with that email exists");
    }

    if (!PasswordUtil.checkPassword(
        loginTherapistDTO.getPassword(), foundTherapist.getPassword())) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "The password you entered is wrong!");
    }

    String jwt = JwtUtil.createJWT(loginTherapistDTO.getEmail());
    JwtUtil.addJwtCookie(httpServletResponse, httpServletRequest, jwt);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(foundTherapist);
  }

  public void logoutTherapist(HttpServletResponse httpServletResponse) {
    JwtUtil.removeJwtCookie(httpServletResponse);
  }
}
