package edu.hm.hafner.dashboard.ui;

import edu.hm.hafner.dashboard.service.UiService;
import edu.hm.hafner.dashboard.service.echart.resultchart.BarChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * @param fetchData   boolean to decide if new data should be fetched
     * @return the result page
     */
    @RequestMapping(path = {"/job/{jobName}/build/{buildNumber}"}, method = RequestMethod.GET)
    public String getResults(@PathVariable("jobName") String jobName, @PathVariable("buildNumber") Integer buildNumber, final Model model, @RequestParam(required = false) boolean fetchData) {
        logger.info("getResults was called");
        if(fetchData){
            logger.info("fetching new data..");
            uiService.fetchData();
        }
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
