package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Build;
import edu.hm.hafner.warningsngui.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BuildRepository extends JpaRepository<Build, Integer> {

    Set<Build> findByJob(Job job);
}
