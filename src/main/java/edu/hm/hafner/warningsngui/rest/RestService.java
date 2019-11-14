package edu.hm.hafner.warningsngui.rest;

import edu.hm.hafner.warningsngui.rest.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RestService() {
        this.restTemplate = new RestTemplate();
    }

    public String getDataFromJenkins(String url) {
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
        return restTemplate.getForObject(url, String.class);
    }

    public JobsResponse getProjects(String url) {
        return restTemplate.getForObject( url, JobsResponse.class);
    }

    public BuildsResponse getBuilds(String url) {
        return restTemplate.getForObject(url, BuildsResponse.class);
    }

    public ToolsResponse getTools(String url) {
        ToolsResponse toolsResponse = null;
        try{
            toolsResponse = restTemplate.getForObject(url, ToolsResponse.class);
        }
        catch (HttpClientErrorException ex){
            logger.info("Tools not Found for url: "+ url);
        }
        return toolsResponse;
    }

    public ToolDetailResponse getToolsDetail(String url){
        ToolDetailResponse toolDetailResponse = null;
        try{
            toolDetailResponse = restTemplate.getForObject(url, ToolDetailResponse.class);
        }catch (HttpClientErrorException ex) {
            logger.info("ToolsDetail not Found for url: " + url);
        }
        return toolDetailResponse;
    }

    public IssuesResponse getIssues(String url) {
        IssuesResponse issuesResponse = null;
        try {
            issuesResponse = restTemplate.getForObject(url, IssuesResponse.class);
        }
        catch (HttpClientErrorException ex) {
            logger.info("Issues not Found for url: "+ url);
        }
        return issuesResponse;
    }
}
