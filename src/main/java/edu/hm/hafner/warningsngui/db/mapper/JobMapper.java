package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Job;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Enables the conversion from a {@link Job} to a {@link JobEntity} and visa versa.
 * Furthermore a list of {@link Job}s can be converted to a list ob {@link JobEntity}s and visa versa.
 *
 * @author Deniz Mardin
 */
public class JobMapper {

    /**
     * Converts a {@link JobEntity} to a {@link Job}.
     *
     * @param jobEntity the {@link JobEntity}
     * @return the converted {@link Job}
     */
    public static Job map(JobEntity jobEntity) {
        if(jobEntity != null) {
            Job job = new Job(
                    jobEntity.getId(),
                    jobEntity.getName(),
                    jobEntity.getUrl(),
                    jobEntity.getLastBuildStatus()
            );
            jobEntity.getBuildEntities().forEach(buildEntity -> job.addBuild(BuildMapper.map(buildEntity)));

            return job;
        }
        else
            return null; //TODO other solution?
    }

    /**
     * Converts a list of {@link JobEntity}s to a list of {@link Job}s.
     *
     * @param jobEntities the list of {@link JobEntity}s
     * @return the converted list of  {@link Job}s
     */
    public static List<Job> map(List<JobEntity> jobEntities){
        return jobEntities.stream().map(JobMapper::map).collect(Collectors.toList());
    }

    /**
     * Converts a {@link Job} to a {@link JobEntity}.
     *
     * @param job the {@link Job}
     * @return the converted {@link JobEntity}
     */
    public static JobEntity mapToEntity(Job job) {
        JobEntity jobEntity = new JobEntity(
                job.getId(),
                job.getName(),
                job.getUrl(),
                job.getLastBuildStatus()
        );
        job.getBuilds().forEach(build -> {
            BuildEntity buildEntity = BuildMapper.mapToEntity(build);
            jobEntity.addBuildEntity(buildEntity);
        });

        return jobEntity;
    }

    /**
     * Converts a list of {@link Job}s to a list of {@link JobEntity}s.
     *
     * @param jobs the {@link Job}s
     * @return the converted list of {@link JobEntity}s
     */
    public static List<JobEntity> mapToEntities(List<Job> jobs) {
        return jobs.stream().map(JobMapper::mapToEntity).collect(Collectors.toList());
    }
}
