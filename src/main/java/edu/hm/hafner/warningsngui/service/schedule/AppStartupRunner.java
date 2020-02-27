package edu.hm.hafner.warningsngui.service.schedule;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.db.mapper.IssueMapper;
import edu.hm.hafner.warningsngui.db.mapper.JobMapper;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.BuildService;
import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.service.schedule.rest.RestService;
import edu.hm.hafner.warningsngui.service.schedule.rest.response.*;
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

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private RestService restService;

    @Autowired
    private JobService jobService;

    @Autowired
    private BuildService buildService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SLASH = "/";
    private static final String API_JSON = "api/json";
    private static final String WARNINGS = "warnings-ng";

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Start requesting data from Jenkins");
        List<Job> allJobs = new ArrayList<>();
        JobsResponse jobsResponse = restService.getProjects();
        logger.info("Start requesting Jobs");
        for (Job job : jobsResponse.getJobs()) {
            logger.info("Start requesting Builds for " + job.getName());
            BuildsResponse buildsResponse = restService.getBuilds(job.getUrl() + API_JSON);
            Job fetchedJob = jobService.findJobByName(job.getName());
            if (fetchedJob != null) {
                int buildNumberFromDatabaseJob = buildService.getLatestBuild(fetchedJob).getNumber();
                List<Build> newBuilds = Arrays.stream(buildsResponse.getBuilds()).filter(build -> build.getNumber() > buildNumberFromDatabaseJob).collect(Collectors.toList());
                if (!newBuilds.isEmpty()) {
                    fetchedJob.setLastBuildStatus(getBuildStatusFromColor(job.getColor()));
                    addBuildsToJob(fetchedJob, newBuilds);
                    buildService.saveAll(newBuilds, JobMapper.mapToEntity(fetchedJob));
                }
            } else {
                job.setLastBuildStatus(getBuildStatusFromColor(job.getColor()));
                addBuildsToJob(job, Arrays.asList(buildsResponse.getBuilds()));
                allJobs.add(job);
            }
        }
        if (!allJobs.isEmpty()) {
            jobService.saveAll(allJobs);
        }
        logger.info("Requested data saved to database");
    }

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
                    ToolDetailResponse toolDetailResponse = restService.getToolsDetail(build.getUrl() + tool.getId().toLowerCase() + SLASH + API_JSON);

                    Result result = new Result();
                    result.setWarningId(tool.getId());
                    result.setName(tool.getName());
                    result.setLatestUrl(tool.getLatestUrl());
                    Arrays.stream(toolDetailResponse.getErrorMessages()).forEach(errorMessage -> result.getErrorMessages().add(errorMessage));
                    Arrays.stream(toolDetailResponse.getInfoMessages()).forEach(infoMessage -> result.getInfoMessages().add(infoMessage));
                    result.setFixedSize(toolDetailResponse.getFixedSize());
                    result.setNewSize(toolDetailResponse.getNewSize());
                    result.setQualityGateStatus(toolDetailResponse.getQualityGateStatus());
                    result.setTotalSize(toolDetailResponse.getTotalSize());
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
                            IssueEntity[] issuesEntities = issuesResponse.getIssues();//TODO streams?
                            for (IssueEntity issueEntity : issuesEntities) {
                                report.add(IssueMapper.map(issueEntity));
                            }
                        }
                    }
                }
            }
        }
    }

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
