package edu.hm.hafner.dashboard.service.echart.tooltrendchart;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.echarts.SeriesBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the series for a line chart showing issues.
 *
 * @author Deniz Mardin
 */
public class AggregatedAnalysisResultsSeriesBuilder extends SeriesBuilder<Build> {
    @Override
    protected Map<String, Integer> computeSeries(Build current) {
        Map<String, Integer> series = new HashMap<>();
        current.getResults().forEach(r -> {
            series.put(r.getName(), r.getTotalSize());
        });
        return series;
    }
}
