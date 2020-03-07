package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;
import edu.hm.hafner.warningsngui.db.model.ReportEntity;
import edu.hm.hafner.warningsngui.db.model.ResultEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Result;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Test the Class {@link ResultMapper}, {@link ReportMapper} and {@link IssueMapper}.
 *
 * @author Deniz Mardin
 */
class ResultMapperTest {
    private static final String JOB_NAME = "jobName";
    private static final String OTHER_JOB_NAME = "otherJobName";
    private static final List<String> UUID_LIST = Arrays.asList(
            "239c88cb-abb2-43c4-8374-735840acbee9",
            "1cda4bbb-77f3-45fb-839a-a9a05bec0ca4",
            "4dc33b00-fe6b-4818-852c-435875e815ab",
            "8bf5fa77-4088-4d01-bc9b-6a5a16ffda54",
            "24dfa1b9-6b16-46d6-abda-e1d19647021e",
            "4c61f5ec-1bff-430a-a94e-6d095b53e0de",
            "e6b61cd5-c357-4581-a06a-bcbdec9c1378",
            "d81589b4-0572-4aca-93b8-86f25647da4c",
            "90be44ba-c895-4c21-9846-866b9104b316",
            "897eab11-0c31-4ce2-900e-a6da19027a12",
            "8cee3e58-8e56-463c-b0c6-4a51b404e24c",
            "732c108a-3356-40fd-8659-d93eac0d7015",
            "7213380e-2ea3-4893-84e5-2560985f32b3",
            "c27e61be-6ee4-482f-8f5a-abb097364a13",
            "df1321a3-feed-4a0e-97e6-181441e49adc",
            "1bfa080e-e529-441b-8c21-9bc1b5aa8aa9"
    );

    @Test
    void shouldMapResultEntityToResult() {
        SoftAssertions.assertSoftly((softly) -> {
            ResultEntity resultEntity = createResultEntity(1, 1, JOB_NAME, 0,0,0,0,0,0);
            Result result = ResultMapper.map(resultEntity);
            Result expectedResult = createResult(1, 1,  JOB_NAME,0,0,0,0,0,0);
            softly.assertThat(result).isEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME, 0,5,5,10,10,15);
            result = ResultMapper.map(resultEntity);
            expectedResult = createResult(1, 1,  JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isEqualTo(expectedResult);
        });
    }

