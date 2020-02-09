package edu.hm.hafner.warningsngui.ui.table.job;


import io.jenkins.plugins.datatables.api.TableModel;

import java.util.List;

/**
 * Model to view a table that contains the jobs.
 *
 * @author Deniz Mardin
 */
public class JobViewTable {

    private final JobRepositoryStatistics jobRepositoryStatistics;

    /**
     * Creates a new instance of the {@link JobViewTable}.
     *
     * @param jobRepositoryStatistics the repository with the job statistics for the table
     */
    public JobViewTable(JobRepositoryStatistics jobRepositoryStatistics) {
        this.jobRepositoryStatistics = jobRepositoryStatistics;
    }

    /**
     * Returns the {@link JobTableModel}.
     * @param id the id of the table
     *
     * @return the {@link JobTableModel}
     */
    public TableModel getTableModel(String id) {
        JobTableModel jobTableModel = new JobTableModel(jobRepositoryStatistics);
        if(jobTableModel.getId().equals(id)){
            return jobTableModel;
        }
        else {
            throw new IllegalArgumentException(String.format("Needed id for this JobTableModel is jobs but currently used %s", id));
        }

    }

    /**
     * Returns the rows of the {@link JobTableModel}.
     *
     * @param id the id of the {@link JobTableModel}
     * @return the rows of the {@link JobTableModel}
     */
    public List<Object> getTableRows(final String id){
        return getTableModel(id).getRows();
    }
}
