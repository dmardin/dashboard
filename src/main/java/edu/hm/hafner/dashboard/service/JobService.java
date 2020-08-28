package edu.hm.hafner.dashboard.service;

import edu.hm.hafner.dashboard.db.JobEntityService;
import edu.hm.hafner.dashboard.db.model.JobEntity;
import edu.hm.hafner.dashboard.service.dto.Job;
import edu.hm.hafner.dashboard.service.mapper.Mapper;
import edu.hm.hafner.dashboard.service.table.job.JobRepositoryStatistics;
import edu.hm.hafner.dashboard.service.table.job.JobViewTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle {@link Job}s between the ui and database.
 */
@Service
class JobService {
    private JobEntityService jobEntityService;

    /**
     * Creates a new instance of {@link JobService}.
     *
     * @param jobEntityService the entity service for jobs
     */
    @Autowired
    public JobService(JobEntityService jobEntityService) {
        this.jobEntityService = jobEntityService;
    }

    /**
     * Returns all Jobs.
     *
     * @return the jobs
     */
    public List<Job> getAllJobs() {
        return Mapper.map(jobEntityService.findAll());
    }

    /**
     * Searches for a job by its name.
     *
     * @param name the job name
     * @return the job
     */
    public Job findJobByName(String name) {
        JobEntity jobEntity = jobEntityService.findJobByName(name);
        if (jobEntity != null) {
            return Mapper.map(jobEntity);
        }
        return null;
    }

    /**
     * Saves a given list of {@link Job}.
     *
     * @param jobs the {@link Job}s to save
     * @return the saves jobs
     */
    public List<Job> saveAll(List<Job> jobs) {
        List<JobEntity> jobEntities = Mapper.mapToEntities(jobs);
        List<JobEntity> savedJobs = jobEntityService.saveAll(jobEntities);
        return Mapper.map(savedJobs);
    }

    /**
     * Method to convert a list of jobs to the needed format of table rows.
     *
     * @param jobs the jobs to convert
     * @return converted table rows
     */
    public List<Object> convertRowsForTheJobViewTable(List<Job> jobs) {
        JobRepositoryStatistics jobRepositoryStatistics = new JobRepositoryStatistics();
        jobRepositoryStatistics.addAll(jobs);
        JobViewTable jobViewTable = new JobViewTable(jobRepositoryStatistics);

        return jobViewTable.getTableRows("jobs");
    }

    /**
     * Creates a new {@link JobViewTable}.
     *
     * @return the JobViewTable
     */
    public JobViewTable createJobViewTable() {
        return new JobViewTable(new JobRepositoryStatistics());
    }
}
