package edu.hm.hafner.warningsngui.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="issues")
public class IssuesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private ResultEntity resultEntity;

    @OneToMany(mappedBy = "issues", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IssueEntity> issues = new ArrayList<>();

    //@ManyToOne(cascade = CascadeType.ALL)
    @Enumerated(EnumType.STRING)
    @Column(name = "warning_type")
    private WarningType warningType;

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

    public WarningType getWarningType() {
        return warningType;
    }

    public void setWarningType(WarningType warningType) {
        this.warningType = warningType;
    }
}
