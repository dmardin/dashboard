package edu.hm.hafner.warningsngui.db;

import edu.hm.hafner.warningsngui.db.mapper.BuildMapper;
import edu.hm.hafner.warningsngui.db.mapper.JobMapper;
import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.db.repository.BuildRepository;
import edu.hm.hafner.warningsngui.service.dto.Build;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Deniz Mardin
 */
@Service
public class BuildEntityService {

    @Autowired
    BuildRepository buildRepository;

    public List<Build> saveAll(List<Build> builds, JobEntity jobEntity) {
        List<BuildEntity> buildEntities = buildRepository.saveAll(BuildMapper.mapToEntities(builds, jobEntity));
        return BuildMapper.map(buildEntities, JobMapper.map(jobEntity));
    }
}
