package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle interaction in the {@link AppStartupRunner} by using the services {@link JobService}, {@link BuildService}, {@link ResultService}.
 *
 * @author Deniz Mardin
 */
@Service
public class AppStartupService {
    private final JobService jobService;
    private final BuildService buildService;

    @Autowired
    public AppStartupService(JobService jobService, BuildService buildService) {
        this.jobService = jobService;
        this.buildService = buildService;
    }

    /**
     * Method to find a {@link Job} by name.
     *
     * @param jobName the job name
     * @return the {@link Job}
     */
    public Job findJobByName(String jobName) {
        return jobService.findJobByName(jobName);
    }

    /**
     * Saves a given list of {@link Job}.
     *
     * @param jobs the {@link Job}s to save
     * @return the saves jobs
     */
    public List<Job> saveNewJobs(List<Job> jobs) {
        return jobService.saveAll(jobs);
    }

    /**
     * Determine the latest Build number from a Job.
     *
     * @param job the given Job
     * @return the latest build number
     */
    public int getLatestBuildNumberFromJob(Job job) {
        return buildService.getLatestBuild(job).getNumber();
    }

    /**
     * Saves a given list of {@link Build} by adding it to an existing {@link Job}..
     *
     * @param fetchedJob the fetched Job
     * @param builds     the builds to add
     * @return the saved builds
     */
    public List<Build> saveNewBuildsFromJob(Job fetchedJob, List<Build> builds) {
        return buildService.saveAll(fetchedJob, builds);
    }
}
