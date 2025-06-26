package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("counselingPlanPhaseRepository")
public interface CounselingPlanPhaseRepository extends JpaRepository<CounselingPlanPhase, String> {}
