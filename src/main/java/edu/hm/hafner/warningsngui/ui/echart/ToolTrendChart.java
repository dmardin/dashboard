package edu.hm.hafner.warningsngui.ui.echart;

import edu.hm.hafner.echarts.*;
import edu.hm.hafner.warningsngui.service.dto.Build;

public class ToolTrendChart {

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
