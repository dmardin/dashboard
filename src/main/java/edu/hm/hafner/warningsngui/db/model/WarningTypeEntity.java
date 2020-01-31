package edu.hm.hafner.warningsngui.db.model;

import javax.persistence.*;
import java.util.List;

public enum WarningTypeEntity {
    FIXED,
    OUTSTANDING,
    NEW;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;

    @OneToMany(mappedBy = "warningType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReportEntity> issues;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<ReportEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<ReportEntity> issues) {
        this.issues = issues;
    }
}
