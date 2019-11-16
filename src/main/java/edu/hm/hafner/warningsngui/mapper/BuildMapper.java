package edu.hm.hafner.warningsngui.mapper;

import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Job;
import edu.hm.hafner.warningsngui.model.BuildEntity;
import edu.hm.hafner.warningsngui.model.JobEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BuildMapper {

    public static Build map(BuildEntity buildEntity, Job job) {
        Build build = new Build();
        build.setNumber(buildEntity.getNumber());
        build.setUrl(buildEntity.getUrl());
        build.setJob(job);
        build.setResults(ResultMapper.map(buildEntity.getResultEntities(), build));
        return build;
    }

    public static List<Build> map(List<BuildEntity> buildEntities, Job job) {
        return buildEntities.stream().map(buildEntity -> map(buildEntity, job)).collect(Collectors.toList());
    }

    public static BuildEntity mapToEntity(Build build, JobEntity jobEntity){
        BuildEntity buildEntity = new BuildEntity();
        buildEntity.setNumber(build.getNumber());
        buildEntity.setUrl(build.getUrl());
        buildEntity.setJobEntity(jobEntity);
        buildEntity.setResultEntities(ResultMapper.mapToEntities(build.getResults(), buildEntity));
        //buildEntity.setJobEntity(JobMapper.mapToEntity(build.getJob()));
        //buildEntity.setResultEntities();

        return buildEntity;
    }

    public static List<BuildEntity> mapToEntities(List<Build> builds, JobEntity jobEntity) {
        return builds.stream().map(build -> mapToEntity(build, jobEntity)).collect(Collectors.toList());
    }
}
