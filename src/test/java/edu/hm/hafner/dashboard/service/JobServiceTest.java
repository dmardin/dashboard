package edu.hm.hafner.dashboard.service;

import edu.hm.hafner.dashboard.db.JobEntityService;
import edu.hm.hafner.dashboard.db.model.JobEntity;
import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.dashboard.service.dto.Job;
import edu.hm.hafner.dashboard.service.table.job.JobViewTable;
import io.jenkins.plugins.datatables.TableColumn;
import io.jenkins.plugins.datatables.TableModel;
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
        SoftAssertions.assertSoftly(softly -> {
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
        SoftAssertions.assertSoftly(softly -> {
            when(jobEntityService.findJobByName(JOB_NAME)).thenReturn(null);
            Job job = jobService.findJobByName(JOB_NAME);
            softly.assertThat(job).isNull();
        });
    }

    @Test
    void shouldFindJobByName() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly(softly -> {
            when(jobEntityService.findJobByName(JOB_NAME + 1)).thenReturn(createJobEntity(1));
            Job job = jobService.findJobByName(JOB_NAME + 1);
            softly.assertThat(job).isEqualTo(createJob(1));
        });
    }

    @Test
    void shouldSaveAllJobs() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly(softly -> {
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

    @Test
    void shouldCreateJobViewTable() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        SoftAssertions.assertSoftly(softly -> {
            JobViewTable jobViewTable = jobService.createJobViewTable();
            TableModel tableModel = jobViewTable.getTableModel("jobs");
            softly.assertThat(tableModel.getId()).isEqualTo("jobs");
            softly.assertThat(tableModel.getColumnsDefinition()).isEqualTo("[{  \"data\": \"jobName\",  \"defaultContent\": \"\"},{  \"data\": \"jobStatus\",  \"defaultContent\": \"\"},{  \"data\": \"jobUrl\",  \"defaultContent\": \"\"}]");
            softly.assertThat(tableModel.getRows()).isEmpty();
            softly.assertThat(jobViewTable.getTableRows("jobs")).isEmpty();

            List<TableColumn> tc = tableModel.getColumns();
            softly.assertThat(tc.size()).isEqualTo(3);
            softly.assertThat(tc.get(0).getHeaderLabel()).isEqualTo("Job Name");
            softly.assertThat(tc.get(0).getDefinition()).isEqualTo("{  \"data\": \"jobName\",  \"defaultContent\": \"\"}");
            softly.assertThat(tc.get(0).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(0).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(1).getHeaderLabel()).isEqualTo("Status");
            softly.assertThat(tc.get(1).getDefinition()).isEqualTo("{  \"data\": \"jobStatus\",  \"defaultContent\": \"\"}");
            softly.assertThat(tc.get(1).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(1).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(2).getHeaderLabel()).isEqualTo("Url");
            softly.assertThat(tc.get(2).getDefinition()).isEqualTo("{  \"data\": \"jobUrl\",  \"defaultContent\": \"\"}");
            softly.assertThat(tc.get(2).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(2).getWidth()).isEqualTo(1);
        });
    }

    private List<JobEntity> createJobEntities() {
        return IntStream.range(0, NUMBER_OF_JOBS).mapToObj(this::createJobEntity).collect(Collectors.toList());
    }

    private JobEntity createJobEntity(final int numberOfJob) {
        return new JobEntity(
                numberOfJob,
                getJobNameForNumber(numberOfJob),
                getUrlForNumber(numberOfJob),
                SUCCESS);
    }

    private Job createJob(final int numberOfJob) {
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

    private String getUrlForNumber(final int number) {
        return "http://localhost:8080/jenkins/job/" + JOB_NAME + number + "/";
    }

    private String getJobNameForNumber(final int numberOfJob) {
        return JOB_NAME + numberOfJob;
    }
}