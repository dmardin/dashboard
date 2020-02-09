package edu.hm.hafner.warningsngui.ui.table.job;

/**
 * Collects statistics for a Job.
 *
 * @author Deniz Mardin
 */
public class JobStatistics {

    private String jobName;
    private String jobStatus;
    private String jobUrl;

    /**
     * Creates a new instance of a {@link JobStatistics}.
     *
     * @param jobName the name of job
     * @param jobStatus the status of the job
     * @param jobUrl the url of the job
     */
    public JobStatistics(String jobName, String jobStatus, String jobUrl) {
        this.jobName = jobName;
        this.jobStatus = jobStatus;
        this.jobUrl = jobUrl;
    }

    /**
     * Returns the name of the job.
     *
     * @return the job name
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Returns the status of the job.
     *
     * @return the status
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * Returns the url of the job.
     *
     * @return the url
     */
    public String getJobUrl() {
        return jobUrl;
    }
}
