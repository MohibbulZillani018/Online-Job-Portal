package com.portal.job_portal.service;

import com.portal.job_portal.entity.Company;
import com.portal.job_portal.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }
    
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        
        company.setName(companyDetails.getName());
        company.setDescription(companyDetails.getDescription());
        company.setEmail(companyDetails.getEmail());
        company.setWebsite(companyDetails.getWebsite());
        company.setPhone(companyDetails.getPhone());
        company.setAddress(companyDetails.getAddress());
        company.setCity(companyDetails.getCity());
        company.setState(companyDetails.getState());
        company.setCountry(companyDetails.getCountry());
        company.setLogoUrl(companyDetails.getLogoUrl());
        company.setIndustry(companyDetails.getIndustry());
        company.setCompanySize(companyDetails.getCompanySize());
        
        return companyRepository.save(company);
    }
    
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }
    
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    
    public List<Company> getCompaniesByUser(Long userId) {
        return companyRepository.findByUser_Id(userId);
    }
    
    public List<Company> getCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustry(industry);
    }
    
    public List<Company> getCompaniesByCity(String city) {
        return companyRepository.findByCity(city);
    }
    
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
