package edu.hm.hafner.warningsngui.model;

public class ToolsDetailPayload {
    private String[] errorMessages;
    private String[] infoMessages;
    private int fixedSize;
    private int newSize;
    private String qualityGateStatus;
    private int totalSize;
    //TODO owner & reference Build -> http://localhost:8080/jenkins/job/plagi/17/checkstyle/api/json


    public String[] getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String[] errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String[] getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(String[] infoMessages) {
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
}
