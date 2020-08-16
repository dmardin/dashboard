package edu.hm.hafner.dashboard.service.echart.newvsfixedchart;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.echarts.BuildResult;
import edu.hm.hafner.echarts.ChartModelConfiguration;
import edu.hm.hafner.echarts.LinesChartModel;

/**
 * Provides the model for new vs fixed issues for given builds.
 *
 * @author Deniz Mardin
 */
public class NewVersusFixedTrendChart extends NewVersusFixedAbstractTrendChart{

    /**
     * Creates the line chart model for new vs. fixed issues.
     *
     * @param buildResults the build results
     * @param configuration the configuration
     * @return the line chart model
     */
    public LinesChartModel create(final Iterable<? extends BuildResult<Build>> buildResults,
                                  final ChartModelConfiguration configuration) {
        return createLineChart(new NewVersusFixedSeriesBuilder(), buildResults, configuration);
    }
}
