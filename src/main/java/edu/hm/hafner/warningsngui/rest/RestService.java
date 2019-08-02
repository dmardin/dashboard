package edu.hm.hafner.warningsngui.rest;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
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
}
