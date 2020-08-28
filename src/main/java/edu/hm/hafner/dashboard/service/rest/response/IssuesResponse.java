package edu.hm.hafner.dashboard.service.rest.response;

import java.util.UUID;

/**
 * Represents a {@link Issue}s response from the Jenkins API.
 */
public class IssuesResponse {
    private Issue[] issues;

    /**
     * Returns the response of the {@link Issue}s.
     *
     * @return the {@link Issue}s
     */
    public Issue[] getIssues() {
        return issues;
    }

    /**
     * Setter to set the response of the {@link Issue}s.
     *
     * @param issues the {@link Issue}s
     */
    public void setIssues(final Issue[] issues) {
        this.issues = issues;
    }

    /**
     * Represents a Issue used as response from the Jenkins API.
     */
    public static class Issue {
        private UUID id;
        private String category;
        private int columnStart;
        private int columnEnd;
        private String description;
        private String fileName;
        private String fingerprint;
        private int lineStart;
        private int lineEnd;
        private String message;
        private String moduleName;
        private String origin;
        private String packageName;
        private String reference;
        private String severity;
        private String type;

        public UUID getId() {
            return id;
        }

        public void setId(final UUID id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(final String category) {
            this.category = category;
        }

        public int getColumnStart() {
            return columnStart;
        }

        public void setColumnStart(final int columnStart) {
            this.columnStart = columnStart;
        }

        public int getColumnEnd() {
            return columnEnd;
        }

        public void setColumnEnd(final int columnEnd) {
            this.columnEnd = columnEnd;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(final String fileName) {
            this.fileName = fileName;
        }

        public String getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(final String fingerprint) {
            this.fingerprint = fingerprint;
        }

        public int getLineStart() {
            return lineStart;
        }

        public void setLineStart(final int lineStart) {
            this.lineStart = lineStart;
        }

        public int getLineEnd() {
            return lineEnd;
        }

        public void setLineEnd(final int lineEnd) {
            this.lineEnd = lineEnd;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(final String moduleName) {
            this.moduleName = moduleName;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(final String origin) {
            this.origin = origin;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(final String reference) {
            this.reference = reference;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(final String severity) {
            this.severity = severity;
        }

        public String getType() {
            return type;
        }

        public void setType(final String type) {
            this.type = type;
        }
    }
}
