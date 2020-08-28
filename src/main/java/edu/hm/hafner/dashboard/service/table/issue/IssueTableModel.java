package edu.hm.hafner.dashboard.service.table.issue;

import edu.hm.hafner.analysis.Issue;
import io.jenkins.plugins.datatables.TableColumn;
import io.jenkins.plugins.datatables.TableModel;

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
        super();
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
        columns.add(new TableColumn("Category", "category"));
        columns.add(new TableColumn("ModuleName", "moduleName"));
        columns.add(new TableColumn("Package", "package"));
        columns.add(new TableColumn("File", "fileName")/*.setWidth(2)*/);
        columns.add(new TableColumn("Type", "type"));
        columns.add(new TableColumn("Severity", "severity"));
        columns.add(new TableColumn("Reference", "reference"));
        columns.add(new TableColumn("Line", "line"));
        columns.add(new TableColumn("Column", "column"));

        return columns;
    }

    /**
     * Provides the rows for the {@link IssueTableModel}.
     *
     * @return the rows of the table
     */
    @Override
    public List<Object> getRows() {
        return statistics.getIssue().stream().map(IssuesRow::new).collect(Collectors.toList());
    }

    /**
     * A single line in the table that contains the statistics of a issue.
     */
    public static class IssuesRow  {

        private final Issue issue;

        /**
         * Creates a new instance of a {@link IssuesRow}.
         *
         * @param issue the issue statistic for one line
         */
        IssuesRow(final Issue issue) {
            this.issue = issue;
        }

        /**
         * Returns the file name of the issue.
         *
         * @return the file name of the issue
         */
        public String getFileName() {
            return issue.getFileName();
        }

        /**
         * Returns the package of the issue.
         *
         * @return the package of the issue
         */
        public String getPackage() {
            return issue.getPackageName();
        }

        /**
         * Returns the category of the issue.
         *
         * @return the category of the issue
         */
        public String getCategory() {
            return issue.getCategory();
        }

        /**
         * Returns the type of the issue.
         *
         * @return the type of the issue
         */
        public String getType() {
            return issue.getType();
        }

        /**
         * Returns the severity of the issue.
         *
         * @return the severity of the issue
         */
        public String getSeverity() {
            return issue.getSeverity().toString();
        }

        /**
         * Returns the module name of the issue.
         *
         * @return the module name of the issue
         */
        public String getModuleName() {
            return issue.getModuleName();
        }

        /**
         * Returns the reference of the issue.
         *
         * @return the reference of the issue
         */
        public String getReference() {
            return issue.getReference();
        }

        /**
         * Returns the start and end line of the issue.
         *
         * @return the start and end line of the issue
         */
        public String getLine() {
            return issue.getLineStart() + ":" + issue.getLineEnd();
        }

        /**
         * Returns the start and end column of the issue.
         *
         * @return the start and end column of the issue
         */
        public String getColumn() {
            return issue.getColumnStart() + ":" + issue.getColumnEnd();
        }
    }
}
