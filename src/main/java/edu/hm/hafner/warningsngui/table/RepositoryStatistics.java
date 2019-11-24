package edu.hm.hafner.warningsngui.table;

import com.google.errorprone.annotations.FormatMethod;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides access to the SCM statistics of all repository files. Additionally,
 * info and error messages during the SCM processing will be stored.
 *
 * @author Ullrich Hafner
 */
public class RepositoryStatistics implements Serializable {
    private static final long serialVersionUID = 3650720039292455024L;

    private final Map<String, FileStatistics> statisticsPerFile = new HashMap<>();

    private final FilteredLog log = new FilteredLog("Errors while mining source control repository:");

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
    public Set<String> getFiles() {
        return statisticsPerFile.keySet();
    }

    /**
     * Returns the statistics for all repository files.
     *
     * @return the requests
     */
    public Collection<FileStatistics> getFileStatistics() {
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
    public FileStatistics get(final String fileName) {
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
    public void addAll(final Collection<FileStatistics> additionalStatistics) {
        statisticsPerFile.putAll(
                additionalStatistics.stream().collect(Collectors.toMap(FileStatistics::getFileName, Function.identity())));
    }

    /**
     * Adds all additional file statistics.
     *
     * @param additionalStatistics
     *         the additional statistics to add
     */
    public void addAll(final RepositoryStatistics additionalStatistics) {
        addAll(additionalStatistics.getFileStatistics());
    }

    /**
     * Adds the additional file statistics instance.
     *
     * @param additionalStatistics
     *         the additional statistics to add
     */
    public void add(final FileStatistics additionalStatistics) {
        statisticsPerFile.put(additionalStatistics.getFileName(), additionalStatistics);
    }

    /**
     * Logs the specified information message. Use this method to log any useful information when composing this log.
     *
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logInfo(final String format, final Object... args) {
        log.logInfo(format, args);
    }

    /**
     * Logs the specified error message. Use this method to log any error when composing this log.
     *
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logError(final String format, final Object... args) {
        log.logError(format, args);
    }

    /**
     * Logs the specified exception. Use this method to log any exception when composing this log.
     *
     * @param exception
     *         the exception to log
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logException(final Exception exception, final String format, final Object... args) {
        log.logException(exception, format, args);
    }

    /**
     * Writes a summary message to the reports' error log that denotes the total number of errors that have been
     * reported.
     */
    public void logSummary() {
        log.logSummary();
    }

    public List<String> getErrorMessages() {
        return log.getErrorMessages();
    }

    public List<String> getInfoMessages() {
        return log.getInfoMessages();
    }
}
