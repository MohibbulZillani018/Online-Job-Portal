// Global variables
let currentUser = null;
let allJobs = [];
let filteredJobs = [];
let currentJobId = null;

// API Base URL
const API_BASE_URL = '/api';

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    loadJobs();
    loadCategories();
    loadJobTypes();
    loadStats();
    setupEventListeners();
    checkLoginStatus();
});

// Check if user is already logged in (from localStorage)
function checkLoginStatus() {
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
        try {
            currentUser = JSON.parse(savedUser);
            updateUIForLoggedInUser();
        } catch (error) {
            console.error('Error parsing saved user data:', error);
            localStorage.removeItem('currentUser');
        }
    }
}

// Setup event listeners
function setupEventListeners() {
    // Login form
    document.getElementById('loginForm').addEventListener('submit', handleLogin);
    
    // Register form
    document.getElementById('registerForm').addEventListener('submit', handleRegister);
    
    // Post job form
    document.getElementById('postJobForm').addEventListener('submit', handlePostJob);
    
    // Apply job form
    document.getElementById('applyJobForm').addEventListener('submit', handleApplyJob);
    
    // Search functionality
    document.getElementById('jobSearch').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            searchJobs();
        }
    });
    
    document.getElementById('locationSearch').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            searchJobs();
        }
    });
}

// Load all jobs
async function loadJobs() {
    try {
        showLoading('jobsList');
        console.log('Loading jobs from:', `${API_BASE_URL}/jobs/active`);
        const response = await fetch(`${API_BASE_URL}/jobs/active`);
        console.log('Response status:', response.status);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const jobs = await response.json();
        console.log('Loaded jobs:', jobs);
        allJobs = jobs;
        filteredJobs = jobs;
        displayJobs(jobs);
    } catch (error) {
        console.error('Error loading jobs:', error);
        showError('jobsList', 'Failed to load jobs. Please try again.');
    }
}

// Display jobs in the UI
function displayJobs(jobs) {
    const jobsList = document.getElementById('jobsList');
    
    if (jobs.length === 0) {
        jobsList.innerHTML = `
            <div class="text-center py-5">
                <i class="fas fa-search fa-3x text-muted mb-3"></i>
                <h5 class="text-muted">No jobs found</h5>
                <p class="text-muted">Try adjusting your search criteria or check back later.</p>
            </div>
        `;
        return;
    }
    
    const jobsHTML = jobs.map(job => `
        <div class="job-card">
            <div class="d-flex justify-content-between align-items-start mb-3">
                <div>
                    <h5 class="job-title">${job.title}</h5>
                    <p class="company-name">${job.company ? job.company.name : 'Company Name'}</p>
                    <p class="job-location">
                        <i class="fas fa-map-marker-alt me-1"></i>${job.location || 'Not specified'}
                    </p>
                </div>
                <div class="text-end">
                    <span class="job-type">${job.jobType || 'Full Time'}</span>
                    <div class="salary mt-2">
                        $${formatNumber(job.minSalary)} - $${formatNumber(job.maxSalary)}
                    </div>
                </div>
            </div>
            
            <div class="mb-3">
                <p class="text-muted">${truncateText(job.description, 150)}</p>
            </div>
            
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <small class="text-muted">
                        <i class="fas fa-clock me-1"></i>
                        Posted ${formatDate(job.createdAt)}
                    </small>
                </div>
                <div>
                    <button class="btn btn-outline-primary btn-sm me-2" onclick="viewJobDetails(${job.id})">
                        View Details
                    </button>
                    <button class="btn btn-primary btn-sm" onclick="applyForJob(${job.id})">
                        Apply Now
                    </button>
                </div>
            </div>
        </div>
    `).join('');
    
    jobsList.innerHTML = jobsHTML;
}

