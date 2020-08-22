package edu.hm.hafner.dashboard.service.echart.newvsfixedchart;

import edu.hm.hafner.dashboard.service.dto.Build;
import edu.hm.hafner.echarts.SeriesBuilder;

/**
 * Abstract Class that extends from {@link SeriesBuilder} and provides object variables NEW and FIXED.
 *
 * @author Deniz Mardin
 */
public abstract class NewVersusFixedAbstractSeriesBuilder extends SeriesBuilder<Build> {
    static final String NEW = "new";
    static final String FIXED = "fixed";
}
