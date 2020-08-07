package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.service.mapper.Mapper;
import edu.hm.hafner.warningsngui.service.rest.RestService;
import edu.hm.hafner.warningsngui.service.rest.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Startup Runner to request data from the Jenkins API and stores it into the database.
 * If the database is empty all fetched data (jobs. builds, results, reports, issues) will be stored, otherwise only the new jobs or new
 * builds with results, report and issues will be added to the database.
 */
@Component
public class AppStartupRunner implements ApplicationRunner {
    private final RestService restService;
    private final AppStartupService appStartupService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SLASH = "/";
    private static final String API_JSON = "api/json";
    private static final String WARNINGS = "warnings-ng";

    /**
     * Creates a new instance of {@link AppStartupRunner}.
     *
     * @param restService       the needed REST service to Jenkins
     * @param appStartupService the needed app startup service
     */
    @Autowired
    public AppStartupRunner(RestService restService, AppStartupService appStartupService) {
        this.restService = restService;
        this.appStartupService = appStartupService;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Start requesting data from Jenkins");
        List<Job> allJobs = new ArrayList<>();
        JobsResponse jobsResponse = restService.getProjects();
        logger.info("Start requesting Jobs");
        for (Job job : jobsResponse.getJobs()) {
            logger.info("Start requesting Builds for " + job.getName());
            BuildsResponse buildsResponse = restService.getBuilds(job.getUrl() + API_JSON);
            Job fetchedJob = appStartupService.findJobByName(job.getName());
            if (fetchedJob != null) {
                int buildNumberFromDatabaseJob = appStartupService.getLatestBuildNumberFromJob(fetchedJob);
                List<Build> newBuilds = Arrays.stream(buildsResponse.getBuilds()).filter(build -> build.getNumber() > buildNumberFromDatabaseJob).collect(Collectors.toList());
                if (!newBuilds.isEmpty()) {
                    fetchedJob.setLastBuildStatus(getBuildStatusFromColor(job.getColor()));
                    addBuildsToJob(fetchedJob, newBuilds);
                    appStartupService.saveNewBuildsFromJob(fetchedJob, newBuilds);
                }
            } else {
                job.setLastBuildStatus(getBuildStatusFromColor(job.getColor()));
                addBuildsToJob(job, Arrays.asList(buildsResponse.getBuilds()));
                allJobs.add(job);
            }
        }
        if (!allJobs.isEmpty()) {
            appStartupService.saveNewJobs(allJobs);
        }
        logger.info("Requested data saved to database");
    }

    /**
     * Adds to a given {@link Job} the corresponding {@link Build}s, with the {@link Result}s to every {@link Build} and the {@link Report} of fixed,
     * outstanding and new {@link Issue}s.
     *
     * @param job the {@link Job}
     * @param buildList the {@link Build}s
     */
    private void addBuildsToJob(Job job, List<Build> buildList) {
        for (Build build : buildList) {
            job.addBuild(build);

            //Get used Tools for every Build form Jenkins
            logger.info("Start requesting Tools for build with number " + build.getNumber());
            ToolsResponse toolsResponse = restService.getTools(build.getUrl() + WARNINGS + SLASH + API_JSON);
            if (toolsResponse != null) {
                ToolsResponse.Tool[] tools = toolsResponse.getTools();
                for (ToolsResponse.Tool tool : tools) {

                    logger.info("Start requesting ToolDetails for Tool with name " + tool.getName());
                    ResultResponse resultResponse = restService.getToolsDetail(build.getUrl() + tool.getId().toLowerCase() + SLASH + API_JSON);

                    Result result = new Result();
                    result.setWarningId(tool.getId());
                    result.setName(tool.getName());
                    result.setLatestUrl(tool.getLatestUrl());
                    Arrays.stream(resultResponse.getErrorMessages()).forEach(errorMessage -> result.getErrorMessages().add(errorMessage));
                    Arrays.stream(resultResponse.getInfoMessages()).forEach(infoMessage -> result.getInfoMessages().add(infoMessage));
                    result.setFixedSize(resultResponse.getFixedSize());
                    result.setNewSize(resultResponse.getNewSize());
                    result.setQualityGateStatus(resultResponse.getQualityGateStatus());
                    result.setTotalSize(resultResponse.getTotalSize());
                    build.addResult(result);

                    for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
                        Report report = new Report();
                        switch (warningTypeEntity) {
                            case OUTSTANDING:
                                result.setOutstandingIssues(report);
                                break;
                            case NEW:
                                result.setNewIssues(report);
                                break;
                            case FIXED:
                                result.setFixedIssues(report);
                                break;
                        }

                        logger.info("Start requesting " + warningTypeEntity.toString() + " Issues for tool with name " + tool.getName());
                        String url = tool.getLatestUrl() + SLASH + warningTypeEntity.toString().toLowerCase() + SLASH + API_JSON;
                        IssuesResponse issuesResponse = restService.getIssues(url);
                        if (issuesResponse != null) {
                            IssuesResponse.Issue[] issues = issuesResponse.getIssues();
                            for (IssuesResponse.Issue issueEntity : issues) {
                                report.add(Mapper.map(issueEntity));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Maps the color from the response to the build status.
     *
     * @param color the color
     * @return the build status
     */
    private String getBuildStatusFromColor(String color) {
        switch (color) {
            case "blue":
                return "Success";
            case "red":
                return "Failed";
            default:
                return "Unknown Status for this Build";
        }
    }
}
