package edu.hm.hafner.dashboard.service;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.dashboard.service.dto.Job;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the Class {@link AppStartupService}.
 *
 * @author Deniz Mardin
 */
class AppStartupServiceTest {
    private static final int NUMBER_OF_JOBS = 5;
    private static final String JOB_NAME = "jobName";
    private static final String SUCCESS = "Success";
    private static final int NUMBER_OF_BUILDS = 5;

    @Test
    void shouldFindJobByName() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);

        AppStartupService appStartupService = new AppStartupService(jobService, buildService);
        SoftAssertions.assertSoftly(softly -> {
            when(jobService.findJobByName("notExist")).thenReturn(null);
            softly.assertThat(appStartupService.findJobByName("notExist")).isNull();

            when(jobService.findJobByName(JOB_NAME)).thenReturn(createJob(1));
            softly.assertThat(appStartupService.findJobByName(JOB_NAME)).isEqualTo(createJob(1));
        });
    }

    @Test
    void shouldSaveNewJobs() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);

        AppStartupService appStartupService = new AppStartupService(jobService, buildService);
        SoftAssertions.assertSoftly(softly -> {
            List<Job> jobs = new ArrayList<>();

            when(jobService.saveAll(jobs)).thenReturn(jobs);
            softly.assertThat(appStartupService.saveNewJobs(jobs)).isEmpty();

            jobs = createJobs();
            when(jobService.saveAll(jobs)).thenReturn(jobs);
            softly.assertThat(appStartupService.saveNewJobs(jobs)).isEqualTo(jobs);
        });
    }

    @Test
    void shouldGetLatestBuildNumberFromJob() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);

        AppStartupService appStartupService = new AppStartupService(jobService, buildService);
        SoftAssertions.assertSoftly(softly -> {

            Job jobWithoutBuilds = createJob(1);
            when(buildService.getLatestBuild(jobWithoutBuilds)).thenThrow(new NoSuchElementException("No Build not found"));
            softly.assertThatThrownBy(() -> appStartupService.getLatestBuildNumberFromJob(jobWithoutBuilds))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("No Build not found");

            Job job = createJobWithBuilds();
            when(buildService.getLatestBuild(job)).thenReturn(createBuild(NUMBER_OF_BUILDS));
            softly.assertThat(appStartupService.getLatestBuildNumberFromJob(job)).isEqualTo(NUMBER_OF_BUILDS);
        });
    }

    @Test
    void shouldSaveNewBuildsFromJob() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);
        Job job = createJob(1);

        AppStartupService appStartupService = new AppStartupService(jobService, buildService);
        SoftAssertions.assertSoftly(softly -> {
            List<Build> emptyBuilds = new ArrayList<>();

            when(buildService.saveAll(job, emptyBuilds)).thenReturn(emptyBuilds);
            softly.assertThat(appStartupService.saveNewBuildsFromJob(job, emptyBuilds)).isEmpty();

            List<Build> builds = createBuilds();
            when(buildService.saveAll(job, builds)).thenReturn(builds);
            softly.assertThat(appStartupService.saveNewBuildsFromJob(job, builds)).isEqualTo(createBuilds());
        });
    }

    private List<Job> createJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_JOBS; i++) {
            jobs.add(createJob(i));
        }

        return jobs;
    }

    private Job createJob(final int Id) {
        return new Job(Id, JOB_NAME + Id, "http://localhost:8080/jenkins/job/" + JOB_NAME + Id + "/", SUCCESS);
    }

    private List<Build> createBuilds() {
        List<Build> builds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BUILDS; i++) {
            builds.add(createBuild(i));
        }
        return builds;
    }

    private Build createBuild(final int numberOfBuild) {
        return new Build(
                numberOfBuild,
                numberOfBuild,
                getUrlForBuildWithBuildNumber(numberOfBuild)
        );
    }

    private Job createJobWithBuilds() {
        Job job = createJob(1);
        List<Build> builds = createBuilds();
        builds.forEach(job::addBuild);

        return job;
    }

    private String getUrlForBuildWithBuildNumber(final int number) {
        return "http://localhost:8080/jenkins/job/" + JOB_NAME + "/" + number + "/";
    }
}