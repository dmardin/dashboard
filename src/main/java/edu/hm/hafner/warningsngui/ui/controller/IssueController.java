package edu.hm.hafner.warningsngui.ui.controller;

import edu.hm.hafner.warningsngui.service.BuildService;
import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.service.ResultService;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.ui.table.issue.IssueRepositoryStatistics;
import edu.hm.hafner.warningsngui.ui.table.issue.IssueViewTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Provides the Controller for the issue page.
 *
 * @author Deniz Mardin
 */
@Controller
public class IssueController {

    @Autowired
    JobService jobService;
    @Autowired
    BuildService buildService;
    @Autowired
    ResultService resultService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Loads the header of the issues by the total size of issues (containing outstanding and new issues)for the table at the issue page.
     *
     * @param jobName the name of the job
     * @param buildNumber the build number
     * @param toolId the tool id (e.g. checkstyle)
     * @param model the configured model with the headers of the issue
     * @return the issue page
     */
    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}"}, method= RequestMethod.GET)
    public String getIssueHeadersForTool(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            final Model model) {
        IssueViewTable issueViewTable = new IssueViewTable(new IssueRepositoryStatistics());
        model.addAttribute("issueViewTable", issueViewTable);
        model.addAttribute("toolId", toolId);
        model.addAttribute("toolIdWithIssueType", toolId);

        return "issue";
    }

    /**
     * Loads the header of the issues for the table at the issue page with a given issue type e.g. fixed, outstanding or new.
     *
     * @param jobName the name of the job
     * @param buildNumber the build number
     * @param toolId the tool id (e.g. checkstyle)
     * @param issueType the issue type (e.g. fixed, outstanding or new)
     * @param model the configured model with the headers of the issue
     * @return the issue page
     */
    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method=RequestMethod.GET)
    public String getIssueHeaders(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            @PathVariable("issueType") String issueType,
            final Model model) {
        IssueViewTable issueViewTable = new IssueViewTable(new IssueRepositoryStatistics());
        model.addAttribute("issueViewTable", issueViewTable);
        model.addAttribute("toolId", toolId);
        model.addAttribute("issueType", issueType);
        model.addAttribute("toolIdWithIssueType", toolId + " (" + issueType + ")");

        return "issue";
    }

    /**
     * Ajax call to fetch the total size of issues (containing outstanding and new issues) and display them in a table.
     *
     * @param jobName the name of the job
     * @param buildNumber the build number
     * @param toolId the tool id (e.g. checkstyle)
     * @return rows of the table
     */
    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolId}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssuesDataForToolWithTotalSize(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId) {
        logger.info("getIssuesDataForToolWithTotalSize is called");
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);

        return resultService.getOutstandingAndNewIssuesForTool(build, toolId);
    }

    /**
     * Ajax call to fetch the total size of issues (containing outstanding and new issues) and display them in a table.
     *
     * @param jobName the name of the job
     * @param buildNumber the build number
     * @param toolId the tool id (e.g. checkstyle)
     * @param issueType the issue type (e.g. fixed, outstanding or new)
     * @return rows of the table
     */
    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssuesDataForToolWithIssueType(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            @PathVariable("issueType") String issueType) {
        logger.info("getIssuesDataForToolWithIssueType is called");
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);

        return resultService.getIssuesByToolIdAndIssueType(build, toolId, issueType);
    }
}
