package edu.hm.hafner.dashboard.db;

import edu.hm.hafner.dashboard.db.model.BuildEntity;
import edu.hm.hafner.dashboard.db.repository.BuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle the interactions for {@link BuildEntity}s with the database by using the {@link BuildRepository}.
 *
 * @author Deniz Mardin
 */
@Service
public class BuildEntityService {
    private BuildRepository buildRepository;

    /**
     * Creates a new instance of {@link BuildEntityService}.
     *
     * @param buildRepository the repository for builds
     */
    @Autowired
    public BuildEntityService(final BuildRepository buildRepository) {
        this.buildRepository = buildRepository;
    }

    /**
     * Saves a list of {@link BuildEntity}s to the database.
     *
     * @param buildEntities the {@link BuildEntity}s to save
     * @return the saved list of {@link BuildEntity}s
     */
    public List<BuildEntity> saveAll(final List<BuildEntity> buildEntities) {
        return buildRepository.saveAll(buildEntities);
    }
}
