package edu.hm.hafner.dashboard.service.echart.severitytrendchart;

import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.echarts.*;
import edu.hm.hafner.echarts.LineSeries.FilledMode;
import edu.hm.hafner.echarts.LineSeries.StackedMode;

import java.util.List;

/**
 * Builds the model for a trend chart showing all issues by severity for a given number of builds.
 *
 * @author Ullrich Hafner
 */
public class SeverityTrendChart {

    public LinesChartModel create(final Iterable<? extends BuildResult<Build>> buildResults,
            final ChartModelConfiguration configuration) {
        SeveritySeriesBuilder builder = new SeveritySeriesBuilder();
        LinesDataSet dataSet = builder.createDataSet(configuration, buildResults);

        return createChartFromDataSet(dataSet);
    }

    private LinesChartModel createChartFromDataSet(final LinesDataSet dataSet) {
        LinesChartModel model = new LinesChartModel();
        model.setDomainAxisLabels(dataSet.getDomainAxisLabels());
        model.setBuildNumbers(dataSet.getBuildNumbers());

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
        return new LineSeries(getLocalizedString(severity),
                SeverityPalette.getColor(severity).getNormal(), StackedMode.STACKED, FilledMode.FILLED);
    }

    private String getLocalizedString(final Severity severity) {
        if (severity == Severity.ERROR) {
            return "Error";
        }
        if (severity == Severity.WARNING_HIGH) {
            return "High";
        }
        if (severity == Severity.WARNING_NORMAL) {
            return "Normal";
        }
        if (severity == Severity.WARNING_LOW) {
            return "Low";
        }
        return severity.getName();
    }
}
