package edu.hm.hafner.dashboard.service.echart.resultchart;

import edu.hm.hafner.dashboard.service.dto.Result;
import edu.hm.hafner.echarts.Palette;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Builds the {@link BarChartModel} for a given {@link Result}.
 */
public class ResultChart {

    /**
     * Creates a {@link BarChartModel} for a given {@link Result}.
     *
     * @param result the {@link Result}
     * @return the {@link BarChartModel}
     */
    public BarChartModel create(Result result) {
        BarChartModel model = new BarChartModel();
        int outstandingSize = result.getOutstandingIssues().getSize();
        Palette[] colors = Palette.values();
        int index = 0;
        for (BarType barType : BarType.values()) {
            BarSeries series = null;
            switch (barType) {
                case OLD_TOTAL_SIZE:
                    series = new BarSeries(barType.getName(), BarSeries.NONE_STACK, colors[index++].getNormal());
                    series.add(result.getFixedSize() + outstandingSize);
                    break;
                case FIXED:
                    series = new BarSeries(barType.getName(), "centerStack", colors[index++].getNormal());
                    series.add(result.getFixedSize());
                    break;
                case OUTSTANDING:
                    series = new BarSeries(barType.getName(), "centerStack", colors[index++].getNormal());
                    series.add(outstandingSize);
                    break;
                case NEW:
                    series = new BarSeries(barType.getName(), "centerStack", colors[index++].getNormal());
                    series.add(result.getNewSize());
                    break;
                case OFFSET_TO_NEW_TOTAL_SIZE:
                    series = new BarSeries(barType.getName(), "bottomStack", ItemStyle.TRANSPARENT);
                    series.add(result.getFixedSize());
                    break;
                case NEW_TOTAL_SIZE:
                    series = new BarSeries(barType.getName(), "bottomStack", colors[index++].getNormal());
                    series.add(result.getTotalSize());
                    break;
            }
            model.addSeries(series);
        }
        List<String> resultToolValues = Arrays.stream(BarType.values()).filter(element -> !element.getName().isEmpty()).map(BarType::getName).collect(Collectors.toList());
        model.addLegend(resultToolValues);

        return model;
    }
}
