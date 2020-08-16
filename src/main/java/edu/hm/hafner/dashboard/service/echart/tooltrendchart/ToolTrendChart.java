package edu.hm.hafner.dashboard.service.echart.tooltrendchart;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.echarts.*;

/**
 * Builds the line model for a trend chart showing the total number of issues per tool for a given number of builds.
 *
 * @author Deniz Mardin
 */
public class ToolTrendChart {

    /**
     * Creates the {@link LinesChartModel} for a given tool (e.g. checkstyle or pmd).
     *
     * @param buildResults the build results to render
     * @param configuration the {@link ChartModelConfiguration}
     * @return the {@link LinesChartModel}
     */
    public LinesChartModel create(final Iterable<? extends BuildResult<Build>> buildResults,
                                  final ChartModelConfiguration configuration) {

        AggregatedAnalysisResultsSeriesBuilder builder = new AggregatedAnalysisResultsSeriesBuilder();
        LinesDataSet dataSet = builder.createDataSet(configuration, buildResults);

        LinesChartModel model = new LinesChartModel();
        model.setDomainAxisLabels(dataSet.getDomainAxisLabels());
        model.setBuildNumbers(dataSet.getBuildNumbers());

        Palette[] colors = Palette.values();
        int index = 0;
        for (String name : dataSet.getDataSetIds()) {
            LineSeries lineSeries;
            if(dataSet.getDataSetIds().size() > 1){
                lineSeries = new LineSeries(name, colors[index++].getNormal(), LineSeries.StackedMode.SEPARATE_LINES, LineSeries.FilledMode.LINES);
            }
            else {
                lineSeries = new LineSeries(name, colors[index++].getNormal(), LineSeries.StackedMode.STACKED, LineSeries.FilledMode.FILLED);
            }

            if (index == colors.length) {
                index = 0;
            }
            lineSeries.addAll(dataSet.getSeries(name));
            model.addSeries(lineSeries);
        }

        return model;
    }
}
