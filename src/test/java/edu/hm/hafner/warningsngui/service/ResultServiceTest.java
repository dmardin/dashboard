package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.service.table.issue.IssueViewTable;
import io.jenkins.plugins.datatables.api.TableColumn;
import io.jenkins.plugins.datatables.api.TableModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Test the Class {@link ResultService}.
 *
 * @author Deniz Mardin
 */
class ResultServiceTest {
    private static final String JOB_NAME = "jobName";
    private static final String SUCCESS = "Success";
    private static final int NUMBER_OF_RESULTS = 3;
    private static final int BUILD_NUMBER = 1;

    @Test
    void shouldGetUsedToolsFromBuild() {
        ResultService resultService = new ResultService();
        Build build = createBuildWithResults(BUILD_NUMBER, BUILD_NUMBER, JOB_NAME, NUMBER_OF_RESULTS);

        SoftAssertions.assertSoftly((softly) -> {
            List<String> usedTools = resultService.getUsedToolsFromBuild(build);
            for (int i = 0; i < usedTools.size(); i++) {
                String usedTool = usedTools.get(i);
                softly.assertThat(usedTool).isEqualTo("toolName" + i + " Warnings");
            }
        });
    }

    @Test
    void shouldGetInfoMessagesAndErrorMessagesFromResultWithToolId() {
        ResultService resultService = new ResultService();
        Build build = createBuildWithResults(BUILD_NUMBER, BUILD_NUMBER, JOB_NAME, NUMBER_OF_RESULTS);

        SoftAssertions.assertSoftly((softly) -> {
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                List<String> infoMessages = resultService.getInfoMessagesFromResultWithToolId(build, "toolId" + i);
                List<String> errorMessages = resultService.getErrorMessagesFromResultWithToolId(build, "toolId" + i);
                softly.assertThat(infoMessages).isEqualTo(createInfoMessage(i));
                softly.assertThat(errorMessages).isEqualTo(createErrorMessage(i));
            }
        });
    }

    @Test
    void shouldNotGetInfoMessagesAndErrorMessagesFromResultWithToolId() {
        ResultService resultService = new ResultService();
        Build build = createBuildWithResults(BUILD_NUMBER, BUILD_NUMBER, JOB_NAME, NUMBER_OF_RESULTS);
        Job job = new Job(1, JOB_NAME, "http://localhost:8080/jenkins/job/" + JOB_NAME + "/", SUCCESS);
        job.addBuild(build);

        SoftAssertions.assertSoftly((softly) -> {
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                String notExistingToolId = "notExistingToolId" + i;
                softly.assertThatThrownBy(() -> resultService.getInfoMessagesFromResultWithToolId(build, notExistingToolId))
                        .isInstanceOf(NoSuchElementException.class)
                        .hasMessage("Result for the Job jobName with the build number1 and the tool id " + notExistingToolId + " not found");
                softly.assertThatThrownBy(() -> resultService.getErrorMessagesFromResultWithToolId(build, notExistingToolId))
                        .isInstanceOf(NoSuchElementException.class)
                        .hasMessage("Result for the Job jobName with the build number1 and the tool id " + notExistingToolId + " not found");
            }
        });
    }

    @Test
    void shouldGetResultByToolId() {
        ResultService resultService = new ResultService();
        Build build = createBuildWithResults(BUILD_NUMBER, BUILD_NUMBER, JOB_NAME, NUMBER_OF_RESULTS);

        SoftAssertions.assertSoftly((softly) -> {
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                Result result = resultService.getResultByToolId(build, "toolId" + i);
                Result expectedResult = new Result(
                        i,
                        "toolId" + i,
                        "http://localhost:8080/jenkins/job/" + JOB_NAME + "/" + 1 + "/" + "toolId" + i,
                        "toolName" + i + " Warnings",
                        i * 10,
                        i * 10,
                        i * 10 * 2,
                        "INACTIVE"
                );
                expectedResult.setInfoMessages(createInfoMessage(i));
                expectedResult.setErrorMessages(createErrorMessage(i));
                for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
                    Report report = new Report();
                    switch (warningTypeEntity) {
                        case OUTSTANDING:
                            for (int issueCounter = 0; issueCounter < 3; issueCounter++) {
                                report.add(createIssue(issueCounter));
                            }
                            expectedResult.setOutstandingIssues(report);
                            break;
                        case NEW:
                            for (int issueCounter = 3; issueCounter < 6; issueCounter++) {
                                report.add(createIssue(issueCounter));
                            }
                            expectedResult.setNewIssues(report);
                            break;
                        case FIXED:
                            for (int issueCounter = 6; issueCounter < 9; issueCounter++) {
                                report.add(createIssue(issueCounter));
                            }
                            expectedResult.setFixedIssues(report);
                            break;
                    }
                }
                build.addResult(result);
                softly.assertThat(result).isEqualTo(expectedResult);
            }
        });
    }

    @Test
    void shouldNotGetResultByToolId() {
        ResultService resultService = new ResultService();
        Build build = createBuildWithResults(1, BUILD_NUMBER, JOB_NAME, NUMBER_OF_RESULTS);
        Job job = new Job(1, JOB_NAME, "http://localhost:8080/jenkins/job/" + JOB_NAME + "/", SUCCESS);
        job.addBuild(build);

        SoftAssertions.assertSoftly((softly) -> {
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                String notExistingToolId = "notExistingToolId" + i;
                softly.assertThatThrownBy(() -> resultService.getResultByToolId(build, notExistingToolId))
                        .isInstanceOf(NoSuchElementException.class)
                        .hasMessage("Tool id " + notExistingToolId + " for the Build 1 from the job jobName not found");
            }
        });
    }

    @Test
    void createIssueViewTable() {
        ResultService resultService = new ResultService();

        SoftAssertions.assertSoftly((softly) -> {
            IssueViewTable issueViewTable = resultService.createIssueViewTable();
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

    private List<String> createErrorMessage(int i) {
        return Arrays.asList("Error", "Message", ": " + i);
    }

    private List<String> createInfoMessage(int i) {
        return Arrays.asList("Info", "Message", ": " + i);
    }
}