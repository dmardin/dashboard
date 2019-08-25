package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface ToolRepository extends JpaRepository<Tool, Serializable> {
}
