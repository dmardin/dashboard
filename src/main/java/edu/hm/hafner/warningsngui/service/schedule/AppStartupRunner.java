package edu.hm.hafner.warningsngui.service.schedule;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.db.mapper.ReportMapper;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.schedule.rest.response.helper.Tool;
import edu.hm.hafner.warningsngui.service.schedule.rest.RestService;
import edu.hm.hafner.warningsngui.service.JobService;
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

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private RestService restService;

    @Autowired
    private JobService jobService;

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
            job.setLastBuildStatus(getBuildStatusFromColor(job.getColor()));

            //Get Builds for every Job form Jenkins
            logger.info("Start requesting Builds for " + job.getName());
            BuildsResponse buildsResponse = restService.getBuilds(job.getUrl() + API_JSON);
            for (Build build : buildsResponse.getBuilds()) {
                job.getBuilds().add(build);
                build.setJob(job);

                //Get used Tools for every Build form Jenkins
                logger.info("Start requesting Tools for build with number " + build.getNumber());
                ToolsResponse toolsResponse = restService.getTools(build.getUrl() + WARNINGS + SLASH + API_JSON);
                if (toolsResponse != null) {
                    Tool[] tools = toolsResponse.getTools();
                    for (Tool tool : tools) {

                        logger.info("Start requesting ToolDetails for Tool with name " + tool.getName());
                        ToolDetailResponse toolDetailResponse = restService.getToolsDetail(build.getUrl() + tool.getId().toLowerCase() + SLASH + API_JSON);

                        Result result = new Result();
                        build.getResults().add(result);
                        result.setBuild(build);
                        result.setWarningId(tool.getId());
                        result.setName(tool.getName());
                        result.setLatestUrl(tool.getLatestUrl());
                        Arrays.stream(toolDetailResponse.getErrorMessages()).forEach(errorMessage -> result.getErrorMessages().add(errorMessage));
                        Arrays.stream(toolDetailResponse.getInfoMessages()).forEach(infoMessage -> result.getInfoMessages().add(infoMessage));
                        result.setFixedSize(toolDetailResponse.getFixedSize());
                        result.setNewSize(toolDetailResponse.getNewSize());
                        result.setQualityGateStatus(toolDetailResponse.getQualityGateStatus());
                        result.setTotalSize(toolDetailResponse.getTotalSize());

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
                                IssueEntity[] issues = issuesResponse.getIssues();
                                for (IssueEntity issue : issues) {
                                    report.add(ReportMapper.map(issue));
                                }
                            }
                        }
                    }
                }
            }
            allJobs.add(job);
        }

        jobService.saveAll(allJobs);
        logger.info("Requested data saved to database");
    }

    private String getBuildStatusFromColor(String color){
        switch (color){
            case "blue":
                return "Success";
            case "red":
                return "Failed";
            default:
                return "Unknown Status for this Build";
        }
    }
}
