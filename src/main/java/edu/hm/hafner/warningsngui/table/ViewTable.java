package edu.hm.hafner.warningsngui.table;

import io.jenkins.plugins.datatables.api.DefaultAsyncTableContentProvider;
import io.jenkins.plugins.datatables.api.TableModel;

public class ViewTable extends DefaultAsyncTableContentProvider {

    private final RepositoryStatistics repositoryStatistics;

    public ViewTable(RepositoryStatistics repositoryStatistics) {
        this.repositoryStatistics = repositoryStatistics;
    }

    @Override
    public TableModel getTableModel(String s) {
        return new TestTable(repositoryStatistics);
    }

    String getDisplayName() {
        return "DisplayName from ModelObject";
    }
}
