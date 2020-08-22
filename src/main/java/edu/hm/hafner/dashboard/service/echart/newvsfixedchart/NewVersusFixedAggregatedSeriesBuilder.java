package edu.hm.hafner.dashboard.service.echart.newvsfixedchart;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.dashboard.service.dto.Result;
import edu.hm.hafner.echarts.SeriesBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Computes the size of new and fixed issues in sum.
 *
 * @author Deniz Mardin
 */
public class NewVersusFixedAggregatedSeriesBuilder extends NewVersusFixedAbstractSeriesBuilder {

    @Override
    protected Map<String, Integer> computeSeries(final Build current) {
        Map<String, Integer> series = new HashMap<>();
        if(!current.getResults().isEmpty()) {
            int newWarnings = current.getResults().stream().mapToInt(Result::getNewSize).sum();
            int fixedWarnings = current.getResults().stream().mapToInt(Result::getFixedSize).sum();
            series.put(NEW, newWarnings);
            series.put(FIXED, fixedWarnings);
        }

        return series;
    }
}
