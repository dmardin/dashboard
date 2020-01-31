package edu.hm.hafner.warningsngui.ui.controller;

import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class JobController {

    @Autowired
    JobService jobService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET)
    public String getJobs(final Model model) {
        logger.info("getJobs is called");
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);

        return "home";
    }
}
