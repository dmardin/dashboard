package edu.hm.hafner.dashboard.db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO to store a {@link ReportEntity} to the database.
 */
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

    /**
     * Creates a new instance of {@link ReportEntity}.
     */
    protected ReportEntity() {
        this.resultEntity = new ResultEntity();
        this.issues = new ArrayList<>();
    }

    /**
     * Creates a new instance of the {@link ReportEntity}.
     *
     * @param warningTypeEntity the {@link WarningTypeEntity} of the {@link ReportEntity} e.g. NEW, OUTSTANDING or FIXED
     */
    public ReportEntity(WarningTypeEntity warningTypeEntity) {
        this.warningTypeEntity = warningTypeEntity;
        this.resultEntity = new ResultEntity();
        this.issues = new ArrayList<>();
    }

    /**
     * Returns the id of the {@link ReportEntity}.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter to set the id of the {@link ReportEntity}.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the issues of the {@link ReportEntity}.
     *
     * @return the issues
     */
    public List<IssueEntity> getIssues() {
        return issues;
    }

    /**
     * Setter to set the issues of the {@link ReportEntity}.
     *
     * @param issues the issues
     */
    public void setIssues(List<IssueEntity> issues) {
        this.issues = issues;
    }

    /**
     * Returns the {@link ResultEntity} of the {@link ReportEntity}.
     *
     * @return the {@link ResultEntity}
     */
    public ResultEntity getResultEntity() {
        return resultEntity;
    }

    /**
     * Setter to set the {@link ResultEntity} of the {@link ReportEntity}.
     *
     * @param resultEntity the {@link ResultEntity}
     */
    public void setResultEntity(ResultEntity resultEntity) {
        this.resultEntity = resultEntity;
    }

    /**
     * Returns the {@link WarningTypeEntity} of the {@link ReportEntity}.
     *
     * @return the {@link WarningTypeEntity}
     */
    public WarningTypeEntity getWarningTypeEntity() {
        return warningTypeEntity;
    }

    /**
     * Setter to set the {@link WarningTypeEntity} of the {@link ReportEntity}.
     *
     * @param warningTypeEntity the {@link WarningTypeEntity}
     */
    public void setWarningTypeEntity(WarningTypeEntity warningTypeEntity) {
        this.warningTypeEntity = warningTypeEntity;
    }

    /**
     * Adds a {@link IssueEntity} tho the {@link ReportEntity}.
     *
     * @param issueEntity the {@link IssueEntity}
     * @return the added {@link IssueEntity}
     */
    public IssueEntity addIssueEntity(IssueEntity issueEntity) {
        getIssues().add(issueEntity);
        issueEntity.setIssues(this);

        return issueEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportEntity that = (ReportEntity) o;

        if (id != that.id) return false;
        if (!issues.equals(that.issues)) return false;
        return warningTypeEntity == that.warningTypeEntity;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + issues.hashCode();
        result = 31 * result + warningTypeEntity.hashCode();
        return result;
    }
}
