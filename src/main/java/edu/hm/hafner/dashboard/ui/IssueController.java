package edu.hm.hafner.dashboard.ui;

import edu.hm.hafner.dashboard.service.UiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides the Controller for the issue page.
 *
 * @author Deniz Mardin
 */
@Controller
public class IssueController {
    private final UiService uiService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a new instance of {@link IssueController}.
     *
     * @param uiService the service for interactions with the ui
     */
    @Autowired
    public IssueController(final UiService uiService) {
        this.uiService = uiService;
    }

    /**
     * Loads the header of the issues by the total size of issues (containing outstanding and new issues)for the table at the issue page.
     *
     * @param jobName     the name of the job
     * @param buildNumber the build number
     * @param toolId      the tool id (e.g. checkstyle)
     * @param model       the configured model with the headers of the issue
     * @param fetchData   boolean to decide if new data should be fetched
     * @return the issue page
     */
    @RequestMapping(path = {"/job/{jobName}/build/{buildNumber}/{toolId}"}, method = RequestMethod.GET)
    public String getIssueHeadersForTool(
            final @PathVariable("jobName") String jobName,
            final @PathVariable("buildNumber") Integer buildNumber,
            final @PathVariable("toolId") String toolId,
            final Model model,
            final @RequestParam(required = false) boolean fetchData) {
        if (fetchData) {
            logger.info("fetching new data..");
            uiService.fetchData();
        }
        model.addAttribute("issueViewTable", uiService.createIssueViewTable());
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
    @RequestMapping(path = {"/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method = RequestMethod.GET)
    public String getIssueHeaders(
            final @PathVariable("jobName") String jobName,
            final @PathVariable("buildNumber") Integer buildNumber,
            final @PathVariable("toolId") String toolId,
            final @PathVariable("issueType") String issueType,
            final Model model) {
        model.addAttribute("issueViewTable", uiService.createIssueViewTable());
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
    @RequestMapping(path = {"/ajax/job/{jobName}/build/{buildNumber}/{toolId}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssuesDataForToolWithTotalSize(
            final @PathVariable("jobName") String jobName,
            final @PathVariable("buildNumber") Integer buildNumber,
            final @PathVariable("toolId") String toolId) {
        logger.info("getIssuesDataForToolWithTotalSize is called");

        return uiService.getIssuesDataForToolWithTotalSize(jobName, buildNumber, toolId);
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
    @RequestMapping(path = {"/ajax/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssuesDataForToolWithIssueType(
            final @PathVariable("jobName") String jobName,
            final @PathVariable("buildNumber") Integer buildNumber,
            final @PathVariable("toolId") String toolId,
            final @PathVariable("issueType") String issueType) {
        logger.info("getIssuesDataForToolWithIssueType is called");

        return uiService.getIssuesDataForToolWithIssueType(jobName, buildNumber, toolId, issueType);
    }
}
