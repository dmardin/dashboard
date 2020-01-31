package edu.hm.hafner.warningsngui.ui.echart;

public class ItemStyle {
    public static String TRANSPARENT = "transparent";
    private String barBorderColor;
    private String color;

    public ItemStyle(String barBorderColor, String color) {
        this.barBorderColor = barBorderColor;
        this.color = color;
    }

    public String getBarBorderColor() {
        return barBorderColor;
    }

    public String getColor() {
        return color;
    }
}
