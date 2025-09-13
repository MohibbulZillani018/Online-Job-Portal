package com.portal.job_portal.repository;

import com.portal.job_portal.entity.ApplicationStatus;
import com.portal.job_portal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUser_Id(Long userId);
    List<JobApplication> findByJob_Id(Long jobId);
    List<JobApplication> findByJob_Company_Id(Long companyId);
    List<JobApplication> findByStatus(ApplicationStatus status);
    Optional<JobApplication> findByJob_IdAndUser_Id(Long jobId, Long userId);
    long countByJob_Id(Long jobId);
    long countByUser_Id(Long userId);
    long countByJob_Company_Id(Long companyId);
}
