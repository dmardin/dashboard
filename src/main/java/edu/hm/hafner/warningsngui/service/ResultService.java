package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.service.table.issue.IssueRepositoryStatistics;
import edu.hm.hafner.warningsngui.service.table.issue.IssueViewTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service to handle {@link Result}s between the ui and database.
 */
@Service
class ResultService {

    /**
     * Determines the used tools (e.g checkstyle or pmd) for a given {@link Build}.
     *
     * @param build the {@link Build}
     * @return the list of {@link String}s with the used tools
     */
    public List<String> getUsedToolsFromBuild(Build build) {
        return build.getResults().stream().map(Result::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns the info messages for a {@link Result} by given {@link Build} and tool id.
     *
     * @param build the {@link Build}
     * @param toolId the tool id
     * @return the list of info messages
     */
    public List<String> getInfoMessagesFromResultWithToolId(Build build, String toolId) {
        return getResultFromBuildWithToolId(build, toolId).getInfoMessages();
    }

    /**
     * Returns the error messages for a {@link Result} by given {@link Build} and tool id.
     *
     * @param build the {@link Build}
     * @param toolId the tool id
     * @return the list of error messages
     */
    public List<String> getErrorMessagesFromResultWithToolId(Build build, String toolId) {
        return getResultFromBuildWithToolId(build, toolId).getErrorMessages();
    }

    /**
     * Determines the {@link Result} by given {@link Build} and tool id.
     *
     * @param build the {@link Build}
     * @param toolId the tool id
     * @return the {@link Result}
     */
    public Result getResultByToolId(Build build, String toolId) {
        return build.getResults().stream()
                .filter(r -> r.getWarningId().equals(toolId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Tool id "+ toolId + " for the Build "+ build.getNumber() + " from the job" + build.getJob().getName() + " not found" ));
    }

    /**
     * Determines the outstanding and new issues in a {@link Report} for a given {@link Build} and tool id.
     * The {@link Report} will be converted to the needed format of table rows.
     *
     * @param build the {@link Build}
     * @param toolId the tool id
     * @return prepared table rows
     */
    public List<Object> getOutstandingAndNewIssuesForTool(Build build, String toolId) {
        Result result = getResultByToolId(build, toolId);
        Report report = new Report();
        report.addAll(result.getOutstandingIssues());
        report.addAll(result.getNewIssues());

        return convertRowsForTheIssueViewTable(report);
    }

    /**
     * Determines the {@link Report} by given issue type (e.g. new, fixed or outstanding) and tool id.
     * The {@link Report} will be converted to the needed format of table rows.
     *
     * @param build the {@link Build}
     * @param toolId the tool id
     * @param issueType the issue type (e.g. new, fixed, outstanding)
     * @return prepared table rows
     */
    public List<Object> getIssuesByToolIdAndIssueType(Build build, String toolId, String issueType) {
        Report report = new Report();
        Result result = getResultByToolId(build, toolId);
        switch (issueType) {
            case "outstanding":
                report = result.getOutstandingIssues();
                break;
            case "fixed":
                report = result.getFixedIssues();
                break;
            case "new":
                report = result.getNewIssues();
                break;
        }

        return convertRowsForTheIssueViewTable(report);
    }

    /**
     * Determines the {@link Result} by a given {@link Build} and tool id.
     *
     * @param build the {@link Build}
     * @param toolId the tool id
     * @return the {@link Result}
     */
    private Result getResultFromBuildWithToolId(Build build, String toolId) {
        return build.getResults().stream()
                .filter(r -> r.getWarningId().equals(toolId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Result for the Job "+ build.getJob().getName() + " with the build number"+ build.getJob() + " and the tool id "+ toolId +"not found" ));
    }

    /**
     * Converts a {@link Report} to the needed format of table rows.
     *
     * @param report the {@link Report}
     * @return converted table rows
     */
    private List<Object> convertRowsForTheIssueViewTable(Report report) {
        ArrayList<Issue> issueStatisticsList = new ArrayList<>();
        report.stream().forEach(issueStatisticsList::add);
        IssueRepositoryStatistics repositoryStatistics = new IssueRepositoryStatistics();
        repositoryStatistics.addAll(issueStatisticsList);
        IssueViewTable issueViewTable = new IssueViewTable(repositoryStatistics);

        return issueViewTable.getTableRows("issues");
    }

    /**
     * Creates a new {@link IssueViewTable}.
     *
     * @return the IssueViewTable
     */
    public IssueViewTable createIssueViewTable() {
        return new IssueViewTable(new IssueRepositoryStatistics());
    }
}
