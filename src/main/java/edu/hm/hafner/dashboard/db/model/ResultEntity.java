package edu.hm.hafner.dashboard.db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO to store a {@link ResultEntity} to the database.
 */
@Entity
@Table(name = "result")
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

    /**
     * Creates a new instance of {@link ResultEntity}.
     */
    protected ResultEntity() {
        this.buildEntity = new BuildEntity();
        this.errorMessages = new ArrayList<>();
        this.infoMessages = new ArrayList<>();
        this.reports = new ArrayList<>();
    }

    /**
     * Creates a new instance of {@link ResultEntity}.
     *
     * @param id the id of the {@link ResultEntity}
     * @param warningId the warning id of the {@link ResultEntity}
     * @param latestUrl the latest url of the {@link ResultEntity}
     * @param name the name of the {@link ResultEntity}
     * @param fixedSize the fixed size of the {@link ResultEntity}
     * @param newSize the new size of the {@link ResultEntity}
     * @param totalSize the total size of the {@link ResultEntity}
     * @param qualityGateStatus the quality gate status of the {@link ResultEntity}
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
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

    /**
     * Returns the list of {@link ReportEntity}s from the {@link ResultEntity}.
     *
     * @return the list of {@link ReportEntity}s
     */
    public List<ReportEntity> getReports() {
        return reports;
    }

    /**
     * Setter to set the list of {@link ReportEntity} of the {@link ResultEntity}.
     *
     * @param reports the list of {@link ReportEntity}
     */
    public void setReports(List<ReportEntity> reports) {
        this.reports = reports;
    }

    /**
     * Returns the fixed size of the {@link ResultEntity}.
     *
     * @return the fixed size
     */
    public int getFixedSize() {
        return fixedSize;
    }

    /**
     * Setter to set the fixed size of the {@link ResultEntity}.
     *
     * @param fixedSize the fixed size
     */
    public void setFixedSize(int fixedSize) {
        this.fixedSize = fixedSize;
    }

    /**
     * Returns the new size of the {@link ResultEntity}.
     *
     * @return the new size
     */
    public int getNewSize() {
        return newSize;
    }

    /**
     * Setter to set the new size of the {@link ResultEntity}.
     *
     * @param newSize the new size
     */
    public void setNewSize(int newSize) {
        this.newSize = newSize;
    }

    /**
     * Returns the quality gate status of the {@link ResultEntity}.
     *
     * @return the quality gate status
     */
    public String getQualityGateStatus() {
        return qualityGateStatus;
    }

    /**
     * Setter to set the quality gate status of the {@link ResultEntity}.
     *
     * @param qualityGateStatus the quality gate status
     */
    public void setQualityGateStatus(String qualityGateStatus) {
        this.qualityGateStatus = qualityGateStatus;
    }

    /**
     * Returns the total size of the {@link ResultEntity}.
     *
     * @return the total size
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Setter to set the total size of the {@link ResultEntity}.
     *
     * @param totalSize the total size
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * Returns the error messages of the {@link ResultEntity}.
     *
     * @return the error messages as list
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Setter to set the error messages of the {@link ResultEntity}.
     *
     * @param errorMessages the error messages
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * Returns the info messages of the {@link ResultEntity}.
     *
     * @return the info messages
     */
    public List<String> getInfoMessages() {
        return infoMessages;
    }

    /**
     * Setter to set the info messages of the {@link ResultEntity}.
     *
     * @param infoMessages the info messages
     */
    public void setInfoMessages(List<String> infoMessages) {
        this.infoMessages = infoMessages;
    }

    /**
     * Returns the name of the {@link ResultEntity}.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter to set the name of the {@link ResultEntity}.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the id of the {@link ResultEntity}.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter to set the id of the {@link ResultEntity}.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the warning id of the {@link ResultEntity}.
     *
     * @return the warning id
     */
    public String getWarningId() {
        return warningId;
    }

    /**
     * Setter to set the waring id of the {@link ResultEntity}.
     *
     * @param warningId the warning id
     */
    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    /**
     * Returns the latest url of the {@link ResultEntity}.
     *
     * @return the latest url
     */
    public String getLatestUrl() {
        return latestUrl;
    }

    /**
     * Setter to set the latest url of the {@link ResultEntity}.
     *
     * @param latestUrl the latest url
     */
    public void setLatestUrl(String latestUrl) {
        this.latestUrl = latestUrl;
    }

    /**
     * Returns the associated {@link BuildEntity} of the {@link ResultEntity}.
     *
     * @return the {@link BuildEntity}
     */
    public BuildEntity getBuildEntity() {
        return buildEntity;
    }

    /**
     * Setter to set the {@link BuildEntity} of the {@link ResultEntity}.
     *
     * @param buildEntity the {@link BuildEntity}
     */
    public void setBuildEntity(BuildEntity buildEntity) {
        this.buildEntity = buildEntity;
    }

    /**
     * Adds a {@link ReportEntity} to the {@link ResultEntity}.
     *
     * @param reportEntity the {@link ReportEntity}
     * @return the added {@link ReportEntity}
     */
    public ReportEntity addReportEntity(ReportEntity reportEntity) {
        getReports().add(reportEntity);
        reportEntity.setResultEntity(this);

        return  reportEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultEntity that = (ResultEntity) o;

        if (id != that.id) {
            return false;
        }
        if (fixedSize != that.fixedSize) {
            return false;
        }
        if (newSize != that.newSize) {
            return false;
        }
        if (totalSize != that.totalSize) {
            return false;
        }
        if (!latestUrl.equals(that.latestUrl)) {
            return false;
        }
        if (!warningId.equals(that.warningId)) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        if (!qualityGateStatus.equals(that.qualityGateStatus)) {
            return false;
        }
        if (!errorMessages.equals(that.errorMessages)) {
            return false;
        }
        if (!infoMessages.equals(that.infoMessages)) {
            return false;
        }
        return reports.equals(that.reports);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + latestUrl.hashCode();
        result = 31 * result + warningId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + fixedSize;
        result = 31 * result + newSize;
        result = 31 * result + qualityGateStatus.hashCode();
        result = 31 * result + totalSize;
        result = 31 * result + errorMessages.hashCode();
        result = 31 * result + infoMessages.hashCode();
        result = 31 * result + reports.hashCode();
        return result;
    }
}
