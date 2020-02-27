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
        if(jobEntity != null) {
            Job job = new Job(
                    jobEntity.getId(),
                    jobEntity.getName(),
                    jobEntity.getUrl(),
                    jobEntity.getLastBuildStatus()
            );
            job.setBuilds(BuildMapper.map(jobEntity.getBuildEntities(), job));

            return job;
        }
        else
            return null; //TODO other solution?
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
        JobEntity jobEntity = new JobEntity(
                job.getId(),
                job.getName(),
                job.getUrl(),
                job.getLastBuildStatus()
        );
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
