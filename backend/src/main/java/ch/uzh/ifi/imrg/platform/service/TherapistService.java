package ch.uzh.ifi.imrg.platform.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TherapistService {
    private final Logger log = LoggerFactory.getLogger(TherapistService.class);

    private final TherapistRepository therapistRepository;

    @Autowired
    public TherapistService(@Qualifier("therapistRepository") TherapistRepository therapistRepository) {
        this.therapistRepository = therapistRepository;
    }

    public List<Therapist> getAllTherapists() {
        return this.therapistRepository.findAll();
    }

}