package edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts;

import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.util.AnalysisBuildResult;

/**
 * Creates a model for a trend chart of a given number of static analysis build results.
 */
public interface TrendChart {
    /**
     * Creates the chart for the specified results.
     *
     * @param results
     *         the analysis results to render
     * @param configuration
     *         the chart configuration to be used
     *
     * @return the chart model
     */
    LinesChartModel create(Iterable<? extends AnalysisBuildResult> results, ChartModelConfiguration configuration);
}
