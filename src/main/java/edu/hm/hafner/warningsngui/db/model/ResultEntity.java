package edu.hm.hafner.warningsngui.db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="result")
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String latestUrl;
    private String warningId;
    private String name;
    private int fixedSize;
    private int newSize;
    private String qualityGateStatus;
    private int totalSize;
    @ManyToOne
    private BuildEntity buildEntity;
    @OrderColumn
    @ElementCollection(targetClass = String.class)
    private List<String> errorMessages;
    @OrderColumn
    @ElementCollection(targetClass = String.class)
    private List<String> infoMessages;
    @OneToMany(mappedBy = "resultEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReportEntity> reports;

    public ResultEntity() {
        this.buildEntity = new BuildEntity();
        this.errorMessages = new ArrayList<>();
        this.infoMessages = new ArrayList<>();
        this.reports = new ArrayList<>();
    }

    public ResultEntity(int id, String warningId, String latestUrl, String name, int fixedSize, int newSize, int totalSize, String qualityGateStatus) {
        this.id = id;
        this.warningId = warningId;
        this.latestUrl = latestUrl;
        this.name = name;
        this.fixedSize = fixedSize;
        this.newSize = newSize;
        this.qualityGateStatus = qualityGateStatus;
        this.totalSize = totalSize;
        this.buildEntity = new BuildEntity();
        this.errorMessages = new ArrayList<>();
        this.infoMessages = new ArrayList<>();
        this.reports = new ArrayList<>();
    }

    public List<ReportEntity> getReports() {
        return reports;
    }

    public void setReports(List<ReportEntity> reports) {
        this.reports = reports;
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

    public String getWarningId() {
        return warningId;
    }

    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    public String getLatestUrl() {
        return latestUrl;
    }

    public void setLatestUrl(String latestUrl) {
        this.latestUrl = latestUrl;
    }

    public BuildEntity getBuildEntity() {
        return buildEntity;
    }

    public void setBuildEntity(BuildEntity buildEntity) {
        this.buildEntity = buildEntity;
    }
}
