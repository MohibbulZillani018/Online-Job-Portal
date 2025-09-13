package com.portal.job_portal.service;

import com.portal.job_portal.entity.Job;
import com.portal.job_portal.entity.JobStatus;
import com.portal.job_portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }
    
    public Job updateJob(Long id, Job jobDetails) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setRequirements(jobDetails.getRequirements());
        job.setLocation(jobDetails.getLocation());
        job.setJobType(jobDetails.getJobType());
        job.setExperienceLevel(jobDetails.getExperienceLevel());
        job.setCategory(jobDetails.getCategory());
        job.setMinSalary(jobDetails.getMinSalary());
        job.setMaxSalary(jobDetails.getMaxSalary());
        job.setCurrency(jobDetails.getCurrency());
        job.setBenefits(jobDetails.getBenefits());
        job.setSkills(jobDetails.getSkills());
        job.setEducation(jobDetails.getEducation());
        job.setApplicationDeadline(jobDetails.getApplicationDeadline());
        job.setStatus(jobDetails.getStatus());
        
        return jobRepository.save(job);
    }
    
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }
    
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    public List<Job> getActiveJobs() {
        return jobRepository.findByStatus(JobStatus.ACTIVE);
    }
    
    public List<Job> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompany_Id(companyId);
    }
    
    public List<Job> getJobsByUser(Long userId) {
        return jobRepository.findByPostedBy_Id(userId);
    }
    
    public List<Job> searchJobs(String title, String location, String jobType, 
                               String category, BigDecimal minSalary, BigDecimal maxSalary) {
        return jobRepository.findJobsWithFilters(JobStatus.ACTIVE, title, location, 
                                               jobType, category, minSalary, maxSalary);
    }
    
    public List<String> getDistinctLocations() {
        return jobRepository.findDistinctLocations(JobStatus.ACTIVE);
    }
    
    public List<String> getDistinctCategories() {
        return jobRepository.findDistinctCategories(JobStatus.ACTIVE);
    }
    
    public List<String> getDistinctJobTypes() {
        return jobRepository.findDistinctJobTypes(JobStatus.ACTIVE);
    }
    
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
    
    public Job updateJobStatus(Long id, JobStatus status) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setStatus(status);
        return jobRepository.save(job);
    }
}
