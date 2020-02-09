package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.warningsngui.db.JobEntityService;
import edu.hm.hafner.warningsngui.service.dto.Job;
import edu.hm.hafner.warningsngui.ui.table.job.JobRepositoryStatistics;
import edu.hm.hafner.warningsngui.ui.table.job.JobStatistics;
import edu.hm.hafner.warningsngui.ui.table.job.JobViewTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to prepare a {@link Job} or {@link Job}s for the ui.
 */
@Service
public class JobService {

    @Autowired
    JobEntityService jobEntityService;

    /**
     * Returns all Jobs.
     *
     * @return the jobs
     */
    public List<Job> getAllJobs(){
        return jobEntityService.findAll();
    }

    /**
     * Searches for a job by its name.
     *
     * @param name the job name
     * @return the job
     */
    public Job findJobByName(String name){
        return jobEntityService.findJobByName(name);
    }

    /**
     * Fetches all jobs from database and converts it to the needed format of table rows.
     *
     * @return prepared table rows
     */
    public List<Object> prepareRowsForJobViewTable(){
        List<Job> allJobs = getAllJobs();
        return convertRowsForTheJobViewTable(allJobs);
    }

    /**
     * Saves all jobs.
     *
     * @param jobs the jobs to save
     * @return the saves jobs
     */
    public List<Job> saveAll(List<Job> jobs) {
        return jobEntityService.saveAll(jobs);
    }

    /**
     * Method to convert a list of jobs to the needed format of table rows.
     *
     * @param jobs the jobs to convert
     * @return converted table rows
     */
    private List<Object> convertRowsForTheJobViewTable(List<Job> jobs) {
        JobRepositoryStatistics jobRepositoryStatistics = new JobRepositoryStatistics();
        ArrayList<JobStatistics> jobsStatistics = new ArrayList<>();
        jobs.forEach(job -> {
             jobsStatistics.add(new JobStatistics(job.getName(),job.getLastBuildStatus(),job.getUrl()));
        });

        jobRepositoryStatistics.addAll(jobsStatistics);
        JobViewTable jobViewTable = new JobViewTable(jobRepositoryStatistics);
        return jobViewTable.getTableRows("jobs");
    }
}
