package edu.hm.hafner.warningsngui.echart;

import edu.hm.hafner.echarts.SeriesBuilder;
import edu.hm.hafner.warningsngui.dto.Result;

import java.util.HashMap;
import java.util.Map;

public class ExampleSeriesBuilder extends SeriesBuilder<Result> {

    @Override
    protected Map<String, Integer> computeSeries(Result result) {
        Map<String, Integer> series = new HashMap<>();

        series.put(result.getName(), result.getFixedSize());
        /*series.put("Error1", 11);
        series.put("Error2", 22);
        series.put("Error3", 33);
        series.put("Error4", 44);*/
        /*for (Severity severity : Severity.getPredefinedValues()) {
            series.put(severity.getName(), myObject.getTotalSizeOf(severity));
        }*/
        return series;
    }
}
