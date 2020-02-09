package edu.hm.hafner.warningsngui.ui.table.job;

import io.jenkins.plugins.datatables.api.TableColumn;
import io.jenkins.plugins.datatables.api.TableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configures the structure for the table that contains the jobs statistics.
 *
 * @author Deniz Mardin
 */
public class JobTableModel extends TableModel {

    private final JobRepositoryStatistics jobRepositoryStatistics;

    /**
     * Creates a new instance of {@link JobTableModel}.
     *
     * @param jobRepositoryStatistics the repository with the job statistics
     */
    public JobTableModel(JobRepositoryStatistics jobRepositoryStatistics) {
        this.jobRepositoryStatistics = jobRepositoryStatistics;
    }

    /**
     * Returns the id of the {@link JobTableModel}.
     *
     * @return the id of the {@link JobTableModel}
     */
    @Override
    public String getId() {
        return "jobs";
    }

    /**
     * Provides the configured columns of the {@link JobTableModel}.
     *
     * @return the columns of the table
     */
    @Override
    public List<TableColumn> getColumns() {
        List<TableColumn> columns = new ArrayList<>();
        columns.add(new TableColumn("Job Name","jobName"));
        columns.add(new TableColumn("Status","jobStatus"));
        columns.add(new TableColumn("Url","jobUrl"));

        return columns;
    }

    /**
     * Provides the rows for the {@link JobTableModel}.
     *
     * @return the rows of the table
     */
    @Override
    public List<Object> getRows() {
        return jobRepositoryStatistics.getJobStatistics().stream().map(JobsRow::new).collect(Collectors.toList());
    }

    /**
     * A single line in the table that contains the statistics of a job.
     */
    public static class JobsRow {

        private final JobStatistics jobStatistics;

        /**
         * Creates a new instance of a {@link JobsRow}
         *
         * @param jobStatistics the job statistic for one line
         */
        JobsRow(final JobStatistics jobStatistics){
            this.jobStatistics = jobStatistics;
        }

        /**
         * Returns the name of the job.
         *
         * @return the name
         */
        public String getJobName() {
            return jobStatistics.getJobName();
        }

        /**
         * Returns the url of the job.
         *
         * @return the url
         */
        public String getJobUrl() {
            return jobStatistics.getJobUrl();
        }

        /**
         * Returns the status of the job.
         *
         * @return the status
         */
        public String getJobStatus() {
            return jobStatistics.getJobStatus();
        }
    }
}
