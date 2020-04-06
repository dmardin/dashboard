package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.warningsngui.db.BuildEntityService;
import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the Class {@link BuildService}.
 *
 * @author Deniz Mardin
 */
class BuildServiceTest {
    private static final String JOB_NAME = "jobName";
    private static final String SUCCESS = "Success";
    private static final int NUMBER_OF_BUILDS = 5;
    private static final int NUMBER_OF_RESULTS = 3;

    @Test
    void shouldSaveAllBuilds() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        SoftAssertions.assertSoftly((softly) -> {
            when(buildEntityService.saveAll(new ArrayList<>())).thenReturn(new ArrayList<>());
            List<Build> builds = buildService.saveAll(createJob(1), new ArrayList<>());
            softly.assertThat(builds).isEmpty();

            List<BuildEntity> buildEntities = createBuildEntities();
            when(buildEntityService.saveAll(buildEntities)).thenReturn(buildEntities);
            List<Build> buildsToSave = createBuilds();
            builds = buildService.saveAll(createJob(1), buildsToSave);
            softly.assertThat(builds).isEqualTo(buildsToSave);
        });
    }

    @Test
    void shouldGetLatestBuild() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);

        SoftAssertions.assertSoftly((softly) -> {
            Job jobWithoutBuilds = createJob(1);
            softly.assertThatThrownBy(() -> buildService.getLatestBuild(jobWithoutBuilds))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("No Build not found");

            Job job = createJobWithBuilds();
            Build latestBuild = buildService.getLatestBuild(job);
            Build shouldBeLatestBuild = job.getBuilds().stream().max(Comparator.comparingInt(Build::getNumber)).orElseThrow(() -> new NoSuchElementException("No Build not found"));
            softly.assertThat(latestBuild).isEqualTo(shouldBeLatestBuild);
        });
    }

    @Test
    void shouldGetBuildWithBuildNumberFromJob() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);

        SoftAssertions.assertSoftly((softly) -> {
            Job jobWithoutBuilds = createJob(3);
            softly.assertThatThrownBy(() -> buildService.getBuildWithBuildNumberFromJob(jobWithoutBuilds, 3))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("Build number " + 3 + " for the Job " + getJobNameForNumber(3) + " not found");

            Job job = createJobWithBuilds();
            Build filteredBuild = buildService.getBuildWithBuildNumberFromJob(job, 3);
            Build build = createBuild(3);
            softly.assertThat(filteredBuild).isEqualTo(build);

            softly.assertThatThrownBy(() -> buildService.getBuildWithBuildNumberFromJob(job, 10))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("Build number " + 10 + " for the Job " + getJobNameForNumber(1) + " not found");
        });
    }

    @Test
    void shouldCreateBuildResult() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);

        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuildsAndResults();
            List<BuildResult<Build>> buildResults = buildService.createBuildResults(job);
            for (int i = 0; i < buildResults.size(); i++) {
                BuildResult<Build> buildBuildResult = buildResults.get(i);
                softly.assertThat(buildBuildResult.getBuild().getDisplayName()).isEqualTo("#" + i);
                softly.assertThat(buildBuildResult.getBuild().getNumber()).isEqualTo(i);
                softly.assertThat(buildBuildResult.getBuild().getBuildTime()).isEqualTo(0); //TODO BuildTime
                softly.assertThat(buildBuildResult.getResult()).isEqualTo(createBuildWithResults(i, i, JOB_NAME, NUMBER_OF_RESULTS));
            }
        });
    }


    @Test
    void shouldCreateBuildResultForTool() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);

        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuildsAndResults();
            List<BuildResult<Build>> buildResults = buildService.createBuildResultsForTool(job, "toolName0 Warnings");
            for (int i = 0; i < buildResults.size(); i++) {
                BuildResult<Build> buildBuildResult = buildResults.get(i);
                softly.assertThat(buildBuildResult.getBuild().getDisplayName()).isEqualTo("#" + i);
                softly.assertThat(buildBuildResult.getBuild().getNumber()).isEqualTo(i);
                softly.assertThat(buildBuildResult.getBuild().getBuildTime()).isEqualTo(0);
                List<Result> results = buildBuildResult.getResult().getResults();
                results.forEach(result -> {
                    softly.assertThat(result.getName()).isEqualTo("toolName0 Warnings");
                });
            }
        });
    }

    private Job createJob(int numberOfJob) {
        return new Job(
                numberOfJob,
                getJobNameForNumber(numberOfJob),
                getUrlForNumber(numberOfJob),
                SUCCESS);
    }

    private String getUrlForNumber(int number) {
        return "http://localhost:8080/jenkins/job/" + JOB_NAME + number + "/";
    }

    private String getJobNameForNumber(int numberOfJob) {
        return JOB_NAME + numberOfJob;
    }

    private List<BuildEntity> createBuildEntities() {
        return IntStream.range(0, NUMBER_OF_BUILDS).mapToObj(this::createBuildEntity).collect(Collectors.toList());
    }

    private BuildEntity createBuildEntity(int numberOfBuild) {
        return new BuildEntity(
                numberOfBuild,
                numberOfBuild,
                "http://localhost:8080/jenkins/job/" + JOB_NAME + "/" + numberOfBuild + "/"
        );
    }

    private List<Build> createBuilds() {
        List<Build> builds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BUILDS; i++) {
            builds.add(createBuild(i));
        }
        return builds;
    }

    private Build createBuild(int numberOfBuild) {
        return new Build(
                numberOfBuild,
                numberOfBuild,
                "http://localhost:8080/jenkins/job/" + JOB_NAME + "/" + numberOfBuild + "/"
        );
    }

    private Job createJobWithBuilds() {
        Job job = createJob(1);
        List<Build> builds = createBuilds();
        builds.forEach(job::addBuild);

        return job;
    }

    private Job createJobWithBuildsAndResults() {
        Job job = createJob(1);
        List<Build> builds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BUILDS; i++) {
            builds.add(createBuildWithResults(i, i, JOB_NAME, NUMBER_OF_RESULTS));
        }
        builds.forEach(job::addBuild);

        return job;
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