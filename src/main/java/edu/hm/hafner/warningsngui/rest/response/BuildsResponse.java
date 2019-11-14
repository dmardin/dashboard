package edu.hm.hafner.warningsngui.rest.response;

import edu.hm.hafner.warningsngui.model.Build;

public class BuildsResponse {
    private Build[] builds;

    public Build[] getBuilds() {
        return builds;
    }

    public void setBuilds(Build[] builds) {
        this.builds = builds;
    }
}
