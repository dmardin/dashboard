package edu.hm.hafner.warningsngui.model;

import javax.persistence.*;

@Entity
@Table(name="tool")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int identifier;
    private String id;
    private String latestUrl;
    private String name;
    private int size;

//    @ManyToOne
//    private Build build;

//    @OneToMany(mappedBy = "tool", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<IssueEntity> issues = new ArrayList<>();

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatestUrl() {
        return latestUrl;
    }

    public void setLatestUrl(String latestUrl) {
        this.latestUrl = latestUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

//    public Build getBuild() {
//        return build;
//    }
//
//    public void setBuild(Build build) {
//        this.build = build;
//    }

//    public List<Issue> getIssues() {
//        return issues;
//    }
//
//    public void setIssues(List<Issue> issues) {
//        this.issues = issues;
//    }
}
