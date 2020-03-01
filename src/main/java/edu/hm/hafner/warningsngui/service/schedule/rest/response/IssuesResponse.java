package edu.hm.hafner.warningsngui.service.schedule.rest.response;

import edu.hm.hafner.warningsngui.db.model.IssueEntity;

/**
 * Represents a {@link IssueEntity}s response from the Jenkins API.
 */
public class IssuesResponse {
    private IssueEntity[] issues;

    /**
     * Returns the response of the {@link IssueEntity}s.
     *
     * @return the {@link IssueEntity}s
     */
    public IssueEntity[] getIssues() {
        return issues;
    }

    /**
     * Setter to set the response of the {@link IssueEntity}s.
     *
     * @param issues the {@link IssueEntity}s
     */
    public void setIssues(IssueEntity[] issues) {
        this.issues = issues;
    }
}
