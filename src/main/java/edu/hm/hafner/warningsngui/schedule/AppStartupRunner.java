package edu.hm.hafner.warningsngui.schedule;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Job;
import edu.hm.hafner.warningsngui.dto.Result;
import edu.hm.hafner.warningsngui.mapper.ReportMapper;
import edu.hm.hafner.warningsngui.model.IssueEntity;
import edu.hm.hafner.warningsngui.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.model.helper.Tool;
import edu.hm.hafner.warningsngui.rest.RestService;
import edu.hm.hafner.warningsngui.rest.response.*;
import edu.hm.hafner.warningsngui.service.JobService;
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

//    @Autowired
//    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String MAIN_URL = "http://localhost:8080/jenkins";
    private static final String SLASH = "/";
    private static final String API_JSON = "api/json";
    private static final String WARNINGS = "warnings-ng";

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Start requesting data from Jenkins");
        List<Job> allJobs = new ArrayList<>();
        JobsResponse jobsResponse = restService.getProjects(MAIN_URL + SLASH + API_JSON);
        logger.info("Start requesting Jobs");
        for (Job job : jobsResponse.getJobs()) {

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

                        //***************************************************************
                        Result result = new Result();
                        build.getResults().add(result);
                        result.setBuild(build);
                        result.setName(tool.getName());
                        Arrays.stream(toolDetailResponse.getErrorMessages()).forEach(errorMessage -> result.getErrorMessages().add(errorMessage));
                        Arrays.stream(toolDetailResponse.getInfoMessages()).forEach(infoMessage -> result.getInfoMessages().add(infoMessage));
                        result.setFixedSize(toolDetailResponse.getFixedSize());
                        result.setNewSize(toolDetailResponse.getNewSize());
                        result.setQualityGateStatus(toolDetailResponse.getQualityGateStatus());
                        result.setTotalSize(toolDetailResponse.getTotalSize());

                        //***************************************************************

//                        ResultEntity result = new ResultEntity();
//                        build.getResultEntities().add(result);
//                        result.setName(tool.getName());
//                        result.setBuildEntity(buildEntity);
//                        Arrays.stream(toolDetailResponse.getErrorMessages()).forEach(errorMessage -> result.getErrorMessages().add(errorMessage));
//                        Arrays.stream(toolDetailResponse.getInfoMessages()).forEach(infoMessage -> result.getInfoMessages().add(infoMessage));
//                        result.setFixedSize(toolDetailResponse.getFixedSize());
//                        result.setNewSize(toolDetailResponse.getNewSize());
//                        result.setQualityGateStatus(toolDetailResponse.getQualityGateStatus());
//                        result.setTotalSize(toolDetailResponse.getTotalSize());

                        for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
                            Report report = new Report();
                            if (warningTypeEntity == WarningTypeEntity.OUTSTANDING){
                                result.setOutstandingIssues(report);
                            } else if (warningTypeEntity == WarningTypeEntity.NEW) {
                                result.setNewIssues(report);
                            } else if (warningTypeEntity == WarningTypeEntity.FIXED) {
                                result.setFixedIssues(report);
                            }


//                            IssuesEntity issuesEntity = new IssuesEntity();
//                            issuesEntity.setResultEntity(result);
//                            result.getIssues().add(issuesEntity);
//                            issuesEntity.setWarningTypeEntity(warningTypeEntity);
                            logger.info("Start requesting " + warningTypeEntity.toString() + " Issues for tool with name " + tool.getName());
                            String url = tool.getLatestUrl() + SLASH + warningTypeEntity.toString().toLowerCase() + SLASH + API_JSON;
                            IssuesResponse issuesResponse = restService.getIssues(url);
                            if (issuesResponse != null) {
                                IssueEntity[] issues = issuesResponse.getIssues();
                                for (IssueEntity issue : issues) {
                                    report.add(ReportMapper.map(issue));
//                                    issuesEntity.getIssues().add(issue);
//                                    issue.setIssues(issuesEntity);
                                }
                            }
                        }
                    }
                }
            }
            allJobs.add(job);
        }
        //TODO Save über JobService
        //jobRepository.saveAll(allJobs); //convert with Mapper
        jobService.saveAll(allJobs);
        logger.info("Requested data saved to database");
    }
}
