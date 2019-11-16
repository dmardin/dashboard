package edu.hm.hafner.warningsngui.dto;

import edu.hm.hafner.analysis.Report;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private String name;
    private int fixedSize;
    private int newSize;
    private String qualityGateStatus;
    private int totalSize;

    private Build build;
    private List<String> errorMessages = new ArrayList<>();
    private List<String> infoMessages = new ArrayList<>();
    private Report outstandingIssues;
    private Report newIssues;
    private Report fixedIssues;

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
}
