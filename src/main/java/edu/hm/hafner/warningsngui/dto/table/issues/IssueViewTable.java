package edu.hm.hafner.warningsngui.dto.table.issues;

import io.jenkins.plugins.datatables.api.DefaultAsyncTableContentProvider;
import io.jenkins.plugins.datatables.api.TableModel;

public class IssueViewTable extends DefaultAsyncTableContentProvider {

    private final RepoStatistics repoStatistics;

    public IssueViewTable(RepoStatistics repoStatistics) {
        this.repoStatistics = repoStatistics;
    }

    @Override
    public TableModel getTableModel(String s) {
        return new IssueTable(repoStatistics);
    }
}
