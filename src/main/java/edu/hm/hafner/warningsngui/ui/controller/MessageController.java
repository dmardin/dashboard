package edu.hm.hafner.warningsngui.ui.controller;

import edu.hm.hafner.warningsngui.service.dto.Build;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.BuildService;
import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    JobService jobService;
    @Autowired
    BuildService buildService;
    @Autowired
    ResultService resultService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path={"/job/{jobName}/build/{buildNumber}/{toolId}/messages"}, method= RequestMethod.GET)
    public String getInfoAndErrorMessages(
            @PathVariable("jobName") String jobName,
            @PathVariable("buildNumber") Integer buildNumber,
            @PathVariable("toolId") String toolId,
            final Model model) {
        logger.info("getInfoAndErrorMessages is called");
        Job job = jobService.findJobByName(jobName);
        Build build = buildService.getBuildWithBuildNumberFromJob(job, buildNumber);
        List<String> infoMessages = resultService.getInfoMessagesFromResultWithToolId(build, toolId);
        List<String> errorMessages = resultService.getErrorMessagesFromResultWithToolId(build, toolId);
        model.addAttribute("infoMessages", infoMessages);
        model.addAttribute("errorMessages", errorMessages);
        model.addAttribute("toolId", toolId);
        model.addAttribute("toolIdWithMessage", toolId + " / messages");

        return "message";
    }
}
