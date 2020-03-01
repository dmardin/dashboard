package edu.hm.hafner.warningsngui.service.schedule.rest.response;

/**
 * Represents a part of the {@link edu.hm.hafner.warningsngui.service.dto.Result}s response from the Jenkins API.
 */
public class ResultResponse {
    private String[] errorMessages;
    private String[] infoMessages;
    private int fixedSize;
    private int newSize;
    private String qualityGateStatus;
    private int totalSize;
    //TODO owner & reference Build -> http://localhost:8080/jenkins/job/plagi/17/checkstyle/api/json

    /**
     * Returns the error messages of the {@link ResultResponse}.
     *
     * @return the error messages
     */
    public String[] getErrorMessages() {
        return errorMessages;
    }

    /**
     * Setter to set the error messages of the {@link ResultResponse}.
     * @param errorMessages the error messages
     */
    public void setErrorMessages(String[] errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * Returns the info messages of the {@link ResultResponse}.
     *
     * @return the info messages
     */
    public String[] getInfoMessages() {
        return infoMessages;
    }

    /**
     * Setter to set the info messages of the {@link ResultResponse}.
     *
     * @param infoMessages the info messages
     */
    public void setInfoMessages(String[] infoMessages) {
        this.infoMessages = infoMessages;
    }

    /**
     * Returns the fixed size of the of the {@link ResultResponse}.
     *
     * @return the fixed size
     */
    public int getFixedSize() {
        return fixedSize;
    }

    /**
     * Setter to set the fixed size of the {@link ResultResponse}.
     *
     * @param fixedSize the fixed size
     */
    public void setFixedSize(int fixedSize) {
        this.fixedSize = fixedSize;
    }

    /**
     * Returns the new size of the {@link ResultResponse}.
     *
     * @return the new size
     */
    public int getNewSize() {
        return newSize;
    }

    /**
     * Setter to set the new size of the {@link ResultResponse}.
     *
     * @param newSize the new size
     */
    public void setNewSize(int newSize) {
        this.newSize = newSize;
    }

    /**
     * Returns the quality gate status of the {@link ResultResponse}.
     *
     * @return the quality gate status
     */
    public String getQualityGateStatus() {
        return qualityGateStatus;
    }

    /**
     * Setter to set the quality gate status of the {@link ResultResponse}.
     *
     * @param qualityGateStatus the quality gate status
     */
    public void setQualityGateStatus(String qualityGateStatus) {
        this.qualityGateStatus = qualityGateStatus;
    }

    /**
     * Returns the total size of the {@link ResultResponse}.
     *
     * @return the total size
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Setter to set the total size of the {@link ResultResponse}.
     *
     * @param totalSize the total size
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
