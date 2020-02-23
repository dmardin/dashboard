package edu.hm.hafner.warningsngui.db.repository;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Deniz Mardin
 */
@Repository
public interface BuildRepository extends JpaRepository<BuildEntity, Integer> {
}
