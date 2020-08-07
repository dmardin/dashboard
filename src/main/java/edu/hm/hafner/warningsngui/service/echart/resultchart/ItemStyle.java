package edu.hm.hafner.warningsngui.service.echart.resultchart;

/**
 * Model to define the item style in the ui.
 */
public class ItemStyle {
    public static String TRANSPARENT = "transparent";
    private final String barBorderColor;
    private final String color;

    /**
     * Creates a new instance of {@link ItemStyle}.
     *
     * @param barBorderColor the bar border color
     * @param color          the main color
     */
    public ItemStyle(String barBorderColor, String color) {
        this.barBorderColor = barBorderColor;
        this.color = color;
    }

    /**
     * Returns the bar border color of the {@link ItemStyle}.
     *
     * @return the bar border color
     */
    public String getBarBorderColor() {
        return barBorderColor;
    }

    /**
     * Returns the color of the {@link ItemStyle}.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }
}
