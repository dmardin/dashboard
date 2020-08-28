package edu.hm.hafner.dashboard.ui;

import edu.hm.hafner.dashboard.service.UiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Provides the Controller for the home page.
 *
 * @author Deniz Mardin
 */
@Controller
public class JobController {
    private final UiService uiService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a new instance of {@link JobController}.
     *
     * @param uiService the service for interactions with the ui
     */
    @Autowired
    public JobController(UiService uiService) {
        this.uiService = uiService;
    }

    /**
     * Loads the header of the jobs for the table at the main page.
     *
     * @param model     the model with the headers of the jobs
     * @param fetchData boolean to decide if new data should be fetched
     * @return the home page
     */
    @RequestMapping(path = {"/"}, method = RequestMethod.GET)
    public String getJobHeaders(final Model model, @RequestParam(required = false) boolean fetchData) {
        logger.info("getJobHeaders is called");
        if(fetchData){
            logger.info("fetching new data..");
            uiService.fetchData();
        }
        model.addAttribute("jobViewTable", uiService.createJobViewTable());

        return "home";
    }

    /**
     * Redirects "/home" to "/".
     *
     * @return the home page
     */
    @RequestMapping(path = {"/home"}, method = RequestMethod.GET)
    public String getHome() {
        return "redirect:/";
    }

    /**
     * Ajax call for the table at the main page that prepares the rows for the table with jobs.
     *
     * @return rows of the table
     */
    @RequestMapping(path = {"/ajax"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getRowsForJobViewTable() {
        logger.info("getRowsForJobViewTable is called");
        return uiService.prepareRowsForJobViewTable();
    }
}
