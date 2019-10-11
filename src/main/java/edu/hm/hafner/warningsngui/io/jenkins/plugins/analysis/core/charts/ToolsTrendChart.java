package edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts;

import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts.LineSeries.FilledMode;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts.LineSeries.StackedMode;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.util.AnalysisBuildResult;

/**
 * Builds the line model for a trend chart showing the total number of issues per tool for a given number of builds.
 *
 * @author Ullrich Hafner
 */
public class ToolsTrendChart implements TrendChart {
    @Override
    public LinesChartModel create(final Iterable<? extends AnalysisBuildResult> results,
            final ChartModelConfiguration configuration) {
        ToolSeriesBuilder builder = new ToolSeriesBuilder();
        LinesDataSet lineModel = builder.createDataSet(configuration, results);

        LinesChartModel model = new LinesChartModel();

        Palette[] colors = Palette.values();
        int index = 0;
        for (String name : lineModel.getDataSetIds()) {
            LineSeries lineSeries = new LineSeries(name, colors[index++].getNormal(),
                    StackedMode.SEPARATE_LINES, FilledMode.LINES);
            if (index == colors.length) {
                index = 0;
            }
            lineSeries.addAll(lineModel.getSeries(name));
            model.addSeries(lineSeries);
        }

        model.addXAxisLabels(lineModel.getXAxisLabels());

        return model;
    }
}
