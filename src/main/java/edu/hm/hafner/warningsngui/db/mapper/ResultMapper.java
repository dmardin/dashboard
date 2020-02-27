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

public class ResultMapper {
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

    public static List<Result> map(List<ResultEntity> resultEntities, Build build) {
        return resultEntities.stream().map(resultEntity -> map(resultEntity, build)).collect(Collectors.toList());
    }

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

    public static List<ResultEntity> mapToEntities(List<Result> results, BuildEntity buildEntity) {
        return results.stream().map(result -> mapToEntity(result, buildEntity)).collect(Collectors.toList());
    }
}
