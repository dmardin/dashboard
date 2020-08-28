package edu.hm.hafner.dashboard.db;

import edu.hm.hafner.dashboard.db.model.JobEntity;
import edu.hm.hafner.dashboard.db.repository.JobRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the Class {@link JobEntityService}.
 *
 * @author Deniz Mardin
 */
@RunWith(MockitoJUnitRunner.class)
class JobEntityServiceTest {
    private static final String JOB_NAME = "jobName";
    private static final String SUCCESS = "Success";
    private static final int NUMBER_OF_JOBS = 5;

    @Test
    void shouldFindAll() {
        JobRepository jobRepository = mock(JobRepository.class);
        JobEntityService jobEntityService = new JobEntityService(jobRepository);

        SoftAssertions.assertSoftly(softly -> {
            when(jobRepository.findAll()).thenReturn(createJobEntities());
            List<JobEntity> jobEntities = jobEntityService.findAll();
            for (int i = 0; i < jobEntities.size(); i++) {
                JobEntity jobEntity = jobEntities.get(i);
                softly.assertThat(jobEntity.getId()).isEqualTo(i);
                softly.assertThat(jobEntity.getName()).isEqualTo(getJobNameForNumber(i));
                softly.assertThat(jobEntity.getUrl()).isEqualTo(getUrlForNumber(i));
                softly.assertThat(jobEntity.getLastBuildStatus()).isEqualTo(SUCCESS);
            }
        });
    }

    @Test
    void shouldNotFindAnyJobEntitiesBySearchingForAll() {
        JobRepository jobRepository = mock(JobRepository.class);
        JobEntityService jobEntityService = new JobEntityService(jobRepository);

        SoftAssertions.assertSoftly(softly -> {
            when(jobRepository.findAll()).thenReturn(new ArrayList<>());
            List<JobEntity> jobEntities = jobEntityService.findAll();
            softly.assertThat(jobEntities).isNotNull();
            softly.assertThat(jobEntities).isEmpty();
        });
    }

    @Test
    void shouldFindJobEntityByJobName() {
        JobRepository jobRepository = mock(JobRepository.class);
        JobEntityService jobEntityService = new JobEntityService(jobRepository);

        SoftAssertions.assertSoftly(softly -> {
            JobEntity jobEntityToFind = createJobEntity(1);
            when(jobRepository.findByName(JOB_NAME + 1)).thenReturn(jobEntityToFind);
            JobEntity jobEntity = jobEntityService.findJobByName(JOB_NAME + 1);
            softly.assertThat(jobEntity).isEqualTo(jobEntityToFind);
        });
    }

    @Test
    void shouldNotFindJobEntityByJobName() {
        JobRepository jobRepository = mock(JobRepository.class);
        JobEntityService jobEntityService = new JobEntityService(jobRepository);

        SoftAssertions.assertSoftly(softly -> {
            when(jobRepository.findByName(JOB_NAME)).thenReturn(null);
            JobEntity jobEntity = jobEntityService.findJobByName(JOB_NAME);
            softly.assertThat(jobEntity).isNull();
        });
    }

    @Test
    void shouldSaveAllJobEntities() {
        JobRepository jobRepository = mock(JobRepository.class);
        JobEntityService jobEntityService = new JobEntityService(jobRepository);


        SoftAssertions.assertSoftly(softly -> {
            List<JobEntity> jobEntitiesToSave = new ArrayList<>();
            when(jobRepository.saveAll(jobEntitiesToSave)).thenReturn(jobEntitiesToSave);
            List<JobEntity> savedJobEntities = jobEntityService.saveAll(jobEntitiesToSave);
            softly.assertThat(savedJobEntities).isNotNull();
            softly.assertThat(savedJobEntities).isEmpty();

            jobEntitiesToSave = createJobEntities();
            when(jobRepository.saveAll(jobEntitiesToSave)).thenReturn(jobEntitiesToSave);
            savedJobEntities = jobEntityService.saveAll(jobEntitiesToSave);
            softly.assertThat(savedJobEntities).isEqualTo(jobEntitiesToSave);
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

    private String getUrlForNumber(final int number) {
        return "http://localhost:8080/jenkins/job/" + JOB_NAME + number + "/";
    }

    private String getJobNameForNumber(final int numberOfJob) {
        return JOB_NAME + numberOfJob;
    }
}