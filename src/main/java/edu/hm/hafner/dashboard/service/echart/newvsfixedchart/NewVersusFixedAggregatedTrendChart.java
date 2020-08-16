package edu.hm.hafner.dashboard.service.echart.newvsfixedchart;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.echarts.ChartModelConfiguration;
import edu.hm.hafner.echarts.LinesChartModel;

/**
 * Provides the logic to aggregate new vs fixed issues in sum for each build.
 *
 * @author Deniz Mardin
 */
public class NewVersusFixedAggregatedTrendChart extends NewVersusFixedAbstractTrendChart{

    /**
     * Creates the line chart model for new vs fixed issues in sum for each build.
     *
     * @param buildResults the build results
     * @param configuration the configuration
     * @return the lines chart model
     */
    public LinesChartModel create(final Iterable<? extends BuildResult<Build>> buildResults,
                                  final ChartModelConfiguration configuration) {
        return createLineChart(new NewVersusFixedAggregatedSeriesBuilder(), buildResults, configuration);
    }
}
