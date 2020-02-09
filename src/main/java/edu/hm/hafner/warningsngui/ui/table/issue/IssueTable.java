package edu.hm.hafner.warningsngui.ui.table.issue;

import io.jenkins.plugins.datatables.api.TableColumn;
import io.jenkins.plugins.datatables.api.TableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IssueTable extends TableModel {

    private final RepoStatistics statistics;

    public IssueTable(RepoStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String getId() {
        return "issues";
    }

    @Override
    public List<TableColumn> getColumns() {
        List<TableColumn> columns = new ArrayList<>();
        columns.add(new TableColumn("File", "fileName")/*.setWidth(2)*/);
        columns.add(new TableColumn("Package", "package"));
        columns.add(new TableColumn("Category", "category"));
        columns.add(new TableColumn("Type", "type"));
        columns.add(new TableColumn("Severity", "severity"));

        return columns;
    }

    @Override
    public List<Object> getRows() {
        return statistics.getFileStatistics().stream().map(IssuesRow::new).collect(Collectors.toList());
    }

    public static class IssuesRow  {
        private final IssueStatistics issueStatistics;

        IssuesRow(final IssueStatistics issueStatistics) {
            this.issueStatistics = issueStatistics;
        }

        public String getFileName() {
            return issueStatistics.getFileName();
        }

        public String getPackage() {
            return issueStatistics.getPackageName();
        }

        public String getCategory() {
            return issueStatistics.getCategory();
        }

        public String getType() {
            return issueStatistics.getType();
        }

        public String getSeverity() {
            return issueStatistics.getSeverity();
        }

    }
}
