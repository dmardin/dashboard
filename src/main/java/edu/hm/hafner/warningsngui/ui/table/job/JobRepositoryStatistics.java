package edu.hm.hafner.warningsngui.ui.table.job;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides access to all repository jobs.
 *
 * @author Deniz Mardin
 */
public class JobRepositoryStatistics {

    private final Map<String, JobStatistics> statisticsPerJob = new HashMap<>();

    /**
     * Returns the statistics for the given job.
     *
     * @param jobName the job name
     * @return the statistic of the job
     */
    public JobStatistics get(final String jobName) {
        if (contains(jobName)) {
            return statisticsPerJob.get(jobName);
        }
        throw new NoSuchElementException(String.format("No information for job %s stored", jobName));
    }

    /**
     * Checks if the repository is empty.
     *
     * @return {@code true} if the repository is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return statisticsPerJob.isEmpty();
    }

    /**
     * Returns the number of jobs in the repository.
     *
     * @return the number of jobs
     */
    public int size() {
        return statisticsPerJob.size();
    }

    /**
     * Checks if the given job is contained by the repository.
     *
     * @param jobName the name of the job
     * @return {@code true} if the job is in the repository, otherwise {@code false}
     */
    public boolean contains(final String jobName) {
        return statisticsPerJob.containsKey(jobName);
    }

    /**
     * Returns the job names that are in the repository.
     *
     * @return the job names as a Set
     */
    public Set<String> getJobs() {
        return statisticsPerJob.keySet();
    }

    /**
     * Returns the values of the repository with all job statistics.
     *
     * @return all job statistics
     */
    public Collection<JobStatistics> getJobStatistics() {
        return statisticsPerJob.values();
    }

    /**
     * Adds additional statistics to the repository.
     *
     * @param additionalStatistics the job statistics to add
     */
    public void addAll(final Collection<JobStatistics> additionalStatistics) {
        statisticsPerJob.putAll(
                additionalStatistics.stream().collect(Collectors.toMap(JobStatistics::getJobName, Function.identity())));
    }

    /**
     * Adds additional statistics to the repository.
     *
     * @param additionalStatistics the job statistics to add
     */
    public void addAll(final JobRepositoryStatistics additionalStatistics) {
        addAll(additionalStatistics.getJobStatistics());
    }

    /**
     * Adds a job statistic to the repository.
     *
     * @param additionalStatistics the additional job statistic to add
     */
    public void add(final JobStatistics additionalStatistics) {
        statisticsPerJob.put(additionalStatistics.getJobName(), additionalStatistics);
    }
}
