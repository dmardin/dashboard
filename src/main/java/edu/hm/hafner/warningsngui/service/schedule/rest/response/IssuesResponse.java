package edu.hm.hafner.warningsngui.service.schedule.rest.response;

import edu.hm.hafner.warningsngui.db.model.IssueEntity;

public class IssuesResponse {
    private IssueEntity[] issues;

    public IssueEntity[] getIssues() {
        return issues;
    }

    public void setIssues(IssueEntity[] issues) {
        this.issues = issues;
    }
}
