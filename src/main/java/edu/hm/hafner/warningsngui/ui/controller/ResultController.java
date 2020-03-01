package edu.hm.hafner.warningsngui.ui.controller;

import edu.hm.hafner.warningsngui.service.BuildService;
import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.service.ResultService;
import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.dto.Result;
import edu.hm.hafner.warningsngui.ui.echart.BarChartModel;
import edu.hm.hafner.warningsngui.ui.echart.ResultChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Provides the Controller for a specific result of a build.
 *
 * @author Deniz Mardin
 */
@Controller
public class ResultController {

    @Autowired
    JobService jobService;
    @Autowired
    BuildService buildService;
    @Autowired
    ResultService resultService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Displays the specific results of a build.
     *
     * @param jobName the name of the job
     * @param buildNumber the build number
     * @param model the model with the needed build information
     * @return the result page
     */
    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}"}, method= RequestMethod.GET)
    public String getResults(@PathVariable("jobName") String jobName, @PathVariable("buildNumber") String buildNumber, final Model model) {
        logger.info("getResults was called");
        List<Job> jobs = jobService.getAllJobs();
        Job neededJob = jobs.stream().filter(job -> job.getName().equals(jobName)).findFirst().get();
        Build build = neededJob.getBuilds().stream().filter(b -> b.getNumber() == Integer.parseInt(buildNumber)).findFirst().get();
        model.addAttribute("build", build);
        model.addAttribute("buildNumber",buildNumber);

        return "result";
    }

    /**
     * Ajax call to display a summarize of a result of a build.
     *
     * @param jobName the name of the job
     * @param buildNumber the build number
     * @param toolName the name of the used tool (e.g. checkstyle)
     * @return the {@link BarChartModel} with the summarize of a result
     */
    @RequestMapping(path={"/ajax/job/{jobName}/build/{buildNumber}/{toolName}/result"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    BarChartModel getResultSummarize(@PathVariable("jobName") String jobName,
                                     @PathVariable("buildNumber") Integer buildNumber,
                                     @PathVariable("toolName") String toolName) {
        logger.info("getResultSummarize was called");
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);
        Result result = resultService.getResultByToolId(build, toolName);
        ResultChart resultChart = new ResultChart();

        return resultChart.create(result);
    }
}
