package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.ui.table.issue.IssueRepositoryStatistics;
import edu.hm.hafner.warningsngui.ui.table.issue.IssueStatistics;
import edu.hm.hafner.warningsngui.ui.table.issue.IssueViewTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ResultService {

    public List<String> getUsedToolsFromBuild(Build build) {
        return build.getResults().stream().map(Result::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<String> getInfoMessagesFromResultWithToolId(Build build, String toolId) {
        return getResultFromBuildWithToolId(build, toolId).getInfoMessages();
    }

    public List<String> getErrorMessagesFromResultWithToolId(Build build, String toolId) {
        return getResultFromBuildWithToolId(build, toolId).getErrorMessages();
    }

    public Result getResultByToolId(Build build, String toolId) {
        return build.getResults().stream()
                .filter(r -> r.getWarningId().equals(toolId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Tool id "+ toolId + " for the Build "+ build.getNumber() + " from the job" + build.getJob().getName() + " not found" ));
    }

    public List<Object> getOutstandingAndNewIssuesForTool(Build build, String toolId) {
        Result result = getResultByToolId(build, toolId);
        Report report = new Report();
        report.addAll(result.getOutstandingIssues());
        report.addAll(result.getNewIssues());

        return convertIssuesDataForAjax(report);
    }

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

        return convertIssuesDataForAjax(report);
    }

    private Result getResultFromBuildWithToolId(Build build, String toolId) {
        return build.getResults().stream()
                .filter(r -> r.getWarningId().equals(toolId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Result for the Job "+ build.getJob().getName() + " with the build number"+ build.getJob() + " and the tool id "+ toolId +"not found" ));
    }

    private List<Object> convertIssuesDataForAjax(Report report) {
        ArrayList<IssueStatistics> issueStatisticsList = new ArrayList<>();
        report.stream().forEach(issue -> issueStatisticsList.add(new IssueStatistics(issue.getId(), issue.getFileName(), issue.getPackageName(), issue.getCategory(), issue.getType(), issue.getSeverity().toString())));
        IssueRepositoryStatistics repositoryStatistics = new IssueRepositoryStatistics();
        repositoryStatistics.addAll(issueStatisticsList);
        IssueViewTable issueViewTable = new IssueViewTable(repositoryStatistics);

        return issueViewTable.getTableRows("issues");
    }
}
