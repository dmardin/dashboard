package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface IssueRepository extends JpaRepository<Issue, Serializable> {
}
