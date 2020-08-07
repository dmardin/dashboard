package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.warningsngui.db.JobEntityService;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.table.job.JobTableModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the Class {@link JobService}.
 *
 * @author Deniz Mardin
 */
class JobServiceTest {
    private static final String JOB_NAME = "jobName";
    private static final String SUCCESS = "Success";
    private static final int NUMBER_OF_JOBS = 5;

    @Test
    void shouldFindAllJobs() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly((softly) -> {
            List<JobEntity> allJobs = new ArrayList<>();
            when(jobEntityService.findAll()).thenReturn(allJobs);
            List<Job> jobs = jobService.getAllJobs();
            softly.assertThat(jobs).isNotNull();
            softly.assertThat(jobs).isEmpty();

            allJobs = createJobEntities();
            when(jobEntityService.findAll()).thenReturn(allJobs);
            jobs = jobService.getAllJobs();
            softly.assertThat(jobs).hasSize(NUMBER_OF_JOBS);
            for (int i = 0; i < NUMBER_OF_JOBS; i++) {
                Job job = jobs.get(i);
                softly.assertThat(job.getId()).isEqualTo(i);
                softly.assertThat(job.getName()).isEqualTo(getJobNameForNumber(i));
                softly.assertThat(job.getUrl()).isEqualTo(getUrlForNumber(i));
                softly.assertThat(job.getLastBuildStatus()).isEqualTo(SUCCESS);
                softly.assertThat(job.getBuilds()).isEqualTo(new ArrayList<Build>());
            }
        });
    }

    @Test
    void shouldNotFindJobByName() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly((softly) -> {
            when(jobEntityService.findJobByName(JOB_NAME)).thenReturn(null);
            Job job = jobService.findJobByName(JOB_NAME);
            softly.assertThat(job).isNull();
        });
    }

    @Test
    void shouldFindJobByName() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly((softly) -> {
            when(jobEntityService.findJobByName(JOB_NAME + 1)).thenReturn(createJobEntity(1));
            Job job = jobService.findJobByName(JOB_NAME + 1);
            softly.assertThat(job).isEqualTo(createJob(1));
        });
    }

    @Test
    void shouldPrepareRowsForJobViewTable() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly((softly) -> {
            when(jobEntityService.findAll()).thenReturn(new ArrayList<>());
            List<Object> objects = jobService.prepareRowsForJobViewTable();
            softly.assertThat(objects).isEmpty();

            when(jobEntityService.findAll()).thenReturn(createJobEntities());
            objects = jobService.prepareRowsForJobViewTable();
            softly.assertThat(objects).hasSize(NUMBER_OF_JOBS);
            softly.assertThat(objects).isInstanceOf(List.class);
            for (int i = 0; i < NUMBER_OF_JOBS; i++) {
                JobTableModel.JobsRow jobsRow = (JobTableModel.JobsRow) objects.get(i);
                softly.assertThat(jobsRow.getJobName()).isEqualTo(getJobNameForNumber(i));
                softly.assertThat(jobsRow.getJobUrl()).isEqualTo(getUrlForNumber(i));
                softly.assertThat(jobsRow.getJobStatus()).isEqualTo(SUCCESS);
            }
        });
    }

    @Test
    void shouldSaveAllJobs() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly((softly) -> {
            when(jobEntityService.saveAll(new ArrayList<>())).thenReturn(new ArrayList<>());
            List<Job> jobs = jobService.saveAll(new ArrayList<>());
            softly.assertThat(jobs).isEmpty();

            List<JobEntity> jobEntities = createJobEntities();
            when(jobEntityService.saveAll(jobEntities)).thenReturn(jobEntities);
            List<Job> jobsToSave = createJobs();
            jobs = jobService.saveAll(jobsToSave);
            softly.assertThat(jobs).isEqualTo(jobsToSave);
        });
    }

    private List<JobEntity> createJobEntities() {
        return IntStream.range(0, NUMBER_OF_JOBS).mapToObj(this::createJobEntity).collect(Collectors.toList());
    }

    private JobEntity createJobEntity(int numberOfJob) {
        return new JobEntity(
                numberOfJob,
                getJobNameForNumber(numberOfJob),
                getUrlForNumber(numberOfJob),
                SUCCESS);
    }

    private Job createJob(int numberOfJob) {
        return new Job(
                numberOfJob,
                getJobNameForNumber(numberOfJob),
                getUrlForNumber(numberOfJob),
                SUCCESS);
    }

    private List<Job> createJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_JOBS; i++) {
            jobs.add(createJob(i));
        }

        return jobs;
    }

    private String getUrlForNumber(int number) {
        return "http://localhost:8080/jenkins/job/" + JOB_NAME + number + "/";
    }

    private String getJobNameForNumber(int numberOfJob) {
        return JOB_NAME + numberOfJob;
    }
}