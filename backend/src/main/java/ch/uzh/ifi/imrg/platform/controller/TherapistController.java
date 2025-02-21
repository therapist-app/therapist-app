package ch.uzh.ifi.imrg.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ch.uzh.ifi.imrg.platform.rest.dto.TherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.DTOMapper;

import java.util.List;

import org.springframework.http.HttpStatus;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.service.TherapistService;

@RestController
public class TherapistController {

    private final TherapistService therapistService;

    TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @GetMapping("/therapists")
    @ResponseStatus(HttpStatus.OK)
    public List<TherapistDTO> getAllTherapists() {
        List<Therapist> therapists = therapistService.getAllTherapists();
        return therapists.stream().map(DTOMapper.INSTANCE::convertEntityToTherapistDTO).toList();

    }
}