package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;

import java.util.List;
import java.util.stream.Collectors;

public class BuildMapper {

    public static Build map(BuildEntity buildEntity, Job job) {
        Build build = new Build(
                buildEntity.getId(),
                buildEntity.getNumber(),
                buildEntity.getUrl()
        );
        build.setJob(job);
        build.setResults(ResultMapper.map(buildEntity.getResultEntities(), build));

        return build;
    }

    public static List<Build> map(List<BuildEntity> buildEntities, Job job) {
        return buildEntities.stream().map(buildEntity -> map(buildEntity, job)).collect(Collectors.toList());
    }

    public static BuildEntity mapToEntity(Build build, JobEntity jobEntity){
        BuildEntity buildEntity = new BuildEntity(
                build.getId(),
                build.getNumber(),
                build.getUrl()
        );
        buildEntity.setJobEntity(jobEntity);
        buildEntity.setResultEntities(ResultMapper.mapToEntities(build.getResults(), buildEntity));

        return buildEntity;
    }

    public static List<BuildEntity> mapToEntities(List<Build> builds, JobEntity jobEntity) {
        return builds.stream().map(build -> mapToEntity(build, jobEntity)).collect(Collectors.toList());
    }
}
