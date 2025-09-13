package com.portal.job_portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Job title is required")
    private String title;
    
    @NotBlank(message = "Job description is required")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotBlank(message = "Job requirements are required")
    @Column(columnDefinition = "TEXT")
    private String requirements;
    
    private String location;
    private String jobType; // FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP
    private String experienceLevel; // ENTRY, MID, SENIOR, EXECUTIVE
    private String category; // IT, FINANCE, HEALTHCARE, etc.
    
    @NotNull(message = "Salary range is required")
    private BigDecimal minSalary;
    
    @NotNull(message = "Salary range is required")
    private BigDecimal maxSalary;
    
    private String currency = "USD";
    private String benefits;
    private String skills;
    private String education;
    
    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.ACTIVE;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by")
    private User postedBy;
    
    @Column(name = "application_deadline")
    private LocalDateTime applicationDeadline;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
