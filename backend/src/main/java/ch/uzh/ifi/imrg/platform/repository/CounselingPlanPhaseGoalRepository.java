package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("counselingPlanPhaseGoalRepository")
public interface CounselingPlanPhaseGoalRepository
    extends JpaRepository<CounselingPlanPhaseGoal, String> {}
