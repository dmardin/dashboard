package edu.hm.hafner.warningsngui.db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public BuildEntity() {
        this.jobEntity = new JobEntity();
        this.resultEntities = new ArrayList<>();
    }

    public BuildEntity(int id, int number, String url) {
        this.id = id;
        this.number = number;
        this.url = url;
        this.jobEntity = new JobEntity();
        this.resultEntities = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JobEntity getJobEntity() {
        return jobEntity;
    }

    public void setJobEntity(JobEntity jobEntity) {
        this.jobEntity = jobEntity;
    }

    public List<ResultEntity> getResultEntities() {
        return resultEntities;
    }

    public void setResultEntities(List<ResultEntity> resultEntities) {
        this.resultEntities = resultEntities;
    }
}
