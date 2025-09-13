package com.portal.job_portal.service;

import com.portal.job_portal.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DataInitializationService implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        try {
            // Check if data already exists
            if (userService.getAllUsers().size() > 0) {
                return; // Data already exists
            }
            
            // Create sample users
            createUser("admin", "admin@jobportal.com", "Admin", "User", "admin123", UserRole.ADMIN);
            User employerUser = createUser("employer", "employer@company.com", "John", "Employer", "employer123", UserRole.EMPLOYER);
            createUser("jobseeker", "jobseeker@email.com", "Jane", "JobSeeker", "jobseeker123", UserRole.JOB_SEEKER);
            
            // Create sample companies
            Company techCorp = createCompany("TechCorp Solutions", "Leading technology company", "tech@techcorp.com", "https://techcorp.com", employerUser);
            Company innovaSoft = createCompany("InnovaSoft Inc", "Innovative software development", "info@innovasoft.com", "https://innovasoft.com", employerUser);
            
            // Create sample jobs
            createJob("Senior Java Developer", "We are looking for an experienced Java developer...", 
                    "5+ years Java experience, Spring Boot, REST APIs", "New York, NY", 
                    "FULL_TIME", "SENIOR", "IT", new BigDecimal("80000"), new BigDecimal("120000"), techCorp, employerUser);
            
            createJob("Frontend React Developer", "Join our frontend team to build amazing user interfaces...", 
                    "3+ years React experience, JavaScript, CSS", "San Francisco, CA", 
                    "FULL_TIME", "MID", "IT", new BigDecimal("70000"), new BigDecimal("100000"), techCorp, employerUser);
            
            createJob("DevOps Engineer", "Manage our cloud infrastructure and deployment pipelines...", 
                    "AWS, Docker, Kubernetes experience required", "Remote", 
                    "FULL_TIME", "MID", "IT", new BigDecimal("90000"), new BigDecimal("130000"), innovaSoft, employerUser);
            
            createJob("Product Manager", "Lead product development and strategy...", 
                    "MBA preferred, 3+ years product management", "Chicago, IL", 
                    "FULL_TIME", "SENIOR", "MANAGEMENT", new BigDecimal("100000"), new BigDecimal("150000"), techCorp, employerUser);
            
            createJob("Data Scientist", "Analyze data and build machine learning models...", 
                    "Python, R, SQL, Machine Learning experience", "Boston, MA", 
                    "FULL_TIME", "MID", "DATA", new BigDecimal("85000"), new BigDecimal("125000"), innovaSoft, employerUser);
            
            System.out.println("Sample data initialized successfully!");
            
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }
    
    private User createUser(String username, String email, String firstName, String lastName, String password, UserRole role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setLocation("New York, NY");
        user.setBio("Experienced professional in the field");
        return userService.createUser(user);
    }
    
    private Company createCompany(String name, String description, String email, String website, User user) {
        Company company = new Company();
        company.setName(name);
        company.setDescription(description);
        company.setEmail(email);
        company.setWebsite(website);
        company.setLocation("New York, NY");
        company.setIndustry("Technology");
        company.setCompanySize("50-200 employees");
        company.setUser(user);
        return companyService.createCompany(company);
    }
    
    private void createJob(String title, String description, String requirements, String location, 
                          String jobType, String experienceLevel, String category, 
                          BigDecimal minSalary, BigDecimal maxSalary, Company company, User postedBy) {
        Job job = new Job();
        job.setTitle(title);
        job.setDescription(description);
        job.setRequirements(requirements);
        job.setLocation(location);
        job.setJobType(jobType);
        job.setExperienceLevel(experienceLevel);
        job.setCategory(category);
        job.setMinSalary(minSalary);
        job.setMaxSalary(maxSalary);
        job.setCurrency("USD");
        job.setBenefits("Health insurance, 401k, flexible hours");
        job.setSkills("Java, Spring Boot, REST APIs");
        job.setEducation("Bachelor's degree in Computer Science");
        job.setStatus(JobStatus.ACTIVE);
        job.setCompany(company);
        job.setPostedBy(postedBy);
        job.setApplicationDeadline(LocalDateTime.now().plusDays(30));
        
        jobService.createJob(job);
    }
}
