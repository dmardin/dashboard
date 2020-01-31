package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Job;
import edu.hm.hafner.warningsngui.dto.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildService {

    public Build getLatestBuild(Job job) {
        Build latestBuild = new Build();
        for (Build build : job.getBuilds()){
            if(build.getNumber() > latestBuild.getNumber()){
                latestBuild = build;
            }
        }
        return latestBuild;
    }

    public List<BuildResult<Build>> createBuildResultsForAggregatedAnalysisResults(Job job) {
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

    public Build getBuildWithBuildNumberFromJob(Job job,int buildNumber){
        return job.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();
    }
}
