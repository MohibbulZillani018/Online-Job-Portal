package com.portal.job_portal.service;

import com.portal.job_portal.entity.ApplicationStatus;
import com.portal.job_portal.entity.JobApplication;
import com.portal.job_portal.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    public JobApplication createApplication(JobApplication application) {
        // Check if user has already applied for this job
        Optional<JobApplication> existingApplication = 
            jobApplicationRepository.findByJob_IdAndUser_Id(
                application.getJob().getId(), 
                application.getUser().getId()
            );
        
        if (existingApplication.isPresent()) {
            throw new RuntimeException("You have already applied for this job");
        }
        
        return jobApplicationRepository.save(application);
    }
    
    public JobApplication updateApplication(Long id, JobApplication applicationDetails) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setCoverLetter(applicationDetails.getCoverLetter());
        application.setResumeUrl(applicationDetails.getResumeUrl());
        application.setAdditionalDocuments(applicationDetails.getAdditionalDocuments());
        
        return jobApplicationRepository.save(application);
    }
    
    public JobApplication updateApplicationStatus(Long id, ApplicationStatus status, String feedback) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setStatus(status);
        application.setFeedback(feedback);
        
        return jobApplicationRepository.save(application);
    }
    
    public JobApplication getApplicationById(Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
    
    public List<JobApplication> getApplicationsByUser(Long userId) {
        return jobApplicationRepository.findByUser_Id(userId);
    }
    
    public List<JobApplication> getApplicationsByJob(Long jobId) {
        return jobApplicationRepository.findByJob_Id(jobId);
    }
    
    public List<JobApplication> getApplicationsByCompany(Long companyId) {
        return jobApplicationRepository.findByJob_Company_Id(companyId);
    }
    
    public List<JobApplication> getApplicationsByStatus(ApplicationStatus status) {
        return jobApplicationRepository.findByStatus(status);
    }
    
    public long getApplicationCountByJob(Long jobId) {
        return jobApplicationRepository.countByJob_Id(jobId);
    }
    
    public long getApplicationCountByUser(Long userId) {
        return jobApplicationRepository.countByUser_Id(userId);
    }
    
    public long getApplicationCountByCompany(Long companyId) {
        return jobApplicationRepository.countByJob_Company_Id(companyId);
    }
    
    public void deleteApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }
    
    public boolean hasUserAppliedForJob(Long jobId, Long userId) {
        return jobApplicationRepository.findByJob_IdAndUser_Id(jobId, userId).isPresent();
    }
}
