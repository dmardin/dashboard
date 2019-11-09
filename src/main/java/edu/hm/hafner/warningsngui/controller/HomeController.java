package edu.hm.hafner.warningsngui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts.ChartModelConfiguration;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts.LinesChartModel;
import edu.hm.hafner.warningsngui.io.jenkins.plugins.analysis.core.charts.SeverityTrendChart2;
import edu.hm.hafner.warningsngui.model.*;
import edu.hm.hafner.warningsngui.model.test.Hudson;
import edu.hm.hafner.warningsngui.repository.BuildRepository;
import edu.hm.hafner.warningsngui.repository.IssueRepository;
import edu.hm.hafner.warningsngui.repository.JobRepository;
import edu.hm.hafner.warningsngui.repository.ToolRepository;
import edu.hm.hafner.warningsngui.rest.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private IssueRepository issueRepository;

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

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET, params = {"getProjects"})
    public String getProjects(final Model model) {
        //get Jobs form Jenkins
        List<Job> allJobs = new ArrayList<>();
        JobsPayload jobsPayload = restService.getProjects();
        for (Job job : jobsPayload.getJobs()) {
//            if (job.getName().contains("plagi")) {

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
                            if (tool.getId().contains("style")) {
                                if (build.getTools() != null) {
                                    build.getTools().add(tool);
                                    tool.setBuild(build);
                                }

                                List<IssueType> issueTypes = new ArrayList<>();
                                issueTypes.add(IssueType.FIXED);
                                issueTypes.add(IssueType.NEW);
                                issueTypes.add(IssueType.OUTSTANDING);
                                for (IssueType issueType : issueTypes) {
                                    String url = tool.getLatestUrl() + "/" + issueType.toString().toLowerCase() + "/api/json";
                                    IssuesPayload issuesPayload = restService.getIssues(url);
                                    if (issuesPayload != null) {
                                        Issue[] issues = issuesPayload.getIssues();
                                        List<Issue> addedIssues = new ArrayList<>();
                                        for (Issue issue : issues) {
                                            //set issue Type!!!
                                            issue.setIssueType(issueType);
                                            tool.getIssues().add(issue);
                                            issue.setTool(tool);
                                            addedIssues.add(issue);

                                        }

                                        allJobs.add(job);
                                    }
                                }

                            }
                        }
                    }
                }
