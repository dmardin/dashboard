package edu.hm.hafner.warningsngui.ui.echart;

import edu.hm.hafner.echarts.SeriesBuilder;
import edu.hm.hafner.warningsngui.service.dto.Build;

import java.util.HashMap;
import java.util.Map;

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
