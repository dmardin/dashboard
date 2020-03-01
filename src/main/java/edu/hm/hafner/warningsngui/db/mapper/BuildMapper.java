package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.ResultEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Result;

/**
 * Enables the conversion from a {@link Build} to a {@link BuildEntity} and visa versa.
 *
 * @author Deniz Mardin
 */
public class BuildMapper {

    /**
     * Provides the mapping from a given {@link BuildEntity} to {@link Build}.
     *
     * @param buildEntity the {@link BuildEntity} to convert
     * @return the converted {@link Build}
     */
    public static Build map(BuildEntity buildEntity) {
        Build build = new Build(
                buildEntity.getId(),
                buildEntity.getNumber(),
                buildEntity.getUrl()
        );
        buildEntity.getResultEntities().forEach(resultEntity -> {
            Result result = ResultMapper.map(resultEntity);
            build.addResult(result);
        });

        return build;
    }

    /**
     * Provides the mapping from a given {@link Build} to {@link BuildEntity}.
     *
     * @param build the {@link Build} to convert
     * @return the converted {@link BuildEntity}
     */
    public static BuildEntity mapToEntity(Build build){
        BuildEntity buildEntity = new BuildEntity(
                build.getId(),
                build.getNumber(),
                build.getUrl()
        );
        build.getResults().forEach(result ->  {
            ResultEntity resultEntity = ResultMapper.mapToEntity(result);
            buildEntity.addResultEntity(resultEntity);
        });

        return buildEntity;
    }
}
