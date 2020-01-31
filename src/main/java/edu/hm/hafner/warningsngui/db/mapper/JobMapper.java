package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.db.model.JobEntity;

import java.util.List;
import java.util.stream.Collectors;

public class JobMapper {

    public static Job map(JobEntity jobEntity) {
        Job job = new Job();
        job.setName(jobEntity.getName());
        job.setLastBuildStatus(jobEntity.getLastBuildStatus());
        job.setUrl(jobEntity.getUrl());
        job.setBuilds(BuildMapper.map(jobEntity.getBuildEntities(), job));
        return job;
    }

    public static List<Job> map(List<JobEntity> jobEntities){
        return jobEntities.stream().map(JobMapper::map).collect(Collectors.toList());
    }

    public static JobEntity mapToEntity(Job job) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(job.getName());
        jobEntity.setLastBuildStatus(job.getLastBuildStatus());
        jobEntity.setUrl(job.getUrl());
        jobEntity.setBuildEntities(BuildMapper.mapToEntities(job.getBuilds(), jobEntity));
        return jobEntity;
    }

    public static List<JobEntity> mapToEntities(List<Job> jobs) {
        return jobs.stream().map(JobMapper::mapToEntity).collect(Collectors.toList());
    }
}
