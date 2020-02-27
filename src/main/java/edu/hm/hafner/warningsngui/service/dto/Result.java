package edu.hm.hafner.warningsngui.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.hm.hafner.analysis.Report;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private int id;
    private String warningId;
    private String latestUrl;
    private String name;
    private int fixedSize;
    private int newSize;
    private String qualityGateStatus;
    private int totalSize;
    @JsonIgnoreProperties("results")
    private Build build;
    private List<String> errorMessages;
    private List<String> infoMessages;
    private Report outstandingIssues;
    private Report newIssues;
    private Report fixedIssues;

    public Result(int id, String warningId, String latestUrl, String name, int fixedSize, int newSize, int totalSize, String qualityGateStatus) {
        this.id = id;
        this.warningId = warningId;
        this.latestUrl = latestUrl;
        this.name = name;
        this.fixedSize = fixedSize;
        this.newSize = newSize;
        this.totalSize = totalSize;
        this.qualityGateStatus = qualityGateStatus;
        this.build = new Build();
        this.errorMessages = new ArrayList<>();
        this.infoMessages = new ArrayList<>();
        this.outstandingIssues = new Report();
        this.newIssues = new Report();
        this.fixedIssues = new Report();
    }

    public Result() {
        this.build = new Build();
        this.errorMessages = new ArrayList<>();
        this.infoMessages = new ArrayList<>();
        this.outstandingIssues = new Report();
        this.newIssues = new Report();
        this.fixedIssues = new Report();
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

    public Report getOutstandingIssues() {
        return outstandingIssues;
    }

    public void setOutstandingIssues(Report outstandingIssues) {
        this.outstandingIssues = outstandingIssues;
    }

    public Report getNewIssues() {
        return newIssues;
    }

    public void setNewIssues(Report newIssues) {
        this.newIssues = newIssues;
    }

    public Report getFixedIssues() {
        return fixedIssues;
    }

    public void setFixedIssues(Report fixedIssues) {
        this.fixedIssues = fixedIssues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