// Search jobs
async function searchJobs() {
    const title = document.getElementById('jobSearch').value;
    const location = document.getElementById('locationSearch').value;
    const category = document.getElementById('categorySearch').value;
    
    try {
        showLoading('jobsList');
        
        const params = new URLSearchParams();
        if (title) params.append('title', title);
        if (location) params.append('location', location);
        if (category) params.append('category', category);
        
        const response = await fetch(`${API_BASE_URL}/jobs/search?${params}`);
        const jobs = await response.json();
        
        filteredJobs = jobs;
        displayJobs(jobs);
    } catch (error) {
        console.error('Error searching jobs:', error);
        showError('jobsList', 'Failed to search jobs. Please try again.');
    }
}

// Apply filters
function applyFilters() {
    const jobType = document.getElementById('filterJobType').value;
    const experience = document.getElementById('filterExperience').value;
    const salary = document.getElementById('filterSalary').value;
    
    let filtered = [...allJobs];
    
    if (jobType) {
        filtered = filtered.filter(job => job.jobType === jobType);
    }
    
    if (experience) {
        filtered = filtered.filter(job => job.experienceLevel === experience);
    }
    
    if (salary) {
        const [min, max] = salary.split('-');
        filtered = filtered.filter(job => {
            if (max === '+') {
                return job.minSalary >= parseInt(min);
            }
            return job.minSalary >= parseInt(min) && job.maxSalary <= parseInt(max);
        });
    }
    
    filteredJobs = filtered;
    displayJobs(filtered);
}

