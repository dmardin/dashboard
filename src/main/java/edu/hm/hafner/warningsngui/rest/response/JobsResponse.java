package edu.hm.hafner.warningsngui.rest.response;

import edu.hm.hafner.warningsngui.dto.Job;

public class JobsResponse {
    private Job[] jobs;

    public Job[] getJobs() {
        return jobs;
    }

    public void setJobs(Job[] jobs) {
        this.jobs = jobs;
    }
}
