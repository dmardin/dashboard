package edu.hm.hafner.warningsngui.service.echart.resultchart;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Model that represents a bar series in the ui.
 *
 * @author Deniz Mardin
 */
public class BarSeries {
    public final static String NONE_STACK = "";
    private final String name;
    @SuppressFBWarnings("SS_SHOULD_BE_STATIC")
    private final String type = "bar";
    @SuppressFBWarnings("SS_SHOULD_BE_STATIC")
    private final List<Integer> data = new ArrayList<>();
    private final String stack;
    private final Optional<ItemStyle> itemStyle;

    /**
     * Creates a new instance of {@link BarChartModel}.
     *
     * @param name the name of the series
     * @param stack the stack of the series
     * @param color the color of the series
     */
    public BarSeries(final String name, final String stack, final String color) {
        this.name = name;
        this.stack = stack;
        this.itemStyle = Optional.of(new ItemStyle(color, color));
    }

    /**
     * Returns the name of the {@link BarSeries}.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the {@link BarSeries}.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the stack of the {@link BarSeries}.
     *
     * @return the stack
     */
    public String getStack() {
        return stack;
    }

    /**
     * Returns the {@link ItemStyle} as {@link Optional} of the {@link BarSeries}.
     *
     * @return the {@link ItemStyle} as {@link Optional}
     */
    public Optional<ItemStyle> getItemStyle() {
        return itemStyle;
    }

    /**
     * Returns the data of the {@link BarSeries}.
     *
     * @return the data
     */
    public List<Integer> getData() {
        return data;
    }

    /**
     * Adds a new value to the {@link BarSeries}.
     *
     * @param value the value
     */
    public void add(final int value) {
        data.add(0, value);
    }

    /**
     * Adds a new list of values to the {@link BarSeries}.
     *
     * @param values the list of values
     */
    public void addAll(final List<Integer> values) {
        data.addAll(values);
    }
}
