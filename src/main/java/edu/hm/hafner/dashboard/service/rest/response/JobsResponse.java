package edu.hm.hafner.dashboard.service.rest.response;

import edu.hm.hafner.dashboard.service.dto.Job;

/**
 * Represents a {@link Job}s response from the Jenkins API.
 */
public class JobsResponse {
    private Job[] jobs;

    /**
     * Returns the response of {@link Job}s.
     *
     * @return the {@link Job}s
     */
    public Job[] getJobs() {
        return jobs;
    }

    /**
     * Setter to set the response of {@link Job}s.
     *
     * @param jobs the {@link Job}s
     */
    public void setJobs(Job[] jobs) {
        this.jobs = jobs;
    }
}
