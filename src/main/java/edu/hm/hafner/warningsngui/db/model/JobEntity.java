package edu.hm.hafner.warningsngui.db.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO to store a {@link JobEntity} to the database.
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
    private List<BuildEntity> buildEntities;

    /**
     * Creates a new instance of {@link JobEntity}.
     */
    protected JobEntity() {
        this.buildEntities = new ArrayList<>();
    }

    /**
     * Creates a new instance of {@link JobEntity}.
     *
     * @param id the id of the {@link JobEntity}
     * @param name the name of the {@link JobEntity}
     * @param url the url of the {@link JobEntity}
     * @param lastBuildStatus the last build status of the {@link JobEntity}
     */
    public JobEntity(int id, String name, String url, String lastBuildStatus){
        this.id = id;
        this.name = name;
        this.url = url;
        this.lastBuildStatus = lastBuildStatus;
        this.buildEntities = new ArrayList<>();
    }

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

    /**
     * Adds a {@link BuildEntity} to the {@link JobEntity}.
     *
     * @param buildEntity the {@link BuildEntity}
     * @return the added {@link BuildEntity}
     */
    public BuildEntity addBuildEntity(BuildEntity buildEntity){
        getBuildEntities().add(buildEntity);
        buildEntity.setJobEntity(this);

        return buildEntity;
    }
}
