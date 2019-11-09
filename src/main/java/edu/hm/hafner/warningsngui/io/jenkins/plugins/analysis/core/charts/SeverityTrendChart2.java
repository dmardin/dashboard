package edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts;

import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.util.LocalizedSeverity;
import edu.hm.hafner.warningsngui.model.Build;
import edu.hm.hafner.warningsngui.model.Job;
import edu.hm.hafner.warningsngui.model.Tool;

import java.util.*;

public class SeverityTrendChart2 {

    private static int counter = 0;

    public LinesChartModel create(final Job job,
                                  final ChartModelConfiguration configuration) {
        SeveritySeriesBuilder builder = new SeveritySeriesBuilder();
//        LinesDataSet dataSet = builder.createDataSet(configuration, results);
        LinesDataSet dataSet = new LinesDataSet();

        Map<String, Integer> dataSetValues = new TreeMap<>();
        List<Build> builds =  job.getBuilds();
        List<Build> sortedBuilds = new ArrayList<>();
        for (int i = builds.size(); i-- > 0; ) {
            sortedBuilds.add(builds.get(i));
        }


        for (Build build : sortedBuilds) {
            List<Tool> tools = build.getTools();
            for (Tool tool : tools) {
                if (tool.getId().contains("style")) {
                    dataSetValues.put("ERROR",tool.getSize());
                    dataSet.add("#"+build.getNumber(), dataSetValues);
                }

            }
        }




        LinesChartModel model = new LinesChartModel();
        model.addXAxisLabels(dataSet.getXAxisLabels());

        Severity[] visibleSeverities = {/*Severity.WARNING_LOW, Severity.WARNING_NORMAL, Severity.WARNING_HIGH,*/ Severity.ERROR};
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

    public LinesChartModel create2(final Job job,
                                  final ChartModelConfiguration configuration) {
        SeveritySeriesBuilder builder = new SeveritySeriesBuilder();
//        LinesDataSet dataSet = builder.createDataSet(configuration, results);
        LinesDataSet dataSet = new LinesDataSet();

        Map<String, Integer> dataSetValues = new TreeMap<>();
        List<Build> builds =  job.getBuilds();
        List<Build> sortedBuilds = new ArrayList<>();
        for (int i = builds.size(); i-- > 0; ) {
            sortedBuilds.add(builds.get(i));
        }


        for (Build build : sortedBuilds) {
            List<Tool> tools = build.getTools();
            for (Tool tool : tools) {
                if (tool.getId().contains("pmd")) {
                    dataSetValues.put("NORMAL",tool.getSize());
                    dataSet.add("#"+build.getNumber()+" :"+counter, dataSetValues);
                    counter++;
                }

            }
        }




        LinesChartModel model = new LinesChartModel();
        model.addXAxisLabels(dataSet.getXAxisLabels());

        Severity[] visibleSeverities = {/*Severity.WARNING_LOW,*/ Severity.WARNING_NORMAL/*, Severity.WARNING_HIGH, Severity.ERROR*/};
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
                SeverityPalette.getColor(severity).getNormal(), LineSeries.StackedMode.STACKED, LineSeries.FilledMode.FILLED);
    }
}
