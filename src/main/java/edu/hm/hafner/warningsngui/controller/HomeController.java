package edu.hm.hafner.warningsngui.controller;

import com.google.gson.Gson;
import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Job;
import edu.hm.hafner.warningsngui.dto.Result;
import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.table.FileStatistics;
import edu.hm.hafner.warningsngui.table.RepositoryStatistics;
import edu.hm.hafner.warningsngui.table.TestTable;
import edu.hm.hafner.warningsngui.table.ViewTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

    @Autowired
    JobService jobService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET)
    public String home(final Model model) {
        //TODO check if this is used!
        logger.info("Normal GET was called");
        return "home";
    }

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET, params = {"getProjects"})
    public String getProjects(final Model model) {
        logger.info("GET with Parameter was called");
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        /*
        for (Job job : jobs) {
            System.out.println(job.getName() + " " + job.getColor() + " " + job.getUrl());
            for (Build build: job.getBuilds()) {
                System.out.println("    Build Number "+ build.getNumber() + " url:" + build.getUrl());
                for (Result result: build.getResults()) {
                    System.out.println("        ResultName " + result.getName() + " Fixed: " + result.getFixedSize() + " New:" + result.getNewSize());

                }
            }
        }*/
        model.addAttribute("jobs", jobs);
        return "home";
    }

    @RequestMapping(path={"/job/{jobName}/build"}, method=RequestMethod.GET)
    public String getBuilds(@PathVariable("jobName") String jobName, final Model model) {
        //TODO check if this is used!
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        model.addAttribute("job", neededJob);
        logger.info("Normal GET was called");
        return "build";
    }

    // /job/kniffel/build/6
    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}"}, method=RequestMethod.GET)
    public String getResults(@PathVariable("jobName") String jobName, @PathVariable("buildNumber") Integer buildNumber,final Model model) {
        //TODO check if this is used!
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build build = neededJob.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();
        model.addAttribute("build", build);
        logger.info("Normal GET was called");
        return "result";
    }

    //http://localhost:8181/job/kniffel/build/4/checkstyle/outstanding
    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}/{issueType}"}, method=RequestMethod.GET)
    public String getIssue(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            @PathVariable("issueType") String issueType,
            final Model model) {
        //TODO check if this is used!
        List<Job> jobs = jobService.createDistributionOfAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build build = neededJob.getBuilds().stream().filter(b -> b.getNumber() == buildNumber).findFirst().get();

        Result result = null;
        switch (issueType) {
            case "outstanding":
                result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
                model.addAttribute("issues", result.getOutstandingIssues());
                break;
            case "fixed":
                result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
                model.addAttribute("issues", result.getFixedIssues());
                break;
            case "new":
                result = build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
                model.addAttribute("issues", result.getNewIssues());
                break;
        }
        logger.info("Normal GET was called");
        return "issue";
    }

    @RequestMapping(path = "/table", method = RequestMethod.GET/*, produces = "application/json"*/)
    //@ResponseBody
    /*ResponseEntity<*/String/*>*/ getTable(final Model model) {
        model.addAttribute("m2", testData());
        return "myTable";
        //return ResponseEntity.ok(gson.toJson(testTable.getRows()));
    }
//[{"data": "fileName"},{"data": "authorsSize"},{"data": "commitsSize"},{"data": "modifiedAt"},{"data": "addedAt"}]


    @RequestMapping(path = "/ajax/table", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    String getTableData() {
        return testData().getTableRows("");
    }

    private ViewTable testData() {
        RepositoryStatistics repositoryStatistics = new RepositoryStatistics();
        ArrayList<FileStatistics> fileStatisticsArrayList = new ArrayList<>();
        for(int i = 0; i < 555; i++ ){
            FileStatistics fileStatistics = new FileStatistics("File "+ getSaltString());
            fileStatistics.setCreationTime(i);
            fileStatistics.setLastModificationTime(i);
            fileStatistics.setNumberOfAuthors(i);
            fileStatistics.setNumberOfCommits(i);

            fileStatisticsArrayList.add(fileStatistics);
        }

        repositoryStatistics.addAll(fileStatisticsArrayList);
        TestTable testTable = new TestTable(repositoryStatistics);
        String columnDef = testTable.getColumnsDefinition();
        testTable.getRows();
        Gson gson = new Gson();

        return new ViewTable(repositoryStatistics);
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @RequestMapping(path = "/ajax/checkstyle", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<String> getCheckstyle() {
//        Job job = jobRepository.fetchJobWithId(1);
        return createTestResponseFrom();
    }

    private ResponseEntity<String> createTestResponseFrom() {
        String testResponse = "{\n" +
                "   \"series\":[\n" +
                "      {\n" +
                "         \"name\":\"Error\",\n" +
                "         \"type\":\"line\",\n" +
                "         \"symbol\":\"circle\",\n" +
                "         \"data\":[\n" +
                "            1869,\n" +
                "            1869,\n" +
                "            1831,\n" +
                "            1791,\n" +
                "            1791,\n" +
                "            1730,\n" +
                "            1693,\n" +
                "            1567,\n" +
                "            1517,\n" +
                "            1353,\n" +
                "            1265,\n" +
                "            1227,\n" +
                "            1172,\n" +
                "            1180,\n" +
                "            1172\n" +
                "         ],\n" +
                "         \"itemStyle\":{\n" +
                "            \"color\":\"#EF9A9A\"\n" +
                "         },\n" +
                "         \"areaStyle\":{\n" +
                "            \"normal\":true\n" +
                "         },\n" +
                "         \"stack\":\"stacked\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"id\":\"\",\n" +
                "   \"xAxisLabels\":[\n" +
                "      \"#3\",\n" +
                "      \"#4\",\n" +
                "      \"#5\",\n" +
                "      \"#6\",\n" +
                "      \"#7\",\n" +
                "      \"#8\",\n" +
                "      \"#9\",\n" +
                "      \"#10\",\n" +
                "      \"#11\",\n" +
                "      \"#12\",\n" +
                "      \"#13\",\n" +
                "      \"#14\",\n" +
                "      \"#15\",\n" +
                "      \"#16\",\n" +
                "      \"#17\"\n" +
                "   ]\n" +
                "}";
        return ResponseEntity.ok(new Gson().toJson(testResponse));
    }
}
