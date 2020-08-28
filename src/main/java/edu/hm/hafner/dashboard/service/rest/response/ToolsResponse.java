package edu.hm.hafner.dashboard.service.rest.response;

/**
 * Represents a part of the {@link edu.hm.hafner.dashboard.service.dto.Result}s response from the Jenkins API.
 */
public class ToolsResponse {
    private Tool[] tools;

    /**
     * Returns the response of the {@link Tool}s.
     *
     * @return the {@link Tool}s
     */
    public Tool[] getTools() {
        return tools;
    }

    /**
     * Setter to set the response of the {@link Tool}s.
     *
     * @param tools the {@link Tool}s
     */
    public void setTools(final Tool[] tools) {
        this.tools = tools;
    }

    /**
     * Represents a Tool used as response from the Jenkins API.
     */
    public static class Tool {
        private String id;
        private String latestUrl;
        private String name;

        /**
         * Returns the id of the {@link Tool}.
         *
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * Setter to set the id of the {@link Tool}.
         *
         * @param id the id
         */
        public void setId(final String id) {
            this.id = id;
        }

        /**
         * Returns the latest url of the {@link Tool}.
         *
         * @return the latest url
         */
        public String getLatestUrl() {
            return latestUrl;
        }

        /**
         * Setter to set the latest url of the {@link Tool}.
         *
         * @param latestUrl the latest url
         */
        public void setLatestUrl(final String latestUrl) {
            this.latestUrl = latestUrl;
        }

        /**
         * Returns the name of the {@link Tool}.
         *
         * @return the name of the {@link Tool}
         */
        public String getName() {
            return name;
        }

        /**
         * Setter to set the name of the {@link Tool}.
         *
         * @param name the name
         */
        public void setName(final String name) {
            this.name = name;
        }
    }
}
