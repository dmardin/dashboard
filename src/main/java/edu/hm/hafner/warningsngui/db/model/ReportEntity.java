package edu.hm.hafner.warningsngui.db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private ResultEntity resultEntity;
    @OneToMany(mappedBy = "issues", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IssueEntity> issues;
    @Enumerated(EnumType.STRING)
    @Column(name = "warning_type")
    private WarningTypeEntity warningTypeEntity;

    public ReportEntity() {
        this.resultEntity = new ResultEntity();
        this.issues = new ArrayList<>();
    }

    public ReportEntity(WarningTypeEntity warningTypeEntity) {
        this.warningTypeEntity = warningTypeEntity;
        this.resultEntity = new ResultEntity();
        this.issues = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<IssueEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueEntity> issues) {
        this.issues = issues;
    }

    public ResultEntity getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(ResultEntity resultEntity) {
        this.resultEntity = resultEntity;
    }

    public WarningTypeEntity getWarningTypeEntity() {
        return warningTypeEntity;
    }

    public void setWarningTypeEntity(WarningTypeEntity warningTypeEntity) {
        this.warningTypeEntity = warningTypeEntity;
    }
}
