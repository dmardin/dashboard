package edu.hm.hafner.warningsngui.repository;

import edu.hm.hafner.warningsngui.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Integer> {

    public Tool findByIdentifier(int identifier);
}
