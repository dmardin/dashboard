package edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts;

import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.util.AnalysisBuildResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the series for a line chart showing the total of issues for each tool.
 *
 * @author Ullrich Hafner
 */
public class ToolSeriesBuilder extends SeriesBuilder {
    @Override
    protected Map<String, Integer> computeSeries(final AnalysisBuildResult current) {
        return new HashMap<>(current.getSizePerOrigin());
    }
}
