package edu.hm.hafner.warningsngui.ui;

import edu.hm.hafner.echarts.LinesChartModel;
import edu.hm.hafner.warningsngui.service.UiService;
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
 * Provides the Controller for the builds.
 *
 * @author Deniz Mardin
 */
@Controller
public class BuildController {
    private final UiService uiService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a new instance of {@link BuildController}.
     *
     * @param uiService the service for interactions with the ui
     */
    @Autowired
    public BuildController(UiService uiService) {
        this.uiService = uiService;
    }

    /**
     * Loads the header of the builds.
     *
     * @param jobName the name of the job
     * @param model the model with the headers of the builds and used tools e.g. checkstyle etc.
     * @return the build page
     */
    @RequestMapping(path={"/job/{jobName}/build"}, method= RequestMethod.GET)
    public String getBuilds(@PathVariable("jobName") String jobName, final Model model) {
        logger.info("getBuilds is called");
        model.addAttribute("usedTools", uiService.getUsedToolsFromLastBuild(jobName));
        model.addAttribute("buildViewTable", uiService.createBuildViewTable());

        return "build";
    }

    /**
     * Ajax call for the table with builds that prepares the rows.
     *
     * @param jobName the name of the job
     * @return rows of the table
     */
    @RequestMapping(path={"/ajax/job/{jobName}/build"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getRowsForBuildViewTable(@PathVariable("jobName") String jobName) {
        logger.info("getRowsForBuildViewTable is called");

        return uiService.getRowsForBuildViewTable(jobName);
    }

    /**
     * Ajax call that prepares the aggregated analysis results as {@link LinesChartModel} to display an echart.
     *
     * @param jobName the name of the job
     * @return the {@link LinesChartModel}
     */
    @RequestMapping(path={"/ajax/aggregatedAnalysisResults/{jobName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getAggregatedAnalysisResultsTrendCharts(@PathVariable("jobName") String jobName) {
        logger.info("getAggregatedAnalysisResultsTrendChartsExample (ajax) is called");

        return uiService.getAggregatedAnalysisResultsTrendCharts(jobName);
    }

    /**
     * Ajax call that prepares a single tool like checkstyle, pmd or spotbugs as {@link LinesChartModel} to display an echart.
     *
     * @param jobName the name of the job
     * @param toolName the name of the used tool
     * @return the {@link LinesChartModel}
     */
    @RequestMapping(path={"/ajax/{jobName}/tool/{toolName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getTrendChartForTool(@PathVariable("jobName") String jobName, @PathVariable("toolName") String toolName) {
        logger.info("getTrendChartForTool (ajax) is called");

        return uiService.getTrendChartForTool(jobName, toolName);
    }

    /**
     * Ajax call to get the aggregated size of new vs fixed issues.
     *
     * @param jobName the name of the project
     * @return the {@link LinesChartModel} model with the size of fixed and new issues for each build
     */
    @RequestMapping(path={"/ajax/{jobName}/newVersusFixedAggregatedTrendChart"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getNewVersusFixedTrendChart(@PathVariable("jobName") String jobName) {
        logger.info("getNewVersusFixedAggregatedTrendChart (ajax) is called");

        return uiService.getNewVersusFixedTrendChart(jobName);
    }

    /**
     * Ajax call to get the size of new vs fixed issues for a given tool (e.g. checkstyle or pmd).
     *
     * @param jobName the name of the project
     * @param toolName the used tool
     * @return the {@link LinesChartModel} with the size of fixed and new issues for each build
     */
    @RequestMapping(path={"/ajax/{jobName}/newVersusFixedTrendChart/{toolName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getNewVersusFixedTrendChartForTool(@PathVariable("jobName") String jobName, @PathVariable("toolName") String toolName) {
        logger.info("getNewVersusFixedTrendChartForTool (ajax) is called");

        return uiService.getNewVersusFixedTrendChartForTool(jobName, toolName);
    }
}
