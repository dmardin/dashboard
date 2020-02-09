package edu.hm.hafner.warningsngui.ui.table.build;

/**
 * Collects statistics for a Build.
 *
 * @author Deniz Mardin
 */
public class BuildStatistics {

    private int buildNumber;
    private String buildUrl;

    /**
     * Creates a new instance of a {@link BuildStatistics}.
     *
     * @param buildNumber the build number
     * @param buildUrl the build url
     */
    public BuildStatistics(int buildNumber, String buildUrl) {
        this.buildNumber = buildNumber;
        this.buildUrl = buildUrl;
    }

    /**
     * Returns the build number of the build.
     *
     * @return the build number
     */
    public int getBuildNumber() {
        return buildNumber;
    }

    /**
     * Returns the url of the build.
     *
     * @return the url
     */
    public String getBuildUrl() {
        return buildUrl;
    }
}
