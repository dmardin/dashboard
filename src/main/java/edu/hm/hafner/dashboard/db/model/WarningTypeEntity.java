package edu.hm.hafner.dashboard.db.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * POJO to store a {@link WarningTypeEntity} to the database.
 */
public enum WarningTypeEntity {
    FIXED,
    OUTSTANDING,
    NEW;
    
    @OneToMany(mappedBy = "warningType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReportEntity> issues;

    /**
     * Returns a list of {@link ReportEntity}s of the {@link WarningTypeEntity}.
     *
     * @return the list of{@link ReportEntity}s
     */
    public List<ReportEntity> getIssues() {
        return issues;
    }

    /**
     * Setter to set the list of {@link ReportEntity}s of the {@link WarningTypeEntity}
     * @param issues the list of{@link ReportEntity}s
     */
    public void setIssues(List<ReportEntity> issues) {
        this.issues = issues;
    }
}
