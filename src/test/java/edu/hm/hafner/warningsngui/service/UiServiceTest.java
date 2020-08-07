package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.echarts.LinesChartModel;
import edu.hm.hafner.warningsngui.db.BuildEntityService;
import edu.hm.hafner.warningsngui.db.JobEntityService;
import edu.hm.hafner.warningsngui.db.model.JobEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.service.echart.resultchart.BarChartModel;
import edu.hm.hafner.warningsngui.service.table.build.BuildRepositoryStatistics;
import edu.hm.hafner.warningsngui.service.table.build.BuildTableModel;
import edu.hm.hafner.warningsngui.service.table.build.BuildViewTable;
import edu.hm.hafner.warningsngui.service.table.issue.IssueRepositoryStatistics;
import edu.hm.hafner.warningsngui.service.table.issue.IssueTableModel;
import edu.hm.hafner.warningsngui.service.table.issue.IssueViewTable;
import edu.hm.hafner.warningsngui.service.table.job.JobRepositoryStatistics;
import edu.hm.hafner.warningsngui.service.table.job.JobTableModel;
import edu.hm.hafner.warningsngui.service.table.job.JobViewTable;
import io.jenkins.plugins.datatables.api.TableColumn;
import io.jenkins.plugins.datatables.api.TableModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Deniz Mardin
 */
class UiServiceTest {
    private static final String JOB_NAME = "jobName";
    private static final String SUCCESS = "Success";
    private static final int NUMBER_OF_RESULTS = 3;
    private static final int BUILD_NUMBER = 1;
    private static final int NUMBER_OF_JOBS = 5;
    private static final int NUMBER_OF_BUILDS = 5;

