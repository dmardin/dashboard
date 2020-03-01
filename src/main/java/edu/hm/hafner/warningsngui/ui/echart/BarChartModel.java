package edu.hm.hafner.warningsngui.ui.echart;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model that represents a bar charts in the ui.
 *
 * @author Deniz Mardin
 */
public class BarChartModel {
    private final List<BarSeries> series = new ArrayList<>();
    private final List<String> legend = new ArrayList<>();
    private String id;

    /**
     * Creates a new instance of {@link BarChartModel}.
     */
    public BarChartModel() {
        this(StringUtils.EMPTY);
    }

    /**
     * Creates a new instance of {@link BarChartModel}.
     *
     * @param id the id
     */
    BarChartModel(final String id) {
        this.id = id;
    }

    /**
     * Setter to set the id of the {@link BarChartModel}.
     *
     * @param id the id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Returns the id of the {@link BarChartModel}.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Adds the series to this {@link BarChartModel}.
     *
     * @param barSeries the {@link BarSeries}
     */
    void addSeries(final List<BarSeries> barSeries) {
        series.addAll(barSeries);
    }

    /**
     * Adds the series to this {@link BarChartModel}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public void addSeries(final BarSeries... barSeries) {
        Collections.addAll(series, barSeries);
    }

    /**
     * Returns the series of the {@link BarChartModel}.
     *
     * @return the {@link BarSeries}
     */
    public List<BarSeries> getSeries() {
        return series;
    }

    /**
     * Adds the legends to this {@link BarChartModel}.
     *
     * @param legends the list of legends
     */
    void addLegend(final List<String> legends) {
        legend.addAll(legends);
    }

    /**
     * Adds the legends to this {@link BarChartModel}.
     *
     * @param legends the list of legends
     */
    public void addLegends(final String... legends) {
        Collections.addAll(legend, legends);
    }

    /**
     * Returns the legends of this {@link BarChartModel}.
     *
     * @return the list of legends
     */
    public List<String> getLegend() {
        return legend;
    }
}
