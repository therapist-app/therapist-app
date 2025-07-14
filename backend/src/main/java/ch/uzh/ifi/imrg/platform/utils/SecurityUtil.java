package ch.uzh.ifi.imrg.platform.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.imrg.platform.entity.OwnedByTherapist;

public class SecurityUtil {

    public static void checkOwnership(OwnedByTherapist entity, String currentTherapistId) {
        if (!entity.getOwningTherapistId().equals(currentTherapistId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "The therapist is not allowed to perform this action");
        }
    }
}