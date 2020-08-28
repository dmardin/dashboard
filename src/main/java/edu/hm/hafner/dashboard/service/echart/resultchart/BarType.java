package edu.hm.hafner.dashboard.service.echart.resultchart;

/**
 * Defines the needed Bars for a {@link ResultChart}.
 */
public enum BarType {
    OLD_TOTAL_SIZE("Old Total Size"),
    FIXED("Fixed"),
    OUTSTANDING("Outstanding"),
    NEW("New"),
    OFFSET_TO_NEW_TOTAL_SIZE(""),
    NEW_TOTAL_SIZE("New Total Size");

    private final String name;

    /**
     * Creates a new instance of the {@link BarType}.
     *
     * @param name the name of the {@link BarType}
     */
    BarType(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the {@link BarType}.
     *
     * @return the name of the {@link BarType}
     */
    public String getName() {
        return name;
    }
}
