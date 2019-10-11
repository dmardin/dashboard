package edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts;

import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts.LineSeries.FilledMode;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts.LineSeries.StackedMode;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.util.AnalysisBuildResult;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.util.LocalizedSeverity;

import java.util.List;

/**
 * Builds the model for a trend chart showing all issues by severity for a given number of builds.
 *
 * @author Ullrich Hafner
 */
public class SeverityTrendChart implements TrendChart {
    @Override
    public LinesChartModel create(final Iterable<? extends AnalysisBuildResult> results,
            final ChartModelConfiguration configuration) {
        SeveritySeriesBuilder builder = new SeveritySeriesBuilder();
        LinesDataSet dataSet = builder.createDataSet(configuration, results);

        LinesChartModel model = new LinesChartModel();
        model.addXAxisLabels(dataSet.getXAxisLabels());

        Severity[] visibleSeverities
                = {Severity.WARNING_LOW, Severity.WARNING_NORMAL, Severity.WARNING_HIGH, Severity.ERROR};
        for (Severity severity : visibleSeverities) {
            List<Integer> values = dataSet.getSeries(severity.getName());
            if (values.stream().anyMatch(integer -> integer > 0)) {
                LineSeries series = createSeries(severity);
                series.addAll(values);
                model.addSeries(series);
            }
        }

        return model;
    }

    private LineSeries createSeries(final Severity severity) {
        return new LineSeries(LocalizedSeverity.getLocalizedString(severity),
                SeverityPalette.getColor(severity).getNormal(), StackedMode.STACKED, FilledMode.FILLED);
    }
}
