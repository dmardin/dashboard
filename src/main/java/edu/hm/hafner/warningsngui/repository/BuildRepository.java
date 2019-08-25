package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Build;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface BuildRepository extends JpaRepository<Build, Serializable> {
}
