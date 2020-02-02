package edu.hm.hafner.warningsngui.db;

import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.db.mapper.JobMapper;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.db.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobEntityService {

    @Autowired
    JobRepository jobRepository;

    public List<Job> findAll() {
        List<JobEntity> jobEntities = jobRepository.findAll();
        return JobMapper.map(jobEntities);
    }

    public Job findJobByName(String name) {
        JobEntity jobEntity = jobRepository.findByName(name);
        return JobMapper.map(jobEntity);
    }

    public List<Job> saveAll(List<Job> jobs) {
        List<JobEntity> jobEntities = jobRepository.saveAll(JobMapper.mapToEntities(jobs));
        return JobMapper.map(jobEntities);
    }

    public Job save (Job job) {
        JobEntity jobEntity = jobRepository.save(JobMapper.mapToEntity(job));
        return JobMapper.map(jobEntity);
    }
}