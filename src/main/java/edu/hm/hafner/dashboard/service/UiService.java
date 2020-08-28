package edu.hm.hafner.dashboard.service;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.dashboard.service.dto.Job;
import edu.hm.hafner.dashboard.service.dto.Result;
import edu.hm.hafner.dashboard.service.echart.newvsfixedchart.NewVersusFixedAggregatedTrendChart;
import edu.hm.hafner.dashboard.service.echart.newvsfixedchart.NewVersusFixedTrendChart;
import edu.hm.hafner.dashboard.service.echart.resultchart.BarChartModel;
import edu.hm.hafner.dashboard.service.echart.resultchart.ResultChart;
import edu.hm.hafner.dashboard.service.echart.severitytrendchart.SeverityTrendChart;
import edu.hm.hafner.dashboard.service.echart.tooltrendchart.AggregatedToolTrendChart;
import edu.hm.hafner.dashboard.service.echart.tooltrendchart.ToolTrendChart;
import edu.hm.hafner.dashboard.service.table.build.BuildViewTable;
import edu.hm.hafner.dashboard.service.table.issue.IssueViewTable;
import edu.hm.hafner.dashboard.service.table.job.JobViewTable;
import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.echarts.ChartModelConfiguration;
import edu.hm.hafner.echarts.LinesChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Service to handle interaction in the ui by using the services {@link JobService}, {@link BuildService}, {@link ResultService}.
 *
 * @author Deniz Mardin
 */
@Service
public class UiService {
    private final JobService jobService;
    private final BuildService buildService;
    private final ResultService resultService;
    private final AppStartupRunner appStartupRunner;

    /**
     * Creates a new instance of {@link UiService}.
     *
     * @param jobService       the service for the jobs
     * @param buildService     the service for the builds
     * @param resultService    the service for the results
     * @param appStartupRunner the AppStartupRunner
     */
    @Autowired
    public UiService(final JobService jobService, final BuildService buildService, final ResultService resultService, final AppStartupRunner appStartupRunner) {
        this.jobService = jobService;
        this.buildService = buildService;
        this.resultService = resultService;
        this.appStartupRunner = appStartupRunner;
    }

    /**
     * Creates a new {@link JobViewTable}.
     *
     * @return the JobViewTable
     */
    public JobViewTable createJobViewTable() {
        return jobService.createJobViewTable();
    }

    /**
     * Fetches all jobs from database and converts it to the needed format of table rows.
     *
     * @return prepared table rows
     */
    public List<Object> prepareRowsForJobViewTable() {
        return jobService.convertRowsForTheJobViewTable(jobService.getAllJobs());
    }

    /**
     * Determines the used tools (e.g checkstyle or pmd) for the last {@link Build} of a given {@link Job} name.
     *
     * @param jobName the name of the Job
     * @return the list of {@link String}s with the used tools for the last Build
     */
    public List<String> getUsedToolsFromLastBuild(final String jobName) {
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getLatestBuild(job);

        return resultService.getUsedToolsFromBuild(build);
    }

    /**
     * Creates a new {@link BuildViewTable}.
     *
     * @return the BuildViewTable
     */
    public BuildViewTable createBuildViewTable() {
        return buildService.createBuildViewTable();
    }

    /**
     * Method for the table with builds that prepares the rows.
     *
     * @param jobName the name of the job
     * @return rows of the table
     */
    public List<Object> getRowsForBuildViewTable(final String jobName) {
        Job job = jobService.findJobByName(jobName);

        return buildService.prepareRowsForBuildViewTable(job.getBuilds());
    }

    /**
     * Method that prepares the aggregated analysis results as {@link LinesChartModel} to display an echart.
     *
     * @param jobName the name of the job
     * @return the {@link LinesChartModel}
     */
    public LinesChartModel getAggregatedAnalysisResultsTrendCharts(final String jobName) {
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> buildResults = buildService.createBuildResults(job);
        AggregatedToolTrendChart toolTrendChart = new AggregatedToolTrendChart();

        return toolTrendChart.create(buildResults, new ChartModelConfiguration());
    }

    /**
     * Method that prepares a single tool like checkstyle, pmd or spotbugs as {@link LinesChartModel} to display an echart.
     *
     * @param jobName  the name of the job
     * @param toolName the name of the used tool
     * @return the {@link LinesChartModel}
     */
    public LinesChartModel getTrendChartForTool(final String jobName, final String toolName) {
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> results = buildService.createBuildResultsForTool(job, toolName);
        ToolTrendChart toolTrendChart = new ToolTrendChart();

        return toolTrendChart.create(results, new ChartModelConfiguration());
    }

