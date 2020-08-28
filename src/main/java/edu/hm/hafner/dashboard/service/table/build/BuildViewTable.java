package edu.hm.hafner.dashboard.service.table.build;

import io.jenkins.plugins.datatables.TableModel;

import java.util.List;

/**
 * Model to view a table that contains the builds.
 * 
 * @author Deniz Mardin
 */
public class BuildViewTable {
    private final BuildRepositoryStatistics buildRepositoryStatistics;

    /**
     * Creates a new instance of the {@link BuildViewTable}.
     *
     * @param buildRepositoryStatistics the repository with the build statistics for the table
     */
    public BuildViewTable(BuildRepositoryStatistics buildRepositoryStatistics) {
        this.buildRepositoryStatistics = buildRepositoryStatistics;
    }

    /**
     * Returns the {@link BuildTableModel}.
     *
     * @param id the id of the table
     * @return the {@link BuildTableModel}
     */
    public TableModel getTableModel(String id) {
        BuildTableModel buildTableModel = new BuildTableModel(buildRepositoryStatistics);
        if (buildTableModel.getId().equals(id)) {
            return buildTableModel;
        } else {
            throw new IllegalArgumentException(String.format("Needed id for this BuildTableModel is builds but currently used %s", id));
        }

    }

    /**
     * Returns the rows of the {@link BuildTableModel}.
     *
     * @param id the id of the {@link BuildTableModel}
     * @return the rows of the {@link BuildTableModel}
     */
    public List<Object> getTableRows(final String id) {
        return getTableModel(id).getRows();
    }
}
