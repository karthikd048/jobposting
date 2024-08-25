package com.job.app.controller;

import com.job.app.entity.JobEntity;
import com.job.app.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Create a new job
    @PostMapping("/addjob")
    public ResponseEntity<JobEntity> createJob(@RequestBody JobEntity jobEntity) {
        JobEntity savedJob = jobService.createJob(jobEntity);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<JobEntity>> getAllJobs() {
        List<JobEntity> jobs = jobService.getAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    // Get a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobEntity> getJobById(@PathVariable Long id) {
        Optional<JobEntity> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a job by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
        if (jobService.deleteJobById(id)) {
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("not found",HttpStatus.NOT_FOUND);
        }
    }

    // Update a job
    @PutMapping("/{id}")
    public ResponseEntity<JobEntity> updateJob(@PathVariable Long id, @RequestBody JobEntity jobEntity) {
        Optional<JobEntity> updatedJob = jobService.updateJob(id, jobEntity);
        return updatedJob.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    //search by keyword,loc, skills
    @GetMapping("/search")
    public ResponseEntity<List<JobEntity>> searchJobs(
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) List<String> requiredSkills) {
        List<JobEntity> jobs = jobService.searchJobs(keywords, location, requiredSkills);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}
