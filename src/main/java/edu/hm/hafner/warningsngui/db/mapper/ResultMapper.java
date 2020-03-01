package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.db.model.BuildEntity;
import edu.hm.hafner.warningsngui.db.model.ReportEntity;
import edu.hm.hafner.warningsngui.db.model.ResultEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enables the conversion from a {@link Result} to a {@link ResultEntity} and visa versa.
 * Furthermore a list of {@link Result}s can be converted to a list ob {@link ResultEntity}s and visa versa.
 *
 * @author Deniz Mardin
 */
public class ResultMapper {

    /**
     * Converts a {@link ResultEntity} to a {@link Result}.
     *
     * @param resultEntity the {@link ResultEntity}
     * @param build the corresponding {@link Build} for {@link Result}
     * @return the converted {@link Result}
     */
    public static Result map(ResultEntity resultEntity, Build build) {
        Result result = new Result(
                resultEntity.getId(),
                resultEntity.getWarningId(),
                resultEntity.getLatestUrl(),
                resultEntity.getName(),
                resultEntity.getFixedSize(),
                resultEntity.getNewSize(),
                resultEntity.getTotalSize(),
                resultEntity.getQualityGateStatus()
        );
        result.setErrorMessages(resultEntity.getErrorMessages());
        result.setInfoMessages(resultEntity.getInfoMessages());
        result.setTotalSize(resultEntity.getTotalSize());
        result.setBuild(build);
        for (ReportEntity reportEntity : resultEntity.getReports()) {
            switch (reportEntity.getWarningTypeEntity()) {
                case OUTSTANDING:
                    result.setOutstandingIssues(ReportMapper.map(reportEntity));
                    break;
                case NEW:
                    result.setNewIssues(ReportMapper.map(reportEntity));
                    break;
                case FIXED:
                    result.setFixedIssues(ReportMapper.map(reportEntity));
                    break;
            }
        }

        return result;
    }

    /**
     * Converts a list of {@link ResultEntity}s to a list of {@link Result}s.
     *
     * @param resultEntities the {@link ResultEntity}s
     * @param build the corresponding {@link Build} for {@link Result}s
     * @return the converted list of {@link Result}s
     */
    public static List<Result> map(List<ResultEntity> resultEntities, Build build) {
        return resultEntities.stream().map(resultEntity -> map(resultEntity, build)).collect(Collectors.toList());
    }

    /**
     * Converts a {@link Result} to a {@link ResultEntity}.
     *
     * @param result the {@link Result}
     * @param buildEntity the corresponding {@link BuildEntity} for {@link ResultEntity}
     * @return the converted {@link ResultEntity}
     */
    public static ResultEntity mapToEntity(Result result, BuildEntity buildEntity) {
        ResultEntity resultEntity = new ResultEntity(
                result.getId(),
                result.getWarningId(),
                result.getLatestUrl(),
                result.getName(),
                result.getFixedSize(),
                result.getNewSize(),
                result.getTotalSize(),
                result.getQualityGateStatus()
        );
        resultEntity.setBuildEntity(buildEntity);
        resultEntity.setInfoMessages(result.getInfoMessages());
        resultEntity.setErrorMessages(result.getErrorMessages());

        List<ReportEntity> reportEntities = new ArrayList<>();
        for(WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
            switch (warningTypeEntity) {
                case OUTSTANDING:
                    reportEntities.add(createReportEntity(result.getOutstandingIssues(), resultEntity));
                    break;
                case NEW:
                    reportEntities.add(createReportEntity(result.getFixedIssues(), resultEntity));
                    break;
                case FIXED:
                    reportEntities.add(createReportEntity(result.getNewIssues(), resultEntity));
                    break;
            }
        }
        resultEntity.setReports(reportEntities);

        return resultEntity;
    }

    private static ReportEntity createReportEntity(Report issueReport, ResultEntity resultEntity) {
        ReportEntity reportEntity = new ReportEntity(WarningTypeEntity.NEW);
        reportEntity.setResultEntity(resultEntity);
        reportEntity.setIssues(ReportMapper.mapToEntities(issueReport, reportEntity));

        return reportEntity;
    }

    /**
     * Converts a list of {@link Result} to a list of {@link ResultEntity}s.
     *
     * @param results the {@link ResultEntity}s
     * @param buildEntity the corresponding {@link BuildEntity} for the {@link ResultEntity}s
     * @return the converted list of {@link ResultEntity}s
     */
    public static List<ResultEntity> mapToEntities(List<Result> results, BuildEntity buildEntity) {
        return results.stream().map(result -> mapToEntity(result, buildEntity)).collect(Collectors.toList());
    }
}
