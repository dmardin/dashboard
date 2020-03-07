package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Test the Class {@link JobMapper}.
 *
 * @author Deniz Mardin
 */
class JobMapperTest {
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String JOB_NAME = "jobName";
    private static final String OTHER_JOB_NAME = "otherJobName";
    private static final int FIVE_BUILDS = 5;
    private static final int NO_BUILDS = 0;

    @Test
    void shouldMapJobEntityToJobWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            Job job = JobMapper.map(jobEntity);
            Job expectedJob = createJobWithBuilds(1, JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);
        });
    }

    @Test
    void shouldMapJobEntityToJobWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            Job job = JobMapper.map(jobEntity);
            Job expectedJob = createJobWithBuilds(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isEqualTo(expectedJob);
        });
    }

    @Test
    void shouldNotMapJobEntityToJobWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            Job job = JobMapper.map(jobEntity);
            Job expectedJob = this.createJobWithBuilds(1, OTHER_JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, SUCCESS, NO_BUILDS);
            job = JobMapper.map(jobEntity);
            expectedJob = this.createJobWithBuilds(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            job = JobMapper.map(jobEntity);
            expectedJob = this.createJobWithBuilds(2, JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(NO_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);
        });
    }

    @Test
    void shouldNotMapJobEntityToJobWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            Job job = JobMapper.map(jobEntity);
            Job expectedJob = createJobWithBuilds(1, OTHER_JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, SUCCESS, FIVE_BUILDS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(job.getBuilds()).hasSize(FIVE_BUILDS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

        });
    }

    @Test
    void shouldMapJobEntitiesToJobsWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = JobMapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> this.createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> this.createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldMapJobEntitiesToJobsWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = JobMapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldNotMapJobEntitiesToJobsWithoutBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = JobMapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, OTHER_JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(6, 11).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(NO_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldNotMapJobEntitiesToJobsWithBuildEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<Job> jobs = JobMapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, OTHER_JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(6, 11).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobs.forEach(j -> softly.assertThat(j.getBuilds()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldMapJobJobToEntityWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, NO_BUILDS);
            JobEntity jobEntity = JobMapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, FAILED, NO_BUILDS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldMapJobJobToEntityWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            JobEntity jobEntity = JobMapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, FAILED, FIVE_BUILDS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldNotMapJobToJobEntityWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, NO_BUILDS);
            JobEntity jobEntity = JobMapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, OTHER_JOB_NAME, SUCCESS, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, SUCCESS, NO_BUILDS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(1, JOB_NAME, FAILED, NO_BUILDS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, NO_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(NO_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldNotMapJobToJobEntityWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJobWithBuilds(1, JOB_NAME, SUCCESS, FIVE_BUILDS);
            JobEntity jobEntity = JobMapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntityWithBuildEntities(1, OTHER_JOB_NAME, SUCCESS, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, SUCCESS, FIVE_BUILDS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(1, JOB_NAME, FAILED, FIVE_BUILDS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED, FIVE_BUILDS);
            softly.assertThat(jobEntity.getBuildEntities()).hasSize(FIVE_BUILDS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldMapJobsToJobEntitiesWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = JobMapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldMapJobsToJobEntitiesWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = JobMapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldNotMapJobsToJobEntitiesWithoutBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = JobMapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, OTHER_JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(6, 11).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, NO_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(NO_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldNotMapJobsToJobEntitiesWithBuilds() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = JobMapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, OTHER_JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(6, 11).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS, FIVE_BUILDS)).collect(Collectors.toList());
            jobEntities.forEach(j -> softly.assertThat(j.getBuildEntities()).hasSize(FIVE_BUILDS));
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);
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
}
