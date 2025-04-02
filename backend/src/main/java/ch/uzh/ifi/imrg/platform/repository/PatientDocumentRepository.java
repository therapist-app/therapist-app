package ch.uzh.ifi.imrg.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.imrg.platform.entity.PatientDocument;

@Repository
public interface PatientDocumentRepository
        extends JpaRepository<PatientDocument, String> {

}
