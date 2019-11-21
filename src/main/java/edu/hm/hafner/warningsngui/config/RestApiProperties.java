package edu.hm.hafner.warningsngui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jenkins.api")
public class RestApiProperties {

    //TODO add security
    private RestConfiguration restConfiguration;

    public RestConfiguration getRestConfiguration() {
        return restConfiguration;
    }

    public void setRestConfiguration(RestConfiguration restConfiguration) {
        this.restConfiguration = restConfiguration;
    }

    public static class RestConfiguration {

        private String endPoint;

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }
    }
}
