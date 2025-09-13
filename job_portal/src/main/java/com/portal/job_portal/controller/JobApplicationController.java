package com.portal.job_portal.controller;

import com.portal.job_portal.entity.ApplicationStatus;
import com.portal.job_portal.entity.JobApplication;
import com.portal.job_portal.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class JobApplicationController {
    
    @Autowired
    private JobApplicationService jobApplicationService;
    
    @PostMapping
    public ResponseEntity<?> createApplication(@Valid @RequestBody JobApplication application) {
        try {
            JobApplication createdApplication = jobApplicationService.createApplication(application);
            return ResponseEntity.ok(createdApplication);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplication(@PathVariable Long id, @Valid @RequestBody JobApplication applicationDetails) {
        try {
            JobApplication updatedApplication = jobApplicationService.updateApplication(id, applicationDetails);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long id, 
                                                   @RequestParam ApplicationStatus status,
                                                   @RequestParam(required = false) String feedback) {
        try {
            JobApplication updatedApplication = jobApplicationService.updateApplicationStatus(id, status, feedback);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        try {
            JobApplication application = jobApplicationService.getApplicationById(id);
            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByUser(@PathVariable Long userId) {
        List<JobApplication> applications = jobApplicationService.getApplicationsByUser(userId);
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByJob(@PathVariable Long jobId) {
        List<JobApplication> applications = jobApplicationService.getApplicationsByJob(jobId);
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByCompany(@PathVariable Long companyId) {
        List<JobApplication> applications = jobApplicationService.getApplicationsByCompany(companyId);
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobApplication>> getApplicationsByStatus(@PathVariable ApplicationStatus status) {
        List<JobApplication> applications = jobApplicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/stats/job/{jobId}")
    public ResponseEntity<?> getApplicationStatsByJob(@PathVariable Long jobId) {
        long count = jobApplicationService.getApplicationCountByJob(jobId);
        Map<String, Long> stats = new HashMap<>();
        stats.put("applicationCount", count);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/stats/user/{userId}")
    public ResponseEntity<?> getApplicationStatsByUser(@PathVariable Long userId) {
        long count = jobApplicationService.getApplicationCountByUser(userId);
        Map<String, Long> stats = new HashMap<>();
        stats.put("applicationCount", count);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/stats/company/{companyId}")
    public ResponseEntity<?> getApplicationStatsByCompany(@PathVariable Long companyId) {
        long count = jobApplicationService.getApplicationCountByCompany(companyId);
        Map<String, Long> stats = new HashMap<>();
        stats.put("applicationCount", count);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/check/{jobId}/{userId}")
    public ResponseEntity<?> checkUserApplication(@PathVariable Long jobId, @PathVariable Long userId) {
        boolean hasApplied = jobApplicationService.hasUserAppliedForJob(jobId, userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("hasApplied", hasApplied);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long id) {
        try {
            jobApplicationService.deleteApplication(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Application deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
