package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Enables the conversion from a {@link Build} to a {@link BuildEntity} and visa versa.
 * Furthermore a list of {@link Build}s can be converted to a list ob {@link BuildEntity}s and visa versa.
 *
 * @author Deniz Mardin
 */
public class BuildMapper {

    /**
     * Provides the mapping from a given {@link BuildEntity} to {@link Build}.
     *
     * @param buildEntity the {@link BuildEntity} to convert
     * @param job the corresponding {@link Job} for the {@link Build}
     * @return the converted {@link Build}
     */
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

    /**
     * Provides the mapping from a given list of {@link BuildEntity}s to a list of {@link Build}s.
     *
     * @param buildEntities the list of {@link BuildEntity}s to convert
     * @param job the corresponding {@link Job} for the {@link Build}s
     * @return the list of converted {@link Build}
     */
    public static List<Build> map(List<BuildEntity> buildEntities, Job job) {
        return buildEntities.stream().map(buildEntity -> map(buildEntity, job)).collect(Collectors.toList());
    }

    /**
     * Provides the mapping from a given {@link Build} to {@link BuildEntity}.
     *
     * @param build the {@link Build} to convert
     * @param jobEntity the corresponding {@link JobEntity} for the {@link BuildEntity}
     * @return the converted {@link BuildEntity}
     */
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

    /**
     * Provides the mapping from a given list of {@link Build}s to a list of {@link BuildEntity}s.
     *
     * @param builds the list of {@link Build}s to convert
     * @param jobEntity the corresponding {@link JobEntity} for the list of {@link BuildEntity}s
     * @return the list of converted {@link BuildEntity}
     */
    public static List<BuildEntity> mapToEntities(List<Build> builds, JobEntity jobEntity) {
        return builds.stream().map(build -> mapToEntity(build, jobEntity)).collect(Collectors.toList());
    }
}
