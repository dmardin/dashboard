package edu.hm.hafner.warningsngui.ui.table.issue;

import io.jenkins.plugins.datatables.api.TableColumn;
import io.jenkins.plugins.datatables.api.TableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configures the structure for the table that contains the issues statistics.
 *
 * @author Deniz Mardin
 */
public class IssueTableModel extends TableModel {

    private final IssueRepositoryStatistics statistics;

    /**
     * Creates a new of {@link IssueTableModel}.
     *
     * @param statistics the repository with the issue statistics
     */
    public IssueTableModel(IssueRepositoryStatistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Returns the id of the {@link IssueTableModel}.
     *
     * @return the id of the {@link IssueTableModel}
     */
    @Override
    public String getId() {
        return "issues";
    }

    /**
     * Provides the configured columns of the {@link IssueTableModel}.
     *
     * @return the columns of the table
     */
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

    /**
     * Provides the rows for the {@link IssueTableModel}.
     *
     * @return the rows of the table
     */
    @Override
    public List<Object> getRows() {
        return statistics.getIssueStatistics().stream().map(IssuesRow::new).collect(Collectors.toList());
    }

    /**
     * A single line in the table that contains the statistics of a issue.
     */
    public static class IssuesRow  {

        private final IssueStatistics issueStatistics;

        /**
         * Creates a new instance of a {@link IssuesRow}
         *
         * @param issueStatistics the issue statistic for one line
         */
        IssuesRow(final IssueStatistics issueStatistics) {
            this.issueStatistics = issueStatistics;
        }

        /**
         * Returns the file name of the issue.
         *
         * @return the file name of the issue
         */
        public String getFileName() {
            return issueStatistics.getFileName();
        }

        /**
         * Returns the package of the issue.
         *
         * @return the package of the issue
         */
        public String getPackage() {
            return issueStatistics.getPackageName();
        }

        /**
         * Returns the category of the issue.
         *
         * @return the category of the issue
         */
        public String getCategory() {
            return issueStatistics.getCategory();
        }

        /**
         * Returns the type of the issue.
         *
         * @return the type of the issue
         */
        public String getType() {
            return issueStatistics.getType();
        }

        /**
         * Returns the severity of the issue.
         *
         * @return the severity of the issue
         */
        public String getSeverity() {
            return issueStatistics.getSeverity();
        }
    }
}
