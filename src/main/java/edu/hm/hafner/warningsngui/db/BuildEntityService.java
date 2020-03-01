package edu.hm.hafner.warningsngui.db;

import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.repository.BuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service to handle the interactions for {@link BuildEntity}s with the database by using the {@link BuildRepository}.
 *
 * @author Deniz Mardin
 */
@Service
public class BuildEntityService {

    @Autowired
    BuildRepository buildRepository;

    /**
     * Saves a list of {@link BuildEntity}s to the database.
     *
     * @param buildEntities the {@link BuildEntity}s to save
     * @return the saved list of {@link BuildEntity}s
     */
    public List<BuildEntity> saveAll(List<BuildEntity> buildEntities) {
        return buildRepository.saveAll(buildEntities);
    }
}
