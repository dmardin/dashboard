package edu.hm.hafner.dashboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to get access to the REST Endpoint from Jenkins.
 * The Endpoint is specified in the applications.properties of this Project.
 *
 * @author Deniz Mardin
 */
@Configuration
@ConfigurationProperties("jenkins.api")
public class RestApiProperties {

    //TODO add security
    private RestConfiguration restConfiguration;

    /**
     * Getter for the {@link RestConfiguration}.
     *
     * @return the configuration for the REST Endpoint
     */
    public RestConfiguration getRestConfiguration() {
        return restConfiguration;
    }

    /**
     * Setter for the {@link RestConfiguration}.
     *
     * @param restConfiguration the new configuration for the REST Endpoint
     */
    public void setRestConfiguration(final RestConfiguration restConfiguration) {
        this.restConfiguration = restConfiguration;
    }

    /**
     * Configuration to get access to the REST Endpoint from Jenkins.
     */
    public static class RestConfiguration {
        private String endPoint;

        /**
         * Getter for the specified Endpoint.
         *
         * @return the Endpoint
         */
        public String getEndPoint() {
            return endPoint;
        }

        /**
         * Setter for the specified Endpoint.
         *
         * @param endPoint the new Endpoint
         */
        public void setEndPoint(final String endPoint) {
            this.endPoint = endPoint;
        }
    }
}
