package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.warningsngui.dto.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    JobEntityService jobEntityService;

    public List<Job> createDistributionOfAllJobs(){
        return jobEntityService.findAll();
    }

    public List<Job> saveAll(List<Job> jobs) {
        return jobEntityService.saveAll(jobs);
    }
}
