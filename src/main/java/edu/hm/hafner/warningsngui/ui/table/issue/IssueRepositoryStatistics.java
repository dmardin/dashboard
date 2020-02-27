package edu.hm.hafner.warningsngui.ui.table.issue;

import edu.hm.hafner.analysis.Issue;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides access to all repository issues.
 *
 * @author Deniz Mardin
 */
public class IssueRepositoryStatistics {
    private final Map<UUID, Issue> statisticsPerIssue = new HashMap<>();

    /**
     * Checks if the repository is empty.
     *
     * @return {@code true} if the repository is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return statisticsPerIssue.isEmpty();
    }

    /**
     * Returns the number of issues in the repository.
     *
     * @return the number of issues
     */
    public int size() {
        return statisticsPerIssue.size();
    }

    /**
     * Checks if the given issue is contained by the repository.
     *
     * @param issueId the id of the issue
     * @return {@code true} if the issue is in the repository, otherwise {@code false}
     */
    public boolean contains(final UUID issueId) {
        return statisticsPerIssue.containsKey(issueId);

    }

    /**
     * Returns the issue ids that are in the repository.
     *
     * @return the ids of the issues
     */
    public Set<UUID> getIssues() {
        return statisticsPerIssue.keySet();
    }

    /**
     * Returns the values of the repository with all issue statistics.
     *
     * @return all issue statistics
     */
    public Collection<Issue> getIssue() {
        return statisticsPerIssue.values();
    }

    /**
     * Returns the statistics for the given issue.
     *
     * @param issueId the id of the issue
     * @return the statistic of the issue
     */
    public Issue get(final UUID issueId) {
        if (contains(issueId)) {
            return statisticsPerIssue.get(issueId);
        }
        throw new NoSuchElementException(String.format("No information for issue %s stored", issueId));
    }

    /**
     *Adds additional statistics to the repository.
     *
     * @param additionalStatistics the issue statistics to add
     */
    public void addAll(final Collection<Issue> additionalStatistics) {
        statisticsPerIssue.putAll(
                additionalStatistics.stream().collect(Collectors.toMap(issueStatistics -> issueStatistics.getId(), Function.identity())));
    }

    /**
     *Adds additional statistics to the repository.
     *
     * @param additionalStatistics the issue statistics to add
     */
    public void addAll(final IssueRepositoryStatistics additionalStatistics) {
        addAll(additionalStatistics.getIssue());
    }

    /**
     *Adds a issue statistic to the repository.
     *
     * @param additionalStatistics the additional issue statistic to add
     */
    public void add(final Issue additionalStatistics) {
        statisticsPerIssue.put(additionalStatistics.getId(), additionalStatistics);
    }
}
