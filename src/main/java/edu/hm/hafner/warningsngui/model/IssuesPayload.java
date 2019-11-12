package edu.hm.hafner.warningsngui.model;

public class IssuesPayload {
    private IssueEntity[] issues;

    public IssueEntity[] getIssues() {
        return issues;
    }

    public void setIssues(IssueEntity[] issues) {
        this.issues = issues;
    }
}
