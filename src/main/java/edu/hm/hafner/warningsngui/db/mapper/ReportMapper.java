package edu.hm.hafner.warningsngui.db.mapper;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.warningsngui.db.model.IssueEntity;
import edu.hm.hafner.warningsngui.db.model.ReportEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportMapper {

    public static List<IssueEntity> mapToEntities(Report report, ReportEntity reportEntity) {
        List<IssueEntity> issueEntities = new ArrayList<>();
        report.forEach(issue -> {
            IssueEntity issueEntity = IssueMapper.mapToEntity(issue);
            issueEntity.setIssues(reportEntity);
            issueEntities.add(issueEntity);
        });

        return issueEntities;
    }

    public static Report map(ReportEntity reportEntity) {
        List<Issue> issues = reportEntity.getIssues().stream()
                .map(IssueMapper::map)
                .collect(Collectors.toList());

        return new Report().addAll(issues);
    }
}
