package edu.hm.hafner.warningsngui.model.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_class",
        "assignedLabels",
        "mode",
        "nodeDescription",
        "nodeName",
        "numExecutors",
        "description",
        "jobs",
        "overallLoad",
        "primaryView",
        "quietingDown",
        "slaveAgentPort",
        "unlabeledLoad",
        "useCrumbs",
        "useSecurity",
        "views"
})
public class Hudson {

    @JsonProperty("_class")
    private String _class;
    @JsonProperty("assignedLabels")
    private List<AssignedLabel> assignedLabels = null;
    @JsonProperty("mode")
    private String mode;
    @JsonProperty("nodeDescription")
    private String nodeDescription;
    @JsonProperty("nodeName")
    private String nodeName;
    @JsonProperty("numExecutors")
    private Integer numExecutors;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("jobs")
    private List<Job> jobs = null;
    @JsonProperty("overallLoad")
    private OverallLoad overallLoad;
    @JsonProperty("primaryView")
    private PrimaryView primaryView;
    @JsonProperty("quietingDown")
    private Boolean quietingDown;
    @JsonProperty("slaveAgentPort")
    private Integer slaveAgentPort;
    @JsonProperty("unlabeledLoad")
    private UnlabeledLoad unlabeledLoad;
    @JsonProperty("useCrumbs")
    private Boolean useCrumbs;
    @JsonProperty("useSecurity")
    private Boolean useSecurity;
    @JsonProperty("views")
    private List<View> views = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_class")
    public String getClass_() {
        return _class;
    }

    @JsonProperty("_class")
    public void setClass_(String _class) {
        this._class = _class;
    }

    @JsonProperty("assignedLabels")
    public List<AssignedLabel> getAssignedLabels() {
        return assignedLabels;
    }

    @JsonProperty("assignedLabels")
    public void setAssignedLabels(List<AssignedLabel> assignedLabels) {
        this.assignedLabels = assignedLabels;
    }

    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @JsonProperty("nodeDescription")
    public String getNodeDescription() {
        return nodeDescription;
    }

    @JsonProperty("nodeDescription")
    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    @JsonProperty("nodeName")
    public String getNodeName() {
        return nodeName;
    }

    @JsonProperty("nodeName")
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @JsonProperty("numExecutors")
    public Integer getNumExecutors() {
        return numExecutors;
    }

    @JsonProperty("numExecutors")
    public void setNumExecutors(Integer numExecutors) {
        this.numExecutors = numExecutors;
    }

    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    @JsonProperty("jobs")
    public List<Job> getJobs() {
        return jobs;
    }

    @JsonProperty("jobs")
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @JsonProperty("overallLoad")
    public OverallLoad getOverallLoad() {
        return overallLoad;
    }

    @JsonProperty("overallLoad")
    public void setOverallLoad(OverallLoad overallLoad) {
        this.overallLoad = overallLoad;
    }

    @JsonProperty("primaryView")
    public PrimaryView getPrimaryView() {
        return primaryView;
    }

    @JsonProperty("primaryView")
    public void setPrimaryView(PrimaryView primaryView) {
        this.primaryView = primaryView;
    }

    @JsonProperty("quietingDown")
    public Boolean getQuietingDown() {
        return quietingDown;
    }

    @JsonProperty("quietingDown")
    public void setQuietingDown(Boolean quietingDown) {
        this.quietingDown = quietingDown;
    }

    @JsonProperty("slaveAgentPort")
    public Integer getSlaveAgentPort() {
        return slaveAgentPort;
    }

    @JsonProperty("slaveAgentPort")
    public void setSlaveAgentPort(Integer slaveAgentPort) {
        this.slaveAgentPort = slaveAgentPort;
    }

    @JsonProperty("unlabeledLoad")
    public UnlabeledLoad getUnlabeledLoad() {
        return unlabeledLoad;
    }

    @JsonProperty("unlabeledLoad")
    public void setUnlabeledLoad(UnlabeledLoad unlabeledLoad) {
        this.unlabeledLoad = unlabeledLoad;
    }

    @JsonProperty("useCrumbs")
    public Boolean getUseCrumbs() {
        return useCrumbs;
    }

    @JsonProperty("useCrumbs")
    public void setUseCrumbs(Boolean useCrumbs) {
        this.useCrumbs = useCrumbs;
    }

    @JsonProperty("useSecurity")
    public Boolean getUseSecurity() {
        return useSecurity;
    }

    @JsonProperty("useSecurity")
    public void setUseSecurity(Boolean useSecurity) {
        this.useSecurity = useSecurity;
    }

    @JsonProperty("views")
    public List<View> getViews() {
        return views;
    }

    @JsonProperty("views")
    public void setViews(List<View> views) {
        this.views = views;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
