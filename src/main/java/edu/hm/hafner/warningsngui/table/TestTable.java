package edu.hm.hafner.warningsngui.table;

import io.jenkins.plugins.datatables.api.TableColumn;
import io.jenkins.plugins.datatables.api.TableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestTable extends TableModel {

    private final RepositoryStatistics statistics;

    public TestTable(RepositoryStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String getId() {
        return "forensics";
    }

    @Override
    public List<TableColumn> getColumns() {
        List<TableColumn> columns = new ArrayList<>();

        columns.add(new TableColumn("File", "fileName")/*.setWidth(2)*/);
        columns.add(new TableColumn("AuthorsSize", "authorsSize"));
        columns.add(new TableColumn("CommitsSize", "commitsSize"));
        columns.add(new TableColumn("LastCommit", "modifiedAt")
                /*.setWidth(2)
                .setHeaderClass(TableColumn.ColumnCss.DATE)*/);
        columns.add(new TableColumn("AddedAt", "addedAt")
                /*.setWidth(2)
                .setHeaderClass(TableColumn.ColumnCss.DATE)*/);

        return columns;
    }

    @Override
    public List<Object> getRows() {
        return statistics.getFileStatistics().stream().map(ForensicsRow::new).collect(Collectors.toList());
    }

    public static class ForensicsRow  {
        private final FileStatistics fileStatistics;

        ForensicsRow(final FileStatistics fileStatistics) {
            this.fileStatistics = fileStatistics;
        }

        public String getFileName() {
            return fileStatistics.getFileName();
        }

        public int getAuthorsSize() {
            return fileStatistics.getNumberOfAuthors();
        }

        public int getCommitsSize() {
            return fileStatistics.getNumberOfCommits();
        }

        public int getModifiedAt() {
            return fileStatistics.getLastModificationTime();
        }

        public int getAddedAt() {
            return fileStatistics.getCreationTime();
        }
    }
}
