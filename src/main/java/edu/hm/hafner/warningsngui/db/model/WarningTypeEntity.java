package edu.hm.hafner.warningsngui.db.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

public enum WarningTypeEntity {
    FIXED,
    OUTSTANDING,
    NEW;
    
    @OneToMany(mappedBy = "warningType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReportEntity> issues;

    public List<ReportEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<ReportEntity> issues) {
        this.issues = issues;
    }
}
