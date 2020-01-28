package edu.hm.hafner.warningsngui.echart;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarChartModel {
    private final List<BarSeries> series = new ArrayList<>();
    private final List<String> legendData = new ArrayList<>();

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

    void addLegendData(final List<String> legendDatas) {
        legendData.addAll(legendDatas);
    }

    public void addLegendDatas(final String... legendDatas) {
        Collections.addAll(legendData, legendDatas);
    }

    public List<String> getLegendData() {
        return legendData;
    }
}
