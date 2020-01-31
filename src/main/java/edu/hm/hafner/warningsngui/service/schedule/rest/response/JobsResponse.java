package edu.hm.hafner.warningsngui.service.schedule.rest.response;

import edu.hm.hafner.warningsngui.service.dto.Job;

public class JobsResponse {
    private Job[] jobs;

    public Job[] getJobs() {
        return jobs;
    }

    public void setJobs(Job[] jobs) {
        this.jobs = jobs;
    }
}
