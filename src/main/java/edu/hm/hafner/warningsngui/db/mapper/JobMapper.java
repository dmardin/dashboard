package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Job;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides the Mapping between {@link Job} and {@link JobEntity}.
 *
 * @author Deniz Mardin
 */
public class JobMapper {

    /**
     * Converts a {@link JobEntity} to a {@link Job}.
     *
     * @param jobEntity the job entity
     * @return the converted job
     */
    public static Job map(JobEntity jobEntity) {
        Job job = new Job();
        job.setName(jobEntity.getName());
        job.setLastBuildStatus(jobEntity.getLastBuildStatus());
        job.setUrl(jobEntity.getUrl());
        job.setBuilds(BuildMapper.map(jobEntity.getBuildEntities(), job));
        return job;
    }

    /**
     * Converts a list of {@link JobEntity}s to a list {@link Job}s.
     *
     * @param jobEntities the job entities
     * @return the converted jobs
     */
    public static List<Job> map(List<JobEntity> jobEntities){
        return jobEntities.stream().map(JobMapper::map).collect(Collectors.toList());
    }

    /**
     * Converts a {@link Job} to a {@link JobEntity}.
     *
     * @param job the job
     * @return the converted job entity
     */
    public static JobEntity mapToEntity(Job job) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(job.getName());
        jobEntity.setLastBuildStatus(job.getLastBuildStatus());
        jobEntity.setUrl(job.getUrl());
        jobEntity.setBuildEntities(BuildMapper.mapToEntities(job.getBuilds(), jobEntity));
        return jobEntity;
    }

    /**
     * Converts a list of {@link Job}s to a list {@link JobEntity}s.
     *
     * @param jobs the jobs
     * @return the job entities
     */
    public static List<JobEntity> mapToEntities(List<Job> jobs) {
        return jobs.stream().map(JobMapper::mapToEntity).collect(Collectors.toList());
    }
}
