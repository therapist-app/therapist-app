package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("counselingPlanRepository")
public interface CounselingPlanRepository extends JpaRepository<CounselingPlan, String> {}
