package edu.hm.hafner.dashboard.service.echart.newvsfixedchart;


import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.echarts.SeriesBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Compute the size of new and fixed issues.
 *
 * @author Deniz Mardin
 */
public class NewVersusFixedSeriesBuilder extends NewVersusFixedAbstractSeriesBuilder {

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
