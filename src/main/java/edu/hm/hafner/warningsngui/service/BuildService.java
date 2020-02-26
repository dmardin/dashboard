package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.warningsngui.db.BuildEntityService;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.ui.table.build.BuildRepositoryStatistics;
import edu.hm.hafner.warningsngui.ui.table.build.BuildStatistics;
import edu.hm.hafner.warningsngui.ui.table.build.BuildViewTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BuildService {

    @Autowired
    BuildEntityService buildEntityService;

    public List<Build> saveAll(List<Build> builds, JobEntity jobEntity) {
        return buildEntityService.saveAll(builds, jobEntity);
    }

    public Build getLatestBuild(Job job) {
        Build latestBuild = new Build();
        for (Build build : job.getBuilds()){
            if(build.getNumber() > latestBuild.getNumber()){
                latestBuild = build;
            }
        }
        return latestBuild;
    }

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

            BuildResult<Build> my = new BuildResult<>(build, neededBuild);
            results.add(my);
        });

        return results;
    }

    public Build getBuildWithBuildNumberFromJob(Job job, int buildNumber){
        return job.getBuilds().stream().filter(b -> b.getNumber() == buildNumber)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Build number "+ buildNumber+ " for the Job "+ job.getName() + " not found" ));
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
        ArrayList<BuildStatistics> buildsStatistics = new ArrayList<>();
        builds.forEach(build -> buildsStatistics.add(new BuildStatistics(build.getNumber(), build.getUrl())));

        buildRepositoryStatistics.addAll(buildsStatistics);
        BuildViewTable buildViewTable = new BuildViewTable(buildRepositoryStatistics);
        return buildViewTable.getTableRows("builds");
    }
}
