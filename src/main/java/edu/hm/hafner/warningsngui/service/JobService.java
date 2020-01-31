package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.warningsngui.db.JobEntityService;
import edu.hm.hafner.warningsngui.service.dto.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    JobEntityService jobEntityService;

    public List<Job> getAllJobs(){
        return jobEntityService.findAll();
    }

    public Job findJobByName(String name){
        return jobEntityService.findJobByName(name);
    }

    public List<Job> saveAll(List<Job> jobs) {
        return jobEntityService.saveAll(jobs);
    }
}
