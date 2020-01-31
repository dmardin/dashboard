package edu.hm.hafner.warningsngui.ui.table;

import io.jenkins.plugins.datatables.api.TableModel;

import java.util.List;

public class IssueViewTable {

    private final RepoStatistics repoStatistics;

    public IssueViewTable(RepoStatistics repoStatistics) {
        this.repoStatistics = repoStatistics;
    }

    public TableModel getTableModel(String s) {
        return new IssueTable(repoStatistics);
    }

    public List<Object> getTableRows(final String id) {
        return getTableModel(id).getRows();
    }
}
