package edu.hm.hafner.warningsngui.rest.response;

import edu.hm.hafner.warningsngui.model.IssueEntity;

public class IssuesResponse {
    private IssueEntity[] issues;

    public IssueEntity[] getIssues() {
        return issues;
    }

    public void setIssues(IssueEntity[] issues) {
        this.issues = issues;
    }
}
