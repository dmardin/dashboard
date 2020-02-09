package edu.hm.hafner.warningsngui.ui.echart;

import edu.hm.hafner.echarts.Palette;
import edu.hm.hafner.warningsngui.service.dto.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResultChart {

    public BarChartModel create(Result result) {
        BarChartModel model = new BarChartModel();
        int outstandingSize = result.getOutstandingIssues().getSize();
        Palette[] colors = Palette.values();
        int index = 0;
        for (ResultToolElement resultToolElement : ResultToolElement.values()) {
            BarSeries series = null;
            switch(resultToolElement){
                case OLD_TOTAL_SIZE:
                    series = new BarSeries(resultToolElement.getName(), BarSeries.NONE_STACK, colors[index++].getNormal());
                    series.add(result.getFixedSize() +  outstandingSize);
                    break;
                case FIXED:
                    series = new BarSeries(resultToolElement.getName(), "centerStack", colors[index++].getNormal());
                    series.add(result.getFixedSize());
                    break;
                case OUTSTANDING:
                    series = new BarSeries(resultToolElement.getName(), "centerStack", colors[index++].getNormal());
                    series.add(outstandingSize);
                    break;
                case NEW:
                    series = new BarSeries(resultToolElement.getName(), "centerStack", colors[index++].getNormal());
                    series.add(result.getNewSize());
                    break;
                case OFFSET_TO_NEW_TOTAL_SIZE:
                    series = new BarSeries(resultToolElement.getName(), "bottomStack", ItemStyle.TRANSPARENT);
                    series.add(result.getFixedSize());
                    break;
                case NEW_TOTAL_SIZE:
                    series = new BarSeries(resultToolElement.getName(), "bottomStack", colors[index++].getNormal());
                    series.add(result.getTotalSize());
                    break;
            }
            model.addSeries(series);
        }
        List<String> resultToolValues = Arrays.stream(ResultToolElement.values()).filter(element -> !element.getName().isEmpty()).map(ResultToolElement::getName).collect(Collectors.toList());
        model.addLegend(resultToolValues);

        return model;
    }
}
