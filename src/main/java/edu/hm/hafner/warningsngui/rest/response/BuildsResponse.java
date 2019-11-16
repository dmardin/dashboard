package edu.hm.hafner.warningsngui.rest.response;

import edu.hm.hafner.warningsngui.dto.Build;

public class BuildsResponse {
    private Build[] builds;

    public Build[] getBuilds() {
        return builds;
    }

    public void setBuilds(Build[] builds) {
        this.builds = builds;
    }
}
