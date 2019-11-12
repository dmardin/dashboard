package edu.hm.hafner.warningsngui.schedule;

import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.model.*;
import edu.hm.hafner.warningsngui.repository.IssueRepository;
import edu.hm.hafner.warningsngui.repository.JobRepository;
import edu.hm.hafner.warningsngui.rest.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private RestService restService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Start getting Data");
        List<Job> allJobs = new ArrayList<>();
        JobsPayload jobsPayload = restService.getProjects();
        for (Job job : jobsPayload.getJobs()) {
            if (job.getName().contains("plagi")) {

                //Get Builds for every Job form Jenkins
                BuildsPayload buildsPayload = restService.getBuilds(job.getUrl() + "api/json");
                for (Build build : buildsPayload.getBuilds()) {
                    if (job.getBuilds() != null) {
                        job.getBuilds().add(build);
                        build.setJob(job);
                    }

                    //Get used Tools for every Build form Jenkins
                    ToolsPayload toolsPayload = restService.getTools(build.getUrl() + "warnings-ng/" + "api/json");
                    if (toolsPayload != null) {
                        Tool[] tools = toolsPayload.getTools();
                        for (Tool tool : tools) {
//                            if (tool.getId().contains("style")) {
//                                if (build.getTools() != null) {
//                                    build.getTools().add(tool);
//                                    tool.setBuild(build);
//                                }

                                ResultEntity result = new ResultEntity();
                                build.getResultEntities().add(result);
                                //result.setTool(tool);
                                result.setName(tool.getName());
                                result.setTotal(result.getTotal() + tool.getSize());
                                result.setBuild(build);

//                                List<IssueType> issueTypes = new ArrayList<>();
//                                issueTypes.add(IssueType.FIXED);
//                                issueTypes.add(IssueType.NEW);
//                                issueTypes.add(IssueType.OUTSTANDING);

//                                Report fixed = new Report();
//                                Report out = new Report();
//                                Report newW = new Report();
                                //Map<WarningType, IssueEntity> warnings = new LinkedHashMap<WarningType, IssueEntity>();

                                for (WarningType warningType : WarningType.values()) {
                                    IssuesEntity issuesEntity = new IssuesEntity();
                                    issuesEntity.setResultEntity(result);
                                    result.getIssues().add(issuesEntity);
                                    issuesEntity.setWarningType(warningType);
                                    String url = tool.getLatestUrl() + "/" + warningType.toString().toLowerCase() + "/api/json";
                                    IssuesPayload issuesPayload = restService.getIssues(url);
                                    if (issuesPayload != null) {
                                        IssueEntity[] issues = issuesPayload.getIssues();
                                        for (IssueEntity issue : issues) {
                                            issuesEntity.getIssues().add(issue);
                                            issue.setIssues(issuesEntity);
                                            //TODO issues EntityRepository fehlt !!!!
                                            //issueRepository.save(issue);

                                            //warnings.put(warningType, issue);
                                            //
                                            //set issue Type!!!

//                                            if(issueType == IssueType.FIXED){
//                                                fixed.add(convertIssue(issue));
//                                            } else if (issueType == IssueType.NEW) {
//                                                newW.add(convertIssue(issue));
//                                            } else if (issueType == IssueType.OUTSTANDING) {
//                                                out.add(convertIssue(issue));
//                                            }


                                            //tool.getIssues().add(issue);
                                            //issue.setTool(tool);

                                        }
                                        //ArrayList list = new ArrayList<>(Arrays.asList(issues));
                                        //result.setIssues(list);
                                    }

                                    //result.setWarnings(warnings);
                                }
                                //result.setFixedIssuesReference(fixed);
                                //result.setNewIssuesReference(newW);
                                //result.setOutstandingIssuesReference(out);


//                            }
                        }
                    }
                }
            }
            allJobs.add(job);
        }
        jobRepository.saveAll(allJobs);
        System.out.println("Finitooooooooooooooooooooooooooooooooooooooooooooooo");
    }


    private edu.hm.hafner.analysis.Issue convertIssue(IssueEntity issue) {
        IssueBuilder issueBuilder = new IssueBuilder();
        //TODO Linerange
        return issueBuilder
                .setCategory(issue.getCategory())
                .setColumnEnd(issue.getColumnEnd())
                .setColumnStart(issue.getColumnStart())
                .setDescription(issue.getDescription())
                .setFileName(issue.getFileName())
                .setFingerprint(issue.getFingerprint())
                .setLineEnd(issue.getLineEnd())
                .setLineStart(issue.getLineStart())
                .setMessage(issue.getMessage())
                .setModuleName(issue.getModuleName())
                .setOrigin(issue.getOrigin())
                .setPackageName(issue.getPackageName())
                .setReference(issue.getReference())
                .setSeverity(Severity.guessFromString(issue.getSeverity()))
                .setType(issue.getType())
                .build();
    }
}
