package edu.hm.hafner.dashboard.service.echart.severitytrendchart;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.dashboard.service.dto.Result;
import edu.hm.hafner.echarts.SeriesBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the series for a stacked line chart showing all issues by severity.
 *
 * @author Ullrich Hafner
 */
public class SeveritySeriesBuilder extends SeriesBuilder<Build> {
    @Override
    protected Map<String, Integer> computeSeries(final Build build) {
        Map<String, Integer> series = new HashMap<>();
        for (Severity severity : Severity.getPredefinedValues()) {
            series.put(severity.getName(), getTotalSizeOf(build, severity));
        }
        return series;
    }


    private int getTotalSizeOf(Build build, Severity severity) {
        int totalSize = 0;
        for(Result result : build.getResults()){
            Report report = new Report();
            report.addAll(result.getOutstandingIssues());
            report.addAll(result.getNewIssues());

            totalSize = totalSize + report.getSizeOf(severity);
        }
        return totalSize;
    }
}
