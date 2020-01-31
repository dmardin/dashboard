package edu.hm.hafner.warningsngui.controller;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Job;
import edu.hm.hafner.warningsngui.dto.Result;
import edu.hm.hafner.warningsngui.dto.table.issues.IssueStatistics;
import edu.hm.hafner.warningsngui.dto.table.issues.IssueViewTable;
import edu.hm.hafner.warningsngui.dto.table.issues.RepoStatistics;
import edu.hm.hafner.warningsngui.service.BuildService;
import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IssueController {

    @Autowired
    JobService jobService;
    @Autowired
    BuildService buildService;
    @Autowired
    ResultService resultService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}"}, method= RequestMethod.GET)
    public String getIssueHeadersForTool(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            final Model model) {
        IssueViewTable issueViewTable = new IssueViewTable(new RepoStatistics());
        model.addAttribute("issueTableRows", issueViewTable);
        model.addAttribute("toolId", toolId);
        model.addAttribute("toolIdWithIssueType", toolId);

        return "issue";
    }

    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method=RequestMethod.GET)
    public String getIssueHeaders(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            @PathVariable("issueType") String issueType,
            final Model model) {
        IssueViewTable issueViewTable = new IssueViewTable(new RepoStatistics());
        model.addAttribute("issueTableRows", issueViewTable);
        model.addAttribute("toolId", toolId);
        model.addAttribute("issueType", issueType);
        model.addAttribute("toolIdWithIssueType", toolId + " / " + issueType);

        return "issue";
    }

    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolId}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssueDataForTool(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId) {
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);
        Result result = resultService.getResultByToolName(build, toolId);
        Report report = new Report();
        report.addAll(result.getOutstandingIssues());
        report.addAll(result.getNewIssues());

        return convertIssuesDataForAjax(report);
    }

    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssueData(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            @PathVariable("issueType") String issueType) {
        logger.info("getIssueData is called");
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);
        Report report = new Report();
        Result result = resultService.getResultByToolName(build, toolId);
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

    private List<Object> convertIssuesDataForAjax(Report report) {
        RepoStatistics repositoryStatistics = new RepoStatistics();
        ArrayList<IssueStatistics> issueStatisticsList = new ArrayList<>();
        report.stream().forEach(issue -> {
            IssueStatistics issueStatistics = new IssueStatistics();
            issueStatistics.setUuid(issue.getId());
            issueStatistics.setFileName(issue.getFileName());
            issueStatistics.setPackageName(issue.getPackageName());
            issueStatistics.setCategory(issue.getCategory());
            issueStatistics.setType(issue.getType());
            issueStatistics.setSeverity(issue.getSeverity().toString());

            issueStatisticsList.add(issueStatistics);
        });

        repositoryStatistics.addAll(issueStatisticsList);
        IssueViewTable issueViewTable = new IssueViewTable(repositoryStatistics);
        return issueViewTable.getTableRows("issues");
    }
}
