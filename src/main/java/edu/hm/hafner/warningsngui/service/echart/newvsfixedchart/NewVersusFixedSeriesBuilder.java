package edu.hm.hafner.warningsngui.service.echart.newvsfixedchart;


import edu.hm.hafner.echarts.SeriesBuilder;
import edu.hm.hafner.warningsngui.service.dto.Build;

import java.util.HashMap;
import java.util.Map;

/**
 * Compute the size of new and fixed issues.
 *
 * @author Deniz Mardin
 */
public class NewVersusFixedSeriesBuilder extends SeriesBuilder<Build> {

    static final String NEW = "new";
    static final String FIXED = "fixed";

    @Override
    protected Map<String, Integer> computeSeries(final Build current) {
        Map<String, Integer> series = new HashMap<>();
        current.getResults().forEach(r -> {
            series.put(NEW, r.getNewSize());
            series.put(FIXED, r.getFixedSize());
        });

        return series;
    }
}
