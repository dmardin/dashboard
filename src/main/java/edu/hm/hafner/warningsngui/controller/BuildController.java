package edu.hm.hafner.warningsngui.controller;

import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.echarts.ChartModelConfiguration;
import edu.hm.hafner.echarts.LinesChartModel;
import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Job;
import edu.hm.hafner.warningsngui.echart.ToolTrendChart;
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
        model.addAttribute("job", job);
        model.addAttribute("usedTools", usedTools);

        return "build";
    }

    @RequestMapping(path={"/ajax/aggregatedAnalysisResults/{jobName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getAggregatedAnalysisResultsTrendChartsExample(@PathVariable("jobName") String jobName) {
        logger.info("getAggregatedAnalysisResultsTrendChartsExample (ajax) is called");
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> buildResults = buildService.createBuildResultsForAggregatedAnalysisResults(job);
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
}
