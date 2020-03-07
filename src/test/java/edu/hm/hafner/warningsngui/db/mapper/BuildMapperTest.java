package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.ReportEntity;
import edu.hm.hafner.warningsngui.db.model.ResultEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Result;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Test the Class {@link BuildMapper}.
 *
 * @author Deniz Mardin
 */
class BuildMapperTest {

    private static final String JOB_NAME = "jobName";
    private static final String OTHER_JOB_NAME = "otherJobName";
    private static final int FIVE_RESULTS = 5;
    private static final int NO_RESULTS = 0;

    @Test
    void shouldMapBuildEntityToBuildWithoutResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            Build build = BuildMapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldNotMapBuildEntityToBuildWithoutResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            Build build = BuildMapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(2, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            build = BuildMapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 2, JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            build = BuildMapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 1, OTHER_JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldMapBuildEntityToBuildWithResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            Build build = BuildMapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldMapNotBuildEntityToBuildWithResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            Build build = BuildMapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(2, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            build = BuildMapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 2, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            build = BuildMapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 1, OTHER_JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldMapBuildToBuildEntityWithoutResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            BuildEntity buildEntity = BuildMapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isEqualTo(expectedBuildEntity);
        });
    }

    @Test
    void shouldNotMapBuildToBuildEntityWithoutResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            BuildEntity buildEntity = BuildMapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(2, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            buildEntity = BuildMapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 2, JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            buildEntity = BuildMapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, OTHER_JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);
        });
    }

    @Test
    void shouldMapBuildToBuildEntityWithResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            BuildEntity buildEntity = BuildMapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isEqualTo(expectedBuildEntity);
        });
    }

    @Test
    void shouldNotMapBuildToBuildEntityWithResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            BuildEntity buildEntity = BuildMapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(2, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            buildEntity = BuildMapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 2, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            buildEntity = BuildMapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, OTHER_JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);
        });
    }

    private BuildEntity createBuildEntityWithResultEntities(int id, int buildNumber, String jobName, int numberOfResults) {
        BuildEntity buildEntity = new BuildEntity(id, buildNumber, "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/");
        for (int i = 0; i < numberOfResults; i++) {
            ResultEntity resultEntity = new ResultEntity(
                    i,
                    "toolId" + i,
                    "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/" + "toolId" + i,
                    "toolName" + i + " Warnings",
                    i * 10,
                    i * 10,
                    i * 10 * 2,
                    "INACTIVE"
            );
            resultEntity.setInfoMessages(createInfoMessage(i));
            resultEntity.setErrorMessages(createErrorMessage(i));
            for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
                switch (warningTypeEntity) {
                    case OUTSTANDING:
                        resultEntity.addReportEntity(new ReportEntity(WarningTypeEntity.OUTSTANDING));
                        break;
                    case NEW:
                        resultEntity.addReportEntity(new ReportEntity(WarningTypeEntity.NEW));
                        break;
                    case FIXED:
                        resultEntity.addReportEntity(new ReportEntity(WarningTypeEntity.FIXED));
                        break;
                }
            }
            buildEntity.addResultEntity(resultEntity);
        }

        return buildEntity;
    }

    private Build createBuildWithResults(int id, int buildNumber, String jobName, int numberOfResults) {
        Build build = new Build(id, buildNumber, "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/");
        for (int i = 0; i < numberOfResults; i++) {
            Result result = new Result(
                    i,
                    "toolId" + i,
                    "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/" + "toolId" + i,
                    "toolName" + i + " Warnings",
                    i * 10,
                    i * 10,
                    i * 10 * 2,
                    "INACTIVE"
            );
            result.setInfoMessages(createInfoMessage(i));
            result.setErrorMessages(createErrorMessage(i));
            for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
                switch (warningTypeEntity) {
                    case OUTSTANDING:
                        result.setOutstandingIssues(new Report());
                        break;
                    case NEW:
                        result.setNewIssues(new Report());
                        break;
                    case FIXED:
                        result.setFixedIssues(new Report());
                        break;
                }
            }
            build.addResult(result);
        }

        return build;
    }

    private List<String> createErrorMessage(int i) {
        return Arrays.asList("Error", "Message", ": " + i);
    }

    private List<String> createInfoMessage(int i) {
        return Arrays.asList("Info", "Message", ": " + i);
    }
}