package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query(value = "select * from job where job.id=?1" , nativeQuery = true)
    Job fetchJobWithId(int id);
}
