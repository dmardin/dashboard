package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;

public class IssueMapper {

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

    public static Issue map(IssueEntity issueEntity){
        IssueBuilder issueBuilder = new IssueBuilder();
        if(issueEntity.getId() != null){
            issueBuilder.setId(issueEntity.getId());
        }
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
                .setSeverity(Severity.valueOf(issueEntity.getSeverity()))
                .setType(issueEntity.getType())
                .build();
    }
}
