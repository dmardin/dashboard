package edu.hm.hafner.warningsngui.controller;

import com.google.gson.Gson;
import edu.hm.hafner.warningsngui.model.*;
import edu.hm.hafner.warningsngui.repository.BuildRepository;
import edu.hm.hafner.warningsngui.repository.JobRepository;
import edu.hm.hafner.warningsngui.repository.ToolRepository;
import edu.hm.hafner.warningsngui.rest.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private RestService restService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private ToolRepository toolRepository;

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET)
    public String home(final Model model) {
        /*
        String result = restService.getDataFromJenkins("http://localhost:8080/jenkins/job/plagi/7/warnings-ng/api/json");
        System.out.println(result);
        model.addAttribute("resultJson", result);

        String result1 = restService.getDataFromJenkins("http://localhost:8080/jenkins/job/plagi/7/checkstyle/api/json");
        System.out.println(result1);
        model.addAttribute("resultJson1", result1);

        String result2 = restService.getDataFromJenkins("http://localhost:8080/jenkins/job/plagi/7/checkstyle/new/api/json");
        System.out.println(result2);
        model.addAttribute("resultJson2", result2);

        String result3 = restService.getDataFromJenkins("http://localhost:8080/jenkins/job/plagi/7/pmd/all/api/json");
        System.out.println(result3);
        model.addAttribute("resultJson3", result3);
         */


        //String example = restService.getProjects();

        return "home";
    }

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET, params={"getProjects"})
    public String getProjects(final Model model) {
        //get Jobs form Jenkins
        JobsPayload jobsPayload = restService.getProjects();
        for (Job job: jobsPayload.getJobs()) {
            jobRepository.save(job);

            //Get Builds for every Job form Jenkins
            BuildsPayload buildsPayload = restService.getBuilds(job.getUrl() +"api/json");
            for (Build build : buildsPayload.getBuilds()) {
                buildRepository.save(build);

                //Get used Tools for every Build form Jenkins
                ToolsPayload toolsPayload = restService.getTools(build.getUrl() + "warnings-ng/" + "api/json");
                if (toolsPayload != null){
                    Tool[] tools = toolsPayload.getTools();
                    for (Tool tool : tools) {
                        toolRepository.save(tool);
                    }
                }
            }
        }






        return "home";
    }


    @RequestMapping(path = "/ajax/builds", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<String> getBuilds(/*@RequestParam("origin") final String origin,
                                         @RequestParam("reference") final String reference*/) {
        List<Build> builds = buildRepository.findAll();
        return createResponseFrom(builds);
    }

    private ResponseEntity<String> createResponseFrom(final Object model) {
        //return ResponseEntity.ok(new Gson().toJson(model));
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
