package edu.hm.hafner.warningsngui.db.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "issue")
public class IssueEntity {
    @Id
    private UUID id;
    private String baseName;
    private String category;
    private int columnStart;
    private int columnEnd;
    private String description;
    private String fileName;
    private String fingerprint;
    private int lineStart;
    private int lineEnd;
    @Column(length = 1024)
    private String message;
    private String moduleName;
    private String origin;
    private String packageName;
    private String reference;
    private String severity;
    private String type;
    @ManyToOne
    ReportEntity issues;

    public IssueEntity() {
        issues = new ReportEntity();
    }

    public IssueEntity(
            UUID id,
            int columnStart,
            int columnEnd,
            int lineStart,
            int lineEnd,
            String category,
            String description,
            String fileName,
            String fingerprint,
            String message,
            String moduleName,
            String origin,
            String packageName,
            String reference,
            String severity,
            String type) {
        this.id = id;
        this.category = category;
        this.columnStart = columnStart;
        this.columnEnd = columnEnd;
        this.description = description;
        this.fileName = fileName;
        this.fingerprint = fingerprint;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.message = message;
        this.moduleName = moduleName;
        this.origin = origin;
        this.packageName = packageName;
        this.reference = reference;
        this.severity = severity;
        this.type = type;
        issues = new ReportEntity();
    }

    public ReportEntity getIssues() {
        return issues;
    }

    public void setIssues(ReportEntity issues) {
        this.issues = issues;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getColumnEnd() {
        return columnEnd;
    }

    public void setColumnEnd(int columnEnd) {
        this.columnEnd = columnEnd;
    }

    public int getColumnStart() {
        return columnStart;
    }

    public void setColumnStart(int columnStart) {
        this.columnStart = columnStart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public int getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(int lineEnd) {
        this.lineEnd = lineEnd;
    }

    public int getLineStart() {
        return lineStart;
    }

    public void setLineStart(int lineStart) {
        this.lineStart = lineStart;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
