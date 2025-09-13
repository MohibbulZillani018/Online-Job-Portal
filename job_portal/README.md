# JobPortal - Online Job Portal System

A comprehensive online job portal built with Spring Boot backend and modern HTML/CSS/JavaScript frontend. This application allows job seekers to find and apply for jobs, while employers can post job listings and manage applications.

## 🚀 Features

### For Job Seekers
- **User Registration & Authentication** - Create account as job seeker
- **Job Search & Filtering** - Search jobs by title, location, category, salary range
- **Job Application System** - Apply for jobs with cover letter and resume
- **Profile Management** - Update personal information, skills, and experience
- **Application Tracking** - View application status and history

### For Employers
- **Company Registration** - Register as employer with company details
- **Job Posting** - Create and manage job listings
- **Application Management** - Review and manage job applications
- **Company Profile** - Maintain company information and branding

### General Features
- **Responsive Design** - Works on desktop, tablet, and mobile devices
- **Modern UI/UX** - Clean, professional interface with Bootstrap 5
- **Real-time Search** - Instant job search with multiple filters
- **Statistics Dashboard** - View job market statistics
- **Admin Panel** - Manage users, jobs, and applications

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.5.5** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **MySQL** - Database
- **Maven** - Dependency management
- **Lombok** - Code generation

### Frontend
- **HTML5** - Structure
- **CSS3** - Styling with custom properties
- **JavaScript (ES6+)** - Interactivity
- **Bootstrap 5** - UI framework
- **Font Awesome** - Icons
- **Google Fonts** - Typography

## 📋 Prerequisites

Before running the application, make sure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd job_portal
```

### 2. Database Setup
1. Install MySQL and create a database:
```sql
CREATE DATABASE job_portal;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/job_portal-0.0.1-SNAPSHOT.jar
```

### 4. Access the Application
Open your browser and navigate to:
```
http://localhost:8080
```

## 📁 Project Structure

```
job_portal/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/portal/job_portal/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST controllers
│   │   │       ├── entity/          # JPA entities
│   │   │       ├── repository/      # Data repositories
│   │   │       ├── service/         # Business logic
│   │   │       └── JobPortalApplication.java
│   │   └── resources/
│   │       ├── static/              # Static web resources
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── index.html
│   │       └── application.properties
│   └── test/                        # Test classes
├── pom.xml                          # Maven configuration
└── README.md
```

## 🔧 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/check-username/{username}` - Check username availability
- `GET /api/auth/check-email/{email}` - Check email availability

### Jobs
- `GET /api/jobs` - Get all jobs
- `GET /api/jobs/active` - Get active jobs
- `GET /api/jobs/{id}` - Get job by ID
- `POST /api/jobs` - Create new job
- `PUT /api/jobs/{id}` - Update job
- `DELETE /api/jobs/{id}` - Delete job
- `GET /api/jobs/search` - Search jobs with filters
- `GET /api/jobs/filters/locations` - Get distinct locations
- `GET /api/jobs/filters/categories` - Get distinct categories
- `GET /api/jobs/filters/job-types` - Get distinct job types

### Job Applications
- `POST /api/applications` - Submit job application
- `GET /api/applications/user/{userId}` - Get user applications
- `GET /api/applications/job/{jobId}` - Get job applications
- `PUT /api/applications/{id}/status` - Update application status
- `GET /api/applications/check/{jobId}/{userId}` - Check if user applied

### Companies
- `GET /api/companies` - Get all companies
- `GET /api/companies/{id}` - Get company by ID
- `POST /api/companies` - Create company
- `PUT /api/companies/{id}` - Update company
- `DELETE /api/companies/{id}` - Delete company

## 🎨 UI Features

### Homepage
- Hero section with job search
- Statistics dashboard
- Featured job listings
- Company showcase

### Job Search
- Advanced filtering options
- Real-time search
- Job cards with key information
- Pagination support

### User Interface
- Responsive design
- Modern color scheme
- Intuitive navigation
- Loading states and error handling

## 🔐 Security Features

- Password encryption with BCrypt
- CORS configuration
- Input validation
- SQL injection prevention
- XSS protection

## 📱 Responsive Design

The application is fully responsive and works on:
- Desktop computers
- Tablets
- Mobile phones
- Various screen sizes

## 🚀 Deployment

### Production Deployment
1. Update `application.properties` for production database
2. Configure security settings
3. Build the application:
```bash
mvn clean package -Pproduction
```
4. Deploy the JAR file to your server
5. Configure reverse proxy (Nginx/Apache) if needed

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/job_portal-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

If you encounter any issues or have questions:

1. Check the existing issues
2. Create a new issue with detailed description
3. Contact the development team

## 🔮 Future Enhancements

- [ ] JWT token authentication
- [ ] Email notifications
- [ ] File upload for resumes
- [ ] Advanced search with AI
- [ ] Mobile app
- [ ] Video interviews
- [ ] Job recommendations
- [ ] Company reviews
- [ ] Salary insights
- [ ] Multi-language support

## 📊 Database Schema

The application uses the following main entities:
- **User** - User accounts and profiles
- **Company** - Company information
- **Job** - Job postings
- **JobApplication** - Job applications
- **UserRole** - User roles (JOB_SEEKER, EMPLOYER, ADMIN)
- **JobStatus** - Job status (ACTIVE, INACTIVE, CLOSED, DRAFT)
- **ApplicationStatus** - Application status (PENDING, REVIEWED, etc.)

---

**JobPortal** - Connecting talent with opportunity! 🚀
