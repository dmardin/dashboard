package edu.hm.hafner.warningsngui.service.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.db.model.*;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.service.rest.response.IssuesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The {@link Mapper} is handling the mapping from DTO to Entity and visa versa.
 * Enables the conversion from a {@link Job} to a {@link JobEntity} and visa versa.
 * Furthermore a list of {@link Job}s can be converted to a list ob {@link JobEntity}s and visa versa.
 * Enables the conversion from a {@link Build} to a {@link BuildEntity} and visa versa.
 * Enables the conversion from a {@link Result} to a {@link ResultEntity} and visa versa.
 * Enables the conversion from a {@link Report} to a {@link ReportEntity} and visa versa.
 * Enables the conversion from a {@link Issue} to a {@link IssueEntity} and visa versa.
 *
 * @author Deniz Mardin
 */
public class Mapper {

    /**
     * Converts a {@link JobEntity} to a {@link Job}.
     *
     * @param jobEntity the {@link JobEntity}
     * @return the converted {@link Job}
     */
    public static Job map(JobEntity jobEntity) {
        Job job = new Job(
                jobEntity.getId(),
                jobEntity.getName(),
                jobEntity.getUrl(),
                jobEntity.getLastBuildStatus()
        );
        jobEntity.getBuildEntities().forEach(buildEntity -> job.addBuild(map(buildEntity)));

        return job;
    }

    /**
     * Converts a list of {@link JobEntity}s to a list of {@link Job}s.
     *
     * @param jobEntities the list of {@link JobEntity}s
     * @return the converted list of  {@link Job}s
     */
    public static List<Job> map(List<JobEntity> jobEntities) {
        List<Job> list = new ArrayList<>();
        for (JobEntity jobEntity : jobEntities) {
            Job map = map(jobEntity);
            list.add(map);
        }
        return list;
    }

    /**
     * Converts a {@link Job} to a {@link JobEntity}.
     *
     * @param job the {@link Job}
     * @return the converted {@link JobEntity}
     */
    public static JobEntity mapToEntity(Job job) {
        JobEntity jobEntity = new JobEntity(
                job.getId(),
                job.getName(),
                job.getUrl(),
                job.getLastBuildStatus()
        );
        job.getBuilds().forEach(build -> {
            BuildEntity buildEntity = mapToEntity(build);
            jobEntity.addBuildEntity(buildEntity);
        });

        return jobEntity;
    }

    /**
     * Converts a list of {@link Job}s to a list of {@link JobEntity}s.
     *
     * @param jobs the {@link Job}s
     * @return the converted list of {@link JobEntity}s
     */
    public static List<JobEntity> mapToEntities(List<Job> jobs) {
        List<JobEntity> list = new ArrayList<>();
        for (Job job : jobs) {
            JobEntity jobEntity = mapToEntity(job);
            list.add(jobEntity);
        }
        return list;
    }

    /**
     * Provides the mapping from a given {@link BuildEntity} to {@link Build}.
     *
     * @param buildEntity the {@link BuildEntity} to convert
     * @return the converted {@link Build}
     */
    public static Build map(BuildEntity buildEntity) {
        Build build = new Build(
                buildEntity.getId(),
                buildEntity.getNumber(),
                buildEntity.getUrl()
        );
        buildEntity.getResultEntities().forEach(resultEntity -> {
            Result result = map(resultEntity);
            build.addResult(result);
        });

        return build;
    }

    /**
     * Provides the mapping from a given {@link Build} to {@link BuildEntity}.
     *
     * @param build the {@link Build} to convert
     * @return the converted {@link BuildEntity}
     */
    public static BuildEntity mapToEntity(Build build) {
        BuildEntity buildEntity = new BuildEntity(
                build.getId(),
                build.getNumber(),
                build.getUrl()
        );
        build.getResults().forEach(result -> {
            ResultEntity resultEntity = mapToEntity(result);
            buildEntity.addResultEntity(resultEntity);
        });

        return buildEntity;
    }

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
                    result.setOutstandingIssues(map(reportEntity));
                    break;
                case NEW:
                    result.setNewIssues(map(reportEntity));
                    break;
                case FIXED:
                    result.setFixedIssues(map(reportEntity));
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
        for (WarningTypeEntity warningTypeEntity : WarningTypeEntity.values()) {
            switch (warningTypeEntity) {
                case OUTSTANDING:
                    resultEntity.addReportEntity(mapToEntity(result.getOutstandingIssues(), warningTypeEntity));
                    break;
                case NEW:
                    resultEntity.addReportEntity(mapToEntity(result.getNewIssues(), warningTypeEntity));
                    break;
                case FIXED:
                    resultEntity.addReportEntity(mapToEntity(result.getFixedIssues(), warningTypeEntity));
                    break;
            }
        }

