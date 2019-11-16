package edu.hm.hafner.warningsngui.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.model.IssueEntity;
import edu.hm.hafner.warningsngui.model.ReportEntity;

import java.util.ArrayList;
import java.util.List;

public class ReportMapper {

//    public static Report map(IssuesEntity issuesEntity){
//        Report report = new Report();
//        report.add(IssueMapper.map(issuesEntity));
//
//        return report;
//    }



    public static List<IssueEntity> mapToEntities(Report report, ReportEntity reportEntity) {

        List<IssueEntity> issueEntities = new ArrayList<>();
        report.stream().forEach(issue -> {
            IssueEntity issueEntity = new IssueEntity();
            issueEntity.setBaseName(issue.getBaseName());
            issueEntity.setCategory(issue.getCategory());
            issueEntity.setColumnStart(issue.getColumnStart());
            issueEntity.setColumnEnd(issue.getColumnEnd());
            issueEntity.setLineStart(issue.getLineStart());
            issueEntity.setLineEnd(issue.getLineEnd());
            issueEntity.setDescription(issue.getDescription());
            issueEntity.setFileName(issue.getFileName());
            issueEntity.setFingerprint(issue.getFingerprint());
            issueEntity.setMessage(issue.getMessage());
            issueEntity.setModuleName(issue.getModuleName());
            issueEntity.setOrigin(issue.getOrigin());
            issueEntity.setReference(issue.getReference());
            issueEntity.setPackageName(issue.getPackageName());
            issueEntity.setType(issue.getType());
            issueEntity.setSeverity(issue.getSeverity().toString()); //TODO prüfen ob serverity stimmt/ einfach vconvertiert werden kann
            issueEntity.setIssues(reportEntity);

            issueEntities.add(issueEntity);
        });
        return issueEntities;
    }


    public static Issue map(IssueEntity issueEntity){
        IssueBuilder issueBuilder = new IssueBuilder();
        //TODO Linerange
        return issueBuilder
                .setCategory(issueEntity.getCategory())
                .setColumnEnd(issueEntity.getColumnEnd())
                .setColumnStart(issueEntity.getColumnStart())
                .setDescription(issueEntity.getDescription())
                .setFileName(issueEntity.getFileName())
                .setFingerprint(issueEntity.getFingerprint())
                .setLineEnd(issueEntity.getLineEnd())
                .setLineStart(issueEntity.getLineStart())
                .setMessage(issueEntity.getMessage())
                .setModuleName(issueEntity.getModuleName())
                .setOrigin(issueEntity.getOrigin())
                .setPackageName(issueEntity.getPackageName())
                .setReference(issueEntity.getReference())
                .setSeverity(Severity.guessFromString(issueEntity.getSeverity()))
                .setType(issueEntity.getType())
                .build();
    }

    public static Report map(ReportEntity reportEntity) {
        Report report = new Report();
        for (IssueEntity issueEntity: reportEntity.getIssues()) {
            report.add(map(issueEntity));
        }
        return report;
    }
}
