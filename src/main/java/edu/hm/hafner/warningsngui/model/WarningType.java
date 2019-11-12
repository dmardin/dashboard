package edu.hm.hafner.warningsngui.model;

import javax.persistence.*;
import java.util.List;

public enum WarningType {
    FIXED,
    OUTSTANDING,
    NEW;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;

    @OneToMany(mappedBy = "warningType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IssuesEntity> issues;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<IssuesEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<IssuesEntity> issues) {
        this.issues = issues;
    }
}
