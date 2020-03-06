package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Job;

import java.util.ArrayList;
import java.util.List;

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
        Job job = new Job(
            jobEntity.getId(),
            jobEntity.getName(),
            jobEntity.getUrl(),
            jobEntity.getLastBuildStatus()
        );
        jobEntity.getBuildEntities().forEach(buildEntity -> {
            job.addBuild(BuildMapper.map(buildEntity));
        });

        return job;
    }

    /**
     * Converts a list of {@link JobEntity}s to a list of {@link Job}s.
     *
     * @param jobEntities the list of {@link JobEntity}s
     * @return the converted list of  {@link Job}s
     */
    public static List<Job> map(List<JobEntity> jobEntities){
        List<Job> list = new ArrayList<>();
        for (JobEntity jobEntity : jobEntities) {
            Job map = map(jobEntity);
            list.add(map);
        }
        return list;
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
        List<JobEntity> list = new ArrayList<>();
        for (Job job : jobs) {
            JobEntity jobEntity = mapToEntity(job);
            list.add(jobEntity);
        }
        return list;
    }
}
