package com.portal.job_portal.repository;

import com.portal.job_portal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByUser_Id(Long userId);
    Optional<Company> findByName(String name);
    List<Company> findByIndustry(String industry);
    List<Company> findByCity(String city);
}
