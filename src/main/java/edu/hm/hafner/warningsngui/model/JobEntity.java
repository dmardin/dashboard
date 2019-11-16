package edu.hm.hafner.warningsngui.model;


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
    private String color;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<BuildEntity> getBuildEntities() {
        return buildEntities;
    }

    public void setBuildEntities(List<BuildEntity> buildEntities) {
        this.buildEntities = buildEntities;
    }
}
