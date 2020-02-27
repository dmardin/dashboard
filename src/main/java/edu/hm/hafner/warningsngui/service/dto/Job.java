package edu.hm.hafner.warningsngui.service.dto;

import java.util.ArrayList;
import java.util.List;

public class Job {

    private int id;
    private String name;
    private String url;
    private String color;
    private String lastBuildStatus;
    private List<Build> builds;

    public Job(int id, String name, String url, String lastBuildStatus) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.lastBuildStatus = lastBuildStatus;
        this.builds = new ArrayList<>();
    }

    Job() {}

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

    public List<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    public String getLastBuildStatus() {
        return lastBuildStatus;
    }

    public void setLastBuildStatus(String lastBuildStatus) {
        this.lastBuildStatus = lastBuildStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Build addBuild(Build build) {
        getBuilds().add(build);
        build.setJob(this);

        return build;
    }
}
