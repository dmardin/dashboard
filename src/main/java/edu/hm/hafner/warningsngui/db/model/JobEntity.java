package edu.hm.hafner.warningsngui.db.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastBuildStatus() {
        return lastBuildStatus;
    }

    public void setLastBuildStatus(String lastBuildStatus) {
        this.lastBuildStatus = lastBuildStatus;
    }

    public List<BuildEntity> getBuildEntities() {
        return buildEntities;
    }

    public void setBuildEntities(List<BuildEntity> buildEntities) {
        this.buildEntities = buildEntities;
    }
}
