package edu.hm.hafner.warningsngui.schedule;

import edu.hm.hafner.warningsngui.model.*;
import edu.hm.hafner.warningsngui.repository.JobRepository;
import edu.hm.hafner.warningsngui.rest.RestService;
import edu.hm.hafner.warningsngui.rest.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private RestService restService;

    @Autowired
    private JobRepository jobRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SLASH = "/";
    private static final String API_JSON = "api/json";
    private static final String WARNINGS = "warnings-ng";

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Start requesting data from Jenkins");
        List<Job> allJobs = new ArrayList<>();
        JobsResponse jobsResponse = restService.getProjects();
        for (Job job : jobsResponse.getJobs()) {
            if (job.getName().contains("plagi")) {

                //Get Builds for every Job form Jenkins
                BuildsResponse buildsResponse = restService.getBuilds(job.getUrl() + API_JSON);
                for (Build build : buildsResponse.getBuilds()) {
                    if (job.getBuilds() != null) {
                        job.getBuilds().add(build);
                        build.setJob(job);
                    }

                    //Get used Tools for every Build form Jenkins
                    ToolsResponse toolsResponse = restService.getTools(build.getUrl() + WARNINGS + SLASH + API_JSON);
                    if (toolsResponse != null) {
                        Tool[] tools = toolsResponse.getTools();
                        for (Tool tool : tools) {

                            ToolDetailResponse toolDetailResponse = restService.getToolsDetail(build.getUrl() + tool.getId().toLowerCase() + SLASH + API_JSON);

                            ResultEntity result = new ResultEntity();
                            build.getResultEntities().add(result);

                            result.setName(tool.getName());
                            result.setTotal(result.getTotal() + tool.getSize());
                            result.setBuild(build);

                            List<ErrorMessage> errorMessages = new ArrayList<>();
                            for (String err : toolDetailResponse.getErrorMessages()) {
                                ErrorMessage em = new ErrorMessage();
                                em.setMessage(err);
                                em.setResult(result);
                                errorMessages.add(em);
                            }
                            result.setErrorMessages(errorMessages);

                            List<InfoMessage> infoMessages = new ArrayList<>();
                            for (String info : toolDetailResponse.getInfoMessages()) {
                                InfoMessage im = new InfoMessage();
                                im.setMessage(info);
                                im.setResult(result);
                                infoMessages.add(im);
                            }

                            result.setInfoMessages(infoMessages);
                            result.setFixedSize(toolDetailResponse.getFixedSize());
                            result.setNewSize(toolDetailResponse.getNewSize());
                            result.setQualityGateStatus(toolDetailResponse.getQualityGateStatus());
                            result.setTotalSize(toolDetailResponse.getTotalSize());

                            for (WarningType warningType : WarningType.values()) {
                                IssuesEntity issuesEntity = new IssuesEntity();
                                issuesEntity.setResultEntity(result);
                                result.getIssues().add(issuesEntity);
                                issuesEntity.setWarningType(warningType);
                                String url = tool.getLatestUrl() + SLASH + warningType.toString().toLowerCase() + SLASH + API_JSON;
                                IssuesResponse issuesResponse = restService.getIssues(url);
                                if (issuesResponse != null) {
                                    IssueEntity[] issues = issuesResponse.getIssues();
                                    for (IssueEntity issue : issues) {
                                        issuesEntity.getIssues().add(issue);
                                        issue.setIssues(issuesEntity);
                                    }

                                }

                            }
                        }
                    }
                }
            }
            allJobs.add(job);
        }
        jobRepository.saveAll(allJobs);
        logger.info("Requested data saved to database");
    }
}
