package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;

/**
 * Enables the conversion from a {@link Issue} to a {@link IssueEntity} and visa versa.
 *
 * @author Deniz Mardin
 */
public class IssueMapper {

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
    public static Issue map(IssueEntity issueEntity){
        IssueBuilder issueBuilder = new IssueBuilder();
        if(issueEntity.getId() != null){
            issueBuilder.setId(issueEntity.getId());
        }

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
