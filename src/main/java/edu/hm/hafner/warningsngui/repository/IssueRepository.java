package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
}
