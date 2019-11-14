package edu.hm.hafner.warningsngui.schedule;

import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.warningsngui.model.*;
import edu.hm.hafner.warningsngui.repository.IssueRepository;
import edu.hm.hafner.warningsngui.repository.JobRepository;
import edu.hm.hafner.warningsngui.rest.RestService;
import edu.hm.hafner.warningsngui.rest.response.*;
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
        JobsResponse jobsResponse = restService.getProjects();
        for (Job job : jobsResponse.getJobs()) {
            if (job.getName().contains("plagi")) {

                //Get Builds for every Job form Jenkins
                BuildsResponse buildsResponse = restService.getBuilds(job.getUrl() + "api/json");
                for (Build build : buildsResponse.getBuilds()) {
                    if (job.getBuilds() != null) {
                        job.getBuilds().add(build);
                        build.setJob(job);
                    }

                    //Get used Tools for every Build form Jenkins
                    ToolsResponse toolsResponse = restService.getTools(build.getUrl() + "warnings-ng/" + "api/json");
                    if (toolsResponse != null) {
                        Tool[] tools = toolsResponse.getTools();
                        for (Tool tool : tools) {

                            ToolDetailResponse toolDetailResponse = restService.getToolsDetail(build.getUrl() + tool.getId().toLowerCase() + "/api/json");

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

                                List<ErrorMessage> errorMessages = new ArrayList<>();
                                for(String err : toolDetailResponse.getErrorMessages()) {
                                    ErrorMessage em = new ErrorMessage();
                                    em.setMessage(err);
                                    em.setResult(result);
                                    errorMessages.add(em);
                                }
                                result.setErrorMessages(errorMessages);

                                List<InfoMessage> infoMessages = new ArrayList<>();
                                for(String info : toolDetailResponse.getInfoMessages()) {
                                    InfoMessage im = new InfoMessage();
                                    im.setMessage(info);
                                    im.setResult(result);
                                    infoMessages.add(im);
                                }
                                result.setInfoMessages(infoMessages);

                                //result.setErrorMessages(toolsDetailPayload.getErrorMessages());
                                //result.setInfoMessages(toolsDetailPayload.getInfoMessages());
                                result.setFixedSize(toolDetailResponse.getFixedSize());
                                result.setNewSize(toolDetailResponse.getNewSize());
                                result.setQualityGateStatus(toolDetailResponse.getQualityGateStatus());
                                result.setTotalSize(toolDetailResponse.getTotalSize());


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
                                    IssuesResponse issuesResponse = restService.getIssues(url);
                                    if (issuesResponse != null) {
                                        IssueEntity[] issues = issuesResponse.getIssues();
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
