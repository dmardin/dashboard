package edu.hm.hafner.warningsngui.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String _class;
    private int number;
    private String url;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="job_id")
    private Job job;

    @OneToMany(mappedBy = "build")
    private List<Tool> tools;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
