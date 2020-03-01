package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.warningsngui.db.model.ReportEntity;
import edu.hm.hafner.warningsngui.db.model.ResultEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;
import edu.hm.hafner.warningsngui.service.dto.Result;

/**
 * Enables the conversion from a {@link Result} to a {@link ResultEntity} and visa versa.
 *
 * @author Deniz Mardin
 */
public class ResultMapper {

    /**
     * Converts a {@link ResultEntity} to a {@link Result}.
     *
     * @param resultEntity the {@link ResultEntity}
     * @return the converted {@link Result}
     */
    public static Result map(ResultEntity resultEntity) {
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
     * Converts a {@link Result} to a {@link ResultEntity}.
     *
     * @param result the {@link Result}
     * @return the converted {@link ResultEntity}
     */
    public static ResultEntity mapToEntity(Result result) {
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
        resultEntity.setInfoMessages(result.getInfoMessages());
        resultEntity.setErrorMessages(result.getErrorMessages());
        for(WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
            switch (warningTypeEntity) {
                case OUTSTANDING:
                    resultEntity.addReportEntity(ReportMapper.mapToEntity(result.getOutstandingIssues(), warningTypeEntity));
                    break;
                case NEW:
                    resultEntity.addReportEntity(ReportMapper.mapToEntity(result.getNewIssues(), warningTypeEntity));
                    break;
                case FIXED:
                    resultEntity.addReportEntity(ReportMapper.mapToEntity(result.getFixedIssues(), warningTypeEntity));
                    break;
            }
        }

        return resultEntity;
    }
}
