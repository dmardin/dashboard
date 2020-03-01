package edu.hm.hafner.warningsngui.service.schedule.rest;

import edu.hm.hafner.warningsngui.config.RestApiProperties;
import edu.hm.hafner.warningsngui.service.schedule.rest.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Service to request data from the Jenkins Endpoint.
 */
@Service
public class RestService {

    @Autowired
    private RestApiProperties restApiProperties;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a new instance of a {@link RestService}.
     */
    public RestService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Starts a request to get the jobs as a {@link JobsResponse} from the Jenkins Endpoint.
     *
     * @return the {@link JobsResponse}
     */
    public JobsResponse getProjects() {
        return restTemplate.getForObject( restApiProperties.getRestConfiguration().getEndPoint(), JobsResponse.class);
    }

    /**
     * Starts a request to get the builds as a {@link BuildsResponse} from the Jenkins Endpoint.
     *
     * @param url the url of the Endpoint
     * @return the {@link BuildsResponse}
     */
    public BuildsResponse getBuilds(String url) {
        return restTemplate.getForObject(url, BuildsResponse.class);
    }

    /**
     * Starts a request to get the tools as a {@link ToolsResponse} from the Jenkins Endpoint.
     * The {@link ToolsResponse} is part of {@link edu.hm.hafner.warningsngui.service.dto.Result}.
     *
     * @param url the url of the Endpoint
     * @return the {@link ToolsResponse}
     */
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

    /**
     * Starts a request to get the results as a {@link ResultResponse} from the Jenkins Endpoint.
     * The {@link ResultResponse} is part of {@link edu.hm.hafner.warningsngui.service.dto.Result}.
     *
     * @param url the url of the Endpoint
     * @return the {@link ResultResponse}
     */
    public ResultResponse getToolsDetail(String url){
        ResultResponse resultResponse = null;
        try{
            resultResponse = restTemplate.getForObject(url, ResultResponse.class);
        }catch (HttpClientErrorException ex) {
            logger.info("ToolsDetail not Found for url: " + url);
        }
        return resultResponse;
    }

    /**
     * Starts a request to get the issues as a {@link IssuesResponse} from the Jenkins Endpoint.
     *
     * @param url the url of the Endpoint
     * @return the {@link IssuesResponse}
     */
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
