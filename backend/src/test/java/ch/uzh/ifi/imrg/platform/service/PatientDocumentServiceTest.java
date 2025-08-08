package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.*;
import ch.uzh.ifi.imrg.platform.repository.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientDocumentServiceTest {

    PatientRepository                pRepo = mock(PatientRepository.class);
    TherapistRepository              tRepo = mock(TherapistRepository.class);
    TherapistDocumentRepository      tdRepo= mock(TherapistDocumentRepository.class);
    PatientDocumentRepository        pdRepo= mock(PatientDocumentRepository.class);
    PatientDocumentService svc;

    @BeforeEach
    void setUp() { svc = new PatientDocumentService(pRepo,tRepo,tdRepo,pdRepo); }

    private static MultipartFile file(String name,String type,String txt,
                                      boolean bytesFail,boolean streamFail) {
        return new MultipartFile() {
            @Override public String getName(){ return "f"; }
            @Override public String getOriginalFilename(){ return name; }
            @Override public String getContentType(){ return type; }
            @Override public boolean isEmpty(){ return false; }
            @Override public long getSize(){ return txt.length(); }
            @Override public byte[] getBytes() throws IOException{
                if(bytesFail) throw new IOException(); return txt.getBytes();
            }
            @Override public InputStream getInputStream() throws IOException{
                if(streamFail) throw new IOException(); return
                        new java.io.ByteArrayInputStream(txt.getBytes());
            }
            @Override public void transferTo(java.io.File dest){}
        };
    }

    @Test
    void upload_bytesFail_throws() throws Exception {
        Patient pat = new Patient(); pat.setTherapist(new Therapist());
        when(pRepo.findById("pid")).thenReturn(Optional.of(pat));

        MultipartFile bad = file("n","t","txt",true,true);

        assertThrows(ResponseStatusException.class,
                ()->svc.uploadPatientDocument("pid",bad,false,"tid"));
    }

    @Test
    void upload_patientMissing() {
        when(pRepo.findById("pid")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                ()->svc.uploadPatientDocument("pid",
                        file("n","t","txt",false,true),false,"tid"));
    }
}
