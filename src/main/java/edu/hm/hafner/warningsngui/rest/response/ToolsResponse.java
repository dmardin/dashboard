package edu.hm.hafner.warningsngui.rest.response;

import edu.hm.hafner.warningsngui.model.helper.Tool;

public class ToolsResponse {
    private Tool[] tools;

    public Tool[] getTools() {
        return tools;
    }

    public void setTools(Tool[] tools) {
        this.tools = tools;
    }
}
