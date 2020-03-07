package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.db.model.*;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Test the Class {@link Mapper}.
 *
 * @author Deniz Mardin
 */
class MapperTest {
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String JOB_NAME = "jobName";
    private static final String OTHER_JOB_NAME = "otherJobName";
    private static final int FIVE_BUILDS = 5;
    private static final int NO_BUILDS = 0;
    private static final int FIVE_RESULTS = 5;
    private static final int NO_RESULTS = 0;
    private static final List<String> UUID_LIST = Arrays.asList(
            "239c88cb-abb2-43c4-8374-735840acbee9",
            "1cda4bbb-77f3-45fb-839a-a9a05bec0ca4",
            "4dc33b00-fe6b-4818-852c-435875e815ab",
            "8bf5fa77-4088-4d01-bc9b-6a5a16ffda54",
            "24dfa1b9-6b16-46d6-abda-e1d19647021e",
            "4c61f5ec-1bff-430a-a94e-6d095b53e0de",
            "e6b61cd5-c357-4581-a06a-bcbdec9c1378",
            "d81589b4-0572-4aca-93b8-86f25647da4c",
            "90be44ba-c895-4c21-9846-866b9104b316",
            "897eab11-0c31-4ce2-900e-a6da19027a12",
            "8cee3e58-8e56-463c-b0c6-4a51b404e24c",
            "732c108a-3356-40fd-8659-d93eac0d7015",
            "7213380e-2ea3-4893-84e5-2560985f32b3",
            "c27e61be-6ee4-482f-8f5a-abb097364a13",
            "df1321a3-feed-4a0e-97e6-181441e49adc",
            "1bfa080e-e529-441b-8c21-9bc1b5aa8aa9"
    );

