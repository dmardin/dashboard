package edu.hm.hafner.warningsngui.dto;

import edu.hm.hafner.analysis.Report;

import java.util.ArrayList;
import java.util.List;

public enum WarningType {
    FIXED,
    OUTSTANDING,
    NEW;

    private List<Report> reports = new ArrayList<>();

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
