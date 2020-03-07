package edu.hm.hafner.warningsngui.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.hm.hafner.analysis.Report;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO that represents a {@link Result}.
 */
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

    /**
     * Creates a new instance of a {@link Result}.
     */
    public Result() {
        this.build = new Build();
        this.errorMessages = new ArrayList<>();
        this.infoMessages = new ArrayList<>();
        this.outstandingIssues = new Report();
        this.newIssues = new Report();
        this.fixedIssues = new Report();
    }

    /**
     * Creates a new instance of a {@link Result}.
     *
     * @param id the id of the {@link Result}
     * @param warningId the waring id of the {@link Result}
     * @param latestUrl the latest url of the {@link Result}
     * @param name the name of the {@link Result}
     * @param fixedSize the fixed size of the {@link Result}
     * @param newSize the new size of the {@link Result}
     * @param totalSize the total size of the {@link Result}
     * @param qualityGateStatus the quality gate of the {@link Result}
     */
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

    /**
     * Returns the waring id of the {@link Result}.
     *
     * @return the warning id
     */
    public String getWarningId() {
        return warningId;
    }

    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    /**
     * Returns the latest url of the {@link Result}.
     *
     * @return the latest url
     */
    public String getLatestUrl() {
        return latestUrl;
    }

    /**
     * Setter to set the latest url of the {@link Result}.
     *
     * @param latestUrl the url
     */
    public void setLatestUrl(String latestUrl) {
        this.latestUrl = latestUrl;
    }

    /**
     * Returns the {@link Report} with outstanding issues of the {@link Result}.
     *
     * @return the {@link Report}
     */
    public Report getOutstandingIssues() {
        return outstandingIssues;
    }

    /**
     * Setter to set a {@link Report} with outstanding issues of the {@link Result}.
     *
     * @param outstandingIssues the {@link Report}
     */
    public void setOutstandingIssues(Report outstandingIssues) {
        this.outstandingIssues = outstandingIssues;
    }

    /**
     * Returns the {@link Report} with new issues of the {@link Result}.
     *
     * @return the {@link Report}
     */
    public Report getNewIssues() {
        return newIssues;
    }

    /**
     * Setter to set a {@link Report} with new issues of the {@link Result}.
     *
     * @param newIssues the {@link Report}
     */
    public void setNewIssues(Report newIssues) {
        this.newIssues = newIssues;
    }

    /**
     * Returns the {@link Report} with fixed issues of the {@link Result}.
     *
     * @return the {@link Report}
     */
    public Report getFixedIssues() {
        return fixedIssues;
    }

    /**
     * Setter to set a {@link Report} with fixed issues of the {@link Result}.
     *
     * @param fixedIssues the {@link Report}
     */
    public void setFixedIssues(Report fixedIssues) {
        this.fixedIssues = fixedIssues;
    }

    /**
     * Returns the name of the {@link Result}.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the fixed size of the {@link Result}.
     *
     * @return the fixed size
     */
    public int getFixedSize() {
        return fixedSize;
    }

    /**
     * Setter to set the fixed size of the {@link Result}.
     *
     * @param fixedSize the fixed size
     */
    public void setFixedSize(int fixedSize) {
        this.fixedSize = fixedSize;
    }

    /**
     * Returns the new size of the {@link Result}.
     *
     * @return the new size
     */
    public int getNewSize() {
        return newSize;
    }

    /**
     * Setter to set the new size of the {@link Result}.
     *
     * @param newSize the new size
     */
    public void setNewSize(int newSize) {
        this.newSize = newSize;
    }

    /**
     * Returns the quality gate status of the {@link Result}.
     *
     * @return the quality gate status
     */
    public String getQualityGateStatus() {
        return qualityGateStatus;
    }

    /**
     * Setter to set the quality gate status of the {@link Result}.
     *
     * @param qualityGateStatus the quality gate status
     */
    public void setQualityGateStatus(String qualityGateStatus) {
        this.qualityGateStatus = qualityGateStatus;
    }

    /**
     * Returns the total size of the {@link Result}.
     *
     * @return the total size
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Setter to set the total size of the {@link Result}.
     *
     * @param totalSize the total size
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * Returns the {@link Build} of the {@link Result}.
     *
     * @return the {@link Build}
     */
    public Build getBuild() {
        return build;
    }

    /**
     * Setter to set the {@link Build} of the {@link Result}.
     *
     * @param build the {@link Build}
     */
    public void setBuild(Build build) {
        this.build = build;
    }

    /**
     * Returns the error messages of the {@link Result}.
     *
     * @return the error messages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Setter to set the error messages of the {@link Result}.
     *
     * @param errorMessages the error messages
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * Returns the info messages of the {@link Result}.
     *
     * @return the info messages
     */
    public List<String> getInfoMessages() {
        return infoMessages;
    }

    /**
     * Setter to set the info messages of the {@link Result}.
     *
     * @param infoMessages the info messages
     */
    public void setInfoMessages(List<String> infoMessages) {
        this.infoMessages = infoMessages;
    }

    /**
     * Returns the id of the {@link Result}.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter to set the id of the {@link Result}.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (id != result.id) return false;
        if (fixedSize != result.fixedSize) return false;
        if (newSize != result.newSize) return false;
        if (totalSize != result.totalSize) return false;
        if (!warningId.equals(result.warningId)) return false;
        if (!latestUrl.equals(result.latestUrl)) return false;
        if (!name.equals(result.name)) return false;
        if (!qualityGateStatus.equals(result.qualityGateStatus)) return false;
        if (!errorMessages.equals(result.errorMessages)) return false;
        if (!infoMessages.equals(result.infoMessages)) return false;
        if (!outstandingIssues.equals(result.outstandingIssues)) return false;
        if (!newIssues.equals(result.newIssues)) return false;
        return fixedIssues.equals(result.fixedIssues);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + warningId.hashCode();
        result = 31 * result + latestUrl.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + fixedSize;
        result = 31 * result + newSize;
        result = 31 * result + qualityGateStatus.hashCode();
        result = 31 * result + totalSize;
        result = 31 * result + errorMessages.hashCode();
        result = 31 * result + infoMessages.hashCode();
        result = 31 * result + outstandingIssues.hashCode();
        result = 31 * result + newIssues.hashCode();
        result = 31 * result + fixedIssues.hashCode();
        return result;
    }
}
