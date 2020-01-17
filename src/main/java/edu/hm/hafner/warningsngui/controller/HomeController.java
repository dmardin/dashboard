package edu.hm.hafner.warningsngui.controller;

import com.google.gson.Gson;
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
import edu.hm.hafner.warningsngui.echart.ToolTrendChart;
import edu.hm.hafner.warningsngui.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // /job/kniffel/build/6
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

    //http://localhost:8181/job/kniffel/build/4/checkstyle/outstanding
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

    @RequestMapping(path = "/ajax/checkstyle", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<String> getCheckstyle() {
//        Job job = jobRepository.fetchJobWithId(1);
        return createTestResponseFrom();
    }

    @RequestMapping(path = "/ajax/grafik", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    Object getGrafik() {
//        Job job = jobRepository.fetchJobWithId(1);
        return grafik();
    }

    private ResponseEntity<String> grafik() {
        String test = "{\n" +
                "  \"series\": [\n" +
                "    {\n" +
                "      \"name\": \"Old Total Size\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"data\": [\n" +
                "        320\n" +
                "      ],\n" +
                "      \"label\": {\n" +
                "        \"show\": true,\n" +
                "        \"position\": \"inside\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Fixed\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"stack\": \"a\",\n" +
                "      \"data\": [\n" +
                "        120\n" +
                "      ],\n" +
                "      \"label\": {\n" +
                "        \"show\": true,\n" +
                "        \"position\": \"inside\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Outstanding\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"stack\": \"a\",\n" +
                "      \"data\": [\n" +
                "        200\n" +
                "      ],\n" +
                "      \"label\": {\n" +
                "        \"show\": true,\n" +
                "        \"position\": \"inside\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"New\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"stack\": \"a\",\n" +
                "      \"data\": [\n" +
                "        50\n" +
                "      ],\n" +
                "      \"label\": {\n" +
                "        \"show\": true,\n" +
                "        \"position\": \"inside\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"stack\": \"b\",\n" +
                "      \"itemStyle\": {\n" +
                "        \"barBorderColor\": \"rgba(0,0,0,0)\",\n" +
                "        \"color\": \"rgba(0,0,0,0)\"\n" +
                "      },\n" +
                "      \"emphasis\": {\n" +
                "        \"itemStyle\": {\n" +
                "          \"barBorderColor\": \"rgba(0,0,0,0)\",\n" +
                "          \"color\": \"rgba(0,0,0,0)\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"data\": [\n" +
                "        120\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"New Total Size\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"stack\": \"b\",\n" +
                "      \"data\": [\n" +
                "        250\n" +
                "      ],\n" +
                "      \"label\": {\n" +
                "        \"show\": true,\n" +
                "        \"position\": \"inside\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "\"legendData\": [\n" +
                "    \"Old Total Size\",\n" +
                "    \"Fixed\",\n" +
                "    \"Outstanding\",\n" +
                "    \"New\",\n" +
                "    \"New Total Size\"\n" +
                "  ]\n" +

                "}";
        return ResponseEntity.ok(new Gson().toJson(test));
    }

    private ResponseEntity<String> createTestResponseFrom() {
        String testResponse = "{\n" +
                "   \"series\":[\n" +
                "      {\n" +
                "         \"name\":\"Error\",\n" +
                "         \"type\":\"line\",\n" +
                "         \"symbol\":\"circle\",\n" +
                "         \"data\":[\n" +
                "            1869,\n" +
                "            1869,\n" +
                "            1831,\n" +
                "            1791,\n" +
                "            1791,\n" +
                "            1730,\n" +
                "            1693,\n" +
                "            1567,\n" +
                "            1517,\n" +
                "            1353,\n" +
                "            1265,\n" +
                "            1227,\n" +
                "            1172,\n" +
                "            1180,\n" +
                "            1172\n" +
                "         ],\n" +
                "         \"itemStyle\":{\n" +
                "            \"color\":\"#EF9A9A\"\n" +
                "         },\n" +
                "         \"areaStyle\":{\n" +
                "            \"normal\":true\n" +
                "         },\n" +
                "         \"stack\":\"stacked\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"id\":\"\",\n" +
                "   \"xAxisLabels\":[\n" +
                "      \"#3\",\n" +
                "      \"#4\",\n" +
                "      \"#5\",\n" +
                "      \"#6\",\n" +
                "      \"#7\",\n" +
                "      \"#8\",\n" +
                "      \"#9\",\n" +
                "      \"#10\",\n" +
                "      \"#11\",\n" +
                "      \"#12\",\n" +
                "      \"#13\",\n" +
                "      \"#14\",\n" +
                "      \"#15\",\n" +
                "      \"#16\",\n" +
                "      \"#17\"\n" +
                "   ]\n" +
                "}";
        return ResponseEntity.ok(new Gson().toJson(testResponse));
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

        /*
        ToolSeriesBuilder builder = new ToolSeriesBuilder();
        LinesDataSet dataSet = builder.createDataSet(new ChartModelConfiguration(), results);

        LinesChartModel model = new LinesChartModel();
        model.setDomainAxisLabels(dataSet.getDomainAxisLabels());
        model.setBuildNumbers(dataSet.getBuildNumbers());
        List<Integer> error1 = dataSet.getSeries("checkstyle");
        LineSeries lineSeries = new LineSeries("checkstyle Fixed", "#c660ff", LineSeries.StackedMode.STACKED, LineSeries.FilledMode.FILLED);
        lineSeries.addAll(error1);
        model.addSeries(lineSeries);
        */

        return model;
    }
}