    @Test
    void shouldNotMapResultEntityToResult() {
        SoftAssertions.assertSoftly((softly) -> {
            ResultEntity resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            Result result = ResultMapper.map(resultEntity);
            Result expectedResult = createResult(2, 1,  JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = ResultMapper.map(resultEntity);
            expectedResult = createResult(1, 2,  JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = ResultMapper.map(resultEntity);
            expectedResult = createResult(1, 1,  OTHER_JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = ResultMapper.map(resultEntity);
            expectedResult = createResult(1, 1,  JOB_NAME, 1,6,6,11,11,16);
            softly.assertThat(result).isNotEqualTo(expectedResult);

            resultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            result = ResultMapper.map(resultEntity);
            expectedResult = createResult(1, 1,  JOB_NAME, 1,2,2,3,3,4);
            softly.assertThat(result).isNotEqualTo(expectedResult);

//            resultEntity = createResultEntity(1, 1, JOB_NAME);
//            result = ResultMapper.map(resultEntity);
//            expectedResult = createResult(1, 1,  JOB_NAME);
//            softly.assertThat(result).isNotEqualTo(expectedResult);
        });
    }

    @Test
    void shouldMapResultToResultEntity() {
        SoftAssertions.assertSoftly((softly) -> {
            Result result = createResult(1, 1, JOB_NAME,0,0,0,0,0,0);
            ResultEntity resultEntity = ResultMapper.mapToEntity(result);
            ResultEntity expectedResultEntity = createResultEntity(1, 1, JOB_NAME,0,0,0,0,0,0);
            softly.assertThat(resultEntity).isEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = ResultMapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isEqualTo(expectedResultEntity);
        });
    }

    @Test
    void shouldNotMapResultToResultEntity() {
        SoftAssertions.assertSoftly((softly) -> {
            System.out.println(UUID.randomUUID());
            Result result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            ResultEntity resultEntity = ResultMapper.mapToEntity(result);
            ResultEntity expectedResultEntity = createResultEntity(2, 1, JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = ResultMapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 2, JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = ResultMapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, OTHER_JOB_NAME,0,5,5,10,10,15);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = ResultMapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, JOB_NAME, 1,6,6,11,11,16);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

            result = createResult(1, 1, JOB_NAME,0,5,5,10,10,15);
            resultEntity = ResultMapper.mapToEntity(result);
            expectedResultEntity = createResultEntity(1, 1, JOB_NAME, 1,2,2,3,3,4);
            softly.assertThat(resultEntity).isNotEqualTo(expectedResultEntity);

//            result = createResult(1, 1, JOB_NAME);
//            resultEntity = ResultMapper.mapToEntity(result);
//            expectedResultEntity = createResultEntity(1, 1, JOB_NAME);
//            softly.assertThat(resultEntity).isEqualTo(expectedResultEntity);
        });
    }

    private ResultEntity createResultEntity(int id, int buildNumber, String jobName, int outstandingStart, int outstandingEndExcluded, int newStart, int newEndExcluded, int fixedStart, int fixedEndExcluded) {
        ResultEntity resultEntity = new ResultEntity(
                id,
                "toolId" + id,
                "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/" + "toolId" + id,
                "toolName" + id + " Warnings",
                id * 10,
                id * 10,
                id * 10 * 2,
                "INACTIVE"
        );
        resultEntity.setInfoMessages(createInfoMessage(id));
        resultEntity.setErrorMessages(createErrorMessage(id));
        for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
            switch (warningTypeEntity) {
                case OUTSTANDING:
                    resultEntity.addReportEntity(createReportEntity(warningTypeEntity, outstandingStart, outstandingEndExcluded));
                    break;
                case NEW:
                    resultEntity.addReportEntity(createReportEntity(warningTypeEntity, newStart, newEndExcluded));
                    break;
                case FIXED:
                    resultEntity.addReportEntity(createReportEntity(warningTypeEntity, fixedStart, fixedEndExcluded));
                    break;
            }
        }
        return resultEntity;
    }

    private Result createResult(int id, int buildNumber, String jobName, int outstandingStart, int outstandingEndExcluded, int newStart, int newEndExcluded, int fixedStart, int fixedEndExcluded) {
        Result result = new Result(
                id,
                "toolId" + id,
                "http://localhost:8080/jenkins/job/" + jobName + "/" + buildNumber + "/" + "toolId" + id,
                "toolName" + id + " Warnings",
                id * 10,
                id * 10,
                id * 10 * 2,
                "INACTIVE"
        );
        result.setInfoMessages(createInfoMessage(id));
        result.setErrorMessages(createErrorMessage(id));
        for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
            switch (warningTypeEntity) {
                case OUTSTANDING:
                    result.setOutstandingIssues(createReport(outstandingStart, outstandingEndExcluded));
                    break;
                case NEW:
                    result.setNewIssues(createReport(newStart, newEndExcluded));
                    break;
                case FIXED:
                    result.setFixedIssues(createReport(fixedStart, fixedEndExcluded));
                    break;
            }
        }

        return result;
    }

    private Report createReport(int issueNumberStart, int issueNumberEnd) {
        Report report = new Report();
        for(int i = issueNumberStart; i < issueNumberEnd; i++) {
            report.add(createIssue(i));
        }
        return report;
    }

    private Issue createIssue(int i) {
        IssueBuilder issueBuilder = new IssueBuilder();
        return issueBuilder
                .setId(getUUID(i))
                .setCategory("category"+i)
                .setColumnEnd(i+2)
                .setColumnStart(i+1)
                .setDescription("description"+i)
                .setFileName("filename"+i)
                .setFingerprint("fingerprint"+i)
                .setLineEnd(i+4)
                .setLineStart(i+3)
                .setMessage("message"+i)
                .setModuleName("moduleName"+i)
                .setOrigin("origin"+i)
                .setPackageName("packageName"+i)
                .setReference("reference"+i)
                .setSeverity(Severity.valueOf("ERROR"+i))
                .setType("type"+i)
                .build();
    }

    private ReportEntity createReportEntity(WarningTypeEntity warningTypeEntity, int issueNumberStart, int issueNumberEnd) {
        ReportEntity reportEntity = new ReportEntity(warningTypeEntity);
        for(int i = issueNumberStart; i < issueNumberEnd; i++) {
            reportEntity.addIssueEntity(createIssueEntity(i));
        }

        return reportEntity;
    }

    private IssueEntity createIssueEntity(int i){
        return new IssueEntity(
                getUUID(i),
                i+1,
                i+2,
                i+3,
                i+4,
                "category"+i,
                "description"+i,
                "filename"+i,
                "fingerprint"+i,
                "message"+i,
                "moduleName"+i,
                "origin"+i,
                "packageName"+i,
                "reference"+i,
                "ERROR"+i,
                "type"+i
        );
    }


    private List<String> createErrorMessage(int i) {
        return Arrays.asList("Error", "Message", ": " + i);
    }

    private List<String> createInfoMessage(int i) {
        return Arrays.asList("Info", "Message", ": " + i);
    }

    private UUID getUUID(int i) {
        return UUID.fromString(String.valueOf(UUID_LIST.get(i)));
    }
}