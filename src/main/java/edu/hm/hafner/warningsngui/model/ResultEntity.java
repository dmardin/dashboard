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

    private int total;
    private String name;
    private int fixedSize;
    private int newSize;
    private String qualityGateStatus;
    private int totalSize;

    @ManyToOne
    private Build build;

    @OrderColumn
    @ElementCollection(targetClass = String.class)
    private List<String> errorMessages = new ArrayList<>();

    @OrderColumn
    @ElementCollection(targetClass = String.class)
    private List<String> infoMessages = new ArrayList<>();

    @OneToMany(mappedBy = "resultEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IssuesEntity> issues = new ArrayList<>();

    public List<IssuesEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<IssuesEntity> issues) {
        this.issues = issues;
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

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(List<String> infoMessages) {
        this.infoMessages = infoMessages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
