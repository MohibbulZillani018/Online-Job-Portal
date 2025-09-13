package com.portal.job_portal.controller;

import com.portal.job_portal.entity.Job;
import com.portal.job_portal.entity.JobStatus;
import com.portal.job_portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Job>> getActiveJobs() {
        List<Job> jobs = jobService.getActiveJobs();
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        try {
            Job job = jobService.getJobById(id);
            return ResponseEntity.ok(job);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createJob(@Valid @RequestBody Job job) {
        try {
            Job createdJob = jobService.createJob(job);
            return ResponseEntity.ok(createdJob);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @Valid @RequestBody Job jobDetails) {
        try {
            Job updatedJob = jobService.updateJob(id, jobDetails);
            return ResponseEntity.ok(updatedJob);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJob(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Job deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable Long companyId) {
        List<Job> jobs = jobService.getJobsByCompany(companyId);
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Job>> getJobsByUser(@PathVariable Long userId) {
        List<Job> jobs = jobService.getJobsByUser(userId);
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary) {
        
        List<Job> jobs = jobService.searchJobs(title, location, jobType, category, minSalary, maxSalary);
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/filters/locations")
    public ResponseEntity<List<String>> getDistinctLocations() {
        List<String> locations = jobService.getDistinctLocations();
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/filters/categories")
    public ResponseEntity<List<String>> getDistinctCategories() {
        List<String> categories = jobService.getDistinctCategories();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/filters/job-types")
    public ResponseEntity<List<String>> getDistinctJobTypes() {
        List<String> jobTypes = jobService.getDistinctJobTypes();
        return ResponseEntity.ok(jobTypes);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateJobStatus(@PathVariable Long id, @RequestParam JobStatus status) {
        try {
            Job updatedJob = jobService.updateJobStatus(id, status);
            return ResponseEntity.ok(updatedJob);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
