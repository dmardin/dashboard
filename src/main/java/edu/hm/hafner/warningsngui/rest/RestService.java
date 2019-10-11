package edu.hm.hafner.warningsngui.rest;

import edu.hm.hafner.warningsngui.model.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    //private static final String URL = "http://localhost:8080/job/Dashboard/7/warnings-ng/api/json";
    private final RestTemplate restTemplate;

    public RestService() {
        this.restTemplate = new RestTemplate();
    }

    public String getDataFromJenkins(String url) {
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
        final String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    public JobsPayload getProjects() {
        JobsPayload jobsPayload = restTemplate.getForObject( "http://localhost:8080/jenkins/api/json", JobsPayload.class);
        for( int i = 0; i < jobsPayload.getJobs().length; i++){
            System.out.println(jobsPayload.getJobs()[i].get_class());
            System.out.println(jobsPayload.getJobs()[i].getId());
            System.out.println(jobsPayload.getJobs()[i].getName());
            System.out.println(jobsPayload.getJobs()[i].getColor());
        }
        System.out.println("####################################################");
        return jobsPayload;
    }

    public BuildsPayload getBuilds(String url) {
        BuildsPayload buildsPayload = restTemplate.getForObject(url, BuildsPayload.class);
        for (Build build: buildsPayload.getBuilds()) {
            System.out.println(build.get_class());
            System.out.println(build.getNumber());
            System.out.println(build.getUrl());
        }
        System.out.println("####################################################");

        return buildsPayload;
    }

    public ToolsPayload getTools(String url) {
        ToolsPayload toolsPayload = null;
        try{
            toolsPayload = restTemplate.getForObject(url, ToolsPayload.class);
        }
        catch (HttpClientErrorException ex){
            System.out.println("Tools not Found for url: "+ url);
        }
        if (toolsPayload != null) {
            Tool[] tools = toolsPayload.getTools();
            for (Tool tool : tools) {
                System.out.println(tool.getId());
                System.out.println(tool.getLatestUrl());
                System.out.println(tool.getName());
                System.out.println(tool.getSize());
            }
            System.out.println("####################################################");
        }
        return toolsPayload;
    }

    public IssuesPayload getIssues(String url) {
        IssuesPayload issuesPayload = null;
        try {
            issuesPayload = restTemplate.getForObject(url, IssuesPayload.class);
        }
        catch (HttpClientErrorException ex) {
            System.out.println("Issues not Found for url: "+ url);
        }
        if(issuesPayload != null) {
            Issue[] issues = issuesPayload.getIssues();
            for(Issue issue : issues) {
                System.out.println(issue.getBaseName());
            }
            System.out.println("####################################################");
        }
        return issuesPayload;
    }
}
