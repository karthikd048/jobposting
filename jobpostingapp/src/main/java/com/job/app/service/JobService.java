package com.job.app.service;

import com.job.app.entity.JobEntity;
import com.job.app.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Create a new job
    public JobEntity createJob(JobEntity jobEntity) {
        return jobRepository.save(jobEntity);
    }

    // Get all jobs
    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get a job by ID
    public Optional<JobEntity> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Delete a job by ID
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Update a job
    public Optional<JobEntity> updateJob(Long id, JobEntity jobEntity) {
        if (jobRepository.existsById(id)) {
            jobEntity.setId(id);
            return Optional.of(jobRepository.save(jobEntity));
        } else {
            return Optional.empty();
        }
    }
    
    public List<JobEntity> searchJobs(String keywords, String location, List<String> requiredSkills) {
        return jobRepository.searchJobs(keywords, location, requiredSkills);
    }
}
