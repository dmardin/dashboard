package edu.hm.hafner.warningsngui.service.echart.newvsfixedchart;

import edu.hm.hafner.echarts.*;
import edu.hm.hafner.warningsngui.service.dto.Build;

/**
 * Provides the logic for the new vs fixed trend chart.
 *
 * @author Deniz Mardin
 */
public abstract class NewVersusFixedAbstractTrendChart {

    /**
     * Creates the lines charts model depending on the builder.
     *
     * @param builder the builder
     * @param buildResults the buildResult
     * @param configuration the configuration
     * @return the lines chart model
     */
    protected LinesChartModel createLineChart(SeriesBuilder<Build> builder, final Iterable<? extends BuildResult<Build>> buildResults,
                              final ChartModelConfiguration configuration) {
        LinesDataSet dataSet = builder.createDataSet(configuration, buildResults);

        LinesChartModel model = new LinesChartModel();
        model.setDomainAxisLabels(dataSet.getDomainAxisLabels());

        LineSeries newSeries = getSeries(dataSet, "New", Palette.RED,
                NewVersusFixedSeriesBuilder.NEW);
        LineSeries fixedSeries = getSeries(dataSet, "Fixed", Palette.GREEN,
                NewVersusFixedSeriesBuilder.FIXED);

        model.addSeries(newSeries, fixedSeries);

        return model;
    }

    private LineSeries getSeries(final LinesDataSet dataSet,
                                   final String name, final Palette color, final String dataSetId) {
        LineSeries newSeries = new LineSeries(name, color.getNormal(), LineSeries.StackedMode.SEPARATE_LINES, LineSeries.FilledMode.FILLED);
        newSeries.addAll(dataSet.getSeries(dataSetId));
        return newSeries;
    }
}
