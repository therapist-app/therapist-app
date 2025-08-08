package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.JwtUtil;
import ch.uzh.ifi.imrg.platform.utils.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
class TherapistServiceTest {

    TherapistRepository repo = mock(TherapistRepository.class);
    TherapistService svc   = new TherapistService(repo);

    private Therapist therapist(String email, String pwd){
        Therapist t = new Therapist();
        t.setEmail(email);
        t.setPassword(pwd);
        return t;
    }

    @Test
    void getById() {
        Therapist t = therapist("e", "p");
        when(repo.getReferenceById("tid")).thenReturn(t);
        assertEquals("e", svc.getTherapistById("tid").getEmail());
    }

    @Test
    void getIdFromRequest_found_and_notFound() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(repo.getTherapistByEmail("e")).thenReturn(new Therapist());

        try (MockedStatic<JwtUtil> jwt = Mockito.mockStatic(JwtUtil.class)) {
            jwt.when(() -> JwtUtil.validateJWTAndExtractEmail(req)).thenReturn("e");
            assertNotNull(svc.getTherapistIdBasedOnRequest(req));

            when(repo.getTherapistByEmail("e")).thenReturn(null);
            assertThrows(ResponseStatusException.class, () -> svc.getTherapistIdBasedOnRequest(req));
        }
    }

    @Test
    void registerTherapist_allBranches() {
        Therapist t = therapist("e", "p");

        when(repo.existsById(any())).thenReturn(false);
        when(repo.existsByEmail("e")).thenReturn(false);
        when(repo.save(t)).thenReturn(t);

        try (MockedStatic<PasswordUtil> pw = Mockito.mockStatic(PasswordUtil.class);
             MockedStatic<JwtUtil> jwt      = Mockito.mockStatic(JwtUtil.class)) {

            pw.when(() -> PasswordUtil.encryptPassword("p")).thenReturn("enc");
            jwt.when(() -> JwtUtil.createJWT("e")).thenReturn("tok");

            TherapistOutputDTO created = svc.registerTherapist(t, mock(HttpServletRequest.class), mock(HttpServletResponse.class));
            assertEquals("e", created.getEmail());
            verify(repo).save(t);
        }

        Therapist noMail = therapist(null, "p");
        assertThrows(ResponseStatusException.class, () -> svc.registerTherapist(noMail, null, null));

        Therapist noPwd = therapist("e2", null);
        assertThrows(ResponseStatusException.class, () -> svc.registerTherapist(noPwd, null, null));

        Therapist dupId = therapist("x@e", "p");
        dupId.setId("X");
        when(repo.existsById("X")).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> svc.registerTherapist(dupId, null, null));

        Therapist dupMail = therapist("e", "p");
        when(repo.existsByEmail("e")).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> svc.registerTherapist(dupMail, null, null));
    }

    @Test
    void updateTherapist() {
        Therapist stored = therapist("e", "old");
        when(repo.getReferenceById("tid")).thenReturn(stored);

        UpdateTherapistDTO dto = new UpdateTherapistDTO();
        dto.setPassword("new");
        dto.setLlmModel(LLMModel.AZURE_OPENAI);

        try (MockedStatic<PasswordUtil> pw = Mockito.mockStatic(PasswordUtil.class)) {
            pw.when(() -> PasswordUtil.encryptPassword("new")).thenReturn("enc");
            TherapistOutputDTO out = svc.updateTherapist(dto, "tid");
            assertEquals("e", out.getEmail());
            assertEquals("enc", stored.getPassword());
            assertEquals(LLMModel.AZURE_OPENAI, stored.getLlmModel());
        }
    }

    @Test
    void loginTherapist_paths() {
        LoginTherapistDTO in = new LoginTherapistDTO();
        in.setEmail("e");
        in.setPassword("p");

        Therapist saved = therapist("e", "hash");
        when(repo.getTherapistByEmail("e")).thenReturn(saved);

        try (MockedStatic<PasswordUtil> pw = Mockito.mockStatic(PasswordUtil.class);
             MockedStatic<JwtUtil> jwt      = Mockito.mockStatic(JwtUtil.class)) {

            pw.when(() -> PasswordUtil.checkPassword("p", "hash")).thenReturn(true);
            jwt.when(() -> JwtUtil.createJWT("e")).thenReturn("tok");

            TherapistOutputDTO ok = svc.loginTherapist(in, mock(HttpServletRequest.class), mock(HttpServletResponse.class));
            assertEquals("e", ok.getEmail());

            pw.when(() -> PasswordUtil.checkPassword("p", "hash")).thenReturn(false);
            assertThrows(ResponseStatusException.class, () -> svc.loginTherapist(in, null, null));
        }

        when(repo.getTherapistByEmail("e")).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> svc.loginTherapist(in, null, null));
    }

    @Test
    void logoutCallsJwtUtil() {
        HttpServletResponse res = mock(HttpServletResponse.class);
        try (MockedStatic<JwtUtil> jwt = Mockito.mockStatic(JwtUtil.class)) {
            svc.logoutTherapist(res);
            jwt.verify(() -> JwtUtil.removeJwtCookie(res));
        }
    }
}
