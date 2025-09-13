package com.portal.job_portal.repository;

import com.portal.job_portal.entity.Job;
import com.portal.job_portal.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStatus(JobStatus status);
    List<Job> findByCompany_Id(Long companyId);
    List<Job> findByPostedBy_Id(Long userId);
    List<Job> findByLocation(String location);
    List<Job> findByJobType(String jobType);
    List<Job> findByCategory(String category);
    List<Job> findByExperienceLevel(String experienceLevel);
    
    @Query("SELECT j FROM Job j WHERE j.status = :status AND " +
           "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:jobType IS NULL OR j.jobType = :jobType) AND " +
           "(:category IS NULL OR j.category = :category) AND " +
           "(:minSalary IS NULL OR j.maxSalary >= :minSalary) AND " +
           "(:maxSalary IS NULL OR j.minSalary <= :maxSalary)")
    List<Job> findJobsWithFilters(@Param("status") JobStatus status,
                                  @Param("title") String title,
                                  @Param("location") String location,
                                  @Param("jobType") String jobType,
                                  @Param("category") String category,
                                  @Param("minSalary") BigDecimal minSalary,
                                  @Param("maxSalary") BigDecimal maxSalary);
    
    @Query("SELECT DISTINCT j.location FROM Job j WHERE j.status = :status AND j.location IS NOT NULL")
    List<String> findDistinctLocations(@Param("status") JobStatus status);
    
    @Query("SELECT DISTINCT j.category FROM Job j WHERE j.status = :status AND j.category IS NOT NULL")
    List<String> findDistinctCategories(@Param("status") JobStatus status);
    
    @Query("SELECT DISTINCT j.jobType FROM Job j WHERE j.status = :status AND j.jobType IS NOT NULL")
    List<String> findDistinctJobTypes(@Param("status") JobStatus status);
}
