package edu.hm.hafner.warningsngui.ui.echart;

public enum ResultToolElement {
    OLD_TOTAL_SIZE("Old Total Size"),
    FIXED("Fixed"),
    OUTSTANDING("Outstanding"),
    NEW("New"),
    OFFSET_TO_NEW_TOTAL_SIZE(""),
    NEW_TOTAL_SIZE("New Total Size");

    private String name;

    ResultToolElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
