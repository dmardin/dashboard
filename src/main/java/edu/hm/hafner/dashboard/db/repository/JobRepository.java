package edu.hm.hafner.dashboard.db.repository;

import edu.hm.hafner.dashboard.db.model.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository to handle the access to the database for the {@link JobEntity}.
 *
 * @author Deniz Mardin
 */
@Repository
public interface JobRepository extends JpaRepository<JobEntity, Integer> {

    JobEntity findByName(String name);
}
