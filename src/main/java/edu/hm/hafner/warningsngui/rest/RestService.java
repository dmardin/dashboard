package edu.hm.hafner.warningsngui.rest;

import edu.hm.hafner.warningsngui.model.*;
import edu.hm.hafner.warningsngui.rest.response.*;
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

    public JobsResponse getProjects() {
        JobsResponse jobsResponse = restTemplate.getForObject( "http://localhost:8080/jenkins/api/json", JobsResponse.class);
//        for( int i = 0; i < jobsPayload.getJobs().length; i++){
//            System.out.println(jobsPayload.getJobs()[i].get_class());
//            System.out.println(jobsPayload.getJobs()[i].getId());
//            System.out.println(jobsPayload.getJobs()[i].getName());
//            System.out.println(jobsPayload.getJobs()[i].getColor());
//        }
//        System.out.println("####################################################");
        return jobsResponse;
    }

    public BuildsResponse getBuilds(String url) {
        BuildsResponse buildsResponse = restTemplate.getForObject(url, BuildsResponse.class);
//        for (Build build: buildsPayload.getBuilds()) {
//            System.out.println(build.get_class());
//            System.out.println(build.getNumber());
//            System.out.println(build.getUrl());
//        }
//        System.out.println("####################################################");

        return buildsResponse;
    }

    public ToolsResponse getTools(String url) {
        ToolsResponse toolsResponse = null;
        try{
            toolsResponse = restTemplate.getForObject(url, ToolsResponse.class);
        }
        catch (HttpClientErrorException ex){
            System.out.println("Tools not Found for url: "+ url);
        }
        if (toolsResponse != null) {
            Tool[] tools = toolsResponse.getTools();
//            for (Tool tool : tools) {
//                System.out.println(tool.getId());
//                System.out.println(tool.getLatestUrl());
//                System.out.println(tool.getName());
//                System.out.println(tool.getSize());
//            }
//            System.out.println("####################################################");
        }
        return toolsResponse;
    }

    public ToolDetailResponse getToolsDetail(String url){
        ToolDetailResponse toolDetailResponse = null;
        try{
            toolDetailResponse = restTemplate.getForObject(url, ToolDetailResponse.class);
        }catch (HttpClientErrorException ex) {
            System.out.println("ToolsDetail not Found for url: " + url);
        }
        return toolDetailResponse;
    }

    public IssuesResponse getIssues(String url) {
        IssuesResponse issuesResponse = null;
        try {
            issuesResponse = restTemplate.getForObject(url, IssuesResponse.class);
        }
        catch (HttpClientErrorException ex) {
            System.out.println("Issues not Found for url: "+ url);
        }
        if(issuesResponse != null) {
            IssueEntity[] issues = issuesResponse.getIssues();
//            for(Issue issue : issues) {
//                System.out.println(issue.getBaseName());
//            }
//            System.out.println("####################################################");
        }
        return issuesResponse;
    }
}
