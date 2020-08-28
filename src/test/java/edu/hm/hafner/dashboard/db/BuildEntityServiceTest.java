package edu.hm.hafner.dashboard.db;

import edu.hm.hafner.dashboard.db.model.BuildEntity;
import edu.hm.hafner.dashboard.db.repository.BuildRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the Class {@link BuildEntityService}.
 *
 * @author Deniz Mardin
 */
class BuildEntityServiceTest {
    private static final String JOB_NAME = "jobName";
    private static final int NUMBER_OF_BUILDS = 5;

    @Test
    void shouldSaveAllBuildEntities() {
        BuildRepository buildRepository = mock(BuildRepository.class);
        BuildEntityService buildEntityService = new BuildEntityService(buildRepository);

        SoftAssertions.assertSoftly(softly -> {
            List<BuildEntity> buildEntitiesToSave = new ArrayList<>();
            when(buildRepository.saveAll(buildEntitiesToSave)).thenReturn(buildEntitiesToSave);
            List<BuildEntity> savedBuildEntities = buildEntityService.saveAll(buildEntitiesToSave);
            softly.assertThat(savedBuildEntities).isNotNull();
            softly.assertThat(savedBuildEntities).isEmpty();

            buildEntitiesToSave = createBuildEntities();
            when(buildRepository.saveAll(buildEntitiesToSave)).thenReturn(buildEntitiesToSave);
            savedBuildEntities = buildEntityService.saveAll(buildEntitiesToSave);
            softly.assertThat(savedBuildEntities).isEqualTo(createBuildEntities());
        });
    }

    private List<BuildEntity> createBuildEntities() {
        return IntStream.range(0, NUMBER_OF_BUILDS).mapToObj(this::createBuildEntity).collect(Collectors.toList());
    }

    private BuildEntity createBuildEntity(final int numberOfBuild) {
        return new BuildEntity(numberOfBuild, numberOfBuild, "http://localhost:8080/jenkins/job/" + JOB_NAME + "/" + numberOfBuild + "/");
    }
}