package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface JobRepository extends JpaRepository<Job, Serializable> {
}
