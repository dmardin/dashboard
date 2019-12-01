package edu.hm.hafner.warningsngui.tableIssue;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides access to the SCM statistics of all repository files. Additionally,
 * info and error messages during the SCM processing will be stored.
 *
 * @author Ullrich Hafner
 */
public class RepoStatistics {

    private final Map<UUID, IssueStatistics> statisticsPerFile = new HashMap<>();

    /**
     * Returns whether the repository is empty.
     *
     * @return {@code true} if the repository is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return statisticsPerFile.isEmpty();
    }

    /**
     * Returns the number of files in the repository.
     *
     * @return number of files in the repository
     */
    public int size() {
        return statisticsPerFile.size();
    }

    /**
     * Returns whether the specified file is part of the repository.
     *
     * @param fileName
     *         the name of the file
     *
     * @return {@code true} if the file file is part of the repository, {@code false} otherwise
     */
    public boolean contains(final String fileName) {
        return statisticsPerFile.containsKey(fileName);
    }

    /**
     * Returns the absolute file names of the files that are part of the repository.
     *
     * @return the file names
     */
    public Set<UUID> getFiles() {
        return statisticsPerFile.keySet();
    }

    /**
     * Returns the statistics for all repository files.
     *
     * @return the requests
     */
    public Collection<IssueStatistics> getFileStatistics() {
        return statisticsPerFile.values();
    }

    /**
     * Returns the statistics for the specified file.
     *
     * @param fileName
     *         absolute file name
     *
     * @return the statistics for that file
     * @throws NoSuchElementException
     *         if the file name is not registered
     */
    public IssueStatistics get(final String fileName) {
        if (contains(fileName)) {
            return statisticsPerFile.get(fileName);
        }
        throw new NoSuchElementException(String.format("No information for file %s stored", fileName));
    }

    /**
     * Adds all additional file statistics.
     *
     * @param additionalStatistics
     *         the additional statistics to add
     */
    public void addAll(final Collection<IssueStatistics> additionalStatistics) {
        statisticsPerFile.putAll(
                additionalStatistics.stream().collect(Collectors.toMap(IssueStatistics::getUuid, Function.identity())));
    }

    /**
     * Adds all additional file statistics.
     *
     * @param additionalStatistics
     *         the additional statistics to add
     */
    public void addAll(final RepoStatistics additionalStatistics) {
        addAll(additionalStatistics.getFileStatistics());
    }

    /**
     * Adds the additional file statistics instance.
     *
     * @param additionalStatistics
     *         the additional statistics to add
     */
    public void add(final IssueStatistics additionalStatistics) {
        statisticsPerFile.put(additionalStatistics.getUuid(), additionalStatistics);
    }
}
