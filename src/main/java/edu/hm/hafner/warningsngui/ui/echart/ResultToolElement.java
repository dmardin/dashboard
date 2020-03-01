package edu.hm.hafner.warningsngui.ui.echart;

/**
 * Defines the needed Bars for a {@link ResultChart}.
 */
public enum ResultToolElement {
    OLD_TOTAL_SIZE("Old Total Size"),
    FIXED("Fixed"),
    OUTSTANDING("Outstanding"),
    NEW("New"),
    OFFSET_TO_NEW_TOTAL_SIZE(""),
    NEW_TOTAL_SIZE("New Total Size");

    private String name;

    /**
     * Creates a new instance of the {@link ResultToolElement}.
     *
     * @param name the name of the {@link ResultToolElement}
     */
    ResultToolElement(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the {@link ResultToolElement}.
     *
     * @return the name of the {@link ResultToolElement}
     */
    public String getName() {
        return name;
    }
}
