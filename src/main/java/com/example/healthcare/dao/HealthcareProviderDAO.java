package com.example.healthcare.dao;

import com.example.healthcare.entity.HealthcareProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthcareProviderDAO extends JpaRepository<HealthcareProvider, Integer> {
    
    List<HealthcareProvider> findBySpecialization(String specialization);
    
    List<HealthcareProvider> findByDepartment(String department);
    
    List<HealthcareProvider> findByIsActive(boolean isActive);
    
    @Query("SELECT hp FROM HealthcareProvider hp WHERE hp.firstName LIKE %:name% OR hp.lastName LIKE %:name%")
    List<HealthcareProvider> findByNameContaining(@Param("name") String name);
    
    List<HealthcareProvider> findByDepartmentAndSpecialization(String department, String specialization);
} 