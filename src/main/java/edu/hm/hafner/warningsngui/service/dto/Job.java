package edu.hm.hafner.warningsngui.service.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO that represents a {@link Job}.
 */
public class Job {
    private int id;
    private String name;
    private String url;
    private String color;
    private String lastBuildStatus;
    private List<Build> builds;

    /**
     * Creates a new instance of a {@link Job}.
     */
    Job() {
        this.builds = new ArrayList<>();
    }

    /**
     * Creates a new instance of a {@link Job}.
     *
     * @param id the id of the {@link Job}
     * @param name the name of the {@link Job}
     * @param url the url of the {@link Job}
     * @param lastBuildStatus the last build status of the {@link Job}
     */
    public Job(int id, String name, String url, String lastBuildStatus) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.lastBuildStatus = lastBuildStatus;
        this.builds = new ArrayList<>();
    }

    /**
     * Returns the name of the {@link Job}.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter to set the name of the {@link Job}.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the url of the {@link Job}.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter to set the url of the {@link Job}.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the color of the {@link Job}.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter to set the color of the {@link Job}.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns the {@link Build}s of the {@link Job}.
     *
     * @return the {@link Build}s
     */
    public List<Build> getBuilds() {
        return builds;
    }

    /**
     * Setter to set the {@link Build}s of the {@link Job}
     *
     * @param builds the {@link Build}s
     */
    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    /**
     * Returns the last build status of the {@link Job}.
     *
     * @return the last build status
     */
    public String getLastBuildStatus() {
        return lastBuildStatus;
    }

    /**
     * Setter to set the last build status of the {@link Job}.
     *
     * @param lastBuildStatus the last build status
     */
    public void setLastBuildStatus(String lastBuildStatus) {
        this.lastBuildStatus = lastBuildStatus;
    }

    /**
     * Returns the id of the {@link Job}.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter to set the id of the {@link Job}.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Adds a given {@link Build} to the {@link Job}.
     *
     * @param build the {@link Build}
     * @return the added {@link Build}
     */
    public Build addBuild(Build build) {
        getBuilds().add(build);
        build.setJob(this);

        return build;
    }
}
