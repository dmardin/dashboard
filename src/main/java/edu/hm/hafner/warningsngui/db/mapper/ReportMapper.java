package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;
import edu.hm.hafner.warningsngui.db.model.ReportEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enables the conversion from a {@link Report} to a list of {@link Issue}s and visa versa.
 *
 * @author Deniz Mardin
 */
public class ReportMapper {

    /**
     * Converts a {@link Report} to a list of {@link Issue}s.
     *
     * @param report the {@link Report}
     * @param reportEntity the corresponding {@link ReportEntity}
     * @return the list of {@link Issue}s
     */
    public static List<IssueEntity> mapToEntities(Report report, ReportEntity reportEntity) {
        List<IssueEntity> issueEntities = new ArrayList<>();
        report.forEach(issue -> {
            IssueEntity issueEntity = IssueMapper.mapToEntity(issue);
            issueEntity.setIssues(reportEntity);
            issueEntities.add(issueEntity);
        });

        return issueEntities;
    }

    /**
     * Converts {@link ReportEntity} to a {@link Report}.
     *
     * @param reportEntity the {@link ReportEntity}
     * @return the converte list of {@link Issue}s
     */
    public static Report map(ReportEntity reportEntity) {
        List<Issue> issues = reportEntity.getIssues().stream()
                .map(IssueMapper::map)
                .collect(Collectors.toList());

        return new Report().addAll(issues);
    }
}
