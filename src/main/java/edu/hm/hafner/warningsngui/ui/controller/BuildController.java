package edu.hm.hafner.warningsngui.ui.controller;

import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.echarts.ChartModelConfiguration;
import edu.hm.hafner.echarts.LinesChartModel;
import edu.hm.hafner.warningsngui.service.BuildService;
import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.service.ResultService;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.ui.echart.NewVersusFixedAggregatedTrendChart;
import edu.hm.hafner.warningsngui.ui.echart.NewVersusFixedTrendChart;
import edu.hm.hafner.warningsngui.ui.echart.ToolTrendChart;
import edu.hm.hafner.warningsngui.ui.table.build.BuildRepositoryStatistics;
import edu.hm.hafner.warningsngui.ui.table.build.BuildViewTable;
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

@Controller
public class BuildController {

    @Autowired
    JobService jobService;
    @Autowired
    BuildService buildService;
    @Autowired
    ResultService resultService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path={"/job/{jobName}/build"}, method= RequestMethod.GET)
    public String getBuilds(@PathVariable("jobName") String jobName, final Model model) {
        logger.info("getBuilds is called");
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getLatestBuild(job);
        List<String> usedTools = resultService.getUsedToolsFromBuild(build);
        BuildViewTable buildViewTable = new BuildViewTable(new BuildRepositoryStatistics());
        model.addAttribute("usedTools", usedTools);
        model.addAttribute("buildViewTable", buildViewTable);

        return "build";
    }

    /**
     * Ajax call for the table with builds that prepares the rows.
     *
     * @return rows of the table
     */
    @RequestMapping(path={"/ajax/job/{jobName}/build"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getRowsForBuildViewTable(@PathVariable("jobName") String jobName) {
        logger.info("getRowsForBuildViewTable is called");
        Job job = jobService.findJobByName(jobName);

        return buildService.prepareRowsForBuildViewTable(job.getBuilds());
    }

    @RequestMapping(path={"/ajax/aggregatedAnalysisResults/{jobName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getAggregatedAnalysisResultsTrendCharts(@PathVariable("jobName") String jobName) {
        logger.info("getAggregatedAnalysisResultsTrendChartsExample (ajax) is called");
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> buildResults = buildService.createBuildResults(job);
        ToolTrendChart toolTrendChart = new ToolTrendChart();
        LinesChartModel model = toolTrendChart.create(buildResults, new ChartModelConfiguration());

        return model;
    }

    @RequestMapping(path={"/ajax/{jobName}/tool/{toolName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getTrendChartForTool(@PathVariable("jobName") String jobName, @PathVariable("toolName") String toolName) {
        logger.info("getTrendChartForTool (ajax) is called");
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> results = buildService.createBuildResultsForTool(job, toolName);
        ToolTrendChart toolTrendChart = new ToolTrendChart();
        LinesChartModel model = toolTrendChart.create(results, new ChartModelConfiguration());

        return model;
    }

    /**
     * Ajax call to get the aggregated size of new vs fixed issues.
     *
     * @param jobName the name of the project
     * @return the lines chart model with the size of fixed and new issues for each build
     */
    @RequestMapping(path={"/ajax/{jobName}/newVersusFixedAggregatedTrendChart"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getNewVersusFixedTrendChart(@PathVariable("jobName") String jobName) {
        logger.info("getNewVersusFixedAggregatedTrendChart (ajax) is called");
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> buildResults = buildService.createBuildResults(job);
        NewVersusFixedAggregatedTrendChart trendChart = new NewVersusFixedAggregatedTrendChart();

        return trendChart.create(buildResults, new ChartModelConfiguration());
    }

    /**
     * Ajax call to get the size of new vs fixed issues for a given tool.
     *
     * @param jobName the name of the project
     * @param toolName the used tool
     * @return the lines chart model with the size of fixed and new issues for each build
     */
    @RequestMapping(path={"/ajax/{jobName}/newVersusFixedTrendChart/{toolName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getNewVersusFixedTrendChartForTool(@PathVariable("jobName") String jobName, @PathVariable("toolName") String toolName) {
        logger.info("getNewVersusFixedTrendChartForTool (ajax) is called");
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> buildResults = buildService.createBuildResultsForTool(job, toolName);
        NewVersusFixedTrendChart trendChart = new NewVersusFixedTrendChart();

        return trendChart.create(buildResults, new ChartModelConfiguration());
    }
}