    @Test
    void shouldMapJobEntityToJobWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            Job job = Mapper.map(jobEntity);
            Job expectedJob = createJobWithBuilds(1, JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            job = Mapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);
        });
    }

    @Test
    void shouldMapJobEntityToJobWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            Job job = Mapper.map(jobEntity);
            Job expectedJob = createJobWithBuilds(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            job = Mapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);
        });
    }

    @Test
    void shouldNotMapJobEntityToJobWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            Job job = Mapper.map(jobEntity);
            Job expectedJob = this.createJobWithBuilds(1, OTHER_JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, SUCCESS, NO_BUILDS);
            job = Mapper.map(jobEntity);
            expectedJob = this.createJobWithBuilds(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            job = Mapper.map(jobEntity);
            expectedJob = this.createJobWithBuilds(2, JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);
        });
    }

    @Test
    void shouldNotMapJobEntityToJobWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            Job job = Mapper.map(jobEntity);
            Job expectedJob = createJobWithBuilds(1, OTHER_JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, SUCCESS, FIVE_BUILDS);
            job = Mapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            job = Mapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

        });
    }

    @Test
    void shouldMapJobEntitiesToJobsWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = Mapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> this.createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs = Mapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> this.createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldMapJobEntitiesToJobsWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = Mapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs = Mapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldNotMapJobEntitiesToJobsWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = Mapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, OTHER_JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobs = Mapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs = Mapper.map(jobEntities);
            expectedJobs = IntStream.range(6, 11).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldNotMapJobEntitiesToJobsWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = Mapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, OTHER_JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobs = Mapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs = Mapper.map(jobEntities);
            expectedJobs = IntStream.range(6, 11).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldMapJobJobToEntityWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, NO_BUILDS);
            JobEntity jobEntity = Mapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, FAILED, NO_BUILDS);
            jobEntity = Mapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldMapJobJobToEntityWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            JobEntity jobEntity = Mapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, FAILED, FIVE_BUILDS);
            jobEntity = Mapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldNotMapJobToJobEntityWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, NO_BUILDS);
            JobEntity jobEntity = Mapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, OTHER_JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, SUCCESS, NO_BUILDS);
            jobEntity = Mapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(1, JOB_NAME, FAILED, NO_BUILDS);
            jobEntity = Mapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldNotMapJobToJobEntityWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            JobEntity jobEntity = Mapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, OTHER_JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, SUCCESS, FIVE_BUILDS);
            jobEntity = Mapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(1, JOB_NAME, FAILED, FIVE_BUILDS);
            jobEntity = Mapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldMapJobsToJobEntitiesWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = Mapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobEntities = Mapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldMapJobsToJobEntitiesWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = Mapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities = Mapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldNotMapJobsToJobEntitiesWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = Mapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, OTHER_JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities = Mapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities = Mapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(6, 11).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldNotMapJobsToJobEntitiesWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = Mapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, OTHER_JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities = Mapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities = Mapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(6, 11).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldMapBuildEntityToBuildWithoutResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            Build build = Mapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldNotMapBuildEntityToBuildWithoutResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            Build build = Mapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(2, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            build = Mapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 2, JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            build = Mapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 1, OTHER_JOB_NAME, NO_RESULTS);
            softly.assertThat(build.getResults()).hasSize(NO_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldMapBuildEntityToBuildWithResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            Build build = Mapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldMapNotBuildEntityToBuildWithResultEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            BuildEntity buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            Build build = Mapper.map(buildEntity);
            Build expectedBuild = createBuildWithResults(2, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            build = Mapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 2, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);

            buildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            build = Mapper.map(buildEntity);
            expectedBuild = createBuildWithResults(1, 1, OTHER_JOB_NAME, FIVE_RESULTS);
            softly.assertThat(build.getResults()).hasSize(FIVE_RESULTS);
            softly.assertThat(build).isNotEqualTo(expectedBuild);
        });
    }

    @Test
    void shouldMapBuildToBuildEntityWithoutResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            BuildEntity buildEntity = Mapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isEqualTo(expectedBuildEntity);
        });
    }

    @Test
    void shouldNotMapBuildToBuildEntityWithoutResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            BuildEntity buildEntity = Mapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(2, 1, JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            buildEntity = Mapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 2, JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, NO_RESULTS);
            buildEntity = Mapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, OTHER_JOB_NAME, NO_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(NO_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);
        });
    }

    @Test
    void shouldMapBuildToBuildEntityWithResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            BuildEntity buildEntity = Mapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isEqualTo(expectedBuildEntity);
        });
    }

    @Test
    void shouldNotMapBuildToBuildEntityWithResults() {
        SoftAssertions.assertSoftly((softly) -> {
            Build build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            BuildEntity buildEntity = Mapper.mapToEntity(build);
            BuildEntity expectedBuildEntity = createBuildEntityWithResultEntities(2, 1, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            buildEntity = Mapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 2, JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);

            build = createBuildWithResults(1, 1, JOB_NAME, FIVE_RESULTS);
            buildEntity = Mapper.mapToEntity(build);
            expectedBuildEntity = createBuildEntityWithResultEntities(1, 1, OTHER_JOB_NAME, FIVE_RESULTS);
            softly.assertThat(buildEntity.getResultEntities()).hasSize(FIVE_RESULTS);
            softly.assertThat(buildEntity).isNotEqualTo(expectedBuildEntity);
        });
    }

    @Test
    void shouldMapResultEntityToResult() {
        SoftAssertions.assertSoftly((softly) -> {
            ResultEntity resultEntity = createResultEntity(1, 1, JOB_NAME, 0,0,0,0,0,0);
            Result result = Mapper.map(resultEntity);
            Result expectedResult = createResult(1, 1,  JOB_NAME,0,0,0,0,0,0);
            softly.assertThat(result).isEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME, 0,5,5,10,10,15);
            result = Mapper.map(resultEntity);
            expectedResult = createResult(1, 1,  JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isEqualTo(expectedResult);
        });
    }

    @Test
    void shouldNotMapResultEntityToResult() {
        SoftAssertions.assertSoftly((softly) -> {
            ResultEntity resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            Result result = Mapper.map(resultEntity);
            Result expectedResult = createResult(2, 1,  JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = Mapper.map(resultEntity);
            expectedResult = createResult(1, 2,  JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = Mapper.map(resultEntity);
            expectedResult = createResult(1, 1,  OTHER_JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = Mapper.map(resultEntity);
            expectedResult = createResult(1, 1,  JOB_NAME, 1,6,6,11,11,16);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = Mapper.map(resultEntity);
            expectedResult = createResult(1, 1,  JOB_NAME, 1,2,2,3,3,4);
            softly.assertThat(result).isNotEqualTo(expectedResult);
        });
    }

    @Test
    void shouldMapResultToResultEntity() {
        SoftAssertions.assertSoftly((softly) -> {
            Result result = createResult(1, 1, JOB_NAME,0,0,0,0,0,0);
            ResultEntity resultEntity = Mapper.mapToEntity(result);
            ResultEntity expectedResultEntity = createResultEntity(1, 1, JOB_NAME,0,0,0,0,0,0);
            softly.assertThat(resultEntity).isEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = Mapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isEqualTo(expectedResultEntity);
        });
    }

    @Test
    void shouldNotMapResultToResultEntity() {
        SoftAssertions.assertSoftly((softly) -> {
            Result result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            ResultEntity resultEntity = Mapper.mapToEntity(result);
            ResultEntity expectedResultEntity = createResultEntity(2, 1, JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = Mapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 2, JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = Mapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, OTHER_JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = Mapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, JOB_NAME, 1,6,6,11,11,16);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = Mapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, JOB_NAME, 1,2,2,3,3,4);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);
        });
    }

    private JobEntity createJobEntityWithBuildEntities(int idNumber, String jobName, String lastBuildStatus, int numberOfBuilds) {
        JobEntity jobEntity = new JobEntity(idNumber, jobName, "http://localhost:8080/jenkins/job/" + jobName + "/", lastBuildStatus);
        for (int i = 0; i < numberOfBuilds; i++) {
            jobEntity.addBuildEntity(new BuildEntity(i, i, "http://localhost:8080/jenkins/job/" + jobName + "/" + i + "/"));
        }

        return jobEntity;
    }

    private Job createJobWithBuilds(int idNumber, String jobName, String lastBuildStatus, int numberOfBuilds) {
        Job job = new Job(idNumber, jobName, "http://localhost:8080/jenkins/job/" + jobName + "/", lastBuildStatus);
        for (int i = 0; i < numberOfBuilds; i++) {
            job.addBuild(new Build(i, i, "http://localhost:8080/jenkins/job/" + jobName + "/" + i + "/"));
        }

        return job;
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

    private ResultEntity createResultEntity(int id, int buildNumber, String jobName, int outstandingStart, int outstandingEndExcluded, int newStart, int newEndExcluded, int fixedStart, int fixedEndExcluded) {
        ResultEntity resultEntity = new ResultEntity(
                id,
                "toolId" + id,
                "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/" + "toolId" + id,
                "toolName" + id + " Warnings",
                id * 10,
                id * 10,
                id * 10 * 2,
                "INACTIVE"
        );
        resultEntity.setInfoMessages(createInfoMessage(id));
        resultEntity.setErrorMessages(createErrorMessage(id));
        for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
            switch (warningTypeEntity) {
                case OUTSTANDING:
                    resultEntity.addReportEntity(createReportEntity(warningTypeEntity, outstandingStart, outstandingEndExcluded));
                    break;
                case NEW:
                    resultEntity.addReportEntity(createReportEntity(warningTypeEntity, newStart, newEndExcluded));
                    break;
                case FIXED:
                    resultEntity.addReportEntity(createReportEntity(warningTypeEntity, fixedStart, fixedEndExcluded));
                    break;
            }
        }
        return resultEntity;
    }

    private Result createResult(int id, int buildNumber, String jobName, int outstandingStart, int outstandingEndExcluded, int newStart, int newEndExcluded, int fixedStart, int fixedEndExcluded) {
        Result result = new Result(
                id,
                "toolId" + id,
                "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/" + "toolId" + id,
                "toolName" + id + " Warnings",
                id * 10,
                id * 10,
                id * 10 * 2,
                "INACTIVE"
        );
        result.setInfoMessages(createInfoMessage(id));
        result.setErrorMessages(createErrorMessage(id));
        for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
            switch (warningTypeEntity) {
                case OUTSTANDING:
                    result.setOutstandingIssues(createReport(outstandingStart, outstandingEndExcluded));
                    break;
                case NEW:
                    result.setNewIssues(createReport(newStart, newEndExcluded));
                    break;
                case FIXED:
                    result.setFixedIssues(createReport(fixedStart, fixedEndExcluded));
                    break;
            }
        }

        return result;
    }

    private Report createReport(int issueNumberStart, int issueNumberEnd) {
        Report report = new Report();
        for(int i = issueNumberStart; i < issueNumberEnd; i++) {
            report.add(createIssue(i));
        }
        return report;
    }

    private Issue createIssue(int i) {
        IssueBuilder issueBuilder = new IssueBuilder();
        return issueBuilder
                .setId(getUUID(i))
                .setCategory("category"+i)
                .setColumnEnd(i+2)
                .setColumnStart(i+1)
                .setDescription("description"+i)
                .setFileName("filename"+i)
                .setFingerprint("fingerprint"+i)
                .setLineEnd(i+4)
                .setLineStart(i+3)
                .setMessage("message"+i)
                .setModuleName("moduleName"+i)
                .setOrigin("origin"+i)
                .setPackageName("packageName"+i)
                .setReference("reference"+i)
                .setSeverity(Severity.valueOf("ERROR"+i))
                .setType("type"+i)
                .build();
    }

    private ReportEntity createReportEntity(WarningTypeEntity warningTypeEntity, int issueNumberStart, int issueNumberEnd) {
        ReportEntity reportEntity = new ReportEntity(warningTypeEntity);
        for(int i = issueNumberStart; i < issueNumberEnd; i++) {
            reportEntity.addIssueEntity(createIssueEntity(i));
        }

        return reportEntity;
    }

    private IssueEntity createIssueEntity(int i){
        return new IssueEntity(
                getUUID(i),
                i+1,
                i+2,
                i+3,
                i+4,
                "category"+i,
                "description"+i,
                "filename"+i,
                "fingerprint"+i,
                "message"+i,
                "moduleName"+i,
                "origin"+i,
                "packageName"+i,
                "reference"+i,
                "ERROR"+i,
                "type"+i
        );
    }

    private UUID getUUID(int i) {
        return UUID.fromString(String.valueOf(UUID_LIST.get(i)));
    }

    private List<String> createErrorMessage(int i) {
        return Arrays.asList("Error", "Message", ": " + i);
    }

    private List<String> createInfoMessage(int i) {
        return Arrays.asList("Info", "Message", ": " + i);
    }
}
