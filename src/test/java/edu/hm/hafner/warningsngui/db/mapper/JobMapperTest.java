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

    @Test
    void shouldMapJobEntityToJob() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntity(1, JOB_NAME, SUCCESS);
            Job job = JobMapper.map(jobEntity);
            Job expectedJob = createJob(1, JOB_NAME, SUCCESS);
            softly.assertThat(job).isEqualTo(expectedJob);

            jobEntity = createJobEntity(2, JOB_NAME, FAILED);
            job = JobMapper.map(jobEntity);
            expectedJob = createJob(2, JOB_NAME, FAILED);
            softly.assertThat(job).isEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(1, JOB_NAME, SUCCESS);
            softly.assertThat(job).isEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED);
            softly.assertThat(job).isEqualTo(expectedJob);
        });
    }

    @Test
    void shouldNotMapJobEntityToJob() {
        SoftAssertions.assertSoftly((softly) -> {
            JobEntity jobEntity = createJobEntity(1, JOB_NAME, SUCCESS);
            Job job = JobMapper.map(jobEntity);
            Job expectedJob = createJob(1, OTHER_JOB_NAME, SUCCESS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntity(2, JOB_NAME, SUCCESS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJob(2, JOB_NAME, FAILED);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntity(1, JOB_NAME, SUCCESS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJob(2, JOB_NAME, SUCCESS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(1, OTHER_JOB_NAME, SUCCESS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, SUCCESS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, FAILED);
            softly.assertThat(job).isNotEqualTo(expectedJob);

            jobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS);
            job = JobMapper.map(jobEntity);
            expectedJob = createJobWithBuilds(2, JOB_NAME, SUCCESS);
            softly.assertThat(job).isNotEqualTo(expectedJob);

        });
    }

    @Test
    void shouldMapJobEntitiesToJobs() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            List<Job> jobs = JobMapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobs).isEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobs).isEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobs).isEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobs).isEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldNotMapJobEntitiesToJobs() {
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            List<Job> jobs = JobMapper.map(jobEntities);
            List<Job> expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, OTHER_JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(6, 11).mapToObj(i -> createJob(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, OTHER_JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);

            jobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            jobs = JobMapper.map(jobEntities);
            expectedJobs = IntStream.range(6, 11).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobs).isNotEqualTo(expectedJobs);
        });
    }

    @Test
    void shouldMapJobJobToEntity() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJob(1, JOB_NAME, SUCCESS);
            JobEntity jobEntity = JobMapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntity(1, JOB_NAME, SUCCESS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);

            job = createJob(2, JOB_NAME, FAILED);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntity(2, JOB_NAME, FAILED);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);

            job = createJobWithBuilds(1, JOB_NAME, SUCCESS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(1, JOB_NAME, SUCCESS);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, FAILED);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED);
            softly.assertThat(jobEntity).isEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldNotMapJobToJobEntity() {
        SoftAssertions.assertSoftly((softly) -> {
            Job job = createJob(1, JOB_NAME, SUCCESS);
            JobEntity jobEntity = JobMapper.mapToEntity(job);
            JobEntity expectedJobEntity = createJobEntity(1, OTHER_JOB_NAME, SUCCESS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJob(2, JOB_NAME, SUCCESS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntity(2, JOB_NAME, FAILED);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJob(1, JOB_NAME, FAILED);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntity(2, JOB_NAME, FAILED);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(1, JOB_NAME, SUCCESS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(1, OTHER_JOB_NAME, SUCCESS);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(2, JOB_NAME, SUCCESS);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);

            job = createJobWithBuilds(1, JOB_NAME, FAILED);
            jobEntity = JobMapper.mapToEntity(job);
            expectedJobEntity = createJobEntityWithBuildEntities(2, JOB_NAME, FAILED);
            softly.assertThat(jobEntity).isNotEqualTo(expectedJobEntity);
        });
    }

    @Test
    void shouldMapJobsToJobEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = JobMapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isEqualTo(expectedJobEntities);
        });
    }

    @Test
    void shouldNotMapJobsToJobEntities() {
        SoftAssertions.assertSoftly((softly) -> {
            List<Job> jobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            List<JobEntity> jobEntities = JobMapper.mapToEntities(jobs);
            List<JobEntity> expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, OTHER_JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntity(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJob(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(6, 11).mapToObj(i -> createJobEntity(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, OTHER_JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(0, 5).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, FAILED)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);

            jobs = IntStream.range(0, 5).mapToObj(i -> createJobWithBuilds(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            jobEntities = JobMapper.mapToEntities(jobs);
            expectedJobEntities = IntStream.range(6, 11).mapToObj(i -> createJobEntityWithBuildEntities(i, JOB_NAME + i, SUCCESS)).collect(Collectors.toList());
            softly.assertThat(jobEntities).isNotEqualTo(expectedJobEntities);
        });
    }

    private JobEntity createJobEntity(int idNumber, String jobName, String lastBuildStatus) {
        return new JobEntity(idNumber, jobName,"http://localhost:8080/jenkins/job/" + jobName +"/", lastBuildStatus);
    }

    private Job createJob(int idNumber, String jobName, String lastBuildStatus) {
        return new Job(idNumber, jobName,"http://localhost:8080/jenkins/job/" + jobName +"/", lastBuildStatus);
    }

    private JobEntity createJobEntityWithBuildEntities(int idNumber, String jobName, String lastBuildStatus) {
        JobEntity jobEntity = createJobEntity(idNumber, jobName, lastBuildStatus);
        for(int i = 0; i < 5; i++) {
            jobEntity.addBuildEntity(new BuildEntity(i, i, "http://localhost:8080/jenkins/job/"+ JOB_NAME +"/" + i + "/"));
        }

        return jobEntity;
    }

    private Job createJobWithBuilds(int idNumber, String jobName, String lastBuildStatus) {
        Job job = createJob(idNumber, jobName, lastBuildStatus);
        for(int i = 0; i < 5; i++) {
            job.addBuild(new Build(i, i, "http://localhost:8080/jenkins/job/"+ JOB_NAME +"/" + i + "/"));
        }

        return job;
    }
}