        return resultEntity;
    }

    /**
     * Converts {@link ReportEntity} to a {@link Report}.
     *
     * @param reportEntity the {@link ReportEntity}
     * @return the converted {@link Report}
     */
    public static Report map(ReportEntity reportEntity) {
        List<Issue> issues = reportEntity.getIssues().stream()
                .map(Mapper::map)
                .collect(Collectors.toList());

        return new Report().addAll(issues);
    }

    /**
     * Converts {@link Report} to a {@link ReportEntity}.
     *
     * @param issueReport       the {@link Report}
     * @param warningTypeEntity the {@link WarningTypeEntity} in the database (e.g. FIXED, OUTSTANDING or NEW)
     * @return the converted {@link ReportEntity}
     */
    public static ReportEntity mapToEntity(Report issueReport, WarningTypeEntity warningTypeEntity) {
        ReportEntity reportEntity = new ReportEntity(warningTypeEntity);
        issueReport.forEach(issue -> {
            IssueEntity issueEntity = mapToEntity(issue);
            reportEntity.addIssueEntity(issueEntity);
        });

        return reportEntity;
    }

    /**
     * Converts a {@link Issue} to a {@link IssueEntity}.
     *
     * @param issue the {@link Issue}
     * @return the converted {@link IssueEntity}
     */
    public static IssueEntity mapToEntity(Issue issue) {
        return new IssueEntity(
                issue.getId(),
                issue.getColumnStart(),
                issue.getColumnEnd(),
                issue.getLineStart(),
                issue.getLineEnd(),
                issue.getCategory(),
                issue.getDescription(),
                issue.getFileName(),
                issue.getFingerprint(),
                issue.getMessage(),
                issue.getModuleName(),
                issue.getOrigin(),
                issue.getPackageName(),
                issue.getReference(),
                issue.getSeverity().toString(),
                issue.getType()
        );
    }

    /**
     * Converts a {@link IssueEntity} to a {@link Issue}.
     *
     * @param issueEntity the {@link IssueEntity}
     * @return the converted {@link Issue}
     */
    public static Issue map(IssueEntity issueEntity) {
        return getIssue(
                issueEntity.getId(),
                issueEntity.getCategory(),
                issueEntity.getColumnEnd(),
                issueEntity.getColumnStart(),
                issueEntity.getDescription(),
                issueEntity.getFileName(),
                issueEntity.getFingerprint(),
                issueEntity.getLineEnd(),
                issueEntity.getLineStart(),
                issueEntity.getMessage(),
                issueEntity.getModuleName(),
                issueEntity.getOrigin(),
                issueEntity.getPackageName(),
                issueEntity.getReference(),
                issueEntity.getSeverity(),
                issueEntity.getType()
        );
    }

    /**
     * Converts a {@link IssuesResponse.Issue} to a {@link Issue}.
     *
     * @param issue the {@link IssuesResponse.Issue}
     * @return the converted {@link Issue}
     */
    public static Issue map(IssuesResponse.Issue issue) {
        return getIssue(
                issue.getId(),
                issue.getCategory(),
                issue.getColumnEnd(),
                issue.getColumnStart(),
                issue.getDescription(),
                issue.getFileName(),
                issue.getFingerprint(),
                issue.getLineEnd(),
                issue.getLineStart(),
                issue.getMessage(),
                issue.getModuleName(),
                issue.getOrigin(),
                issue.getPackageName(),
                issue.getReference(),
                issue.getSeverity(),
                issue.getType()
        );
    }

    private static Issue getIssue(UUID id, String category, int columnEnd, int columnStart, String description, String fileName, String fingerprint, int lineEnd, int lineStart, String message, String moduleName, String origin, String packageName, String reference, String severity, String type) {
        IssueBuilder issueBuilder = new IssueBuilder();
        if (id != null) {
            issueBuilder.setId(id);
        }

        return issueBuilder
                .setCategory(category)
                .setColumnEnd(columnEnd)
                .setColumnStart(columnStart)
                .setDescription(description)
                .setFileName(fileName)
                .setFingerprint(fingerprint)
                .setLineEnd(lineEnd)
                .setLineStart(lineStart)
                .setMessage(message)
                .setModuleName(moduleName)
                .setOrigin(origin)
                .setPackageName(packageName)
                .setReference(reference)
                .setSeverity(Severity.valueOf(severity))
                .setType(type)
                .build();
    }
}
