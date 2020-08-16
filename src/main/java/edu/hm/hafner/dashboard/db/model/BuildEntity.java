package edu.hm.hafner.dashboard.db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO to store a {@link BuildEntity} to the database.
 *
 * @author Deniz Mardin
 */
@Entity
@Table(name="build")
public class BuildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int number;
    private String url;
    @ManyToOne
    private JobEntity jobEntity;
    @OneToMany(mappedBy = "buildEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ResultEntity> resultEntities;

    /**
     * Creates a new instance of {@link BuildEntity}.
     */
    protected BuildEntity() {
        this.jobEntity = new JobEntity();
        this.resultEntities = new ArrayList<>();
    }

    /**
     * Creates a new instance of {@link BuildEntity}.
     *
     * @param id the id of the {@link BuildEntity}
     * @param number the build number of the {@link BuildEntity}
     * @param url the url of the {@link BuildEntity}
     */
    public BuildEntity(int id, int number, String url) {
        this.id = id;
        this.number = number;
        this.url = url;
        this.jobEntity = new JobEntity();
        this.resultEntities = new ArrayList<>();
    }

    /**
     * Returns the id of the {@link BuildEntity}.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the id of the {@link BuildEntity}.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the build number of the {@link BuildEntity}.
     *
     * @return the build number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setter for the build number of the {@link BuildEntity}.
     *
     * @param number the build number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns the url of the {@link BuildEntity}.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for the url of the {@link BuildEntity}.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the associated {@link JobEntity} from the {@link BuildEntity}.
     *
     * @return the {@link JobEntity}
     */
    public JobEntity getJobEntity() {
        return jobEntity;
    }

    /**
     * Setter to set the associated {@link JobEntity} for the {@link BuildEntity}.
     *
     * @param jobEntity the {@link JobEntity}
     */
    public void setJobEntity(JobEntity jobEntity) {
        this.jobEntity = jobEntity;
    }

    /**
     * Returns the {@link ResultEntity}s for the {@link BuildEntity}.
     *
     * @return the {@link ResultEntity}s
     */
    public List<ResultEntity> getResultEntities() {
        return resultEntities;
    }

    /**
     * Setter for the {@link ResultEntity}s for the {@link BuildEntity}.
     *
     * @param resultEntities the {@link ResultEntity}s
     */
    public void setResultEntities(List<ResultEntity> resultEntities) {
        this.resultEntities = resultEntities;
    }

    /**
     * Adds a {@link ResultEntity} to the {@link BuildEntity}.
     *
     * @param resultEntity the {@link ResultEntity}
     * @return the added {@link ResultEntity}
     */
    public ResultEntity addResultEntity(ResultEntity resultEntity) {
        getResultEntities().add(resultEntity);
        resultEntity.setBuildEntity(this);

        return resultEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildEntity that = (BuildEntity) o;

        if (id != that.id) return false;
        if (number != that.number) return false;
        if (!url.equals(that.url)) return false;
        return resultEntities.equals(that.resultEntities);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        result = 31 * result + url.hashCode();
        result = 31 * result + resultEntities.hashCode();
        return result;
    }
}
