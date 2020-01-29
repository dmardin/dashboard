package edu.hm.hafner.warningsngui.echart;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarChartModel {
    private final List<BarSeries> series = new ArrayList<>();
    private final List<String> legend = new ArrayList<>();

    private String id;

    public BarChartModel() {
        this(StringUtils.EMPTY);
    }

    BarChartModel(final String id) {
        this.id = id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    void addSeries(final List<BarSeries> barSeries) {
        series.addAll(barSeries);
    }

    public void addSeries(final BarSeries... barSeries) {
        Collections.addAll(series, barSeries);
    }

    public List<BarSeries> getSeries() {
        return series;
    }

    void addLegend(final List<String> legends) {
        legend.addAll(legends);
    }

    public void addLegends(final String... legends) {
        Collections.addAll(legend, legends);
    }

    public List<String> getLegend() {
        return legend;
    }
}