// Load categories for filter dropdown
async function loadCategories() {
    try {
        const response = await fetch(`${API_BASE_URL}/jobs/filters/categories`);
        const categories = await response.json();
        
        const categorySelect = document.getElementById('categorySearch');
        const filterCategorySelect = document.getElementById('filterJobType');
        
        categories.forEach(category => {
            const option1 = new Option(category, category);
            const option2 = new Option(category, category);
            categorySelect.add(option1);
            filterCategorySelect.add(option2);
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

// Load job types for filter dropdown
async function loadJobTypes() {
    try {
        const response = await fetch(`${API_BASE_URL}/jobs/filters/job-types`);
        const jobTypes = await response.json();
        
        const jobTypeSelect = document.getElementById('filterJobType');
        
        jobTypes.forEach(jobType => {
            const option = new Option(jobType, jobType);
            jobTypeSelect.add(option);
        });
    } catch (error) {
        console.error('Error loading job types:', error);
    }
}

// Load statistics
async function loadStats() {
    try {
        const [jobsResponse, companiesResponse] = await Promise.all([
            fetch(`${API_BASE_URL}/jobs/active`),
            fetch(`${API_BASE_URL}/companies`)
        ]);
        
        const jobs = await jobsResponse.json();
        const companies = await companiesResponse.json();
        
        document.getElementById('totalJobs').textContent = jobs.length;
        document.getElementById('totalCompanies').textContent = companies.length;
        document.getElementById('totalApplications').textContent = '0'; // This would need a separate endpoint
        document.getElementById('successRate').textContent = '85%'; // This would be calculated
    } catch (error) {
        console.error('Error loading stats:', error);
    }
}

// View job details
async function viewJobDetails(jobId) {
    try {
        const response = await fetch(`${API_BASE_URL}/jobs/${jobId}`);
        const job = await response.json();
        
        document.getElementById('jobDetailsTitle').textContent = job.title;
        document.getElementById('jobDetailsBody').innerHTML = `
            <div class="row">
                <div class="col-md-8">
                    <h6>Job Description</h6>
                    <p>${job.description}</p>
                    
                    <h6 class="mt-4">Requirements</h6>
                    <p>${job.requirements}</p>
                    
                    <h6 class="mt-4">Skills Required</h6>
                    <p>${job.skills || 'Not specified'}</p>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h6>Job Details</h6>
                            <p><strong>Company:</strong> ${job.company ? job.company.name : 'Not specified'}</p>
                            <p><strong>Location:</strong> ${job.location || 'Not specified'}</p>
                            <p><strong>Type:</strong> ${job.jobType || 'Not specified'}</p>
                            <p><strong>Experience:</strong> ${job.experienceLevel || 'Not specified'}</p>
                            <p><strong>Category:</strong> ${job.category || 'Not specified'}</p>
                            <p><strong>Salary:</strong> $${formatNumber(job.minSalary)} - $${formatNumber(job.maxSalary)}</p>
                            <p><strong>Posted:</strong> ${formatDate(job.createdAt)}</p>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        currentJobId = jobId;
        document.getElementById('applyJobBtn').style.display = 'block';
        
        new bootstrap.Modal(document.getElementById('jobDetailsModal')).show();
    } catch (error) {
        console.error('Error loading job details:', error);
        alert('Failed to load job details. Please try again.');
    }
}

// Apply for job
function applyForJob(jobId) {
    currentJobId = jobId;
    document.getElementById('applyJobId').value = jobId;
    new bootstrap.Modal(document.getElementById('applyJobModal')).show();
}

// Handle login
async function handleLogin(e) {
    e.preventDefault();
    
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    
    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });
        
        const result = await response.json();
        
        if (response.ok) {
            currentUser = result.user;
            // Save user data to localStorage
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            showSuccess('Login successful!');
            // Clear the login form
            document.getElementById('loginForm').reset();
            bootstrap.Modal.getInstance(document.getElementById('loginModal')).hide();
            updateUIForLoggedInUser();
        } else {
            showError('loginForm', result.error || 'Login failed');
        }
    } catch (error) {
        console.error('Login error:', error);
        showError('loginForm', 'Login failed. Please try again.');
    }
}

// Handle register
async function handleRegister(e) {
    e.preventDefault();
    
    const userData = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        role: document.getElementById('role').value
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
        
        const result = await response.json();
        
        if (response.ok) {
            currentUser = result.user;
            // Save user data to localStorage
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            showSuccess('Registration successful!');
            // Clear the register form
            document.getElementById('registerForm').reset();
            bootstrap.Modal.getInstance(document.getElementById('registerModal')).hide();
            updateUIForLoggedInUser();
        } else {
            showError('registerForm', result.error || 'Registration failed');
        }
    } catch (error) {
        console.error('Registration error:', error);
        showError('registerForm', 'Registration failed. Please try again.');
    }
}

// Handle post job
async function handlePostJob(e) {
    e.preventDefault();
    
    if (!currentUser) {
        showError('postJobForm', 'Please login to post a job');
        return;
    }
    
    const jobData = {
        title: document.getElementById('jobTitle').value,
        description: document.getElementById('jobDescription').value,
        requirements: document.getElementById('jobRequirements').value,
        location: document.getElementById('jobLocation').value,
        jobType: document.getElementById('jobType').value,
        category: document.getElementById('jobCategory').value,
        minSalary: parseFloat(document.getElementById('minSalary').value),
        maxSalary: parseFloat(document.getElementById('maxSalary').value),
        postedBy: { id: currentUser.id }
    };
    
    // Try to get user's company, but don't require it
    try {
        const companyResponse = await fetch(`${API_BASE_URL}/companies/user/${currentUser.id}`);
        const companies = await companyResponse.json();
        if (companies && companies.length > 0) {
            jobData.company = { id: companies[0].id };
        }
    } catch (error) {
        console.log('No company found for user, proceeding without company');
    }
    
    try {
        console.log('Posting job data:', jobData);
        const response = await fetch(`${API_BASE_URL}/jobs`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jobData)
        });
        
        console.log('Job post response status:', response.status);
        const result = await response.json();
        console.log('Job post response:', result);
        
        if (response.ok) {
            showSuccess('Job posted successfully!');
            bootstrap.Modal.getInstance(document.getElementById('postJobModal')).hide();
            document.getElementById('postJobForm').reset();
            loadJobs();
        } else {
            showError('postJobForm', result.error || 'Failed to post job');
        }
    } catch (error) {
        console.error('Post job error:', error);
        showError('postJobForm', 'Failed to post job. Please try again.');
    }
}

// Ensure user has a company, create one if not
async function ensureUserHasCompany() {
    try {
        // First, check if user already has a company
        const response = await fetch(`${API_BASE_URL}/companies/user/${currentUser.id}`);
        const companies = await response.json();
        
        if (companies && companies.length > 0) {
            return companies[0]; // Return the first company
        }
        
        // If no company exists, create a default one
        const companyData = {
            name: `${currentUser.firstName} ${currentUser.lastName}'s Company`,
            description: 'Company created automatically for job posting',
            email: currentUser.email,
            user: { id: currentUser.id }
        };
        
        const createResponse = await fetch(`${API_BASE_URL}/companies`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(companyData)
        });
        
        if (createResponse.ok) {
            const newCompany = await createResponse.json();
            return newCompany;
        } else {
            console.error('Failed to create company');
            return null;
        }
    } catch (error) {
        console.error('Error ensuring company:', error);
        return null;
    }
}

// Handle apply for job
async function handleApplyJob(e) {
    e.preventDefault();
    
    if (!currentUser) {
        showError('applyJobForm', 'Please login to apply for jobs');
        return;
    }
    
    const applicationData = {
        job: { id: parseInt(document.getElementById('applyJobId').value) },
        user: { id: currentUser.id },
        coverLetter: document.getElementById('coverLetter').value,
        resumeUrl: document.getElementById('resumeUrl').value
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/applications`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(applicationData)
        });
        
        const result = await response.json();
        
        if (response.ok) {
            showSuccess('Application submitted successfully!');
            bootstrap.Modal.getInstance(document.getElementById('applyJobModal')).hide();
            document.getElementById('applyJobForm').reset();
        } else {
            showError('applyJobForm', result.error || 'Failed to submit application');
        }
    } catch (error) {
        console.error('Apply job error:', error);
        showError('applyJobForm', 'Failed to submit application. Please try again.');
    }
}

// Update UI for logged in user
function updateUIForLoggedInUser() {
    console.log('Updating UI for logged in user:', currentUser);
    
    if (!currentUser) {
        console.log('No current user found');
        return;
    }
    
    // Find the second navbar-nav (the one without me-auto class)
    const navbarNavs = document.querySelectorAll('.navbar-nav');
    console.log('Found navbar navs:', navbarNavs.length);
    
    const rightNavbar = navbarNavs[1]; // Second navbar-nav element
    
    if (rightNavbar) {
        console.log('Updating right navbar with user:', currentUser.firstName, currentUser.lastName);
        rightNavbar.innerHTML = `
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                    <i class="fas fa-user me-1"></i>${currentUser.firstName} ${currentUser.lastName}
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="#"><i class="fas fa-user me-2"></i>Profile</a></li>
                    <li><a class="dropdown-item" href="#"><i class="fas fa-briefcase me-2"></i>My Applications</a></li>
                    <li><a class="dropdown-item" href="#"><i class="fas fa-building me-2"></i>My Jobs</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" onclick="logout()"><i class="fas fa-sign-out-alt me-2"></i>Logout</a></li>
                </ul>
            </li>
        `;
    } else {
        console.log('Right navbar not found');
    }
}

// Logout
function logout() {
    currentUser = null;
    localStorage.removeItem('currentUser');
    location.reload();
}

// Utility functions
function showLoading(elementId) {
    document.getElementById(elementId).innerHTML = `
        <div class="loading">
            <div class="spinner-border" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
            <p class="mt-2">Loading...</p>
        </div>
    `;
}

function showError(elementId, message) {
    const element = document.getElementById(elementId);
    element.innerHTML = `
        <div class="alert alert-danger" role="alert">
            <i class="fas fa-exclamation-triangle me-2"></i>${message}
        </div>
    `;
}

function showSuccess(message) {
    // Create a temporary success alert
    const alertDiv = document.createElement('div');
    alertDiv.className = 'alert alert-success alert-dismissible fade show position-fixed';
    alertDiv.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    alertDiv.innerHTML = `
        <i class="fas fa-check-circle me-2"></i>${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(alertDiv);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.parentNode.removeChild(alertDiv);
        }
    }, 5000);
}

function formatNumber(num) {
    return new Intl.NumberFormat().format(num);
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

function truncateText(text, maxLength) {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
}