//            }
        }
        jobRepository.saveAll(allJobs);
        System.out.println("*** Saving Data finished ***");

        return "home";
    }


    @RequestMapping(path = "/ajax/checkstyle", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<String> getCheckstyle(/*@RequestParam("origin") final String origin,
                                         @RequestParam("reference") final String reference*/) {
        //getProjects(null);
        Job job = jobRepository.fetchJobWithId(1);
//        Job job = jobRepository.findById(1).orElse(null);

        /*
        ItemGroup itemGroup = new ItemGroup() {
            @Override
            public String getFullName() {
                return null;
            }

            @Override
            public String getFullDisplayName() {
                return null;
            }

            @Override
            public Collection getItems() {
                return null;
            }

            @Override
            public String getUrl() {
                return null;
            }

            @Override
            public String getUrlChildPrefix() {
                return null;
            }

            @CheckForNull
            @Override
            public Item getItem(String s) throws AccessDeniedException {
                return null;
            }

            @Override
            public File getRootDirFor(Item item) {
                return null;
            }

            @Override
            public void onDeleted(Item item) throws IOException {

            }

            @Override
            public String getDisplayName() {
                return null;
            }

            @Override
            public File getRootDir() {
                return null;
            }

            @Override
            public void save() throws IOException {

            }
        };
        FreeStyleProject freeStyleProject = new FreeStyleProject(itemGroup, "MyName");
        try {
            FreeStyleBuild freeStyleBuild = new FreeStyleBuild(freeStyleProject);
            SeverityTrendChart severityTrendChart = new SeverityTrendChart();
            StaticAnalysisLabelProvider labelProvider = new StaticAnalysisLabelProvider("Checkstyle");

            History history = new AnalysisHistory(freeStyleBuild, new ByIdResultSelector(labelProvider.getId()));
            severityTrendChart.create( history, new ChartModelConfiguration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        Set<Build> builds = buildRepository.findByJob(job);
        SeverityTrendChart2 severityTrendChart2 = new SeverityTrendChart2();
        LinesChartModel model = severityTrendChart2.create(job, new ChartModelConfiguration());
        String json = new Gson().toJson(model);
        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();
        String hudsonString = "{\n" +
                "  \"_class\" : \"hudson.model.Hudson\",\n" +
                "  \"assignedLabels\" : [\n" +
                "    {\n" +
                "      \"name\" : \"master\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"mode\" : \"NORMAL\",\n" +
                "  \"nodeDescription\" : \"the master Jenkins node\",\n" +
                "  \"nodeName\" : \"\",\n" +
                "  \"numExecutors\" : 2,\n" +
                "  \"description\" : null,\n" +
                "  \"jobs\" : [\n" +
                "    {\n" +
                "      \"_class\" : \"hudson.model.FreeStyleProject\",\n" +
                "      \"name\" : \"dashboard\",\n" +
                "      \"url\" : \"http://localhost:8080/jenkins/job/dashboard/\",\n" +
                "      \"color\" : \"red\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"_class\" : \"hudson.model.FreeStyleProject\",\n" +
                "      \"name\" : \"plagi\",\n" +
                "      \"url\" : \"http://localhost:8080/jenkins/job/plagi/\",\n" +
                "      \"color\" : \"blue\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"overallLoad\" : {\n" +
                "    \n" +
                "  },\n" +
                "  \"primaryView\" : {\n" +
                "    \"_class\" : \"hudson.model.AllView\",\n" +
                "    \"name\" : \"all\",\n" +
                "    \"url\" : \"http://localhost:8080/jenkins/\"\n" +
                "  },\n" +
                "  \"quietingDown\" : false,\n" +
                "  \"slaveAgentPort\" : 0,\n" +
                "  \"unlabeledLoad\" : {\n" +
                "    \"_class\" : \"jenkins.model.UnlabeledLoadStatistics\"\n" +
                "  },\n" +
                "  \"useCrumbs\" : false,\n" +
                "  \"useSecurity\" : false,\n" +
                "  \"views\" : [\n" +
                "    {\n" +
                "      \"_class\" : \"hudson.model.AllView\",\n" +
                "      \"name\" : \"all\",\n" +
                "      \"url\" : \"http://localhost:8080/jenkins/\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            Hudson h = mapper.readValue(hudsonString, edu.hm.hafner.warningsngui.model.test.Hudson.class);
            System.out.println(h);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Gson gsonJenkins = new GsonBuilder()
                /*.registerTypeAdapter(Action.class,  InterfaceSerializer.interfaceSerializer(CauseAction.class))
                */
        /*.create();
        Hudson run = gsonJenkins.fromJson("{\n" +
                "  \"_class\" : \"hudson.model.Hudson\",\n" +
                "  \"assignedLabels\" : [\n" +
                "    {\n" +
                "      \"name\" : \"master\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"mode\" : \"NORMAL\",\n" +
                "  \"nodeDescription\" : \"the master Jenkins node\",\n" +
                "  \"nodeName\" : \"\",\n" +
                "  \"numExecutors\" : 2,\n" +
                "  \"description\" : null,\n" +
                "  \"jobs\" : [\n" +
                "    {\n" +
                "      \"_class\" : \"hudson.model.FreeStyleProject\",\n" +
                "      \"name\" : \"dashboard\",\n" +
                "      \"url\" : \"http://localhost:8080/jenkins/job/dashboard/\",\n" +
                "      \"color\" : \"red\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"_class\" : \"hudson.model.FreeStyleProject\",\n" +
                "      \"name\" : \"plagi\",\n" +
                "      \"url\" : \"http://localhost:8080/jenkins/job/plagi/\",\n" +
                "      \"color\" : \"blue\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"overallLoad\" : {\n" +
                "    \n" +
                "  },\n" +
                "  \"primaryView\" : {\n" +
                "    \"_class\" : \"hudson.model.AllView\",\n" +
                "    \"name\" : \"all\",\n" +
                "    \"url\" : \"http://localhost:8080/jenkins/\"\n" +
                "  },\n" +
                "  \"quietingDown\" : false,\n" +
                "  \"slaveAgentPort\" : 0,\n" +
                "  \"unlabeledLoad\" : {\n" +
                "    \"_class\" : \"jenkins.model.UnlabeledLoadStatistics\"\n" +
                "  },\n" +
                "  \"useCrumbs\" : false,\n" +
                "  \"useSecurity\" : false,\n" +
                "  \"views\" : [\n" +
                "    {\n" +
                "      \"_class\" : \"hudson.model.AllView\",\n" +
                "      \"name\" : \"all\",\n" +
                "      \"url\" : \"http://localhost:8080/jenkins/\"\n" +
                "    }\n" +
                "  ]\n" +
                "}", Hudson.class);*/


        //SeverityTrendChart severityChart = new SeverityTrendChart();
        /*List<Iterable<? extends AnalysisBuildResult>> histories = jobs.stream()
                .map(job -> job.getActions(JobAction.class))
                .flatMap(Collection::stream)
                .filter(createToolFilter(selectTools, tools))
                .map(JobAction::createBuildHistory).collect(Collectors.toList());

        return new JacksonFacade().toJson(
                severityChart.create(new CompositeResult(histories), new ChartModelConfiguration(AxisType.DATE)));
         */

        //LinesChartModel linesChartModel = severityChart.create(new CompositeResult(null), new ChartModelConfiguration(ChartModelConfiguration.AxisType.DATE));
        //return createResponseFrom(linesChartModel);
        return createResponseFrom(json);
    }

//    @RequestMapping(path = "/ajax/pmd", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    ResponseEntity<String> getPMD(/*@RequestParam("origin") final String origin,
//                                         @RequestParam("reference") final String reference*/) {
//
//        Job job = jobRepository.findById(1).orElse(null);
//
//
//        SeverityTrendChart2 severityTrendChart2 = new SeverityTrendChart2();
//        LinesChartModel model = severityTrendChart2.create2(job, new ChartModelConfiguration());
//        String json = new Gson().toJson(model);
//        System.out.println(json);
//
//        return createResponseFrom(json);
//    }


    private ResponseEntity<String> createResponseFrom(final Object model) {
        return ResponseEntity.ok(new Gson().toJson(model));
        /*String testResponse = "{\n" +
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
         */
    }
}
