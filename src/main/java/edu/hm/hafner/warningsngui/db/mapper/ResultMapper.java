package edu.hm.hafner.warningsngui.db.mapper;

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
//        result.setId(resultEntity.getId());
//        result.setWarningId(resultEntity.getWarningId());
//        result.setLatestUrl(resultEntity.getLatestUrl());
//        result.setName(resultEntity.getName());
//        result.setNewSize(resultEntity.getNewSize());
//        result.setFixedSize(resultEntity.getFixedSize());
//        result.setQualityGateStatus(resultEntity.getQualityGateStatus());

        result.setErrorMessages(resultEntity.getErrorMessages());
        result.setInfoMessages(resultEntity.getInfoMessages());
        result.setBuild(build);
        result.setTotalSize(resultEntity.getTotalSize());
        for (ReportEntity reportEntity : resultEntity.getReports()) {
            if(reportEntity.getWarningTypeEntity() == WarningTypeEntity.OUTSTANDING) {
                result.setOutstandingIssues(ReportMapper.map(reportEntity));
            }
            else if(reportEntity.getWarningTypeEntity() == WarningTypeEntity.NEW) {
                result.setNewIssues(ReportMapper.map(reportEntity));
            }
            else if(reportEntity.getWarningTypeEntity() == WarningTypeEntity.FIXED) {
                result.setFixedIssues(ReportMapper.map(reportEntity));
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
//        resultEntity.setId(result.getId());
//        resultEntity.setWarningId(result.getWarningId());
//        resultEntity.setLatestUrl(result.getLatestUrl());
//        resultEntity.setName(result.getName());
//        resultEntity.setTotalSize(result.getTotalSize());
//        resultEntity.setNewSize(result.getNewSize());
//        resultEntity.setFixedSize(result.getFixedSize());
//        resultEntity.setQualityGateStatus(result.getQualityGateStatus());
        resultEntity.setBuildEntity(buildEntity);
        resultEntity.setInfoMessages(result.getInfoMessages());
        resultEntity.setErrorMessages(result.getErrorMessages());

        List<ReportEntity> reportEntities = new ArrayList<>();
        ReportEntity reportEntityOutstanding = new ReportEntity();
        reportEntityOutstanding.setResultEntity(resultEntity);
        reportEntityOutstanding.setWarningTypeEntity(WarningTypeEntity.OUTSTANDING);
        reportEntityOutstanding.setIssues(ReportMapper.mapToEntities(result.getOutstandingIssues(), reportEntityOutstanding));
        reportEntities.add(reportEntityOutstanding);

        ReportEntity reportEntityFixed = new ReportEntity();
        reportEntityFixed.setResultEntity(resultEntity);
        reportEntityFixed.setWarningTypeEntity(WarningTypeEntity.FIXED);
        reportEntityFixed.setIssues(ReportMapper.mapToEntities(result.getFixedIssues(), reportEntityFixed));
        reportEntities.add(reportEntityFixed);

        ReportEntity reportEntityNew = new ReportEntity();
        reportEntityNew.setResultEntity(resultEntity);
        reportEntityNew.setWarningTypeEntity(WarningTypeEntity.NEW);
        reportEntityNew.setIssues(ReportMapper.mapToEntities(result.getNewIssues(), reportEntityNew));
        reportEntities.add(reportEntityNew);

        resultEntity.setReports(reportEntities);

        return resultEntity;
    }

    public static List<ResultEntity> mapToEntities(List<Result> results, BuildEntity buildEntity) {
        return results.stream().map(result -> mapToEntity(result, buildEntity)).collect(Collectors.toList());
    }
}