    /**
     * Method to get the aggregated size of new vs fixed issues.
     *
     * @param jobName the name of the project
     * @return the {@link LinesChartModel} model with the size of fixed and new issues for each build
     */
    public LinesChartModel getNewVersusFixedAggregatedTrendChart(final String jobName) {
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> buildResults = buildService.createBuildResults(job);
        NewVersusFixedAggregatedTrendChart trendChart = new NewVersusFixedAggregatedTrendChart();

        return trendChart.create(buildResults, new ChartModelConfiguration());
    }

    /**
     * Method to get the size of new vs fixed issues for a given tool (e.g. checkstyle or pmd).
     *
     * @param jobName  the name of the project
     * @param toolName the used tool
     * @return the {@link LinesChartModel} with the size of fixed and new issues for each build
     */
    public LinesChartModel getNewVersusFixedTrendChartForTool(final String jobName, final String toolName) {
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> buildResults = buildService.createBuildResultsForTool(job, toolName);
        NewVersusFixedTrendChart trendChart = new NewVersusFixedTrendChart();

        return trendChart.create(buildResults, new ChartModelConfiguration());
    }

    /**
     * Finds a {@link Build} from a given Job name and given build number .
     *
     * @param jobName     given Job name
     * @param buildNumber the needed build number
     * @return the requested {@link Build}
     */
    public Build getBuildWithBuildNumberFromJob(final String jobName, final Integer buildNumber) {
        Job job = jobService.findJobByName(jobName);

        return buildService.getBuildWithBuildNumberFromJob(job, buildNumber);
    }

    /**
     * Method that provides a summarize of a result of a build.
     *
     * @param jobName     the name of the job
     * @param buildNumber the build number
     * @param toolName    the name of the used tool (e.g. checkstyle)
     * @return the {@link BarChartModel} with the summarize of a result
     */
    public BarChartModel getResultSummarize(final String jobName, final Integer buildNumber, final String toolName) {
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);
        Result result = resultService.getResultByToolId(build, toolName);
        ResultChart resultChart = new ResultChart();

        return resultChart.create(result);
    }

    /**
     * Creates a new {@link IssueViewTable}.
     *
     * @return the IssueViewTable
     */
    public IssueViewTable createIssueViewTable() {
        return resultService.createIssueViewTable();
    }

    /**
     * Method to fetch the total size of issues (containing outstanding and new issues) and returns them.
     *
     * @param jobName     the name of the job
     * @param buildNumber the build number
     * @param toolId      the tool id (e.g. checkstyle)
     * @return rows of the table
     */
    public List<Object> getIssuesDataForToolWithTotalSize(final String jobName, final Integer buildNumber, final String toolId) {
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);

        return resultService.getOutstandingAndNewIssuesForTool(build, toolId);
    }

    /**
     * Method to fetch the size of issues by given IssueType (e.g outstanding, new or fixed) and returns them.
     *
     * @param jobName     the name of the job
     * @param buildNumber the build number
     * @param toolId      the tool id (e.g. checkstyle)
     * @param issueType   the issue type (e.g. fixed, outstanding or new)
     * @return rows of the table
     */
    public List<Object> getIssuesDataForToolWithIssueType(final String jobName, final Integer buildNumber, final String toolId, final String issueType) {
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);

        return resultService.getIssuesByToolIdAndIssueType(build, toolId, issueType);
    }

    /**
     * Method that returns the information messages for a {@link Result} by given {@link Build} and tool id.
     *
     * @param build  the {@link Build}
     * @param toolId the tool id
     * @return the list of information messages
     */
    public List<String> getInfoMessagesFromResultWithToolId(final Build build, final String toolId) {
        return resultService.getInfoMessagesFromResultWithToolId(build, toolId);
    }

    /**
     * Method that returns the error messages for a {@link Result} by given {@link Build} and tool id.
     *
     * @param build  the {@link Build}
     * @param toolId the tool id
     * @return the list of error messages
     */
    public List<String> getErrorMessagesFromResultWithToolId(final Build build, final String toolId) {
        return resultService.getErrorMessagesFromResultWithToolId(build, toolId);
    }

    /**
     * Method to fetch new Data from Jenkins.
     */
    public void fetchData() {
        appStartupRunner.run(new DefaultApplicationArguments());
    }

    /**
     * Method to get {@link LinesChartModel} for the severity of a given tool.
     *
     * @param jobName  the name of the project
     * @param toolName the used tool
     * @return the {@link LinesChartModel} the LinesChartModel for the severity
     */
    public LinesChartModel getSeverityTrendChartForTool(final String jobName, final String toolName) {
        Job job = jobService.findJobByName(jobName);
        List<BuildResult<Build>> results = buildService.createBuildResultsForTool(job, toolName);
        SeverityTrendChart severityTrendChart = new SeverityTrendChart();

        return severityTrendChart.create(results, new ChartModelConfiguration());
    }

}
