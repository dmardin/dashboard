package edu.hm.hafner.warningsngui.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="result")
public class ResultEntity {
    //TODO issues müssen nicht neu berrechnet werden
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
//    private transient Report outstandingIssuesReference;
//    private transient Report fixedIssuesReference;
//    private transient Report newIssuesReference;

//    @OneToOne
//    private Tool tool;
    private int total;
    private String name;

    @ManyToOne
    private Build build;

    @OneToMany(mappedBy = "result", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    @OneToMany(mappedBy = "result", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InfoMessage> infoMessages = new ArrayList<>();

    private int fixedSize;
    private int newSize;
    private String qualityGateStatus;
    private int totalSize;

    @OneToMany(mappedBy = "resultEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IssuesEntity> issues = new ArrayList<>();

    public List<IssuesEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<IssuesEntity> issues) {
        this.issues = issues;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<InfoMessage> getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(List<InfoMessage> infoMessages) {
        this.infoMessages = infoMessages;
    }

    public int getFixedSize() {
        return fixedSize;
    }

    public void setFixedSize(int fixedSize) {
        this.fixedSize = fixedSize;
    }

    public int getNewSize() {
        return newSize;
    }

    public void setNewSize(int newSize) {
        this.newSize = newSize;
    }

    public String getQualityGateStatus() {
        return qualityGateStatus;
    }

    public void setQualityGateStatus(String qualityGateStatus) {
        this.qualityGateStatus = qualityGateStatus;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }


    //    @ElementCollection
//    @MapKeyColumn(name = "warning_type")
//    @MapKeyEnumerated(EnumType.STRING)
//    private Map<WarningType, IssueEntity> warnings = new LinkedHashMap<>();

//    public Map<WarningType, IssueEntity> getWarnings() {
//        return warnings;
//    }
//
//    public void setWarnings(Map<WarningType, IssueEntity> warnings) {
//        this.warnings = warnings;
//    }

//    @OneToMany
//    List<IssuesEntity> issues;

//    public List<IssuesEntity> getIssues() {
//        return issues;
//    }
//
//    public void setIssues(List<IssuesEntity> issues) {
//        this.issues = issues;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //    public Report getOutstandingIssuesReference() {
//        return outstandingIssuesReference;
//    }
//
//    public void setOutstandingIssuesReference(Report outstandingIssuesReference) {
//        this.outstandingIssuesReference = outstandingIssuesReference;
//    }
//
//    public Report getFixedIssuesReference() {
//        return fixedIssuesReference;
//    }
//
//    public void setFixedIssuesReference(Report fixedIssuesReference) {
//        this.fixedIssuesReference = fixedIssuesReference;
//    }
//
//    public Report getNewIssuesReference() {
//        return newIssuesReference;
//    }
//
//    public void setNewIssuesReference(Report newIssuesReference) {
//        this.newIssuesReference = newIssuesReference;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Tool getTool() {
//        return tool;
//    }
//
//    public void setTool(Tool tool) {
//        this.tool = tool;
//    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }
}
