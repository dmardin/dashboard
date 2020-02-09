package edu.hm.hafner.warningsngui.db;

import edu.hm.hafner.warningsngui.db.mapper.JobMapper;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.db.repository.JobRepository;
import edu.hm.hafner.warningsngui.service.dto.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle the interactions with the database for a job.
 *
 * @author Deniz Mardin
 */
@Service
public class JobEntityService {

    @Autowired
    JobRepository jobRepository;

    /**
     * Fetches all jobs form the database.
     *
     * @return the jobs
     */
    public List<Job> findAll() {
        List<JobEntity> jobEntities = jobRepository.findAll();
        return JobMapper.map(jobEntities);
    }

    /**
     * Searches for a job by its name.
     *
     * @param name the name of the job
     * @return the job
     */
    public Job findJobByName(String name) {
        JobEntity jobEntity = jobRepository.findByName(name);
        return JobMapper.map(jobEntity);
    }

    /**
     * Saves all jobs.
     *
     * @param jobs the jobs to save
     * @return the saved jobs
     */
    public List<Job> saveAll(List<Job> jobs) {
        List<JobEntity> jobEntities = jobRepository.saveAll(JobMapper.mapToEntities(jobs));
        return JobMapper.map(jobEntities);
    }

    /**
     * Saves a specific job.
     *
     * @param job the job
     * @return the saved job
     */
    public Job save (Job job) {
        JobEntity jobEntity = jobRepository.save(JobMapper.mapToEntity(job));
        return JobMapper.map(jobEntity);
    }
}
