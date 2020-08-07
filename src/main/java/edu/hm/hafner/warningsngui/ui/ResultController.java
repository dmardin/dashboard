package edu.hm.hafner.warningsngui.ui;

import edu.hm.hafner.warningsngui.service.UiService;
import edu.hm.hafner.warningsngui.service.echart.resultchart.BarChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Provides the Controller for a specific result of a build.
 *
 * @author Deniz Mardin
 */
@Controller
public class ResultController {
    private final UiService uiService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a new instance of {@link ResultController}.
     *
     * @param uiService the service for interactions with the ui
     */
    public ResultController(UiService uiService) {
        this.uiService = uiService;
    }

    /**
     * Displays the specific results of a build.
     *
     * @param jobName     the name of the job
     * @param buildNumber the build number
     * @param model       the model with the needed build information
     * @return the result page
     */
    @RequestMapping(path = {"/job/{jobName}/build/{buildNumber}"}, method = RequestMethod.GET)
    public String getResults(@PathVariable("jobName") String jobName, @PathVariable("buildNumber") Integer buildNumber, final Model model) {
        logger.info("getResults was called");
        model.addAttribute("build", uiService.getBuildWithBuildNumberFromJob(jobName, buildNumber));
        model.addAttribute("buildNumber", buildNumber);

        return "result";
    }

    /**
     * Ajax call to display a summarize of a result of a build.
     *
     * @param jobName     the name of the job
     * @param buildNumber the build number
     * @param toolName    the name of the used tool (e.g. checkstyle)
     * @return the {@link BarChartModel} with the summarize of a result
     */
    @RequestMapping(path = {"/ajax/job/{jobName}/build/{buildNumber}/{toolName}/result"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public BarChartModel getResultSummarize(@PathVariable("jobName") String jobName,
                                            @PathVariable("buildNumber") Integer buildNumber,
                                            @PathVariable("toolName") String toolName) {
        logger.info("getResultSummarize was called");

        return uiService.getResultSummarize(jobName, buildNumber, toolName);
    }
}
