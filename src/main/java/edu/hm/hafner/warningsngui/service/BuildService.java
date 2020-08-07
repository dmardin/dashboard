package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.warningsngui.db.BuildEntityService;
import edu.hm.hafner.warningsngui.db.mapper.Mapper;
import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.ui.table.build.BuildRepositoryStatistics;
import edu.hm.hafner.warningsngui.ui.table.build.BuildViewTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service to handle {@link Build}s between the ui and database.
 */
@Service
class BuildService {
    BuildEntityService buildEntityService;

    /**
     * Creates a new instance of {@link BuildService}.
     *
     * @param buildEntityService the entity service for buildss
     */
    @Autowired
    public BuildService(BuildEntityService buildEntityService) {
        this.buildEntityService = buildEntityService;
    }

    /**
     * saves a given list of {@link Build}s by adding them to the corresponding {@link Job}.
     *
     * @param fetchedJob the fetched {@link Job}
     * @param builds     list of {@link Build}s
     * @return the saved {@link Build}s
     */
    public List<Build> saveAll(Job fetchedJob, List<Build> builds) {
        JobEntity jobEntity = Mapper.mapToEntity(fetchedJob);
        List<BuildEntity> buildEntities = builds.stream().map(build -> jobEntity.addBuildEntity(Mapper.mapToEntity(build))).collect(Collectors.toList());
        List<BuildEntity> savedBuildEntities = buildEntityService.saveAll(buildEntities);

        return savedBuildEntities.stream().map(Mapper::map).collect(Collectors.toList());
    }

    /**
     * Determines the last {@link Build} of a given {@link Job}.
     *
     * @param job the {@link Job}
     * @return the last {@link Build}
     */
    public Build getLatestBuild(Job job) {
        return job.getBuilds().stream()
                .max(Comparator.comparing(Build::getNumber))
                .orElseThrow(() -> new NoSuchElementException("No Build not found"));
    }

    /**
     * Creates a list of {@link BuildResult}s of {@link Build}s for a given {@link Job}.
     *
     * @param job the needed {@link Job}
     * @return the needed list of {@link BuildResult}s for the echarts
     */
    public List<BuildResult<Build>> createBuildResults(Job job) {
        List<BuildResult<Build>> buildResults = new ArrayList<>();
        for (Build b : job.getBuilds()) {
            //TODO BuildTime
            edu.hm.hafner.echarts.Build build = new edu.hm.hafner.echarts.Build(b.getNumber(), "#" + b.getNumber(), 0);
            BuildResult<Build> buildBuildResult = new BuildResult<>(build, b);
            buildResults.add(buildBuildResult);
        }

        return buildResults;
    }

    /**
     * Creates a list of {@link BuildResult}s of {@link Build}s for a given {@link Job} and tool name (e.g. checkstyle).
     *
     * @param job the needed {@link Job}
     * @param toolName the tool name
     * @return the needed list of {@link BuildResult}s for the echarts
     */
    public List<BuildResult<Build>> createBuildResultsForTool(Job job, String toolName) {
        List<BuildResult<Build>> results = new ArrayList<>();

        job.getBuilds().forEach(b -> {
            edu.hm.hafner.echarts.Build build = new edu.hm.hafner.echarts.Build(b.getNumber(), "#" + b.getNumber(), 0);
            Build neededBuild = new Build();
            ArrayList<Result> resultArrayList = new ArrayList<>();
            for (Result result : b.getResults()) {
                if (result.getName().equals(toolName)) {
                    resultArrayList.add(result);
                    neededBuild.setResults(resultArrayList);
                }
            }

            BuildResult<Build> buildResult = new BuildResult<>(build, neededBuild);
            results.add(buildResult);
        });

        return results;
    }

    /**
     * Determines the {@link Build} from a {@link Job} by given build number.
     *
     * @param job the {@link Job}
     * @param buildNumber the build number
     * @return the {@link Build}
     */
    public Build getBuildWithBuildNumberFromJob(Job job, int buildNumber){
        return job.getBuilds().stream().filter(b -> b.getNumber() == buildNumber)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Build number " + buildNumber + " for the Job " + job.getName() + " not found"));
    }

    /**
     * Fetches all build from database and converts it to the needed format of table rows.
     *
     * @return prepared table rows
     * @param builds the builds
     */
    public List<Object> prepareRowsForBuildViewTable(List<Build> builds) {
        return convertRowsForTheBuildViewTable(builds);
    }

    /**
     * Method to convert a list of builds to the needed format of table rows.
     *
     * @param builds the jobs to convert
     * @return converted table rows
     */
    private List<Object> convertRowsForTheBuildViewTable(List<Build> builds) {
        BuildRepositoryStatistics buildRepositoryStatistics = new BuildRepositoryStatistics();
        buildRepositoryStatistics.addAll(builds);
        BuildViewTable buildViewTable = new BuildViewTable(buildRepositoryStatistics);

        return buildViewTable.getTableRows("builds");
    }

    public BuildViewTable createBuildViewTable() {
        return new BuildViewTable(new BuildRepositoryStatistics());
    }
}
