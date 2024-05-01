package com.example.springsecurityv6.Controllers;

import com.example.springsecurityv6.Models.Jobs;
import com.example.springsecurityv6.Repositories.JobsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class JobsController {
    private final JobsRepository jobsRepository;

    public JobsController(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    @PostMapping("/addoffer")
    public Jobs saveJob(@RequestBody Jobs jobs){
        return jobsRepository.save(jobs);
    }

    @GetMapping("/offers")
    public List<Jobs> getAllJobs() {
        return jobsRepository.findAll();
    }

    @DeleteMapping("/deleteoffer/{id}")
    public void delJob(@PathVariable Long id) {
        // Use the id to delete the job offer from the database
        jobsRepository.deleteById(id);
    }

}
