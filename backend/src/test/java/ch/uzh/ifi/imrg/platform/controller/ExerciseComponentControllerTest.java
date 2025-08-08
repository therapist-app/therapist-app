package ch.uzh.ifi.imrg.platform.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.service.ExerciseComponentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ExerciseComponentControllerTest {

    @Mock ExerciseComponentService service;
    @InjectMocks ExerciseComponentController controller;

    @Test
    void createDelegates() {
        CreateExerciseComponentDTO dto = new CreateExerciseComponentDTO();
        controller.createExerciseComponent(dto, "T");
        verify(service).createExerciseComponent(dto, "T");
    }

    @Test
    void createWithFileDelegates() {
        CreateExerciseComponentDTO dto = new CreateExerciseComponentDTO();
        MultipartFile file = mock(MultipartFile.class);
        controller.createExerciseComponentWithFile(dto, file, "T");
        verify(service).createExerciseComponentWithFile(dto, file, "T");
    }

    @Test
    void downloadBuildsResponse() {
        ExerciseComponent ec = new ExerciseComponent();
        ec.setFileData(new byte[] {1, 2});
        ec.setFileName("a.pdf");
        ec.setFileType("application/pdf");
        when(service.downloadExerciseComponent("X", "T")).thenReturn(ec);

        ResponseEntity<Resource> resp = controller.downloadExerciseComponentFile("X", "T");
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("attachment; filename=\"a.pdf\"",
                resp.getHeaders().getFirst("Content-Disposition"));
    }

    @Test
    void updateDelegates() {
        UpdateExerciseComponentDTO dto = new UpdateExerciseComponentDTO();
        controller.updateExerciseComponent(dto, "T");
        verify(service).updateExerciseComponent(dto, "T");
    }

    @Test
    void deleteDelegates() {
        controller.deleteExerciseComponent("X", "T");
        verify(service).deleteExerciseComponent("X", "T");
    }
}
