package edu.hm.hafner.warningsngui.ui.table.build;

import edu.hm.hafner.warningsngui.service.dto.Build;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides access to all repository builds.
 * 
 * @author Deniz Mardin
 */
public class BuildRepositoryStatistics {
    private final Map<Integer, Build> statisticsPerBuild = new HashMap<>();

    /**
     * Returns the statistics for the given build.
     *
     * @param buildNumber the build number
     * @return the statistic of the build
     */
    public Build get(final int buildNumber) {
        if (contains(buildNumber)) {
            return statisticsPerBuild.get(buildNumber);
        }
        throw new NoSuchElementException(String.format("No information for build with build number %s stored", buildNumber));
    }

    /**
     * Checks if the repository is empty.
     *
     * @return {@code true} if the repository is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return statisticsPerBuild.isEmpty();
    }

    /**
     * Returns the number of builds in the repository.
     *
     * @return the number of builds
     */
    public int size() {
        return statisticsPerBuild.size();
    }

    /**
     * Checks if the given build is contained by the repository.
     *
     * @param buildNumber the name of the build
     * @return {@code true} if the build is in the repository, otherwise {@code false}
     */
    public boolean contains(final int buildNumber) {
        return statisticsPerBuild.containsKey(buildNumber);
    }

    /**
     * Returns the build numbers that are in the repository.
     *
     * @return the build numbers as a Set
     */
    public Set<Integer> getBuilds() {
        return statisticsPerBuild.keySet();
    }

    /**
     * Returns the values of the repository with all build statistics.
     *
     * @return all build statistics
     */
    public Collection<Build> getBuildStatistics() {
        return statisticsPerBuild.values();
    }

    /**
     * Adds additional statistics to the repository.
     *
     * @param additionalStatistics the build statistics to add
     */
    public void addAll(final Collection<Build> additionalStatistics) {
        statisticsPerBuild.putAll(
                additionalStatistics.stream().collect(Collectors.toMap(Build::getNumber, Function.identity())));
    }

    /**
     * Adds additional statistics to the repository.
     *
     * @param additionalStatistics the build statistics to add
     */
    public void addAll(final BuildRepositoryStatistics additionalStatistics) {
        addAll(additionalStatistics.getBuildStatistics());
    }

    /**
     * Adds a build statistic to the repository.
     *
     * @param additionalStatistics the additional build statistic to add
     */
    public void add(final Build additionalStatistics) {
        statisticsPerBuild.put(additionalStatistics.getNumber(), additionalStatistics);
    } 
    
}
