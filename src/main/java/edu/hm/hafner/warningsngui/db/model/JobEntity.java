package edu.hm.hafner.warningsngui.db.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity that represents a job.
 *
 * @author Deniz Mardin
 */
@Entity
@Table(name="job")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String url;
    private String lastBuildStatus;
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "jobEntity", cascade = CascadeType.ALL)
    private List<BuildEntity> buildEntities = new ArrayList<>();

    /**
     * Returns the id of the {@link JobEntity}.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the id of the {@link JobEntity}.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the {@link JobEntity}.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the {@link JobEntity}.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the url of the {@link JobEntity}.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for the url of the {@link JobEntity}.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the last build status of the {@link JobEntity}.
     *
     * @return the last build status
     */
    public String getLastBuildStatus() {
        return lastBuildStatus;
    }

    /**
     * Setter for the last build status of the {@link JobEntity}.
     *
     * @param lastBuildStatus the last build status
     */
    public void setLastBuildStatus(String lastBuildStatus) {
        this.lastBuildStatus = lastBuildStatus;
    }

    /**
     * Returns the corresponding {@link BuildEntity}s of the {@link JobEntity}.
     *
     * @return the build entities
     */
    public List<BuildEntity> getBuildEntities() {
        return buildEntities;
    }

    /**
     * Setter to set a list of {@link BuildEntity}s for a {@link JobEntity}.
     *
     * @param buildEntities the build entities
     */
    public void setBuildEntities(List<BuildEntity> buildEntities) {
        this.buildEntities = buildEntities;
    }
}
