package edu.hm.hafner.warningsngui.ui.table.issue;

import java.util.UUID;

/**
 * Collects statistics for a issue.
 *
 * @author Deniz Mardin
 */
public class IssueStatistics {

    private UUID uuid;
    private String fileName;
    private String packageName;
    private String category;
    private String type;
    private String Severity;

    /**
     * Creates an instance of an {@link IssueStatistics}.
     *
     * @param uuid the uuid of the issue
     * @param fileName the file name of the issue
     * @param packageName the package name of the issue
     * @param category the category of the issue
     * @param type the type of the issue
     * @param severity the severity of the issue
     */
    public IssueStatistics(UUID uuid, String fileName, String packageName, String category, String type, String severity) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.packageName = packageName;
        this.category = category;
        this.type = type;
        Severity = severity;
    }

    /**
     * Returns the file name of the issue.
     *
     * @return the file name of the issue
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the package name of the issue.
     *
     * @return the package name of the issue
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Returns the category of the issue.
     *
     * @return the category of the issue
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the type of the issue.
     *
     * @return the type of the issue
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the severity of the issue.
     *
     * @return the severity of the issue
     */
    public String getSeverity() {
        return Severity;
    }

    /**
     * Returns the uuid of the issue.
     *
     * @return the uuid of the issue
     */
    public UUID getUuid() {
        return uuid;
    }
}
