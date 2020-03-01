package edu.hm.hafner.warningsngui.ui.controller;

import edu.hm.hafner.warningsngui.service.JobService;
import edu.hm.hafner.warningsngui.ui.table.job.JobRepositoryStatistics;
import edu.hm.hafner.warningsngui.ui.table.job.JobViewTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Provides the Controller for the home page.
 *
 * @author Deniz Mardin
 */
@Controller
public class JobController {

    @Autowired
    JobService jobService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Loads the header of the jobs for the table at the main page.
     *
     * @param model the model with the headers of the jobs
     * @return the home page
     */
    @RequestMapping(path = {"/", "home"}, method= RequestMethod.GET)
    public String getJobHeaders(final Model model) {
        logger.info("getJobHeaders is called");
        JobViewTable jobViewTable = new JobViewTable(new JobRepositoryStatistics());
        model.addAttribute("jobViewTable", jobViewTable);

        return "home";
    }

    /**
     * Ajax call for the table at the main page that prepares the rows for the table with jobs.
     *
     * @return rows of the table
     */
    @RequestMapping(path={"/ajax"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getRowsForJobViewTable() {
        logger.info("getRowsForJobViewTable is called");
        return jobService.prepareRowsForJobViewTable();
    }
}
