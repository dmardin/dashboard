package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Integer> {

    @Query(value = "select * from job where job.id=?1" , nativeQuery = true)
    JobEntity fetchJobWithId(int id);

    JobEntity findByName(String name);
}
