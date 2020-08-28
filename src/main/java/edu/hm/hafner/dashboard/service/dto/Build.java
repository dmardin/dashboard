package edu.hm.hafner.dashboard.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO that represents a {@link Build}.
 */
public class Build {
    private int id;
    private int number;
    private String url;
    @JsonIgnoreProperties("builds")
    private Job job;
    private List<Result> results;

    /**
     * Creates a new instance of a {@link Build}.
     */
    public Build() {
        this.job = new Job();
        this.results = new ArrayList<>();
    }

    /**
     * Creates a new instance of a {@link Build}.
     *
     * @param id the id of the {@link Build}
     * @param number the build number of the {@link Build}
     * @param url the url of the {@link Build}
     */
    public Build(int id, int number, String url) {
        this.id = id;
        this.number = number;
        this.url = url;
        this.job = new Job();
        this.results = new ArrayList<>();
    }

    /**
     * Returns the {@link Result}s of the {@link Build}.
     *
     * @return the {@link Result}s
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * Setter to set the {@link Result}s of the {@link Build}.
     *
     * @param results the {@link Result}s
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * Returns the build number of the {@link Build}.
     *
     * @return the build number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setter to set the build number of the {@link Build}.
     *
     * @param number the build number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns the url of the {@link Build}.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter to set the url of the {@link Build}.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the {@link Job} of the {@link Build}.
     *
     * @return the {@link Job}
     */
    public Job getJob() {
        return job;
    }

    /**
     * Setter to set the {@link Job} of the {@link Build}.
     *
     * @param job the {@link Job}
     */
    public void setJob(Job job) {
        this.job = job;
    }

    /**
     * Returns the id of the {@link Build}.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter to set the id of the {@link Build}.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Adds a {@link Result} to the {@link Build}.
     *
     * @param result the {@link Result}
     * @return the added {@link Result}
     */
    public Result addResult(Result result) {
        getResults().add(result);
        result.setBuild(this);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Build build = (Build) o;

        if (id != build.id) {
            return false;
        }
        if (number != build.number) {
            return false;
        }
        if (!url.equals(build.url)) {
            return false;
        }
        return results.equals(build.results);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        result = 31 * result + url.hashCode();
        result = 31 * result + results.hashCode();
        return result;
    }
}