    @Test
    void shouldCreateJobViewTable() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);
        ResultService resultService = mock(ResultService.class);

        UiService uiService = new UiService(jobService, buildService, resultService);
        SoftAssertions.assertSoftly((softly) -> {
            when(jobService.createJobViewTable()).thenReturn(new JobViewTable(new JobRepositoryStatistics()));
            JobViewTable jobViewTable = uiService.createJobViewTable();

            TableModel tableModel = jobViewTable.getTableModel("jobs");
            softly.assertThat(tableModel.getId()).isEqualTo("jobs");
            softly.assertThat(tableModel.getColumnsDefinition()).isEqualTo("[{\"data\": \"jobName\"},{\"data\": \"jobStatus\"},{\"data\": \"jobUrl\"}]");
            softly.assertThat(tableModel.getRows()).isEmpty();
            softly.assertThat(jobViewTable.getTableRows("jobs")).isEmpty();

            List<TableColumn> tc = tableModel.getColumns();
            softly.assertThat(tc.size()).isEqualTo(3);
            softly.assertThat(tc.get(0).getHeaderLabel()).isEqualTo("Job Name");
            softly.assertThat(tc.get(0).getDefinition()).isEqualTo("{\"data\": \"jobName\"}");
            softly.assertThat(tc.get(0).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(0).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(1).getHeaderLabel()).isEqualTo("Status");
            softly.assertThat(tc.get(1).getDefinition()).isEqualTo("{\"data\": \"jobStatus\"}");
            softly.assertThat(tc.get(1).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(1).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(2).getHeaderLabel()).isEqualTo("Url");
            softly.assertThat(tc.get(2).getDefinition()).isEqualTo("{\"data\": \"jobUrl\"}");
            softly.assertThat(tc.get(2).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(2).getWidth()).isEqualTo(1);
        });
    }

    @Test
    void shouldCreateBuildViewTable() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);
        ResultService resultService = mock(ResultService.class);

        UiService uiService = new UiService(jobService, buildService, resultService);
        SoftAssertions.assertSoftly((softly) -> {
            when(buildService.createBuildViewTable()).thenReturn(new BuildViewTable(new BuildRepositoryStatistics()));
            BuildViewTable buildViewTable = uiService.createBuildViewTable();

            TableModel tableModel = buildViewTable.getTableModel("builds");
            softly.assertThat(tableModel.getId()).isEqualTo("builds");
            softly.assertThat(tableModel.getColumnsDefinition()).isEqualTo("[{\"data\": \"buildNumber\"},{\"data\": \"buildUrl\"}]");
            softly.assertThat(tableModel.getRows()).isEmpty();
            softly.assertThat(buildViewTable.getTableRows("builds")).isEmpty();

            List<TableColumn> tc = tableModel.getColumns();
            softly.assertThat(tc.size()).isEqualTo(2);
            softly.assertThat(tc.get(0).getHeaderLabel()).isEqualTo("Build Number");
            softly.assertThat(tc.get(0).getDefinition()).isEqualTo("{\"data\": \"buildNumber\"}");
            softly.assertThat(tc.get(0).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(0).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(1).getHeaderLabel()).isEqualTo("Url");
            softly.assertThat(tc.get(1).getDefinition()).isEqualTo("{\"data\": \"buildUrl\"}");
            softly.assertThat(tc.get(1).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(1).getWidth()).isEqualTo(1);
        });
    }

    @Test
    void shouldCreateIssueViewTable() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);
        ResultService resultService = mock(ResultService.class);

        UiService uiService = new UiService(jobService, buildService, resultService);
        SoftAssertions.assertSoftly((softly) -> {
            when(resultService.createIssueViewTable()).thenReturn(new IssueViewTable(new IssueRepositoryStatistics()));
            IssueViewTable issueViewTable = uiService.createIssueViewTable();

            TableModel tableModel = issueViewTable.getTableModel("issues");
            softly.assertThat(tableModel.getId()).isEqualTo("issues");
            softly.assertThat(tableModel.getColumnsDefinition()).isEqualTo("[{\"data\": \"category\"},{\"data\": \"moduleName\"},{\"data\": \"package\"},{\"data\": \"fileName\"},{\"data\": \"type\"},{\"data\": \"severity\"},{\"data\": \"reference\"},{\"data\": \"line\"},{\"data\": \"column\"}]");
            softly.assertThat(tableModel.getRows()).isEmpty();
            softly.assertThat(issueViewTable.getTableRows("issues")).isEmpty();

            List<TableColumn> tc = tableModel.getColumns();
            softly.assertThat(tc.size()).isEqualTo(9);
            softly.assertThat(tc.get(0).getHeaderLabel()).isEqualTo("Category");
            softly.assertThat(tc.get(0).getDefinition()).isEqualTo("{\"data\": \"category\"}");
            softly.assertThat(tc.get(0).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(0).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(1).getHeaderLabel()).isEqualTo("ModuleName");
            softly.assertThat(tc.get(1).getDefinition()).isEqualTo("{\"data\": \"moduleName\"}");
            softly.assertThat(tc.get(1).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(1).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(2).getHeaderLabel()).isEqualTo("Package");
            softly.assertThat(tc.get(2).getDefinition()).isEqualTo("{\"data\": \"package\"}");
            softly.assertThat(tc.get(2).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(2).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(3).getHeaderLabel()).isEqualTo("File");
            softly.assertThat(tc.get(3).getDefinition()).isEqualTo("{\"data\": \"fileName\"}");
            softly.assertThat(tc.get(3).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(3).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(4).getHeaderLabel()).isEqualTo("Type");
            softly.assertThat(tc.get(4).getDefinition()).isEqualTo("{\"data\": \"type\"}");
            softly.assertThat(tc.get(4).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(4).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(5).getHeaderLabel()).isEqualTo("Severity");
            softly.assertThat(tc.get(5).getDefinition()).isEqualTo("{\"data\": \"severity\"}");
            softly.assertThat(tc.get(5).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(5).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(6).getHeaderLabel()).isEqualTo("Reference");
            softly.assertThat(tc.get(6).getDefinition()).isEqualTo("{\"data\": \"reference\"}");
            softly.assertThat(tc.get(6).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(6).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(7).getHeaderLabel()).isEqualTo("Line");
            softly.assertThat(tc.get(7).getDefinition()).isEqualTo("{\"data\": \"line\"}");
            softly.assertThat(tc.get(7).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(7).getWidth()).isEqualTo(1);

            softly.assertThat(tc.get(8).getHeaderLabel()).isEqualTo("Column");
            softly.assertThat(tc.get(8).getDefinition()).isEqualTo("{\"data\": \"column\"}");
            softly.assertThat(tc.get(8).getHeaderClass()).isEqualTo("");
            softly.assertThat(tc.get(8).getWidth()).isEqualTo(1);
        });
    }

    @Test
    void prepareRowsForJobViewTable() {
        JobEntityService jobEntityService = mock(JobEntityService.class);
        JobService jobService = new JobService(jobEntityService);
        BuildService buildService = mock(BuildService.class);
        ResultService resultService = mock(ResultService.class);

        UiService uiService = new UiService(jobService, buildService, resultService);
        SoftAssertions.assertSoftly((softly) -> {
            when(jobEntityService.findAll()).thenReturn(new ArrayList<>());
            List<Object> objects = uiService.prepareRowsForJobViewTable();
            softly.assertThat(objects).isEmpty();

            when(jobEntityService.findAll()).thenReturn(createJobEntities());
            objects = uiService.prepareRowsForJobViewTable();
            softly.assertThat(objects).hasSize(NUMBER_OF_JOBS);
            softly.assertThat(objects).isInstanceOf(List.class);
            for (int i = 0; i < NUMBER_OF_JOBS; i++) {
                JobTableModel.JobsRow jobsRow = (JobTableModel.JobsRow) objects.get(i);
                softly.assertThat(jobsRow.getJobName()).isEqualTo(getJobNameForNumber(i));
                softly.assertThat(jobsRow.getJobUrl()).isEqualTo(getUrlForNumber(i));
                softly.assertThat(jobsRow.getJobStatus()).isEqualTo(SUCCESS);
            }

        });
    }

    @Test
    void getUsedToolsFromLastBuild() {
        JobService jobService = mock(JobService.class);
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        ResultService resultService = new ResultService();

        UiService uiService = new UiService(jobService, buildService, resultService);
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            jobs.add(createJobWithBuildsAndResults(i));
        }
        String jobName = JOB_NAME + 1;
        when(jobService.findJobByName(jobName)).thenReturn(jobs.get(1));
        SoftAssertions.assertSoftly((softly) -> {
            List<String> usedTools = uiService.getUsedToolsFromLastBuild(jobName);
            for (int i = 0; i < usedTools.size(); i++) {
                String usedTool = usedTools.get(i);
                softly.assertThat(usedTool).isEqualTo("toolName" + i + " Warnings");
            }
        });
    }

    @Test
    void shouldGetRowsForBuildViewTable() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();
        Job job = createJobWithBuildsAndResults(1);

        UiService uiService = new UiService(jobService, buildService, resultService);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {

            List<Object> objects = uiService.getRowsForBuildViewTable(job.getName());
            for (int i = 0; i < objects.size(); i++) {
                BuildTableModel.BuildsRow buildsRow = (BuildTableModel.BuildsRow) objects.get(i);
                softly.assertThat(buildsRow.getBuildNumber()).isEqualTo(i);
                softly.assertThat(buildsRow.getBuildUrl()).isEqualTo(getUrlForBuildWithBuildNumber(i));
            }
        });
    }

    @Test
    void shouldGetAggregatedAnalysisResultsTrendCharts() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();

        UiService uiService = new UiService(jobService, buildService, resultService);
        Job job = createJobWithBuildsAndResults(1);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {
            LinesChartModel linesChartModel = uiService.getAggregatedAnalysisResultsTrendCharts(job.getName());
            softly.assertThat(linesChartModel.getDomainAxisLabels()).isEqualTo(Arrays.asList("#0", "#1", "#2", "#3", "#4"));
            softly.assertThat(linesChartModel.getBuildNumbers()).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
            softly.assertThat(linesChartModel.getSeries().get(0).getData()).isEqualTo(Arrays.asList(40, 40, 40, 40, 40));
            softly.assertThat(linesChartModel.getSeries().get(1).getData()).isEqualTo(Arrays.asList(20, 20, 20, 20, 20));
            softly.assertThat(linesChartModel.getSeries().get(2).getData()).isEqualTo(Arrays.asList(0, 0, 0, 0, 0));
        });
    }

    @Test
    void getTrendChartForTool() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();

        UiService uiService = new UiService(jobService, buildService, resultService);
        Job job = createJobWithBuildsAndResults(1);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {
            LinesChartModel linesChartModel = uiService.getTrendChartForTool(job.getName(), "toolName" + 1 + " Warnings");
            softly.assertThat(linesChartModel.getDomainAxisLabels()).isEqualTo(Arrays.asList("#0", "#1", "#2", "#3", "#4"));
            softly.assertThat(linesChartModel.getBuildNumbers()).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
            softly.assertThat(linesChartModel.getSeries().get(0).getData()).isEqualTo(Arrays.asList(20, 20, 20, 20, 20));
        });
    }

    @Test
    void shouldGetNewVersusFixedTrendChart() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();

        UiService uiService = new UiService(jobService, buildService, resultService);
        Job job = createJobWithBuildsAndResults(1);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {
            LinesChartModel linesChartModel = uiService.getNewVersusFixedTrendChart(job.getName());
            softly.assertThat(linesChartModel.getDomainAxisLabels()).isEqualTo(Arrays.asList("#0", "#1", "#2", "#3", "#4"));
            softly.assertThat(linesChartModel.getSeries().get(0).getData()).isEqualTo(Arrays.asList(30, 30, 30, 30, 30));
            softly.assertThat(linesChartModel.getSeries().get(1).getData()).isEqualTo(Arrays.asList(30, 30, 30, 30, 30));
        });
    }

    @Test
    void getNewVersusFixedTrendChartForTool() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();

        UiService uiService = new UiService(jobService, buildService, resultService);
        Job job = createJobWithBuildsAndResults(1);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {
            LinesChartModel linesChartModel = uiService.getNewVersusFixedTrendChartForTool(job.getName(), "toolName" + 1 + " Warnings");
            softly.assertThat(linesChartModel.getDomainAxisLabels()).isEqualTo(Arrays.asList("#0", "#1", "#2", "#3", "#4"));
            softly.assertThat(linesChartModel.getSeries().get(0).getData()).isEqualTo(Arrays.asList(10, 10, 10, 10, 10));
        });
    }

    @Test
    void shouldGetBuildWithBuildNumberFromJob() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();

        UiService uiService = new UiService(jobService, buildService, resultService);
        SoftAssertions.assertSoftly((softly) -> {
            Job jobWithoutBuilds = createJob(3);
            when(jobService.findJobByName(jobWithoutBuilds.getName())).thenThrow(new NoSuchElementException("Build number " + 3 + " for the Job " + getJobNameForNumber(3) + " not found"));
            softly.assertThatThrownBy(() -> uiService.getBuildWithBuildNumberFromJob(jobWithoutBuilds.getName(), 3))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("Build number " + 3 + " for the Job " + getJobNameForNumber(3) + " not found");

            Job job = createJobWithBuilds();
            when(jobService.findJobByName(job.getName())).thenReturn(job);
            Build filteredBuild = uiService.getBuildWithBuildNumberFromJob(job.getName(), 3);
            Build build = createBuild(3);
            softly.assertThat(filteredBuild).isEqualTo(build);

            softly.assertThatThrownBy(() -> uiService.getBuildWithBuildNumberFromJob(job.getName(), 10))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("Build number " + 10 + " for the Job " + getJobNameForNumber(1) + " not found");
        });
    }

    @Test
    void getResultSummarize() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();

        UiService uiService = new UiService(jobService, buildService, resultService);
        Job job = createJobWithBuildsAndResults(1);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {
            BarChartModel barChartModel = uiService.getResultSummarize(job.getName(), 1, "toolId" + 1);
            //softly.assertThat(barChartModel.getDomainAxisLabels()).isEqualTo(Arrays.asList("#0", "#1", "#2","#3","#4"));
            softly.assertThat(barChartModel.getSeries().get(0).getData()).isEqualTo(Collections.singletonList(13));
            softly.assertThat(barChartModel.getSeries().get(0).getName()).isEqualTo("Old Total Size");
            softly.assertThat(barChartModel.getSeries().get(1).getData()).isEqualTo(Collections.singletonList(10));
            softly.assertThat(barChartModel.getSeries().get(1).getName()).isEqualTo("Fixed");
            softly.assertThat(barChartModel.getSeries().get(2).getData()).isEqualTo(Collections.singletonList(3));
            softly.assertThat(barChartModel.getSeries().get(2).getName()).isEqualTo("Outstanding");
            softly.assertThat(barChartModel.getSeries().get(3).getData()).isEqualTo(Collections.singletonList(10));
            softly.assertThat(barChartModel.getSeries().get(3).getName()).isEqualTo("New");
            softly.assertThat(barChartModel.getSeries().get(4).getData()).isEqualTo(Collections.singletonList(10));
            softly.assertThat(barChartModel.getSeries().get(4).getName()).isEqualTo("");
            softly.assertThat(barChartModel.getSeries().get(5).getData()).isEqualTo(Collections.singletonList(20));
            softly.assertThat(barChartModel.getSeries().get(5).getName()).isEqualTo("New Total Size");
        });
    }

    @Test
    void getIssuesDataForToolWithTotalSize() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();
        Job job = createJobWithBuildsAndResults(1);

        int buildNumber = 0;
        UiService uiService = new UiService(jobService, buildService, resultService);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {
            List<Object> report = uiService.getIssuesDataForToolWithTotalSize(job.getName(), buildNumber, "toolId" + buildNumber);
            softly.assertThat(report.size()).isEqualTo(6);
            int issueRowIndex = 0;
            for (int i = report.size() - 1; i >= 0; i--) {
                issueRowIndex = assertIssueRowAndReturnNextIndex(softly, report, issueRowIndex, i);
            }
        });

    }

    @Test
    void getIssuesDataForToolWithIssueType() {
        BuildEntityService buildEntityService = mock(BuildEntityService.class);
        BuildService buildService = new BuildService(buildEntityService);
        JobService jobService = mock(JobService.class);
        ResultService resultService = new ResultService();
        Job job = createJobWithBuildsAndResults(1);

        int buildNumber = 0;
        UiService uiService = new UiService(jobService, buildService, resultService);
        when(jobService.findJobByName(job.getName())).thenReturn(job);
        SoftAssertions.assertSoftly((softly) -> {
            for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
                List<Object> report = uiService.getIssuesDataForToolWithIssueType(job.getName(), buildNumber, "toolId" + buildNumber, warningTypeEntity.toString().toLowerCase());
                int issueRowIndex = 0;
                if (warningTypeEntity.toString().equals("FIXED")) {
                    for (int i = 8; i >= 6; i--) {
                        issueRowIndex = assertIssueRowAndReturnNextIndex(softly, report, issueRowIndex, i);
                    }
                } else if (warningTypeEntity.toString().equals("OUTSTANDING")) {
                    for (int i = 2; i >= 0; i--) {
                        issueRowIndex = assertIssueRowAndReturnNextIndex(softly, report, issueRowIndex, i);
                    }
                } else {
                    for (int i = 5; i >= 3; i--) {
                        issueRowIndex = assertIssueRowAndReturnNextIndex(softly, report, issueRowIndex, i);
                    }
                }

            }
        });
    }

    @Test
    void getInfoMessagesAndErrorMessagesFromResultWithToolId() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);
        ResultService resultService = mock(ResultService.class);

        UiService uiService = new UiService(jobService, buildService, resultService);
        Build build = createBuildWithResults(BUILD_NUMBER, BUILD_NUMBER, JOB_NAME, NUMBER_OF_RESULTS);

        SoftAssertions.assertSoftly((softly) -> {
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                when(resultService.getInfoMessagesFromResultWithToolId(build, "toolId" + i)).thenReturn(createInfoMessage(i));
                List<String> infoMessages = uiService.getInfoMessagesFromResultWithToolId(build, "toolId" + i);
                softly.assertThat(infoMessages).isEqualTo(createInfoMessage(i));

                when(resultService.getErrorMessagesFromResultWithToolId(build, "toolId" + i)).thenReturn(createErrorMessage(i));
                List<String> errorMessages = uiService.getErrorMessagesFromResultWithToolId(build, "toolId" + i);
                softly.assertThat(errorMessages).isEqualTo(createErrorMessage(i));
            }
        });
    }

    @Test
    void shouldNotGetInfoMessagesAndErrorMessagesFromResultWithToolId() {
        JobService jobService = mock(JobService.class);
        BuildService buildService = mock(BuildService.class);
        ResultService resultService = mock(ResultService.class);

        UiService uiService = new UiService(jobService, buildService, resultService);
        Job job = new Job(1, JOB_NAME, "http://localhost:8080/jenkins/job/" + JOB_NAME + "/", SUCCESS);
        Build build = createBuildWithResults(BUILD_NUMBER, BUILD_NUMBER, JOB_NAME, NUMBER_OF_RESULTS);
        job.addBuild(build);

        SoftAssertions.assertSoftly((softly) -> {
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                String notExistingToolId = "notExistingToolId" + i;
                when(resultService.getInfoMessagesFromResultWithToolId(build, notExistingToolId)).thenThrow(new NoSuchElementException(
                        "Result for the Job " + build.getJob().getName() + " with the build number" + build.getNumber()
                                + " and the tool id " + notExistingToolId + " not found"
                ));
                softly.assertThatThrownBy(() -> uiService.getInfoMessagesFromResultWithToolId(build, notExistingToolId))
                        .isInstanceOf(NoSuchElementException.class)
                        .hasMessage("Result for the Job jobName with the build number1 and the tool id " + notExistingToolId + " not found");

                when(resultService.getErrorMessagesFromResultWithToolId(build, notExistingToolId)).thenThrow(new NoSuchElementException(
                        "Result for the Job " + build.getJob().getName() + " with the build number" + build.getNumber()
                                + " and the tool id " + notExistingToolId + " not found"
                ));
                softly.assertThatThrownBy(() -> uiService.getErrorMessagesFromResultWithToolId(build, notExistingToolId))
                        .isInstanceOf(NoSuchElementException.class)
                        .hasMessage("Result for the Job jobName with the build number1 and the tool id " + notExistingToolId + " not found");
            }
        });
    }

    private int assertIssueRowAndReturnNextIndex(SoftAssertions softly, List<Object> report, int issueRowIndex, int i) {
        IssueTableModel.IssuesRow issuesRow = (IssueTableModel.IssuesRow) report.get(issueRowIndex);
        softly.assertThat(issuesRow.getCategory()).isEqualTo("category" + i);
        softly.assertThat(issuesRow.getColumn()).isEqualTo(i + ":" + i);
        softly.assertThat(issuesRow.getFileName()).isEqualTo("fileName" + i);
        softly.assertThat(issuesRow.getLine()).isEqualTo(i + ":" + i);
        softly.assertThat(issuesRow.getModuleName()).isEqualTo("moduleName" + i);
        softly.assertThat(issuesRow.getPackage()).isEqualTo("packageName" + i);
        softly.assertThat(issuesRow.getReference()).isEqualTo("reference" + i);
        softly.assertThat(issuesRow.getSeverity()).isEqualTo(Severity.valueOf("severity" + i).toString());
        softly.assertThat(issuesRow.getType()).isEqualTo("type" + i);
        issueRowIndex++;

        return issueRowIndex;
    }

    private Job createJobWithBuilds() {
        Job job = createJob(1);
        List<Build> builds = createBuilds();
        builds.forEach(job::addBuild);

        return job;
    }

    private List<Build> createBuilds() {
        List<Build> builds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BUILDS; i++) {
            builds.add(createBuild(i));
        }
        return builds;
    }

    private Build createBuild(int numberOfBuild) {
        return new Build(
                numberOfBuild,
                numberOfBuild,
                getUrlForBuildWithBuildNumber(numberOfBuild)
        );
    }

    private String getUrlForBuildWithBuildNumber(int number) {
        return "http://localhost:8080/jenkins/job/" + JOB_NAME + "/" + number + "/";
    }

    private Job createJobWithBuildsAndResults(int numberOfJob) {
        Job job = createJob(numberOfJob);
        List<Build> builds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BUILDS; i++) {
            builds.add(createBuildWithResults(i, i, JOB_NAME, NUMBER_OF_RESULTS));
        }
        builds.forEach(job::addBuild);

        return job;
    }

    private Job createJob(int numberOfJob) {
        return new Job(
                numberOfJob,
                getJobNameForNumber(numberOfJob),
                getUrlForNumber(numberOfJob),
                SUCCESS);
    }

    private List<JobEntity> createJobEntities() {
        return IntStream.range(0, NUMBER_OF_JOBS).mapToObj(this::createJobEntity).collect(Collectors.toList());
    }

    private JobEntity createJobEntity(int numberOfJob) {
        return new JobEntity(
                numberOfJob,
                getJobNameForNumber(numberOfJob),
                getUrlForNumber(numberOfJob),
                SUCCESS);
    }

    private String getUrlForNumber(int number) {
        return "http://localhost:8080/jenkins/job/" + JOB_NAME + number + "/";
    }

    private String getJobNameForNumber(int numberOfJob) {
        return JOB_NAME + numberOfJob;
    }

    private Build createBuildWithResults(int id, int buildNumber, String jobName, int numberOfResults) {
        Build build = new Build(id, buildNumber, "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/");
        for (int i = 0; i < numberOfResults; i++) {
            Result result = new Result(
                    i,
                    "toolId" + i,
                    "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/" + "toolId" + i,
                    "toolName" + i + " Warnings",
                    i * 10,
                    i * 10,
                    i * 10 * 2,
                    "INACTIVE"
            );
            result.setInfoMessages(createInfoMessage(i));
            result.setErrorMessages(createErrorMessage(i));
            for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
                Report report = new Report();
                switch (warningTypeEntity) {
                    case OUTSTANDING:
                        for (int issueCounter = 0; issueCounter < 3; issueCounter++) {
                            report.add(createIssue(issueCounter));
                        }
                        result.setOutstandingIssues(report);
                        break;
                    case NEW:
                        for (int issueCounter = 3; issueCounter < 6; issueCounter++) {
                            report.add(createIssue(issueCounter));
                        }
                        result.setNewIssues(report);
                        break;
                    case FIXED:
                        for (int issueCounter = 6; issueCounter < 9; issueCounter++) {
                            report.add(createIssue(issueCounter));
                        }
                        result.setFixedIssues(report);
                        break;
                }
            }
            build.addResult(result);
        }

        return build;
    }

    private List<String> createErrorMessage(int i) {
        return Arrays.asList("Error", "Message", ": " + i);
    }

    private List<String> createInfoMessage(int i) {
        return Arrays.asList("Info", "Message", ": " + i);
    }

    private Issue createIssue(int issueCounter) {
        return new IssueBuilder()
                .setId(UUID.fromString(issueCounter + "39c88cb-abb2-43c4-8374-735840acbee9"))
                .setCategory("category" + issueCounter)
                .setColumnEnd(issueCounter)
                .setColumnStart(issueCounter)
                .setDescription("description" + issueCounter)
                .setFileName("fileName" + issueCounter)
                .setFingerprint("fingerprint" + issueCounter)
                .setLineEnd(issueCounter)
                .setLineStart(issueCounter)
                .setMessage("message" + issueCounter)
                .setModuleName("moduleName" + issueCounter)
                .setOrigin("origin" + issueCounter)
                .setPackageName("packageName" + issueCounter)
                .setReference("reference" + issueCounter)
                .setSeverity(Severity.valueOf("severity" + issueCounter))
                .setType("type" + issueCounter)
                .build();
    }

}