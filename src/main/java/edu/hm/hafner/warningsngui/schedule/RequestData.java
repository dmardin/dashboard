package edu.hm.hafner.warningsngui.schedule;

import edu.hm.hafner.warningsngui.model.*;
import edu.hm.hafner.warningsngui.repository.JobRepository;
import edu.hm.hafner.warningsngui.rest.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

//TODO check if this is correct

public class RequestData {

    @Autowired
    private RestService restService;

    @Autowired
    private JobRepository jobRepository;

    @Scheduled(initialDelay=2000,fixedRate = 600000)
    public void getDataFromJenkins() {
        System.out.println("Start getting Data");
        List<Job> allJobs = new ArrayList<>();
        JobsPayload jobsPayload = restService.getProjects();
        for (Job job : jobsPayload.getJobs()) {
            if (job.getName().contains("plagi")) {

            //Get Builds for every Job form Jenkins
            BuildsPayload buildsPayload = restService.getBuilds(job.getUrl() + "api/json");
            for (Build build : buildsPayload.getBuilds()) {
                if (job.getBuilds() != null) {
                    job.getBuilds().add(build);
                    build.setJob(job);
                }

                //Get used Tools for every Build form Jenkins
                ToolsPayload toolsPayload = restService.getTools(build.getUrl() + "warnings-ng/" + "api/json");
                if (toolsPayload != null) {
                    Tool[] tools = toolsPayload.getTools();
                    for (Tool tool : tools) {
                        if (tool.getId().contains("style")) {
                            if (build.getTools() != null) {
                                build.getTools().add(tool);
                                tool.setBuild(build);
                            }

                            List<IssueType> issueTypes = new ArrayList<>();
                            issueTypes.add(IssueType.FIXED);
                            issueTypes.add(IssueType.NEW);
                            issueTypes.add(IssueType.OUTSTANDING);
                            for (IssueType issueType : issueTypes) {
                                String url = tool.getLatestUrl() + "/" + issueType.toString().toLowerCase() + "/api/json";
                                IssuesPayload issuesPayload = restService.getIssues(url);
                                if (issuesPayload != null) {
                                    Issue[] issues = issuesPayload.getIssues();
                                    List<Issue> addedIssues = new ArrayList<>();
                                    for (Issue issue : issues) {
                                        //set issue Type!!!
                                        issue.setIssueType(issueType);
                                        tool.getIssues().add(issue);
                                        issue.setTool(tool);
                                        addedIssues.add(issue);

                                    }

                                    allJobs.add(job);
                                }
                            }

                        }
                    }
                }
            }
            }
        }
        jobRepository.saveAll(allJobs);
        System.out.println("Finitooooooooooooooooooooooooooooooooooooooooooooooo");
    }
}
