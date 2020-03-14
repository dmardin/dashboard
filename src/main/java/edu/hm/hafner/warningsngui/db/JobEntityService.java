package edu.hm.hafner.warningsngui.db;

import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.db.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle the interactions for {@link JobEntity}s with the database by using the {@link JobRepository}.
 *
 * @author Deniz Mardin
 */
@Service
public class JobEntityService {
    JobRepository jobRepository;

    @Autowired
    JobEntityService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Fetches all {@link JobEntity}s form the database.
     *
     * @return the fetched {@link JobEntity}s
     */
    public List<JobEntity> findAll() {
        return jobRepository.findAll();
    }

    /**
     * Searches for a job by its name.
     *
     * @param name the name of the job
     * @return the job
     */
    public JobEntity findJobByName(String name) {
        return jobRepository.findByName(name);
    }

    /**
     * Saves all given {@link JobEntity}s.
     *
     * @param jobEntities the {@link JobEntity}s to save
     * @return the saved {@link JobEntity}s
     */
    public List<JobEntity> saveAll(List<JobEntity> jobEntities) {
        return jobRepository.saveAll(jobEntities);
    }
}
