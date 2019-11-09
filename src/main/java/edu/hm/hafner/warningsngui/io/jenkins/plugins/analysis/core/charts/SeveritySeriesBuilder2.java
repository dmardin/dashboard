package edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts;

import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.util.AnalysisBuildResult;

import java.util.HashMap;
import java.util.Map;

public class SeveritySeriesBuilder2 {

    protected Map<String, Integer> computeSeries(final AnalysisBuildResult current) {
        Map<String, Integer> series = new HashMap<>();
        for (Severity severity : Severity.getPredefinedValues()) {
            series.put(severity.getName(), current.getTotalSizeOf(severity));
        }
        return series;
    }
}
