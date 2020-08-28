package edu.hm.hafner.dashboard.service.rest.response;

import edu.hm.hafner.dashboard.service.dto.Build;

/**
 * Represents a {@link Build}s response from the Jenkins API.
 */
public class BuildsResponse {
    private Build[] builds;

    /**
     * Returns the response of {@link Build}s.
     *
     * @return the {@link Build}s
     */
    public Build[] getBuilds() {
        return builds;
    }

    /**
     * Setter to set the response of {@link Build}s.
     *
     * @param builds the {@link Build}s
     */
    public void setBuilds(final Build[] builds) {
        this.builds = builds;
    }
}
