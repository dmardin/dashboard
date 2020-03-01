package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;
import edu.hm.hafner.warningsngui.db.model.ReportEntity;
import edu.hm.hafner.warningsngui.db.model.WarningTypeEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Enables the conversion from a {@link Report} to a {@link ReportEntity} and visa versa.
 *
 * @author Deniz Mardin
 */
public class ReportMapper {

    /**
     * Converts {@link ReportEntity} to a {@link Report}.
     *
     * @param reportEntity the {@link ReportEntity}
     * @return the converted {@link Report}
     */
    public static Report map(ReportEntity reportEntity) {
        List<Issue> issues = reportEntity.getIssues().stream()
                .map(IssueMapper::map)
                .collect(Collectors.toList());

        return new Report().addAll(issues);
    }

    /**
     * Converts {@link Report} to a {@link ReportEntity}.
     *
     * @param issueReport the {@link Report}
     * @param warningTypeEntity the {@link WarningTypeEntity} in the database (e.g. FIXED, OUTSTANDING or NEW)
     * @return the converted {@link ReportEntity}
     */
    public static ReportEntity mapToEntity(Report issueReport, WarningTypeEntity warningTypeEntity) {
        ReportEntity reportEntity = new ReportEntity(warningTypeEntity);
        issueReport.forEach(issue -> {
            IssueEntity issueEntity = IssueMapper.mapToEntity(issue);
            reportEntity.addIssueEntity(issueEntity);
        });

        return reportEntity;
    }
}
