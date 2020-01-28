package edu.hm.hafner.warningsngui.echart;

import edu.hm.hafner.warningsngui.dto.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResultChart {

    public BarChartModel create(Result result) {

        BarChartModel model = new BarChartModel();
        int outstandingSize = result.getOutstandingIssues().getSize();
        for (ResultToolElement resultToolElement : ResultToolElement.values()) {
            BarSeries series = null;
            switch(resultToolElement){
                case OLD_TOTAL_SIZE:
                    series = new BarSeries(resultToolElement.getName());
                    series.add(result.getFixedSize() +  outstandingSize);
                    break;
                case FIXED:
                    series = new BarSeries(resultToolElement.getName(), "centerStack");
                    series.add(result.getFixedSize());
                    break;
                case OUTSTANDING:
                    series = new BarSeries(resultToolElement.getName(), "centerStack");
                    series.add(outstandingSize);
                    break;
                case NEW:
                    series = new BarSeries(resultToolElement.getName(), "centerStack");
                    series.add(result.getNewSize());
                    break;
                case OFFSET_TO_NEW_TOTAL_SIZE:
                    series = new BarSeries(resultToolElement.getName(), "bottomStack");
                    series.add(result.getFixedSize());
                    break;
                case NEW_TOTAL_SIZE:
                    series = new BarSeries(resultToolElement.getName(), "bottomStack");
                    series.add(result.getTotalSize());
                    break;
                default:
                    //TODO throw an error ?
                    break;
            }
            model.addSeries(series);
        }

        List<String> resultToolValues = Arrays.stream(ResultToolElement.values()).filter(element -> !element.getName().isEmpty()).map(ResultToolElement::getName).collect(Collectors.toList());
        model.addLegendData(resultToolValues);

        return model;
    }
}
