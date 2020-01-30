package edu.hm.hafner.warningsngui.controller;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.echarts.ChartModelConfiguration;
import edu.hm.hafner.echarts.LinesChartModel;
import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Job;
import edu.hm.hafner.warningsngui.dto.Result;
import edu.hm.hafner.warningsngui.dto.table.issues.IssueStatistics;
import edu.hm.hafner.warningsngui.dto.table.issues.IssueViewTable;
import edu.hm.hafner.warningsngui.dto.table.issues.RepoStatistics;
import edu.hm.hafner.warningsngui.echart.BarChartModel;
import edu.hm.hafner.warningsngui.echart.ResultChart;
import edu.hm.hafner.warningsngui.echart.ToolTrendChart;
import edu.hm.hafner.warningsngui.service.JobService;
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
public class HomeController {

    @Autowired
    JobService jobService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET/*, params = {"getProjects"}*/)
    public String getProjects(final Model model) {
        logger.info("GET with Parameter was called");
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        model.addAttribute("jobs", jobs);
        return "home";
    }

    @RequestMapping(path={"/job/{jobName}/build"}, method=RequestMethod.GET)
    public String getBuilds(@PathVariable("jobName") String jobName, final Model model) {
        //TODO check if this is used!
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        model.addAttribute("job", neededJob);
        model.addAttribute("jobName", jobName);
        logger.info("Normal GET was called");

        ArrayList<String> tools = new ArrayList<>();
        Build build = jobs.get(0).getBuilds().get(0);
        for(Result r : build.getResults()) {
            tools.add(r.getName());
        }
        model.addAttribute("usedtools", tools);


        return "build";
    }

    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}"}, method=RequestMethod.GET)
    public String getResults(@PathVariable("jobName") String jobName, @PathVariable("buildNumber") Integer buildNumber,final Model model) {
        //TODO check if this is used!
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build build = neededJob.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();
        model.addAttribute("build", build);
        model.addAttribute("buildNumber",buildNumber);
        logger.info("Normal GET was called");
        return "result";
    }

    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}"}, method=RequestMethod.GET)
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

    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolId}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssueDataForTool(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId) {
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build build = neededJob.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();
        Result result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
        Report report = new Report();
        report.addAll(result.getOutstandingIssues());
        report.addAll(result.getNewIssues());

        return convertDataForAjax(report);
    }

    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}/messages"}, method=RequestMethod.GET)
    public String getInfoAndErrorMessages(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            final Model model) {
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build build = neededJob.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();
        Result result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();

        model.addAttribute("infoMessages", result.getInfoMessages());
        model.addAttribute("errorMessages", result.getErrorMessages());
        model.addAttribute("toolId", toolId);
        model.addAttribute("toolIdWithMessage", toolId + " / messages");
        return "message";
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

    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getIssueData(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            @PathVariable("issueType") String issueType) {
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build build = neededJob.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();

        logger.info("Normal GET was called");
        Report report = null;
        switch (issueType) {
            case "outstanding":
                Result result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
                report = result.getOutstandingIssues();
                break;
            case "fixed":
                result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
                report = result.getFixedIssues();
                break;
            case "new":
                result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
                report = result.getNewIssues();
                break;
        }

        return convertDataForAjax(report);
    }

    private List<Object> convertDataForAjax(Report report) {
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
    
    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolName}/result"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    BarChartModel getResultSummarize(@PathVariable("jobName") String jobName,
                     @PathVariable("buildNumber") Integer buildNumber,
                     @PathVariable("toolName") String toolName) {
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build neededBuild = neededJob.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();
        Result result = neededBuild.getResults().stream().filter(r -> r.getName().equals(toolName)).findFirst().get();

        ResultChart resultChart = new ResultChart();
        BarChartModel model = resultChart.create(result);

        return model;
    }

    @RequestMapping(path={"/ajax/aggregatedAnalysisResults/{jobName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getAggregatedAnalysisResultsTrendChartsExample(@PathVariable("jobName") String jobName) {
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();

        List<BuildResult<Build>> buildResults = new ArrayList<>();
        for (Build b : neededJob.getBuilds()) {
            //TODO BuildTime
            edu.hm.hafner.echarts.Build build = new edu.hm.hafner.echarts.Build(b.getNumber(), "#" + b.getNumber(), 0);
            BuildResult<Build> buildBuildResult = new BuildResult<>(build, b);
            buildResults.add(buildBuildResult);
        }

        ToolTrendChart toolTrendChart = new ToolTrendChart();
        LinesChartModel model = toolTrendChart.create(buildResults, new ChartModelConfiguration());

        return model;
    }

    @RequestMapping(path={"/ajax/{jobName}/tool/{toolName}"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public LinesChartModel getTrendChartsExample(@PathVariable("jobName") String jobName, @PathVariable("toolName") String toolName) {
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        List<BuildResult<Build>> results = new ArrayList<>();


        neededJob.getBuilds().forEach(b -> {
            edu.hm.hafner.echarts.Build build = new edu.hm.hafner.echarts.Build(b.getNumber(), "#" + b.getNumber(), 0);
            Build neededBuild = new Build();
            ArrayList<Result> resultArrayList = new ArrayList<>();
            for (Result result : b.getResults()) {
                if (result.getName().equals(toolName)) {
                    resultArrayList.add(result);
                    neededBuild.setResults(resultArrayList);
                }
            }

            BuildResult<Build> my = new BuildResult<>(build, neededBuild);
            results.add(my);
        });

        ToolTrendChart toolTrendChart = new ToolTrendChart();
        LinesChartModel model = toolTrendChart.create(results, new ChartModelConfiguration());

        return model;
    }
}
